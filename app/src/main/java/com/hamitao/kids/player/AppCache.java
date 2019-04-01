package com.hamitao.kids.player;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linjianwen on 2018/3/1.
 */

public class AppCache {
    private Context mContext;
    private final List<MediaListBean> mLocalMusicList = new ArrayList<>();
    //    private final List<SheetInfo> mSheetList = new ArrayList<>();//表信息
    private final List<Activity> mActivityStack = new ArrayList<>();
//    private final LongSparseArray<DownloadMusicInfo> mDownloadList = new LongSparseArray<>();//下载音乐信息

    private AppCache() {
    }

    private static class SingletonHolder {
        private static AppCache instance = new AppCache();
    }

    public static AppCache get() {
        return SingletonHolder.instance;
    }

    public void init(Application application) {
        mContext = application.getApplicationContext();

        CrashHandler.getInstance().init();
        CoverLoader.getInstance().init(mContext);
        application.registerActivityLifecycleCallbacks(new ActivityLifecycle());
    }

    public Context getContext() {
        return mContext;
    }

    public List<MediaListBean> getLocalMusicList() {
        return mLocalMusicList;
    }

/*    public List<SheetInfo> getSheetList() {
        return mSheetList;
    }*/

    public void clearStack() {
        List<Activity> activityStack = mActivityStack;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }
/*
    public LongSparseArray<DownloadMusicInfo> getDownloadList() {
        return mDownloadList;
    }*/


    private class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
        private static final String TAG = "Activity";

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, "onCreate: " + activity.getClass().getSimpleName());
            mActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onDestroy: " + activity.getClass().getSimpleName());
            mActivityStack.remove(activity);
        }
    }
}
