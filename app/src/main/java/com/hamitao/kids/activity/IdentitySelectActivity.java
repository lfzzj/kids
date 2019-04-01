package com.hamitao.kids.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.IdentityAdapter;
import com.hamitao.kids.model.IdentityModel;
import com.hamitao.kids.mvp.identitySelect.IdentitySelectPresenter;
import com.hamitao.kids.mvp.identitySelect.IdentitySelectView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 身份选择
 */

@Route("identity_select")
public class IdentitySelectActivity extends BaseActivity implements IdentitySelectView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;

    private String deviceId = "";
    private String deviceName = "";

    private int index; // 选中身份的ID

    private IdentityAdapter adapter;
    private IdentitySelectPresenter presenter;

    private int[] imgs = {R.drawable.icon_father, R.drawable.icon_mother, R.drawable.icon_grandfather, R.drawable.icon_grandmother, R.drawable.icon_grandpa,
            R.drawable.icon_grandma, R.drawable.icon_uncle, R.drawable.icon_aunt, R.drawable.icon_brother, R.drawable.icon_sister, R.drawable.icon_other};

    private String[] names = {"爸爸", "妈妈", "爷爷", "奶奶", "外公", "外婆", "叔叔", "阿姨", "哥哥", "姐姐", "其他"};

    private List<IdentityModel> list = new ArrayList<>();

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.CustomerInfosBean> infosBeanList;

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> terminalInfosBeans;

    private boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_select);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new IdentitySelectPresenter(this);
        adapter = new IdentityAdapter(recyclerview);
        deviceId = getIntent().getStringExtra("deviceid");
        //根据ID查询机器人
        presenter.queryDeviceRelationByDeviceid(deviceId);
        //身份数据写死处理
        for (int i = 0; i < imgs.length; i++) {
            IdentityModel model = new IdentityModel();
            model.setImg(imgs[i]);
            model.setName(names[i]);
            model.setCheck(false);
            list.add(model);
        }

        adapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, final int position) {
                if (childView.getId() == R.id.cb) {
                    if (position == 10) {
                        adapter.getData().get(position).setCheck(true);
                        for (int j = 0; j < adapter.getData().size(); j++) {
                            if (j != position) {
                                adapter.getData().get(j).setCheck(false);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        showInputDialog(IdentitySelectActivity.this, "请输入与宝宝的关系", new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                if (view.getId() == R.id.tv_confirm) {
                                    String name = ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString();
                                    if (isExistRelation(name)) {
                                        isExist = true;
                                    } else {
                                        isExist = false;
                                        index = position;
                                        adapter.getData().get(position).setName(name);
                                        adapter.notifyItemChanged(position);
                                    }
                                }
                                tDialog.dismiss();
                            }
                        });
                    } else {
                        if (isExistRelation(adapter.getData().get(position).getName())) {
                            isExist = true;
                            //全部取消选中
                            for (int j = 0; j < adapter.getData().size(); j++) {
                                adapter.getData().get(j).setCheck(false);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            isExist = false;
                            if (!adapter.getData().get(position).isCheck()) {
                                index = position;
                                adapter.getData().get(position).setCheck(true);
                                for (int j = 0; j < adapter.getData().size(); j++) {
                                    if (j != position) {
                                        adapter.getData().get(j).setCheck(false);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
        adapter.setData(list);
    }


    /**
     * 判断该关系是否绑定机器人
     * @param name
     * @return
     */
    private boolean isExistRelation(String name) {

        if (infosBeanList == null) {
            return false;
        }
        for (int i = 0; i < infosBeanList.size(); i++) {
            if (infosBeanList.get(i).getBindname().equals(name)) {
                ToastUtil.showShort("该关系已经存在，请选择其他的关系");
                return true;
            }
        }
        return false;
    }

    private void initView() {
        title.setText("与宝贝的关系");
        more.setText("完成");
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //这里返回相应的身份
                //显示弹窗，输入机器人的名字，然后做请求
                if (isExist) {
                    ToastUtil.showShort("请选择未绑定过的关系吧！");
                    return;
                }
                showInputDialog(IdentitySelectActivity.this, "请输入机器人名字", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        EditText et = ((EditText) tDialog.getView().findViewById(R.id.et_name));
                        if (view.getId() == R.id.tv_confirm) {
                            if (et.getText().toString().equals("")) {
                                ToastUtil.showShort("请输入机器人名字！");
                                return;
                            }
                            //绑定机器人
                            presenter.bindDevice(adapter.getData().get(index).getName(), et.getText().toString(), UserUtil.user().getCustomerid(), deviceId);
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
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShortError(msg);
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {

        switch ((String) status) {
            case "query_Relation":
                //设备绑定成功后，查询绑定关系
                ToastUtil.showShort("绑定成功");

                //查询成功后将绑定机器人信息存入本地
                PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Device_Relation, GsonUtil.GsonToString(((DeviceRelationModel.ResponseDataObjBean) data).getRelation().getTerminalInfos()));

                terminalInfosBeans = GsonUtil.GsonToList(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, ""),
                        DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);

                //是否绑定了我们公司的机器人，如果有绑定则开放制卡，课表，等功能。
                PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isBindDevice, terminalInfosBeans.size() <= 0 ? false : true);

                //刷新机器人信息
                RxBus.getInstance().post(RxBusEvent.MEFRAGMENT_REFRESH_DEVICE_INFO);

                //机器人绑定成功
                RxBus.getInstance().post(RxBusEvent.BIND_DEVICE_SUCCESS);

                //生成聊天对话
                if (JMessageClient.getSingleConversation(deviceId) == null) {
                    Conversation.createSingleConversation(deviceId);
                }

                //延迟0.5秒刷新会话列表
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                    }
                }, 500);
                //机器人绑定成功,到机器人页面刷新机器人
                //RxBus.getInstance().post(RxBusEvent.BIND_DEVICE_SUCCESS);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void getDeviceRelation(DeviceRelationModel.ResponseDataObjBean bean) {
        infosBeanList = bean.getRelation().getCustomerInfos();
    }


    /**
     * 请求添加好友
     * @param deviceId
     * @param bindName 用户昵称
     * @param customerid
     * @param devicename
     */
    @Override
    public void requestAddFriend(String deviceId, String bindName, String customerid,String devicename) {

        //发送好友请求，要添成功添加好友才能添加备注名字
        ContactManager.sendInvitationRequest(deviceId, getString(R.string.JPUSH_APPKEY), bindName, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    LogUtil.d("绑定机器人", "好友请求请求发送成功");
                } else {
                    JMessageClient.getUserInfo(deviceId, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            userInfo.updateNoteName(devicename, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (0 == i) {
                                        LogUtil.d("绑定机器人", "备注名修改成功");
                                    } else {
                                        LogUtil.d("绑定机器人", "备注名修改失败");
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        presenter.queryRelationById(customerid);
    }
}
