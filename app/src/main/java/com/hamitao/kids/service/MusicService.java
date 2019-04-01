package com.hamitao.kids.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by linjianwen on 2018/3/6.
 */

public class MusicService extends Service {

    public MediaPlayer mediaPlayer = new MediaPlayer();
    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicService() {
        try {
            mediaPlayer.setDataSource("http://www.tingge123.com/mp3/2016-09-15/1473940221.mp3");//绑定播放的歌曲
            mediaPlayer.prepare();//进入就绪状态
            mediaPlayer.setLooping(true);//设置循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();//停止歌曲播放
            mediaPlayer.release();//释放mediaPlayer资源
        }
    }

    public void playORpuase() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();//暂停
        } else {
            mediaPlayer.start();//开始
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();//停止
            try {
                mediaPlayer.prepare();//就绪
                mediaPlayer.seekTo(0);//设置歌曲回到最开始
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
