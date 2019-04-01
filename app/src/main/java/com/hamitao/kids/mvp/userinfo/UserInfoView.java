package com.hamitao.kids.mvp.userinfo;

import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.base_module.base.BaseView;

/**
 * Created by linjianwen on 2018/1/17.
 */

public interface UserInfoView extends BaseView {


    /**
     * 网络请求查询用户信息
     */
    void getUserInfo(UserInfoModel.ResponseDataObjBean bean);


    void uploadSuccess();


//
//    /**
//     * 上传图片成功
//     * @param imgUrl
//     */
//    void updateImgSuccess(String imgUrl);
//
//
//    /**
//     * 上传图片失败
//     * @param msg
//     */
//    void updateImgFail(String msg);


}
