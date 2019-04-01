package com.hamitao.kids.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.MusicListAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.event.PlayVideoEvent;
import com.hamitao.kids.mvp.musiclist.MusicListPresenter;
import com.hamitao.kids.mvp.musiclist.MusicListView;
import com.hamitao.kids.player.AudioPlayer;
import com.hamitao.kids.player.OnPlayerEventListener;
import com.hamitao.kids.util.DialogListUtil;
import com.hamitao.kids.videoplayer.JZExoPlayer;
import com.hamitao.kids.view.MyVideoView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.jzvd.Jzvd;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 音乐清单列表
 */

@Route("music_list")
public class MusicListActivity extends BaseActivity implements MusicListView, BGAOnRVItemClickListener, BGAOnItemChildClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_list_title)
    TextView tvListTitle;
    @BindView(R.id.civ_face)
    CircleImageView civFace;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_music)
    CircleImageView btnMusic;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_put)
    TextView tvPut;
    @BindView(R.id.tv_nfc)
    TextView tvNfc;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.rl_video)
    LinearLayout rl_video;
    @BindView(R.id.videoview)
    MyVideoView videoView;

    //当前正在播放时的视频位置
    private int index = 0;

    //投送工具
    private DialogListUtil dialogListUtil;

    private MusicListPresenter presenter;

    private MusicListAdapter adapter;

    private ContentModel.ResponseDataObjBean.ContentsBean contentsBean;

    //单曲列表;这是存mp3格式的比内容
    private List<ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean> listBeans = new ArrayList<>();

    //是否点赞，收藏
    private boolean islike, isCollect;

    //点赞的ID
    private String likeid = "";

    //收藏ID
    private String favoriteid = "";

    //内容ID
    private String contentid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initView();
    }


    private void initData() {
        contentid = getIntent().getStringExtra("contentid");
        dialogListUtil = new DialogListUtil(this);
        presenter = new MusicListPresenter(this);
        adapter = new MusicListAdapter(recyclerView);
        //请求数据
        presenter.getMusiclist(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, contentid);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
        Jzvd.setMediaInterface(new JZExoPlayer());

        AudioPlayer.get().addOnPlayEventListener(new OnPlayerEventListener() {
            @Override
            public void onChange(ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music) {

            }

            @Override
            public void onPlayerStart() {

            }

            @Override
            public void onPlayerPause() {

            }

            @Override
            public void onPublish(int progress) {

            }

            @Override
            public void onBufferingUpdate(int percent) {

            }

            @Override
            public void onPosition(int position) {
                adapter.setIndex(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(PlayVideoEvent messageEvent) {
        playNextVideo(index, messageEvent.getCurrentScreen());
        adapter.setIndex(index);
        adapter.notifyDataSetChanged();
    }


    private void initView() {
        title.setText("听单详情");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void playVideo(int position) {
        //当前正在播放的视频位置
        this.index = position;
        rl_video.setVisibility(View.VISIBLE);
        Jzvd.releaseAllVideos();
        videoView.setUp(adapter.getData().get(position).getHttpURL(), StringUtil.deleteNumber(adapter.getData().get(position).getMediatitle()), Jzvd.SCREEN_WINDOW_NORMAL);
        videoView.startVideo();

        //加入播放记录
        if (UserUtil.user() != null) {
            presenter.addPlayRecord(UserUtil.user().getCustomerid(), "mediaid",
                    adapter.getData().get(position).getMediaid(), "video",
                    StringUtil.deleteNumber(adapter.getData().get(position).getMediatitle()), contentsBean.getImgtitleurl(), "");
        }
        adapter.setIndex(position);
        adapter.notifyDataSetChanged();
    }

    private void playNextVideo(int index, int currentScreen) {
        if (index + 1 > adapter.getData().size() - 1) {
            videoView.onCompletion();
        } else {
            Jzvd.releaseAllVideos();
            ToastUtil.showShort("准备播放下一个视频");
            this.index = index + 1;
            videoView.setUp(adapter.getData().get(index + 1).getHttpURL(), StringUtil.deleteNumber(adapter.getData().get(index + 1).getMediatitle()), Jzvd.SCREEN_WINDOW_NORMAL);
            videoView.startButton.performClick();
            //加入播放记录
            if (UserUtil.user() != null) {
                presenter.addPlayRecord(UserUtil.user().getCustomerid(), "mediaid",
                        adapter.getData().get(this.index).getMediaid(), "video",
                        StringUtil.deleteNumber(adapter.getData().get(this.index).getMediatitle()), contentsBean.getImgtitleurl(), "");
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    @OnClick({R.id.back, R.id.more, R.id.btn_music, R.id.tv_like, R.id.tv_collect, R.id.tv_put, R.id.tv_nfc, R.id.tv_add, R.id.rl_video})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //分享
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    showShareDialog(this, new OnViewClickListener() {
                        @Override
                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                            EditText et = tDialog.getView().findViewById(R.id.dialog_comment_content);
                            if (view.getId() == R.id.tv_confirm) {
                                //分享到广场

                                if (contentsBean == null) {
                                    return;
                                }

                                presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                        contentsBean.getNameI18List().get(0).getValue(), et.getText().toString(), contentsBean.getImgtitleurl(), "forum_hamitao_official", new String[]{""}, "contentid",
                                        contentsBean.getContentid());
                            }
                            tDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btn_music:
                //跳转到播放器页面
                showPlayer();
                break;
            case R.id.tv_like:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    if (contentsBean == null) {
                        return;
                    }
                    if (islike) {
                        //取消点赞
                        presenter.unlike(contentsBean.getMylike_id(), -1);
                        tvLike.setText(Integer.parseInt(tvLike.getText().toString()) - 1 + "");
                    } else {
                        presenter.like(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                contentsBean.getDescriptionI18List().get(0).getValue(), "contentid", contentsBean.getContentid(), -1);  // -1
                        tvLike.setText(Integer.parseInt(tvLike.getText().toString()) + 1 + "");
                    }
                }
                break;
            case R.id.tv_collect:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    if (isCollect) {
                        //取消收藏N
                        if (!favoriteid.equals("")) {
                            presenter.deleteCollection(UserUtil.user().getCustomerid(), favoriteid);
                        }
                    } else {
                        dialogListUtil.showClipListDialog(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                //这里创建收藏夹
                                if (view.getId() == R.id.dialog_list_more) {

                                    showInputDialog(MusicListActivity.this, "请输入收藏夹名字", new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            if (view.getId() == R.id.tv_confirm) {
                                                String name = ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString();
                                                if (dialogListUtil.isSameClip(name)){
                                                    ToastUtil.showShort("该文件夹已存在");
                                                    return;
                                                }
                                                //判断是否有重复名称
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
                            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                                presenter.addCollection(dialogListUtil.getClipName(position), contentsBean.getDescriptionI18List().get(0).getValue(),
                                        contentsBean.getImgtitleurl(), contentsBean.getContentid(), "contentid", "", UserUtil.user().getCustomerid(), StringUtil.deleteNumber(contentsBean.getNameI18List().get(0).getValue()));
                                dialogListUtil.dismissDialog();
                            }
                        });

                    }
                }
                break;
            case R.id.tv_put:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                        @Override
                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                            if (contentsBean == null) {
                                return;
                            }
                            if (contentsBean.getMediaList().size() <= 0) {
                                ToastUtil.showShort("专辑内容为空，投送失败！");
                            } else {
                                presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(index), "contentid", contentsBean.getContentid(),
                                        StringUtil.deleteNumber(contentsBean.getNameI18List().get(0).getValue()), "", dialogListUtil.getTargetChannel(index), new String[]{"contentid", contentsBean.getContentid()});
                            }
                            dialogListUtil.dismissDialog();
                        }
                    });
                }
                break;
            case R.id.tv_nfc:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    if (contentsBean == null) {
                        return;
                    }
                    if (!BaseApplication.getInstance().isPublicVendor()) {
                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                        return;
                    }
                    Router.build("scan")
                            .with("info", contentsBean.getContentid())
                            .with("infotype", "contentid")
                            .with("title", contentsBean.getNameI18List().get(0).getValue())
                            .with("img", contentsBean.getImgtitleurl())
                            .go(this);
                }
                break;
            case R.id.tv_add:
                if (UserUtil.user() == null) {
                    showLoginDialog();
                } else {
                    if (contentsBean == null) {
                        return;
                    }

                    if (!BaseApplication.getInstance().isPublicVendor()) {
                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                        return;
                    }


                    Router.build("add_course")
                            .with("coursename", contentsBean.getNameI18List().get(0).getValue())
                            .with("info", contentsBean.getContentid())
                            .with("infotype", "contentid")
                            .with("imgurl", contentsBean.getImgtitleurlhttpURL())
                            .go(this);
                }
                break;
            case R.id.rl_video:
                break;
            default:
                break;
        }
    }

    //显示播放器
    public void showPlayer() {
        PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.list_more) {
            showMoreDialog(position);
        }
    }


    /**
     * 显示更多弹窗
     * @param position
     */
    private void showMoreDialog(final int position) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_item_more)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setTag("DialogTest")
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_collect, R.id.tv_put, R.id.tv_share, R.id.tv_nfc, R.id.dialog_list_more, R.id.tv_cancel)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_collect:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    dialogListUtil.showClipListDialog(new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog listDialog) {
                                            switch (view.getId()) {
                                                case R.id.dialog_list_more:

                                                    //打开新建文件夹的弹窗
                                                    showInputDialog(MusicListActivity.this, "请输入收藏夹名称", new OnViewClickListener() {
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
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }, new BGAOnRVItemClickListener() {
                                        @Override
                                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                                            presenter.addCollection(dialogListUtil.getClipName(index), "",
                                                    contentsBean.getImgtitleurl(), adapter.getData().get(position).getMediaid(),
                                                    "mediaid", adapter.getData().get(position).getMediatype(), UserUtil.user().getCustomerid(), StringUtil.deleteNumber(adapter.getData().get(position).getMediatitle()));
                                            dialogListUtil.dismissDialog();
                                        }
                                    });
                                }

                                break;
                            case R.id.tv_put:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                                        @Override
                                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                                            ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean model = adapter.getData().get(position);
                                            presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(index),
                                                    "mediaid", model.getMediaid(), StringUtil.deleteNumber(StringUtil.deleteNumber(model.getMediatitle())), "单曲", dialogListUtil.getTargetChannel(index), new String[]{"mediaid", model.getMediaid()});
                                            dialogListUtil.dismissDialog();
                                        }
                                    });
                                }
                                break;
                            case R.id.tv_share:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    if (contentsBean == null) {
                                        return;
                                    }
                                    showShareDialog(MusicListActivity.this, new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            EditText et = tDialog.getView().findViewById(R.id.dialog_comment_content);
                                            //分享到广场
                                            if (view.getId() == R.id.tv_confirm) {
                                                if (et.getText().equals("")) {
                                                    ToastUtil.showShort("请输入您的心得");
                                                } else {
                                                    presenter.shareToSquare(
                                                            UserUtil.user().getCustomerid(),
                                                            PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                                            StringUtil.deleteNumber(adapter.getData().get(position).getMediatitle()),
                                                            et.getText().toString(),
                                                            contentsBean.getImgtitleurl(),
                                                            Constant.OFFICIAL_FORUM, new String[]{""},
                                                            "mediaid",
                                                            adapter.getData().get(position).getMediaid());
                                                }
                                            }
                                            tDialog.dismiss();
                                        }
                                    });
                                }
                                break;
                            case R.id.tv_nfc:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    if (!BaseApplication.getInstance().isPublicVendor()){
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    Router.build("scan")
                                            .with("info", adapter.getData().get(position).getMediaid())
                                            .with("infotype", "mediaid")
                                            .with("title", adapter.getData().get(position).getMediatitle())
                                            .with("img", contentsBean.getImgtitleurl())
                                            .go(MusicListActivity.this);

                                }
                                break;
                            case R.id.dialog_list_more:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    // 传入内容图片,单曲没有图片
                                    Router.build("add_course")
                                            .with("coursename", StringUtil.deleteNumber(adapter.getData().get(position).getMediatitle()))
                                            .with("info", adapter.getData().get(position).getMediaid())
                                            .with("infotype", "mediaid")
                                            .with("imgurl", adapter.getData().get(position).getHeaderimgurlhttpURL())
                                            .go(MusicListActivity.this);
                                }
                                break;
                            case R.id.tv_cancel:
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                })
                .create().show();    //展示
    }

    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }


    //初始化数据
    @Override
    public void getContent(ContentModel.ResponseDataObjBean.ContentsBean contentsBean) {
        this.contentsBean = contentsBean;
        //如果是视频则隐藏播放按钮
        if (contentsBean.getMediaList().get(0).getMediasubtype().equals("mp4") ||
                contentsBean.getMediaList().get(0).getMediasubtype().equals("avi") ||
                contentsBean.getMediaList().get(0).getMediasubtype().equals("rm")) {
            btnMusic.setVisibility(View.GONE);
        }


        //封面
        Glide.with(this).load(contentsBean.getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(ivCover);
        Glide.with(this).load(contentsBean.getImgauthorurlhttpURL()).error(R.drawable.icon_mother).into(civFace); //头像
        tvListTitle.setText(contentsBean.getNameI18List().get(0).getValue()); // 标题
        tvAuthor.setText(contentsBean.getAuthorList().get(0)); //作者名
        tvLike.setText(contentsBean.getLikeCount() + ""); // 点赞数
        favoriteid = contentsBean.getMyfavouriteid(); //收藏ID

        //点赞
        if (!(contentsBean.getMylike_id() == null || contentsBean.getMylike_id().equals(""))) {
            setLikeStatus(true);
            islike = true;
        }

        //收藏
        if (contentsBean.getFavourited() == null || contentsBean.getFavourited().equals("no")) {
            tvCollect.setText("收藏");
        } else {
            setCollectStatus(true);
            isCollect = true;
        }

        for (int i = 0; i < contentsBean.getMediaList().size(); i++) {
            String mediaType = contentsBean.getMediaList().get(i).getMediasubtype();
            if (mediaType.equals("mp3") ||mediaType.equals("mp4") || mediaType.equals("avi") || mediaType.equals("rm")) {
                listBeans.add(contentsBean.getMediaList().get(i));
            }
        }

        adapter.setData(listBeans);

        tvCount.setText("播放全部(" + adapter.getData().size() + ")");

        for (int i = 0; i < listBeans.size(); i++) {
            if (listBeans.get(i).getMediatitle().equals(AudioPlayer.get().getPlayMusic().getMediatitle())) {
                adapter.setIndex(i);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }


    //设置点赞状态
    private void setLikeStatus(Boolean islike) {
        if (islike) {
            Drawable like = getResources().getDrawable(R.drawable.icon_list_like_p);
            like.setBounds(0, 0, like.getMinimumWidth(), like.getMinimumHeight());
            tvLike.setCompoundDrawables(null, like, null, null);
        } else {
            Drawable unlike = getResources().getDrawable(R.drawable.icon_list_like_n);
            unlike.setBounds(0, 0, unlike.getMinimumWidth(), unlike.getMinimumHeight());
            tvLike.setCompoundDrawables(null, unlike, null, null);
        }
    }

    //判断是否收藏
    private void setCollectStatus(Boolean isCollect) {
        if (isCollect) {
            Drawable collect = getResources().getDrawable(R.drawable.icon_list_collect_p);
            collect.setBounds(0, 0, collect.getMinimumWidth(), collect.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, collect, null, null);
        } else {
            Drawable uncollect = getResources().getDrawable(R.drawable.icon_list_collect_n);
            uncollect.setBounds(0, 0, uncollect.getMinimumWidth(), uncollect.getMinimumHeight());
            tvCollect.setCompoundDrawables(null, uncollect, null, null);
        }
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, final int position) {
        if (adapter.getData().get(position).getMediasubtype().equals("mp4") || adapter.getData().get(position).getMediasubtype().equals("avi")) {
            playVideo(position);
        } else {
            //不管点击哪一项，都将该歌单作为播放器的歌单列表
            if (listBeans != null && listBeans.size() > 0) {
                for (int i = 0; i < listBeans.size(); i++) {
                    listBeans.get(i).setMediatitle(StringUtil.deleteNumber(listBeans.get(i).getMediatitle()));
                    listBeans.get(i).setHeaderimgurl(contentsBean.getImgtitleurl());
                    listBeans.get(i).setMediacontentype(Constant.Media);
                }
                AudioPlayer.get().setMusicList(listBeans);
            }
            AudioPlayer.get().play(position);
            showPlayer();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {

        switch ((String) status) {
            case "like":
                islike = true;
                setLikeStatus(islike);
                likeid = (String) data;
                contentsBean.setMylike_id(likeid);
                break;
            case "unlike":
                islike = false;
                setLikeStatus(islike);
                break;
            case "addcollect":
                isCollect = true;
                setCollectStatus(isCollect);
                favoriteid = (String) data;
                tvCollect.setText("已收藏");
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                break;
            case "addcollectmedia":
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                break;
            case "deletecollect":
                ToastUtil.showShort("已取消收藏");
                isCollect = false;
                setCollectStatus(isCollect);
                tvCollect.setText("收藏");
                break;
            case "createclip":
                //新建收藏夹
                ToastUtil.showShort("创建成功");
                dialogListUtil.notyfyClipList((String) data);
                break;
            default:
                break;
        }
    }
}


