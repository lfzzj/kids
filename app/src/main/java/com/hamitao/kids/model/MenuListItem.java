package com.hamitao.kids.model;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.kids.R;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class MenuListItem extends AbstractExpandableAdapterItem implements View.OnClickListener {
    private MenuList menuList;
    private RelativeLayout rl_menu;
    private TextView menu_name;

    private ChangeListener listener;

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange(int contentSize);
    }

    @Override
    public void onExpansionToggled(boolean expanded) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_menu_name;
    }


    @Override
    public void onBindViews(final View root) {
        rl_menu = (RelativeLayout) root.findViewById(R.id.rl_menu);
        menu_name = (TextView) root.findViewById(R.id.tv_menu_name);
        rl_menu.setOnClickListener(this);
    }

    @Override
    public void onSetViews() {

    }


    public String getMenuName(){
        return menu_name.getText().toString();
    }

    @Override
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
        onSetViews();
        if (model instanceof MenuList) {
            menuList = (MenuList) model;
            menu_name.setText(menuList.name);
        }
        //展开第三级项
        /*
        ExpandableListItem parentListItem = (ExpandableListItem) model;
        List<?> childItemList = parentListItem.getChildItemList();
       if (childItemList != null && !childItemList.isEmpty()) {
            mArrow.setVisibility(View.VISIBLE);
            mExpand.setText(parentListItem.isExpanded() ? "unexpand" : "expand");
        } else mExpand.setText("expand");*/
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rl_menu:
                //展开列表
                if (getExpandableListItem() != null && getExpandableListItem().getChildItemList() != null) {
                    if (getExpandableListItem().isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                    if (listener != null)
                        listener.onChange(menuList.mContentList.size());
                }
                break;
        }

    }
}
