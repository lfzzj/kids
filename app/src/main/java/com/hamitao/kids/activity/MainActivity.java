package com.hamitao.kids.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.FileUtil;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.fragment.MeFragment;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.fragment.RecommendFragment;
import com.hamitao.kids.fragment.SquareFragment;
import com.hamitao.kids.fragment.WechatFragment;
import com.hamitao.kids.model.VersionModel;
import com.hamitao.kids.mvp.main.MainPresenter;
import com.hamitao.kids.mvp.main.MainView;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.player.AppCache;
import com.hamitao.kids.player.PlayService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.api.JCoreInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

@Route("main")
public class MainActivity extends BaseActivity implements MainView, WechatFragment.UnReadMsgListener {

    @BindViews({R.id.iv_recommend, R.id.iv_wechat, R.id.iv_square, R.id.iv_me})
    List<ImageView> tab_ivs;
    @BindViews({R.id.tab_recommend, R.id.tab_wechat, R.id.tab_square, R.id.tab_me})
    List<View> tabs;
    @BindViews({R.id.tv_recommend, R.id.tv_wechat, R.id.tv_square, R.id.tv_me})
    List<TextView> tab_tvs;

    @BindView(R.id.music)
    ImageView iv_music;

    @BindView(R.id.tv_unread_num)
    TextView tv_unread_num;

