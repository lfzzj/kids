package com.hamitao.kids.activity;

import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author linjianwen
 */
@Route("wifi_connection")
public class WifiConnectActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_ssid)
    EditText etSsid;
    @BindView(R.id.et_ssid_psw)
    EditText etSsidPsw;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.cb_show_psw)
    CheckBox cbShowPsw;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connect);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        String ssid = getSSID();

        if (!ssid.equals("")) {
            etSsid.setText(ssid);
            etSsid.setSelection(etSsid.getText().toString().length());
        }
    }

    private void initView() {
        title.setText("生成wifi二维码");
        cbShowPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isShowPassword(b);
            }
        });
    }

    @OnClick({R.id.back, R.id.more, R.id.btn_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.btn_code:
                if (etSsid.getText().toString().equals("") || etSsidPsw.getText().toString().equals("")) {
                    ToastUtil.showShort("信息不可为空");
                } else {
                    if (etSsidPsw.getText().toString().length() < 8) {
                        ToastUtil.showShort("请输入至少8位密码");
                    } else {
                        createQRcode(etSsid.getText().toString() + "," + etSsidPsw.getText().toString());
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取当前连接WIFI的SSID
     */
    public String getSSID() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            if (winfo != null) {
                String s = winfo.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        return "";
    }

    /**
     * 是否显示密码
     *
     * @param isShow
     */
    private void isShowPassword(boolean isShow) {
        etSsidPsw.setTransformationMethod(isShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etSsidPsw.setSelection(etSsidPsw.getText().toString().length());
    }

    /**
     * 生成二维码
     */
    private void createQRcode(final String msg) {

        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {

                //生成带logo的二维码
//                Bitmap logoBitmap = BitmapFactory.decodeResource(CreateCodeActivity.this.getResources(), R.drawable.app_logo);
//                e.onNext(QRCodeEncoder.syncEncodeQRCode(msg, BGAQRCodeUtil.dp2px(CreateCodeActivity.this, 150), Color.BLACK, Color.WHITE, logoBitmap));

                e.onNext(QRCodeEncoder.syncEncodeQRCode(msg, BGAQRCodeUtil.dp2px(WifiConnectActivity.this, 150)));


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                //隐藏键盘
                BGAKeyboardUtil.closeKeyboard(WifiConnectActivity.this);

                if (bitmap != null) {
                    ivCode.setImageBitmap(bitmap);
                    ivCode.setVisibility(View.VISIBLE);
                    tvTips.setVisibility(View.VISIBLE);
                } else {
                    ToastUtil.showShort("生成二维码失败");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
