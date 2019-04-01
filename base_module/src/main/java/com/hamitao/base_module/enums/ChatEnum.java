package com.hamitao.base_module.enums;

/**
 * <p>操作系统的枚举类
 *
 * @author lian
 */
public enum ChatEnum {
    TEXT(0, "文字"),
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    VOICE(3, "语音"),
    RICH_TEXT(4, "富文本"),
    COUPON(5, "优惠券"),
    ORDER(6, "订单"),
    COUSTOM(7, "客服"),
    TIPS(8, "提示"),
    UPDATE_INFO(9, "更新用戶信息"),
    WARNING(10, "预警"), //用于后台预警
    JOIN_UP_KF(11, "接入类消息"),
    POPUP(12, "弹窗"),
    RESUND(13, "退款消息"),
    Order_For_Server(101, "订单联系客服"),
    ;

    private ChatEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public static ChatEnum getType(int key) {
        ChatEnum[] types = ChatEnum.values();
        for (ChatEnum type : types) {
            if (key == type.type) {
                return type;
            }
        }
        return null;
    }

    public static boolean valid(int key) {
        ChatEnum[] types = ChatEnum.values();
        for (ChatEnum type : types) {
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
