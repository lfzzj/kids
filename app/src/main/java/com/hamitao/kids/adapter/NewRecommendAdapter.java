package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.RecommendContentModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by linjianwen on 2018/7/4.
 */

public class NewRecommendAdapter extends BGARecyclerViewAdapter<List<RecommendContentModel.ResponseDataObjBean.ContentsBean>> {

    private BGABanner banner;

    String[] tips = new String[]{"今日推荐", "小淘课堂", "幼儿安全知识", "国学经典", "英语磨耳朵", "小小音乐家", "科普知多点", "我爱绘本", "热门动画", "早睡早起", "同步教材"};

    public NewRecommendAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.item_recommend_banner;
        } else {
            return R.layout.item_recommend_detail_list;
        }
    }


    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, final List<RecommendContentModel.ResponseDataObjBean.ContentsBean> data) {
        if (data == null) {
            return;
        }
        if (position == 0) {
            helper.getTextView(R.id.tv_today_recommend).setVisibility(View.GONE);
            helper.getTextView(R.id.tv_type).setText("今日推荐");
            banner = helper.getView(R.id.banner);

            final List<String> imgs = new ArrayList<>();
            final List<String> names = new ArrayList<>();

            if (data.get(0).getCategoryList().contains("今日推荐")) {
                for (int i = 0; i < data.size(); i++) {
                    imgs.add(data.get(i).getImgtitleurlhttpURL() == null ? "" : data.get(i).getImgtitleurlhttpURL());
                    names.add(data.get(i).getNameI18List() == null ? "" : data.get(i).getNameI18List().get(0).getValue());
                }
            }

            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {

                    Glide.with(mContext)
                            .load((String) model)
                            .error(R.drawable.default_cover)
                            .centerCrop()
                            .into(itemView);
                }
            });

            banner.setData(imgs, names);
            banner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemView, Object models, int index) {

                    if (data.get(index).getKeywordList() == null) {
                        return;
                    }

                    if (data.get(index).getKeywordList().contains("小淘")) {
                        if (UserUtil.user() == null || !PropertiesUtil.getInstance().getBoolean(PropertiesUtil.SpKey.isBindDevice, false)) {
                            ToastUtil.showShort("请先绑定机器人");
                            return;
                        } else {
                            //如果绑定了机器人
                            if (!BaseApplication.getInstance().isPublicVendor()) {
                                ToastUtil.showShort(mContext.getResources().getString(R.string.content_close));
                                return;
                            }
                        }
                    }
                    Router.build("music_list").with("contentid", data.get(index).getContentid()).go(mContext);
                }
            });
        } else {
            helper.getTextView(R.id.tv_type).setText(tips[position]);
            RelativeLayout rl = helper.getView(R.id.rl_content1);
            RelativeLayout r2 = helper.getView(R.id.rl_content2);
            RelativeLayout r3 = helper.getView(R.id.rl_content3);
            View line1 = rl.findViewById(R.id.line1);
            View line2 = r2.findViewById(R.id.line2);

            if (data.size() == 1) {
                //第一个item
                Glide.with(mContext).load(data.get(0).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
                helper.getTextView(R.id.tv_title).setText(data.get(0).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content).setText(data.get(0).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(0).getDescriptionI18List().get(0).getValue());
                rl.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
            } else if (data.size() == 2) {
                //第一个item
                Glide.with(mContext).load(data.get(0).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
                helper.getTextView(R.id.tv_title).setText(data.get(0).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content).setText(data.get(0).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(0).getDescriptionI18List().get(0).getValue());
                //第二个item
                Glide.with(mContext).load(data.get(1).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img2));
                helper.getTextView(R.id.tv_title2).setText(data.get(1).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content2).setText(data.get(1).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(1).getDescriptionI18List().get(0).getValue());
                rl.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.GONE);
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
            } else if (data.size() >= 3) {
                //第一个item
                Glide.with(mContext).load(data.get(0).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img));
                helper.getTextView(R.id.tv_title).setText(data.get(0).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content).setText(data.get(0).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(0).getDescriptionI18List().get(0).getValue());
                //第二个item
                Glide.with(mContext).load(data.get(1).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img2));
                helper.getTextView(R.id.tv_title2).setText(data.get(1).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content2).setText(data.get(1).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(1).getDescriptionI18List().get(0).getValue());
                //第三个item
                Glide.with(mContext).load(data.get(2).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(helper.getImageView(R.id.iv_img3));
                helper.getTextView(R.id.tv_title3).setText(data.get(2).getNameI18List().get(0).getValue());
                helper.getTextView(R.id.tv_content3).setText(data.get(2).getDescriptionI18List().get(0).getValue().isEmpty() ? "文件夹" : data.get(2).getDescriptionI18List().get(0).getValue());
                rl.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.tv_type);
        if (viewType == R.layout.item_recommend_detail_list) {
            helper.setItemChildClickListener(R.id.rl_content1);
            helper.setItemChildClickListener(R.id.rl_content2);
            helper.setItemChildClickListener(R.id.rl_content3);
        }
    }

}
