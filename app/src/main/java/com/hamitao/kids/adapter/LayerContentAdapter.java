package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.LayerContentModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class LayerContentAdapter extends BGARecyclerViewAdapter<LayerContentModel.ResponseDataObjBean.ContentTreeNodesBean> {

        public LayerContentAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_recommend_list);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, LayerContentModel.ResponseDataObjBean.ContentTreeNodesBean model) {


            if (model.getContentDesc() != null) {
                Glide.with(mContext).load(model.getContentDesc().getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
//                helper.getTextView(R.id.tv_title).setText(StringUtil.deleteNumber(model.getContentDesc().getNameI18List().get(0).getValue()));
                helper.getTextView(R.id.tv_title).setText(StringUtil.deleteNumber(model.getNodename()));
                helper.getTextView(R.id.tv_content).setText(model.getContentDesc().getDescriptionI18List().get(0).getValue());
            } else {
                Glide.with(mContext).load(model.getNodeheaderimgurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
                helper.getTextView(R.id.tv_title).setText(StringUtil.deleteNumber(model.getNodename()));
                helper.getTextView(R.id.tv_content).setText(model.getNodedesc());
            }

        }

    }