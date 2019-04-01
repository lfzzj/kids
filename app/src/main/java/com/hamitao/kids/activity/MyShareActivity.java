package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.ShareAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.model.ShareModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.mvp.CommonView;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.player.AudioPlayer;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

/**
 * @author linjianwen
 */
@Route("my_share")
public class MyShareActivity extends BaseActivity implements BGAOnItemChildClickListener, CommonView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.iv_none)
    ImageView ivNone;

    private ShareAdapter adapter;

    private CommonPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_share);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        adapter = new ShareAdapter(recyclerview);
        adapter.setOnItemChildClickListener(this);
        presenter = new CommonPresenter(this);
        getList(UserUtil.user().getCustomerid());
    }

    private void initView() {
        tvNone.setText("");
        title.setText("我的分享");
        ivNone.setImageResource(R.drawable.bg_collect_none);
        tvNone.setText("你还没有分享过内容...快来分享吧!");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemChildClick(final ViewGroup parent, View childView, final int position) {
        switch (childView.getId()) {
            case R.id.tv_likeCount:
                break;
            case R.id.iv_delete:
                //删除我的分享
                showConfirmDialog(this, "是否删除我的分享?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            delteMyShare(adapter.getData().get(position).getTopic_id(), position);
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.ll_content:
                //跳转或打开相关的内容界面
                switch (adapter.getData().get(position).getInfotype()) {
                    case "contentid":
                        Router.build("music_list").with("contentid", adapter.getData().get(position).getInfo()).go(this);
                        break;
                    case "recordid":
                        if (UserUtil.user() != null) {
                            presenter.queryRecordById(UserUtil.user().getCustomerid());
                        }
                        break;
                    case "mediaid":
                        presenter.queryMediaById(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, adapter.getData().get(position).getInfo(), position);
                        break;
                    case "coursescheduleid":
                        Router.build("create_schedule").with("coursescheduleid", adapter.getData().get(position).getInfo()).go(this);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }


    private void getList(String createId) {
        NetworkRequest.queryMyShareRequest(createId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ShareModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ShareModel.ResponseDataObjBean.class);
                adapter.setData(bean.getTopics());
                llNone.setVisibility(bean.getTopics().size() <= 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
            }

            @Override
            public void onBegin() {
                showProgressDialog();
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }
        }));
    }


    //删除我的分享
    private void delteMyShare(String topicId, final int position) {
        NetworkRequest.deleteTopicRequest(topicId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                adapter.removeItem(position);
                llNone.setVisibility(adapter.getData().size() <= 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
            }

            @Override
            public void onBegin() {
                showProgressDialog();
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }
        }));
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
                break;
            case "addcollectmedia":
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {

    }
}
