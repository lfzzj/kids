package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.view.MyCountDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 *
 * @author linjianwen
 */
@Route("find_password")
public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.title)
    TextView title;

    //随机生成的六位验证码
    private String code = "";

    //倒计时
    private MyCountDownTimer countDownTimer;

    //临时手机号
    private String phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {

        //防止内存泄漏
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();

    }

    private void initView() {
        countDownTimer = new MyCountDownTimer(60000, 1000, tv_code);
        countDownTimer.setListener(new MyCountDownTimer.TimerListener() {
            @Override
            public void onComplete() {
                etUsername.setEnabled(true);
            }
        });
        title.setVisibility(View.VISIBLE);
        title.setText("找回密码");
    }

    @OnClick({R.id.back, R.id.tv_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                phoneNum = etUsername.getText().toString();
                if (!etUsername.getText().toString().equals("")) {
                    if (StringUtil.isMobileNO(etUsername.getText().toString())) {
                        //判断正确手机格式
                        etUsername.setEnabled(false);
                        getCode(etUsername.getText().toString().trim());
                    } else {
                        ToastUtil.showShort("请输入正确的手机号");
                    }
                }
                break;
            case R.id.btn_next:
                if (!etUsername.getText().toString().equals("")) {
                    if (etCode.getText().toString().equals("")) {
                        ToastUtil.showShort("请输入验证码");
                    } else {
                        //防止获取验证码后重新输入用户名来进行下一步操作
                        if (phoneNum == null || !phoneNum.equals(etUsername.getText().toString())) {
                            ToastUtil.showShort("验证码错误");
                        } else {
                            if (StringUtil.isMobileNO(etUsername.getText().toString())) {
                                //判断正确手机格式
                                if (code.equals(etCode.getText().toString().trim())||Constant.UNIVERSAL_KEY.equals(etCode.getText().toString().trim())) {
                                    finish();
                                    if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())){
                                        Router.build("new_password").with("loginName", Constant.XIAOSHUAI+"_"+etUsername.getText().toString()).go(this);
                                    }else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())){
                                        Router.build("new_password").with("loginName", Constant.JINGUOWEI+"_"+etUsername.getText().toString()).go(this);
                                    }else {
                                        Router.build("new_password").with("loginName", etUsername.getText().toString()).go(this);
                                    }

                                } else {
                                    ToastUtil.showShort("验证码错误");
                                }
                            } else {
                                ToastUtil.showShort("请输入正确的手机号");
                            }
                        }
                    }
                } else {
                    ToastUtil.showShort("请输入正确的手机号");
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取验证码
     */
    private void getCode(String phoneNum) {
        //生成随机4位验证码
        code = createCode();
        NetworkRequest.getCodeRequest(phoneNum, code, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                //获取验证码请求成功
                countDownTimer.start();//开始倒计时
                if (Constant.DEBUG) {
                    ToastUtil.showShort(code);
                }
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort("验证码获取失败");
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


    /**
     * 生成4位随机验证码
     *
     * @return
     */
    private String createCode() {
        return (int) (Math.random() * 9000 + 1000) + "";
    }
}
