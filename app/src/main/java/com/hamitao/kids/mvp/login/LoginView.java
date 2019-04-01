package com.hamitao.kids.mvp.login;

import com.hamitao.base_module.base.BaseView;
import com.hamitao.base_module.model.DeviceRelationModel;

/**
 * Created by linjianwen on 2018/1/16.
 */

public interface LoginView extends BaseView {


    /**
     * 跳转到主界面
     */
    void goMain(String channelid);


    /**
     * 登录后获取机器人绑定信息
     */
    void getRobotRelation(DeviceRelationModel.ResponseDataObjBean bean);
}
