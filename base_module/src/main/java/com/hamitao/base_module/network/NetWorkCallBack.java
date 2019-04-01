package com.hamitao.base_module.network;


import com.hamitao.base_module.util.StringUtil;

import io.reactivex.disposables.Disposable;

/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * 网络请求回调
 */

public class NetWorkCallBack {

    private BaseCallBack callBack = null;

    public NetWorkCallBack(BaseCallBack callBack) {
        this.callBack = callBack;
    }

    public NetworkObserver getNetWorkSubscriber() {
        return networkObserver;
    }

    private NetworkObserver networkObserver = new NetworkObserver() {

        @Override
        public void onSubscribe(Disposable d) {
            super.onSubscribe(d);
            if (callBack != null) callBack.onBegin();
        }

        @Override
        public void onComplete() {
            super.onComplete();
            if (callBack != null) callBack.onEnd();
        }

        @Override
        public void onError(Throwable e) {
            try {
                if (callBack != null) {
                    String msg = e.getMessage();

                    if ("Unable to resolve host \"cloud.kidknow.net\": No address associated with hostname".equals(msg) || "Unable to resolve host \"cloud.kidknow.net\": No address associated with hostname".equals(msg) ) {
                        msg = "网络连接错误";
                    } else if ("failed to connect to cloud.kidknow.net/218.17.211.31(port 8080)from/192.168.3.47(port 46035)after 10000ms".equals(msg)) {
                        msg = "连接超时";
                    }else {
                        msg = "请求失败";
                    }
                    callBack.onFail(null, msg);
                    callBack.onEnd();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @Override
        public void onNext(NetWorkResult response) {
            try {
                if (response.isOk()) {
                    //请求成功
                    if (callBack != null)
                        callBack.onSuccess(response); //返回请求结果
                } else {
                    //请求失败
                    String message = "请求失败";
                    if (!StringUtil.isBlank(response.getCause())) {
                        message = response.getCause();
                    }
//                    if (!StringUtil.isBlank(result.getError().getMessage())) {
//                        message = result.getError().getMessage();
//                    }
                 /*   if (result.getError().getCode() == ErrorCode.LOGIN_TOKEN_INVALID.getType()) {
                        //token过期
                        //身份异常,重新登录
                        // ReloginUtil.relogin();
                    }*/
                    if (callBack != null)
                        callBack.onFail(response, message);
                }
            } catch (Exception e) {
                //此处打印网络请求的异常
                e.printStackTrace();
            }
        }
    };


    public interface BaseCallBack {

        void onSuccess(NetWorkResult result);

        void onFail(NetWorkResult result, String msg);

        void onBegin();

        void onEnd();
    }
}
