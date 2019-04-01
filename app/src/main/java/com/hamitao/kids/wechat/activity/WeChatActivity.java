package com.hamitao.kids.wechat.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.ResultCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.video.VideoChatMessageModel;
import com.hamitao.kids.wechat.ChatView;
import com.hamitao.kids.wechat.ChattingListAdapter;
import com.hamitao.kids.wechat.DropDownListView;
import com.hamitao.kids.wechat.chatutil.IdHelper;
import com.hamitao.kids.wechat.chatutil.StorageType;
import com.hamitao.kids.wechat.chatutil.StorageUtil;
import com.hamitao.kids.wechat.event.Event;
import com.hamitao.kids.wechat.event.EventType;
import com.hamitao.kids.wechat.event.ImageEvent;
import com.hamitao.kids.wechat.keyboard.utils.SimpleCommonUtils;
import com.hamitao.kids.wechat.view.EmoticonsEditText;
import com.hamitao.kids.wechat.view.SimpleAppsGridView;
import com.hamitao.kids.wechat.view.TipItem;
import com.hamitao.kids.wechat.view.TipView;
import com.hamitao.kids.wechat.view.XhsEmoticonsKeyBoard;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.GroupApprovalEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;


/**
 * Created by ${chenyn} on 2017/3/26.
 */

@Route("wechat")
public class WeChatActivity extends BaseActivity implements View.OnClickListener, XhsEmoticonsKeyBoard.VideoClickCallBack {

    @BindView(R.id.lv_chat)
    DropDownListView lvChat;

    @BindView(R.id.ek_bar)
    XhsEmoticonsKeyBoard ekBar;

    @BindView(R.id.chat_view)
    ChatView mChatView;

    public static final String JPG = ".jpg";

    private static String MsgIDs = "msgIDs";

    private String mTitle;

    private boolean mLongClick = false;

    private static final String MEMBERS_COUNT = "membersCount";

    private static final String GROUP_NAME = "groupName";

    public static final String TARGET_ID = "targetId";

    public static final String TARGET_APP_KEY = "targetAppKey";

    private static final String DRAFT = "draft";

    private static final String GROUP_ID = "groupId";  //群ID

    int maxImgCount = 9;

    public static final int REQUEST_CODE_SELECT = 100;

    private boolean mIsSingle = true;

    private Conversation mConv;

    private String mTargetId;

    private String mTargetAppKey;

    private Activity mContext;

    private ChattingListAdapter mChatAdapter;

    private List<UserInfo> mAtList;

    private long mGroupId;

    private static final int REFRESH_LAST_PAGE = 0x1023;
    private static final int REFRESH_CHAT_TITLE = 0x1024;
    private static final int REFRESH_GROUP_NAME = 0x1025;
    private static final int REFRESH_GROUP_NUM = 0x1026;

    private Dialog mDialog;

    private GroupInfo mGroupInfo; //群信息

    private UserInfo mMyInfo;  //用户信息

    private int mAtMsgId;

    private int mAtAllMsgId;

    private int mUnreadMsgCnt;

    private boolean mShowSoftInput = false;

    private List<UserInfo> forDel = new ArrayList<>();

    Window mWindow;

    InputMethodManager mImm;

    private final UIHandler mUIHandler = new UIHandler(this);

    private boolean mAtAll = false;

