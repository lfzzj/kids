package com.hamitao.kids.mvp.alarm;

import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.AlarmModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/4/16.
 */

public class AlarmPresenter extends CommonPresenter {

    private AlarmView view;

    public AlarmPresenter(AlarmView view) {
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

    /**
     * 查询闹钟
     */
    public void queryAlarm(String terminalid) {
        NetworkRequest.queryAlarmRequest(terminalid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                AlarmModel.ResponseDataObjBean model = GsonUtil.GsonToBean(result.getResponseDataObj(), AlarmModel.ResponseDataObjBean.class);
                view.getAlarmList(model);
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


    /**
     * 更新闹钟
     */

    public void updateAlarm(String terminalid, String name, String alarmid, final String status,final int position) {
        NetworkRequest.changeAlarmStatusRequest(terminalid, name, alarmid, status, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("update_alarm", status, position);
//                ToastUtil.showShort(status.endsWith("enable") ? "打开闹钟" : "关闭闹钟");
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


    /**
     * 删除闹钟
     */
    public void deleteAlarm(String terminalid, String timerid, final int position) {
        NetworkRequest.deleteAlarmRequest(terminalid, timerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                view.refreshData("delete_alarm", null, position);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }

}
