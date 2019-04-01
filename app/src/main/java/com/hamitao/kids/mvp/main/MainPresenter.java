package com.hamitao.kids.mvp.main;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.VersionModel;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/4/9.
 */

public class MainPresenter implements BasePresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }


    //查询版本更新
    public void checkNewVersion() {
        NetworkRequest.updateVersionRequest(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

//                                String str = "{\n" +
//                        "    \"uptodateVersion\": {\n" +
//                        "        \"appversion\": \"1.0.1\",\n" +
//                        "        \"force\": false,\n" +
//                        "        \"httpURL\": \"http://cloud.kidknow.net:8888/appdoc/xiaotaotongxue.apk\",\n" +
//                        "        \"osversion\": \">10.3\",\n" +
//                        "        \"tip\": \"- 修复了部分bug\n- 提升了稳定性\",\n " +
//                        "        \"title\": \"版本更新\",\n" +
//                        "        \"url\": \"aaa/bbb/ccc\",\n" +
//                        "        \"urltype\": \"keyonoss\",\n" +
//                        "        \"versioncode\": \"2\"\n" +
//                        "    }\n" +
//                        "}";
//
//                VersionModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(str,VersionModel.ResponseDataObjBean.class);

                VersionModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), VersionModel.ResponseDataObjBean.class);
                view.getVersionUpdateInfo(bean);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }


    /**
     * 查询机器人关系
     *
     * @param customerid
     */
    public void queryRelationById(String customerid) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);

                view.getRobotRelation(bean);

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }
}
