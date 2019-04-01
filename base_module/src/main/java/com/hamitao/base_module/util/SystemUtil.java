package com.hamitao.base_module.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hamitao.base_module.base.BaseApplication;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class SystemUtil {
    public static final String RING_ON_OFF = "ring_on_off";
    public static final String VIBRATE_ON_OFF = "vibrate_on_off";

    public static final String KEY_RING_TONE = "ring_tone";
    public static final String KEY_NEW_MAIL_VIBRATE = "new_mail_vibrate";

    private static AudioManager volMgr = null;

    private Context context;
    private static Vibrator vibrator = null;
    private static Ringtone ringtone = null;

    private static SystemUtil systemUtil = null;

    public static SystemUtil getInstance(Context context) {
        if (null == systemUtil) {
            systemUtil = new SystemUtil(context);
        }
        return systemUtil;
    }

    private SystemUtil(Context context) {
        this.context = context;
        volMgr = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        vibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);// 获取震动器;
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(context, uri);
    }


    //获取手机系统的语言
    public static String getLanguage() {
        Locale locale = BaseApplication.getInstance().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    @SuppressLint("NewApi")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return tm.getImei();
            }

        }
        return null;
    }


    /**
     * 判断某个界面是否在前台
     *
     * @param activity 要判断的Activity
     * @return 是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }


    /**
     * 设置震动
     */
    @SuppressWarnings("deprecation")
    public void setVibrate() {
        boolean vibrateType = PropertiesUtil.getInstance().getBoolean(
                VIBRATE_ON_OFF, true);
        if (vibrateType) {
            // 即震动,又响铃
            // volMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
            // AudioManager.VIBRATE_SETTING_ON);
            // volMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
            // AudioManager.VIBRATE_SETTING_ON);
            vibrator.vibrate(200);
        } else {
            // 不震动,不响铃
            // volMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
            // AudioManager.VIBRATE_SETTING_OFF);
            // volMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
            // AudioManager.VIBRATE_SETTING_OFF);
        }
    }

    /**
     * 设置提示音
     */
    public void setRing() {

        boolean ringType = PropertiesUtil.getInstance().getBoolean(RING_ON_OFF,
                true);

        if (null != volMgr) {
            if (ringType) {
                // 只响铃,不震动
                // volMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                ringtone.play();
            } else {
                // 只震动,不响铃
                // volMgr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
    }


    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    public enum NetWorkEnum {
        NETTYPE_WIFI, NETTYPE_2G, NETTYPE_3G, NETTYPE_4G, NETTYPE_NONE;
    }


    /**
     * 判断app是否在运行中
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAppAlive(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                //find it, break
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 是否已经安装微信
     *
     * @return
     */
    public static boolean hasInstallWechat(Activity mContext) {
        boolean hasWx = false;
        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> pklist = pm.getInstalledPackages(0);
        for (int i = 0; i < pklist.size(); i++) {
            PackageInfo pak = (PackageInfo) pklist.get(i);
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                if (pak.applicationInfo.packageName.equals("com.tencent.mm")) {
                    hasWx = true;
                }
            }
        }
        System.out.println("has wechat app:" + hasWx);
        return hasWx;
    }

    /**
     * 获取手机机器人号 , GSM手机的 IMEI 和 CDMA手机的 MEID.
     */
    protected static final String PREFS_FILE = "gank_device_id.xml";
    protected static final String PREFS_DEVICE_ID = "gank_device_id";
    protected static String uuid;

    /**
     * 获取唯一标识码
     *
     * @param mContext
     * @return
     */
    public synchronized static String getUDID(Context mContext) {
        if (uuid == null) {
            if (uuid == null) {
                final SharedPreferences prefs = mContext.getApplicationContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
                final String id = prefs.getString(PREFS_DEVICE_ID, null);

                if (id != null) {
                    // Use the ids previously computed and stored in the prefs file
                    uuid = id;
                } else {

                    final String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                    // Use the Android ID unless it's broken, in which case fallback on deviceId,
                    // unless it's not available, then fallback on a random number which we store
                    // to a prefs file
                    try {
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                        } else {
                            @SuppressLint("MissingPermission") final String deviceId = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    // Write the value out to the prefs file
                    prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
                }
            }
        }
        return uuid;
    }

    /**
     * 返回手机厂商
     */
    public String getHandsetmakers() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取机器人名
     *
     * @param activity
     * @return
     */
    public static Build bd = new Build();

    public static String getDevice() {
        return bd.BRAND + "-" + bd.DEVICE;
    }

    /**
     * 获取手机系统版本
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }


    // 检查调用者是否具有 permission权限
    // 此方法仅在调用IPC（interprocess communication）方法时有用
    public static boolean checkPermission(Context context, String permission) {
        // 检查如果是当前应用则返回真
        if (Binder.getCallingPid() == android.os.Process.myPid()) {
            return true;
        }
        if (context.checkCallingPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * 获取渠道
     *
     * @param ctx
     * @return
     */
    public static String getAppMetaData(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(ctx.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString("TD_CHANNEL_ID");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    public static String getMacAddress(Context context) {
        String mMACAddr = "";
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            WifiInfo WifiInfo = mWifiManager.getConnectionInfo();
            mMACAddr = WifiInfo.getMacAddress();
        }
        return mMACAddr;
    }

    private static PowerManager.WakeLock wl;


    private static Bundle metaData(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(context.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        return applicationInfo.metaData;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return new Bundle();
    }

    /**
     * 根据key获取Manifest的meta-data的String值
     *
     * @param key
     * @return
     */
    public static String getMetaDataString(Context context, String key) {
        Bundle bundle = metaData(context);
        if (bundle == null) return "";
        else return bundle.getString(key, "");
    }

    /**
     * 根据key获取Manifest的meta-data的Boolean值
     *
     * @param key
     * @return
     */
    public static boolean getMetaDataBoolean(Context context, String key) {
        Bundle bundle = metaData(context);
        if (bundle == null) return false;
        else return bundle.getBoolean(key, false);
    }

    /**
     * 根据key获取Manifest的meta-data的Boolean值
     *
     * @param key
     * @return
     */
    public static int getMetaDataInteger(Context context, String key) {
        Bundle bundle = metaData(context);
        if (bundle == null) return 0;
        else return bundle.getInt(key, 0);
    }

    /**
     * 安装下载后的应用程序
     *
     * @param activity
     * @param fileName 文件名字
     */
    public static void installNewApk(Activity activity, String path, final String fileName) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(new File(path, fileName)),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);

    }

    /**
     * 检测用户是否已经安装这个app
     *
     * @param packageName
     * @return 已安装返回false
     */
    public static Boolean checkApp(Context context, String packageName) {
        List<PackageInfo> userPackageInfos = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : userPackageInfos) {
            if (packageName.equals(packageInfo.packageName)) {
                return false;
            }
        }
        return true;
    }

    public static String getAppName(Context context, int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


    /**
     * 是否魅族系统
     *
     * @return
     */
    public static boolean isFlyme() {
        String flymeFlag = getSystemProperty("ro.build.display.id");
        return !TextUtils.isEmpty(flymeFlag) && flymeFlag.toLowerCase().contains("flyme");
    }




    /**
     * 获取系统属性
     *
     * @param key
     * @return
     */
    private static String getSystemProperty(String key) {
        try {
            Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            return (String) getMethod.invoke(classType, key);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

}
