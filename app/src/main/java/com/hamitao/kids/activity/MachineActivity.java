package com.hamitao.kids.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.ResultCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceInfoModel;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.model.PushMessageModel;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.service.MyMessageReceiver;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.machine.MachinePresenter;
import com.hamitao.kids.mvp.machine.MachineView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import io.reactivex.functions.Consumer;

import static com.hamitao.base_module.model.DeciveFootprintsModel.ResponseDataObjBean.FootprintsBean;
import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * 我的机器人
 */
@Route("machine")
public class MachineActivity extends BaseActivity implements MachineView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_machine_state)
    TextView machine_state;     //机器人状态

    @BindView(R.id.tv_electric)
    TextView tvElectric;    //电量
    @BindView(R.id.tv_signal)
    TextView tv_signal; //信号

    @BindView(R.id.tv_wifi)
    TextView tvWifi;  //wifi

    @BindView(R.id.iv_machine_isOpen)
    ImageView iv_machine_isOpen;        //开机开关

    @BindView(R.id.iv_machine_play)
    ImageView ivMachinePlay;            //播放快关

    @BindView(R.id.iv_machine_protectEye)
    ImageView protect_eye;              //护眼开关

    @BindView(R.id.iv_machine_phone)
    ImageView iv_machine_phone;       //电话接听管理

    @BindView(R.id.rg_machine_add)
    RadioGroup rg_machine_add;  //机器人按钮

    @BindView(R.id.tv_machine_unbind)
    TextView tv_machine_unbind;   //解绑


    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;


    private MachinePresenter presenter;
    private int select = 0;
    private int machineIndex = 0; //当前显示的机器人，默认为第一个。

    private double longitude = 0;//经度
    private double latitude = 0;//纬度

    private DeviceRelationModel.ResponseDataObjBean deviceInfoBean = null;


    private boolean isOpen; // 默认是开机状态
    private boolean isPlaying = false; // 是否正在播放
    private boolean isProtectEye; //是否开启护眼设置
    private boolean isMagPhone;

    private boolean isReceiveMsg = false;  //是否接收到推送消息

    private boolean isFirstQuery = true;

    private int timingCloseTime = 0; //定时关闭


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(myMessageReceiver);
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }


    private void initData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PushParams.DEVICE_INFO);
        filter.addAction(PushParams.STOP_PLAY);
        filter.addAction(PushParams.START_PLAY);
        registerReceiver(myMessageReceiver, filter);

        presenter = new MachinePresenter(this);
        refreshLayout.setDelegate(this);
        //获取用户绑定的机器人的信息
        presenter.queryDeviceRelation(UserUtil.user().getCustomerid());

        RxBus.getInstance().toObservable(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                switch (s) {
                    case RxBusEvent.BIND_DEVICE_SUCCESS:
                    case RxBusEvent.RENAME_DEVICE:
                        //成功绑定机器人后刷新列表
                        if (presenter != null) {
                            //获取用户绑定的机器人的信息
                            refreshData();
                        }
                        break;
                }
            }
        });
    }


    /**
     * 刷新数据，默认选中第一个机器人
     */
    private void refreshData() {
        presenter.queryDeviceRelation(UserUtil.user().getCustomerid());
    }

    //接受收到的推送消息
    private MyMessageReceiver myMessageReceiver = new MyMessageReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String action = intent.getAction();
            switch (action) {
                case PushParams.DEVICE_INFO:
                    //获取收到的推送消息
                    String message = intent.getStringExtra("message");
                    Log.d("收到的推送消息", "onReceive: " + message);
                    PushMessageModel messageModel = GsonUtil.GsonToBean(message, PushMessageModel.class);
                    if (messageModel.getResponseDataObj() != null) {
                        DeviceInfoModel deviceInfoModel = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), DeviceInfoModel.class);
                        initDeviceInfo(deviceInfoModel);
                    }
                    break;
                case PushParams.STOP_PLAY:
                    //机器人停止播放
                    isPlaying = false;
                    ivMachinePlay.setImageResource(R.drawable.icon_machine_pause);
                    break;
                case PushParams.START_PLAY:
                    isPlaying = true;
                    ivMachinePlay.setImageResource(R.drawable.icon_machine_start);
                    break;
                default:
                    break;
            }
        }
    };

    private void initView() {
        title.setText("我的机器人");

        refreshLayout.setDelegate(this);//设置下拉刷新监听

        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(this)); //设置下拉样式
    }

    //初始机器人信息
    private void initDeviceInfo(DeviceInfoModel model) {

        BGARefreshUtil.completeRequest(refreshLayout);
        isReceiveMsg = true;
        timingCloseTime = model.getTimingCloseTime();
        //状态
        machine_state.setText(model.isDeviceState() ? "开机" : "无");
        //电量
        tvElectric.setText(model.getBattery() + "%");
        //信号
        String status = "极弱";
        switch (model.getGSMIntensity()) {
            case 1:
                status = "极强";
                break;
            case 2:
                status = "强";
                break;
            case 3:
                status = "一般";
                break;
            case 4:
                status = "弱";
                break;
            case 5:
                status = "极弱";
                break;
            default:
                break;
        }
        tv_signal.setText(status);
        //wifi
        tvWifi.setText(model.getWifiid());
        //开关机
        isOpen = model.isDeviceState();
        //播放
        isPlaying = model.isIsPlaying();
        //护眼
        isProtectEye = model.isIsOpenEyeProtect();
        //电话管理
        isMagPhone = model.isIsPhoneManager();
        //经纬度
        latitude = model.getGeom().getLatitude();
        longitude = model.getGeom().getLongitude();

        iv_machine_isOpen.setImageResource(isOpen ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        ivMachinePlay.setImageResource(isPlaying ? R.drawable.icon_machine_pause : R.drawable.icon_machine_start);
        protect_eye.setImageResource(isProtectEye ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        iv_machine_phone.setImageResource(isMagPhone ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
    }

    /**
     * 网络加载获取机器人的状态
     *
     * @param list
     */
    @Override
    public void getDeciveFootprint(List<FootprintsBean> list) {

        FootprintsBean model = list.get(0);

        String state[] = model.getDevicecondition().split(";");

        for (int i = 0; i < state.length; i++) {
            String params = state[i].split("=")[0];
            String data = state[i].split("=")[1];
            if ("timingCloseTime".equals(params)) {
                timingCloseTime = Integer.parseInt(data);
            } else if ("isOpenEyeProtect".equals(params)) {
                isProtectEye = Boolean.parseBoolean(data);
            } else if ("isPhoneManager".equals(params)) {
                isMagPhone = Boolean.parseBoolean(data);
            }
        }


        //状态默认未无
        machine_state.setText("无");
        //电量
        tvElectric.setText(model.getBattery() + "%");
        //信号
        tv_signal.setText("极弱");
        //wifi
        tvWifi.setText("".equals(model.getWifiid()) ? "无" : model.getWifiid());
        //开关机
//        isOpen = true;
        //经纬度
        latitude = model.getGeom().getLatitude();
        longitude = model.getGeom().getLongitude();

//        iv_machine_isOpen.setImageResource(isOpen ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        ivMachinePlay.setImageResource(isPlaying ? R.drawable.icon_machine_pause : R.drawable.icon_machine_start);
        protect_eye.setImageResource(isProtectEye ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        iv_machine_phone.setImageResource(isMagPhone ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
    }

    @OnClick({R.id.back, R.id.more, R.id.rl_machine_wifi, R.id.tv_alarm, R.id.tv_deliver_list, R.id.tv_phone, R.id.tv_bind, R.id.rl_machine_isOpen, R.id.iv_machine_play
            , R.id.rl_machine_timer, R.id.tv_machine_location, R.id.tv_machine_wechat, R.id.tv_machine_habit, R.id.iv_machine_phone,
            R.id.tv_machine_unbind, R.id.iv_machine_protectEye})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                Router.build("scan").requestCode(RequestCode.REQUEST_SCAN_DEVICE).go(this);
                break;
            case R.id.rl_machine_wifi:
                //机器人连接的wifi,重新配置
                Router.build("wifi_connection").go(this);
                break;
            case R.id.tv_alarm:
                //机器人闹钟
                if (deviceInfoBean != null) {
                    Router.build("alarm")
                            .with("terminalid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid())
                            .with("channelid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver())
                            .go(this);
                }
                break;
            case R.id.tv_deliver_list:
                //投送清单
                Router.build("deliver_list").with("terminalid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid()).go(this);
                break;
            case R.id.tv_phone:
                //机器人电话本
                Router.build("contact")
                        .with("terminalid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid())
                        .with("channelid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver())
                        .go(this);
                break;
            case R.id.tv_bind:
                //绑定的账号
                Router.build("bind_account").with("terminalid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid()).go(this);
                break;
            //---------------------------------------------------------远程控制--------------------------------------------------------------------------------------------------
            case R.id.rl_machine_isOpen:
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }

                iv_machine_isOpen.setImageResource(isOpen ? R.drawable.icon_switch_n : R.drawable.icon_switch_p);
                presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), isOpen ?
                        PushParams.DEVICE_CLOSE : PushParams.DEVICE_OPEN, new String[]{""});
                isOpen = isOpen ? false : true;
                ToastUtil.showShort(isProtectEye ? "开机" : "关机");
                break;
            case R.id.iv_machine_play:
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }

                ivMachinePlay.setImageResource(isPlaying ? R.drawable.icon_machine_start : R.drawable.icon_machine_pause);
                presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), isPlaying ?
                        PushParams.STOP_PLAY : PushParams.START_PLAY, new String[]{""});
                isPlaying = isPlaying ? false : true;
                ToastUtil.showShort(isPlaying ? "机器人开始播放" : "机器人停止播放");

                break;
            case R.id.rl_machine_timer:
                //定时关闭
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }
                Router.build("alarm_close")
                        .requestCode(RequestCode.RESULT_CLOSE_ALARM_TIME)
                        .with("channelid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver())
                        .with("timingCloseTime", timingCloseTime)
                        .go(this);

                break;
            case R.id.tv_machine_location:
                //定位
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }

                if (longitude == 0 || latitude == 0) {
                    ToastUtil.showShort("获取设备定位失败");
                    return;
                }

                Router.build("location")
                        .with("devicename", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getBindname())
                        .with("longitude", longitude)
                        .with("latitude", latitude)
                        .go(this);

                break;
            case R.id.tv_machine_wechat:

                //打开对聊
                String id = deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getDeviceid();
                Conversation conv = JMessageClient.getSingleConversation(id);
                if (conv == null) {
                    conv = Conversation.createSingleConversation(id);
                }
                if (conv == null) {
                    ToastUtil.showShort("请重新登录");
                    return;
                }
                //跳转到对聊页面
                Router.build("wechat")
                        .with(Constant.CONV_TITLE, deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getBindname())
                        .with(Constant.TARGET_APP_KEY, getString(R.string.JPUSH_APPKEY))
                        .with(Constant.TARGET_ID, id)
                        .with("channelid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver())
                        .with("terminalid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid())
                        .go(this);
                break;
            case R.id.tv_machine_habit:
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }
                //跳转到推送列表
                Router.build("push_msg").with("channelid", deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver()).go(this);
                break;
            case R.id.iv_machine_protectEye:
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }
                protect_eye.setImageResource(isProtectEye ? R.drawable.icon_switch_n : R.drawable.icon_switch_p);
                presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), isProtectEye ? PushParams.EYE_UNPROTECT : PushParams.EYE_PROTECT, new String[]{""});
                isProtectEye = isProtectEye ? false : true;
                ToastUtil.showShort(isProtectEye ? "打开护眼管理" : "关闭护眼管理");
                break;
            case R.id.iv_machine_phone:
                if (!isReceiveMsg) {
                    ToastUtil.showShort("尚未获取到机器人信息...");
                    return;
                }
                iv_machine_phone.setImageResource(isMagPhone ? R.drawable.icon_switch_n : R.drawable.icon_switch_p);
                presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), isMagPhone ? PushParams.PHONE_CLOSE : PushParams.PHONE_OPEN, new String[]{""});
                isMagPhone = isMagPhone ? false : true;
                ToastUtil.showShort(isMagPhone ? "打开接听管理" : "关闭接听管理");
                break;
            case R.id.tv_machine_unbind:
                showConfirmDialog(this, "确定要解绑机器人吗?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            //解绑机器人
                            if (deviceInfoBean != null)
                                presenter.unbindDevice(UserUtil.user().getCustomerid(), deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getTerminalid(), deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getDeviceid());
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_SCAN_DEVICE && resultCode == RequestCode.REQUEST_SCAN_COMPLETE) {
            presenter.queryDeviceRelation(UserUtil.user().getCustomerid());
        } else if (requestCode == RequestCode.RESULT_CLOSE_ALARM_TIME && resultCode == ResultCode.RESULT_CLOSE_ALARM_TIME) {
            timingCloseTime = data.getIntExtra("timingCloseTime", 0);
        }
    }

    //显示选择的时间
    private String selectTime(int select) {
        switch (select) {
            case 1:
                return "0";
            case 2:
                return "15";
            case 3:
                return "30";
            case 4:
                return "40";
            case 5:
                return "50";
            case 6:
                return "60";
            default:
                return "";
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
        ToastUtil.showShort(msg);
    }

    //获取机器人绑定关系
    @Override
    public void getDeviceRelation(final DeviceRelationModel.ResponseDataObjBean bean) {

        //刷新列表
        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);

        this.deviceInfoBean = bean;

        //每次查询默认选中第一个机器人
        machineIndex = 0;

        //如果没有绑定机器人则退出
        if (bean.getRelation().getTerminalInfos().size() <= 0) {
            finish();
            return;
        }

        if (isFirstQuery) {
            //第一次进入界面的时候，仅查询一次当前选中的机器人信息
            isFirstQuery = false;
            getDeviceDataRequest(machineIndex);
        }

        //如果只有一个机器人的时候，则标题位机器人名字，隐藏多机器人布局
        if (bean.getRelation().getTerminalInfos().size() == 1) {
            rg_machine_add.setVisibility(View.GONE);
            title.setText(bean.getRelation().getTerminalInfos().get(0).getBindname());
        } else {
            title.setText("我的机器人");
            rg_machine_add.setVisibility(View.VISIBLE);
            //清空RadioGroup
            rg_machine_add.removeAllViews();

            //遍历机器人
            for (int i = 0; i < bean.getRelation().getTerminalInfos().size(); i++) {
                RadioButton radioButton = new RadioButton(this);   //创建RadioButton
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT, 1);
                radioButton.setId(i);
                radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
                radioButton.setGravity(Gravity.CENTER);
                @SuppressLint("ResourceType") ColorStateList colorStateList = getResources().getColorStateList(R.drawable.text_color_selector);
                radioButton.setTextColor(colorStateList);
                radioButton.setSingleLine();
                radioButton.setEllipsize(TextUtils.TruncateAt.END);
                radioButton.setText("机器人:" + bean.getRelation().getTerminalInfos().get(i).getBindname());
                radioButton.setTextSize(14);
                radioButton.setBackground(getResources().getDrawable(R.drawable.bg_color_selector));//设置按钮选中/未选中的背景
                if (i == 0) {
                    radioButton.setChecked(true);
                }
                rg_machine_add.addView(radioButton, layoutParams);
            }

            rg_machine_add.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    machineIndex = i;
                    //每次切换都还原数据
                    resetData();
                    getDeviceDataRequest(machineIndex);
                }
            });
        }
    }

    /**
     * 获取机器人数据请求。先取网络数据，再取推送数据
     */
    private void getDeviceDataRequest(int machineIndex) {
        presenter.initDeviceInfo(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getDeviceid()); //默认获取第一个机器人的轨迹
        presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), PushParams.DEVICE_INFO, null);
    }

    /**
     * 重置数据
     */
    private void resetData() {
        machine_state.setText("无"); //机器人状态
        tvElectric.setText("无");//电量
        tv_signal.setText("无");//信号
        tvWifi.setText("");//wifi
        isOpen = false;
        isProtectEye = false;
        //机器人开关机
        iv_machine_isOpen.setImageResource(isOpen ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
        //护眼提醒
        protect_eye.setImageResource(isProtectEye ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
    }

    /**
     * 解绑设备
     *
     * @param sourcepeer
     * @param deviceid
     */
    @Override
    public void unBindDevice(String sourcepeer, String deviceid) {
        //解绑后删除聊天会话
        JMessageClient.deleteSingleConversation(deviceid);
        //重新查询机器人关系
        presenter.queryDeviceRelation(sourcepeer);
        //刷新我的页面的机器人绑定情况
        RxBus.getInstance().post(RxBusEvent.MEFRAGMENT_REFRESH_DEVICE_INFO);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "rename_device":
                RadioButton btn = (RadioButton) rg_machine_add.getChildAt(machineIndex);
                btn.setText((Integer) data);
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        presenter.pushMessage(deviceInfoBean.getRelation().getTerminalInfos().get(machineIndex).getChannelid_listen_on_pushserver(), PushParams.DEVICE_INFO, null);
        //开始定时，刷新8秒超时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BGARefreshUtil.completeRequest(refreshLayout);
            }
        }, 6 * 1000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
