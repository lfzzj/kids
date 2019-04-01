package com.hamitao.kids.mvp.identitySelect;

import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/7/30.
 */

public class IdentitySelectPresenter extends CommonPresenter {

    private IdentitySelectView view;

    public IdentitySelectPresenter(IdentitySelectView view) {
        super(view);
        this.view = view;
    }





    /**
     * 绑定机器人
     *
     * @param bindName   身份
     * @param devicename 机器人名
     * @param customerid
     * @param deviceId
     */
    public void bindDevice(final String bindName, final String devicename, final String customerid, final String deviceId) {
        NetworkRequest.bindDeviceRequest(bindName, devicename, customerid, deviceId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.requestAddFriend(deviceId,bindName,customerid,devicename);
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
     * 根据Deviceid查询绑定关系
     */
    public void queryDeviceRelationByDeviceid(String deviceid) {
        NetworkRequest.queryDeviceRelationByDeviceidRequest(deviceid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                view.getDeviceRelation(bean);
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
