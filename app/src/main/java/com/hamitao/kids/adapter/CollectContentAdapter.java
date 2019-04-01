package com.hamitao.kids.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.CollectContentModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/5/9.
 */

public class CollectContentAdapter extends BGARecyclerViewAdapter<CollectContentModel.ResponseDataObjBean.FavoritesBean> {

    public CollectContentAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_header_name_more_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, CollectContentModel.ResponseDataObjBean.FavoritesBean model) {

        helper.getImageView(R.id.iv_header).setImageResource(R.drawable.default_cover);

        helper.getTextView(R.id.tv_name).setText(model.getInfotype().equals("contentid") ? model.getTitle() + "[专辑]" : model.getTitle());

        Glide.with(mContext).load(model.getHeaderimgurlHttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_header));

        if (model.getInfotype().equals("contentid")) {
            Drawable album = mContext.getResources().getDrawable(R.drawable.icon_share_album);
            album.setBounds(0, 0, album.getMinimumWidth(), album.getMinimumHeight());
            helper.getTextView(R.id.tv_name).setCompoundDrawables(null, null, album, null);
            helper.getTextView(R.id.tv_name).setCompoundDrawablePadding(10);
        }
        helper.getTextView(R.id.tv_name).setText(model.getTitle());
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.list_more);
    }
}
