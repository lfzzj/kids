package com.hamitao.base_module.util;


import com.hamitao.base_module.model.CustomerInfo;
import com.hamitao.base_module.model.CustomerInfo.ResponseDataObjBean.CustomerInfoBean;

import static com.hamitao.base_module.util.PropertiesUtil.SpKey.Login_Name;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class UserUtil {

    private static final String CUSTOMER_INFO = "customer_info";

    private static CustomerInfoBean user;

    private UserUtil() {
    }

    /**
     * 保存自己的用户资料
     *
     * @param user
     */
    public static void saveUserMine(CustomerInfoBean user, String loginname) {
        if (user != null) {
            UserUtil.user = user;
            PropertiesUtil.getInstance().setString(CUSTOMER_INFO, GsonUtil.GsonToString(user));
            PropertiesUtil.getInstance().setString(Login_Name, loginname);
        }
    }

    /**
     * 获取自己的用户资料
     *
     * @return
     */
    public static CustomerInfoBean user() {
        if (user == null) {
            String userJson = PropertiesUtil.getInstance().getString(CUSTOMER_INFO, "");
            if (StringUtil.isBlank(userJson)){
                return null;
            }
            user = GsonUtil.GsonToBean(userJson, CustomerInfoBean.class);
        }
        return user;
    }

    /**
     * 判断用户资料是否不为null
     *
     * @return true不为null, 反之则为null
     */
    public static boolean isUserNotNull() {
        String userJson = PropertiesUtil.getInstance().getString(CUSTOMER_INFO, "");
        if (StringUtil.isBlank(userJson)) return false;
        return GsonUtil.GsonToBean(userJson, CustomerInfo.class) != null;
    }

    /**
     * 清除本地用户信息
     */
    public static void clear() {
        //用户资料
        PropertiesUtil.getInstance().setString(CUSTOMER_INFO, "");
        //登录状态存在本地
        PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isLogin, false);
        PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Nickname, "");
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.Login_Name);
        user = null;
    }


    /**
     * 本地缓存用户资料
     *
     * @param user 用户资料
     */
    public static void saveUserByUserId(CustomerInfo user) {
        if (user != null) {
            PropertiesUtil.getInstance().setString(String.valueOf(user.getResponseDataObj().getCustomerInfo().getCustomerid()), GsonUtil.GsonToString(user));
        }
    }

    /**
     * 获取本地用户资料
     *
     * @param userId 用户ID
     * @return
     */
    public static CustomerInfo user(long userId) {
        String userJson = PropertiesUtil.getInstance().getString(String.valueOf(userId), "");
        if (StringUtil.isBlank(userJson)){
            return null;
        }
        CustomerInfo user = GsonUtil.GsonToBean(userJson, CustomerInfo.class);
        if (user != null){
            return user;
        }
        else{
            return null;
        }
    }

    /**
     * 本地缓存用户资料
     *
     * @param user 用户资料
     */
    public static void saveUserByImId(CustomerInfo user) {
       /* if (user != null)
            PropertiesUtil.getInstance().setString(user.getImId(), GsonUtil.GsonToString(user));*/
    }

    /**
     * 获取本地用户资料
     *
     * @param imId 用户 IM ID
     */
    public static CustomerInfo user(String imId) {
        String userJson = PropertiesUtil.getInstance().getString(imId, "");
        if (StringUtil.isBlank(userJson)) return null;
        CustomerInfo user = GsonUtil.GsonToBean(userJson, CustomerInfo.class);
        if (user != null) return user;
        else return null;
    }
}
