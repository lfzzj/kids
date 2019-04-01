package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("feedback")
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_advice)
    EditText etAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("使用反馈");
    }

    @OnClick({R.id.back, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_confirm:
                if (StringUtil.isMobileNO(etContact.getText().toString()) || StringUtil.isEmail(etContact.getText().toString())) {
                    commitfeedback(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                            etContact.getText().toString(), "", etAdvice.getText().toString());
                } else {
                    ToastUtil.showShort("请输入正确的联系方式");
                }
                break;
        }
    }


    private void commitfeedback(String customerid, String name, String contact1, String contact2, String text) {
        NetworkRequest.feedbackRequest(customerid, name, contact1, contact2, text, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ToastUtil.showShort("反馈成功");
                finish();
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
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
