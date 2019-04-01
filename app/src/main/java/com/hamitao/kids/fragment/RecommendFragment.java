package com.hamitao.kids.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.base.BaseFragment;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.NewRecommendAdapter;
import com.hamitao.kids.model.RecommendContentModel;
import com.hamitao.kids.mvp.recommend.RecommendPresenter;
import com.hamitao.kids.mvp.recommend.RecommendView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;
import static com.hamitao.kids.model.RecommendContentModel.ResponseDataObjBean;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class RecommendFragment extends BaseFragment implements RecommendView, BGAOnItemChildClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.sort)
    TextView sort;

    @BindView(R.id.smart_tree)
    ImageView smart_tree;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.tv_none)
    TextView tv_none;

    private BaseActivity activity;

    private NewRecommendAdapter newAdapter;

    private RecommendPresenter presenter;

    private View view;

    private Unbinder unbinder;

    private List<List<RecommendContentModel.ResponseDataObjBean.ContentsBean>> itemlists = new ArrayList<>(); //总项数
    private List<ResponseDataObjBean.ContentsBean> contentDataList = new ArrayList<>();//其他内容
    private List<ResponseDataObjBean.ContentsBean> bannerDataList = new ArrayList<>(); //banner内容

    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> banner = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> xiaotao = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> anquan = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> guoxue = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> yingyu = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> xiaoxiao = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> kepu = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> huiben = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> donghua = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> zaoqi = new ArrayList<>();
    private List<RecommendContentModel.ResponseDataObjBean.ContentsBean> tongbu = new ArrayList<>();


    String[] tips = new String[]{"今日推荐", "小淘课堂", "幼儿安全知识", "国学经典", "英语磨耳朵", "小小音乐家", "科普知多点", "我爱绘本", "热门动画", "早睡早起", "同步教材"};
    String[] key = new String[]{"幼儿安全知识", "国学经典", "英语磨耳朵", "小小音乐家", "科普知多点", "我爱绘本", "热门动画", "早睡早起"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new RecommendPresenter(this);
        activity = (BaseActivity) getActivity();
        initView();
        return view;
    }

    private void initView() {
        editTextable(etSearch, false);  //不可编辑
        refreshLayout.setDelegate(this);//设置下拉刷新监听
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(getActivity())); //设置下拉样式
        newAdapter = new NewRecommendAdapter(recyclerView);
        newAdapter.setOnItemChildClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //设置列表样式
        recyclerView.setAdapter(newAdapter); //添加适配器到列表
        startRefreshing();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sort, R.id.smart_tree, R.id.rl_search, R.id.iv_search, R.id.et_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort:
                Router.build("sort").go(this);
                break;
            case R.id.et_search:
            case R.id.rl_search:
                Router.build("search").go(this);
                break;
            case R.id.iv_search:
                //扫一扫
                Router.build("scan").go(this);
                break;
            case R.id.smart_tree:
//                ToastUtil.showShort("智慧树功能正在开发中，请期待...");
//                String str = "https://mp.weixin.qq.com/s/Z91AihwROQnoO74Y7BSR_A";
                String str = "https://www.hamitao.cn/store";
//                Router.build("webview").with("result", "http://cloud.kidknow.net:8888/appdoc/app-release.apk").go(this);
                Router.build("webview").with("result", str).go(this);

