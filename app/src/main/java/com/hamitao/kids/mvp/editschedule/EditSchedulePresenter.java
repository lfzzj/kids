package com.hamitao.kids.mvp.editschedule;

import com.hamitao.kids.model.ClipModel;
import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/5/16.
 */

public class EditSchedulePresenter implements BasePresenter {


    private EditScheduleView view;

    public EditSchedulePresenter(EditScheduleView view) {
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


    //查询我的收藏
    public void queryMyAllCollection(final String customerid) {
        NetworkRequest.queryMyCollectionRequest(customerid, null, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                CollectContentModel.ResponseDataObjBean collection = GsonUtil.GsonToBean(result.getResponseDataObj(), CollectContentModel.ResponseDataObjBean.class);
                queryMyClip(customerid, collection);
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

    //查询搜藏夹
    public void queryMyClip(final String customerid, final CollectContentModel.ResponseDataObjBean collection) {
        NetworkRequest.queryMyClipRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                final ClipModel.ResponseDataObjBean clip = GsonUtil.GsonToBean(result.getResponseDataObj(), ClipModel.ResponseDataObjBean.class);
                queryMyRecordList(customerid, clip, collection);

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

    //查询我的录音
    public void queryMyRecordList(String ownerid, final ClipModel.ResponseDataObjBean clip, final CollectContentModel.ResponseDataObjBean collection) {
        NetworkRequest.queryRecordRequest(ownerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecordModel.ResponseDataObjBean record = GsonUtil.GsonToBean(result.getResponseDataObj(), RecordModel.ResponseDataObjBean.class);

                view.initClip(clip, collection, record);
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
