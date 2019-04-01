package com.hamitao.kids.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hamitao.kids.player.AudioPlayer;

/**
 * Created by linjianwen on 2018/3/1.
 * <p>
 * 来电/耳机拔出时暂停播放
 */

public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioPlayer.get().playPause();
    }

}
