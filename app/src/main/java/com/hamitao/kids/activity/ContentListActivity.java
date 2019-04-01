package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.customview.SpacesItemDecoration;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.LayerContentAdapter;
import com.hamitao.kids.model.LayerContentModel;
import com.hamitao.kids.model.RecommendContentModel;
import com.hamitao.kids.network.NetworkRequest;

import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

@Route("content_list")
public class ContentListActivity extends BaseActivity implements BGAOnRVItemClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;

    private String[] type;

    private MyRecommendAdapter adapter;

    private LayerContentAdapter layerAdapter;

    private boolean isLayer = false; //是否分层内容
    private String key; //分类关键字
    private String nodeName; //节点名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        isLayer = getIntent().getBooleanExtra("isLayer", false);

        if (isLayer) {
            layerAdapter = new LayerContentAdapter(recyclerview);
            layerAdapter.setOnRVItemClickListener(this);
            key = getIntent().getStringExtra("key");
            nodeName = getIntent().getStringExtra("nodename");
            title.setText(StringUtil.deleteNumber(getIntent().getStringExtra("title")));
            getLayerContent(key, nodeName);
        } else {
            title.setText(getIntent().getStringExtra("title"));
            adapter = new MyRecommendAdapter(recyclerview);
            adapter.setOnRVItemClickListener(this);
            type = getIntent().getStringArrayExtra("contenttype");
            getRecommendList(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, type); //获取列表数据

        }

    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new SpacesItemDecoration(26, 0));//设置列间距
        recyclerview.setAdapter(isLayer ? layerAdapter : adapter);
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


    private void getRecommendList(String customerid, String[] keyword) {

        NetworkRequest.queryContentWithoutMedia(customerid, "categoryList", keyword, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                RecommendContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecommendContentModel.ResponseDataObjBean.class);

                Collections.sort(bean.getContents(), new Comparator<RecommendContentModel.ResponseDataObjBean.ContentsBean>() {
                    @Override
                    public int compare(RecommendContentModel.ResponseDataObjBean.ContentsBean contentsBean, RecommendContentModel.ResponseDataObjBean.ContentsBean t1) {
                        return StringUtil.compareTo(contentsBean.getNameI18List().get(0).getValue(),t1.getNameI18List().get(0).getValue());
                    }
                });

                adapter.setData(bean.getContents());
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


    private void getLayerContent(String key, String nodename) {
        NetworkRequest.quertContentByLayerRequest(key, nodename, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                LayerContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), LayerContentModel.ResponseDataObjBean.class);

                Collections.sort(bean.getContentTreeNodes(), new Comparator<LayerContentModel.ResponseDataObjBean.ContentTreeNodesBean>() {
                    @Override
                    public int compare(LayerContentModel.ResponseDataObjBean.ContentTreeNodesBean contentTreeNodesBean, LayerContentModel.ResponseDataObjBean.ContentTreeNodesBean t1) {
                        return StringUtil.changeNum(contentTreeNodesBean.getNodename(),t1.getNodename());
                    }
                });
                layerAdapter.setData(bean.getContentTreeNodes());
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
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (isLayer) {
            if (layerAdapter.getData().get(position).getContentid() == null || layerAdapter.getData().get(position).getContentid().equals("")) {
                Router.build("content_list").with("isLayer", true)
                        .with("nodename", layerAdapter.getData().get(position).getNodeid())
                        .with("title", layerAdapter.getData().get(position).getNodename())
                        .with("key", key).go(this);
            } else {
                Router.build("music_list").with("contentid", layerAdapter.getData().get(position).getContentid()).go(this);
            }
        } else {
            Router.build("music_list").with("contentid", adapter.getItem(position).getContentid()).go(this);
        }

    }

    class MyRecommendAdapter extends BGARecyclerViewAdapter<RecommendContentModel.ResponseDataObjBean.ContentsBean> {

        public MyRecommendAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_recommend_list);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, RecommendContentModel.ResponseDataObjBean.ContentsBean model) {
            Glide.with(mContext).load(model.getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
            helper.getTextView(R.id.tv_title).setText(StringUtil.deleteNumber(model.getNameI18List().get(0).getValue()));
            helper.getTextView(R.id.tv_content).setText(model.getDescriptionI18List().get(0).getValue());
        }
    }



}
