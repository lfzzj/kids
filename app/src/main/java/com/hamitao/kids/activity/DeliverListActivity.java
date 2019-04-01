package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.DeliverListAdapter;
import com.hamitao.kids.model.DeliverModel;
import com.hamitao.kids.model.DeliverModel.ResponseDataObjBean.AirDropListBean;
import com.hamitao.kids.mvp.deliver.DeliverPresenter;
import com.hamitao.kids.mvp.deliver.DeliverView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * 投送机器人清单
 */
@Route("deliver_list")
public class DeliverListActivity extends BaseActivity implements DeliverView, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;


    private DeliverListAdapter adapter;

    private DeliverPresenter presenter;

    private String pages;//数据请求页
    private int totalData;//总数据条数
    private int lastData = 0;//首次加载18条
    private int max = 20; //每次加载max条数据

    private String terminalid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_list);
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
        presenter = new DeliverPresenter(this);
        adapter = new DeliverListAdapter(recyclerview);
        terminalid = getIntent().getStringExtra("terminalid");
        startRefreshing();
    }

    private void initView() {
        title.setText("投送机器人清单");
        more.setText("清空");
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
    private void getList(String max, int requestType) {

        presenter.getListRequest(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, terminalid, max, requestType);
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
    private void setData(DeliverModel.ResponseDataObjBean bean, int requestType) {
        //请求获取的最后一条数据
        lastData = bean.getPaginationInfo().getCurrent().getTo();
        //总数据条数
        totalData = bean.getPaginationInfo().getTotalrow();
        //判断数据是否到底了，没到底则添加数据,若d = 0 则到底了，弹出提示。
        int d = (totalData - 1) - bean.getPaginationInfo().getCurrent().getFrom();

        List<AirDropListBean> models = bean.getAirDropList();
        if (models == null) {
            return;
        }
        if (requestType == Constant.REQUEST_REFRESH) {
            if (models.size() <= 0) {
                adapter.clear();
            } else {
                adapter.setData(models);
            }
            tvNone.setVisibility(models.size() <= 0 ? View.VISIBLE : View.GONE);
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
                            presenter.cleanListRequest(UserUtil.user().getCustomerid(), terminalid);
                        }
                        tDialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public void onBegin() {
//        showProgressDialog();
    }

    @Override
    public void onFinish() {
        completeRequest();
//        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "clean":
                //清空列表
                adapter.clear();
                break;
        }
    }

    @Override
    public void getList(DeliverModel.ResponseDataObjBean bean, int requestType) {
        setData(bean, requestType);
    }
}
