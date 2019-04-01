package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.ResultCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@Route("single_info")
public class SingleInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;


    private String deviceName = "";
    private String deviceId = "";
    private String terminalId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        deviceName = getIntent().getStringExtra("devicename");
        deviceId = getIntent().getStringExtra("deviceid");
        terminalId = getIntent().getStringExtra("terminalid");
    }


    private void initView() {
        title.setText("机器人信息");
        tvDeviceName.setText(deviceName);
    }

    @OnClick({R.id.back, R.id.tv_tv_clear, R.id.rl_device_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_tv_clear:
                clean();
                break;
            case R.id.rl_device_name:
                rename();
                break;
            default:
                break;
        }
    }

    /**
     * 修改机器人名称
     */
    private void rename() {
        showInputDialog(this, "修改机器人名称", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                String str = ((EditText) viewHolder.getView(R.id.et_name)).getText().toString();

                if (view.getId() == R.id.tv_confirm) {
                    if (str.equals("")) {
                        ToastUtil.showShort("机器人名不可为空");
                        return;
                    } else {
                        renameDevice(UserUtil.user().getCustomerid(), terminalId, "", str);
                    }
                }
                tDialog.dismiss();
            }
        }, new OnBindViewListener() {
            @Override
            public void bindView(BindViewHolder viewHolder) {
                EditText et = ((EditText) viewHolder.getView(R.id.et_name));
                et.setText(deviceName);
                et.setSelection(et.getText().toString().length());
            }
        });
    }


    /**
     * 清空聊天记录
     */
    private void clean() {
        showConfirmDialog(this, "确定要清空聊天内容吗?", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    JMessageClient.getSingleConversation(deviceId).deleteAllMessage();
                    setResult(ResultCode.RESULT_CLEAR_MESSAGE);
                    ToastUtil.showShort("已清空所有聊天记录");
                }
                tDialog.dismiss();
            }
        });
    }


    /**
     * 重命名绑定机器人
     *
     * @param id_of_peer1       这里相当于 customerid
     * @param id_of_peer2       这里相当于  terminalid
     * @param bindname_of_peer1 用户名（修改后）
     * @param bindname_of_peer2 机器人名（修改后）
     */
    public void renameDevice(String id_of_peer1, final String id_of_peer2, String bindname_of_peer1, final String bindname_of_peer2) {
        NetworkRequest.renameDeviceNameRequest(id_of_peer1, id_of_peer2, bindname_of_peer1, bindname_of_peer2, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                JMessageClient.getUserInfo(deviceId, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (0 == i) {

                            userInfo.updateNoteName(bindname_of_peer2, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        ToastUtil.showShort("修改成功");
                                        RxBus.getInstance().post(RxBusEvent.RENAME_DEVICE);
                                    }
                                }
                            });
                        }
                    }
                });

                Intent it = new Intent();
                it.putExtra("devicename", bindname_of_peer2);
                setResult(ResultCode.RESULT_RENAME_DEVICE_CHAT, it);
                tvDeviceName.setText(bindname_of_peer2);
                RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
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
