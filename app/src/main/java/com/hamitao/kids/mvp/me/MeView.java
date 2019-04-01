package com.hamitao.kids.mvp.me;

import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.base_module.base.BaseView;
import com.hamitao.base_module.model.DeviceRelationModel;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/20.
 */

public interface MeView extends BaseView {



    /**
     * 获取用户信息
     * @param model
     */
    void getUserInfo(UserInfoModel.ResponseDataObjBean model);

    /**
     * 获取用户信息失败
     */
//    void getUseInfoFail();


    /**
     * 获取用户的绑定关系
     * @param terminalInfos
     */
    void getDeviceRelation(List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> terminalInfos);


}
