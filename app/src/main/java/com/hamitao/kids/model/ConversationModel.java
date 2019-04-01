package com.hamitao.kids.model;

import com.hamitao.base_module.model.DeviceRelationModel;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by linjianwen on 2018/8/14.
 */

public class ConversationModel {

    private Conversation conversation;

    private DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean terminalInfosBean;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean getTerminalInfosBean() {
        return terminalInfosBean;
    }

    public void setTerminalInfosBean(DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean terminalInfosBean) {
        this.terminalInfosBean = terminalInfosBean;
    }
}
