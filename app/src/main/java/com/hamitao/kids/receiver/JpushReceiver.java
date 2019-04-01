package com.hamitao.kids.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenenyu.router.Router;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.model.PushMessageModel;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.model.VideoInfoModel;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by linjianwen on 2018/3/13.
 */

public class JpushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPushInterface";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (nm == null) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtil.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            LogUtil.d(TAG, "接受到推送下来的自定义消息");
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.d(TAG, "接受到推送下来的通知");

//            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.d(TAG, "用户点击打开了通知");

//            openNotification(context,bundle);

        } else if ("com.hamitao.kids.broadcast.SHOW_DIALOG".equals(intent.getAction())) {

        } else {
            LogUtil.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Intent intent = new Intent();
        intent.putExtra("message", message);

        PushMessageModel messageModel = GsonUtil.GsonToBean(message, PushMessageModel.class);
        if (messageModel != null && messageModel.getResponseDataObj() != null) {
            //在这里可以对message的aType进行判断
            switch (messageModel.getResponseDataObj().getChannelMsg().getActionResult().getAction()) {
                case PushParams.PHONE_VIDEO_DEVICE:
                    LogUtil.e(TAG, "手机呼叫机器人视频通话");
                    //手机呼叫机器人，收到消息后进行语音初始化
                    intent.setAction(PushParams.PHONE_VIDEO_DEVICE);
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.PHONE_VOICE_DEVICE:
                    LogUtil.e(TAG, "手机呼叫机器人语音通话");
                    //手机呼叫机器人，收到消息后进行语音初始化
                    intent.setAction(PushParams.PHONE_VOICE_DEVICE);
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.DEVICE_VIDEO_PHONE:
                    LogUtil.e(TAG, "机器人呼叫手机视频通话");
                    VideoInfoModel model = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), VideoInfoModel.class);
                    Router.build("p2p_video")
                            .with("guid", model.getGuid())
                            .with("channelid", messageModel.getResponseDataObj().getChannelMsg().getSource_channelid())
                            .with("fomedevice", true)//来自机器人的呼叫
                            .with("status", PushParams.DEVICE_VIDEO_PHONE).with("token", model.getToken()).go(context);
                    break;
                case PushParams.PHONE_MONITOR_DEVICE:
                    LogUtil.e(TAG, "视频监控");
                    intent.setAction(PushParams.PHONE_MONITOR_DEVICE);
                    intent.putExtra("message", message);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.HANG_UP:
                    LogUtil.e(TAG, "机器人挂断");
                    intent.setAction(PushParams.HANG_UP);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.DEVICE_INFO:
                    LogUtil.e(TAG, "获取机器人信息");
                    intent.setAction(PushParams.DEVICE_INFO);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.STOP_PLAY:
                    intent.setAction(PushParams.STOP_PLAY);
                    context.sendBroadcast(intent);
                case PushParams.START_PLAY:
                    intent.setAction(PushParams.START_PLAY);
                    context.sendBroadcast(intent);
                    break;
                case PushParams.LINE_BUSY:
                    intent.setAction(PushParams.LINE_BUSY);
                    context.sendBroadcast(intent);
                    break;
                default:
                    ToastUtil.showShort(message);
                    break;
            }
        } else {
            ToastUtil.showShort(message);
        }
    }


}
