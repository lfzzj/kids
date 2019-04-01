package com.hamitao.kids.mvp.scan;

import com.hamitao.kids.model.nfccard.NfcModel;
import com.hamitao.kids.model.requestmodel.NfcRequestModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.ToastUtil;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by linjianwen on 2018/4/4.
 */

public class ScanPresenter implements BasePresenter {

    private ScanView view;

    public ScanPresenter(ScanView view) {
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
     * 制卡
     */
    public void makeCard(final NfcRequestModel model) {
        NetworkRequest.makeCardRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.onMessageShow("制卡成功");
                //制卡成功后查卡
                queryMyCardByCode(model.getNfcard().getNFCID());
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
     * 根据条码查询制卡
     */
    public void queryMyCardByCode(String nfcid) {
        NetworkRequest.queryMyCardByCodeRequest(nfcid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }

                //制卡后查询卡片
                NfcModel.ResponseDataObjBean model = GsonUtil.GsonToBean(result.getResponseDataObj(), NfcModel.ResponseDataObjBean.class);

                if (model.getNFCCards().size() == 0) {
                    ToastUtil.showShort("该卡片尚未绑定任何内容！");
                    view.bindNull();//删除扫码页面
                } else {
                    view.bindSuccess(model.getNFCCards().get(0));
                }
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
     * 绑定机器人
     */
    public void bindDeviceRequest(String bindName, String deviceName, String customerid, final String deviceId) {
        NetworkRequest.bindDeviceRequest(bindName, deviceName, customerid, deviceId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.onMessageShow("已绑定:" + deviceId);
                Conversation.createSingleConversation(deviceId); //绑定成功后创建会话
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                ToastUtil.showShort("绑定" + deviceId + "失败:" + msg);
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
