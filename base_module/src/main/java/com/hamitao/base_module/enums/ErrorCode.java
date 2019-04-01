package com.hamitao.base_module.enums;

/**
 * Created by zfz on 2017/4/20.
 */
public enum ErrorCode {
    LOGIN_TOKEN_INVALID(10000, "您的身份信息异常，请重新登录"),
    SYSTEM_ERROR(10001, "当前系统错误，请稍后再试"),
    ACCOUNT_EXIST(10010, "账户已经存在"),
    ;

    ErrorCode(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public static ErrorCode getType(int key) {
        ErrorCode[] types = ErrorCode.values();
        for (ErrorCode type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return null;
    }

    public static boolean valid(byte key) {
        ErrorCode[] types = ErrorCode.values();
        for (ErrorCode type : types) {
            if (key == type.type) {
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
