package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.RecordAdapter;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.model.RecordModel.ResponseDataObjBean.VoiceRecordingsBean;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

@Route("device_record")
public class DeviceRecordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;

    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_record);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        adapter = new RecordAdapter(recyclerview);
        if (getIntent().getStringExtra("terminalid") != null)
            queryMyRecordList(getIntent().getStringExtra("terminalid"));
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }


    //查询我的录音
    public void queryMyRecordList(String ownerid) {
        NetworkRequest.queryRecordRequest(ownerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                RecordModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecordModel.ResponseDataObjBean.class);
                getMyRecordList(bean.getVoiceRecordings());
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

    private void getMyRecordList(List<VoiceRecordingsBean> voiceRecordings) {
        tvNone.setVisibility(voiceRecordings.size() <= 0 ? View.VISIBLE : View.GONE);
        adapter.setData(voiceRecordings);
    }
}
