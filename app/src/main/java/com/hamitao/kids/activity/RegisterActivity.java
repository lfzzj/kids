package com.hamitao.kids.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.FileUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.register.RegisterPresenter;
import com.hamitao.kids.mvp.register.RegisterView;
import com.hamitao.kids.view.MyCountDownTimer;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@Route("register")
public class RegisterActivity extends BaseActivity implements RegisterView {

    private String TAG = "注册";

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm)
    EditText etConfirm;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_code)
    TextView tv_code;

    //倒计时
    private MyCountDownTimer countDownTimer;

    private String code;

    private RegisterPresenter presenter;

    private String phoneNum = ""; //存放临时的手机号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
        initView();
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

    private void initData() {
        countDownTimer = new MyCountDownTimer(60000, 1000, tv_code); // 60秒倒计时

        countDownTimer.setListener(new MyCountDownTimer.TimerListener() {
            @Override
            public void onComplete() {
                etUsername.setEnabled(true);
            }
        });
        presenter = new RegisterPresenter(this);
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("注册");
        SpannableStringBuilder style = new SpannableStringBuilder("已有账号，登录");
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_login.setText(style);
    }

    @OnClick({R.id.back, R.id.tv_code, R.id.btn_register, R.id.tv_login})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                //应用生成的验证码
                String phone = etUsername.getText().toString();
                phoneNum = phone;
                if (StringUtil.isBlank(phone) || !StringUtil.isMobileNO(phone)) {
                    ToastUtil.showShort("请输入正确的手机号");
                } else {
                    etUsername.setEnabled(false);
                    code = createCode();
                    presenter.getCode(etUsername.getText().toString(), code);

                    if (Constant.DEBUG) {
                        ToastUtil.showShort(code);
                    }

                }
                break;
            case R.id.btn_register:
                //注册
                //这里判断验证码是否正确
                if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 15) {
                    ToastUtil.showShort("请输入6-15位密码");
                } else {
                    if (!phoneNum.equals(etUsername.getText().toString())) {
                        ToastUtil.showShort("验证码错误");
                    } else {
                        if (!etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                            ToastUtil.showShort("两次输入的密码不一致！");
                        } else {
                            if (code.equals(etCode.getText().toString().trim()) || Constant.UNIVERSAL_KEY.equals(etCode.getText().toString().trim())) {
                                if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())) {
                                    presenter.regiset(Constant.XIAOSHUAI + "_" + etUsername.getText().toString(), etPassword.getText().toString());
                                } else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
                                    presenter.regiset(Constant.JINGUOWEI + "_" + etUsername.getText().toString(), etPassword.getText().toString());
                                } else {
                                    presenter.regiset(etUsername.getText().toString(), etPassword.getText().toString());
                                }
                            } else {
                                ToastUtil.showShort("您输入的验证码有误！");
                            }
                        }
                    }
                }
//                presenter.regiset(etUsername.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_login:
                finish();
                Router.build("login").go(this);
                break;
            default:
                break;
        }
    }


    /**
     * 生成4位随机验证码
     *
     * @return
     */
    private String createCode() {
        return (int) (Math.random() * 9000 + 1000) + "";
    }

    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShortError(msg);
    }

    @Override
    public void startCountDown() {
        countDownTimer.start();
    }

    @Override
    public void goPerfectInfo(String customerid, String channelid) {
        //跳转到完善个人信息页面
        ToastUtil.showShort("登录成功，请完善个人信息");
        //注册成功后，上传头像
        uploadUserInfo();
        //注册成功后设置别名
        JPushInterface.setAlias(this, 123, channelid);
        //跳转到完善页面
        Router.build("user_info").with("isregister", true).with("channelid", channelid).go(this);
        RxBus.getInstance().post(RxBusEvent.LOGIN_SUCCESS);
        finish();
    }


    //上传默认的昵称，头像
    private void uploadUserInfo() {
        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_head_default);
        Bitmap mBitmap = bd.getBitmap();
        final File header = FileUtil.saveBitmapFile(mBitmap, Constant.USER_PIC_LOCAL, "default_header.png");
        JMessageClient.updateUserAvatar(header, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                LogUtil.d(TAG, "gotResult: " + i + "    result=" + s);
                if (i == 0) {
                    LogUtil.d(TAG, "注册头像上传成功");
//                    //上传成功后后删除本地的头像文件
                    if (header.exists()) {
                        header.delete();
                    }
                    //上传默认昵称
                    final UserInfo userInfo = JMessageClient.getMyInfo();
                    userInfo.setNickname("淘妈妈");
                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                LogUtil.d(TAG, "昵称上传成功");
                                PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Nickname, "淘妈妈");
                            }
                        }
                    });
                }
            }
        });

    }


}
