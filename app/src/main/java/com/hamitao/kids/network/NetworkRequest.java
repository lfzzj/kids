package com.hamitao.kids.network;


import android.util.ArrayMap;

import com.google.gson.Gson;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.CommonParams;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.RxUtils;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.SystemUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.model.requestmodel.AlarmRequestModel;
import com.hamitao.kids.model.requestmodel.ContentInfoRequestModel;
import com.hamitao.kids.model.requestmodel.CreateScheduleRequestModel;
import com.hamitao.kids.model.requestmodel.NfcRequestModel;
import com.hamitao.kids.model.requestmodel.UpdateScheduleRequestModel;
import com.hamitao.kids.model.scheduler.PublishScheduleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.hamitao.base_module.network.CommonParams.commonParam;


/**
 * Created by linjianwen on 2018/1/5.
 */

public class NetworkRequest {

    //observable  被观察者
    //observer    观察者

    private static <T> void addObservable(Observable<T> observable, Observer<T> observer) {

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }



    /**
     * 停止所有请求
     */
    public static void stop() {
        RxUtils.getInstance().unSubscription();
    }


    /**
     * 检查最新版本
     */
    public static void updateVersionRequest(NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("host", "mobile"); //
        map.put("devicetype", "H1");//
        map.put("os", "android");//用户手机类型:android
        map.put("osversion", SystemUtil.getOSVersion());//手机系统版本
        map.put("lang", SystemUtil.getLanguage());//手机语言
        map.put("appversion", Constant.versionName);//版本控制
        if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())){
            map.put("vendor", Constant.XIAOSHUAI);
        }
        addObservable(NetWork.getApi().updateVersion(map), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 用户反馈
     */
    public static void feedbackRequest(String customerid, String customername, String contact1, String contact2, String text, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("customerid", customerid);
        map.put("customername", customername);
        map.put("contact1", contact1);
        map.put("contact2", contact2);
        map.put("text", text);
        addObservable(NetWork.getApi().feedback(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 用户登录
     */
    public static void loginRequest(String loginname, String password, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("loginname", loginname);
        map.put("password", password);
        addObservable(NetWork.getApi().login(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 用户注册
     *
     * @param netWorkCallBack
     */
    public static void registerRequest(String loginname, String password, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("loginname", loginname);
        map.put("password", password);
        addObservable(NetWork.getApi().register(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 获取验证码
     */
    public static void getCodeRequest(String phoneNum, String code, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().getCode(phoneNum, code), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 修改密码:(当忘记密码调用该接口时，oldPsw 该参数请填万能密码为： 登录账号+"&Hamitao%987"))
     */
    public static void modifyPswRequest(String loginname, String oldpassword, String newpassword, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("loginname", loginname);
        map.put("oldpasswd", oldpassword);
        map.put("newpasswd", newpassword);
        addObservable(NetWork.getApi().modifyPassword(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 获取OSS KEY
     */
    public static void getOssKeyRequest(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().getOssKey(commonParam()), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 通过机器人ID绑定机器人
     */
    public static void bindDeviceRequest(String bindName, String devicename, String customerid, String deviceId, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("bindname_of_customer", bindName);
        map.put("bindname_of_device", devicename);
        map.put("customerid", customerid);
        map.put("deviceid", deviceId);
        addObservable(NetWork.getApi().bindDevice(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 关键字查询内容, 如果关键字为空，则是查询首页推荐内容
     */
    public static void searchContentRequest(String[] keyWord, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("keywordList", keyWord);
        addObservable(NetWork.getApi().searchContent(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


 /*   public static void queryContent(String costomerid, String type, String[] keyWord, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put(type, keyWord);
        addObservable(NetWork.getApi().searchContent(costomerid, CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }
*/

    public static void queryContentWithoutMedia(String costomerid, String type, String[] keyWord, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put(type, keyWord);
        addObservable(NetWork.getApi().searchContentWithoutMedia(costomerid, CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 分页查询不含媒体的内容
     * @param costomerid
     * @param type
     * @param keyWord
     * @param pages
     * @param netWorkCallBack
     */
    public static void queryContentWithoutMediaWithPages(String costomerid, String type, String[] keyWord,String pages, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put(type, keyWord);
        addObservable(NetWork.getApi().searchContentWithoutMedia(costomerid,pages, CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 分层查询内容
     *
     * @param scenario 关键字
     * @param mynodeid 子节点名称
     */
    public static void quertContentByLayerRequest(String scenario, String mynodeid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().searchContentByLayer(scenario, mynodeid), netWorkCallBack.getNetWorkSubscriber());
    }


    //通过年龄查询内容
    public static void queryContentByAge(String costomerid, int age_min, int age_max,String pages, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        Map<String, Object> orient = new ArrayMap<>();
        orient.put("age_max", age_max);
        orient.put("age_min", age_min);
        orient.put("sex", "any");
        map.put("orientUser", orient);
        addObservable(NetWork.getApi().searchContentWithoutMedia(costomerid, pages,CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    public static void queryKeywordRequest(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryKeyWord(), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 根据内容ID查询内容
     */
    public static void queryContentByIdRequest(String customerid, String contentid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryContentById(customerid, contentid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 根据MediaId查询音乐
     */
    public static void queryMediaByIdRequest(String customerid, String mediaid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMediaById(customerid, mediaid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 根据 info 和 infotype
     *
     * @param netWorkCallBack
     */
    public static void queryContentByInfoRequest(List<ContentInfoRequestModel> models, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryContentByInfo(RequestBody.create(MediaType.parse("application/json"), GsonUtil.GsonToString(models))), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询内容分类
     */
    public static void queryContentCategortRequest(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryContentCategory(), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 获取机器人信息
     */
    public static void getDeviceInfoRequest(String deviceId, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("deviceid", deviceId);
        addObservable(NetWork.getApi().getDeviceInfo(map), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询机器人绑定关系
     */
    public static void queryDeviceRelationRequest(String customerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryDeviceRelation(customerid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 根据deviceid查询关系
     */
    public static void queryDeviceRelationByDeviceidRequest(String deviceid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryDeviceRelationByDeviceid(deviceid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 重命名绑定机器人的名称
     *
     * @param id_of_peer1       第一方
     * @param id_of_peer2       第二方
     * @param bindname_of_peer1 用户端的名称（不修改则填空
     * @param bindname_of_peer2 机器人端的名称（不修改则填空
     * @param netWorkCallBack
     */
    public static void renameDeviceNameRequest(String id_of_peer1, String id_of_peer2, String bindname_of_peer1, String bindname_of_peer2, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("id_of_peer1", id_of_peer1);
        map.put("id_of_peer2", id_of_peer2);
        map.put("bindname_of_peer1", bindname_of_peer1);
        map.put("bindname_of_peer2", bindname_of_peer2);
        addObservable(NetWork.getApi().renameDevice(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 解除绑定机器人
     */
    public static void unbindDeviceRequest(String sourcePeer, String targetPeer, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("id_of_peer1", sourcePeer);
        map.put("id_of_peer2", targetPeer);
        addObservable(NetWork.getApi().unbindDevice(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 上传孩子信息
     */
    public static void uploadBabyInfoRequest(String babyName, String babySex, String babyBirthday, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        Map<String, Object> info = new ArrayMap<>();
        info.put("birthday", babyBirthday);
        info.put("name", babyName);
        info.put("sex", babySex);
        map.put("childInfo", info);
        addObservable(NetWork.getApi().uploadBabyInfo(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询孩子信息
     */
    public static void queryBabyInfoRequest(String customerid, NetWorkCallBack netWorkCallBack) {


        addObservable(NetWork.getApi().queryBabyInfo(customerid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询用户信息
     */
    public static void queryUserInfoRequest(String customerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryUserInfo(customerid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 上传用户信息
     */
    public static void uploadUserInfoRequest(String faceUrl, String nickName, String babyName, String babySex, String babyBirthday, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        Map<String, Object> babyInfo = new ArrayMap<>();
        babyInfo.put("birthday", babyBirthday);
        babyInfo.put("name", babyName);
        babyInfo.put("sex", babySex);
        Map<String, Object> myInfo = new ArrayMap<>();
        myInfo.put("myheaderpic", faceUrl);
        myInfo.put("mynickname", nickName);
        map.put("childInfo", babyInfo);
        map.put("myInfo", myInfo);

        addObservable(NetWork.getApi().uploadUserInfo(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 上传个人信息
     */
    public static void uploadPartUserinfoRequest(String faceUrl, String nickName, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        Map<String, Object> myInfo = new ArrayMap<>();
        myInfo.put("myheaderpic", faceUrl);
        myInfo.put("mynickname", nickName);
        map.put("myInfo", myInfo);
        addObservable(NetWork.getApi().uploadUserInfo(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 推送消息请求
     */
    public static void pushMessageRequest(String targetChannelid, String action, String[] contentid, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("source_channelid", UserUtil.user().getChannelid_listen_on_pushserver());
        map.put("target_channelid", targetChannelid);
        Map<String, Object> remoteControlCommandsMap = new ArrayMap<>();
        Map<String, Object> contentMap = new ArrayMap<>();
        contentMap.put("contentid", contentid);
        remoteControlCommandsMap.put("action", action);
        remoteControlCommandsMap.put("seqid", 0);
        remoteControlCommandsMap.put("contents", contentMap);
        map.put("remoteControlCommands", remoteControlCommandsMap);
        addObservable(NetWork.getApi().pushMessage(targetChannelid,BaseApplication.getInstance().getVendor(),CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 制卡
     */
    public static void makeCardRequest(NfcRequestModel model, NetWorkCallBack netWorkCallBack) {

        addObservable(NetWork.getApi().makeNfcCard(RequestBody.create(MediaType.parse("application/json"), GsonUtil.GsonToString(model))), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询制卡
     */
    public static void queryMyCardRequest(String cusromerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMyCard(cusromerid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 根据条码查询制卡
     */
    public static void queryMyCardByCodeRequest(String nfcid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMyCardbyCode(nfcid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 删除制卡
     */
    public static void deleteCardRequest(String cusromerid, String nfcid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteNfc(cusromerid, nfcid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询课程信息
     */
    public static void queryMyScheduleRequest(String customerId, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMySchedule(customerId), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 创建课表
     */
    public static void crateScheduleRequest(CreateScheduleRequestModel model, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().createSchedule(RequestBody.create(MediaType.parse("application/json"), GsonUtil.GsonToString(model))), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 更新课程表
     */
    public static void updateScheduleRequest(UpdateScheduleRequestModel model, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().updateSchedule(RequestBody.create(MediaType.parse("application/json"), GsonUtil.GsonToString(model))), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 发布课程表到机器人
     */
    public static void publishScheduleRequest(PublishScheduleModel model, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().publishSchedule(RequestBody.create(MediaType.parse("application/json"), GsonUtil.GsonToString(model))), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询发布的课程表
     */
    public static void queryPublishScheduleRequest(String targetid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryPublishSchedule(targetid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除发布到机器人的课程表
     */
    public static void deletePublishScheduleRequest(String targetid, String coursescheduleid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deletePublishSchedule(targetid, coursescheduleid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除课表
     */
    public static void deleteScheduleRequest(String coursescheduleid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteMySchedule(coursescheduleid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除联系人
     */
    public static void deleteContactRequest(String customerid, String contactid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteContact(customerid, contactid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 添加联系人
     */
    public static void addContactRequest(String customerid, String contactName, String phoneNum, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = commonParam();
        map.put("contactid", "");
        map.put("contactname", contactName);
        map.put("contacttype", "tel");
        map.put("creator", "bbb");
        map.put("ownerid", customerid);
        map.put("phonenum", phoneNum);
        map.put("shortnum", "9876");
        map.put("source", "aaa");
        addObservable(NetWork.getApi().addContact(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 更新联系人
     */
    public static void updateContactRequest(String customerid, String contactid, String contactName, String phoneNum, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("contactid", contactid);
        map.put("contactname", contactName);
        map.put("contacttype", "");
        map.put("creator", "");
        map.put("ownerid", customerid);
        map.put("phonenum", phoneNum);
        map.put("shortnum", "");
        map.put("source", "");
        addObservable(NetWork.getApi().updateContact(customerid, contactid, CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询联系人
     */
    public static void queryContactRequest(String customerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryContact(customerid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询闹钟
     */
    public static void queryAlarmRequest(String terminalid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryAlarm(terminalid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除闹钟
     */
    public static void deleteAlarmRequest(String terminalid, String timerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteAlarm(terminalid, timerid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 添加闹钟
     */

    public static void addAlarmRequest(String label, String creator, String terminalid, String day, String starttime, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> timeMap = new ArrayMap<>();
        timeMap.put("day", day);
        timeMap.put("starttime", starttime);
        List<Map> list = new ArrayList<>();
        list.add(timeMap);
        Map<String, Object> map = new ArrayMap<>();
        map.put("ownerid", terminalid);
        map.put("name", label);
        map.put("status", "enable");
        map.put("creator", creator);
        map.put("timers", list);
        addObservable(NetWork.getApi().addAlarm(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * @param ownerid 拥有者，这里指 terminalid
     * @param id      闹钟的唯一ID
     * @Param status 闹钟的是否启用
     */
    public static void updateAlarmRequest(String ownerid, String id, String idx, String name, String status, List<AlarmRequestModel.TimersBean> bean, NetWorkCallBack netWorkCallBack) {
        AlarmRequestModel model = new AlarmRequestModel();
        model.setId(id);
        model.setIdx(idx);
        model.setName(name);
        model.setOwnerid(ownerid);
        model.setStatus(status);
        model.setTimers(bean);
        String string = new Gson().toJson(model);
        addObservable(NetWork.getApi().updateAlarm(RequestBody.create(MediaType.parse("application/json"), string)), netWorkCallBack.getNetWorkSubscriber());
    }

    public static void updateAlarmInfoRequest() {

    }

    /**
     * 改变闹钟状态
     *
     * @param ownerid
     * @param id
     * @param status
     * @param netWorkCallBack
     */
    public static void changeAlarmStatusRequest(String ownerid, String name, String id, String status, NetWorkCallBack netWorkCallBack) {
        AlarmRequestModel model = new AlarmRequestModel();
        model.setId(id);
        model.setName(name);
        model.setStatus(status);
        model.setOwnerid(ownerid);
        String string = new Gson().toJson(model);
        addObservable(NetWork.getApi().updateAlarmStatus(RequestBody.create(MediaType.parse("application/json"), string)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 根据所有者查询录音信息
     */
    public static void queryRecordRequest(String ownerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryRecord(ownerid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 根据录音ID查询录音信息
     */
    public static void queryRecordByIdRequest(String id, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryRecordById(id), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 添加录音
     */
    public static void addRecordRequest(String description, String id, String name, String ownerid, String ownername, String selectStatus, String url, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("description", description);
        map.put("id", id);
        map.put("name", name);
        map.put("ownerid", ownerid);
        map.put("ownername", ownername);
        map.put("selectStatus", selectStatus);
        map.put("url", url);
        addObservable(NetWork.getApi().addRecord(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 更新录音
     */
    public static void updatRecordRequest(String description, String id, String name, String ownerid, String ownername, String selectStatus, String url, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("description", description);
        map.put("id", id);
        map.put("name", name);
        map.put("ownerid", ownerid);
        map.put("ownername", ownername);
        map.put("selectStatus", selectStatus);
        map.put("url", url);
        addObservable(NetWork.getApi().updateRecord(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除录音
     */
    public static void deleteRecordRequest(String[] id, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("IDs", id);
        addObservable(NetWork.getApi().deleteRecord(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询投送记录
     */
    public static void queryDeliverRequest(String sourceid, String targetid, String pages, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryDeliverRecordList(sourceid, targetid, pages), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 添加投送记录
     */
    public static void addDeliverRequest(String creator, String description, String infotype, String info, String title, String targetid, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("creator", creator);
        map.put("description", description);
        map.put("infotype", infotype);
        map.put("info", info);
        map.put("sourceid", creator);
        map.put("targetid", targetid);
        map.put("title", title);
        addObservable(NetWork.getApi().addDeliverRecord(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 清空投送记录
     */
    public static void cleanDeliverRequest(String sourceid, String targetid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().cleanDeliverRecord(sourceid, targetid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询播放记录列表
     */
    public static void queryPlayRecordRequest(String ownid, String pages, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryPlayRecordList(ownid, pages), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 添加播放记录
     */
    public static void addPlayRecordRequest(String ownid, String infotype, String info, String auxinfo, String title, String headerimgurl, String description, String creator, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("ownerid", ownid);
        map.put("infotype", infotype);
        map.put("info", info);
        map.put("auxinfo", auxinfo);
        map.put("headerimgurl", headerimgurl);
        map.put("title", title);
        map.put("description", description);
        map.put("creator", creator);

        addObservable(NetWork.getApi().addPlayRecord(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 清空播放记录
     */
    public static void cleanPlayListRequest(String ownerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().cleanPlayRecord(ownerid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 创建收藏夹
     */
    public static void createClipRequest(String customerId, String clipName, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().createClip(customerId, clipName), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 删除收藏夹
     */
    public static void deleteClipRequest(String customerid, String category, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteClip(customerid, category), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 删除收藏
     */
    public static void deleteCollectionRequest(String customerid, String favoriteid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteCollection(customerid, favoriteid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 收藏夹改名
     */
    public static void renameClipRequest(String customerid, String oldcategory, String newcategory, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().renameClip(customerid, oldcategory, newcategory), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 更新收藏
     */
    public static void updateCollectionRequest(String ownerid, String favoriteid, String category, String infotype, String info, String title, String description, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("ownerid", ownerid);
        map.put("favoriteid", favoriteid);
        map.put("category", category);
        map.put("infotype", infotype);
        map.put("info", info);
        map.put("title", title);
        map.put("description", description);
        addObservable(NetWork.getApi().updateCollection(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询我的收藏
     */
    public static void queryMyCollectionRequest(String customerid, String category, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMyCollection(customerid, category), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询我的收藏夹
     */
    public static void queryMyClipRequest(String customerid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMyClip(customerid), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 添加收藏
     */
    public static void addCollection(String category, String description, String img, String info, String infotype, String mediatype, String ownerid, String title, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("category", category);
        map.put("description", description);
        map.put("headerimgurl", img);
        map.put("info", info);
        map.put("infotype", infotype);
        map.put("mediatype", mediatype);
        map.put("ownerid", ownerid);
        map.put("title", title);
        addObservable(NetWork.getApi().addCollection(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 查询广场(目前只有一个)
     */
    public static void querySquareRequest(NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().querySquare(), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询主题（看广场帖子）
     */
    public static void queryForumRequest(String forumId, String customerid, String pages, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryForum(forumId, customerid, pages), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 创建主题（发帖子）
     *
     * @param creatorid
     * @param createName
     * @param title
     * @param description
     * @param imgUrl
     * @param forumId     forum_id,哪个论坛下面的主题
     * @param keywords
     * @param infoType    可选值："contentid"/"recordid"/"coursesheduleid" 即 内容id/录音id/课程id
     * @param info        具体 ID
     */
    public static void createTopicRequest(String creatorid, String createName, String title, String description, String imgUrl, String forumId, String[] keywords, String infoType, String info, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("creatorid", creatorid);
        map.put("createName", createName);
        map.put("description", description);
        map.put("forum_id", forumId);
        map.put("headerimgurl", imgUrl);
        map.put("info", info);
        map.put("infoType", infoType);
        map.put("title", title);
        map.put("keywords", keywords);
        addObservable(NetWork.getApi().createTopic(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 删除主题
     *
     * @param topic_id
     * @param netWorkCallBack
     */
    public static void deleteTopicRequest(String topic_id, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().deleteTopic(topic_id), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 点赞
     */
    public static void likeRequest(String creatorid, String createName, String description, String onwhat_infotype, String onwhat_info, NetWorkCallBack netWorkCallBack) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("action", "like");
        map.put("creatorid", creatorid);
        map.put("createName", createName);
        map.put("description", description);
        map.put("onwhat_info", onwhat_info);
        map.put("onwhat_infotype", onwhat_infotype);
        addObservable(NetWork.getApi().like(CommonParams.NetWorkRequestBody(map)), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 取消点赞
     */
    public static void unlikeRequest(String like_id, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().unlike(like_id), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 查询我的分享
     *
     * @param creatorid
     * @param netWorkCallBack
     */
    public static void queryMyShareRequest(String creatorid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().queryMyShare(creatorid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * APP协议信息
     *
     * @param host       宿主机器人：可选项：mobile(手机)/kidrobot(儿童机器人)
     * @param devicetype 产品型号
     * @param os         操作系统
     * @param osversion  手机操作系统版本号
     * @param lang       语言
     * @param appversion APP版本
     */
    public static void aboutProductRequest(String devicetype, String osversion, String lang, String vendor, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getApi().aboutProduct("mobile", devicetype, "android", osversion, lang, vendor,Constant.versionName), netWorkCallBack.getNetWorkSubscriber());
    }


    /**
     * 机器人端视频通讯
     */
    public static void getP2PRequest(String guid, String imei, String wifimac, String groupid, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getVideoApi().getP2P(guid, imei, wifimac, groupid), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * 机器人端释放视频
     */
    public static void releaseP2P(String guid, String token, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getVideoApi().releaseP2P(guid, token), netWorkCallBack.getNetWorkSubscriber());
    }

    /**
     * APP初始化视频通讯
     */
    public static void initP2PRequest(String guid, String token, NetWorkCallBack netWorkCallBack) {
        addObservable(NetWork.getVideoApi().initP2P(guid, token), netWorkCallBack.getNetWorkSubscriber());

    }
}
