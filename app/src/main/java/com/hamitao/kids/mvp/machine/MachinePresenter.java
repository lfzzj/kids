package com.hamitao.kids.mvp.machine;

import com.hamitao.base_module.model.DeciveFootprintsModel;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/3/16.
 */

public class MachinePresenter extends CommonPresenter {


    private MachineView view;

    public MachinePresenter(MachineView view) {
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

    /**
     * 初始化机器人的信息
     */
    public void initDeviceInfo(String deviceId) {
        NetworkRequest.getDeviceInfoRequest(deviceId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                DeciveFootprintsModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeciveFootprintsModel.ResponseDataObjBean.class);
                view.getDeciveFootprint(bean.getFootprints());
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
     * 查询机器人绑定关系
     */
    public void queryDeviceRelation(String customerid) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);

                //查询成功后后将机器人关系存入本地
                PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Device_Relation, GsonUtil.GsonToString(bean.getRelation().getTerminalInfos()));
                //是否绑定了我们公司的机器人，如果有绑定则开放制卡，课表，等功能。
                PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isBindDevice, bean.getRelation().getTerminalInfos().size() <= 0 ? false : true);

                view.getDeviceRelation(bean);
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
     * 解绑机器人
     */
    public void unbindDevice(final String sourcePeer, final String targetPeer, final String deviceid) {
        NetworkRequest.unbindDeviceRequest(sourcePeer, targetPeer, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.onMessageShow("解绑成功");
                view.unBindDevice(sourcePeer, deviceid); //解绑机器人后要进行查询
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
     * 重命名绑定机器人
     *
     * @param id_of_peer1       这里相当于 customerid
     * @param id_of_peer2       这里相当于  terminalid
     * @param bindname_of_peer1 用户名（修改后）
     * @param bindname_of_peer2 机器人名（修改后）
     */
    public void renameDevice(String id_of_peer1, String id_of_peer2, String bindname_of_peer1, final String bindname_of_peer2) {
        NetworkRequest.renameDeviceNameRequest(id_of_peer1, id_of_peer2, bindname_of_peer1, bindname_of_peer2, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("rename_device", bindname_of_peer2, null);
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
