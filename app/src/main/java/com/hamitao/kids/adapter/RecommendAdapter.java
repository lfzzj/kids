package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.hamitao.kids.R;
import com.hamitao.kids.model.RecommendContentModel;

import java.util.Arrays;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class RecommendAdapter extends BGARecyclerViewAdapter<RecommendContentModel.ResponseDataObjBean.ContentsBean> {

    private BGABanner banner;

    public RecommendAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || (position % 3) - 2 == 0) {
            return R.layout.item_recommend_tips;
        } else if (position == 1) {
            return R.layout.item_recommend_banner;
        } else {
            return R.layout.item_recommend_detail_list;
        }
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, final int position, RecommendContentModel.ResponseDataObjBean.ContentsBean model) {

        String title = "";
        switch (position) {
            case 0:
                title = "今日推荐";
                break;
            case 2:
                title = "最新上架";
                break;
            case 5:
                title = "幼儿安全知识";
                break;
            case 8:
                title = "国学经典";
                break;
            case 11:
                title = "英语磨耳朵";
                break;
            case 14:
                title = "小小音乐家";
                break;
            case 17:
                title = "科普知多点";
                break;
            case 20:
                title = "我爱绘本";
                break;
            case 23:
                title = "热门动画";
                break;
            case 26:
                title = "早睡早起";
                break;
            case 29:
                title = "学科教材配套";
                break;
        }

        if (position == 0 || (position % 3) - 2 == 0) {
            helper.getTextView(R.id.tv_type).setText(title);
        } else if (position == 1) {
            //banner 默认显示三张图片
            banner = helper.getView(R.id.banner);
            banner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                    Glide.with(mContext)
                            .load((String) model)
                            .error(R.drawable.default_cover)
                            .centerCrop()
                            .dontAnimate()
                            .into((ImageView) itemView);
                }
            });

            String a = getData().get(position).getImgtitleurlhttpURL();
            String b = getData().get(position + 1).getImgtitleurlhttpURL();
            String c = getData().get(position + 2).getImgtitleurlhttpURL();

            String t1 = getData().get(position).getNameI18List().get(0).getValue();
            String t2 = getData().get(position + 1).getNameI18List().get(0).getValue();
            String t3 = getData().get(position + 2).getNameI18List().get(0).getValue();

            banner.setData(Arrays.asList(a, b, c), Arrays.asList(t1, t2, t3));
            banner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemView, Object model, int index) {
                    Router.build("music_list").with("contentid", getData().get(position + index).getContentid()).go(mContext);
                }
            });
        } else {
            Glide.with(mContext).load(getData().get(position).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
            helper.getTextView(R.id.tv_title).setText(model.getNameI18List().get(0).getValue());
            helper.getTextView(R.id.tv_content).setText(model.getAuthorList().get(0));
        }

    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.tv_type);
    }


}
