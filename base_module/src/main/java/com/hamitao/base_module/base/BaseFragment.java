package com.hamitao.base_module.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class BaseFragment extends Fragment {

    protected Handler handler;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());

    }


    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }


}
