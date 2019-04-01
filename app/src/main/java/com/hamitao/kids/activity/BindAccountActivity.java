package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.BindAccountAdapter;
import com.hamitao.kids.network.NetworkRequest;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;


@Route("bind_account")
public class BindAccountActivity extends BaseActivity implements BGAOnRVItemLongClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private BindAccountAdapter adapter;

    private String terminalid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account);
        ButterKnife.bind(this);
        initData();
        initView();
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

    private void initData() {
        adapter = new BindAccountAdapter(recyclerView);
        adapter.setOnRVItemLongClickListener(this);
        terminalid = getIntent().getStringExtra("terminalid");
        queryBindAccount(terminalid);
    }

    private void initView() {
        title.setText("绑定的账号");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //查询绑定的账户
    private void queryBindAccount(String terminalid) {
        NetworkRequest.queryDeviceRelationRequest(terminalid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                adapter.setData(bean.getRelation().getCustomerInfos());
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

    //管理员解绑机器人
    private void unbindDevice(String comserid, String terminalid, final int position) {
        NetworkRequest.unbindDeviceRequest(comserid, terminalid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ToastUtil.showShort("解绑成功！");
                adapter.removeItem(position);
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

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {
        vibrate50();
        showConfirmDialog(this, "确定要解除绑定吗？", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    //解除绑定 ，这里需要推送一条消息给被解绑的用户
                    unbindDevice(adapter.getData().get(position).getCustomerid(), terminalid, position);
                }
                tDialog.dismiss();
            }
        });
        return true;
    }


}
