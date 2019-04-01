package com.hamitao.kids.model;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class MenuList implements ExpandableListItem {

    public boolean mExpanded = false;  //是否展开
    public String name;  //菜单名
    public List<ContentList> mContentList; //菜单下的内容


    @Override
    public List<ContentList> getChildItemList() {
        return mContentList;
    }

    @Override
    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void setExpanded(boolean isExpanded) {
        mExpanded = isExpanded;
    }


    @Override
    public String toString() {
        return "MenuList{" +
                "name='" + name + '\'' +
                '}';
    }
}
