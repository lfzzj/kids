package com.hamitao.base_module.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.chenenyu.router.BuildConfig;
import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.model.OssProvider;
import com.hamitao.base_module.oss.OssConfig;
import com.hamitao.base_module.oss.OssTool;
import com.hamitao.base_module.util.AppVersionUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.RecordUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseApplication extends Application {

    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<Message> forwardMsg = new ArrayList<>();
    public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<UserInfo> mSearchGroup = new ArrayList<>();
    public static List<UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<Message> ids = new ArrayList<>();
    public static List<UserInfo> alreadyRead = new ArrayList<>();
    public static List<UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();

    private static final String TAG = BaseApplication.class.getSimpleName();


    public String vendor; // 厂家、默认为哈蜜淘

    private boolean allowGSM = false; //是否允许使用移动流量播放

    public boolean isAllowGSM() {
        return allowGSM;
    }

    public void setAllowGSM(boolean allowGSM) {
        this.allowGSM = allowGSM;
    }

    /**
     * 内存泄漏
     */
//    private RefWatcher refWatcher;


    private OSS oss;
    private OssTool ossTool;

    public static BaseApplication instance;

    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }


    /**
     * 是否开放小淘课堂的厂家
     *
     * 目前仅仅对 小淘和小帅开放
     *
     *
     * @return
     */
    public boolean isPublicVendor(){
        if (vendor == Constant.HAMITAO|| vendor == Constant.XIAOSHUAI){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        initVendor();
    }


    /**
     * 初始化厂家
     */
    private void initVendor() {
        String packageName = getApplicationContext().getPackageName();
        if (packageName.equals("com.hamitao.haierrobot")) {
            vendor = Constant.XIAOSHUAI;
        } else if (packageName.equals("com.hamitao.jgwrobot")) {
            vendor = Constant.JINGUOWEI;
        } else {
            vendor = Constant.HAMITAO;
        }
    }

    public String getVendor() {
        return vendor;
    }


    private void initApp() {
        instance = this;

        //初始化Sharepreference
        PropertiesUtil.getInstance().init(this);

//        MultiDex.install(this);
          //内存泄漏
//        refWatcher = setupLeakCanary();


        BGASwipeBackHelper.init(this, null);

        File file = new File(Constant.BASE_LOCAL + "/Downloader/");
        if (!file.exists()) {
            file.mkdirs();
        }


        //初始化阿里云
        initOss();
        //初始化录音存放位置：
        RecordUtil.getInstance().setSavePath(Constant.USER_RECORD_LOCAL);

        //应用版本号
        Constant.versionCode = AppVersionUtil.getVersionCode(this);
        //应用版本名
        Constant.versionName = AppVersionUtil.getVersionName(this);

        //初始化极光推送
        //设置开启日志，发布时关闭日志
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        //初始化极光IM，true为消息同步记录漫游
        JMessageClient.init(this, false);
        //极光IM调试模式
        JMessageClient.setDebugMode(false);

        //初始化路由框架
        Router.initialize(new Configuration.Builder()
                // 调试模式，开启后会打印log
                .setDebuggable(BuildConfig.DEBUG)
                // 模块名，每个使用Router的module都要在这里注册
                .registerModules("app", "openvioce_module", "base_module", "chat_module")
                .build());
    }


    /**
     * 初始化阿里云OSS
     */
    private void initOss() {
        ossTool = initOSS(OssConfig.endpoint, OssConfig.bucket);
    }

    public OssTool initOSS(String endpoint, String bucket) {
        OSSCredentialProvider provider = new OssProvider(OssConfig.STSSERVER);
        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(5);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);
        oss = new OSSClient(getApplicationContext(), endpoint, provider, conf);
        return new OssTool(oss, bucket);
    }

    public OssTool getOssTool() {
        return ossTool;
    }

    public OSS getOss() {
        return oss;
    }

//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }

//    public static RefWatcher getRefWatcher() {
//        return instance.refWatcher;
//    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}
