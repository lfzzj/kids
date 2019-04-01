package com.hamitao.kids.mvp.recommend;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.CommonParams;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.kids.model.RecommendContentModel;
import com.hamitao.kids.network.NetWork;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hamitao.base_module.network.CommonParams.commonParam;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class RecommendPresenter implements BasePresenter {

    private RecommendView view;


    public RecommendPresenter(RecommendView view) {
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


    //获取列表数据
    public void getListRequest(final String customerid, final String[] key, final int requestType) {
        request(customerid, key);
    }


    private void request(String id, final String[] key) {

        view.onBegin();

        Map<String, Object> map1 = commonParam();
        map1.put("categoryList", key);


        Map<String, Object> map2 = commonParam();
        map2.put("categoryList", "今日推荐");


        Observable contentData = NetWork.getApi().searchContentWithoutMedia(id, CommonParams.NetWorkRequestBody(map1));

        Observable bannerData = NetWork.getApi().searchContentWithoutMedia(id, CommonParams.NetWorkRequestBody(map2));

        Observable b1 = NetWork.getApi().searchContentByLayer("小淘课堂", "");

        Observable b2 = NetWork.getApi().searchContentByLayer("同步教材", "");


        Observable.merge(contentData, bannerData, b1, b2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetWorkResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(NetWorkResult result) {
                        LogUtil.d("result", result.toString());
                        RecommendContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecommendContentModel.ResponseDataObjBean.class);
                        if (bean == null) {
                            return;
                        }
                        if (bean.getContentTreeNodes() == null) {
                            if (bean.getContents().get(0).getCategoryList().get(0).equals("今日推荐")) {
                                //banner
                                view.setBannerData(bean.getContents());
                            } else {
                                //普通内容
                                view.setContentData(bean.getContents());
                            }
                        } else {
                            //层级内容（小淘课堂、同步教材）
                            view.setLayerData(bean.getContentTreeNodes(), 0);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        String msg = e.getMessage();

                        if ("Unable to resolve host \"cloud.kidknow.net\": No address associated with hostname".equals(msg) || "Unable to resolve host \"cloud.kidknow.net\": No address associated with hostname".equals(msg)) {
                            msg = "网络连接错误";
                        } else if ("failed to connect to cloud.kidknow.net/218.17.211.31(port 8080)from/192.168.3.47(port 46035)after 10000ms".equals(msg)) {
                            msg = "连接超时";
                        } else {
                            msg = "未知错误";
                        }

                        view.onError(msg);
                        view.onFinish();
                    }

                    @Override
                    public void onComplete() {
                        view.setData();
                        view.onFinish();
                    }
                });
    }
}
