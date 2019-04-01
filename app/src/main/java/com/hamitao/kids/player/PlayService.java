package com.hamitao.kids.player;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by linjianwen on 2018/3/1.
 * <p>
 * 音乐后台播放服务
 */

public class PlayService extends Service {

    private static final String TAG = PlayService.class.getSimpleName();

    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    @Override
    public void onCreate() {

        super.onCreate();
        //初始化播放器
        AudioPlayer.get().init(this);
        //初始化媒体会话管理器
        MediaSessionManager.get().init(this);

        Notifier.get().init(this);

        QuitTimer.get().init(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }


    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        context.startService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_STOP:
                    stop();
                    break;
            }
        }
        return START_NOT_STICKY;
    }


    private void stop() {
        AudioPlayer.get().stopPlayer();
        QuitTimer.get().stop();
        Notifier.get().cancelAll();
    }
}
