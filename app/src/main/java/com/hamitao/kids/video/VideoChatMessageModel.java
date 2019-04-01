package com.hamitao.kids.video;

/**
 * Created by linjianwen on 2018/8/16.
 */

public class VideoChatMessageModel {

    private String status;

    private String content;

    public VideoChatMessageModel(String status, String content) {
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
