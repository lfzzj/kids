package com.hamitao.kids.mvp.CollectContent;

import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.mvp.CommonView;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/9.
 */

public interface CollectContentView extends CommonView {

    //获取列表
    void getList(List<CollectContentModel.ResponseDataObjBean.FavoritesBean> list);
}
