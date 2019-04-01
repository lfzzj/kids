package com.hamitao.kids.network;

import com.hamitao.base_module.network.NetWorkResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * url 和 请求方法
 */

public interface ServiceApi {


    @FormUrlEncoded
    @POST(Api.AUTOLOGIN)
    Observable<NetWorkResult> autoLogin(@FieldMap Map<String, String> map);

    /* ------------------------------------------应用相关----------------------------------------------*/
    //检查最新版本
    @GET(Api.UPDATE_VERSINO)
    Observable<NetWorkResult> updateVersion(@QueryMap Map<String, Object> map);

    //获取OSSkey
    @GET(Api.GET_OSSKEY)
    Observable<NetWorkResult> getOssKey(@QueryMap Map<String, Object> map);

    @POST(Api.FEEDBACK)
    Observable<NetWorkResult> feedback(@Body RequestBody requestBody);

    /* ------------------------------------------登录注册----------------------------------------------*/
    //用户注册
    @POST(Api.USER_REGISTER)
    Observable<NetWorkResult> register(@Body RequestBody requestBody);

    //用户登录
    @POST(Api.USER_LOGIN)
    Observable<NetWorkResult> login(@Body RequestBody requestBody);

    //获取验证码
    @GET(Api.GET_CODE)
    Observable<NetWorkResult> getCode(@Query("target") String target, @Query("content") String content);

    //修改密码
    @POST(Api.MODIFY_PASSWORD)
    Observable<NetWorkResult> modifyPassword(@Body RequestBody requestBody);

    /* -----------------------------------------绑定机器人----------------------------------------------*/

    //通过机器人ID绑定机器人
    @POST(Api.BIND_DEVICE)
    Observable<NetWorkResult> bindDevice(@Body RequestBody requestBody);

    //通过机器人ID绑定机器人
    @POST(Api.UNBIND_DEVICE)
    Observable<NetWorkResult> unbindDevice(@Body RequestBody requestBody);

    //查询机器人信息
    @GET(Api.GET_LAST_INFO)
    Observable<NetWorkResult> getDeviceInfo(@QueryMap Map<String, Object> map);

    //查询机器人绑定关系
    @GET(Api.QUERY_DEVICE_RELATION)
    Observable<NetWorkResult> queryDeviceRelation(@Query("myid") String id);

    //通过Deviceid查询
    @GET(Api.QUERY_DEVICE_RELATION_BY_DEVICEID)
    Observable<NetWorkResult> queryDeviceRelationByDeviceid(@Query("deviceid") String deviceid);

    @POST(Api.RENAME_BINDNAME)
    Observable<NetWorkResult> renameDevice(@Body RequestBody requestBody);

    /* -----------------------------------------闹钟相关----------------------------------------------*/

    //查询闹钟
    @GET(Api.QUERT_ALARM)
    Observable<NetWorkResult> queryAlarm(@Query("terminalid") String id);

    //查询闹钟
    @GET(Api.DELETE_ALARM)
    Observable<NetWorkResult> deleteAlarm(@Query("terminalid") String terminalid, @Query("timerid") String timerid);

    //添加闹钟
    @POST(Api.ADD_ALARM)
    Observable<NetWorkResult> addAlarm(@Body RequestBody requestBody);

    //更新闹钟
    @POST(Api.UPDATE_ALARM)
    Observable<NetWorkResult> updateAlarm(@Body RequestBody requestBody);

    //更新闹钟状态
    @POST(Api.UPDATE_ALARM_STATUS)
    Observable<NetWorkResult> updateAlarmStatus(@Body RequestBody requestBody);

    /* -----------------------------------------录音相关----------------------------------------------*/

    //通过拥有者查询录音
    @GET(Api.QUERY_RECORD_OWN)
    Observable<NetWorkResult> queryRecord(@Query("ownerid") String ownerid);

    //通过录音ID查询录音
    @GET(Api.QUERY_RECORD_ID)
    Observable<NetWorkResult> queryRecordById(@Query("id") String id);

    //添加录音
    @POST(Api.ADD_RECORD)
    Observable<NetWorkResult> addRecord(@Body RequestBody requestBody);

