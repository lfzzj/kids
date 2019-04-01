package com.hamitao.kids.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ContentModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/14.
 */

public class MusicListAdapter extends BGARecyclerViewAdapter<ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean> {


    private int index = -1; // 正在播放的位置

    public MusicListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_musiclist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean model) {
        helper.getTextView(R.id.tv_position).setText(position + 1 + "");
        helper.getTextView(R.id.tv_name).setText(StringUtil.deleteNumber(model.getMediatitle()));
        helper.getTextView(R.id.tv_duration).setText(DateUtil.formatSeconds(model.getDuration()));
        if (position == index ){
            helper.getTextView(R.id.tv_name).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        }else {
            helper.getTextView(R.id.tv_name).setTextColor(ContextCompat.getColor(mContext,R.color.text_default_d));
        }
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.list_more);
    }


    public void setIndex(int index) {
        this.index = index;
    }
}
