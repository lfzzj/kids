package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/6/28.
 */

public class DialogScheduleModel {

    private String nickname;
    private String id;
    private Boolean isOpen;
    private String channelid;

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
