package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.nfccard.NfcModel.ResponseDataObjBean.NFCCardsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/28.
 */

public class CardAdapter extends BGARecyclerViewAdapter<NFCCardsBean> {


    public DeleteCallbakc deleteCallbakc;

    public void setDeleteCallbakc(DeleteCallbakc deleteCallbakc) {
        this.deleteCallbakc = deleteCallbakc;
    }

    public interface DeleteCallbakc{
        void deleteNfc(String costomerid, String nfcid, int position);//删除制卡
    }

    public CardAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_makecard);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NFCCardsBean model) {

        if (model.getInfotype().equals("contentid")) {
            helper.getTextView(R.id.tv_contentName).setText(model.getContentDesc()==null?"":model.getContentDesc().getNameI18List().get(0).getValue());
        } else if (model.getInfotype().equals("recordid")) {
            if (model.getVoiceRecordingDesc() == null){
                deleteCallbakc.deleteNfc(UserUtil.user().getCustomerid(),model.getNFCID(),position);
            }else {
                helper.getTextView(R.id.tv_contentName).setText(model.getVoiceRecordingDesc().getName());
            }
        } else {
            helper.getTextView(R.id.tv_contentName).setText(StringUtil.deleteNumber(model.getTitle()));
        }
        helper.getTextView(R.id.tv_cardId).setText("ID: " + model.getNFCID());
    }
}
