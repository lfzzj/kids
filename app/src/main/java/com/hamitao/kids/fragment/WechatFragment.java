package com.hamitao.kids.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.ResultCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseFragment;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.BGARefreshUtil;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.ConversationAdapter;
import com.hamitao.kids.network.NetworkRequest;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.functions.Consumer;

import static com.hamitao.base_module.util.BGARefreshUtil.getBGAMeiTuanRefreshViewHolder;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class WechatFragment extends BaseFragment implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    View view;
    Unbinder unbinder;


    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    ImageView more;

    @BindView(R.id.tv_bind)
    TextView tv_bind;

    @BindView(R.id.rl_none)
    RelativeLayout rl_none;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    @BindView(R.id.tv)
    TextView tvNone;


    @BindView(R.id.title_bar)
    RelativeLayout title_bar;

    private List<Conversation> list = new ArrayList<>();

    private ConversationAdapter adapter;

    private UnReadMsgListener listener;

    private BaseActivity baseActivity;

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> infosBeanList;

    public interface UnReadMsgListener {
        //刷新未读消息数
        void refreshUnReadMsg();
    }

    public void setUnReadMsgListener(UnReadMsgListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wechat, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        refreshConversationList();
        return view;
    }


    private void initData() {
        baseActivity = (BaseActivity) getActivity();
        adapter = new ConversationAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);
        adapter.setOnRVItemLongClickListener(this);
        JMessageClient.registerEventReceiver(this);
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String info) throws Exception {
                        if (info.equals(RxBusEvent.REFRESH_CONVERSATION_LIST)) {
                            //更新了机器人信息，通知列表改变
                            refreshConversationList();
                        }
                    }
                });
    }


    /**
     * 显示没有会话的背景
     */
    public void showNoneConversationBg() {
        rl_none.setVisibility(View.VISIBLE);
        adapter.clear();
    }


    public void getDeviceInfo() {
        NetworkRequest.queryDeviceRelationRequest(UserUtil.user().getCustomerid(), new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);
                setConversationList(bean);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                showNoneConversationBg();
            }

            @Override
            public void onBegin() {
            }

            @Override
            public void onEnd() {
            }
        }));
    }


    /**
     * 设置聊天会话列表
     *
     * @param bean
     */
    private void setConversationList(DeviceRelationModel.ResponseDataObjBean bean) {
        infosBeanList = bean.getRelation().getTerminalInfos();
        //查询成功后后将机器人关系存入本地
        PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Device_Relation, GsonUtil.GsonToString(bean.getRelation().getTerminalInfos()));
        //获取会话列表
        list = JMessageClient.getConversationList();
        //当list为空的时候说明极光IM被别的账号顶下线了，无法获取list
        if (list == null) {
            showNoneConversationBg();
            return;
        }

        List<String> deviceids = new ArrayList<>(); // 用来存放所有机器人的ID

        //村放会话的列表
        List<Conversation> conList = new ArrayList<>();

        //遍历会话，将
        for (int i = 0; i < infosBeanList.size(); i++) {
            String deviceid = infosBeanList.get(i).getDeviceid();
            deviceids.add(deviceid);
            //会话ID与机器人ID一致，创建会话
            Conversation conversation = JMessageClient.getSingleConversation(deviceid);
            if (conversation == null) {
                Conversation.createSingleConversation(infosBeanList.get(i).getDeviceid());
            }
            //如果机器人名字不于备注名字相同，则重新发送好友请求或者直接命名
            if (!infosBeanList.get(i).getBindname().equals(((UserInfo) conversation.getTargetInfo()).getDisplayName())) {
                //发送好友请求，要添成功添加好友才能添加备注名字
                final String id = infosBeanList.get(i).getDeviceid();
                final String deviceiName = infosBeanList.get(i).getBindname();
                ContactManager.sendInvitationRequest(id, getString(R.string.JPUSH_APPKEY) ,"reason", new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (0 == responseCode) {
                            LogUtil.d("绑定机器人", "好友请求请求发送成功");
                        } else {
                            JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    userInfo.updateNoteName(deviceiName, new BasicCallback() {
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
            }
            conList.add(conversation);
        }

        //存放会话列表
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equals(ConversationType.group)) {
                conList.add(list.get(i));
            } else {
                String userId = ((UserInfo) list.get(i).getTargetInfo()).getUserName();
                if (!deviceids.contains(userId)) {
                    JMessageClient.deleteSingleConversation(userId);
                }
            }
        }

        if (conList.size() == 0) {
            showNoneConversationBg();
        } else {
            rl_none.setVisibility(View.GONE);
            adapter.setData(conList);
        }

        listener.refreshUnReadMsg();
    }


    public void onEventMainThread(MessageEvent event) {
        adapter.notifyDataSetChanged();
    }

    public void onEvent(MessageEvent event) { }

    @Override
    public void onResume() {
        super.onResume();
        refreshConversationList();
    }

    private void initView() {
        back.setVisibility(View.GONE);
        title.setText("聊天");
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        //设置下拉刷新监听
        refreshLayout.setDelegate(this);
        //设置下拉样式
        refreshLayout.setRefreshViewHolder(getBGAMeiTuanRefreshViewHolder(baseActivity));
    }

    //显示弹窗
    private void initPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.chat_popwindow, null, false);

        TextView group = view.findViewById(R.id.tv_group);

        TextView scan = view.findViewById(R.id.tv_scan);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置加载动画
        popWindow.setAnimationStyle(R.anim.chat_popwindow);

        popWindow.setTouchable(true);

        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //要为popWindow设置一个背景才有效
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(title_bar, title_bar.getWidth() - view.getMeasuredWidth(), -40, Gravity.START);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (infosBeanList == null) {
                    return;
                }

                if (infosBeanList.size() <= 0) {
                    //绑定机器人数为0
                    ToastUtil.showShort("必须绑定机器人才可以使用哦");
                    return;
                }

                Router.build("create_group").requestCode(RequestCode.REQUEST_CREATE_GROUP).go(WechatFragment.this);

                popWindow.dismiss();
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Router.build("scan").go(WechatFragment.this);

                popWindow.dismiss();
            }
        });
    }


    /**
     * 刷新列表
     */
    public void refreshConversationList() {
        if (UserUtil.user() == null) {
            showNoneConversationBg();
            return;
        }
        getDeviceInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResultCode.RESULT_REFRESH_UNREAD_MESSAGE) {
            listener.refreshUnReadMsg();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.more, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more:
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    initPopWindow();
                }
                break;
            case R.id.tv_bind:
                if (UserUtil.user() == null) {
                    ToastUtil.showShort("请先登录");
                } else {
                    Router.build("scan").go(this);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 点击事件
     */
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        if (JMessageClient.getMyInfo() == null) {
            ToastUtil.showShort("请重新登录");
            return;
        }
        Conversation conv = adapter.getData().get(position);


        if (conv.getType().equals(ConversationType.single)) {
            String channelid = "";
            String nickname = "";
            String terminalid = "";
            //单聊
            final String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
            for (int i = 0; i < infosBeanList.size(); i++) {
                if (infosBeanList.get(i).getDeviceid().equals(targetId)) {
                    terminalid = infosBeanList.get(i).getTerminalid();
                    channelid = infosBeanList.get(i).getChannelid_listen_on_pushserver();
                    nickname = infosBeanList.get(i).getBindname();
                }
            }
            Router.build("wechat")
                    .requestCode(ResultCode.RESULT_REFRESH_UNREAD_MESSAGE)
                    .with(Constant.CONV_TITLE, nickname)
                    .with(Constant.TARGET_APP_KEY, getString(R.string.JPUSH_APPKEY))
                    .with(Constant.TARGET_ID, targetId)
                    .with("channelid", channelid)
                    .with("terminalid", terminalid)
                    .go(this);

        } else {
            //群组为空，即该群不存在
            GroupInfo groupInfo = (GroupInfo) conv.getTargetInfo();
            long grouid = groupInfo.getGroupID();
            Message msg = conv.getLatestMessage();
            if (msg != null && msg.getContentType().equals(ContentType.eventNotification) && ((EventNotificationContent) msg.getContent()).getEventText().equals("群组已解散")) {
                ToastUtil.showShort("该群已解散");
                JMessageClient.deleteGroupConversation(grouid);
                refreshConversationList();
            } else {
                Router.build("wechat")
                        .requestCode(ResultCode.RESULT_REFRESH_UNREAD_MESSAGE)
                        .with(Constant.CONV_TITLE, conv.getTitle()).with("isSingle", false)
                        .with(Constant.TARGET_APP_KEY, getString(R.string.JPUSH_APPKEY))
                        .with(Constant.GROUP_ID, grouid)
                        .go(this);
            }
        }
    }


    public void onEvent(NotificationClickEvent event) {
        //点击通知栏后跳转到相应的对聊页
//
//        if (event.getMessage().getTargetType().equals(ConversationType.single)){
//            UserInfo userInfo = event.getMessage().getFromUser();
//            Router.build("wechat")
//                    .requestCode(ResultCode.RESULT_REFRESH_UNREAD_MESSAGE)
//                    .with(Constant.CONV_TITLE, userInfo.getNickname())
//                    .with(Constant.TARGET_APP_KEY, Constant.JPUSH_APPKEY)
//                    .with(Constant.TARGET_ID, userInfo.getUserName())
////                    .with("channelid", channelid)
////                    .with("terminalid", terminalid)
//                    .go(this);
//        }else {
//            GroupInfo groupInfo = (GroupInfo)event.getMessage().getTargetInfo();
//            long grouid = groupInfo.getGroupID();
//            if (groupInfo.getGroupOwner() == null) {
//                ToastUtil.showShort("该群已解散");
//                JMessageClient.deleteGroupConversation(grouid);
//                refreshConversationList();
//            } else {
//                Router.build("wechat")
//                        .requestCode(ResultCode.RESULT_REFRESH_UNREAD_MESSAGE)
//                        .with(Constant.CONV_TITLE, groupInfo.getGroupName()).with("isSingle", false)
//                        .with(Constant.TARGET_APP_KEY, Constant.JPUSH_APPKEY)
//                        .with(Constant.GROUP_ID, grouid)
//                        .go(this);
//            }
//        }
    }


    /**
     * 长按事件
     *
     * @param parent
     * @param itemView
     * @param position
     * @return
     */
    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {

        baseActivity.vibrate50();
        if (!adapter.getData().get(position).getType().equals(ConversationType.single)) {
            ((BaseActivity) getActivity()).showConfirmDialog(getActivity(), "是否删除对话消息?", new OnViewClickListener() {
                @Override
                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {

                    if (view.getId() == R.id.tv_confirm) {

                        if (adapter.getData().get(position).getType().equals(ConversationType.group)) {

                            JMessageClient.deleteGroupConversation(Long.parseLong(adapter.getData().get(position).getTargetId()));

                        } else {

                            JMessageClient.deleteSingleConversation(((UserInfo) (adapter.getData().get(position).getTargetInfo())).getUserName());
                        }

                        adapter.getData().get(position).resetUnreadCount();

                        adapter.removeItem(position);

                    }
                    tDialog.dismiss();
                }
            });
        }
        return false;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
        refreshConversationList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BGARefreshUtil.completeRequest(refreshLayout);
            }
        }, 1000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }


}
