package com.hamitao.kids.mvp.search;

import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean;
import com.hamitao.kids.mvp.CommonView;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/14.
 */

public interface SearchView extends CommonView {

    //加载搜索到的数据
    void setSearchData(List<ContentsBean> list);

}
