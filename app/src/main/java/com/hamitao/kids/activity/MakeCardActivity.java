package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.CardAdapter;
import com.hamitao.kids.model.nfccard.NfcModel.ResponseDataObjBean.NFCCardsBean;
import com.hamitao.kids.mvp.nfc.NfcPresenter;
import com.hamitao.kids.mvp.nfc.NfcView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的制卡
 */

@Route("make_card")
public class MakeCardActivity extends BaseActivity implements BGAOnRVItemLongClickListener, NfcView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.ll_none)
    LinearLayout ll_none;


    private CardAdapter adapter;

    private List<NFCCardsBean> list = new ArrayList<>();

    private NfcPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_card);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    private void initData() {
        presenter = new NfcPresenter(this);
        adapter = new CardAdapter(recyclerview);
        adapter.setDeleteCallbakc(new CardAdapter.DeleteCallbakc() {
            @Override
            public void deleteNfc(String costomerid, String nfcid, int position) {
                presenter.deleteNfc(costomerid,nfcid,position);
            }
        });
        presenter.queryMyCard(UserUtil.user().getCustomerid()); //查询我的制卡
        adapter.setOnRVItemLongClickListener(this);
    }

    private void initView() {
        title.setText("我的制卡");
        ((ImageView)findViewById(R.id.iv_none)).setImageResource(R.drawable.bg_none);
        tvNone.setText("还没有制卡内容...快去制卡吧!");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        adapter.setData(list);
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

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {
        vibrate50(); //长按删除后震动

        showConfirmDialog(this, "是否删除制卡?", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        break;
                    case R.id.tv_confirm:
                        presenter.deleteNfc(UserUtil.user().getCustomerid(), adapter.getData().get(position).getNFCID(), position);
                        break;
                }
                tDialog.dismiss();
            }
        });
        return true;
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
    public void setList(List<NFCCardsBean> list) {
        adapter.setData(list);
        ll_none.setVisibility(list.size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void deldteNfc(int position) {
        ToastUtil.showShort("删除成功");
        adapter.removeItem(position);
        ll_none.setVisibility(adapter.getData().size() <= 0 ? View.VISIBLE : View.GONE);
    }


}
