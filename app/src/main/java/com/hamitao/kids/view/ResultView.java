package com.hamitao.kids.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.SearchContentAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean;
import com.hamitao.kids.mvp.search.SearchPresenter;
import com.hamitao.kids.mvp.search.SearchView;
import com.hamitao.kids.player.AudioPlayer;
import com.hamitao.kids.util.DialogListUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/22.
 */

public class ResultView extends FrameLayout implements SearchView, BGAOnItemChildClickListener, BGAOnRVItemClickListener {


    @BindView(R.id.rb_single)
    RadioButton rbSingle;

    @BindView(R.id.rb_album)
    RadioButton rbAlbum;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private SearchPresenter presenter;

    private List<ContentsBean> contentsBeans = new ArrayList<>(); // 专辑

    private List<ContentsBean.MediaListBean> mediaListBeans = new ArrayList<>(); //单曲

    private SearchContentAdapter adapter; //专辑adapter

    private SearchMediaAdapter mediaAdapter; // 单曲adapter

    private BaseActivity baseActivity;

    private DialogListUtil dialogListUtil; //弹窗工具

    String id = "";
    String title = "";
    String infotype = "";


    private PlayVideoCallback callback;

    public void setCallback(PlayVideoCallback callback) {
        this.callback = callback;
    }

    public interface PlayVideoCallback {
        void statVieo(String url, String title);
    }


    public void cleanData() {
        mediaAdapter.clear();
        adapter.clear();
    }

    @OnClick({R.id.rb_single, R.id.rb_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_single:
                recyclerview.setAdapter(mediaAdapter);
                mediaAdapter.setData(mediaListBeans);
                break;
            case R.id.rb_album:
                recyclerview.setAdapter(adapter);
                adapter.setData(contentsBeans);
                break;
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.list_more) {
            showMoreDialog(position);
        }
    }


