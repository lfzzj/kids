package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.RecommendContentModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class SortResultAdapter extends BGARecyclerViewAdapter<RecommendContentModel.ResponseDataObjBean.ContentsBean> {

    public SortResultAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recommend_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, RecommendContentModel.ResponseDataObjBean.ContentsBean model) {
        helper.getTextView(R.id.tv_title).setText(model.getNameI18List().get(0).getValue());
        helper.getTextView(R.id.tv_content).setText(model.getDescriptionI18List().get(0).getValue());
        Glide.with(mContext).load(model.getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
    }
}