package com.hamitao.kids.mvp.userinfo;

import com.hamitao.base_module.util.LogUtil;
import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class UserInfoPresenter implements BasePresenter {


    private UserInfoView view;


    public UserInfoPresenter(UserInfoView view) {
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
     * 获取信息
     */
    public void queryInfo(String customerid) {
        NetworkRequest.queryUserInfoRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
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
//                view.onFinish();
            }

        }));
    }


    /**
     * 上传用户默认昵称
     */
    public void uploadPartUserInfo(String faceUrl, String nickName) {
        NetworkRequest.uploadPartUserinfoRequest(faceUrl, nickName, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                LogUtil.d("用户默认信息", "用户默认信息上传成功");
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
