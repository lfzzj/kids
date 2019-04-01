package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;
import com.hamitao.kids.model.RecordModel.ResponseDataObjBean.VoiceRecordingsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;


/**
 * Created by linjianwen on 2018/1/12.
 */

public class RecordAdapter extends BGARecyclerViewAdapter<VoiceRecordingsBean> {


    public RecordAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recordlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, VoiceRecordingsBean model) {
        helper.getTextView(R.id.tv_name).setText(model.getName());
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.list_more);
    }
}