    private String channelid;
    private String terminalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        JMessageClient.registerEventReceiver(this);
        mContext = this;
        ButterKnife.bind(this);
        mChatView.initModule(mDensity, mDensityDpi);
        this.mWindow = getWindow();
        this.mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mChatView.setListeners(this);
        initData();
        initView();
    }

    private void initData() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        Intent intent = getIntent();
        mTargetId = intent.getStringExtra(TARGET_ID);
        mTargetAppKey = intent.getStringExtra(TARGET_APP_KEY);
        mTitle = intent.getStringExtra(Constant.CONV_TITLE);
        channelid = intent.getStringExtra("channelid");
        terminalid = intent.getStringExtra("terminalid");
        ekBar.setChannelid(channelid);
        ekBar.setVideoClickCallBack(this);
        mMyInfo = JMessageClient.getMyInfo();
        if (!TextUtils.isEmpty(mTargetId)) {
            //单聊
            mIsSingle = true;
            mChatView.setChatTitle(mTitle);
            mChatView.dismissRightBtn();
            mChatView.showVideoButton();
            mConv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
            if (mConv == null) {
                mConv = Conversation.createSingleConversation(mTargetId, mTargetAppKey);
            }
//            mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);//长按聊天内容监听
            mChatAdapter = new ChattingListAdapter(mContext, mConv, null, channelid);//长按聊天内容监听
            mChatView.showRightBtn();
            mChatView.setSingleIcon();
        } else {
            //群聊
            mIsSingle = false;
            mChatView.hindVIdeoButton();
            mGroupId = intent.getLongExtra(GROUP_ID, 0);
            final boolean fromGroup = intent.getBooleanExtra("fromGroup", false);
            if (fromGroup) {
                mChatView.setChatTitle(mTitle, intent.getIntExtra(MEMBERS_COUNT, 0));
                mConv = JMessageClient.getGroupConversation(mGroupId);
//                mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);//长按聊天内容监听
                mChatAdapter = new ChattingListAdapter(mContext, mConv, null, channelid);//长按聊天内容监听
            } else {
                mAtMsgId = intent.getIntExtra("atMsgId", -1);
                mAtAllMsgId = intent.getIntExtra("atAllMsgId", -1);
                mConv = JMessageClient.getGroupConversation(mGroupId);
                if (mConv != null) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(mMyInfo.getUserName(), mMyInfo.getAppKey());
                    //如果自己在群聊中，聊天标题显示群人数
                    if (userInfo != null) {
                        if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
                            mChatView.setChatTitle(mTitle, groupInfo.getGroupMembers().size());
                        } else {
                            mChatView.setChatTitle(mTitle, groupInfo.getGroupMembers().size());
                        }
                        mChatView.showRightBtn();
                    } else {
                        if (!TextUtils.isEmpty(mTitle)) {
                            mChatView.setChatTitle(mTitle);
                        } else {
                            mChatView.setChatTitle("群组");
                        }
                        mChatView.dismissRightBtn();
                    }
                } else {
                    mConv = Conversation.createGroupConversation(mGroupId);
                }
                //更新群名
                JMessageClient.getGroupInfo(mGroupId, new GetGroupInfoCallback(false) {
                    @Override
                    public void gotResult(int status, String desc, GroupInfo groupInfo) {
                        if (status == 0) {
                            mGroupInfo = groupInfo;
                            mUIHandler.sendEmptyMessage(REFRESH_CHAT_TITLE);
                        }
                    }
                });
                if (mAtMsgId != -1) {
                    mUnreadMsgCnt = mConv.getUnReadMsgCnt();
                    // 如果 @我 的消息位于屏幕显示的消息之上，显示 有人@我 的按钮
                    if (mAtMsgId + 8 <= mConv.getLatestMessage().getId()) {
                        mChatView.showAtMeButton();
                    }
//                    mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener, mAtMsgId);
                    mChatAdapter = new ChattingListAdapter(mContext, mConv, null, mAtMsgId, channelid);
                } else {
//                    mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);
                    mChatAdapter = new ChattingListAdapter(mContext, mConv, null, channelid);
                }

            }
            //聊天信息标志改变
            mChatView.setGroupIcon();
        }

        String draft = intent.getStringExtra(DRAFT);
        if (draft != null && !TextUtils.isEmpty(draft)) {
            ekBar.getEtChat().setText(draft);
        }

        mChatView.setChatListAdapter(mChatAdapter);
//        mChatAdapter.initMediaPlayer();
        mChatView.getListView().setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                mUIHandler.sendEmptyMessageDelayed(REFRESH_LAST_PAGE, 1000);
            }
        });
        mChatView.setToBottom();
        mChatView.setConversation(mConv);

//        RxBus.getInstance().toObservable(VideoChatMessageModel.class).subscribe(new Consumer<VideoChatMessageModel>() {
//            @Override
//            public void accept(VideoChatMessageModel model) throws Exception {
//                switch (model.getStatus()) {
//                    case RxBusEvent.CHAT_MESSAGE_CANCEL:
//                        sendMessage("已取消");
//                        break;
//                    case RxBusEvent.CHAT_MESSAGE_REFUSE:
//                        sendMessage("已拒绝");
//                        break;
//                    case RxBusEvent.CHAT_MESSAGE_DURATION:
//                        sendMessage("通话时长 " + model.getContent());
//                        break;
//                }
//            }
//        });


