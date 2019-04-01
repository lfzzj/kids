package com.hamitao.base_module.enums;

/**
 * <p>操作系统的枚举类
 */
public enum OsEnum {
    NOT_FOUND((byte)0,"其它操作系统"),
    ANDROID((byte)1,"安卓"),
    IOS((byte)2,"IOS"),
    ;

    private OsEnum(byte type, String desc){
        this.type = type;
        this.desc = desc;
    }

    private byte type;
    private String desc;

    public static OsEnum getType(byte key){
        OsEnum[] types = OsEnum.values();
        for(OsEnum type:types){
            if (key==type.type) {
                return type;
            }
        }
        return null;
    }

    public static boolean valid(byte key){
        OsEnum[] types = OsEnum.values();
        for(OsEnum type:types){
            if (key==type.type) {
                return true;
            }
        }
        return false;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
