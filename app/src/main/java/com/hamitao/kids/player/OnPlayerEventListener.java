package com.hamitao.kids.player;

import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean;

/**
 * Created by linjianwen on 2018/3/1.
 * <p>
 * 播放进度监听器
 */

public interface OnPlayerEventListener {

    /**
     * 切换歌曲
     */
    void onChange(MediaListBean music);

    /**
     * 开始播放播放
     */
    void onPlayerStart();

    /**
     * 暂停播放
     */
    void onPlayerPause();

    /**
     * 更新进度
     */
    void onPublish(int progress);

    /**
     * 缓冲百分比
     */
    void onBufferingUpdate(int percent);

    /**
     * 播放位置
     */
    void onPosition(int position);

}
