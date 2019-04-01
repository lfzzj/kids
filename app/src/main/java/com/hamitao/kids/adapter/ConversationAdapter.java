package com.hamitao.kids.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.util.TimeFormat;
import com.hamitao.kids.R;

import java.util.List;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by linjianwen on 2018/1/18.
 */

public class ConversationAdapter extends BGARecyclerViewAdapter<Conversation> {

    private TextView title, time, content, unReadCount;
    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> list;

    public ConversationAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_chat_group);

    }

    @Override
    protected void fillData(final BGAViewHolderHelper helper, int position, final Conversation conversation) {
        //用户名
        title = helper.getTextView(R.id.item_main_username);
        //时间
        time = helper.getTextView(R.id.item_main_time);
        //最后一条消息
        content = helper.getTextView(R.id.item_main_content);
        //未读消息数
        unReadCount = helper.getTextView(R.id.tv_unread_num);

        if (conversation.getUnReadMsgCnt() > 0) {
            unReadCount.setVisibility(View.VISIBLE);
            unReadCount.setText(conversation.getUnReadMsgCnt() + "");
        } else {
            unReadCount.setVisibility(View.GONE);
        }
        if (conversation.getType().equals(ConversationType.single)) {
            String name = ((UserInfo) conversation.getTargetInfo()).getDisplayName();
            title.setText(name);
            helper.getImageView(R.id.item_main_img).setImageResource(R.drawable.icon_device_logo);
        } else {
            title.setText(conversation.getTitle());
            helper.getImageView(R.id.item_main_img).setImageResource(R.drawable.icon_group_conversation);
        }

        Message lastMsg = conversation.getLatestMessage();
        String contentStr = "";

        if (lastMsg != null) {
            //最后一条消息不为空
            TimeFormat timeFormat = new TimeFormat(mContext, lastMsg.getCreateTime());
            time.setText(timeFormat.getTime());
            if (lastMsg.getContent().getContentType().equals(ContentType.voice)) {
                contentStr = "[语音]";
            } else if (lastMsg.getContent().getContentType().equals(ContentType.text)) {
                contentStr = ((TextContent) lastMsg.getContent()).getText();
            } else if (lastMsg.getContent().getContentType().equals(ContentType.eventNotification)) {
                contentStr = ((EventNotificationContent) lastMsg.getContent()).getEventText();
            } else {
                contentStr = "";
            }
            content.setText(contentStr);
        } else {
            if (conversation.getType().equals(ConversationType.single)) {
                content.setText("你已添加了" + title.getText().toString() + ",现在可以开始聊天了。");
            } else {
                content.setText("新的群聊,现在可以开始聊天了。");
            }
        }
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);

    }
}
