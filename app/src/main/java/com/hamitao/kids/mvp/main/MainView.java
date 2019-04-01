package com.hamitao.kids.mvp.main;

import com.hamitao.base_module.base.BaseView;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.kids.model.VersionModel;

/**
 * Created by linjianwen on 2018/4/9.
 */

public interface MainView extends BaseView {

    void getVersionUpdateInfo(VersionModel.ResponseDataObjBean bean);


    void getRobotRelation(DeviceRelationModel.ResponseDataObjBean bean);

}
