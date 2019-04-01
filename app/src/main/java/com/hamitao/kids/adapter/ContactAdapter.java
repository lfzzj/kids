package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;
import com.hamitao.kids.model.ContactModel.ResponseDataObjBean.ContactsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/2/9.
 */

public class ContactAdapter extends BGARecyclerViewAdapter<ContactsBean> {


    public ContactAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_contact_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ContactsBean model) {
        helper.getTextView(R.id.contact_name).setText(model.getContactname());
        helper.getTextView(R.id.contact_phone).setText(model.getPhonenum());
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.contact_more);
    }
}
