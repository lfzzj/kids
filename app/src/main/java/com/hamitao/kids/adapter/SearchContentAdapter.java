package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/23.
 */

public class SearchContentAdapter extends BGARecyclerViewAdapter<ContentsBean> {


    public SearchContentAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_img_text_more_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ContentsBean model) {

        helper.getTextView(R.id.tv_name).setText(model.getNameI18List().get(0).getValue());  //内容名称
        Glide.with(mContext).load(model.getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_header));
        helper.getImageView(R.id.list_more).setImageResource(R.drawable.icon_arrow_right);
    }


}
