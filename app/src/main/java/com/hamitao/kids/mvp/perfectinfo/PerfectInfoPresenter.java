package com.hamitao.kids.mvp.perfectinfo;

import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;

/**
 * Created by linjianwen on 2018/4/20.
 */

public class PerfectInfoPresenter implements BasePresenter {


    private PerfectInfoView view;

    public PerfectInfoPresenter(PerfectInfoView view) {
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
     * 上传用户信息
     */
    public void uploadUserInfo(String faceUrl, String nickName, String bbName, String sex, String birthday) {
        NetworkRequest.uploadUserInfoRequest(faceUrl, nickName, bbName, sex, birthday, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.uploadSuccess();
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
