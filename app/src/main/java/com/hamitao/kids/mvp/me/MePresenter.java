package com.hamitao.kids.mvp.me;

import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/4/20.
 */

public class MePresenter implements BasePresenter {

    private MeView view;

    public MePresenter(MeView view) {
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


    //加载信息
    public void updateUserinfo(String costomerId) {
        NetworkRequest.queryUserInfoRequest(costomerId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                UserInfoModel.ResponseDataObjBean model = GsonUtil.GsonToBean(result.getResponseDataObj(), UserInfoModel.ResponseDataObjBean.class);
                view.getUserInfo(model);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
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


    //查询机器人绑定关系
    public void queryDeviceRelation(String customerid) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                view.getDeviceRelation(bean.getRelation().getTerminalInfos());

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
