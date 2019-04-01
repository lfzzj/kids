package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;
import com.hamitao.kids.model.scheduler.ScheduleContentsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGARecyclerViewHolder;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class SchedulerAdapter extends BGARecyclerViewAdapter<ScheduleContentsBean> {
    public SchedulerAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_schedule);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ScheduleContentsBean model) {
        if (position % 8 == 0) {
            int index = (position / 8) + 1;
            helper.getTextView(R.id.name).setText(index + "");
        } else {
            helper.getTextView(R.id.name).setText(model == null ? "" : model.getTitle());
        }
    }


    @Override
    public void onBindViewHolder(BGARecyclerViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (position % 8 != 0 && getData().get(position) != null) {
            viewHolder.getViewHolderHelper().getTextView(R.id.name).setBackgroundColor(mContext.getResources().getColor(R.color.divider));
        }else {
            viewHolder.getViewHolderHelper().getTextView(R.id.name).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}