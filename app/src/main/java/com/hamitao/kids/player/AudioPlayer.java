package com.hamitao.kids.player;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.receiver.NoisyAudioStreamReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linjianwen on 2018/3/1.
 * 音频播放器：单例
 */

public class AudioPlayer {

    private static final int STATE_IDLE = 0;  //空闲
    private static final int STATE_PREPARING = 1; //准备
    private static final int STATE_PLAYING = 2;  //正在播放
    private static final int STATE_PAUSE = 3;  //暂停
    private static final long TIME_UPDATE = 100L; //更新时间
    private int index = 1; //定时的位置 1-无定时   2-15分钟  3-30分钟  4-40分钟 5-50分钟 6-60分钟
    private Runnable runnable;

    private Context context;
    private AudioFocusManager audioFocusManager;  //音频焦点管理器
    private MediaPlayer mediaPlayer; //播放器
    private Handler handler;
    private NoisyAudioStreamReceiver noisyReceiver;  //拔出耳机的接收器
    private IntentFilter noisyFilter; //意图过滤器
    private List<MediaListBean> musicList = new ArrayList<>();  //音乐列表
    private final List<OnPlayerEventListener> listeners = new ArrayList<>();  //存放播放监听器
    private int state = STATE_IDLE; //默认为空闲状态


    public AnimationListener animationListener;

    public void setListener(AnimationListener listener) {
        this.animationListener = listener;
    }

    public interface AnimationListener {
        void playStatus(String status);
    }


    public PositionCallback positionCallback;

    public interface PositionCallback {
        void onPointion(int position);
    }

    public void setCallback(PositionCallback callback) {
        this.positionCallback = callback;
    }


    private static class SingletonHolder {
        private static AudioPlayer instance = new AudioPlayer();
    }

    public static AudioPlayer get() {
        return SingletonHolder.instance;
    }

    private AudioPlayer() {
    }

    public AudioFocusManager getAudioFocusManager() {
        return audioFocusManager;
    }

    public int getIndex() {
        return index;
    }

    public void intSetIndex(int index) {
        this.index = index;
    }

