package com.hamitao.kids.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ForumModel.ResponseDataObjBean.TopicsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGARecyclerViewHolder;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class SquareAdapter extends BGARecyclerViewAdapter<TopicsBean> {


    RecyclerView recyclerview;

    public SquareAdapter(RecyclerView recyclerView) {

        super(recyclerView, R.layout.item_square_list);

    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, TopicsBean model) {
        //头像
        Glide.with(mContext).load(model.getCreatorheaderimgurlHttpURL())
                .error(R.drawable.icon_head_default)
                .into(helper.getImageView(R.id.square_face));

        switch (model.getInfotype()) {
            case "contentid":
//                helper.getImageView(R.id.iv_contentType).setVisibility(View.VISIBLE);
                //内容封面
                Glide.with(mContext).load(model.getHeaderimgurlHttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_face));
                break;
            case "mediaid":
//                helper.getImageView(R.id.iv_contentType).setVisibility(View.GONE);
                Glide.with(mContext).load(model.getHeaderimgurlHttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_face));
                break;
            case "recordid":
//                helper.getImageView(R.id.iv_contentType).setVisibility(View.GONE);
                helper.getImageView(R.id.iv_face).setImageResource(R.drawable.icon_share_mic);
                helper.getImageView(R.id.iv_face).setScaleType(ImageView.ScaleType.FIT_CENTER);
                helper.getImageView(R.id.iv_face).setPadding(0,20,0,20);
                break;
            case "coursescheduleid":
//                helper.getImageView(R.id.iv_contentType).setVisibility(View.GONE);
                helper.getImageView(R.id.iv_face).setImageResource(R.drawable.icon_share_schedule);
                helper.getImageView(R.id.iv_face).setScaleType(ImageView.ScaleType.FIT_CENTER);
                helper.getImageView(R.id.iv_face).setPadding(0,20,0,20);
                break;
            default:
                break;
        }

        //内容标题
        helper.getTextView(R.id.tv_contentTitle).setText(model.getInfotype().equals("coursescheduleid") ? model.getCreatornickname() + "的课表" : model.getTitle());

        //评论内容
        helper.getTextView(R.id.tv_comment).setText(model.getDescription());

        //作者名字
        helper.getTextView(R.id.square_name).setText(model.getCreatornickname());

        //发布时间
        helper.getTextView(R.id.square_time).setText(model.getUpdatetime());

        //点赞数
        helper.getTextView(R.id.tv_good).setText(model.getLikecount() + "");

    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {

        super.setItemChildListener(helper, viewType);

        //点赞
        helper.setItemChildClickListener(R.id.tv_good);

        helper.setItemChildClickListener(R.id.ll_content);
    }

    @Override
    public void onBindViewHolder(BGARecyclerViewHolder viewHolder, int position) {

        super.onBindViewHolder(viewHolder, position);

        if (getData().get(position).getMylike_id() == null || getData().get(position).getMylike_id().equals("")) {

            Drawable unlike = mContext.getResources().getDrawable(R.drawable.icon_share_like_n);

            unlike.setBounds(0, 0, unlike.getMinimumWidth(), unlike.getMinimumHeight());

            viewHolder.getViewHolderHelper().getTextView(R.id.tv_good).setCompoundDrawables(unlike, null, null, null);
        } else {
            Drawable like = mContext.getResources().getDrawable(R.drawable.icon_share_like_p);

            like.setBounds(0, 0, like.getMinimumWidth(), like.getMinimumHeight());

            viewHolder.getViewHolderHelper().getTextView(R.id.tv_good).setCompoundDrawables(like, null, null, null);
        }
        viewHolder.getViewHolderHelper().getTextView(R.id.tv_good).setTag(position);
    }

}
