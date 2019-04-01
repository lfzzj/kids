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
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.CollectContentAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.CollectContent.CollectContentPresenter;
import com.hamitao.kids.mvp.CollectContent.CollectContentView;
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

/**
 * 收场的内容
 *
 * @author admin
 */
@Route("collect_content")
public class CollectContentActivity extends BaseActivity implements CollectContentView, BGAOnRVItemClickListener, BGAOnItemChildClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.ll_none)
    LinearLayout llNone;

    @BindView(R.id.iv_none)
    ImageView ivNone;

    @BindView(R.id.tv_none)
    TextView tvNone;

    private CollectContentPresenter presenter;

    private CollectContentAdapter adapter;

    private DialogListUtil dialogListUtil;

    private String clipName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_content);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        clipName = getIntent().getStringExtra("clipname");
        presenter = new CollectContentPresenter(this);
        adapter = new CollectContentAdapter(recyclerview);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
        dialogListUtil = new DialogListUtil(this);
        presenter.queryCollection(UserUtil.user().getCustomerid(), clipName);
    }

    private void initView() {
        title.setText(clipName);
        ivNone.setImageResource(R.drawable.bg_collect_none);
        tvNone.setText("你还没有收藏过内容...快来收藏吧！");
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

    @Override
    public void getList(List<CollectContentModel.ResponseDataObjBean.FavoritesBean> list) {
        adapter.clear();
        adapter.setData(list);
        llNone.setVisibility(list.size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.list_more) {
            showMoreDialog(position);
        }
    }

    private void showMoreDialog(final int index) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_collect_content)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_put, R.id.tv_add, R.id.tv_share, R.id.tv_nfc, R.id.tv_delete, R.id.tv_cancel)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_put:
                                dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                                    @Override
                                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                                        presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(position), adapter.getData().get(index).getInfotype(), adapter.getData().get(index).getInfo(),
                                                adapter.getData().get(index).getTitle(), adapter.getData().get(index).getDescription(), dialogListUtil.getTargetChannel(position),
                                                new String[]{adapter.getData().get(index).getInfotype(), adapter.getData().get(index).getInfo()});
                                        dialogListUtil.dismissDialog();
                                    }
                                });
                                break;
                            case R.id.tv_share:
                                showShareDialog(CollectContentActivity.this, new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            /**
                                             * 创建主题（发帖子）
                                             *
                                             * @param creatorid
                                             * @param createName
                                             * @param title
                                             * @param description
                                             * @param imgUrl
                                             * @param forumId     forum_id,哪个论坛下面的主题
                                             * @param keywords
                                             * @param infoType    可选值："contentid"/"recordid"/"coursesheduleid" 即 内容id/录音id/课程id
                                             * @param userInfo        具体 ID
                                             */
                                            presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                                    adapter.getData().get(index).getTitle(), ((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString(), adapter.getData().get(index).getHeaderimgurl(), Constant.OFFICIAL_FORUM,
                                                    new String[]{""}, adapter.getData().get(index).getInfotype(), adapter.getData().get(index).getInfo());
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_nfc:
                                if (!BaseApplication.getInstance().isPublicVendor()) {
                                    ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                    return;
                                }
                                Router.build("scan")
                                        .with("title", adapter.getData().get(index).getTitle())
                                        .with("info", adapter.getData().get(index).getInfo())
                                        .with("img", adapter.getData().get(index).getHeaderimgurl())
                                        .with("infotype", adapter.getData().get(index).getInfotype())
                                        .go(CollectContentActivity.this);

                                break;
                            case R.id.tv_delete:
                                showConfirmDialog(CollectContentActivity.this, "确定要删除该条收藏吗？", new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            presenter.deleteCollection(UserUtil.user().getCustomerid(), adapter.getData().get(index).getFavoriteid());
                                            adapter.removeItem(index);
                                        }
                                        tDialog.dismiss();

                                    }
                                });
                                break;
                            case R.id.tv_add:

                                //加入课表
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {

                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }


                                    Router.build("add_course")
                                            .with("coursename", adapter.getData().get(index).getTitle())
                                            .with("info", adapter.getData().get(index).getInfo())
                                            .with("infotype", adapter.getData().get(index).getInfotype())
                                            .with("imgurl", adapter.getData().get(index).getHeaderimgurl())
                                            .go(CollectContentActivity.this);
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
    public void refreshData(Object status, Object data, Object aux) {
        //这里做了偷懒，要改
        presenter.queryCollection(UserUtil.user().getCustomerid(), clipName);

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
                AudioPlayer.get().play(music);
                PlayFragment.getInstance().show(getSupportFragmentManager(), "CollectContentActivity");
                break;
            case "media":
                MediaModel.ResponseDataObjBean musicbean = (MediaModel.ResponseDataObjBean) data;
                ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music1 = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
                if ("audio".equals(musicbean.getContents().get(0).getMediaList().get(0).getMediatype())) {
                    //音频
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
                    PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                } else {
                    Router.build("videoplayer")
                            .with("title", musicbean.getContents().get(0).getMediaList().get(0).getMediatitle())
                            .with("url", musicbean.getContents().get(0).getMediaList().get(0).getHttpURL())
                            .with("info", musicbean.getContents().get(0).getMediaList().get(0).getMediaid())
                            .go(this);
                }
                break;
            case "deletecollect":
                ToastUtil.showShort("删除成功");
                llNone.setVisibility(adapter.getData().size() <= 0 ? View.VISIBLE : View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        switch (adapter.getData().get(position).getInfotype()) {
            case "mediaid":
                //直接播放单曲
                presenter.queryMediaById(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, adapter.getData().get(position).getInfo(), position);
                break;
            case "contentid":
                Router.build("music_list").with("contentid", adapter.getData().get(position).getInfo()).go(this);
                break;
            case "recordid":
                presenter.queryRecordById(adapter.getData().get(position).getInfo());
                break;
            default:
                break;
        }
    }
}