    //初始化播放器
    public void init(Context context) {
        this.context = BaseApplication.getInstance();
        audioFocusManager = new AudioFocusManager(context);
        mediaPlayer = new MediaPlayer();
        handler = new Handler(Looper.getMainLooper());
        noisyReceiver = new NoisyAudioStreamReceiver();
        noisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        //缓存更新监听器
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onBufferingUpdate(percent);
                }
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (musicList.size() > 1) {
                    next(false);
                } else if (musicList.size() == 1) {
                    animationListener.playStatus("stop");
                    pausePlayer();
                    if (!"record".equals(musicList.get(0).getMediasubtype()))
                        //直接重新播放
                        play(0);
//                    if (PlayModeEnum.valueOf(PropertiesUtil.getInstance().getInt(PropertiesUtil.SpKey.Play_Mode, 0))==SINGLE){
//                        //如果是单曲循环则继续播放第一首当前歌曲
//                        play(0);
//                    }else {
//                        pausePlayer();
//                        animationListener.playStatus("stop");
//                    }
                }
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                LogUtil.d("AudioPlayer", "Error" + i);
                return true;
            }
        });

    }

    //设置音乐播放列表
    public void setMusicList(List<MediaListBean> list) {
        musicList = list;
    }

    //添加播放监听器
    public void addOnPlayEventListener(OnPlayerEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    //移除播放监听器
    public void removeOnPlayEventListener(OnPlayerEventListener listener) {
        listeners.remove(listener);
    }


    //开始几时：
    public void starTimer(final String time) {
        long duration = 0;
        switch (time) {
            case "15分钟":
                duration = 900000;
//                duration = 5000;
                break;
            case "30分钟":
                duration = 1800000;
//                duration = 10000;
                break;
            case "40分钟":
                duration = 2400000;
//                duration = 15000;
                break;
            case "50分钟":
                duration = 3000000;
//                duration = 20000;
                break;
            case "60分钟":
                duration = 3600000;
//                duration = 25000;
                break;
            default:
                duration = 0;
                break;
        }

        if (duration != 0) {
            ToastUtil.showShort(time + "后停止播放");

            //清空之前的任务
            if (runnable != null) {
                handler.removeCallbacks(runnable);
                runnable = null;
            }
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (isPlaying()) {
                        ToastUtil.showShort("暂停播放");
                        //关闭播放器
                        pausePlayer();
                    }
                    //index = 1 的时候定时取消
                    index = 1;
                }
            };
            handler.postDelayed(runnable, duration);
        }
    }

    //添加到本地歌单后播放
    public void addAndPlay(MediaListBean music) {
        int position = musicList.indexOf(music);
        if (position < 0) {
            musicList.add(music);
            position = musicList.size() - 1;
        }
        play(position);
    }

    /**
     * 直接播放音乐实体
     *
     * @param music
     */
    public void play(MediaListBean music) {
        musicList.clear();
        musicList.add(music);
        int position = musicList.indexOf(music);
        play(position);
    }


    //播放后添加到播放记录
    private void addPlayRecord(String ownid, String infotype, String info, String auxinfo, String title, String headerimgurl, String description) {
        NetworkRequest.addPlayRecordRequest(ownid, infotype, info, auxinfo, title, headerimgurl, description, ownid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                Log.d("AudipPlayer", "加入播放记录");
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                Log.d("AudioPlayer", "添加播记录失败");
            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }

    //播放
    public void play(int position) {
        if (musicList.isEmpty()) {
            return;
        }
        if (position < 0) {
            position = musicList.size() - 1;
        } else if (position >= musicList.size()) {
            position = 0;
        }
        setPlayPosition(position);
        final MediaListBean music = getPlayMusic();
        //切歌
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getHttpURL());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    if (positionCallback != null)
                        positionCallback.onPointion(getPlayPosition());

                    for (OnPlayerEventListener listener : listeners) {
                        listener.onPosition(getPlayPosition());
                    }


                    state = STATE_PREPARING;
                    AudioPlayer.get().startPlayer();
//                    //开启播放服务
                    Notifier.get().showPlay(music);
                    //更新音乐
                    MediaSessionManager.get().updateMetaData(music);
                    MediaSessionManager.get().updatePlaybackState();
                    //准备完成后，回调
                    for (OnPlayerEventListener listener : listeners) {
                        listener.onChange(music);
                    }

                    animationListener.playStatus("start");


                    if (UserUtil.user() != null) {
                        //当音乐播放的时候，将该条记录添加到播放记录
                        addPlayRecord(UserUtil.user().getCustomerid(),
                                //判断是否为录音
                                music.getMediasubtype() != null && music.getMediasubtype().equals("record") ? Constant.Record : Constant.Media,
                                music.getMediaid(), "audio",
                                StringUtil.deleteNumber(music.getMediatitle()), music.getHeaderimgurl(), "");
                    }
                }
            });

        } catch (Exception e) {
            ToastUtil.showShort("当前歌曲无法播放");
            e.printStackTrace();
        }
    }


    /**
     * 从播放列表中移除歌曲
     *
     * @param position
     */
    public void delete(int position) {
        int playPosition = getPlayPosition();
        MediaListBean music = musicList.remove(position);
        if (playPosition > position) {
            setPlayPosition(playPosition - 1);
        } else if (playPosition == position) {
            if (isPlaying() || isPreparing()) {
                setPlayPosition(playPosition - 1);
                next(false);
            } else {
                stopPlayer();
                for (OnPlayerEventListener listener : listeners) {
                    listener.onChange(getPlayMusic());
                }
            }
        }
    }

    public void deleteAll() {
        musicList.clear();
    }

    //暂停
    public void playPause() {
        if (isPreparing()) {
            stopPlayer();
            animationListener.playStatus("stop");
        } else if (isPlaying()) {
            pausePlayer();
            animationListener.playStatus("pause");
        } else if (isPausing()) {
            startPlayer();
            animationListener.playStatus("resume");
        } else {
            play(getPlayPosition());
        }
    }

    //开始播放器
    public void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }
        if (audioFocusManager.requestAudioFocus()) {
            //开启播放器
            mediaPlayer.start();
            //播放器状态:正在播放
            state = STATE_PLAYING;
            handler.post(mPublishRunnable);
            //通知栏获取正在播放的歌曲
            Notifier.get().showPlay(getPlayMusic());
            //更新播放状态
            MediaSessionManager.get().updatePlaybackState();
            //注册广播
            context.registerReceiver(noisyReceiver, noisyFilter);

            for (OnPlayerEventListener listener : listeners) {
                listener.onPlayerStart();
            }
            try {

            } catch (Exception e) {
                ToastUtil.showShort(e.toString());
            }

        }
    }

    //暂停播放器
    public void pausePlayer() {
        pausePlayer(true);

    }

    /**
     * 暂停播放器
     *
     * @param abandonAudioFocus 是否释放音频焦点
     */
    public void pausePlayer(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }
        mediaPlayer.pause();
        state = STATE_PAUSE;
        handler.removeCallbacks(mPublishRunnable);
        Notifier.get().showPause(getPlayMusic());
        MediaSessionManager.get().updatePlaybackState();
        context.unregisterReceiver(noisyReceiver);
        if (abandonAudioFocus) {
            audioFocusManager.abandonAudioFocus();
        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    //停止播放器
    public void stopPlayer() {
        if (isIdle()) {
            return;
        }
        pausePlayer();
        mediaPlayer.reset();
        state = STATE_IDLE;
    }


    /**
     * 播放下一首,isClick:是否手动点击播放下一首
     *
     * @param isclick
     */
    public void next(boolean isclick) {
        if (musicList.isEmpty()) {
            return;
        }
        PlayModeEnum mode = PlayModeEnum.valueOf(PropertiesUtil.getInstance().getInt(PropertiesUtil.SpKey.Play_Mode, 0));
        switch (mode) {
//            case SHUFFLE:
//                随机播放
//                play(new Random().nextInt(musicList.size()));
//                break;
            case SINGLE:
                //单曲
                play(isclick ? getPlayPosition() + 1 : getPlayPosition());
                break;
            case LOOP:
                //列表播放
            default:
                play(getPlayPosition() + 1);
                break;
        }
    }

    //播放上一首
    public void prev(boolean isclick) {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(PropertiesUtil.getInstance().getInt(PropertiesUtil.SpKey.Play_Mode, 0));
        switch (mode) {
//            case SHUFFLE:
//                play(new Random().nextInt(musicList.size()));
//                break;
            case SINGLE:
                play(isclick ? getPlayPosition() - 1 : getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() - 1);
                break;
        }
    }

    //进度条跳转到指定位置
    public void seekTo(int msec) {
        if (isPlaying() || isPausing()) {
            mediaPlayer.seekTo(msec);
            MediaSessionManager.get().updatePlaybackState();
            for (OnPlayerEventListener listener : listeners) {
                listener.onPublish(msec);
            }
        }
    }


    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onPublish(mediaPlayer.getCurrentPosition());
                }
            }
            handler.postDelayed(this, TIME_UPDATE);
        }
    };

    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    //获取音频位置
    public long getAudioPosition() {
        if (isPlaying() || isPausing()) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }


    //获取播放的音频
    public MediaListBean getPlayMusic() {
        if (musicList.isEmpty()) {
            return null;
        }
        return musicList.get(getPlayPosition());
    }

    //获取媒体播放器
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public List<MediaListBean> getMusicList() {
        return musicList;
    }

    public boolean isPlaying() {
        return state == STATE_PLAYING;
    }

    public boolean isPausing() {
        return state == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return state == STATE_PREPARING;
    }

    public boolean isIdle() {
        return state == STATE_IDLE;
    }

    //获取歌曲得播放位置
    public int getPlayPosition() {
        int position = PropertiesUtil.getInstance().getInt(PropertiesUtil.SpKey.Play_Positon, 0);
        if (position < 0 || position >= musicList.size()) {
            position = 0;
            PropertiesUtil.getInstance().setInt(PropertiesUtil.SpKey.Play_Positon, position);
        }
        return position;
    }

    //设置歌曲得播放位置，存入本地
    private void setPlayPosition(int position) {
        PropertiesUtil.getInstance().setInt(PropertiesUtil.SpKey.Play_Positon, position);
    }


}
