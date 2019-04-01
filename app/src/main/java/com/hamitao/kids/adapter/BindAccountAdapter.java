package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.kids.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/11/14.
 */
public class BindAccountAdapter extends BGARecyclerViewAdapter<DeviceRelationModel.ResponseDataObjBean.RelationBean.CustomerInfosBean> {

    public BindAccountAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_contact_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, DeviceRelationModel.ResponseDataObjBean.RelationBean.CustomerInfosBean model) {

        helper.getTextView(R.id.contact_name).setText(model.getBindname());
        helper.getImageView(R.id.contact_more).setVisibility(View.GONE);

        String loginName = model.getLoginname();
        if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor()) || Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
            if (loginName.contains("_")){
                loginName = loginName.split("_")[1];
            }
        }
        helper.getTextView(R.id.contact_phone).setText(loginName);
    }
}