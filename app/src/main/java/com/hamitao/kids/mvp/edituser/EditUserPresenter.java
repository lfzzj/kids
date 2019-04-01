package com.hamitao.kids.mvp.edituser;

import android.content.Context;

import com.hamitao.base_module.base.BasePresenter;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class EditUserPresenter implements BasePresenter {



    private EditUserView view;

    private Context context;

    public EditUserPresenter(EditUserView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


}
