package com.hamitao.kids.mvp.record;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.RecordUtil;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView view;

    private BaseActivity baseActivity;


    public RecordPresenter(RecordView recordView, BaseActivity context) {
        this.view = recordView;
        this.baseActivity = context;
        start();
    }


    /**
     * 初始化
     */
    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }


    /**
     * 添加录音
     */
    public void addRecord(final String recordName, String cutomerid) {
//        String ok = "appstorage/testdir/" + PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "") + "/Record/" + recordName + ".aac";
        String ok = "appstorage/testdir/" + PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "") + "/Record/" + recordName + ".mp3";
        NetworkRequest.addRecordRequest("", "", recordName, cutomerid, "", "yes", ok, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                uploadRecord(recordName);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                baseActivity.showProgressDialog("录音保存中...");
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
            }
        }));
    }


    /**
     * 上传录音到OSS
     */
    public void uploadRecord(String recordName) {

        String ok = "appstorage/testdir/" + PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "") + "/Record/" + recordName + ".mp3";

        PutObjectRequest put = new PutObjectRequest(Constant.bucket, ok, RecordUtil.getInstance().getRecordFile().getPath());

        BaseApplication.getInstance().getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (view == null) {
                    return;
                }
                view.refreshData("success", null, null);
                view.onFinish();
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (view == null) {
                    return;
                }
                view.refreshData("failure", null, null);
                view.onFinish();
            }
        });

    }


}
