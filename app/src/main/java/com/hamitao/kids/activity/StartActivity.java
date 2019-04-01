package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chenenyu.router.Router;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.start.StartPresenter;
import com.hamitao.kids.mvp.start.StartView;


/**
 * Created by linjianwen on 2018/1/4.
 */

public class StartActivity extends BaseActivity implements StartView {

    private String TAG = StartActivity.class.getSimpleName();

    private StartPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        init();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    private void init() {
        presenter = new StartPresenter(this);
        presenter.jumpActivity();

        //初始化弹窗
//        DialogListUtil.getInstance().init(this);
    }


    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void naviMain() {
        Router.build("main").go(StartActivity.this);
        finish();
    }

    @Override
    public void naviLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void autoLogin() {

    }
}
