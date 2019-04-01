package com.hamitao.kids.player;

import android.os.Build;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean;

/**
 * Created by linjianwen on 2018/3/1.
 * 媒体会话管理器
 */

public class MediaSessionManager {

    private static final String TAG = MediaSessionManager.class.getSimpleName();

    private static final long MEDIA_SESSION_ACTIONS = PlaybackStateCompat.ACTION_PLAY
            | PlaybackStateCompat.ACTION_PAUSE
            | PlaybackStateCompat.ACTION_PLAY_PAUSE
            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            | PlaybackStateCompat.ACTION_STOP
            | PlaybackStateCompat.ACTION_SEEK_TO;

    private PlayService playService;

    private MediaSessionCompat mediaSession;

    public static MediaSessionManager get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static MediaSessionManager instance = new MediaSessionManager();
    }

    private MediaSessionManager() {
    }

    public void init(PlayService playService) {
        this.playService = playService;
        setupMediaSession();
    }

    private void setupMediaSession() {
        mediaSession = new MediaSessionCompat(playService, TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS | MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);
        mediaSession.setCallback(callback);
        mediaSession.setActive(true);
    }


    /**
     * 更新播放状态
     */
    public void updatePlaybackState() {
        int state = (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing()) ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;
        mediaSession.setPlaybackState(
                new PlaybackStateCompat.Builder()
                        .setActions(MEDIA_SESSION_ACTIONS)
                        .setState(state, AudioPlayer.get().getAudioPosition(), 1)
                        .build());
    }

    /**
     * 更新音乐
     * @param music
     */
    public void updateMetaData(MediaListBean music) {
        if (music == null) {
            mediaSession.setMetadata(null);
            return;
        }

        MediaMetadataCompat.Builder metaData = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.getMediatitle()) //音乐名
//                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.getArtist())  //演唱者
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, music.getAlbum())    //专辑
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, music.getArtist())    //演唱者
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, music.getDuration()) //歌曲时长
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, CoverLoader.getInstance().loadThumbnail(music));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            metaData.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, AppCache.get().getLocalMusicList().size());
        }

        mediaSession.setMetadata(metaData.build());
    }

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            AudioPlayer.get().playPause();
        }

        @Override
        public void onPause() {
            AudioPlayer.get().playPause();
        }

        @Override
        public void onSkipToNext() {
            AudioPlayer.get().next(false);
        }

        @Override
        public void onSkipToPrevious() {
            AudioPlayer.get().prev(false);
        }

        @Override
        public void onStop() {
            AudioPlayer.get().stopPlayer();
        }

        @Override
        public void onSeekTo(long pos) {
            AudioPlayer.get().seekTo((int) pos);
        }
    };
}
