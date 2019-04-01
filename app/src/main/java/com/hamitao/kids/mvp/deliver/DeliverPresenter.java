package com.hamitao.kids.mvp.deliver;

import com.hamitao.kids.model.DeliverModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class DeliverPresenter implements BasePresenter {

    private DeliverView view;

    public DeliverPresenter(DeliverView view) {
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
     * 查询投送列表
     */
    public void getListRequest(String sourceid,String targetid, String pages, final int requestType) {
        NetworkRequest.queryDeliverRequest(sourceid, targetid,pages, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                DeliverModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeliverModel.ResponseDataObjBean.class);
                view.getList(bean, requestType);

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
     * 清空投送记录
     *
     * @param sourceid
     * @param targetid
     */
    public void cleanListRequest(String sourceid, String targetid) {
        NetworkRequest.cleanDeliverRequest(sourceid, targetid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("clean", null, null);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }
}
