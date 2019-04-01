package com.hamitao.kids.activity;

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

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.RecentPlayAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecentPlayModel;
import com.hamitao.kids.model.RecentPlayModel.ResponseDataObjBean.PlayListBean;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.recentplay.RecentPlayPresenter;
import com.hamitao.kids.mvp.recentplay.RecentPlayView;
import com.hamitao.kids.player.AudioPlayer;
import com.hamitao.kids.util.DialogListUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * 最近播放
 */

@Route("recent_play")
public class RecentPlayActivity extends BaseActivity implements RecentPlayView, BGAOnRVItemClickListener, BGAOnItemChildClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    TextView more;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.ll_none)
    LinearLayout llNone;

    @BindView(R.id.tv_none)
    TextView tvNone;

    @BindView(R.id.iv_none)
    ImageView ivNone;

    private RecentPlayAdapter adapter;

    private RecentPlayPresenter presenter;

    private DialogListUtil dialogListUtil;

    private String pages;//数据请求页
    private int totalData;//总数据条数
    private int lastData = 0;//首次加载18条
    private int max = 20; //每次加载max条数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_play);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    private void initData() {
        dialogListUtil = new DialogListUtil(this);
        presenter = new RecentPlayPresenter(this);
        if (UserUtil.user() != null) {
            startRefreshing();
        }
        adapter = new RecentPlayAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    private void initView() {
        title.setText("最近播放");
        more.setText("清空");
        ivNone.setImageResource(R.drawable.bg_recent_play);
        tvNone.setText("最近还没有播放任何内容呢！");
        //设置下拉刷新监听
        refreshLayout.setDelegate(this);
        //设置下拉样式
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(this));
        refreshLayout.setIsShowLoadingMoreView(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    /**
     * 开始刷新
     */
    public void startRefreshing() {

        if (refreshLayout != null) {

            refreshLayout.setVisibility(View.VISIBLE);

            pages = "0~" + max;

            getList(pages, Constant.REQUEST_REFRESH);
        }
    }

    /**
     * 列表数据请求
     *
     * @param requestType 下拉刷新/加载更多
     */
    private void getList(String pages, int requestType) {

        presenter.getListRequest(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, pages, requestType);
    }

    /**
     * 刷新/加载成功
     */
    public void completeRequest() {
        BGARefreshUtil.completeRequest(refreshLayout);
    }


    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        int d = (totalData - 1) - lastData;

        if (d < max) {
            pages = (lastData) + "~" + (((lastData) + d));
            getList(pages, Constant.REQUEST_MORE);
        } else if (d > max) {
            pages = (lastData) + "~" + (((lastData) + max));
            getList(pages, Constant.REQUEST_MORE);
        }
        return true;
    }

    /**
     * 设置数据
     *
     * @param bean
     * @param requestType
     */
    private void setData(RecentPlayModel.ResponseDataObjBean bean, int requestType) {
        //请求获取的最后一条数据
        lastData = bean.getPaginationInfo().getCurrent().getTo();
        //总数据条数
        totalData = bean.getPaginationInfo().getTotalrow();
        //判断数据是否到底了，没到底则添加数据,若d = 0 则到底了，弹出提示。
        int d = (totalData - 1) - bean.getPaginationInfo().getCurrent().getFrom();

        List<PlayListBean> models = bean.getPlayList();
        if (models == null) {
            return;
        }
        if (requestType == Constant.REQUEST_REFRESH) {
            if (models.size() <= 0) {
                adapter.clear();
            } else {
                adapter.setData(models);
            }
            llNone.setVisibility(models.size() <= 0 ? View.VISIBLE : View.GONE);
        } else if (requestType == Constant.REQUEST_MORE) {

            if (d == 0) {
                ToastUtil.showShort("已经到底了");
            } else {
                models.remove(0);
                adapter.addMoreData(models);
            }
        }
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pages = "0~" + max;
        getList(pages, Constant.REQUEST_REFRESH);
    }


    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                showConfirmDialog(this, "确定清空记录吗?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            presenter.cleanPlayRecord(UserUtil.user().getCustomerid());
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        completeRequest();
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        //点击播放
        if (adapter.getData().get(position).getInfotype().equals(Constant.Record)) {
            //查询录音
            presenter.queryRecordById(adapter.getData().get(position).getInfo());
        } else {
            //查询媒体
            presenter.queryMediaById(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, adapter.getData().get(position).getInfo(), position);
        }
    }

    @Override
    public void getList(RecentPlayModel.ResponseDataObjBean bean, int requestType) {
        setData(bean, requestType);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "record":
                RecordModel.ResponseDataObjBean bean = (RecordModel.ResponseDataObjBean) data;
                ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                music.setHttpURL(bean.getVoiceRecordings().get(0).getHttpURL());
                music.setMediaid(bean.getVoiceRecordings().get(0).getId());
                music.setMediatitle(bean.getVoiceRecordings().get(0).getName());
                music.setMediasubtype("record");
                if (AudioPlayer.get().getMusicList().size() > 0) {
                    AudioPlayer.get().getMusicList().clear();
                }
                //播放音乐
                AudioPlayer.get().addAndPlay(music);
                PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                break;
            case "media":
                MediaModel.ResponseDataObjBean musicbean = (MediaModel.ResponseDataObjBean) data;
                ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music1 = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                if (musicbean.getContents().get(0).getMediaList().get(0).getMediatype().equals("audio")) {
                    if (musicbean.getContents().get(0).getMyfavouriteid() != null) {
                        music1.setFavourited("yes");
                        music1.setMyfavouriteid(musicbean.getContents().get(0).getMyfavouriteid());
                    }
                    music1.setHttpURL(musicbean.getContents().get(0).getMediaList().get(0).getHttpURL());
                    music1.setMediaid(musicbean.getContents().get(0).getMediaList().get(0).getMediaid());
                    music1.setMediatitle(musicbean.getContents().get(0).getMediaList().get(0).getMediatitle());
                    music1.setHeaderimgurl(musicbean.getContents().get(0).getImgtitleurl());
                    music1.setMediasubtype("mp3");
                    if (AudioPlayer.get().getMusicList().size() > 0) {
                        AudioPlayer.get().getMusicList().clear();
                    }
                    //播放音乐
                    AudioPlayer.get().addAndPlay(music1);
                    PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                } else {
                    Router.build("videoplayer")
                            .with("title", musicbean.getContents().get(0).getMediaList().get(0).getMediatitle())
                            .with("url", musicbean.getContents().get(0).getMediaList().get(0).getHttpURL())
                            .with("info", musicbean.getContents().get(0).getMediaList().get(0).getMediaid())
                            .with("img",musicbean.getContents().get(0).getImgtitleurl())
                            .go(this);
                }
                break;
            case "clean":
                ToastUtil.showShort("清空成功!");
                adapter.clear();
                llNone.setVisibility(View.VISIBLE);
                break;
            case "createclip":
                //新建收藏夹
                ToastUtil.showShort("创建成功");
                dialogListUtil.notyfyClipList((String) data);
                break;
            case "addcollectmedia":
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.iv_more) {
            showMoreDialog(position);
        }
    }

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
                                                    showInputDialog(RecentPlayActivity.this, "请输入收藏夹名称", new OnViewClickListener() {
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
                                                    adapter.getData().get(position).getHeaderimgurl(),
                                                    adapter.getData().get(position).getInfo(),
                                                    adapter.getData().get(position).getInfotype(),
                                                    adapter.getData().get(position).getAuxinfo(),
                                                    UserUtil.user().getCustomerid(), adapter.getData().get(position).getTitle());
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
                                            PlayListBean bean = adapter.getData().get(position);
                                            presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(index),
                                                    bean.getInfotype(), bean.getInfo(), bean.getTitle(), bean.getDescription(), dialogListUtil.getTargetChannel(index), new String[]{bean.getInfotype(), bean.getInfo()});
                                            dialogListUtil.dismissDialog();
                                        }
                                    });
                                }
                                break;
                            case R.id.tv_share:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {
                                    showShareDialog(RecentPlayActivity.this, new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            EditText et = tDialog.getView().findViewById(R.id.dialog_comment_content);
                                            //分享到广场
                                            if (view.getId() == R.id.tv_confirm) {
                                                if (et.getText().equals("")) {
                                                    ToastUtil.showShort("请输入您的心得");
                                                } else {
                                                    PlayListBean bean = adapter.getData().get(position);
                                                    presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                                            StringUtil.deleteNumber(bean.getTitle()), et.getText().toString(), adapter.getData().get(position).getHeaderimgurl(), Constant.OFFICIAL_FORUM, new String[]{""}, bean.getInfotype(),
                                                            bean.getInfo());
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

                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    Router.build("scan")
                                            .with("info", adapter.getData().get(position).getInfo())
                                            .with("infotype", adapter.getData().get(position).getInfotype())
                                            .with("title", adapter.getData().get(position).getTitle())
                                            .with("img", adapter.getData().get(position).getHeaderimgurl())
                                            .go(RecentPlayActivity.this);
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
                                    Router.build("add_course")
                                            .with("coursename", adapter.getData().get(position).getTitle())
                                            .with("info", adapter.getData().get(position).getInfo())
                                            .with("infotype", adapter.getData().get(position).getInfotype())
                                            .with("imgurl", "")
                                            .go(RecentPlayActivity.this);
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
}
