package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ProductInfoModel;
import com.hamitao.kids.network.NetworkRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * 关于界面
 */
@Route("about")
public class AboutActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;

    //关于产品，法律条约，服务，导向
    private String about, leagle, service, guide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        aboutProduct("H1", "8.0", "zh", BaseApplication.getInstance().getVendor());
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("关于");
    }


    @OnClick({R.id.back, R.id.more, R.id.tv_about, R.id.tv_productIntroduce, R.id.tv_aggrement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_about:
                Router.build("webview").with("title", "关于产品").with("result", about).go(this);//关于产品
                break;
            case R.id.tv_productIntroduce:
//                String str = "file:///android_asset/about.html";
                Router.build("webview").with("title", "产品使用说明").with("result", guide).go(this);//产品使用说明
                break;
            case R.id.tv_aggrement:
                Router.build("webview").with("title", "用户协议与条款说明").with("result", service).go(this);//用户服务协议
                break;
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }


    private void aboutProduct(String devicetype, String osversion, String lang, String vendor) {
        NetworkRequest.aboutProductRequest(devicetype, osversion, lang, vendor, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ProductInfoModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ProductInfoModel.ResponseDataObjBean.class);
                about = bean.getTechnicalSupportInfo().getAboutDocURL();
                leagle = bean.getTechnicalSupportInfo().getLegalNoticesDocURL();
                service = bean.getTechnicalSupportInfo().getServiceContractURL();
                guide = bean.getTechnicalSupportInfo().getUserGuideDocURL();
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }

}
