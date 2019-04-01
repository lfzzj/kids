package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.kids.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


@Route("login_guide")
public class LoginGuideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_guide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Router.build("login").go(this);
                break;
            case R.id.btn_register:
                Router.build("register").go(this);
                break;
        }
        finish();
    }
}
