package com.hamitao.kids.view;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

import com.hamitao.kids.model.event.PlayVideoEvent;
import com.hamitao.kids.player.AudioPlayer;

import org.greenrobot.eventbus.EventBus;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

/**
 * Created by linjianwen on 2018/9/3.o
 */
public class MyVideoView extends JzvdStd {

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startVideo() {
        //如果音频正在播放，则暂停
        if (AudioPlayer.get().isPlaying()){
            AudioPlayer.get().pausePlayer();
        }
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            Log.d(TAG, "startVideo [" + this.hashCode() + "] ");
            initTextureView();
            addTextureView();
            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            JZMediaManager.setDataSource(jzDataSource);
            JZMediaManager.instance().positionInList = positionInList;
            onStatePreparing();
        } else {
            super.startVideo();
        }
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    public int  getCurrentScreen(){
        return currentScreen;
    }

    @Override
    public void onAutoCompletion() {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {

            onStateAutoComplete();
        } else {
            //这个方法会退出全屏
            super.onAutoCompletion();

        }
        EventBus.getDefault().post(new PlayVideoEvent("playnext",currentScreen));
    }






}
