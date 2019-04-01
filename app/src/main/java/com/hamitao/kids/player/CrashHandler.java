package com.hamitao.kids.player;

import android.os.Build;
import android.util.Log;

import com.hamitao.kids.BuildConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by linjianwen on 2018/3/1.
 * 捕获异常处理
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CrashHandler instance = new CrashHandler();
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfo(ex);
        mDefaultHandler.uncaughtException(thread, ex);
    }

    private void saveCrashInfo(Throwable ex) {
        String stackTrace = Log.getStackTraceString(ex);
        String filePath = MusicFileUtil.getLogDir() + "crash.log";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
            bw.write("*** crash ***\n");
            bw.write(getInfo());
            bw.write(stackTrace);
            bw.newLine();
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getInfo() {
        String time = TIME_FORMAT.format(new Date());
        String version = BuildConfig.VERSION_NAME + "/" + BuildConfig.VERSION_CODE;
        String device = Build.MANUFACTURER + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE;
        StringBuilder sb = new StringBuilder();
        sb.append("*** time: ").append(time).append(" ***").append("\n");
        sb.append("*** version: ").append(version).append(" ***").append("\n");
        sb.append("*** device: ").append(device).append(" ***").append("\n");
        return sb.toString();
    }
}
