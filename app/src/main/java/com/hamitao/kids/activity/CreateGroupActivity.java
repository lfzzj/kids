package com.hamitao.kids.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.CheckBoxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

/**
 * 发起群聊
 */
@Route("create_group")
public class CreateGroupActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    TextView more;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.et_groupName)
    EditText etGroupName;

    private CheckBoxAdapter adapter;

    private List<String> ids = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> infosBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        adapter = new CheckBoxAdapter(false);
        if (UserUtil.user() != null) {
            String info = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, "");
            infosBean = GsonUtil.GsonToList(info, DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);
            for (int i = 0; i < infosBean.size(); i++) {
                ids.add(infosBean.get(i).getDeviceid());
                names.add(infosBean.get(i).getBindname());
            }
            if (ids != null && ids.size() != 0) {
                adapter.initDeviceId(ids, names);
            }
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort("请先登录");
        }
    }


    private void initView() {
        title.setText("发起群聊");
        more.setText("完成");
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
                if (etGroupName.getText().toString().trim().equals("")) {
                    ToastUtil.showShort("群组名称不能为空");
                } else {
                    //将选中的机器人拉入群聊
                    more.setClickable(false);
                    JMessageClient.createPublicGroup(etGroupName.getText().toString(), "", new CreateGroupCallback() {
                        @Override
                        public void gotResult(int i, String s, long groupid) {
                            if (i == 0) {
                                Conversation.createGroupConversation(groupid);
                                addUserInGroup(groupid);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                                    }
                                }, 200);
                                //防止多次点击创建多个群聊
                                finish();
                            } else {
                                ToastUtil.showShort("请重新登录");
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }


    //添加成员到群组
    private void addUserInGroup(long groupid) {
        List<String> userList = new ArrayList<>();
        Map<Integer, Boolean> map = adapter.getCheckedMap();
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            if (entry.getValue()) {
                userList.add(ids.get(entry.getKey()));
            }
        }

        JMessageClient.addGroupMembers(groupid, userList, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    ToastUtil.showShort("创建成功");
                } else {
//                    ToastUtil.showShort("添加成员失败");
                }
            }
        });
    }


}
