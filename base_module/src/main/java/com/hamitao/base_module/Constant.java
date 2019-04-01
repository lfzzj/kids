package com.hamitao.base_module;

import android.os.Environment;

/**
 * Created by linjianwen on 2018/1/25.
 */

public class Constant {

    public static boolean DEBUG = false;//调试模式

    public static String PROXY_SERVER_URL = "";

    public static final String PACKAGE = "com.hamitao.kids";

    public static final String APP_ID = "";

    public static final String bucket = "hamitaocontent";

    //极光推送KEY
    public static final String JPUSH_APPKEY = "2822915cb877d822d680a6e2";

    //广场：官网论坛
    public static final String OFFICIAL_FORUM = "forum_hamitao_official";

    //无网络
    public static final int NETWORK_NONE = -1;

    public static boolean isTimer = false;


    public static String Record = "recordid";
    public static String Media = "mediaid";
    public static String Content = "contentid";

    //通用密码
    public static String UNIVERSAL_KEY = "8091";

    //厂商
    public static String XIAOSHUAI = "xiaoshuai";
    public static String JINGUOWEI = "jinguowei";
    public static String HAMITAO = "hamitao";


    /**
     * 小于5M的视频无需压缩
     */
    public static final long max_video_size = 1024 * 1024 * 9;

    public static int versionCode = 1;//版本号
    public static String versionName = "1.0.0";//版本名


    /**
     * 请求方式 1:刷新列表 2：加载更多
     */
    public static final int REQUEST_REFRESH = 1;
    public static final int REQUEST_MORE = 2;

    /**
     * 极光IM 相关变量
     */
    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_NAME = "groupName";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String DRAFT = "draft";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";
    public static final String MEMBERS_COUNT = "membersCount";


    /**
     * 数据本地保存地址
     */
    public final static String BASE_LOCAL = Environment.getExternalStorageDirectory() + "/Kids";
    //图片保存路径
    public final static String USER_PIC_LOCAL = BASE_LOCAL + "/Downloader/pic/";
    //apk保存路径
    public final static String USER_APK_LOCAL = BASE_LOCAL + "/Downloader/apk/";
    //音频保存路径
    public final static String USER_VOICE_LOCAL = BASE_LOCAL + "/Downloader/voice/";
    //聊天语音
    public final static String USER_CHAT_RECORD = BASE_LOCAL + "/Downloader/chatRecord/";
    //歌词保存路径
    public final static String USER_LRC_LOCAL = BASE_LOCAL + "/Downloader/lyric/";
    //视频保存路径
    public final static String USER_VIDEO_LOCAL = BASE_LOCAL + "/Downloader/video/";
    //录音保存路径
    public final static String USER_RECORD_LOCAL = BASE_LOCAL + "/Downloader/record/";

}
