package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;
import com.hamitao.kids.model.RecentPlayModel.ResponseDataObjBean.PlayListBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/29.
 */

public class RecentPlayAdapter extends BGARecyclerViewAdapter<PlayListBean> {


    public RecentPlayAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_deliverlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, PlayListBean model) {
        helper.getTextView(R.id.tv_content).setText(model.getTitle());
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.iv_more);
    }
}
