package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ShareModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/4/16.
 */

public class ShareAdapter extends BGARecyclerViewAdapter<ShareModel.ResponseDataObjBean.TopicsBean> {

    public ShareAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_share);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.tv_likeCount);
        helper.setItemChildClickListener(R.id.iv_delete);
        helper.setItemChildClickListener(R.id.ll_content);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ShareModel.ResponseDataObjBean.TopicsBean model) {
        helper.getTextView(R.id.tv_time).setText(model.getUpdatetime()); //发布时间
        helper.getTextView(R.id.tv_likeCount).setText(model.getLikecount() + ""); //点赞数
        helper.getTextView(R.id.tv_content).setText(model.getDescription()); //评论内容
        helper.getTextView(R.id.tv_contentTitle).setText(model.getInfotype().equals("coursescheduleid") ? "我" + model.getTitle() : model.getTitle()); //内容标题
        switch (model.getInfotype()) {
            case "contentid":
                helper.getImageView(R.id.iv_type).setImageResource(R.drawable.icon_share_album);
                Glide.with(mContext).load(model.getHeaderimgurlHttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_face));  //内容封面
                break;
            case "mediaid":
                helper.getImageView(R.id.iv_face).setImageResource(R.drawable.default_cover);
                Glide.with(mContext).load(model.getHeaderimgurlHttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_face));  //内容封面
                break;
            case "recordid":
                helper.getImageView(R.id.iv_face).setImageResource(R.drawable.icon_share_mic);
                helper.getImageView(R.id.iv_face).setScaleType(ImageView.ScaleType.FIT_CENTER);
                helper.getImageView(R.id.iv_face).setPadding(0,20,0,20);
                break;
            case "coursescheduleid":
                helper.getImageView(R.id.iv_face).setImageResource(R.drawable.icon_share_schedule);
                helper.getImageView(R.id.iv_face).setScaleType(ImageView.ScaleType.FIT_CENTER);
                helper.getImageView(R.id.iv_face).setPadding(0,20,0,20);
                break;
        }
    }

}