//                String str = "https://mp.weixin.qq.com/s?__biz=MzU1NDcxNz this);

                break;
        }
    }


    /**
     * EditText是否可以编辑
     */
    private void editTextable(EditText editText, boolean editable) {
        if (!editable) {
            editText.setFocusable(false);   // disable editing password
            editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            editText.setClickable(false); // user navigates with wheel and selects widget
        } else {
            editText.setFocusable(true);  // enable editing of password
            editText.setFocusableInTouchMode(true);
            editText.setClickable(true);
        }
    }


    /**
     * 开始刷新
     */
    public void startRefreshing() {
        if (refreshLayout != null) {
            refreshLayout.setVisibility(View.VISIBLE);
            getList(Constant.REQUEST_REFRESH);
        }
    }

    /**
     * 列表数据请求
     *
     * @param requestType 下拉刷新/加载更多
     */
    private void getList(int requestType) {
        presenter.getListRequest(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, key, requestType);
    }

    /**
     * 刷新/加载完成
     */
    public void completeRequest() {
        BGARefreshUtil.completeRequest(refreshLayout);
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getList(Constant.REQUEST_REFRESH);
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        page++;
//        getList(Constant.REQUEST_MORE);
        return false;
    }

    @Override
    public void onBegin() {
        if (newAdapter.getData() == null || newAdapter.getData().size() <= 0)
            activity.showProgressDialog();
    }

    @Override
    public void onFinish() {
        completeRequest();//完成请求
        activity.dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
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


    /**
     * 获取分层数据（小淘同学、同步教材）
     *
     * @param bean
     * @param position
     */
    @Override
    public void setLayerData(List<ResponseDataObjBean.ContentTreeNodesBean> bean, int position) {

        Collections.sort(bean, new Comparator<ResponseDataObjBean.ContentTreeNodesBean>() {
            @Override
            public int compare(ResponseDataObjBean.ContentTreeNodesBean contentTreeNodesBean, ResponseDataObjBean.ContentTreeNodesBean t1) {
                return StringUtil.changeNum(contentTreeNodesBean.getNodename(),t1.getNodename());
            }
        });

        //将层次内容转换为content
        for (int i = 0; i < bean.size(); i++) {
            ResponseDataObjBean.ContentsBean model = new ResponseDataObjBean.ContentsBean();
            if (bean.get(i).getContentDesc() != null) {
                //专辑
                model.setImgtitleurlhttpURL(bean.get(i).getContentDesc().getImgtitleurlhttpURL());
                model.setContentid(bean.get(i).getContentid());
                model.setCategoryList(bean.get(i).getContentDesc().getCategoryList());

                List<ResponseDataObjBean.ContentsBean.NameI18ListBean> nameI18ListBeans = new ArrayList<>();
                ResponseDataObjBean.ContentsBean.NameI18ListBean name = new ResponseDataObjBean.ContentsBean.NameI18ListBean();
                name.setValue(StringUtil.deleteNumber(bean.get(i).getContentDesc().getNameI18List().get(0).getValue()));
                nameI18ListBeans.add(name);
                model.setNameI18List(nameI18ListBeans);

                model.setDescriptionI18List(bean.get(i).getContentDesc().getDescriptionI18List());
                model.setStatus("enable");

            } else {
                model.setImgtitleurlhttpURL(bean.get(i).getNodeheaderimgurlhttpURL());
                model.setContentid(bean.get(i).getNodeid());
                model.setCategoryList(Arrays.asList(new String[]{bean.get(i).getScenario()}));

                List<ResponseDataObjBean.ContentsBean.NameI18ListBean> nameI18ListBeans = new ArrayList<>();
                ResponseDataObjBean.ContentsBean.NameI18ListBean name = new ResponseDataObjBean.ContentsBean.NameI18ListBean();
                name.setValue(StringUtil.deleteNumber(bean.get(i).getNodename()));
                nameI18ListBeans.add(name);
                model.setNameI18List(nameI18ListBeans);

                List<ResponseDataObjBean.ContentsBean.DescriptionI18ListBean> descriptionI18ListBeans = new ArrayList<>();
                ResponseDataObjBean.ContentsBean.DescriptionI18ListBean description = new ResponseDataObjBean.ContentsBean.DescriptionI18ListBean();
                description.setValue(bean.get(i).getNodedesc());
                descriptionI18ListBeans.add(description);
                model.setDescriptionI18List(descriptionI18ListBeans);
            }
            contentDataList.add(model);
        }
    }


    /**
     * 获取普通内容数据
     *
     * @param bean
     */
    @Override
    public void setContentData(List<ResponseDataObjBean.ContentsBean> bean) {
        contentDataList.addAll(bean);
    }

    /**
     * 获取banner数据（今日推荐）
     *
     * @param bean
     */
    @Override
    public void setBannerData(List<ResponseDataObjBean.ContentsBean> bean) {
        bannerDataList.addAll(bean);
    }


    /**
     * 当数据请求完成后设置数据
     */
    @Override
    public void setData() {
        //清空所有项
        if (itemlists.size() != 0) {
            itemlists.clear();
        }

        itemlists.add(banner);
        itemlists.add(xiaotao);
        itemlists.add(anquan);
        itemlists.add(guoxue);
        itemlists.add(yingyu);
        itemlists.add(xiaoxiao);
        itemlists.add(kepu);
        itemlists.add(huiben);
        itemlists.add(donghua);
        itemlists.add(zaoqi);
        itemlists.add(tongbu);

        banner.clear();
        banner.addAll(bannerDataList);


        /**
         * 先清空数据
         */
        xiaotao.clear();
        anquan.clear();
        guoxue.clear();
        yingyu.clear();
        xiaoxiao.clear();
        kepu.clear();
        huiben.clear();
        donghua.clear();
        zaoqi.clear();
        tongbu.clear();


        //遍历所有的专辑
        for (int i = 0; i < contentDataList.size(); i++) {
            if (contentDataList.get(i).getCategoryList().contains(tips[1])) {
                if (xiaotao.size() < 3)
                    xiaotao.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[2])) {
                if (anquan.size() < 3)
                    anquan.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[3])) {
                if (guoxue.size() < 3)
                    guoxue.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[4])) {
                if (yingyu.size() < 3)
                    yingyu.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[5])) {
                if (xiaoxiao.size() < 3)
                    xiaoxiao.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[6])) {
                if (kepu.size() < 3)
                    kepu.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[7])) {
                if (huiben.size() < 3)
                    huiben.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[8])) {
                if (donghua.size() < 3)
                    donghua.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[9])) {
                if (zaoqi.size() < 3)
                    zaoqi.add(contentDataList.get(i));
            } else if (contentDataList.get(i).getCategoryList().get(0).equals(tips[10])) {
                if (tongbu.size() < 3)
                    tongbu.add(contentDataList.get(i));
            }
        }


        if (itemlists.size() <= 0) {
            newAdapter.clear();
        } else {
            //将为空的的数据村到集合里面然后移除
            List<List<RecommendContentModel.ResponseDataObjBean.ContentsBean>> tempList = new ArrayList<>();
            for (int i = 0; i < itemlists.size(); i++) {
                if (itemlists.get(i).size() <= 0) {
                    tempList.add(itemlists.get(i));
                }
            }
            itemlists.removeAll(tempList);
            newAdapter.setData(itemlists);
            if (tempList != null) {
                tempList.clear();
                tempList = null;
            }
        }

        contentDataList.clear();
        bannerDataList.clear();
    }


    /**
     * 请求失败，显示占位图，当前jin
     */
    @Override
    public void onError(String cause) {
        ToastUtil.showShort(cause);
        itemlists.clear();
        banner.clear();

        List<String> str = new ArrayList<>();
        str.add("今日推荐");

        for (int i = 0; i < 3; i++) {
            ResponseDataObjBean.ContentsBean bean = new ResponseDataObjBean.ContentsBean();
            bean.setCategoryList(str);
            bannerDataList.add(bean);
        }
        banner.addAll(bannerDataList);
        itemlists.add(banner);
        newAdapter.setData(itemlists);
        bannerDataList.clear();
    }


    /**
     * 点击事件
     *
     * @param parent
     * @param childView
     * @param position
     */
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {

        //是否分层数据
        boolean isLayer = (newAdapter.getData().get(position).get(0).getCategoryList().contains(tips[1]) ||
                newAdapter.getData().get(position).get(0).getCategoryList().get(0).equals(tips[10]));

        boolean isXiaotao = newAdapter.getData().get(position).get(0).getCategoryList().contains("小淘课堂");


        switch (childView.getId()) {
            case R.id.tv_type:
                if (isXiaotao && !isPublicXiaotao()) return;
                String params = ((TextView) childView).getText().toString();
                if (!params.equals("今日推荐")) {
                    queryContentByParams(params);
                }
                break;
            case R.id.rl_content1:
                clickContent(isLayer, isXiaotao, position, 0);
                break;
            case R.id.rl_content2:
                clickContent(isLayer, isXiaotao, position, 1);
                break;
            case R.id.rl_content3:
                clickContent(isLayer, isXiaotao, position, 2);
                break;
        }

    }


    /**
     * 点击了内容
     *
     * @param isLayer
     * @param isXiaotao    是否小淘课堂
     * @param position     内容位置
     * @param contentIndex 第几项内容
     */
    private void clickContent(boolean isLayer, boolean isXiaotao, int position, int contentIndex) {

        if (isXiaotao && !isPublicXiaotao()) return;

        boolean isEnable = newAdapter.getData().get(position).get(contentIndex).getStatus() != null
                && "enable".equals(newAdapter.getData().get(position).get(contentIndex).getStatus());
        String nodeName = newAdapter.getData().get(position).get(contentIndex).getContentid();
        String title = newAdapter.getData().get(position).get(contentIndex).getContentid().substring(1);
        String key = newAdapter.getData().get(position).get(contentIndex).getCategoryList().get(0);

        if (isLayer) {
            if (isEnable) {
                Router.build("music_list").with("contentid", nodeName).go(this);
            } else {
                Router.build("content_list").with("isLayer", true)
                        .with("nodename", nodeName)
                        .with("title", title)
                        .with("key", key).go(this);
            }
        } else {
            Router.build("music_list").with("contentid", nodeName).go(this);
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

        boolean isPublic = Constant.HAMITAO.equals(BaseApplication.getInstance().getVendor())
                || Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor());

        if (UserUtil.user() != null && isBindRobot) {
            if (!isPublic) {
                ToastUtil.showShort(getString(R.string.content_close));
                return false;
            }
            return true;
        } else {
            ToastUtil.showShort("请先绑定机器人");
            return false;
        }
    }


    /**
     * 根据参数查询内容
     *
     * @param title
     */
    private void queryContentByParams(String title) {
        String[] p = null;
        switch (title) {
            case "今日推荐":
                p = new String[]{"今日推荐"};
                break;
            case "小淘课堂":
                p = new String[]{"小淘课堂"};
                break;
            case "幼儿安全知识":
                p = new String[]{"安全"};
                break;
            case "国学经典":
                p = new String[]{"国学"};
                break;
            case "英语磨耳朵":
                p = new String[]{"英语"};
                break;
            case "小小音乐家":
                p = new String[]{"儿歌"};
                break;
            case "科普知多点":
                p = new String[]{"科学"};
                break;
            case "我爱绘本":
                p = new String[]{"绘本"};
                break;
            case "热门动画":
                p = new String[]{"动画"};
                break;
            case "早睡早起":
                p = new String[]{"睡觉", "起床"};
                break;
            case "同步教材":
                p = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
                break;
            default:
                break;
        }

        if (title.equals("同步教材") || title.equals("小淘课堂")) {
            Router.build("content_list")
                    .with("isLayer", true)
                    .with("nodename", "")
                    .with("title", title)
                    .with("key", title).go(this);
        } else {
            Router.build("content_list").with("title", title).with("contenttype", p).go(this);
        }
    }


}
