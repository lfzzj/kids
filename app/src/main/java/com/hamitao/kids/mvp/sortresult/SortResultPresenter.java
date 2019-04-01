package com.hamitao.kids.mvp.sortresult;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.RecommendContentModel;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2019/1/18.
 */
public class SortResultPresenter implements BasePresenter {


    private SortResultView view;


    public SortResultPresenter(SortResultView view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }


    /**
     * 查询内容
     *
     * @param customerid
     * @param type
     * @param keyword
     */
    public void queryContent(String customerid, String type, String[] keyword,String pages,int requestType) {
        NetworkRequest.queryContentWithoutMediaWithPages(customerid, type, keyword,pages, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecommendContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecommendContentModel.ResponseDataObjBean.class);

//                Collections.sort(bean.getContents(), new Comparator<RecommendContentModel.ResponseDataObjBean.ContentsBean>() {
//                    @Override
//                    public int compare(RecommendContentModel.ResponseDataObjBean.ContentsBean contentsBean, RecommendContentModel.ResponseDataObjBean.ContentsBean t1) {
//                        return StringUtil.changeNum(contentsBean.getNameI18List().get(0).getValue(), t1.getNameI18List().get(0).getValue());
//                    }
//                });

                view.getList(bean,requestType);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }

    /**
     * 通过年龄段查找内容
     *
     * @param customerid
     * @param minAge
     * @param maxAge
     */
    public void queryContentByAge(String customerid, int minAge, int maxAge,String pages,int requestType) {
        NetworkRequest.queryContentByAge(customerid, minAge, maxAge, pages,new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecommendContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecommendContentModel.ResponseDataObjBean.class);
//                Collections.sort(bean.getContents(), new Comparator<RecommendContentModel.ResponseDataObjBean.ContentsBean>() {
//                    @Override
//                    public int compare(RecommendContentModel.ResponseDataObjBean.ContentsBean contentsBean, RecommendContentModel.ResponseDataObjBean.ContentsBean t1) {
//                        return StringUtil.compareTo(contentsBean.getNameI18List().get(0).getValue(), t1.getNameI18List().get(0).getValue());
//                    }
//                });
                view.getList(bean,requestType);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }

}
