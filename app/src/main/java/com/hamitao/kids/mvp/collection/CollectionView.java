package com.hamitao.kids.mvp.collection;

import com.hamitao.kids.mvp.CommonView;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/8.
 */

public interface CollectionView extends CommonView {

    //获取收藏夹列表
    void getList(List<String> favoriteCategorys);

    void onError();

}
