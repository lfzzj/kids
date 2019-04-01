package com.hamitao.kids.mvp.identitySelect;

import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/7/30.
 */

public interface IdentitySelectView extends CommonView {

    void getDeviceRelation(DeviceRelationModel.ResponseDataObjBean bean);

    void requestAddFriend(String deviceId, String bindName, String customerid,String devicename);
}