//
//        RxBus.getInstance().toObservable(VideoChatMessageModel.class).
//                subscribe(new Observer<VideoChatMessageModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(VideoChatMessageModel model) {
//                        switch (model.getStatus()) {
//                            case RxBusEvent.CHAT_MESSAGE_CANCEL:
//                                sendMessage("已取消");
//                                break;
//                            case RxBusEvent.CHAT_MESSAGE_REFUSE:
//                                sendMessage("已拒绝");
//                                break;
//                            case RxBusEvent.CHAT_MESSAGE_DURATION:
//                                sendMessage("通话时长 " + model.getContent());
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    public void onEventMainThread(VideoChatMessageModel model) {
        switch (model.getStatus()) {
            case RxBusEvent.CHAT_MESSAGE_CANCEL:
                sendMessage("已取消");
                break;
            case RxBusEvent.CHAT_MESSAGE_REFUSE:
                sendMessage("已拒绝");
                break;
            case RxBusEvent.CHAT_MESSAGE_DURATION:
                sendMessage("通话时长 " + model.getContent());
                break;
            case RxBusEvent.CHAT_LINE_BUSY:
                sendMessage("对方忙");
                break;
            default:
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        initEmoticonsKeyBoardBar();
        initListView();

        ekBar.getEtChat().addTextChangedListener(new TextWatcher() {
            private CharSequence temp = "";

            @Override
            public void afterTextChanged(Editable arg0) {
                if (temp.length() > 0) {
                    mLongClick = false;
                }
                if (mAtList != null && mAtList.size() > 0) {
                    for (UserInfo info : mAtList) {
                        String name = info.getDisplayName();
                        if (!arg0.toString().contains("@" + name + " ")) {
                            forDel.add(info);
                        }
                    }
                    mAtList.removeAll(forDel);
                }
                if (!arg0.toString().contains("@所有成员 ")) {
                    mAtAll = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                temp = s;
//                if (s.length() > 0 && after >= 1 && s.subSequence(start, start + 1).charAt(0) == '@' && !mLongClick) {
//                    if (null != mConv && mConv.getType() == ConversationType.group) {
//
//                        ToastUtil.showShort("选择成员");
//                        ChooseAtMemberActivity.show(WeChatActivity.this, ekBar.getEtChat(), mConv.getTargetId());
//                    }
//                }
            }
        });
    }

    //初始化键盘
    private void initEmoticonsKeyBoardBar() {
//        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));

        SimpleAppsGridView gridView = new SimpleAppsGridView(this);
        ekBar.addFuncView(gridView);
        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        //发送按钮
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(ekBar.getEtChat().getText().toString());
//                String mcgContent = ekBar.getEtChat().getText().toString();
//                scrollToBottom();
//                if (mcgContent.equals("")) {
//                    return;
//                }
//                Message msg;
//                TextContent content = new TextContent(mcgContent);
//                if (mAtAll) {
//                    msg = mConv.createSendMessageAtAllMember(content, null);
//                    mAtAll = false;
//                } else if (null != mAtList) {
//                    msg = mConv.createSendMessage(content, mAtList, null);
//                } else {
//                    msg = mConv.createSendMessage(content);
//                }
//                //设置需要已读回执
//                MessageSendingOptions options = new MessageSendingOptions();
//                options.setNeedReadReceipt(true);
//                JMessageClient.sendMessage(msg, options);
//                mChatAdapter.addMsgFromReceiptToList(msg);
//                ekBar.getEtChat().setText("");
//                if (mAtList != null) {
//                    mAtList.clear();
//                }
//                if (forDel != null) {
//                    forDel.clear();
//                }
            }
        });
        //切换语音输入
        ekBar.getVoiceOrText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.btn_voice_or_text) {
                    ekBar.setVideoText();
                    ekBar.getBtnVoice().initConv(mConv, mChatAdapter, mChatView);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jmui_return_btn:
                returnBtn();
                break;
            case R.id.jmui_right_btn:
//                startChatDetailActivity(mTargetId, mTargetAppKey, mGroupId);
                //跳转到群聊详情
                if (mIsSingle) {
                    Router.build("single_info")
                            .with("terminalid", terminalid)
                            .with("devicename", mTitle)
                            .with("deviceid", mConv.getTargetId())
                            .requestCode(RequestCode.REQUEST_CHAT)
                            .go(this);
                } else {
                    Router.build("group_info").requestCode(RequestCode.REQUEST_CHAT).with("groupId", mGroupId).go(this);
                }

                break;
            case R.id.jmui_at_me_btn:
                if (mUnreadMsgCnt < ChattingListAdapter.PAGE_MESSAGE_COUNT) {
                    int position = ChattingListAdapter.PAGE_MESSAGE_COUNT + mAtMsgId - mConv.getLatestMessage().getId();
                    mChatView.setToPosition(position);
                } else {
                    mChatView.setToPosition(mAtMsgId + mUnreadMsgCnt - mConv.getLatestMessage().getId());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnBtn();
    }

    private void returnBtn() {
        mConv.resetUnreadCount();
        setResult(ResultCode.RESULT_REFRESH_UNREAD_MESSAGE);
        dismissSoftInput();
        JMessageClient.exitConversation();
        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
        //发送保存为草稿事件到会话列表
        EventBus.getDefault().post(new Event.Builder().setType(EventType.draft)
                .setConversation(mConv)
                .setDraft(ekBar.getEtChat().getText().toString())
                .build());
        finish();
    }

    private void dismissSoftInput() {
        if (mShowSoftInput) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(ekBar.getEtChat().getWindowToken(), 0);
                mShowSoftInput = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startChatDetailActivity(String targetId, String appKey, long groupId) {
        ToastUtil.showShort("跳转到聊天详情页面");
     /*   Intent intent = new Intent();
        intent.putExtra(TARGET_ID, targetId);
        intent.putExtra(TARGET_APP_KEY, appKey);
        intent.putExtra(GROUP_ID, groupId);
        intent.setClass(this, ChatDetailActivity.class);
        startActivityForResult(intent, JGApplication.REQUEST_CODE_CHAT_DETAIL);*/
    }

   /* EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
                        OnSendImage(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };*/


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (EmoticonsKeyboardUtils.isFullScreen(this)) {
            boolean isConsum = ekBar.dispatchKeyEventInFullScreen(event);
            return isConsum ? isConsum : super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    private void initListView() {
        lvChat.setAdapter(mChatAdapter);
        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        ekBar.reset();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    private void scrollToBottom() {
        lvChat.requestLayout();
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JMessageClient.exitConversation();
        ekBar.reset();
    }

    @Override
    protected void onResume() {
        String targetId = getIntent().getStringExtra(TARGET_ID);
        if (!mIsSingle) {
            long groupId = getIntent().getLongExtra(GROUP_ID, 0);
            if (groupId != 0) {
                BaseApplication.isAtMe.put(groupId, false);
                BaseApplication.isAtall.put(groupId, false);
                JMessageClient.enterGroupConversation(groupId);
            }
        } else if (null != targetId) {
            String appKey = getIntent().getStringExtra(TARGET_APP_KEY);
            JMessageClient.enterSingleConversation(targetId, appKey);
        }

        //历史消息中删除后返回到聊天界面刷新界面
        if (BaseApplication.ids != null && BaseApplication.ids.size() > 0) {
            for (Message msg : BaseApplication.ids) {
                mChatAdapter.removeMessage(msg);
            }
        }
        mChatAdapter.notifyDataSetChanged();
        //发送名片返回聊天界面刷新信息
    /*    if (SharePreferenceManager.getIsOpen()) {
            initData();
            SharePreferenceManager.setIsOpen(false);
        }*/
        super.onResume();

    }

    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();

        //若为群聊相关事件，如添加、删除群成员
        if (message.getContentType() == ContentType.eventNotification) {
            GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
            long groupId = groupInfo.getGroupID();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) message
                    .getContent()).getEventNotificationType();
            if (groupId == mGroupId) {
                switch (type) {
                    case group_member_added:
                        //添加群成员事件
                        List<String> userNames = ((EventNotificationContent) message.getContent()).getUserNames();
                        //群主把当前用户添加到群聊，则显示聊天详情按钮
                        refreshGroupNum();
                        if (userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mChatView.showRightBtn();
                                }
                            });
                        }
                        break;
                    case group_member_removed:
                        //删除群成员事件
                        userNames = ((EventNotificationContent) message.getContent()).getUserNames();
                        //群主删除了当前用户，则隐藏聊天详情按钮
                        if (userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mChatView.dismissRightBtn();
                                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                                    if (TextUtils.isEmpty(groupInfo.getGroupName())) {
                                        mChatView.setChatTitle("群聊");
                                    } else {
                                        mChatView.setChatTitle(groupInfo.getGroupName());
                                    }
                                    mChatView.dismissGroupNum();
                                }
                            });
                        } else {
                            refreshGroupNum();
                        }
                        break;
                    case group_member_exit:
                        EventNotificationContent content = (EventNotificationContent) message.getContent();
                        if (content.getUserNames().contains(JMessageClient.getMyInfo().getUserName())) {
                            mChatAdapter.notifyDataSetChanged();
                        } else {
                            refreshGroupNum();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.getTargetType() == ConversationType.single) {
                    UserInfo userInfo = (UserInfo) message.getTargetInfo();
                    String targetId = userInfo.getUserName();
                    String appKey = userInfo.getAppKey();
                    if (mIsSingle && targetId.equals(mTargetId) && appKey.equals(mTargetAppKey)) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        if (lastMsg == null || message.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(message);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    long groupId = ((GroupInfo) message.getTargetInfo()).getGroupID();
                    if (groupId == mGroupId) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        if (lastMsg == null || message.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(message);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void onEventMainThread(MessageRetractEvent event) {
        Message retractedMessage = event.getRetractedMessage();
        mChatAdapter.delMsgRetract(retractedMessage);
    }

    /**
     * 当在聊天界面断网再次连接时收离线事件刷新
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (conv.getType().equals(ConversationType.single)) {
            UserInfo userInfo = (UserInfo) conv.getTargetInfo();
            String targetId = userInfo.getUserName();
            String appKey = userInfo.getAppKey();
            if (mIsSingle && targetId.equals(mTargetId) && appKey.equals(mTargetAppKey)) {
                List<Message> singleOfflineMsgList = event.getOfflineMessageList();
                if (singleOfflineMsgList != null && singleOfflineMsgList.size() > 0) {
                    mChatView.setToBottom();
                    mChatAdapter.addMsgListToList(singleOfflineMsgList);
                }
            }
        } else {
            long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
            if (groupId == mGroupId) {
                List<Message> offlineMessageList = event.getOfflineMessageList();
                if (offlineMessageList != null && offlineMessageList.size() > 0) {
                    mChatView.setToBottom();
                    mChatAdapter.addMsgListToList(offlineMessageList);
                }
            }
        }
    }


    private void refreshGroupNum() {
        Conversation conv = JMessageClient.getGroupConversation(mGroupId);
        GroupInfo groupInfo = (GroupInfo) conv.getTargetInfo();
        if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NAME;
            Bundle bundle = new Bundle();
            bundle.putString(GROUP_NAME, groupInfo.getGroupName());
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        } else {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NUM;
            Bundle bundle = new Bundle();
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        }
    }

    private ChattingListAdapter.ContentLongClickListener longClickListener = new ChattingListAdapter.ContentLongClickListener() {

        @Override
        public void onContentLongClick(final int position, View view) {
            final Message msg = mChatAdapter.getMessage(position);

            if (msg == null) {
                return;
            }
            //如果是文本消息
            if ((msg.getContentType() == ContentType.text) && ((TextContent) msg.getContent()).getStringExtra("businessCard") == null) {
                //接收方
                if (msg.getDirect() == MessageDirect.receive) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(WeChatActivity.this, mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
//                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            if (Build.VERSION.SDK_INT > 11) {
                                                ClipboardManager clipboard = (ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clip = ClipData.newPlainText("Simple text", content);
                                                clipboard.setPrimaryClip(clip);
                                            } else {
                                                android.text.ClipboardManager clip = (android.text.ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                if (clip.hasText()) {
                                                    clip.getText();
                                                }
                                            }
                                            Toast.makeText(WeChatActivity.this, "已复制", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(WeChatActivity.this, "只支持复制文字", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 2) {
                                        ToastUtil.showShort("跳转到转发页面");
                                       /* Intent intent = new Intent(WeChatActivity.this, ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);*/
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                    //发送方
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(WeChatActivity.this, mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
//                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("撤回"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            if (Build.VERSION.SDK_INT > 11) {
                                                ClipboardManager clipboard = (ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clip = ClipData.newPlainText("Simple text", content);
                                                clipboard.setPrimaryClip(clip);
                                            } else {
                                                android.text.ClipboardManager clip = (android.text.ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                if (clip.hasText()) {
                                                    clip.getText();
                                                }
                                            }
                                            Toast.makeText(WeChatActivity.this, "已复制", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(WeChatActivity.this, "只支持复制文字", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 3) {
                                        //转发
                                        if (msg.getContentType() == ContentType.text || msg.getContentType() == ContentType.image ||
                                                (msg.getContentType() == ContentType.file && (msg.getContent()).getStringExtra("video") != null)) {
                                            ToastUtil.showShort("跳转到传发页面");
                                      /*      Intent intent = new Intent(WeChatActivity.this, ForwardMsgActivity.class);
                                            JGApplication.forwardMsg.clear();
                                            JGApplication.forwardMsg.add(msg);
                                            startActivity(intent);*/
                                        } else {
                                            Toast.makeText(WeChatActivity.this, "只支持转发文本,图片,小视频", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 1) {
                                        //撤回
                                        mConv.retractMessage(msg, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 855001) {
                                                    ToastUtil.showShort("发送时间过长，不能撤回");
                                                } else if (i == 0) {
                                                    mChatAdapter.delMsgRetract(msg);
                                                }
                                            }
                                        });
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                //除了文本消息类型之外的消息类型
            } else {
                //接收方
                if (msg.getDirect() == MessageDirect.receive) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(WeChatActivity.this, mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
//                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 1) {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    } else {
                                        //跳转到转发界面
                                      /*  Intent intent = new Intent(WeChatActivity.this, ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);*/
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                    //发送方
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(WeChatActivity.this, mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("撤回"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 1) {
                                        //撤回
                                        mConv.retractMessage(msg, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 855001) {
                                                    Toast.makeText(WeChatActivity.this, "发送时间过长，不能撤回", Toast.LENGTH_SHORT).show();
                                                } else if (i == 0) {
                                                    mChatAdapter.delMsgRetract(msg);
                                                }
                                            }
                                        });
                                    } else if (position == 0) {
                                        ToastUtil.showShort("跳转到转发页面");
                                        /*Intent intent = new Intent(WeChatActivity.this, ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);*/
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
            }
        }
    };

    /**
     * 消息已读事件
     */
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        List<MessageReceiptStatusChangeEvent.MessageReceiptMeta> messageReceiptMetas = event.getMessageReceiptMetas();
        for (MessageReceiptStatusChangeEvent.MessageReceiptMeta meta : messageReceiptMetas) {
            long serverMsgId = meta.getServerMsgId();
            int unReceiptCnt = meta.getUnReceiptCnt();
            mChatAdapter.setUpdateReceiptCount(serverMsgId, unReceiptCnt);
        }
    }

    public void onEventMainThread(ImageEvent event) {
        Intent intent;
        switch (event.getFlag()) {
            case Constant.IMAGE_MESSAGE:
                ToastUtil.showShort("发送图片");
               /* int from = PickImageActivity.FROM_LOCAL;
                int requestCode = RequestCode.PICK_IMAGE;
                if (ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请在应用管理中打开“读写存储”访问权限！", Toast.LENGTH_LONG).show();
                } else {
                    PickImageActivity.start(WeChatActivity.this, requestCode, from, tempFile(), true, 9,
                            true, false, 0, 0);
                }*/
                break;
            case Constant.TAKE_PHOTO_MESSAGE:
                ToastUtil.showShort("发送相片");
             /*   if ((ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "请在应用管理中打开“相机,读写存储,录音”访问权限！", Toast.LENGTH_LONG).show();
                } else {
                    intent = new Intent(WeChatActivity.this, CameraActivity.class);
                    startActivityForResult(intent, RequestCode.TAKE_PHOTO);
                }*/
                break;
            case Constant.TAKE_LOCATION:
                ToastUtil.showShort("发送位置");
              /*  if (ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请在应用管理中打开“位置”访问权限！", Toast.LENGTH_LONG).show();
                } else {
                    intent = new Intent(mContext, MapPickerActivity.class);
                    intent.putExtra(JGApplication.TARGET_ID, mTargetId);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, mTargetAppKey);
                    intent.putExtra(JGApplication.GROUP_ID, mGroupId);
                    intent.putExtra("sendLocation", true);
                    startActivityForResult(intent, JGApplication.REQUEST_CODE_SEND_LOCATION);
                }*/
                break;
            case Constant.FILE_MESSAGE:
                ToastUtil.showShort("发送文件");
               /* if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请在应用管理中打开“读写存储”访问权限！", Toast.LENGTH_LONG).show();

                } else {
                    intent = new Intent(mContext, SendFileActivity.class);
                    intent.putExtra(JGApplication.TARGET_ID, mTargetId);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, mTargetAppKey);
                    intent.putExtra(JGApplication.GROUP_ID, mGroupId);
                    startActivityForResult(intent, JGApplication.REQUEST_CODE_SEND_FILE);
                }*/
                break;
            case Constant.BUSINESS_CARD:
                ToastUtil.showShort("发送卡片");
               /* intent = new Intent(mContext, FriendListActivity.class);
                intent.putExtra("isSingle", mIsSingle);
                intent.putExtra("userId", mTargetId);
                intent.putExtra("groupId", mGroupId);
                startActivity(intent);*/
                break;
            case Constant.TACK_VIDEO:
            case Constant.TACK_VOICE:
                ToastUtil.showShort("该功能正在添加中");
                break;
            default:
                break;
        }

    }

    private String tempFile() {
        String filename = StringUtil.get32UUID() + JPG;
        return StorageUtil.getWritePath(filename, StorageType.TYPE_TEMP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode.REQUEST_CHAT && resultCode == ResultCode.RESULT_FINISH_CHAT) {
            finish();
        }


        switch (resultCode) {
            case RequestCode.RESULT_CODE_AT_MEMBER:
                if (!mIsSingle) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    String username = data.getStringExtra(Constant.TARGET_ID);
                    String appKey = data.getStringExtra(Constant.TARGET_APP_KEY);
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(username, appKey);
                    if (null == mAtList) {
                        mAtList = new ArrayList<UserInfo>();
                    }
                    mAtList.add(userInfo);
                    mLongClick = true;
                    ekBar.getEtChat().appendMention(data.getStringExtra(Constant.NAME));
                    ekBar.getEtChat().setSelection(ekBar.getEtChat().getText().length());
                }
                break;
            case RequestCode.RESULT_CODE_AT_ALL:
                mAtAll = data.getBooleanExtra(Constant.ATALL, false);
                mLongClick = true;
                if (mAtAll) {
                    ekBar.getEtChat().setText(ekBar.getEtChat().getText().toString() + "所有成员 ");
                    ekBar.getEtChat().setSelection(ekBar.getEtChat().getText().length());
                }
                break;
            case RequestCode.TAKE_PHOTO:
                if (data != null) {
                    String name = data.getStringExtra("take_photo");
                    Bitmap bitmap = BitmapFactory.decodeFile(name);
                    ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage, ImageContent imageContent) {
                            if (responseCode == 0) {
                                Message msg = mConv.createSendMessage(imageContent);
                                handleSendMsg(msg.getId());
                            }
                        }
                    });
                }
                break;
            case RequestCode.TAKE_VIDEO:
                if (data != null) {
                    String path = data.getStringExtra("video");
                    try {
                        FileContent fileContent = new FileContent(new File(path));
                        fileContent.setStringExtra("video", "mp4");
                        Message msg = mConv.createSendMessage(fileContent);
                        handleSendMsg(msg.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RequestCode.RESULT_CODE_SEND_LOCATION:
                //之前是在地图选择那边做的发送逻辑,这里是通过msgID拿到的message放到ui上.但是发现问题,message的status始终是send_going状态
                //因为那边发送的是自己创建的对象,这里通过id取出来的不是同一个对象.尽管内容都是一样的.所以为了保证发送的对象个ui上拿出来的
                //对象是同一个,就地图那边传过来数据,在这边进行创建message
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                int mapview = data.getIntExtra("mapview", 0);
                String street = data.getStringExtra("street");
                String path = data.getStringExtra("path");
                LocationContent locationContent = new LocationContent(latitude,
                        longitude, mapview, street);
                locationContent.setStringExtra("path", path);
                Message message = mConv.createSendMessage(locationContent);
                MessageSendingOptions options = new MessageSendingOptions();
                options.setNeedReadReceipt(true);
                JMessageClient.sendMessage(message, options);
                mChatAdapter.addMsgFromReceiptToList(message);
                int customMsgId = data.getIntExtra("customMsg", -1);
                if (-1 != customMsgId) {
                    Message customMsg = mConv.getMessage(customMsgId);
                    mChatAdapter.addMsgToList(customMsg);
                }
                mChatView.setToBottom();
                break;
            case RequestCode.RESULT_CODE_SEND_FILE:
                int[] intArrayExtra = data.getIntArrayExtra(MsgIDs);
                for (int msgId : intArrayExtra) {
                    handleSendMsg(msgId);
                }
                break;
            case RequestCode.RESULT_CODE_CHAT_DETAIL:
                String title = data.getStringExtra(Constant.CONV_TITLE);
                if (!mIsSingle) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(mMyInfo.getUserName(), mMyInfo.getAppKey());
                    //如果自己在群聊中，同时显示群人数
                    if (userInfo != null) {
                        if (TextUtils.isEmpty(title)) {
                            mChatView.setChatTitle(IdHelper.getString(mContext, "group"),
                                    data.getIntExtra(MEMBERS_COUNT, 0));
                        } else {
                            mChatView.setChatTitle(title, data.getIntExtra(MEMBERS_COUNT, 0));
                        }
                    } else {
                        if (TextUtils.isEmpty(title)) {
                            mChatView.setChatTitle(IdHelper.getString(mContext, "group"));
                        } else {
                            mChatView.setChatTitle(title);
                        }
                        mChatView.dismissGroupNum();
                    }
                } else mChatView.setChatTitle(title);
                if (data.getBooleanExtra("deleteMsg", false)) {
                    mChatAdapter.clearMsgList();
                }
                break;
            case ResultCode.RESULT_CLEAR_MESSAGE:
                mChatAdapter.clearMsgList();
                break;
            case ResultCode.RESULT_RENAME_DEVICE_CHAT:
                mChatView.setChatTitle(data.getStringExtra("devicename"));
                break;
            default:
                break;
        }

    }


    /**
     * 图片选取回调
     */
    private void onPickImageActivityResult(int requestCode, Intent data) {
       /* if (data == null) {
            return;
        }
        boolean local = data.getBooleanExtra(Extras.EXTRA_FROM_LOCAL, false);
        if (local) {
            // 本地相册
            sendImageAfterSelfImagePicker(data);
        }*/
    }

    /**
     * 发送图片
     */

    private void sendImageAfterSelfImagePicker(final Intent data) {
       /* SendImageHelper.sendImageAfterSelfImagePicker(this, data, new SendImageHelper.Callback() {
            @Override
            public void sendImage(final File file, boolean isOrig) {

                //所有图片都在这里拿到
                ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, ImageContent imageContent) {
                        if (responseCode == 0) {
                            Message msg = mConv.createSendMessage(imageContent);
                            handleSendMsg(msg.getId());
                        }
                    }
                });

            }
        });*/
    }

    //发送极光熊
    private void OnSendImage(String iconUri) {
        String substring = iconUri.substring(7);
        File file = new File(substring);
        ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, ImageContent imageContent) {
                if (responseCode == 0) {
                    imageContent.setStringExtra("jiguang", "xiong");
                    Message msg = mConv.createSendMessage(imageContent);
                    handleSendMsg(msg.getId());
                } else {
                    ToastUtil.showShort(responseMessage);
                }
            }
        });
    }

    /**
     * 处理发送图片，刷新界面
     *
     * @param data intent
     */
    private void handleSendMsg(int data) {
        mChatAdapter.setSendMsgs(data);
        mChatView.setToBottom();
    }


    //    //接受群认证消息
    public void onEvent(final GroupApprovalEvent event) {
        //默认扫码入群直接通过
        event.acceptGroupApproval(event.getFromUsername(),getString(R.string.JPUSH_APPKEY), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {

                }
            }
        });
    }


    @Override
    public void onVideochat() {
        pushMessage(channelid, PushParams.PHONE_VIDEO_DEVICE, null);
        //视频聊天，进入等待页面
        Router.build("p2p_video")
                .with("channelid", channelid)
                .with("name", mTitle)
                .with("status", PushParams.PHONE_VIDEO_DEVICE).go(this);
        //创建一条消息（通话时长，对方忙）
    }


    @Override
    public void onVoiceChat() {
        pushMessage(channelid, PushParams.PHONE_VOICE_DEVICE, null);
        //视频聊天，进入等待页面
        Router.build("p2p_video")
                .with("channelid", channelid)
                .with("name", mTitle)
                .with("status", PushParams.PHONE_VOICE_DEVICE).go(this);
    }

    @Override
    public void onMoniter() {
        pushMessage(channelid, PushParams.PHONE_MONITOR_DEVICE, null);
        //视频聊天，进入等待页面
        Router.build("p2p_video")
                .with("channelid", channelid)
                .with("name", mTitle)
                .with("status", PushParams.PHONE_MONITOR_DEVICE).go(this);
    }


    public void sendMessage(String message) {
        LogUtil.d("发送消息:", message);
        String mcgContent = message;
        scrollToBottom();
        if (mcgContent.equals("")) {
            return;
        }
        Message msg;
        TextContent content = new TextContent(mcgContent);
        if (mAtAll) {
            msg = mConv.createSendMessageAtAllMember(content, null);
            mAtAll = false;
        } else if (null != mAtList) {
            msg = mConv.createSendMessage(content, mAtList, null);
        } else {
            msg = mConv.createSendMessage(content);
        }
        //设置需要已读回执
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(true);
        JMessageClient.sendMessage(msg, options);
        mChatAdapter.addMsgFromReceiptToList(msg);
        ekBar.getEtChat().setText("");
        if (mAtList != null) {
            mAtList.clear();
        }
        if (forDel != null) {
            forDel.clear();
        }
    }

    private static class UIHandler extends Handler {
        private final WeakReference<WeChatActivity> mActivity;

        public UIHandler(WeChatActivity activity) {
            mActivity = new WeakReference<WeChatActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            WeChatActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_LAST_PAGE:
                        activity.mChatAdapter.dropDownToRefresh();
                        activity.mChatView.getListView().onDropDownComplete();
                        if (activity.mChatAdapter.isHasLastPage()) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                activity.mChatView.getListView()
                                        .setSelectionFromTop(activity.mChatAdapter.getOffset(),
                                                activity.mChatView.getListView().getHeaderHeight());
                            } else {
                                activity.mChatView.getListView().setSelection(activity.mChatAdapter
                                        .getOffset());
                            }
                            activity.mChatAdapter.refreshStartPosition();
                        } else {
                            activity.mChatView.getListView().setSelection(0);
                        }
                        //显示上一页的消息数18条
                        activity.mChatView.getListView()
                                .setOffset(activity.mChatAdapter.getOffset());
                        break;
                    case REFRESH_GROUP_NAME:
                        if (activity.mConv != null) {
                            int num = msg.getData().getInt(MEMBERS_COUNT);
                            String groupName = msg.getData().getString(GROUP_NAME);
                            activity.mChatView.setChatTitle(groupName, num);
                        }
                        break;
                    case REFRESH_GROUP_NUM:
                        int num = msg.getData().getInt(MEMBERS_COUNT);
                        activity.mChatView.setChatTitle("群聊", num);
                        break;
                    case REFRESH_CHAT_TITLE:
                        if (activity.mGroupInfo != null) {
                            //检查自己是否在群组中
                            UserInfo info = activity.mGroupInfo.getGroupMemberInfo(activity.mMyInfo.getUserName(),
                                    activity.mMyInfo.getAppKey());
                            if (!TextUtils.isEmpty(activity.mGroupInfo.getGroupName())) {
                                if (info != null) {
                                    activity.mChatView.setChatTitle(activity.mTitle,
                                            activity.mGroupInfo.getGroupMembers().size());
                                    activity.mChatView.showRightBtn();
                                } else {
                                    activity.mChatView.setChatTitle(activity.mTitle);
                                    activity.mChatView.dismissRightBtn();
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

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

}
