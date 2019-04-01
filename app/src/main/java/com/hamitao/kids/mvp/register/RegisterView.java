package com.hamitao.kids.mvp.register;

import com.hamitao.base_module.base.BaseView;

/**
 * Created by linjianwen on 2018/3/5.
 */

public interface RegisterView extends BaseView {

    /**
     * 开始倒计时
     */
    void startCountDown();


    /**
     * 跳转完善用户信息界面
     */
    void goPerfectInfo(String customerid, String pushChannal);



}
