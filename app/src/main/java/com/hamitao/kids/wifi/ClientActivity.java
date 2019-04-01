package com.hamitao.kids.wifi;

import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @data on 2018/5/23 9:23
 * @describe:
 */



public class ClientActivity extends BaseActivity {
    @BindView(R.id.et_ssid)
    EditText mEtSSID;
    @BindView(R.id.et_ssid_psw)
    EditText mEtPasswd;

    private WifiManageUtils wifiManageUtils;
    private WifiManager wifiManager;
    private WifiClientThread clientThread;

    private WifiManager.WifiLock wifiLock;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connect);
        ButterKnife.bind(this);
        initData();
    }


    public void initData() {
        wifiManageUtils = new WifiManageUtils(this);
        wifiManager = wifiManageUtils.getWifiManager();
        wifiLock = wifiManageUtils.getWifiLock();
    }


    private void btnClient() {
        String ssid = mEtSSID.getText().toString();
        String pwd = mEtPasswd.getText().toString();
        if (ssid == null || ssid.equals("")) {
            ssid = "Hamitao";
        }
        if (pwd == null || pwd.equals("")) {
            pwd = "Hamitao%888";
        }


        String mSSID = "TestHot";
        String mPasswd = "12345678";
        WifiConfiguration netConfig = wifiManageUtils
                .getCustomeWifiClientConfiguration(mSSID, mPasswd, 3);

        int wcgID = wifiManager.addNetwork(netConfig);
        boolean b = wifiManager.enableNetwork(wcgID, true);

        Boolean iptoready = false;
        if (!b) {

            ToastUtil.showShort("wifi连接配置不可用");
            return;
        }
        while (!iptoready) {
            try {
                // 为了避免程序一直while循环，让它睡个100毫秒在检测……
                Thread.currentThread();
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }

            DhcpInfo dhcp = new WifiManageUtils(this).getDhcpInfo();
            int ipInt = dhcp.gateway;
            if (ipInt != 0) {
                iptoready = true;
            }
        }
        wifiLock.acquire();
        clientThread = new WifiClientThread(this);
        clientThread.setWifiInfo(ssid, pwd);
        clientThread.start();
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
                ToastUtil.showShort("开始连接机器人");
                btnClient();
                break;
        }
    }

}
