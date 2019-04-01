package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.kids.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/3/23.
 */

public class MyCollectAdapter extends BGARecyclerViewAdapter<String> {


    private int[] hamitaoRes = {R.drawable.icon_clip1, R.drawable.icon_clip2, R.drawable.icon_clip3, R.drawable.icon_clip4, R.drawable.icon_clip5};
    private int[] otherRes = {R.drawable.icon_clip1, R.drawable.icon_clip3, R.drawable.icon_clip4, R.drawable.icon_clip5};

    public MyCollectAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_header_name_more_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.getImageView(R.id.list_more).setVisibility(model.equals("我的收藏") ? View.GONE : View.VISIBLE);
        helper.getTextView(R.id.tv_name).setText(model);
        helper.getImageView(R.id.iv_header).setImageResource(
                Constant.HAMITAO.endsWith(BaseApplication.getInstance().getVendor()) ? hamitaoRes[position % 5] : otherRes[position % 4]);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.list_more);
    }
}
