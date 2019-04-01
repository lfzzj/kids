package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hamitao.kids.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class PushMsgAdapter extends BGARecyclerViewAdapter<String> {

        public PushMsgAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_deliverlist);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, String model) {
            helper.getTextView(R.id.tv_content).setText(model);
            helper.getImageView(R.id.iv_more).setVisibility(View.GONE);
        }
    }