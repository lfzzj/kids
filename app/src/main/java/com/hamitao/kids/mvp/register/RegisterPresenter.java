package com.hamitao.kids.mvp.register;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.model.CustomerInfo;
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
 * Created by linjianwen on 2018/3/5.
 */

public class RegisterPresenter implements BasePresenter {


    private RegisterView view;

    public RegisterPresenter(RegisterView view) {
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
     * 获取验证码
     */
    public void getCode(String username, String code) {

        NetworkRequest.getCodeRequest(username, code, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.startCountDown(); //请求成功则开始60秒倒计时
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                ToastUtil.showShort("验证码获取失败");
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
     * 用户注册
     */
    public void regiset(final String loginname, final String password) {
        NetworkRequest.registerRequest(loginname, password, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(final NetWorkResult result) {
                if (view == null) {
                    return;
                }
                final CustomerInfo.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CustomerInfo.ResponseDataObjBean.class);
                //注册极光IM
                JMessageClient.register(loginname, bean.getCustomerInfo().getCustomerid(), new BasicCallback() {
                    @Override
                    public void gotResult(int code, String s) {
                        if (code == 0) {
                            //自动登录
                            autoLogin(loginname, password);
                        } else {
//                            ToastUtil.showShort(s);     // 注册失败。status：错误码；desc：错误描述
                            ToastUtil.showShortError(s);
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
                view.onFinish();
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
            }
        }));
    }


    /**
     * 自动登录
     */
    private void autoLogin(final String loginname, String password) {
        NetworkRequest.loginRequest(loginname, password, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                final CustomerInfo.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CustomerInfo.ResponseDataObjBean.class);
                //登录极光IM
                JMessageClient.login(loginname, bean.getCustomerInfo().getCustomerid(), new BasicCallback() {
                    @Override
                    public void gotResult(int code, String s) {
                        if (code == 0) {
                            UserUtil.saveUserMine(bean.getCustomerInfo(), loginname);  //保存个人信息在本地
                            PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isLogin, true);//登录状态存在本地
                            view.goPerfectInfo(bean.getCustomerInfo().getCustomerid(), bean.getCustomerInfo().getChannelid_listen_on_pushserver());
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
