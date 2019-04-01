package com.hamitao.kids.mvp.start;

import com.hamitao.base_module.base.BaseView;

/**
 * Created by linjianwen on 2018/1/26.
 */

public interface StartView extends BaseView {

    /**
     * 跳转到主界面
     */
    void naviMain();


    /**
     * 跳转到登陆界面
     */
    void naviLogin();


    /**
     * 自动登陆
     */
    void autoLogin();


}