    //删除录音
    @POST(Api.DELETE_RECORD)
    Observable<NetWorkResult> deleteRecord(@Body RequestBody requestBody);

    //更新录音
    @POST(Api.UPDATE_RECORD)
    Observable<NetWorkResult> updateRecord(@Body RequestBody requestBody);


    /* -----------------------------------------投送播放记录相关----------------------------------------------*/

    //查询投送记录列表
    @GET(Api.QUERY_DELIVER_RECORD)
    Observable<NetWorkResult> queryDeliverRecordList(@Query("sourceid") String sourceid, @Query("targetid") String targetid, @Query("pages") String pages);

    //添加投送记录
    @POST(Api.ADD_DELIVER_RECORD)
    Observable<NetWorkResult> addDeliverRecord(@Body RequestBody requestBody);

    //清空投送记录
    @GET(Api.CLEAN_DELIVER_RECORD)
    Observable<NetWorkResult> cleanDeliverRecord(@Query("sourceid") String sourceid, @Query("targetid") String targetid);

    //查询播放记录列表
    @GET(Api.QUERY_PLAY_RECORD)
    Observable<NetWorkResult> queryPlayRecordList(@Query("ownerid") String ownerid, @Query("pages") String pages);

    //添加播放记录
    @POST(Api.ADD_PLAY_RECORD)
    Observable<NetWorkResult> addPlayRecord(@Body RequestBody requestBody);

    //清空播放记录
    @GET(Api.CLEAN_PLAY_RECORD)
    Observable<NetWorkResult> cleanPlayRecord(@Query("ownerid") String ownerid);


    /* -----------------------------------------搜索内容相关----------------------------------------------*/
    //搜索内容
//    @POST(Api.SEARCH_CONTENT)
//    Observable<NetWorkResult> searchContent(@Query("customerid") String customerid, @Body RequestBody requestBody);

    @POST(Api.SEARCH_CONTENT)
    Observable<NetWorkResult> searchContent(@Body RequestBody requestBody);

    //搜索内容(不包含媒体)
    @POST(Api.SEARCH_CONTENT_WITHOUT_MEDIA)
    Observable<NetWorkResult> searchContentWithoutMedia(@Query("customerid") String customerid, @Body RequestBody requestBody);


    //搜索内容(不包含媒体)(分页查询)
    @POST(Api.SEARCH_CONTENT_WITHOUT_MEDIA)
    Observable<NetWorkResult> searchContentWithoutMedia(@Query("customerid") String customerid,@Query("pages") String pages, @Body RequestBody requestBody);


    @GET(Api.SEARCH_CONTENT_BY_LAYER)
    Observable<NetWorkResult> searchContentByLayer(@Query("scenario") String scenario, @Query("mynodeid") String mynodeid);

    //根据内容ID搜索内容
    @GET(Api.QUERY_KEYWORD)
    Observable<NetWorkResult> queryKeyWord();


    //根据内容ID搜索内容
    @GET(Api.QUERY_CONTENT_BY_ID)
    Observable<NetWorkResult> queryContentById(@Query("customerid") String customerid, @Query("contentid") String contentid);

    //根据MediaID搜索内容
    @GET(Api.QUERY_MEDIA_BY_ID)
    Observable<NetWorkResult> queryMediaById(@Query("customerid") String customerid, @Query("mediaid") String mediaid);

    //根据Info 和 infotype 查询内容
    @POST(Api.QUERY_CONTENT_BY_INFO)
    Observable<NetWorkResult> queryContentByInfo(@Body RequestBody requestBody);

    @GET(Api.QUERY_CONTENT_CATEGORY)
    Observable<NetWorkResult> queryContentCategory();

    /* -----------------------------------------课表相关----------------------------------------------*/
    //创建课表
    @POST(Api.CREATE_SCHEDULE)
    Observable<NetWorkResult> createSchedule(@Body RequestBody requestBody);

    //更新课表
    @POST(Api.UPDATE_SCHEDULE)
    Observable<NetWorkResult> updateSchedule(@Body RequestBody requestBody);

    //查询课程信息
    @GET(Api.QUERY_MY_SCHEDULE)
    Observable<NetWorkResult> queryMySchedule(@Query("authorid") String customerid);

