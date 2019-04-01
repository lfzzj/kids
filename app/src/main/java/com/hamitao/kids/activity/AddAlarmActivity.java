package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.AlarmModel;
import com.hamitao.kids.model.requestmodel.AlarmRequestModel;
import com.hamitao.kids.network.NetworkRequest;
import com.qiniu.android.jpush.utils.StringUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  添加闹钟
 */
@Route("add_alarm")
public class AddAlarmActivity extends BaseActivity {

    public static final int SELECT_WEEK = 100;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.timepicker)
    TimePicker timepicker;
    @BindView(R.id.tv_bell)
    TextView tvBell;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.tv_lable)
    TextView tv_lable;


    private String hour;
    private String minute;

    private String cycle = "";

    private String terminalid;
    private String channeiid;


    //编辑闹钟用到的字段—————————————————————
    private boolean isEditAlarm = false;
    private int position = -1;
    private String id;
    private String idx;
    private String name;
    private String startTime;
    private String day;
    private List<AlarmModel.ResponseDataObjBean.TimerAlarmsBean.TimersBean> timer;
    //编辑闹钟用到的字段—————————————————————


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        terminalid = getIntent().getStringExtra("terminalid");
        channeiid = getIntent().getStringExtra("channelid");

        if (getIntent().getIntExtra("position", -1) != -1) {
            isEditAlarm = true;
            id = getIntent().getStringExtra("id");
            idx = getIntent().getStringExtra("idx");
            timer = (List<AlarmModel.ResponseDataObjBean.TimerAlarmsBean.TimersBean>) getIntent().getBundleExtra("bundle").getSerializable("timer");
            name = getIntent().getStringExtra("name");
        }


    }


    private void initView() {
        title.setText("添加闹钟");
        more.setText("保存");

        if (isEditAlarm) {
            tv_lable.setText(name);
            tvCycle.setText(formatDay(timer.get(0).getDay()));
            cycle = timer.get(0).getDay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String h = timer.get(0).getStarttime().split(":")[0];
                String m = timer.get(0).getStarttime().split(":")[1];
                timepicker.setHour(Integer.parseInt(h));
                timepicker.setMinute(Integer.parseInt(m));
            }

        }


        timepicker.setIs24HourView(true);

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteofDay) {
                hour = String.valueOf(hourOfDay);
                minute = String.valueOf(minuteofDay);
            }
        });
    }


    @OnClick({R.id.back, R.id.more, R.id.rl_alarm_cycle, R.id.rl_alarm_lable, R.id.rl_alarm_bell, R.id.rl_record_bell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                if (isEditAlarm) {
                    List<AlarmRequestModel.TimersBean> list = new ArrayList<>();
                    AlarmRequestModel.TimersBean bean = new AlarmRequestModel.TimersBean();
                    bean.setStarttime((timepicker.getCurrentHour() < 10 ? ("0" + timepicker.getCurrentHour()) : timepicker.getCurrentHour() + "") + ":" +
                            (timepicker.getCurrentMinute() < 10 ? ("0" + timepicker.getCurrentMinute()) : timepicker.getCurrentMinute() + ""));
                    bean.setDay(cycle);
                    list.add(bean);
                    //编辑闹钟
                    updateAlarm(terminalid, id, idx, tv_lable.getText().toString(), "enable", list);
                } else {
                    //添加闹钟
                    addAlarm(tv_lable.getText().toString(), UserUtil.user().getCustomerid(), terminalid, cycle,
                            (timepicker.getCurrentHour() < 10 ? ("0" + timepicker.getCurrentHour()) : timepicker.getCurrentHour() + "") + ":" +
                                    (timepicker.getCurrentMinute() < 10 ? ("0" + timepicker.getCurrentMinute()) : timepicker.getCurrentMinute() + ""));
                }

                break;
            case R.id.rl_alarm_cycle:
                Router.build("select_week").with("weekStr",tvCycle.getText().toString()).requestCode(SELECT_WEEK).go(this);
                break;
            case R.id.rl_alarm_lable:
                showInputDialog(this, "请输入闹钟标签", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            tv_lable.setText(((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString());
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.rl_alarm_bell:
                break;
            case R.id.rl_record_bell:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_WEEK && resultCode == SELECT_WEEK) {
            cycle = data.getStringExtra("selectWeek");
            tvCycle.setText(formatDay(cycle));
        }
    }


    /**
     * 格式化日期
     * @param day
     * @return
     */
    private String formatDay(String day) {
        String[] a = day.split(",");
        String[] b = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            switch (a[i]) {
                case "SUN":
                    b[i] = "周日";
                    break;
                case "MON":
                    b[i] = "周一";
                    break;
                case "TUE":
                    b[i] = "周二";
                    break;
                case "WED":
                    b[i] = "周三";
                    break;
                case "THU":
                    b[i] = "周四";
                    break;
                case "FRI":
                    b[i] = "周五";
                    break;
                case "SAT":
                    b[i] = "周六";
                    break;
                default:
                    b[i] = "";
                    break;
            }
        }
        return StringUtils.join(b, ",");
    }


    /**
     * 更新闹钟
     * @param ownerid
     * @param id
     * @param idx
     * @param name
     * @param status
     * @param bean
     */
    private void updateAlarm(String ownerid, String id, String idx, String name, String status, List<AlarmRequestModel.TimersBean> bean) {
        NetworkRequest.updateAlarmRequest(ownerid, id, idx, name, status, bean, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ToastUtil.showShort("修改成功");
                pushMessage(channeiid, PushParams.UPDATE_ALARM, null);
                setResult(RequestCode.REQUEST_ADD_ALARM);
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


    /**
     * 添加闹钟
     * @param label 标签
     * @param cusomerid
     * @param terminalid
     * @param day   循坏日期
     * @param starttime 闹铃时间
     */
    private void addAlarm(String label, String cusomerid, String terminalid, String day, String starttime) {
        NetworkRequest.addAlarmRequest(label, cusomerid, terminalid, day, starttime, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ToastUtil.showShort("保存成功");
                pushMessage(channeiid, PushParams.UPDATE_ALARM, null);
                setResult(RequestCode.REQUEST_ADD_ALARM);
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


    private void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

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
