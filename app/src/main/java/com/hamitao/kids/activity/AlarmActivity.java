package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.AlarmAdapter;
import com.hamitao.kids.model.AlarmModel;
import com.hamitao.kids.mvp.alarm.AlarmPresenter;
import com.hamitao.kids.mvp.alarm.AlarmView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 闹钟界面
 */
@Route("alarm")
public class AlarmActivity extends BaseActivity implements AlarmView, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnRVItemClickListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;


    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.tv_none)
    TextView tvNone;

    private AlarmAdapter adapter;

    private AlarmPresenter presenter;

    private String terminalid = "";//机器人id
    private String channelid = ""; // 推送Id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        initData();
        initView();


    }

    @Override
    protected void onDestroy() {
        //防止内存泄漏
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    private void initData() {
        presenter = new AlarmPresenter(this);
        terminalid = getIntent().getStringExtra("terminalid");
        channelid = getIntent().getStringExtra("channelid");
        //查询闹钟
        presenter.queryAlarm(terminalid);
    }

    private void initView() {

        title.setText("闹钟");
        tvNone.setText("还没有设置闹钟哦..快去添加闹钟吧！");
        adapter = new AlarmAdapter(recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

        //设置长按监听器
        adapter.setOnRVItemLongClickListener(this);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
    }


    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                Router.build("add_alarm").with("terminalid", terminalid).with("channelid", channelid).requestCode(RequestCode.REQUEST_ADD_ALARM).go(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_ADD_ALARM && resultCode == RequestCode.REQUEST_ADD_ALARM) {
            if (!terminalid.equals("")) {
                //重新查询闹钟
                presenter.queryAlarm(terminalid);
            }
        }
    }

    /**
     * 长按点击删除
     */
    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {
        vibrate50();
        showConfirmDialog(this, "是否删除闹钟?", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    //这里进行网络请求，删除闹钟
                    presenter.deleteAlarm(terminalid, adapter.getData().get(position).getId(), position);
                }
                tDialog.dismiss();
            }
        });
        return false;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.iv_isopen) {
            AlarmModel.ResponseDataObjBean.TimerAlarmsBean alarm = adapter.getData().get(position);


            if (alarm.getStatus().equals("enable")) {
                ((ImageView) childView).setImageResource(R.drawable.icon_switch_n);
                adapter.getData().get(position).setStatus("disable");
                presenter.updateAlarm(alarm.getOwnerid(), alarm.getName(), alarm.getId(), "disable", position);
            } else {
                ((ImageView) childView).setImageResource(R.drawable.icon_switch_p);
                adapter.getData().get(position).setStatus("enable");
                presenter.updateAlarm(alarm.getOwnerid(), alarm.getName(), alarm.getId(), "enable", position);
            }
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

    @Override
    public void getAlarmList(AlarmModel.ResponseDataObjBean model) {
        if (model.getTimerAlarms().size() <= 0) {
            llNone.setVisibility(View.VISIBLE);
        } else {
            llNone.setVisibility(View.GONE);
            adapter.setData(model.getTimerAlarms());
        }


    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("timer", (Serializable) adapter.getData().get(position).getTimers());
        //点击进入编辑闹钟
        Router.build("add_alarm")
                .with("bundle", bundle)
                .with("name", adapter.getData().get(position).getName())
                .with("alarmid", adapter.getData().get(position).getId())
                .with("id", adapter.getData().get(position).getId())
                .with("idx", adapter.getData().get(position).getIdx())
                .with("terminalid", terminalid)
                .with("channelid", channelid)
                .with("position", position)
                .requestCode(RequestCode.REQUEST_ADD_ALARM)
                .go(this);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "delete_alarm":
                presenter.pushMessage(channelid, PushParams.UPDATE_ALARM, null);
                adapter.removeItem((int) aux);
                if (adapter.getData().size() == 0) {
                    llNone.setVisibility(View.VISIBLE);
                }
                break;
            case "update_alarm":
                //更新闹钟开光
                presenter.pushMessage(channelid, PushParams.UPDATE_ALARM, null);
                ToastUtil.showShort(data.equals("enable") ? "打开闹钟" : "关闭闹钟");
                break;
            default:
                break;
        }
    }
}
