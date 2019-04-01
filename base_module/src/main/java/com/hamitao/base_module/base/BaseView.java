package com.hamitao.base_module.base;

/**
 * Created by linjianwen on 2018/1/5.
 */

public interface BaseView {

    //初始化
    void onBegin();

    //操作完成
    void onFinish();

    //错误信息
    void onMessageShow(String msg);


}
