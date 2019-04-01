package com.hamitao.kids.mvp.login;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.model.CustomerInfo;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.network.NetworkRequest;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by linjianwen on 2018/1/16.
 */

public class LoginPresenter implements BasePresenter {


    private LoginView view;

    public LoginPresenter(LoginView view) {
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
     * 用户登录
     */
    public void login(final String loginname, final String password) {

        NetworkRequest.loginRequest(loginname, password, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(final NetWorkResult result) {
                if (view == null) {
                    return;
                }

                final CustomerInfo.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CustomerInfo.ResponseDataObjBean.class);
                //登录极光IM
                JMessageClient.login(loginname, bean.getCustomerInfo().getCustomerid(), new BasicCallback() {
                    @Override
                        public void gotResult(int code, String s) {
                        if (code == 0) {
                            UserUtil.saveUserMine(bean.getCustomerInfo(), loginname); //登录状态存在本地
                            PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isLogin, true); //记录是否登录
                            view.goMain(bean.getCustomerInfo().getChannelid_listen_on_pushserver()); //跳转到主界面
                        } else {
                            view.onMessageShow(s);
                        }
                    }
                });
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
     * 查询机器人关系
     *
     * @param customerid
     */
    public void queryRelationById(String customerid) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                view.getRobotRelation(bean);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort("");
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
