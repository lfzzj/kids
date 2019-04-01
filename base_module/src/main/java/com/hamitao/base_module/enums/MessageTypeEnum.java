package com.hamitao.base_module.enums;

/**
 * Created by tulip on 2017/3/4.
 */

public enum MessageTypeEnum {
    /**
     * 系统消息
     */
    SYSTEM(1, "系统消息"),
    /**
     * 私聊消息，如打招呼，邀请视频消息
     */
    PRI_MSG(2, "私聊消息"),

    /**
     * 视频消息
     */
    VIDEO(3, "视频消息"),;

    private int type;
    private String desc;

    private MessageTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static MessageTypeEnum getType(int key){
        MessageTypeEnum[] types = MessageTypeEnum.values();
        for(MessageTypeEnum type:types){
            if (key==type.type) {
                return type;
            }
        }
        return null;
    }

    public static boolean valid(int key){
        MessageTypeEnum[] types = MessageTypeEnum.values();
        for(MessageTypeEnum type:types){
            if (key==type.type) {
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
