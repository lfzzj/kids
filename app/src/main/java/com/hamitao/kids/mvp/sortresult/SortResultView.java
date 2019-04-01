package com.hamitao.kids.mvp.sortresult;

import com.hamitao.base_module.base.BaseView;
import com.hamitao.kids.model.RecommendContentModel;

/**
 * Created by linjianwen on 2019/1/18.
 */
public interface SortResultView extends BaseView {


    void getList(RecommendContentModel.ResponseDataObjBean bean, int requestType);

//    void getContentDataByAge(RecommendContentModel.ResponseDataObjBean bean);


}
