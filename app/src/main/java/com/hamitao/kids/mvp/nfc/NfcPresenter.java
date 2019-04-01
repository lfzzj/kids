package com.hamitao.kids.mvp.nfc;

import com.hamitao.kids.model.nfccard.NfcModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/3/28.
 */

public class NfcPresenter implements BasePresenter {

    private NfcView view;

    public NfcPresenter(NfcView view) {
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
     * 查询我的制卡
     */
    public void queryMyCard(String costomerid) {

        NetworkRequest.queryMyCardRequest(costomerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                NfcModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), NfcModel.ResponseDataObjBean.class);
                view.setList(bean.getNFCCards());
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
     * 删除制卡
     *
     * @param costomerid
     * @param nfcid
     */
    public void deleteNfc(String costomerid, String nfcid, final int position) {
        NetworkRequest.deleteCardRequest(costomerid, nfcid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.deldteNfc(position);
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
