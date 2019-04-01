package com.hamitao.kids.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.ContactAdapter;
import com.hamitao.kids.model.ContactModel;
import com.hamitao.kids.mvp.contact.ContactPresenter;
import com.hamitao.kids.mvp.contact.ContactView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 联系人
 */
@Route("contact")
public class ContactActivity extends BaseActivity implements ContactView, BGAOnItemChildClickListener, BGAOnRVItemClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    ImageView more;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.ll_none)
    LinearLayout ll_none;

    @BindView(R.id.tv_none)
    TextView tvNone;

    private ContactPresenter presenter;

    private ContactAdapter adatper;

    private String terminalid;

    private String channelid; //推送ID

    private int EDIT_CONTACT = 1;
    private int ADD_CONTACT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        initData();
        initView();

        adatper.setOnRVItemClickListener(this);
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
        presenter = new ContactPresenter(this);
        terminalid = getIntent().getStringExtra("terminalid");
        channelid = getIntent().getStringExtra("channelid");
        presenter.queryContact(terminalid);
        // 刷新联系人列表
        adatper = new ContactAdapter(recyclerview);
        adatper.setOnItemChildClickListener(this);


    }

    private void initView() {
        title.setText("机器人电话本");
        tvNone.setText("还没有联系人哦...快添加联系人吧！");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adatper);
    }

    /**
     * 显示更多选项弹窗
     */
    private void showMoreDialog(final int positon) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_contact)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_edit, R.id.tv_delete)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_edit:
                                //编辑弹窗
                                showEditDialog(positon, "请输入修改信息", EDIT_CONTACT);
                                break;
                            case R.id.tv_delete:
                                showConfirmDialog(ContactActivity.this, "确定要删除“" + adatper.getData().get(positon).getContactname() + "”吗?", new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            presenter.deleteContact(terminalid, adatper.getData().get(positon).getContactid(), positon);
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                        }
                        tDialog.dismiss();
                    }
                })
                .create().show();    //展示
    }


    /**
     * 编辑联系人弹窗
     * @param position
     * @param title
     * @param type
     */
    private void showEditDialog(final int position, String title, final int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_contact_edit, null);
        final EditText et_name = view.findViewById(R.id.et_name);
        final EditText et_phone = view.findViewById(R.id.et_phone);
        if (adatper.getData().size() >= 0 && type != ADD_CONTACT) {
            et_name.setText(adatper.getData().get(position).getContactname());
            et_phone.setText(adatper.getData().get(position).getPhonenum());
            et_name.setSelection(et_name.getText().toString().length());
            et_phone.setSelection(et_phone.getText().toString().length());
        }
        ((TextView) view.findViewById(R.id.title)).setText(title);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.8f)
                .setGravity(Gravity.CENTER)
                .setDimAmount(0.25f)
                .setCancelableOutside(false)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                            case R.id.tv_confirm:
                                //添加联系人
                                if (et_name.getText().toString().equals("") || et_phone.getText().toString().equals("")) {
                                    ToastUtil.showShort("信息不能为空");
                                } else {
                                    if (type == ADD_CONTACT) {
                                        presenter.addContact(terminalid, et_name.getText().toString(), et_phone.getText().toString(), ADD_CONTACT);
                                    } else {
                                        presenter.updateContact(position, terminalid, adatper.getData().get(position).getContactid(),
                                                et_name.getText().toString(), et_phone.getText().toString());
                                    }
                                    tDialog.dismiss();
                                }
                                break;
                        }
                    }
                }).create().show();    //展示
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //显示添加联系人弹窗
                showEditDialog(0, "请输入联系人信息", ADD_CONTACT);
                break;
        }
    }


    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.contact_more) {
            showMoreDialog(position);
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
    public void getContactList(List<ContactModel.ResponseDataObjBean.ContactsBean> contacts) {
        adatper.setData(contacts);
        ll_none.setVisibility(contacts.size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addContact(ContactModel.ResponseDataObjBean.ContactsBean contactsBean) {
        ToastUtil.showShort("添加成功");
        presenter.pushMessage(channelid, PushParams.UPDATA_CONTACT, null);
        adatper.addItem(0, contactsBean);
        ll_none.setVisibility(adatper.getData().size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void deleteContact(int position) {
        ToastUtil.showShort("删除成功");
        presenter.pushMessage(channelid, PushParams.UPDATA_CONTACT, null);
        adatper.removeItem(position);
        ll_none.setVisibility(adatper.getData().size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshContact(int position, String name, String phone) {
        ToastUtil.showShort("修改成功");
        presenter.pushMessage(channelid, PushParams.UPDATA_CONTACT, null);
        adatper.getData().get(position).setContactname(name);
        adatper.getData().get(position).setPhonenum(phone);
        adatper.notifyItemChanged(position);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {

    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, final int position) {
        showConfirmDialog(this, "是否要给 " + adatper.getData().get(position).getContactname() + " 打电话?", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + adatper.getData().get(position).getPhonenum());
                    intent.setData(data);
                    if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);
                }
                tDialog.dismiss();
            }
        });
    }
}
