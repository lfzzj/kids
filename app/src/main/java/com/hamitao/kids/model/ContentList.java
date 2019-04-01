package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class ContentList {

    public CollectContentModel.ResponseDataObjBean.FavoritesBean content_name;

    public boolean Selected;

    public ContentList(CollectContentModel.ResponseDataObjBean.FavoritesBean content_name) {
        this.content_name = content_name;
    }

    public CollectContentModel.ResponseDataObjBean.FavoritesBean getContent_name() {
        return content_name;
    }

    public void setContent_name(CollectContentModel.ResponseDataObjBean.FavoritesBean content_name) {
        this.content_name = content_name;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