    /**
     * 是否开放小淘课堂
     *
     * @return
     */
    private boolean isPublicXiaotao() {

        //是否绑定了机器人
        boolean isBindRobot = PropertiesUtil.getInstance().getBoolean(PropertiesUtil.SpKey.isBindDevice, false);
        //是否其他厂商（目前只对小淘、小帅开放）
        if (UserUtil.user() != null && isBindRobot) {
            if (!BaseApplication.getInstance().isPublicVendor()) {
                ToastUtil.showShort(getContext().getString(R.string.content_close));
                return false;
            }
            return true;
        } else {
            ToastUtil.showShort("请先绑定机器人");
            return false;
        }

    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {


        if (rbSingle.isChecked()) {
            //单曲按钮是否选中
            String mediaType = mediaAdapter.getData().get(position).getMediasubtype();

            if (mediaType.equals("mp4") || mediaType.equals("avi") || mediaType.equals("rm")) {
                boolean isXiaotao = mediaAdapter.getData().get(position).getMediacontent().contains("小淘课堂");
                if (isXiaotao && !isPublicXiaotao()) {
                    ToastUtil.showShort("请先绑定机器人");
                    return;
                }

                String title = StringUtil.deleteNumber(mediaAdapter.getData().get(position).getMediatitle());
                String url = mediaAdapter.getData().get(position).getHttpURL();
                String info = mediaAdapter.getData().get(position).getMediaid();
                String img = mediaAdapter.getData().get(position).getHeaderimgurl();
                Router.build("videoplayer")
                        .with("title", title)
                        .with("url", url)
                        .with("info", info)
                        .with("img", img)
                        .go(getContext());
            } else {
                PlayFragment.getInstance().show(baseActivity.getSupportFragmentManager(), "ResultView");
                AudioPlayer.get().play(mediaAdapter.getData().get(position));
            }

        } else {
            boolean isXiaotao = adapter.getData().get(position).getCategoryList().contains("小淘课堂");

            if (isXiaotao && !isPublicXiaotao()) {
                return;
            }
            //跳转到具体的听单
            Router.build("music_list").with("contentid", adapter.getData().get(position).getContentid()).go(baseActivity);
        }
    }

    public ResultView(BaseActivity context) {
        super(context);
        this.baseActivity = context;
        LayoutInflater.from(context).inflate(R.layout.view_result, this);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        presenter = new SearchPresenter(this);
        dialogListUtil = new DialogListUtil(baseActivity);

        mediaAdapter = new SearchMediaAdapter(recyclerview);
        mediaAdapter.setOnRVItemClickListener(this);
        mediaAdapter.setOnItemChildClickListener(this);

        adapter = new SearchContentAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);
//        adapter.setOnItemChildClickListener(this);
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(baseActivity));
    }

    public void setData(List<ContentsBean> list, String key) {

        if (list == null && key == null) {
            cleanData();
        }
        contentsBeans = list;

        Collections.sort(list, new Comparator<ContentsBean>() {
            @Override
            public int compare(ContentsBean contentsBean, ContentsBean t1) {
//                return contentsBean.getNameI18List().get(0).getValue().compareTo(t1.getNameI18List().get(0).getValue());
                return StringUtil.changeNum(contentsBean.getNameI18List().get(0).getValue(),t1.getNameI18List().get(0).getValue());
            }
        });
        try {
            for (int i = 0; i < list.size(); i++) {
                //专辑
                for (int j = 0; j < list.get(i).getMediaList().size(); j++) {
                    //单曲
                    String mediaType = list.get(i).getMediaList().get(j).getMediasubtype();
                    if (mediaType.equals("mp3") || mediaType.equals("mp4") || mediaType.equals("avi") || mediaType.equals("rm")) {
                        list.get(i).setImgauthorurl(list.get(i).getImgtitleurlhttpURL());
                        list.get(i).getMediaList().get(j).setHeaderimgurlhttpURL(list.get(i).getImgtitleurlhttpURL());
                        list.get(i).getMediaList().get(j).setHeaderimgurl(list.get(i).getImgtitleurl());
                        list.get(i).getMediaList().get(j).setMediatitle(StringUtil.deleteNumber(list.get(i).getMediaList().get(j).getMediatitle()));
                        list.get(i).getMediaList().get(j).setMediatype("meida");
                        if (list.get(i).getMediaList().get(j).getMediatitle().equals(key)) {
                            mediaListBeans.add(0, list.get(i).getMediaList().get(j));
                        } else {
                            mediaListBeans.add(list.get(i).getMediaList().get(j));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerview.setAdapter(mediaAdapter);


        mediaAdapter.setData(mediaListBeans);
    }

    /**
     * 显示更多选项弹窗
     */
    private void showMoreDialog(final int position) {
        new TDialog.Builder(baseActivity.getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_item_more)
                .setScreenWidthAspect(baseActivity, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_collect, R.id.tv_put, R.id.tv_share, R.id.tv_nfc, R.id.dialog_list_more, R.id.tv_cancel)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (rbSingle.isChecked()) {
                            id = mediaAdapter.getData().get(position).getMediaid();
                            title = mediaAdapter.getData().get(position).getMediatitle();
                            infotype = "mediaid";
                        } else {
                            id = adapter.getData().get(position).getContentid();
                            title = adapter.getData().get(position).getNameI18List().get(0).getValue();
                            infotype = "contentid";
                        }

                        switch (view.getId()) {
                            case R.id.tv_collect:
                                if (UserUtil.user() == null) {
                                    baseActivity.showLoginDialog();
                                } else {
                                    dialogListUtil.showClipListDialog(new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            //打开新建文件夹的弹窗
                                            baseActivity.showInputDialog(baseActivity, "请输入收藏夹名称", new OnViewClickListener() {
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
                                                        //这里新建收藏夹
                                                        presenter.createClip(UserUtil.user().getCustomerid(), ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString());
                                                    }
                                                    tDialog.dismiss();
                                                }
                                            });
                                        }
                                    }, new BGAOnRVItemClickListener() {
                                        @Override
                                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                                            //收藏，判断是专辑还是单曲
                                            if (rbSingle.isChecked()) {
                                                presenter.addCollection(dialogListUtil.getClipName(index), "", mediaAdapter.getData().get(position).getHeaderimgurl()
                                                        , mediaAdapter.getData().get(position).getMediaid(), "mediaid", mediaAdapter.getData().get(position).getMediatype(), UserUtil.user().getCustomerid(), mediaAdapter.getData().get(position).getMediatitle());
                                            } else {
                                                presenter.addCollection(dialogListUtil.getClipName(index), adapter.getData().get(position).getDescriptionI18List().get(0).getValue()
                                                        , adapter.getData().get(position).getImgtitleurl(), adapter.getData().get(position).getContentid(), "contentid", "", UserUtil.user().getCustomerid(),
                                                        adapter.getData().get(position).getNameI18List().get(0).getValue());
                                            }
                                            dialogListUtil.dismissDialog();
                                        }
                                    });
                                }
                                break;
                            case R.id.tv_put:
                                //添加投送
                                if (UserUtil.user() != null) {
                                    dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                                        @Override
                                        public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                                            presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTargetChannel(index), infotype, id, title, "", dialogListUtil.getTargetChannel(index), new String[]{infotype, id});
                                            dialogListUtil.dismissDialog();
                                        }
                                    });
                                } else {
                                    baseActivity.showLoginDialog();
                                }
                                break;
                            case R.id.tv_share:
                                if (UserUtil.user() == null) {
                                    baseActivity.showLoginDialog();
                                } else {
                                    baseActivity.showShareDialog(baseActivity, new OnViewClickListener() {
                                        @Override
                                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                            if (view.getId() == R.id.tv_confirm) {
                                                presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, "")
                                                        , title, ((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString(), rbSingle.isChecked() ? mediaAdapter.getData().get(position).getHeaderimgurl() : adapter.getData().get(position).getImgtitleurl(),
                                                        Constant.OFFICIAL_FORUM, new String[]{}, infotype, id);
                                            }
                                            tDialog.dismiss();
                                        }
                                    });
                                }
                                break;
                            case R.id.tv_nfc:
                                if (UserUtil.user() == null) {
                                    baseActivity.showLoginDialog();
                                } else {
                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    Router.build("scan")
                                            .with("info", id)
                                            .with("infotype", infotype)
                                            .with("title", mediaAdapter.getData().get(position).getMediatitle())
                                            .with("img", mediaAdapter.getData().get(position).getHeaderimgurl())
                                            .go(baseActivity);
                                }
                                break;
                            case R.id.dialog_list_more:

                                if (UserUtil.user() == null) {
                                    baseActivity.showLoginDialog();
                                } else {
                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    if (rbSingle.isChecked()) {
                                        //单曲
                                        Router.build("add_course")
                                                .with("coursename", mediaAdapter.getData().get(position).getMediatitle())
                                                .with("info", mediaAdapter.getData().get(position).getMediaid())
                                                .with("infotype", "mediaid")
                                                .go(baseActivity);
                                    } else {
                                        //专辑
                                        Router.build("add_course")
                                                .with("coursename", adapter.getData().get(position).getNameI18List().get(0).getValue())
                                                .with("info", adapter.getData().get(position).getContentid())
                                                .with("infotype", "contentid")
                                                .go(baseActivity);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                })
                .create()   //创建TDialog
                .show();    //展示
    }


    @Override
    public void onBegin() {
        baseActivity.showProgressDialog();
    }

    @Override
    public void onFinish() {
        baseActivity.dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void setSearchData(List<ContentsBean> list) {
        //搜索内容，此处不做处理
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        //刷新内容，此处不做处理
        switch ((String) status) {
            case "addcollect":
            case "addcollectmedia":
                ToastUtil.showShort("已加入收藏，可以到“我>收藏”里管理听单");
                break;
            case "clean":
                mediaAdapter.clear();
                break;
            case "createclip":
                //新建收藏夹
                ToastUtil.showShort("创建成功");
                dialogListUtil.notyfyClipList((String) data);
                break;
        }
    }


    //单曲适配器
    class SearchMediaAdapter extends BGARecyclerViewAdapter<ContentsBean.MediaListBean> {

        public SearchMediaAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_img_text_more_list);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ContentsBean.MediaListBean model) {
            helper.getTextView(R.id.tv_name).setText(model.getMediatitle());
            Glide.with(mContext).load(model.getHeaderimgurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_header));
        }


        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            super.setItemChildListener(helper, viewType);
            helper.setItemChildClickListener(R.id.list_more);

        }
    }
}
