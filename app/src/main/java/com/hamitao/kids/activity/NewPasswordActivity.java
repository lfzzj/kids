package com.hamitao.kids.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新密码设置
 */
@Route("new_password")
public class NewPasswordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.cb_showPassword)
    CheckBox cbShowPassword;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("新密码设置");

        Bundle mExtras = getIntent().getExtras();
        loginName = mExtras.getString("loginName", "");


        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShowPassword(isChecked);
            }
        });
    }

    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    modifyPassword(etConfirmPassword.getText().toString());
                } else {
                    ToastUtil.showShort("两次输入的密码不相同");
                }
                break;
                default:break;
        }
    }


    /**
     * 是否显示密码
     *
     * @param isShow
     */
    private void isShowPassword(boolean isShow) {
        etPassword.setTransformationMethod(isShow? HideReturnsTransformationMethod.getInstance(): PasswordTransformationMethod.getInstance());
        etConfirmPassword.setTransformationMethod(isShow? HideReturnsTransformationMethod.getInstance(): PasswordTransformationMethod.getInstance());
    }


    /**
     * 修改密码
     *
     * @param newPsw
     */
    public void modifyPassword(String newPsw) {
        //请求服务器修改密码
        NetworkRequest.modifyPswRequest(loginName, loginName + "&Hamitao%987", newPsw, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ToastUtil.showShort("修改密码成功");
                finish();
                Router.build("login").go(NewPasswordActivity.this);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
                dismissProgressDialog();
            }

            @Override
            public void onBegin() {
                showProgressDialog();
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }
        }));
    }
}
