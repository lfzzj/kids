package com.hamitao.kids.model.event;

/**
 * Created by linjianwen on 2018/9/25.
 */
public class PlayVideoEvent {

    private String status;

    private int currentScreen;

    public PlayVideoEvent(String status,int currentScrent) {
        this.status = status;
        this.currentScreen = currentScrent;
    }

    public int getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
