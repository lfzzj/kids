package com.hamitao.kids.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.DataCleanUtil;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.VersionModel;
import com.hamitao.kids.network.NetworkRequest;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.hamitao.base_module.Constant.USER_APK_LOCAL;
import static com.hamitao.base_module.Constant.USER_CHAT_RECORD;
import static com.hamitao.base_module.Constant.USER_PIC_LOCAL;
import static com.hamitao.base_module.Constant.USER_RECORD_LOCAL;
import static com.hamitao.base_module.Constant.USER_VOICE_LOCAL;


/**
 * Created by linjianwen on 2018/1/4.
 * <p>
 * 设置
 */
@Route("setting")
public class SettingActivity extends BaseActivity {

    public static final String TAG = SettingActivity.class.getSimpleName();


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.switch_push)
    ImageView switchPush;
    @BindView(R.id.tv_checkVersion)
    TextView tv_checkVersion;

    @BindView(R.id.tv_cache)
    TextView tv_cache;

    PackageInfo packageInfo;

    DataCleanUtil cleanUtil;


    private boolean isOpen = true; //是否打开推送

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setVisibility(View.VISIBLE);
        title.setText("设置");
        switchPush.setImageResource(isOpen ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        try {
            packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_checkVersion.setText(packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        cleanUtil = DataCleanUtil.getInstance();
        List<String> paths = new ArrayList<>();
        paths.add(USER_PIC_LOCAL);    //图片
        paths.add(USER_APK_LOCAL);    //安装包
        paths.add(USER_VOICE_LOCAL);  //视频
        paths.add(USER_RECORD_LOCAL); //录音
        paths.add(USER_CHAT_RECORD);  //聊天语音
        cleanUtil.init(paths);
        //初始化缓存路径
        try {
            tv_cache.setText(cleanUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.rl_push, R.id.rl_cleanCache, R.id.rl_about, R.id.rl_feedBack, R.id.rl_checkVersion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_push:
                if (isOpen) {
                    isOpen = false;
                    switchPush.setImageResource(R.drawable.icon_switch_n);
                    //关闭推送
                    ToastUtil.showShort("关闭推送");
                    JPushInterface.stopPush(BaseApplication.getInstance());
                } else {
                    isOpen = true;
                    //重新开启推送
                    switchPush.setImageResource(R.drawable.icon_switch_p);
                    if (JPushInterface.isPushStopped(BaseApplication.getInstance())) {
                        ToastUtil.showShort("打开推送");
                        JPushInterface.resumePush(BaseApplication.getInstance());
                    }
                }
                break;
            case R.id.rl_cleanCache:
                showConfirmDialog(this, "确定要清除所有数据吗?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_confirm:
                                cleanCache(SettingActivity.this);
                                break;
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.rl_about:
                Router.build("about").go(this);
                break;
            case R.id.rl_feedBack:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    Router.build("feedback").go(this);
                }
                break;
            case R.id.rl_checkVersion:
                checkNewVersion();
                break;
            default:
                break;
        }
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public void cleanCache(final Context context) {
        cleanUtil.cleanInternalCache(context);  //清除内部缓存
        cleanUtil.cleanExternalCache(context);  //清除外部存储
        cleanUtil.cleanCustomCache(USER_PIC_LOCAL); //清除图片
        cleanUtil.cleanCustomCache(USER_APK_LOCAL); //清除安装包
        cleanUtil.cleanCustomCache(USER_VOICE_LOCAL);  //清除音频
        cleanUtil.cleanCustomCache(USER_RECORD_LOCAL);  //清除录音
        cleanUtil.cleanCustomCache(USER_CHAT_RECORD); //清除聊天录音
        PropertiesUtil.getInstance().destory();//清除SharePreference的数据
        ToastUtil.showShort("清除成功");
        tv_cache.setText("0KB");
    }


    /**
     * 检查版本更新
     */
    public void checkNewVersion() {
        NetworkRequest.updateVersionRequest(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

//                String str = "{\n" +
//                        "    \"uptodateVersion\": {\n" +
//                        "        \"appversion\": \"1.0.1\",\n" +
//                        "        \"force\": false,\n" +
//                        "        \"httpURL\": \"http://cloud.kidknow.net:8888/appdoc/xiaotaotongxue.apk\",\n" +
//                        "        \"osversion\": \">10.3\",\n" +
//                        "        \"tip\": \"修复了部分bug\",\n" +
//                        "        \"title\": \"版本更新\",\n" +
//                        "        \"url\": \"aaa/bbb/ccc\",\n" +
//                        "        \"urltype\": \"keyonoss\",\n" +
//                        "        \"versioncode\": \"2\"\n" +
//                        "    }\n" +
//                        "}";

//                VersionModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(str,VersionModel.ResponseDataObjBean.class);

                VersionModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), VersionModel.ResponseDataObjBean.class);
                //这里进行升级操作
                updateVersion(bean.getUptodateVersion());
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {
            }
        }));
    }


    /**
     * 版本更新
     *
     * @param uptodateVersion
     */
    public void updateVersion(VersionModel.ResponseDataObjBean.UptodateVersionBean uptodateVersion) {

        if (Integer.parseInt(uptodateVersion.getVersioncode() == null ? "1" : uptodateVersion.getVersioncode()) <= Constant.versionCode) {
            ToastUtil.showShort("当前已是最新版本");
            return;
        }

        if (Integer.parseInt(uptodateVersion.getVersioncode()) > Constant.versionCode) {
            //这里没有做是否强制更新判断，因为APP每次进来都会进行版本判断处理
            showUpdateDialog(uptodateVersion.getTitle(), uptodateVersion.getTip(), uptodateVersion.getAppversion(), uptodateVersion.getVersioncode());
        }
    }


    /**
     * 更新弹窗
     *
     * @param title
     * @param msg
     * @param versionName
     * @param versionCode
     */
    private void showUpdateDialog(String title, String msg, String versionName, final String versionCode) {
        View view = LayoutInflater.from(this).inflate(com.hamitao.base_module.R.layout.dialog_tips, null);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_msg)).setText(msg);
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


}
