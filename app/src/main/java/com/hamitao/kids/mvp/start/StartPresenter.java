package com.hamitao.kids.mvp.start;

import android.os.Handler;

import com.hamitao.base_module.base.BasePresenter;

/**
 * Created by linjianwen on 2018/1/26.
 */

public class StartPresenter implements BasePresenter {

    private StartView view;

    public StartPresenter(StartView view) {
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


    public void jumpActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view == null) {
                    return;
                }
                view.naviMain();
            }
        },2000);
    }


}
