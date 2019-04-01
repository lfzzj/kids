package com.hamitao.base_module.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.R;
import com.hamitao.base_module.receiver.IntentReceiver;
import com.hamitao.base_module.util.DownloadUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {

    //侧滑
    protected BGASwipeBackHelper mSwipeBackHelper;

    private static final int WHAT_LOADING = 0;

    private BroadcastReceiver receiver = new IntentReceiver();


    /**
     * 屏幕的宽度、高度、密度
     */
    public static int mScreenWidth;
    public static int mScreenHeight;
    public static float mDensity;
    public static int mDensityDpi;


    private LoadingDailog loadingDailog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_LOADING:
                    if (loadingDailog != null && loadingDailog.isShowing()) {
                        loadingDailog.dismiss();
                    }
                    return;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        //禁止横屏`
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
        mDensityDpi = metric.densityDpi;

//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        this.registerReceiver(receiver,filter);
    }




    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
//        unregisterReceiver(receiver);
    }


    public void showProgressDialog() {
        if (loadingDailog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(true)
                    .setCancelOutside(false);
            loadingDailog = loadBuilder.create();
        }
        if (!loadingDailog.isShowing()) {
            try {
                loadingDailog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessageDelayed(WHAT_LOADING, 10 * 1000);
    }

    public void showProgressDialog(String tips) {
        if (loadingDailog == null) {
            LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                    .setMessage(tips)
                    .setCancelable(true)
                    .setCancelOutside(false);
            loadingDailog = loadBuilder.create();
        }
        if (!loadingDailog.isShowing()) {
            try {
                loadingDailog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessageDelayed(WHAT_LOADING, 10 * 1000);
    }

    public void dismissProgressDialog() {
        if (null != loadingDailog && loadingDailog.isShowing()) {
            try {
                loadingDailog.dismiss();
                loadingDailog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 更新应用版本
     *
     * @param versionCode
     */
    public void updateVersion(final String versionCode) {


        final DownloadUtil downloadUtil = new DownloadUtil(this);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                String apkurl = BaseApplication.getInstance().getOssTool().getUrlByObjectName("apk/app/xiaotaotongxue_" + versionCode + ".apk");
                //存放在阿里云服务器的URL
                if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())) {
                    apkurl = BaseApplication.getInstance().getOssTool().getUrlByObjectName("apk/app/" + Constant.XIAOSHUAI + "_" + versionCode + ".apk");
                } else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
                    apkurl = BaseApplication.getInstance().getOssTool().getUrlByObjectName("apk/app/" + Constant.JINGUOWEI + "_" + versionCode + ".apk");
                }
                e.onNext(apkurl);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                ToastUtil.showShortError("应用正在下载...");
                if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())) {
                    downloadUtil.downloadAPK(s, Constant.USER_APK_LOCAL, Constant.XIAOSHUAI+".apk");
                } else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
                    downloadUtil.downloadAPK(s, Constant.USER_APK_LOCAL, Constant.JINGUOWEI+".apk");
                }else {
                    downloadUtil.downloadAPK(s, Constant.USER_APK_LOCAL, "xiaotaotongxue.apk");
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortError("下载错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 显示确认弹窗
     *
     * @param context
     * @param msg
     * @param listener
     */
    public void showConfirmDialog(Context context, String msg, OnViewClickListener listener) {
        View view = LayoutInflater.from(context).inflate(com.hamitao.base_module.R.layout.dialog_tips, null);
        TextView tv_msg = view.findViewById(com.hamitao.base_module.R.id.tv_msg);
        tv_msg.setText(msg);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.6f)
                .setGravity(Gravity.CENTER)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(com.hamitao.base_module.R.id.tv_cancel, com.hamitao.base_module.R.id.tv_confirm)
                .setOnViewClickListener(listener).create().show();
    }


    /**
     * 显示警告弹窗
     */
    public void showWarnDialog(Context context, String msg, OnViewClickListener listener) {
        View view = LayoutInflater.from(context).inflate(com.hamitao.base_module.R.layout.dialog_warn, null);
        TextView tv_msg = view.findViewById(com.hamitao.base_module.R.id.tv_msg);
        tv_msg.setText(msg);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.6f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.CENTER)     //设置弹窗展示位置
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.25f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(false)     //弹窗在界面外是否可以点击取消
                .setCancelable(true)    //弹窗是否可以取消
                .addOnClickListener(com.hamitao.base_module.R.id.tv_confirm)   //添加进行点击控件的id
                .setOnViewClickListener(listener).create().show();
    }


    /**
     * 显示分享弹窗
     */
    public void showShareDialog(Activity context, OnViewClickListener listener) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_comment)
                .setScreenWidthAspect(context, 1f)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.tv_confirm, "分享到广场");
                    }
                })
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(listener)
                .create()   //创建TDialog
                .show();    //展示;
    }


    /**
     * 震动
     *
     * @time 震动时长
     */
    public void vibrate(long time) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    /**
     * 震动
     */
    public void vibrate50() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

    /**
     * 显示输入弹窗
     */
    public void showInputDialog(Context context, String title, OnViewClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_input, null);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((EditText) view.findViewById(R.id.et_name)).setHint(title);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.8f)
                .setGravity(Gravity.CENTER)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(listener)
                .create()
                .show();
    }

    /**
     * 显示输入弹窗
     */
    public void showInputDialog(Context context, String title, OnViewClickListener listener, OnBindViewListener bindListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_input, null);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((EditText) view.findViewById(R.id.et_name)).setHint(title);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.8f)
                .setGravity(Gravity.CENTER)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setOnBindViewListener(bindListener)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(listener)
                .create()
                .show();
    }


    /**
     * 显示登录弹窗
     */
    public void showLoginDialog() {
//        showConfirmDialog(this, "请先登录", new OnViewClickListener() {
//            @Override
//            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
//                if (view.getId() == R.id.tv_confirm) {
//                    Router.build("login").go(BaseActivity.this);
//                }
//                tDialog.dismiss();
//            }
//        });
        ToastUtil.showShort("请先登录");
    }


    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }


    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }


    /**
     * 取消侧滑
     */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }


    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


}
