package com.hamitao.base_module;

/**
 * Created by linjianwen on 2018/3/8.
 */

public class RequestCode {

    //---------------页面请求码-----------------------------------

    public static final int REQUEST_FROM_GALLERY = 10000;//从相册中选择

    public static final int REQUEST_CAMERA = 10001; //拍照

    public static final int REQUEST_EDIT_SCHEDULE = 10002; //编辑课表

    public static final int REQUEST_RECORD = 10003;  //录音界面

    public static final int REQUEST_USERINFO = 10004;  //录音界面

    public static final int REQUEST_VIDEO_CHAT = 1005; //视频聊天

    public static final int REQUEST_ADD_ALARM = 10006; //新建闹钟界面

    public static final int PHOTO_CLIP = 10007;//裁剪后的回调

    public static final int REQUEST_CHAT = 10008; //跳转到对聊

    public static final int REQUEST_LOGIN = 10009; //登录页面，登录成功后的跳转

    public static final int REQUEST_SCHEDULE_SETTING = 10010; // 课表设置

    public static final int REQUEST_SCHEDULE_DELETE = 10011;//删除课表

    public static final int REQUEST_COURSE_DETAIL = 10012; //课程详情

    public static final int REQUEST_GROUP_CODE = 10013; //群二维码

    public static final int REQUEST_SCAN_DEVICE = 10014; //从机器人页面跳转到扫码页

    public static final int REQUEST_SCAN_COMPLETE = 10015; //扫描完成

    public static final int REQUEST_CREATE_GROUP = 10016; //发起群聊

    public static final int RESULT_CLOSE_ALARM_TIME =10017;//定时关闭


    //聊天对话
    public final static int CAPTURE_VIDEO = 10017;// 拍摄视频
    public final static int GET_LOCAL_VIDEO = 10018;// 选择视频
    public final static int GET_LOCAL_FILE = 10019; // 选择文件
    public final static int PICK_IMAGE = 10020;
    public final static int PICKER_IMAGE_PREVIEW = 10021;
    public final static int PREVIEW_IMAGE_FROM_CAMERA = 10022;
    public final static int GET_LOCAL_IMAGE = 10023;// 相册
    public static final int TAKE_PHOTO = 10024;
    public static final int TAKE_VIDEO = 10025;
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;



}
