package com.hamitao.base_module.util;

import android.content.Context;

import com.hamitao.base_module.R;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by linjianwen on 2018/1/17.
 */

public class BGARefreshUtil {


    public static BGAMeiTuanRefreshViewHolder getBGAMeiTuanRefreshViewHolder(Context context) {
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(context, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_loading01);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_loding); //帧动画
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_loding);
        meiTuanRefreshViewHolder.setPullDistanceScale(1.0f);
        meiTuanRefreshViewHolder.setSpringDistanceScale(0f);
        meiTuanRefreshViewHolder.setLoadingMoreText("加载中...");
        return meiTuanRefreshViewHolder;
    }

    public static void completeRequest(final BGARefreshLayout recyclerview_refresh){
        if (recyclerview_refresh != null) {
            recyclerview_refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerview_refresh.endRefreshing();
                }
            }, 100);
            recyclerview_refresh.endLoadingMore();
        }
    }
}
