package com.hamitao.kids.mvp.record;

import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class MyRecordPresenter extends CommonPresenter {

    private MyRecordView view;

    public MyRecordPresenter(MyRecordView view) {
        super(view);
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


    //查询我的录音
    public void queryMyRecordList(String ownerid) {
        NetworkRequest.queryRecordRequest(ownerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecordModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecordModel.ResponseDataObjBean.class);
                view.getMyRecordList(bean.getVoiceRecordings());
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
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


    //删除录音
    public void deleteMyRecord(String[] recordid, final String url, final int position) {
        NetworkRequest.deleteRecordRequest(recordid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                //删除OSS上的录音 , 这里回传  文件名字 url

                view.refreshData("delete", url, position);
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
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


    //修改录音名称
    public void renameRecord(String description, String id, String name, String ownerid, String url, final int position) {
        NetworkRequest.updatRecordRequest(description, id, name, ownerid, "", "", url, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecordModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecordModel.ResponseDataObjBean.class);
                view.refreshData("rename", bean.getVoiceRecordings().get(0), position);
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
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


}