    //删除课程表
    @GET(Api.DELETE_MY_SCHEDULE)
    Observable<NetWorkResult> deleteMySchedule(@Query("coursescheduleid") String coursescheduleid);

    //发布课程表到机器人
    @POST(Api.PUBLISH_SCHEDULE)
    Observable<NetWorkResult> publishSchedule(@Body RequestBody requestBody);

    //查询发布的课程表
    @GET(Api.QUERY_PUBLISH_SCHEDULE)
    Observable<NetWorkResult> queryPublishSchedule(@Query("targetid") String targetid);


    //删除发布到机器人的课程表
    @GET(Api.DELETE_PUBLISH_SCHADULE)
    Observable<NetWorkResult> deletePublishSchedule(@Query("targetid") String targetid, @Query("coursescheduleid") String coursescheduleid);


    /* -----------------------------------------制卡相关----------------------------------------------*/
    //制卡
    @POST(Api.ADD_NFC)
    Observable<NetWorkResult> makeNfcCard(@Body RequestBody requestBody);

    //查询制卡
    @GET(Api.QUERY_NFC)
    Observable<NetWorkResult> queryMyCard(@Query("customerid") String customerid);

    //查询制卡
    @GET(Api.QUERY_NFC_BY_CODE)
    Observable<NetWorkResult> queryMyCardbyCode(@Query("nfcid") String nfcid);

    //删除制卡
    @GET(Api.DELECT_NFC)
    Observable<NetWorkResult> deleteNfc(@Query("customerid") String customerid, @Query("nfcid") String nfcid);


    /* ------------------------------------------用户信息----------------------------------------------*/
    //上传孩子信息
    @POST(Api.UPLOAD_BABY_INFO)
    Observable<NetWorkResult> uploadBabyInfo(@Body RequestBody requestBody);

    //查询孩子信息
    @GET(Api.QUERY_BABY_INFO)
    Observable<NetWorkResult> queryBabyInfo(@Query("customerid") String customerid);

    //上传用户信息
    @POST(Api.UPLOAD_USER_INFO)
    Observable<NetWorkResult> uploadUserInfo(@Body RequestBody requestBody);

    //查询用户信息
    @GET(Api.QUERY_USER_INFO)
    Observable<NetWorkResult> queryUserInfo(@Query("customerid") String customerid);

    //推送消息
    @POST(Api.PUSH_MESSAGE)
    Observable<NetWorkResult> pushMessage(@Query("channelid") String channelid,@Query("vendor") String vendor, @Body RequestBody requestBody);


    //------------------------------------------联系人----------------------------------------------

    //删除联系人
    @GET(Api.DELETE_CONTACT)
    Observable<NetWorkResult> deleteContact(@Query("ownerid") String ownerid, @Query("contactid") String contactid);//拥有者，terminalid或者customerid

    //增加联系人
    @POST(Api.ADD_CONTACT)
    Observable<NetWorkResult> addContact(@Body RequestBody requestBody);

    //更新联系人
    @POST(Api.UPDATE_CONTACT)
    Observable<NetWorkResult> updateContact(@Query("ownerid") String ownerid, @Query("contactid") String contactid, @Body RequestBody requestBody);

    //查询联系人
    @GET(Api.QUERT_CONTACT)
    Observable<NetWorkResult> queryContact(@Query("ownerid") String ownerid);//拥有者，terminalid或者customerid

    //------------------------------------------收藏----------------------------------------------

    //创建收藏夹
    @GET(Api.CREATE_CLIP)
    Observable<NetWorkResult> createClip(@Query("ownerid") String ownerid, @Query("category") String category);//拥有者，terminalid或者customerid

    //删除收藏
    @GET(Api.DELETE_COLLECTION)
    Observable<NetWorkResult> deleteCollection(@Query("ownerid") String ownerid, @Query("favoriteid") String favoriteid);//拥有者，terminalid或者customerid

    //删除收藏夹
    @GET(Api.DELETE_CLIP)
    Observable<NetWorkResult> deleteClip(@Query("ownerid") String ownerid, @Query("category") String category);//拥有者，terminalid或者customerid

    //收藏夹改名
    @GET(Api.RENAME_CLIP)
    Observable<NetWorkResult> renameClip(@Query("ownerid") String ownerid, @Query("oldcategory") String oldcategory, @Query("newcategory") String newcategory);//拥有者，terminalid或者customerid

