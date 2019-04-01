package com.hamitao.kids.mvp.recentplay;

import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.RecentPlayModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class RecentPlayPresenter extends CommonPresenter {

    private RecentPlayView view;

    public RecentPlayPresenter(RecentPlayView view) {
        super(view);
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


    public void getListRequest(String owner, final String pages, final int requestType) {
        NetworkRequest.queryPlayRecordRequest(owner, pages, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }

                RecentPlayModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecentPlayModel.ResponseDataObjBean.class);
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
     * 清空播放记录
     */
    public void cleanPlayRecord(String ownerid) {
        NetworkRequest.cleanPlayListRequest(ownerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.refreshData("clean", null, null);
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
