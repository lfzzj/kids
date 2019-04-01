package com.hamitao.base_module.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hamitao.base_module.R;
import com.hamitao.base_module.util.StringUtil;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/5/10.
 */

public class DialogListAdapter extends BGARecyclerViewAdapter<String> {


    private boolean isShow = false; // 是否显示删除按钮

    private boolean isMusicList = false;//是否是听单列表

    private int index = -1; //听单列表在播放的位置

    private int[] drawables = {R.drawable.icon_clip1, R.drawable.icon_clip2, R.drawable.icon_clip3, R.drawable.icon_clip4, R.drawable.icon_clip5,};


    public DialogListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_header_name_more_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.getTextView(R.id.tv_name).setText(StringUtil.deleteNumber(model));
        helper.getImageView(R.id.list_more).setVisibility(View.GONE);


        helper.getImageView(R.id.iv_header).setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isShow) {
            helper.getImageView(R.id.iv_header).setImageResource(drawables[position % 5]);
        }

        if (isMusicList) {
            if (position == index && index >= 0) {
                helper.getTextView(R.id.tv_name).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                helper.getTextView(R.id.tv_name).setTextColor(ContextCompat.getColor(mContext, R.color.text_default_d));
            }
        }

    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.list_more);
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setMusicList(boolean isMusicList,int index) {
        this.isMusicList = isMusicList;
        this.index = index;
    }

    public void setIndex(int index){
        this.index = index;
    }

}
