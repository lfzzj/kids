package com.hamitao.kids.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by linjianwen on 2018/3/1.
 */

public class StatusBarReceiver extends BroadcastReceiver {

    public static final String ACTION_STATUS_BAR = "com.hamitao.kids.STATUS_BAR_ACTIONS";
    public static final String EXTRA = "extra";
    //    public static final String EXTRA_PRE = "pre";
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_PLAY_PAUSE = "play_pause";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }

        String extra = intent.getStringExtra(EXTRA);
        if (TextUtils.equals(extra, EXTRA_NEXT)) {
            AudioPlayer.get().next(true); //播放下一首
        } else if (TextUtils.equals(extra, EXTRA_PLAY_PAUSE)) {
            AudioPlayer.get().playPause(); //播放或暂停
        }

//        else if (TextUtils.equals(extra, EXTRA_PRE)){
//            AudioPlayer.get().prev(); //播放上一首
//        }
    }
}
