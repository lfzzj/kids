package com.hamitao.kids.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.hamitao.kids.R;
import com.hamitao.kids.model.IdentityModel;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/7/30.
 */

public class IdentityAdapter extends BGARecyclerViewAdapter<IdentityModel> {

    public IdentityAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_img_text);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, IdentityModel model) {
        ((CheckBox) helper.getView(R.id.cb)).setText(model.getName());

        ((CheckBox) helper.getView(R.id.cb)).setTextColor(mContext.getResources().getColor(model.isCheck() ? R.color.colorPrimary : R.color.text_default_d));

        Drawable img = mContext.getResources().getDrawable(model.getImg());

        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());

        ((CheckBox) helper.getView(R.id.cb)).setCompoundDrawables(null, img, null, null);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.cb);
    }
}