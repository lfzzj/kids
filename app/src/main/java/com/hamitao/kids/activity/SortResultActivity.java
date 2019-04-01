package com.hamitao.kids.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.SortResultAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.RecommendContentModel;
import com.hamitao.kids.mvp.sortresult.SortResultPresenter;
import com.hamitao.kids.mvp.sortresult.SortResultView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * 分类页面的搜索结果
 */
@Route("sort_result")
public class SortResultActivity extends BaseActivity implements SortResultView, BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.sort_rb1)
    RadioButton sortRb1;
    @BindView(R.id.sort_rb2)
    RadioButton sortRb2;
    @BindView(R.id.sort_rb3)
    RadioButton sortRb3;
    @BindView(R.id.sort_rb4)
    RadioButton sortRb4;
    @BindView(R.id.sort_rb5)
    RadioButton sortRb5;
    @BindView(R.id.sort_rb6)
    RadioButton sortRb6;

    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.ll_none)
    LinearLayout llNone;

    private SortResultPresenter presenter;
    private SortResultAdapter adapter;

    private String[] content; //内容
    private String contenttype; //内容类型
    private int index;//分类点击的下标


    private int totalData;//总数据条数
    private int lastData = 0;//首次加载18条
    private int max = 20; //每次加载max条数据
    private String pages;//数据请求页

    private boolean isAgeType; // 是否年龄类型


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_result);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new SortResultPresenter(this);
        //内容类型
        contenttype = getIntent().getStringExtra("contenttype");
        //分类下标
        index = getIntent().getIntExtra("index", 0);
        //获取请求的数据
        content = getIntent().getStringArrayExtra("content");

        isAgeType = "年龄".equals(contenttype);
        if (isAgeType) {
            quertContentByAge(pages, Constant.REQUEST_REFRESH);
        } else {
            queryContent(new String[]{content[index]}, pages, Constant.REQUEST_REFRESH);
        }

    }

    private void initView() {
        title.setText(contenttype);
        adapter = new SortResultAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);
        refreshLayout.setDelegate(this);
        //设置下拉样式
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(this));
        refreshLayout.setIsShowLoadingMoreView(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        initRadioButton(); //初始化按钮
    }

    @SuppressLint("ResourceType")
    private void initRadioButton() {

        sortRb1.setText(content[0]);
        sortRb2.setText(content[1]);
        sortRb3.setText(content[2]);

        //如果类别是"育儿"，则隐藏后面三个项
        if (contenttype.equals("育儿")) {
            sortRb4.setVisibility(View.GONE);
            sortRb5.setVisibility(View.GONE);
            sortRb6.setVisibility(View.GONE);
        } else {
            sortRb4.setText(content[3]);
            sortRb5.setText(content[4]);
            sortRb6.setText(content[5]);
        }

        sortRb1.setId(0);
        sortRb2.setId(1);
        sortRb3.setId(2);
        sortRb4.setId(3);
        sortRb5.setId(4);
        sortRb6.setId(5);

        switch (index) {
            case 0:
                sortRb1.setChecked(true);
                break;
            case 1:
                sortRb2.setChecked(true);
                break;
            case 2:
                sortRb3.setChecked(true);
                break;
            case 3:
                sortRb4.setChecked(true);
                break;
            case 4:
                sortRb5.setChecked(true);
                break;
            case 5:
                sortRb6.setChecked(true);
                break;
            default:
                break;

        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                index = checkedId;

                if (isAgeType) {
                    quertContentByAge(pages, Constant.REQUEST_REFRESH);
                } else {
                    queryContent(new String[]{content[checkedId]}, pages, Constant.REQUEST_REFRESH);
                }
            }
        });
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        //跳转到停单详情
        Router.build("music_list").with("contentid", adapter.getData().get(position).getContentid()).go(this);
    }

    @OnClick({R.id.back, R.id.iv_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_player:
                PlayFragment.getInstance().show(getSupportFragmentManager(), SortResultActivity.class.getSimpleName());
                break;
            default:
                break;
        }
    }


    private void setData(RecommendContentModel.ResponseDataObjBean bean, int requestType) {
        //请求获取的最后一条数据
        lastData = bean.getPaginationInfo().getCurrent().getTo();
        //总数据条数
        totalData = bean.getPaginationInfo().getTotalrow();
        //判断数据是否到底了，没到底则添加数据,若d = 0 则到底了，弹出提示。
        int d = (totalData - 1) - bean.getPaginationInfo().getCurrent().getFrom();

        List<RecommendContentModel.ResponseDataObjBean.ContentsBean> models = bean.getContents();

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

    @Override
    public void getList(RecommendContentModel.ResponseDataObjBean bean, int requestType) {
        setData(bean, requestType);
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


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (isAgeType) {
            quertContentByAge(pages, Constant.REQUEST_REFRESH);
        } else {
            queryContent(new String[]{content[index]}, pages, Constant.REQUEST_REFRESH);
        }
    }


    /**
     * 上拉加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        int d = (totalData - 1) - lastData;
        if (d < max) {
            pages = (lastData) + "~" + (((lastData) + d));
        } else if (d > max) {
            pages = (lastData) + "~" + (((lastData) + max));
        }
        if (isAgeType) {
            quertContentByAge(pages, Constant.REQUEST_REFRESH);
        } else {
            queryContent(new String[]{content[index]}, pages, Constant.REQUEST_MORE);
        }
        return true;
    }


    /**
     * 查询内容
     *
     * @param keywork
     * @param pages
     * @param requestType
     */
    public void queryContent(String[] keywork, String pages, int requestType) {
        if (requestType == Constant.REQUEST_REFRESH) {
            pages = "0~" + max;
        }
        presenter.queryContent(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, "categoryList", keywork, pages, requestType);

    }

    /**
     * 根据年龄查询内容
     *
     * @param pages
     * @param requestType
     */
    public void quertContentByAge(String pages, int requestType) {
        if (requestType == Constant.REQUEST_REFRESH) {
            pages = "0~" + max;
        }

        //6+岁即查询7到100岁的内容
        int min = "6+岁".equals(content[index]) ? 7 : Integer.parseInt(String.valueOf(content[index].charAt(0)));
        int max = "6+岁".equals(content[index]) ? 100 : Integer.parseInt(String.valueOf(content[index].charAt(2)));
        presenter.queryContentByAge(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, min, max, pages, requestType);
    }


    /**
     * 刷新/加载成功
     */
    public void completeRequest() {
        BGARefreshUtil.completeRequest(refreshLayout);
    }


}
