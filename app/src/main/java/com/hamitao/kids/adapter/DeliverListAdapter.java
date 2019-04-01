package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hamitao.kids.R;
import com.hamitao.kids.model.DeliverModel.ResponseDataObjBean.AirDropListBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/2/7.
 * 投送清单
 */

public class DeliverListAdapter extends BGARecyclerViewAdapter<AirDropListBean> {


    public DeliverListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_deliverlist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, AirDropListBean model) {
        helper.getImageView(R.id.iv_more).setVisibility(View.GONE);
        helper.getTextView(R.id.tv_content).setText(model.getTitle());
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);

    }
}
