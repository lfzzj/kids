package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.MyScheduleAdapter;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的课表
 */
@Route("my_schedule")
public class MyScheduleActivity extends BaseActivity implements BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.more)
    TextView more;

    private MyScheduleAdapter adapter;

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("我的课表");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    private void initData() {
        adapter = new MyScheduleAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        list.add("我的课表");
        list.add("官方推荐课表");
        adapter.setData(list);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                startActivity(new Intent(this, EditScheduleActivity.class));
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        //跳转到课程表页面，position :0为我的课表，1为官方推荐课表
        Router.build("create_schedule").with("fromsquare", true).with("position", position).go(this);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.list_more) {
            showDialog();
        }
    }

    /**
     * 显示更多操作弹窗
     */
    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_item_more, null);
        ((LinearLayout) view.findViewById(R.id.ll_other)).setVisibility(View.GONE);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 1f)
                .setDimAmount(0.25f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .setCancelable(true)    //弹窗是否可以取消
                .setGravity(Gravity.BOTTOM)     //设置弹窗展示位置
                .addOnClickListener(R.id.tv_put, R.id.tv_share, R.id.tv_nfc, R.id.tv_cancel)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_put:
                                ToastUtil.showShort("投送");
                                break;
                            case R.id.tv_share:
                                showShareDialog(MyScheduleActivity.this, new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        switch (view.getId()) {
                                            case R.id.tv_confirm:
                                                EditText et = viewHolder.getView(R.id.dialog_comment_content);
                                                ToastUtil.showShort(et.getText().toString());
                                                break;
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_nfc:
                                //将课表的ID传递到ScanActivity
                                Router.build("scan").with("contentid", title.getText()).with("contenttype", "schedule").go(MyScheduleActivity.this);
                                break;
                            case R.id.tv_cancel:
                                break;
                        }
                        tDialog.dismiss();
                    }
                })
                .create()   //创建TDialog
                .show();    //展示;


    }


}
