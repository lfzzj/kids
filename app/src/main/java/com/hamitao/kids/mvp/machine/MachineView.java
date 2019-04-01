package com.hamitao.kids.mvp.machine;

import com.hamitao.kids.mvp.CommonView;
import com.hamitao.base_module.model.DeciveFootprintsModel.ResponseDataObjBean.FootprintsBean;
import com.hamitao.base_module.model.DeviceRelationModel;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/16.
 */

public interface MachineView extends CommonView {

    /**
     * 初始化机器人信息
     */
    void getDeciveFootprint(List<FootprintsBean> list);

    /**
     * 获取用户绑定机器人的关系
     *
     * @param bean
     */
    void getDeviceRelation(DeviceRelationModel.ResponseDataObjBean bean);

    /**
     * 解绑机器人
     */
    void unBindDevice(String sourcePeer,String deviceid); //这里的sourcePeer返回用户的Customerid
}
