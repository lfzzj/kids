package com.hamitao.kids.mvp.player;

import com.hamitao.kids.mvp.CommonPresenter;

/**
 * Created by linjianwen on 2018/5/15.
 */

public class PlayerPresenter extends CommonPresenter {

    private PlayerView view;

    public PlayerPresenter(PlayerView view) {
        super(view);
        this.view = view;

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }
}
