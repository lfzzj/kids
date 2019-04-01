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
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.login.LoginPresenter;
import com.hamitao.kids.mvp.login.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

@Route("login")
public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        presenter = new LoginPresenter(this);
    }

    private void initView() {
        title.setText("登录");
    }

    @OnClick({R.id.back, R.id.tv_findPassword, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_findPassword:
                finish();
                Router.build("find_password").go(this);
                break;
            case R.id.btn_login:
                if (etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("")) {
                    ToastUtil.showShort("账号密码不能为空！");
                } else {
                    if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())) {
                        presenter.login(Constant.XIAOSHUAI + "_" + etUsername.getText().toString(), etPassword.getText().toString());
                    } else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
                        presenter.login(Constant.JINGUOWEI + "_" + etUsername.getText().toString(), etPassword.getText().toString());
                    } else {
                        presenter.login(etUsername.getText().toString(), etPassword.getText().toString());
                    }
                }
                break;
            default:
                break;
        }
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
    public void goMain(String channelid) {
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this);
        }
        //判断机器人关系时候为空，如果为空则查询机器人关系，将其存入本地
        if (PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, "").equals("")) {
            presenter.queryRelationById(UserUtil.user().getCustomerid());
        }
        JPushInterface.setAlias(this, 123, channelid); //设置别名
        RxBus.getInstance().post(RxBusEvent.LOGIN_SUCCESS);
        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
        finish();
    }

    @Override
    public void getRobotRelation(DeviceRelationModel.ResponseDataObjBean bean) {
        //查询成功后后将机器人关系存入本地
        PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Device_Relation, GsonUtil.GsonToString(bean.getRelation().getTerminalInfos()));
        //是否绑定了我们公司的机器人，如果有绑定则开放制卡，课表，等功能。
        PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isBindDevice, bean.getRelation().getTerminalInfos().size() <= 0 ? false : true);
    }

}
