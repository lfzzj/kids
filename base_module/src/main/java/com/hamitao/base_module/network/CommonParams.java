package com.hamitao.base_module.network;


import com.hamitao.base_module.Constant;
import com.hamitao.base_module.enums.OsEnum;
import com.hamitao.base_module.model.CustomerInfo.ResponseDataObjBean.CustomerInfoBean;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.SystemUtil;
import com.hamitao.base_module.util.UserUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * 参数类
 */

public class CommonParams {


    /**
     * 所有接口的共有参数
     */
    public static Map<String, Object> commonParam() {
        Map<String, Object> map = new HashMap<>();
        CustomerInfoBean customerInfo = UserUtil.user();
        if (customerInfo != null) {
            map.put("customerid", String.valueOf(customerInfo.getCustomerid()));
            String token = customerInfo.getToken();
     /*       if (StringUtil.isNotBlank(token))
                map.put("token", token);*/
        }
//        map.putAll(commonPhoneInfo());
        return map;
    }


    /**
     * 参数  -> json
     * json ->  RequestBody
     *
     * @return
     */
    public static RequestBody NetWorkRequestBody(Map map) {
        return RequestBody.create(MediaType.parse("application/json"), mapToJson(map));
    }


    /**
     * 集合转为Json
     *
     * @return-
     */
    public static String mapToJson(Map map) {
        JSONObject jsonObject = new JSONObject(map);
        LogUtil.d("request", "请求参数：" + jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 用户未登录的状态下,接口请求用这个参数
     * 手机信息相关参数
     */
    public static Map<String, String> commonPhoneInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("os", OsEnum.ANDROID.getType() + "");//用户手机类型:android
        map.put("osversion", SystemUtil.getOSVersion());//手机系统版本
        map.put("lang", SystemUtil.getLanguage());//手机语言
        map.put("appversion", Constant.versionCode + "");//版本控制
//        map.put("deviceId", SystemUtil.getUDID(BaseApplication.getInstance()));//手机UDID
//        map.put("mac", SystemUtil.getMacAddress(BaseApplication.getInstance()));//手机wifi的Mac地址
//        String appChannel = SystemUtil.getAppMetaData(BaseApplication.getInstance());
//        if (!StringUtil.isBlank(appChannel)) {
//            map.put("channel", appChannel);//渠道名
//        }
//        map.put("deviceName", SystemUtil.getDevice());//手机型号
//        map.put("packId", Constant.PACKAGE_ID);
//        if (BaseApplication.mCurrentLat != default_mCurrent && BaseApplication.mCurrentLong != 0) {
//            map.put("lon", BaseApplication.mCurrentLong + "");//用户经度
//            map.put("lat", BaseApplication.mCurrentLat + "");//用户纬度
//        }
//        map.put("serverIndex", "1");//区分腾讯IM和环信IM
//        map.put("serveVersion", "2");//区分重构新包和旧包
        return map;
    }

}
