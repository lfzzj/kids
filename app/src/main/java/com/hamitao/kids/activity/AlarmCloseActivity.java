package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.base_module.ResultCode.RESULT_CLOSE_ALARM_TIME;

/**
 * 定时关闭
 */
@Route("alarm_close")
public class AlarmCloseActivity extends BaseActivity {

    @BindView(R.id.cb0)
    CheckBox cb0;
    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.tv_my_clock)
    TextView tvClock;


    @BindView(R.id.ll)
    LinearLayout linearLayout;


    View view;

    private String channelid = "";

    private int timingCloseTime;

    /**
     * 是否开启定时关闭
     */
    private boolean isAlarm = false;

    SimpleDateFormat formatter = new SimpleDateFormat("mm分ss秒");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_close);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        channelid = getIntent().getStringExtra("channelid");
        timingCloseTime = getIntent().getIntExtra("timingCloseTime", 0);
    }


    private void initView() {
        title.setText("定时关闭");
        more.setText("确定");
        switch (timingCloseTime) {
            case 0:
                isAlarm = false;
                resetChose(0);
                timingCloseTime = 0;
                break;
            case 15:
                //15分钟
                resetChose(1);
//                time = 899;
                timingCloseTime = 15;
                break;
            case 30:
                resetChose(2);
//                time = 1799;
                timingCloseTime = 30;
                break;
            case 40:
                //30分钟
                resetChose(3);
//                time = 1799;
                timingCloseTime = 40;
                break;
            case 50:
                //30分钟
                resetChose(4);
//                time = 1799;
                timingCloseTime = 50;
                break;
            case 60:
                //30分钟
                resetChose(5);
//                time = 1799;
                timingCloseTime = 60;
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.back, R.id.more, R.id.cb0, R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                setResult(RESULT_CLOSE_ALARM_TIME, new Intent().putExtra("timingCloseTime", timingCloseTime));
                finish();
                break;
            case R.id.more:
                starTimer();
                break;
            case R.id.cb0:
                isAlarm = false;
                resetChose(0);
                timingCloseTime = 0;
                break;
            case R.id.cb1:
                //15分钟
                resetChose(1);
                timingCloseTime = 15;
                break;
            case R.id.cb2:
                //30分钟
                resetChose(2);
                timingCloseTime = 30;
                break;
            case R.id.cb3:
                //40分钟
                resetChose(3);
                timingCloseTime = 40;
                break;
            case R.id.cb4:
                //50分钟
                resetChose(4);
                timingCloseTime = 50;
                break;
            case R.id.cb5:
                //60分钟;
                resetChose(5);
                timingCloseTime = 60;
                break;
            default:
                break;
        }
    }

    private void starTimer() {
        //开始定时
        Constant.isTimer = true;
        PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isTimer, true);
        if (timingCloseTime != 0) {
            isAlarm = true;
            if (timingCloseTime == 15) {
                ToastUtil.showShort("15分钟后,将自动暂停正在播放的歌曲");
            } else if (timingCloseTime == 30) {
                ToastUtil.showShort("30分钟后,将自动暂停正在播放的歌曲");
            } else if (timingCloseTime == 40) {
                ToastUtil.showShort("40分钟后,将自动暂停正在播放的歌曲");
            } else if (timingCloseTime == 50) {
                ToastUtil.showShort("50分钟后,将自动暂停正在播放的歌曲");
            } else if (timingCloseTime == 60) {
                ToastUtil.showShort("60分钟后,将自动暂停正在播放的歌曲");
            }

            pushMessage(channelid, PushParams.TIMER, new String[]{String.valueOf(timingCloseTime)});
        }
    }


    /**
     * 消息推送远程请求
     *
     * @param targetChannelid
     * @param actionType
     * @param contentid       这里是播放内容，OSS上的名字
     */
    public void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }

            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                showProgressDialog();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                dismissProgressDialog();
            }
        }));
    }

    private void changeTime(final long l) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(formatter.format(l) + "");
                String str = formatter.format(l) + "后，将自动暂停正在播放的歌曲";
                if (tvClock != null) {
                    tvClock.setText(str);
                }
            }
        });

    }


    private void resetChose(int position) {
        cb0.setChecked(false);
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        switch (position) {
            case 0:
                cb0.setChecked(true);
                break;
            case 1:
                cb1.setChecked(true);
                break;
            case 2:
                cb2.setChecked(true);
                break;
            case 3:
                cb3.setChecked(true);
                break;
            case 4:
                cb4.setChecked(true);
                break;
            case 5:
                cb5.setChecked(true);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
        if (slideOffset == 1)
            setResult(RESULT_CLOSE_ALARM_TIME, new Intent().putExtra("timingCloseTime", timingCloseTime));
        super.onSwipeBackLayoutSlide(slideOffset);
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
