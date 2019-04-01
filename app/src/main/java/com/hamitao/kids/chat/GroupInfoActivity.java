package com.hamitao.kids.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.ResultCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 群组消息
 */
@Route("group_info")
public class GroupInfoActivity extends BaseActivity implements BGAOnRVItemLongClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_groupName)
    TextView tvGroupName;
    @BindView(R.id.btn_dissolvegroup)
    TextView dissolvergroup;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private long groupId;

    private GroupmemberAdapter adapter;

    private GroupInfo info = null;

    private boolean isKeeper; //是否群主


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_GROUP_CODE) {
            if (info != null) {
                adapter.setData(info.getGroupMembers());
            }
        }
    }

    private void initData() {

        adapter = new GroupmemberAdapter(recyclerview);

        adapter.setOnRVItemLongClickListener(this);

        groupId = getIntent().getLongExtra("groupId", 0);

        JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (i == 0) {
                    isKeeper = groupInfo.getGroupOwner().equals(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, ""));
                    //获取群消息成功
                    info = groupInfo;
                    adapter.setData(groupInfo.getGroupMembers());
                    tvGroupName.setText(groupInfo.getGroupName());
                    dissolvergroup.setText(isKeeper ? "退出并解散群组" : "退出群组");
                }
            }
        });
    }


    private void initView() {
        title.setText("群聊信息");
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        recyclerview.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.tv_createCode, R.id.rl_groupname, R.id.tv_clear, R.id.btn_dissolvegroup})
    public void onViewClicked(final View view) {

        if (view.getId() == R.id.back) {

            finish();

        } else if (view.getId() == R.id.tv_createCode) {

            Router.build("create_code").requestCode(RequestCode.REQUEST_GROUP_CODE).with("groupId", groupId).go(this);

        } else if (view.getId() == R.id.rl_groupname) {

            showInputDialog(this, "请输入新的群组名", new OnViewClickListener() {
                @Override
                public void onViewClick(BindViewHolder viewHolder, View view, final TDialog tDialog) {
                    final EditText et = tDialog.getView().findViewById(R.id.et_name);
                    if (view.getId() == R.id.tv_confirm) {
                        if (et.getText().toString().equals("")) {
                            ToastUtil.showShort("信息不可为空！");
                        } else {
                            //这里进行群名更改
                            JMessageClient.getGroupInfo(groupId, new GetGroupInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, GroupInfo groupInfo) {
                                    if (i == 0) {
                                        //获取群消息成功
                                        groupInfo.updateName(et.getText().toString(), new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 0) {
                                                    //修改群名成功
                                                    ToastUtil.showShort("修改群名成功！");
                                                    tvGroupName.setText(et.getText().toString());
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            tDialog.dismiss();
                        }
                    } else {
                        tDialog.dismiss();
                    }
                }
            }, new OnBindViewListener() {
                @Override
                public void bindView(BindViewHolder viewHolder) {
                    ((EditText) viewHolder.getView(R.id.et_name)).setHint("不超过10个字符");
                }
            });
        } else if (view.getId() == R.id.tv_clear) {
            showConfirmDialog(this, "确定要清空聊天内容吗？", new OnViewClickListener() {
                @Override
                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                    if (view.getId() == R.id.tv_confirm) {
                        //清空所有消息
                        JMessageClient.getGroupConversation(groupId).deleteAllMessage();
                        setResult(ResultCode.RESULT_CLEAR_MESSAGE);
                        ToastUtil.showShort("已清空所有聊天记录");
                    }
                    tDialog.dismiss();
                }
            });
        } else if (view.getId() == R.id.btn_dissolvegroup) {

            showConfirmDialog(this, isKeeper ? "确定要退出并解散群组吗?" : "确定要退出群组吗?", new OnViewClickListener() {
                @Override
                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                    if (view.getId() == R.id.tv_confirm) {
                        if (isKeeper) {
                            JMessageClient.adminDissolveGroup(groupId, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        ToastUtil.showShort("已解散群组!");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                JMessageClient.deleteGroupConversation(groupId);
                                                RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                                            }
                                        }, 250);
                                    }
                                }
                            });
                        } else {
                            JMessageClient.exitGroup(groupId, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        ToastUtil.showShort("已退群组!");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                JMessageClient.deleteGroupConversation(groupId);
                                                RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                                            }
                                        }, 250);
                                    }
                                }
                            });
                        }
                        setResult(ResultCode.RESULT_FINISH_CHAT, new Intent().putExtra("groupid", groupId));
                        finish();
                    }
                    tDialog.dismiss();
                }
            });


        }
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {

        vibrate50();
        //长按移除群聊
        showConfirmDialog(this, "确定将 " + adapter.getData().get(position).getDisplayName() + " 移除群聊吗?", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    JMessageClient.removeGroupMembers(groupId, Arrays.asList(new String[]{adapter.getData().get(position).getUserName()}), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                adapter.removeItem(position);
                            } else {
                                ToastUtil.showShort("移除失败");
                            }
                        }
                    });
                }
                tDialog.dismiss();
            }
        });
        return true;
    }


    //群组成员适配器
    class GroupmemberAdapter extends BGARecyclerViewAdapter<UserInfo> {

        public GroupmemberAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.chat_item_groupmember);
        }

        @Override
        protected void fillData(final BGAViewHolderHelper helper, int position, UserInfo model) {

            //获取成员头像
            model.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    if (i == 0) {
                        helper.getImageView(R.id.iv_face).setImageBitmap(bitmap);
                    } else {
                        helper.getImageView(R.id.iv_face).setImageResource(R.drawable.icon_avarta_circle);
                    }
                }
            });
            helper.getTextView(R.id.tv_name).setText(model.getDisplayName());
        }
    }
}