    private ServiceConnection serviceConnection;
    private PlayService playService;
    private FragmentManager fragmentManager;
    private RecommendFragment recommendFragment;
    private WechatFragment wechatFragment;
    private SquareFragment squareFragment;
    private MeFragment meFragment;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            //是否为异常崩溃之后启动方式或者是后台长时间被系统回收重启
            if (savedInstanceState.getBoolean("isExceptionStart", false)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            }
        }
        bindService();

        initPermisson();

        initData();
    }

    @Override
    protected void onDestroy() {
        //如果打开了定时关闭，页面销毁前，记录当前的时间
        if (PropertiesUtil.getInstance().getBoolean(PropertiesUtil.SpKey.isTimer, false)) {
            PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Close_App_time, String.valueOf(System.currentTimeMillis()));
        }
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        serviceConnection = new MainActivity.PlayServiceConnection();
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    /**
     * 获取更新信息
     *
     * @param bean
     */
    @Override
    public void getVersionUpdateInfo(VersionModel.ResponseDataObjBean bean) {
        //是否有新版本
        if (Integer.parseInt(bean.getUptodateVersion().getVersioncode()) > Constant.versionCode) {
            //是否强制更新
            if (bean.getUptodateVersion().isForce()) {
                //强制更新
                showForceUpdateDialog(bean.getUptodateVersion().getTitle(), bean.getUptodateVersion().getTip(), bean.getUptodateVersion().getAppversion(), bean.getUptodateVersion().getVersioncode());
            } else {
                //非强制更新
                showUpdateDialog(bean.getUptodateVersion().getTitle(), bean.getUptodateVersion().getTip(), bean.getUptodateVersion().getAppversion(), bean.getUptodateVersion().getVersioncode());
            }
        }
    }


    /**
     * 获取设备绑定关系
     *
     * @param bean
     */
    @Override
    public void getRobotRelation(DeviceRelationModel.ResponseDataObjBean bean) {
        //查询成功后后将机器人关系存入本地
        PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Device_Relation, GsonUtil.GsonToString(bean.getRelation().getTerminalInfos()));
        //是否绑定了我们公司的机器人，如果有绑定则开放制卡，课表，等功能。
        PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isBindDevice, bean.getRelation().getTerminalInfos().size() <= 0 ? false : true);
    }

    /**
     * 禁止侧滑删除
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 初始化权限
     */
    private void initPermisson() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            ToastUtil.showShort("没有权限");
                        }
                    }
                });
    }

    /**
     * 显示强制更新弹窗
     */
    private void showForceUpdateDialog(String title, String msg, String versionName, final String versionCode) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //点击返回键无效
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_warn);

        TextView tv_title = (TextView) window.findViewById(R.id.title);
        TextView tv_msg = (TextView) window.findViewById(R.id.tv_msg);
        tv_msg.setGravity(Gravity.LEFT);
        TextView btn_update = (TextView) window.findViewById(R.id.tv_confirm);

        tv_title.setText(title);
        tv_msg.setText(msg);
        btn_update.setText("立即更新");

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVersion(versionCode);
            }
        });
    }

    /**
     * 非强制显示更新弹窗
     */
    private void showUpdateDialog(String title, String msg, String versionName, final String versionCode) {
        View view = LayoutInflater.from(this).inflate(com.hamitao.base_module.R.layout.dialog_tips, null);

        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_msg)).setText(msg);
        ((TextView) view.findViewById(R.id.tv_msg)).setGravity(Gravity.LEFT);
        ((TextView) view.findViewById(R.id.tv_confirm)).setText("立即更新");
        ((TextView) view.findViewById(R.id.tv_cancel)).setText("下次再说");

        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.6f)
                .setGravity(Gravity.CENTER)
                .setDimAmount(0.25f)
                .setCancelableOutside(false)
                .setCancelable(true)
                .addOnClickListener(com.hamitao.base_module.R.id.tv_cancel, com.hamitao.base_module.R.id.tv_confirm)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            updateVersion(versionCode);
                        }
                        tDialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void refreshUnReadMsg() {
        getUnReadCount();
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playService = ((PlayService.PlayBinder) service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e(getClass().getSimpleName(), "service disconnected");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("isExceptionStart", true);
    }


    //初始化
    private void initData() {
        JPushInterface.resumePush(this);
        JMessageClient.registerEventReceiver(this);
        fragmentManager = getSupportFragmentManager();
        presenter = new MainPresenter(this);
        //默认选中第一个tab
        showFragment(1);
        AppCache.get().init(BaseApplication.getInstance());
        //未读消息数
        getUnReadCount();

        //判断用户头像是否上传
        if (UserUtil.user() != null) {
            if (JMessageClient.getMyInfo() != null && JMessageClient.getMyInfo().getAvatarFile() == null) {
                BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_head_default);
                Bitmap mBitmap = bd.getBitmap();
                final File header = FileUtil.saveBitmapFile(mBitmap, Constant.USER_PIC_LOCAL, "default_header.png");
                JMessageClient.updateUserAvatar(header, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            //上传成功后后删除本地的头像文件
                            if (header.exists()) {
                                header.delete();
                            }
                        }
                    }
                });
            } else if (JMessageClient.getMyInfo() != null && JMessageClient.getMyInfo().getNickname() == null) {
                //上传昵称
            }
        }

        //检查版本更新
        presenter.checkNewVersion();

        if (UserUtil.user() != null) {
            presenter.queryRelationById(UserUtil.user().getCustomerid());
        }
    }

    //获取未读消息数
    private void getUnReadCount() {
        final int count = JMessageClient.getAllUnReadMsgCount();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count > 0) {
                    tv_unread_num.setVisibility(View.VISIBLE);
                    tv_unread_num.setText(count > 99 ? "99+" : count + "");
                } else {
                    tv_unread_num.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onEvent(MessageEvent event) {
    }

    public void onEventMainThread(MessageEvent event) {
        getUnReadCount();
    }

    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        final String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        switch (event.getType()) {
            case invite_received:
                //收到好友邀请
                //...
                break;
            case invite_accepted:
                //对方接收了你的好友邀请
                //修改机器人的备注名字
                JMessageClient.getUserInfo(fromUsername, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (0 == i) {
                            if (UserUtil.user() != null) {
                                setNoteName(UserUtil.user().getCustomerid(), fromUsername, userInfo);
                            }

                        }
                    }
                });

                break;
            case invite_declined:
                //对方拒绝了你的好友邀请
                //...
                break;
            case contact_deleted:
                //对方将你从好友中删除
                //...
                break;
            default:
                break;
        }
    }

    /**
     * 设置备注明
     *
     * @param customerid
     * @param deviceid
     * @param userinfo
     */
    private void setNoteName(String customerid, final String deviceid, final UserInfo userinfo) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                final DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                for (int i = 0; i < bean.getRelation().getTerminalInfos().size(); i++) {
                    if (deviceid.equals(bean.getRelation().getTerminalInfos().get(i).getDeviceid())) {
                        userinfo.updateNoteName(bean.getRelation().getTerminalInfos().get(i).getBindname(), new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (0 == i) {
                                    userinfo.updateNoteName(bean.getRelation().getTerminalInfos().get(i).getBindname(), new BasicCallback() {
                                        @Override
                                        public void gotResult(int i, String s) {
                                            if (0 == i) {
                                                RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                                                LogUtil.d("MainActivity", "备注名更新成功");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }

    //    ---------------------------------------------Fragment显藏-----------------------------------------------

    private void showFragment(int page) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (page) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (recommendFragment != null) {
                    ft.show(recommendFragment);
                }
                // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    recommendFragment = new RecommendFragment();
                    ft.add(R.id.fragment_container, recommendFragment);
                }
                //选中第一个Tab时的状态
                tab_ivs.get(0).setImageResource(tabResId_p[0]);
                tab_tvs.get(0).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                break;
            case 2:
                if (wechatFragment != null) {
                    ft.show(wechatFragment);
                } else {
                    wechatFragment = new WechatFragment();
                    wechatFragment.setUnReadMsgListener(this);
                    ft.add(R.id.fragment_container, wechatFragment);
                }
                break;
            case 3:
                if (squareFragment != null) {
                    ft.show(squareFragment);
                } else {
                    squareFragment = new SquareFragment();
                    ft.add(R.id.fragment_container, squareFragment);
                }
                break;
            case 4:
                if (meFragment != null) {
                    ft.show(meFragment);
                } else {
                    meFragment = new MeFragment();
                    ft.add(R.id.fragment_container, meFragment);
                }
                break;
            default:
                break;

        }
        ft.commit();
    }


    /**
     * 当fragment已被实例化，相当于发生过切换，就隐藏起来
     *
     * @param ft
     */
    public void hideFragments(FragmentTransaction ft) {
        if (recommendFragment != null) {
            ft.hide(recommendFragment);
        }
        if (wechatFragment != null) {
            ft.hide(wechatFragment);
        }
        if (squareFragment != null) {
            ft.hide(squareFragment);
        }
        if (meFragment != null) {
            ft.hide(meFragment);
        }
    }


    //    ---------------------------------------------点击事件----------------------------------------------------
    private int index;
    private int currentTabIndex;// 当前fragment的index
    private int tabResId[] = {R.drawable.icon_recommand_n, R.drawable.icon_wechat_n, R.drawable.icon_square_n, R.drawable.icon_me_n,};
    private int tabResId_p[] = {R.drawable.icon_recommand_p, R.drawable.icon_wechat_p, R.drawable.icon_square_p, R.drawable.icon_me_p};

    public void onTabClick(View v) {
        //每次点击遍历下标
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i) == v) {
                index = i;
                break;
            }
        }
        if (currentTabIndex != index) {
            showFragment(index + 1);
            tab_ivs.get(currentTabIndex).setImageResource(tabResId[currentTabIndex]);
            tab_tvs.get(currentTabIndex).setTextColor(ContextCompat.getColor(this, R.color.text_default_d));
        }
        tab_ivs.get(index).setImageResource(tabResId_p[index]);
        tab_tvs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        currentTabIndex = index;
    }

    @OnClick({R.id.tab_recommend, R.id.tab_wechat, R.id.tab_square, R.id.tab_me, R.id.music})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_recommend:
                onTabClick(tabs.get(0));
                break;
            case R.id.tab_wechat:
                onTabClick(tabs.get(1));
                break;
            case R.id.tab_square:
                onTabClick(tabs.get(2));
                break;
            case R.id.tab_me:
                onTabClick(tabs.get(3));
                break;
            case R.id.music:
                PlayFragment.getInstance().show(getSupportFragmentManager(), "main");
                break;
            default:
                break;
        }
    }
    //    ---------------------------------------------点击事件----------------------------------------------------


    //------------------------------双击返回桌面---------------------------------------------------
    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                ToastUtil.showShort("再次点击退出程序");
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    //------------------------------双击返回桌面---------------------------------------------------
}