    //更新收藏
    @POST(Api.UPDATE_COLLECTION)
    Observable<NetWorkResult> updateCollection(@Body RequestBody requestBody);

    //查询的收藏
    @GET(Api.QUERY_MY_COLLECTION)
    Observable<NetWorkResult> queryMyCollection(@Query("ownerid") String ownerid, @Query("category") String category);//拥有者，terminalid或者customerid

    //查询的收藏夹
    @GET(Api.QUERY_MY_CLIP)
    Observable<NetWorkResult> queryMyClip(@Query("ownerid") String ownerid);//拥有者，terminalid或者customerid

    //添加收藏
    @POST(Api.ADD_COLLECTION)
    Observable<NetWorkResult> addCollection(@Body RequestBody requestBody);

    //------------------------------------------广场----------------------------------------------


    //查询广场论坛
    @GET(Api.QUERY_SQUARE)
    Observable<NetWorkResult> querySquare();

    //查询主题
    @GET(Api.QUERY_FORUM)
    Observable<NetWorkResult> queryForum(@Query("forum_id") String forum_id, @Query("customerid") String customerid, @Query("pages") String pages);

    //创建主题（发帖子）
    @POST(Api.CREATE_TOPIC)
    Observable<NetWorkResult> createTopic(@Body RequestBody requestBody);

    //删除主题
    @GET(Api.DELETE_TOPIC)
    Observable<NetWorkResult> deleteTopic(@Query("topic_id") String topic_id);

    //点赞和取消点赞
    @POST(Api.LIKE)
    Observable<NetWorkResult> like(@Body RequestBody requestBody);

    //查询我的分享
    @GET(Api.UNLIKE)
    Observable<NetWorkResult> unlike(@Query("like_id") String like_id);

    //查询我的分享
    @GET(Api.QUERY_MY_SHARE)
    Observable<NetWorkResult> queryMyShare(@Query("creatorid") String creatorid);

    //------------------------------------------产品相关----------------------------------------------

    @GET(Api.ABOUT_PRODUCT)
    Observable<NetWorkResult> aboutProduct(@Query("host") String host, @Query("devicetype") String devicetype,
                                           @Query("os") String os, @Query("osversion") String osversion, @Query("lang") String lang,
                                           @Query("vendor") String vendor,@Query("appversion") String appversion);


    class Api {

        //        public static final String BASE_URL = "http://192.168.1.200:8080/";  // 测服
        public static final String BASE_URL = "http://cloud.kidknow.net:8080/";  // 正服


        /*------------------------------------------应用相关----------------------------------------------*/
        //检查最新版本
        static final String UPDATE_VERSINO = "/proxy/sysMaintain/uptodateversion";
        //获取OSS key
        static final String GET_OSSKEY = "/proxy/security/querykeyonoss";
        //用户反馈
        static final String FEEDBACK = "/proxy/marketCustomerCare/customercomplain";

        /*------------------------------------------登录注册修改密码----------------------------------------------*/
        //自动登录
        static final String AUTOLOGIN = "/api/user/auto/LoginPresenter";
        //退出登陆
        static final String LOGOUT_APP = "/api/user/logout";
        //用户注册
        static final String USER_REGISTER = "/proxy/socialgraph/createcustomer";
        //用户登录
        static final String USER_LOGIN = "/proxy/socialgraph/login";
        //获取验证码
        static final String GET_CODE = "/proxy/sms/sendsms";
        //修改密码
        static final String MODIFY_PASSWORD = "/proxy/socialgraph/modifypasswd";

