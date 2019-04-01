package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class MyScheduleAdapter extends BGARecyclerViewAdapter<String> {


    public MyScheduleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_musiclist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.getTextView(R.id.tv_name).setText(model);

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);

        helper.setItemChildClickListener(R.id.list_more);
    }
}
