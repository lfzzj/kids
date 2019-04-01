package com.hamitao.kids.mvp.square;

import com.hamitao.kids.model.ForumModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/1/19.
 */

public interface SquareView extends CommonView {

    void getList(ForumModel.ResponseDataObjBean topics, int requestType);

    /**
     * 请求失败
     */
    void onError();
}