        /* -----------------------------------------内容相关----------------------------------------------*/
        //删除内容
        static final String DELETE_CONTENT = "/proxy/contentIndex/deletecontent";
        //搜索内容
        static final String SEARCH_CONTENT = "/proxy/contentIndex/querycontent";
        //搜索不包含media的内容
        static final String SEARCH_CONTENT_WITHOUT_MEDIA = "/proxy/contentIndex/querycontent_without_media";
        //根据层级查询内容
        static final String SEARCH_CONTENT_BY_LAYER = "/proxy/contentIndex/getChildrenTreeNodeBy1Level";
        //查询所有关键字
        static final String QUERY_KEYWORD = "/proxy/contentIndex/queryallkeywords";
        //根据内容ID查询内容
        static final String QUERY_CONTENT_BY_ID = "/proxy/contentIndex/querycontentByContentid";
        //根据mediaId查询内容
        static final String QUERY_MEDIA_BY_ID = "/proxy/contentIndex/querycontentByMediaid";
        //根据ID和类型查询内容
        static final String QUERY_CONTENT_BY_INFO = "/proxy/compositeagent/batchQueryCourseScheduleDetail";
        //查询内容分类
        static final String QUERY_CONTENT_CATEGORY = "/proxy/contentIndex/getContentCategory";

        /* -----------------------------------------制卡相关----------------------------------------------*/
        //通过扫描机器人ID二维码绑定机器人
        static final String BIND_DEVICE = "/proxy/socialgraph/bindrelationbydeviceid";
        //解绑机器人
        static final String UNBIND_DEVICE = "/proxy/socialgraph/unbindrelation";
        //制卡
        static final String ADD_NFC = "/proxy/NFC/addnfc";
        //查询制卡
        static final String QUERY_NFC = "/proxy/NFC/querymynfc";
        //根据条码查询制卡
        static final String QUERY_NFC_BY_CODE = "/proxy/NFC/querynfcbyid";
        //删除制卡
        static final String DELECT_NFC = "/proxy/NFC/deletenfc";

        /* -----------------------------------------机器人相关----------------------------------------------*/
        //获取机器人最新信息
        static final String GET_LAST_INFO = "/proxy/deviceHome/getlastfootprint";
        //查询我绑定的机器人关系
        static final String QUERY_DEVICE_RELATION = "/proxy/socialgraph/queryrelation";
        //按deviceid查询关系
        static final String QUERY_DEVICE_RELATION_BY_DEVICEID = "/proxy/socialgraph/queryrelationbydeviceid";
        //重命名绑定的机器人
        static final String RENAME_BINDNAME = "/proxy/socialgraph/rename_bindname";

        /* -----------------------------------------闹钟相关----------------------------------------------*/
        //查询闹钟
        static final String QUERT_ALARM = "/proxy/deviceHome/queryTimerAlarm";
        //删除闹钟
        static final String DELETE_ALARM = "/proxy/deviceHome/deleteTimerAlarm";
        //添加闹钟
        static final String ADD_ALARM = "/proxy/deviceHome/addTimerAlarm";
        //更新闹钟
        static final String UPDATE_ALARM = "/proxy/deviceHome/updateTimerAlarm";
        //更新闹钟状态
        static final String UPDATE_ALARM_STATUS = "/proxy/deviceHome/updateTimerAlarmStatus";

        /* -----------------------------------------录音相关----------------------------------------------*/
        //根据所有者查询录音
        static final String QUERY_RECORD_OWN = "/proxy/voiceRecording/queryVoiceRecordingByOwner";
        //根据录音ID查询录音
        static final String QUERY_RECORD_ID = "/proxy/voiceRecording/queryVoiceRecordingByID";
        //添加录音
        static final String ADD_RECORD = "/proxy/voiceRecording/addVoiceRecording";
        //删除录音
        static final String DELETE_RECORD = "/proxy/voiceRecording/deleteVoiceRecording";
        //更新录音
        static final String UPDATE_RECORD = "/proxy/voiceRecording/updateVoiceRecording";

        /* -----------------------------------------投送播放记录相关----------------------------------------------*/
        //查询投送记录列表
        static final String QUERY_DELIVER_RECORD = "/proxy/AirDropPlaylist/queryAirdrop";
        //添加投记录
        static final String ADD_DELIVER_RECORD = "/proxy/AirDropPlaylist/addAirdrop";
        //清空投送记录
        static final String CLEAN_DELIVER_RECORD = "/proxy/AirDropPlaylist/clearAirdrop";
        //查询播放记录列表
        static final String QUERY_PLAY_RECORD = "/proxy/AirDropPlaylist/queryPlaylist";
        //添加播放记录
        static final String ADD_PLAY_RECORD = "/proxy/AirDropPlaylist/addPlaylist";
        //清空播放记录
        static final String CLEAN_PLAY_RECORD = "/proxy/AirDropPlaylist/clearPlaylist";

