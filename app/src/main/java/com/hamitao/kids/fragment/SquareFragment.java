package com.hamitao.kids.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseFragment;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.SquareAdapter;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.ForumModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.square.SquarePresenter;
import com.hamitao.kids.mvp.square.SquareView;
import com.hamitao.kids.player.AudioPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import io.reactivex.functions.Consumer;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class SquareFragment extends BaseFragment implements SquareView, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnItemChildClickListener {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.tv_none)
    TextView tv_none;

    @BindView(R.id.ll_none)
    LinearLayout ll_none;


    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.back)
    TextView back;

    private View view;

    private Unbinder unbinder;

    private BaseActivity baseActivity;

    private SquareAdapter adapter;

    private SquarePresenter presenter;

    private String pages;//数据请求页
    private int totalData;//总数据条数
    private int lastData = 0;//首次加载18条
    private int max = 9; //每次加载max条数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_square, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }


    private void initData() {
        baseActivity = (BaseActivity) getActivity();
        presenter = new SquarePresenter(this);
        //设置下拉刷新监听
        refreshLayout.setDelegate(this);
        //设置下拉样式
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(getActivity()));
        //是否显示加载更多视图
        refreshLayout.setIsShowLoadingMoreView(true);
        adapter = new SquareAdapter(recyclerView);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
        startRefreshing();//开始刷新数据
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String info) throws Exception {
                        if (info.equals(RxBusEvent.REFRESH_SQUARE_LIST)) {
                            //更新了机器人信息，通知列表改变
                            startRefreshing();//开始刷新数据
                        }
                    }
                });
    }


    private void initView() {
        title.setText("广场");
        back.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(baseActivity));
        recyclerView.setAdapter(adapter);
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
    private void getList(String max, int requestType) {
        presenter.getListRequest(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, max, requestType);
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
    private void setData(ForumModel.ResponseDataObjBean bean, int requestType) {
        //请求获取的最后一条数据
        lastData = bean.getPaginationInfo().getCurrent().getTo();
        //总数据条数
        totalData = bean.getPaginationInfo().getTotalrow();
        //判断数据是否到底了，没到底则添加数据,若d = 0 则到底了，弹出提示。
        int d = (totalData - 1) - bean.getPaginationInfo().getCurrent().getFrom();
        List<ForumModel.ResponseDataObjBean.TopicsBean> models = bean.getTopics();
        if (models == null) {
            return;
        }
        if (requestType == Constant.REQUEST_REFRESH) {
            if (models.size() <= 0) {
                adapter.clear();
            } else {
                adapter.setData(models);
            }
            ll_none.setVisibility(models.size() <= 0 ? View.VISIBLE : View.GONE);
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
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pages = "0~" + max;
        getList(pages, Constant.REQUEST_REFRESH);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    @Override
    public void onBegin() {
        if (adapter.getData() == null || adapter.getData().size() <= 0) {
            baseActivity.showProgressDialog();
        }
    }

    @Override
    public void onFinish() {
        completeRequest();
        baseActivity.dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void getList(ForumModel.ResponseDataObjBean topics, int requestType) {
        setData(topics, requestType);
    }

    @Override
    public void onError() {
        adapter.clear();
        tv_none.setText("数据请求失败");
        ll_none.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        switch (childView.getId()) {
            case R.id.tv_good:
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    changeStatus(position, adapter.getData().get(position).getMylike_id() == null || "".equals(adapter.getData().get(position).getMylike_id()) ? false : true, (TextView) childView.findViewById(R.id.tv_good));
                }
                break;
            case R.id.ll_content:
                switch (adapter.getData().get(position).getInfotype()) {
                    case "contentid":
                        Router.build("music_list").with("contentid", adapter.getData().get(position).getInfo()).go(this);
                        break;
                    case "recordid":
                        //查询录音
                        presenter.queryRecordById(adapter.getData().get(position).getInfo());
                        break;
                    case "mediaid":
                        //查询单曲
                        presenter.queryMediaById(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, adapter.getData().get(position).getInfo(), position);
                        break;
                    case "coursescheduleid":
                        //fromsquare : 由广场跳转的课表不可编辑
                        Router.build("create_schedule").with("fromsquare", true).with("titlename", ((TextView) childView.findViewById(R.id.tv_contentTitle)).getText()).with("customerid", adapter.getData().get(position).getCreatorid()).go(this);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 改变点赞状态
     *
     * @param position
     * @param islike
     * @param tv
     */
    public void changeStatus(int position, boolean islike, TextView tv) {
        if (tv.getTag().equals(position)) {
            if (islike) {
                Drawable unlike = ContextCompat.getDrawable(getActivity(),R.drawable.icon_share_like_n);
                unlike.setBounds(0, 0, unlike.getMinimumWidth(), unlike.getMinimumHeight());
                tv.setCompoundDrawables(unlike, null, null, null);
                tv.setText("0".equals(tv.getText()) ? "0" : (Integer.parseInt(tv.getText().toString()) - 1) + "");
                adapter.getData().get(position).setLikecount(Integer.parseInt(tv.getText().toString()));
                presenter.unlike(adapter.getData().get(position).getMylike_id(), position);
            } else {
                Drawable like =  ContextCompat.getDrawable(getActivity(),R.drawable.icon_share_like_p);
                like.setBounds(0, 0, like.getMinimumWidth(), like.getMinimumHeight());
                tv.setCompoundDrawables(like, null, null, null);
                tv.setText((Integer.parseInt(tv.getText().toString()) + 1) + "");
                adapter.getData().get(position).setLikecount(Integer.parseInt(tv.getText().toString()));
                presenter.like(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""), adapter.getData().get(position).getDescription(), "topic", adapter.getData().get(position).getTopic_id(), position);
            }
        }

    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

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
                AudioPlayer.get().play(music);
                PlayFragment.getInstance().show(getFragmentManager(), "player");
                break;
            case "media":
                MediaModel.ResponseDataObjBean musicbean = (MediaModel.ResponseDataObjBean) data;
                if ("audio".equals(musicbean.getContents().get(0).getMediaList().get(0).getMediatype())) {
                    //判断是否音频
                    ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music1 = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                    if (musicbean.getContents().get(0).getMyfavouriteid() != null) {
                        music1.setFavourited("yes");
                        music1.setMyfavouriteid(musicbean.getContents().get(0).getMyfavouriteid());
                    }
                    music1.setHeaderimgurl(musicbean.getContents().get(0).getImgtitleurl());
                    music1.setHttpURL(musicbean.getContents().get(0).getMediaList().get(0).getHttpURL());
                    music1.setMediaid(musicbean.getContents().get(0).getMediaList().get(0).getMediaid());
                    music1.setMediatitle(musicbean.getContents().get(0).getMediaList().get(0).getMediatitle());
                    music1.setMediasubtype("mp3");
                    if (AudioPlayer.get().getMusicList().size() > 0) {
                        AudioPlayer.get().getMusicList().clear();
                    }
                    //播放音乐
                    AudioPlayer.get().play(music1);
                    PlayFragment.getInstance().show(getFragmentManager(), "player");
                } else {
                    //播放视频
                    Router.build("videoplayer")
                            .with("title", musicbean.getContents().get(0).getMediaList().get(0).getMediatitle())
                            .with("url", musicbean.getContents().get(0).getMediaList().get(0).getHttpURL())
                            .with("info", musicbean.getContents().get(0).getMediaList().get(0).getMediaid())
                            .go(this);
                }

                break;
            case "like":
                adapter.getData().get((Integer) aux).setMylike_id((String) data);
                break;
            case "unlike":
                adapter.getData().get((Integer) aux).setMylike_id("");
                break;
            default:
                break;
        }
    }
}
