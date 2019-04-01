package com.hamitao.kids.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean;
import com.hamitao.kids.mvp.player.PlayerPresenter;
import com.hamitao.kids.mvp.player.PlayerView;
import com.hamitao.kids.player.AudioPlayer;
import com.hamitao.kids.player.OnPlayerEventListener;
import com.hamitao.kids.player.PlayModeEnum;
import com.hamitao.kids.util.DialogListUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import me.wcy.lrcview.LrcView;

/**
 * Created by linjianwen on 2018/3/6.
 * <p>
 * 播放界面
 */


public class PlayFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener
        , OnPlayerEventListener, LrcView.OnPlayClickListener, PlayerView {
    private static PlayFragment instance = null;

    @SuppressLint("ValidFragment")
    private PlayFragment() {
    }

    public static PlayFragment getInstance() {
        if (instance == null) {
            //防止多个线程同时创建这个对象
            synchronized (PlayFragment.class) {
                //线程进来时，判断对象是否为空，保证对象的单一性。
                if (instance == null) {
                    instance = new PlayFragment();
                }
            }
        }
        return instance;
    }

    long mCurrentPlayTime;


    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_lrc)
    TextView tvLrc;  //歌词

    @BindView(R.id.lrcView)
    LrcView lrcView; //歌词

    @BindView(R.id.iv_mode)
    ImageView ivMode; // 播放模式

    @BindView(R.id.iv_collect)
    ImageView ivCollect;  //收藏

    @BindView(R.id.tv_startTime)
    TextView tvStartTime; //开始事时间

    @BindView(R.id.tv_endTime)
    TextView tvEndTime; //结束时间

    @BindView(R.id.seekbar)
    SeekBar mSeekbar; //进度条

    @BindView(R.id.iv_play)
    ImageView ivPlay; //播放

    @BindView(R.id.rl_bg)
    RelativeLayout rl_bg; //旋转动画背景

    @BindView(R.id.iv_bg_lrc)
    ImageView bg_lrc; //歌词背景

    Unbinder unbinder;

    private View view;

    private BaseActivity baseActivity;

    private ObjectAnimator circle_anim;  //旋转动画

    private int mLastProgress;//进度条末尾

    private int mLastBuffProgress = 0;//缓冲进度

    private int select = 1;

    private boolean isDraggingProgress;//是否拖动进度条

    private DialogListUtil dialogListUtil; //列表弹窗攻工具

    private PlayerPresenter presenter;

    private ResponseDataObjBean.ContentsBean.MediaListBean music; //获取到的音乐实体类

    private String favouritedid = "";

    //该单曲是否已被加入收藏夹
    private boolean isCollect;

    //歌曲是否准备就绪
    private boolean isPrepared = false;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LogUtil.d("player", "onCreateDialog");
        view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_player, null);
        unbinder = ButterKnife.bind(this, view);
        Dialog dialog = new Dialog(getActivity(), R.style.style_dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //可设置dialog的位置
        window.setGravity(Gravity.BOTTOM);
        //消除边距
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置宽度充满屏幕
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        return dialog;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        dialogListUtil = new DialogListUtil(baseActivity);
        presenter = new PlayerPresenter(this);
        init();
    }


    private void init() {
        //初始化播放模式
        initPlayMode();
        //初始化动画
        initanimation();
        //添加菜单按钮的监听器
        AudioPlayer.get().addOnPlayEventListener(this);
        //进度条监听器
        mSeekbar.setOnSeekBarChangeListener(this);
        if (AudioPlayer.get().getMusicList().size() <= 0) {
            title.setText("播放列表暂无歌曲");
        }
//
//        AudioPlayer.get().setCallback(new AudioPlayer.PositionCallback() {
//            @Override
//            public void onPointion(int position) {
//                //当前听单正在播放的位置
//                dialogListUtil.setIndex(position);
//                if (dialogListUtil.getMusicAdapter()!=null)
//                dialogListUtil.getMusicAdapter().notifyDataSetChanged();
//            }
//        });

        //动画回调
        AudioPlayer.get().setListener(new AudioPlayer.AnimationListener() {
            @Override
            public void playStatus(String status) {
                switch (status) {
                    case "start":
                        //开始播放
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                circle_anim.start();
                            }
                        }, 1000);
                        break;
                    case "pause":
                        //暂停播放,暂停动画
                        mCurrentPlayTime = circle_anim.getCurrentPlayTime();
                        circle_anim.pause();
                        break;
                    case "resume":
                        //继续播放
                        circle_anim.start();
                        circle_anim.setCurrentPlayTime(mCurrentPlayTime);
                        break;
                    case "stop":
                        //停止播放器，复位动画
                        circle_anim.cancel();
                        circle_anim.setCurrentPlayTime(0);
                        break;
                    default:
                        break;
                }
            }
        });


        if (isPrepared) {
            initView(AudioPlayer.get().getPlayMusic());
        }
    }

    private void initView(ResponseDataObjBean.ContentsBean.MediaListBean music) {
        if (music == null) {
            return;
        }
        if (music.getFavourited() != null && music.getMyfavouriteid() != null && music.getFavourited().equals("yes")) {
            isCollect = true;
            favouritedid = music.getMyfavouriteid() != null && !music.equals("") ? music.getMyfavouriteid() : "";
        } else {
            isCollect = false;
        }
        ivCollect.setImageResource(isCollect ? R.drawable.icon_collection_p : R.drawable.icon_collection_n);
        //切换标题
        title.setText(StringUtil.deleteNumber(music.getMediatitle()));
        mSeekbar.setSecondaryProgress(0);
        tvStartTime.setText("00:00");
        tvEndTime.setText(formatTime(AudioPlayer.get().getMediaPlayer().getDuration()));
        mSeekbar.setMax(AudioPlayer.get().getMediaPlayer().getDuration());
        mSeekbar.setProgress(AudioPlayer.get().getMediaPlayer().getCurrentPosition());
    }


    /**
     * 切歌
     */
    private void onChangeImpl(ResponseDataObjBean.ContentsBean.MediaListBean music) {
        isPrepared = true;
        initView(music);
        ivPlay.setSelected(AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing() ? true : false);
        circle_anim.cancel();
        circle_anim.setCurrentPlayTime(0);

    }


    /**
     * 初始化播放模式，从本地获取。// 0 为循环， 1 为单曲
     */
    private void initPlayMode() {
        int mode = PropertiesUtil.getInstance().getInt("play_mode", 0);
        ivMode.setImageResource(mode == 0 ? R.drawable.icon_mode_cycle : R.drawable.icon_mode_single);
    }

    /**
     * 初始化旋转动画
     */
    private void initanimation() {
        //添加旋转动画，旋转中心默认为控件中点
        circle_anim = ObjectAnimator.ofFloat(bg_lrc, "rotation", 0f, 360f);
        //设置动画时间
        circle_anim.setDuration(10000);
        //动画时间线性渐变
        circle_anim.setInterpolator(new LinearInterpolator());
        circle_anim.setRepeatCount(ObjectAnimator.INFINITE);
        circle_anim.setRepeatMode(ObjectAnimator.RESTART);
        //播放暂停按钮状态
        ivPlay.setSelected(AudioPlayer.get().isPlaying() || AudioPlayer.get().isPreparing() ? true : false);
    }

    @Override
    public void onResume() {
        super.onResume();
        circle_anim.setCurrentPlayTime(mCurrentPlayTime);
        if (AudioPlayer.get().isPlaying()) {
            circle_anim.start();
        }

    }

    @Override
    public void onPause() {
        //当播放界面不可见的时候记录当前的动画的播放事件
        if (AudioPlayer.get().isPlaying()) {
            mCurrentPlayTime = circle_anim.getCurrentPlayTime();
        }
        super.onPause();
    }

    private void play() {
        AudioPlayer.get().playPause();
    }

    private void next() {
        AudioPlayer.get().next(true);
    }

    private void prev() {
        AudioPlayer.get().prev(true);
    }

    private void loadLrc(String path) {
        File file = new File(path);
        lrcView.loadLrc(file);
    }

    private void setLrc(ResponseDataObjBean.ContentsBean.MediaListBean music) {
    }

    private void setLrcLabel(String label) {
        lrcView.setLabel(label);
    }

    private String formatTime(int time) {

        String str = DateUtil.formatTime("mm:ss", time);

        return Integer.parseInt(str.split(":")[0]) > 60 ? "00:00" : str;
    }

    /**
     * 切换播放模式
     */
    private void switchPlayMode() {
        PlayModeEnum mode = PlayModeEnum.valueOf(PropertiesUtil.getInstance().getInt(PropertiesUtil.SpKey.Play_Mode, 0));
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SINGLE;
                ToastUtil.showShort("单曲循环");
                ivMode.setImageResource(R.drawable.icon_mode_single);
                break;
            case SINGLE:
                mode = PlayModeEnum.LOOP;
                ToastUtil.showShort("列表播放");
                ivMode.setImageResource(R.drawable.icon_mode_cycle);
                break;
            default:
                break;
        }
        PropertiesUtil.getInstance().setInt(PropertiesUtil.SpKey.Play_Mode, mode.value()); //将播放模式存入本地
        initPlayMode();
    }


    /**
     * 显示定时弹窗
     */
    private void showTimerDialog() {
        new TDialog.Builder(getFragmentManager())
                .setLayoutRes(R.layout.dialog_item_timer)
                .setScreenWidthAspect(getActivity(), 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4, R.id.rb5, R.id.rb6, R.id.tv_cancel, R.id.tv_confirm)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        switch (AudioPlayer.get().getIndex()) {
                            case 1:
                                ((RadioButton) viewHolder.getView(R.id.rb1)).setChecked(true);
                                break;
                            case 2:
                                ((RadioButton) viewHolder.getView(R.id.rb2)).setChecked(true);
                                break;
                            case 3:
                                ((RadioButton) viewHolder.getView(R.id.rb3)).setChecked(true);
                                break;
                            case 4:
                                ((RadioButton) viewHolder.getView(R.id.rb4)).setChecked(true);
                                break;
                            case 5:
                                ((RadioButton) viewHolder.getView(R.id.rb5)).setChecked(true);
                                break;
                            case 6:
                                ((RadioButton) viewHolder.getView(R.id.rb6)).setChecked(true);
                                break;
                            default:
                                break;
                        }
                    }
                }).setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                switch (view.getId()) {
                    case R.id.rb1:
                        select = 1;
                        ((RadioGroup) viewHolder.getView(R.id.rg2)).clearCheck();
                        break;
                    case R.id.rb2:
                        select = 2;
                        ((RadioGroup) viewHolder.getView(R.id.rg2)).clearCheck();
                        break;
                    case R.id.rb3:
                        select = 3;
                        ((RadioGroup) viewHolder.getView(R.id.rg2)).clearCheck();
                        break;
                    case R.id.rb4:
                        select = 4;
                        ((RadioGroup) viewHolder.getView(R.id.rg1)).clearCheck();
                        break;
                    case R.id.rb5:
                        select = 5;
                        ((RadioGroup) viewHolder.getView(R.id.rg1)).clearCheck();
                        break;
                    case R.id.rb6:
                        select = 6;
                        ((RadioGroup) viewHolder.getView(R.id.rg1)).clearCheck();
                        break;
                    case R.id.tv_cancel:
                        tDialog.dismiss();
                        break;
                    case R.id.tv_confirm:
                        if (!selectTime(select).equals("")) {
                            if (AudioPlayer.get().isPlaying()) {
                                AudioPlayer.get().intSetIndex(select);
                                AudioPlayer.get().starTimer(selectTime(select));
                            } else {
                                ToastUtil.showShort("播放器已暂停");
                            }
                        }
                        tDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }).create().show();
    }

    /**
     * 选择定时关闭事件
     *
     * @param select
     * @return
     */
    private String selectTime(int select) {
        switch (select) {
            case 1:
                return "无";
            case 2:
                return "15分钟";
            case 3:
                return "30分钟";
            case 4:
                return "40分钟";
            case 5:
                return "50分钟";
            case 6:
                return "60分钟";
            default:
                return "";
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.back, R.id.tv_lrc, R.id.iv_mode, R.id.iv_collect, R.id.iv_put, R.id.tv_timer, R.id.iv_share, R.id.iv_pre, R.id.iv_play, R.id.iv_next, R.id.iv_list})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back:
                dismiss();
                break;
            case R.id.rl_bg:
            case R.id.tv_lrc:
                if (!lrcView.isShown()) {
                    lrcView.setVisibility(View.VISIBLE);
                    rl_bg.setVisibility(View.GONE);
                } else {
                    lrcView.setVisibility(View.GONE);
                    rl_bg.setVisibility(View.VISIBLE
                    );
                }
                break;
            case R.id.iv_mode:
                switchPlayMode();
                break;
            case R.id.iv_collect:
                if (UserUtil.user() != null) {
                    if (AudioPlayer.get().getPlayMusic() == null) {
                        ToastUtil.showShort("歌单为空");
                        return;
                    }
                    if (isCollect) {
                        //取消收藏
                        presenter.deleteCollection(UserUtil.user().getCustomerid(), favouritedid);
                    } else {
                        dialogListUtil.showClipListDialog(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                if (view.getId() == R.id.dialog_list_more) {
                                    //打开新建文件夹的弹窗
                                    baseActivity.showInputDialog(getActivity(), "请输入收藏夹名称", new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            //这里创建收藏夹
                                            if (view.getId() == R.id.tv_confirm) {

                                                String name = ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString();
                                                if (dialogListUtil.isSameClip(name)){
                                                    ToastUtil.showShort("该文件夹已存在");
                                                    return;
                                                }

                                                if (name.equals("")) {
                                                    ToastUtil.showShort("请输入收藏夹名字");
                                                    return;
                                                }
                                                presenter.createClip(UserUtil.user().getCustomerid(), ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString());
                                            }
                                            tDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }, new BGAOnRVItemClickListener() {
                            @Override
                            public void onRVItemClick(ViewGroup parent, View itemView, int index) {

                                String infotype = getInfoType(AudioPlayer.get().getPlayMusic().getMediasubtype() == null ? Constant.Media : AudioPlayer.get().getPlayMusic().getMediasubtype());

                                //这里进行内容收藏
                                presenter.addCollection(dialogListUtil.getClipName(index), "", AudioPlayer.get().getPlayMusic().getHeaderimgurl(), AudioPlayer.get().getPlayMusic().getMediaid(), infotype,
                                        "audio", UserUtil.user().getCustomerid(), AudioPlayer.get().getPlayMusic().getMediatitle());
                                dialogListUtil.dismissDialog();

                            }
                        });
                    }
                } else {
                    baseActivity.showLoginDialog();
                }

                break;
            case R.id.iv_put:
                if (UserUtil.user() != null) {
                    //投送
                    if (AudioPlayer.get().getPlayMusic() == null) {
                        ToastUtil.showShort("歌单为空");
                        return;
                    }
                    dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                        @Override
                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                            String infotype = getInfoType(AudioPlayer.get().getPlayMusic().getMediasubtype() == null ? Constant.Media : AudioPlayer.get().getPlayMusic().getMediasubtype());
                            presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(index), infotype,
                                    AudioPlayer.get().getPlayMusic().getMediaid(), AudioPlayer.get().getPlayMusic().getMediatitle(),
                                    "", dialogListUtil.getTargetChannel(index), new String[]{infotype, AudioPlayer.get().getPlayMusic().getMediaid()});
                            dialogListUtil.dismissDialog();
                        }
                    });
                } else {
                    baseActivity.showLoginDialog();
                }
                break;
            case R.id.tv_timer:
                //定时
                if (AudioPlayer.get().getPlayMusic() == null) {
                    ToastUtil.showShort("歌单为空");
                    return;
                }
                showTimerDialog();
                break;
            case R.id.iv_share:
                //分享
                if (UserUtil.user() != null) {
                    if (AudioPlayer.get().getPlayMusic() == null) {
                        ToastUtil.showShort("歌单为空");
                        return;
                    }
                    baseActivity.showShareDialog(getActivity(), new OnViewClickListener() {
                        @Override
                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                            if (view.getId() == R.id.tv_confirm) {
                                if (((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString().trim().equals("")) {
                                    ToastUtil.showShort("请输入您的心得");
                                } else {
                                    String infotype = getInfoType(AudioPlayer.get().getPlayMusic().getMediasubtype());
                                    presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                            StringUtil.deleteNumber(AudioPlayer.get().getPlayMusic().getMediatitle()), ((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString(),
                                            AudioPlayer.get().getPlayMusic().getHeaderimgurl(), Constant.OFFICIAL_FORUM, new String[]{}, infotype, AudioPlayer.get().getPlayMusic().getMediaid());
                                }
                            }
                            tDialog.dismiss();
                        }
                    });
                } else {
                    baseActivity.showLoginDialog();
                }
                break;
            case R.id.iv_pre:
                prev();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
            case R.id.iv_list:
                //显示歌单列表
                dialogListUtil.showMusicListDialog(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.dialog_list_more) {
                            //清空播放列表
                            baseActivity.showConfirmDialog(baseActivity, "确定要清空列表吗?", new OnViewClickListener() {
                                @Override
                                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                    if (view.getId() == R.id.tv_confirm) {
                                        dialogListUtil.deleteAll();
                                    }
                                    tDialog.dismiss();
                                }
                            });
                        }
                    }
                }, new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                        AudioPlayer.get().play(position);
                        dialogListUtil.dismissDialog();
                    }
                }, new BGAOnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(ViewGroup parent, View childView, int position) {
                        if (childView.getId() == R.id.list_more) {
                            dialogListUtil.deleteMusic(position);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }


    /**
     * 获取歌曲的类型
     *
     * @param mediaSubType
     * @return
     */
    private String getInfoType(String mediaSubType) {
        if (mediaSubType == null) {
            return Constant.Media;
        }
        String info = "";
        switch (mediaSubType) {
            case "mp3":
                info = Constant.Media;
                break;
            case "record":
                info = Constant.Record;
                break;
            default:
                break;
        }
        return info;
    }

    @Override
    public void onDestroy() {
        AudioPlayer.get().removeOnPlayEventListener(this);
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
            tvStartTime.setText(formatTime(progress + 1));
            mLastProgress = progress;
        }
    }


    /**
     * 更新缓存条
     *
     * @param percent
     */
    @Override
    public void onBufferingUpdate(int percent) {
//        if (percent != 0) {
//            mSeekbar.setSecondaryProgress(mSeekbar.getMax() * 100 / percent);
//            mLastBuffProgress = mSeekbar.getMax() * 100 / percent;
//        }
    }

    @Override
    public void onPosition(int position) {
        //当前听单正在播放的位置
        dialogListUtil.setIndex(position);
        if (dialogListUtil.getMusicAdapter()!=null)
            dialogListUtil.getMusicAdapter().notifyDataSetChanged();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isDraggingProgress = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isDraggingProgress = false;
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing()) {
            int progress = seekBar.getProgress();
            AudioPlayer.get().seekTo(progress);
            if (lrcView.hasLrc()) {
                lrcView.updateTime(progress);
            }
        } else {
            seekBar.setProgress(0);
        }
    }

    @Override
    public void onChange(ResponseDataObjBean.ContentsBean.MediaListBean music) {
        onChangeImpl(music);
    }

    @Override
    public void onPlayerStart() {
        ivPlay.setSelected(true);
    }

    @Override
    public void onPlayerPause() {
        ivPlay.setSelected(false);
    }

    @Override
    public void onPublish(int progress) {
        if (!isDraggingProgress) {
            mSeekbar.setProgress(progress);
        }
        if (lrcView.hasLrc()) {
            lrcView.updateTime(progress);
        }
    }


    @Override
    public boolean onPlayClick(long time) {
        if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing()) {
            AudioPlayer.get().seekTo((int) time);
            if (AudioPlayer.get().isPausing()) {
                AudioPlayer.get().playPause();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }


    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "createclip":
                ToastUtil.showShort("创建成功");
                dialogListUtil.addClip((String) data);
                break;
            case "addcollectmedia":
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                isCollect = true;
                ivCollect.setImageResource(R.drawable.icon_collection_p);
                favouritedid = (String) data;
                AudioPlayer.get().getPlayMusic().setMyfavouriteid(favouritedid);
                AudioPlayer.get().getPlayMusic().setFavourited("yes");
                break;
            case "deletecollect":
                ToastUtil.showShort("已取消收藏");
                isCollect = false;
                ivCollect.setImageResource(R.drawable.icon_collection_n);
                AudioPlayer.get().getPlayMusic().setMyfavouriteid("");
                AudioPlayer.get().getPlayMusic().setFavourited("no");
                break;
            default:
                break;
        }
    }


}
