package com.hamitao.kids.mvp.recentplay;

import com.hamitao.kids.model.RecentPlayModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/4/18.
 */

public interface RecentPlayView extends CommonView {

    void getList(RecentPlayModel.ResponseDataObjBean bean,int requestType);
}
