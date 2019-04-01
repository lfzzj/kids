package com.hamitao.base_module.rxbus;

/**
 * Created by linjianwen on 2018/8/15.
 */

public class RxBusEvent {
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS"; //登录

    public static final String REGISTER_WITHOUT_SAVE = "REGISTER_WITHOUT_SAVE";//刚注册位保存信心

    public static final String LOGOUT = "LOGOUT";//退出登录

    public static final String REFRESH_CONVERSATION_LIST = "REFRESH_CONVERSATION_LIST";//刷新会话列表

    public static final String REFRESH_SQUARE_LIST = "REFRESH_SQUARE_LIST";//刷新广场列表

    public static final String BIND_DEVICE_SUCCESS = "BIND_DEVICE_SUCCESS"; //成功绑定机器人

    public static final String CHAT_MESSAGE_REFUSE = "CHAT_MESSAGE_REFUSE"; //聊天已拒绝

    public static final String CHAT_MESSAGE_CANCEL = "CHAT_MESSAGE_CANCEL"; //聊天已取消（主动）

    public static final String CHAT_MESSAGE_DURATION = "CHAT_MESSAGE_DURATION";//聊天时长

    public static final String CHAT_LINE_BUSY = "CHAT_LINE_BUSY";//对方忙


    public static final String RENAME_DEVICE = "rename_device";//重命名机器人




    //刷新我的页面种的机器人绑定状态
    public static final String MEFRAGMENT_REFRESH_DEVICE_INFO ="MEFRAGMENT_REFRESH_DEVICE_INFO";

}
