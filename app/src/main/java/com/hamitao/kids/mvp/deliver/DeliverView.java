package com.hamitao.kids.mvp.deliver;

import com.hamitao.kids.model.DeliverModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/4/18.
 */

public interface DeliverView extends CommonView {

    //获取投送清单
    void getList(DeliverModel.ResponseDataObjBean bean, int requestType);
}