        /* -----------------------------------------课表相关----------------------------------------------*/
        //创建课表
        static final String CREATE_SCHEDULE = "/proxy/courseSchedule/createCourseScheule";
        //查询我的课表
        static final String QUERY_MY_SCHEDULE = "/proxy/courseSchedule/queryMyCourseSchedule";
        //删除我的课表
        static final String DELETE_MY_SCHEDULE = "/proxy/courseSchedule/deleteCourseSchedule";
        //更新课程表
        static final String UPDATE_SCHEDULE = "/proxy/courseSchedule/updateCourseSchedule";
        //发布课表到机器人
        static final String PUBLISH_SCHEDULE = "/proxy/courseSchedule/publishCourseSchedule";
        //查询发布的课程表
        static final String QUERY_PUBLISH_SCHEDULE = "/proxy/courseSchedule/queryPublishedCourseSchedule";
        //删除发布到机器人的课程表
        static final String DELETE_PUBLISH_SCHADULE = "/proxy/courseSchedule/unpublishCourseSchedule";

        /* -----------------------------------------用户信息----------------------------------------------*/
        //上传孩子信息(没用到)
        static final String UPLOAD_BABY_INFO = "/proxy/customerHome/updatechildinfo";
        //查询孩子信息(没用到)
        static final String QUERY_BABY_INFO = "/proxy/customerHome/querychildinfo";
        //上传用户信息
        static final String UPLOAD_USER_INFO = "/proxy/customerHome/updateAllSetting";
        //查询用户信息
        static final String QUERY_USER_INFO = "/proxy/customerHome/queryAllSetting";
        //推送消息
        static final String PUSH_MESSAGE = "/proxy/channelMsgPush/pushmsg/";

        //------------------------------------------联系人----------------------------------------------
        //删除联系人
        static final String DELETE_CONTACT = "/proxy/contactBook/deletecontact";
        //增加联系人
        static final String ADD_CONTACT = "/proxy/contactBook/addcontact";
        //更新联系人
        static final String UPDATE_CONTACT = "/proxy/contactBook/updatecontact";
        //查询联系人
        static final String QUERT_CONTACT = "/proxy/contactBook/querycontact";

        //------------------------------------------收藏----------------------------------------------
        //创建收藏夹
        static final String CREATE_CLIP = "/proxy/customerHome/createFavoriteCategory";
        //删除收藏
        static final String DELETE_COLLECTION = "/proxy/customerHome/deleteFavorite";
        //删除收藏夹
        static final String DELETE_CLIP = "/proxy/customerHome/deleteFavoriteCategory";
        //收藏夹改名
        static final String RENAME_CLIP = "/proxy/customerHome/renameFavoriteCategory";
        //更新收藏
        static final String UPDATE_COLLECTION = "/proxy/customerHome/updateFavorite";
        //查询我的收藏夹
        static final String QUERY_MY_CLIP = "/proxy/customerHome/queryFavoriteCategory";
        //查询我的收藏
        static final String QUERY_MY_COLLECTION = "/proxy/customerHome/queryFavorite";
        //添加收藏
        static final String ADD_COLLECTION = "/proxy/customerHome/addFavorite";

        //------------------------------------------广场----------------------------------------------
        //查询广场（主题），获取ForumId
        static final String QUERY_SQUARE = "/proxy/forum/queryForum_Official";
        //查询主题
        static final String QUERY_FORUM = "/proxy/forum/queryTopicByForum";
        //创建主题（发帖子）
        static final String CREATE_TOPIC = "/proxy/forum/createTopic";
        //删除主题（删除帖子）
        static final String DELETE_TOPIC = "/proxy/forum/deleteTopic";
        //点赞
        static final String LIKE = "/proxy/forum/like";
        //取消点赞
        static final String UNLIKE = "/proxy/forum/unlike";
        //查询我的分享
        static final String QUERY_MY_SHARE = "/proxy/forum/queryTopicByCreatorID";

        //----------------------------------------产品相关------------------------------------------------

        //关于
        static final String ABOUT_PRODUCT = "/proxy/sysMaintain/gettechnicalsupportinfo";

    }
}
