package com.hamitao.base_module.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * SharedPreferences 工具类
 */

public class PropertiesUtil {

    private static final String fileName = "hamitao";

    private SharedPreferences sp;

    private SharedPreferences.Editor editor;

    private Context context;

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void destory() {
        editor.clear().commit();
    }

    private static PropertiesUtil INSTANCE = null;

    public static PropertiesUtil getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new PropertiesUtil();
        }
        return INSTANCE;
    }

    private PropertiesUtil() {
    }

    public void remove(SpKey e) {
        editor.remove(e.getText());
        editor.commit();
    }

    public void setString(SpKey e, String value) {
        editor.putString(e.getText(), value);
        editor.commit();
    }

    public String getString(SpKey e, String defValue) {
        return sp.getString(e.getText(), defValue);
    }

    public void setString(String e, String value) {
        editor.putString(e, value);
        editor.commit();
    }

    public String getString(String e, String defValue) {
        return sp.getString(e, defValue);
    }

    public void setInt(SpKey e, int value) {
        editor.putInt(e.getText(), value);
        editor.commit();
    }

    public int getInt(SpKey e, int defValue) {
        return sp.getInt(e.getText(), defValue);
    }

    public void setLong(SpKey e, long value) {
        editor.putLong(e.getText(), value);
        editor.commit();
    }

    public long getLong(SpKey e, long defValue) {
        return sp.getLong(e.getText(), defValue);
    }

    public void setLong(String e, long value) {
        editor.putLong(e, value);
        editor.commit();
    }

    public long getLong(String e, long defValue) {
        return sp.getLong(e, defValue);
    }

    public void setBoolean(SpKey e, boolean value) {
        editor.putBoolean(e.getText(), value);
        editor.commit();
    }

    public boolean getBoolean(SpKey e, boolean defValue) {
        return sp.getBoolean(e.getText(), defValue);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static enum SpKey {

        //极光推送和极光IM的key
        Jpush_Key("jpush_key"),

        //用户是否登录
        isLogin("isLogin"),

        //首次使用软件
        isFirst("isFirst"),

        isFirstDate("isFirstDate"),

        // 引导页
        Show_Guide_Page("Show_Guide_Page2.0"),

        //搜索记录
        Search_Record("search_record"),

        //滤波大小
        Filter_Size("filter_size"),

        //滤波时间
        Filter_Time("filter_time"),

        //用户信息
        Customer_Info("customer_info"),

        //播放模式
        Play_Mode("play_mode"),

        //播放位置
        Play_Positon("play_positon"),

        //是否开启定时停止机器人播放
        isTimer("istimer"),
        //记录关闭APP的时间
        Close_App_time("close_app_time"),

        //头像
        Avator("avator"),

        //登录名（手机号）
        Login_Name("login_name"),

        //昵称
        Nickname("nickname"),

        //是否其他厂商的产品
        Other_Product("other_product"),

        //是否绑定了机器人
        isBindDevice("isBindDevice"),

        //机器人关系
        Device_Relation("device_relation");

        public String text;

        private SpKey(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }
}
