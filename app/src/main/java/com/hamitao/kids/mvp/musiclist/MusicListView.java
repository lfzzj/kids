package com.hamitao.kids.mvp.musiclist;

import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/5/4.
 */

public interface MusicListView extends CommonView {

    //获取内容
    void getContent(ContentModel.ResponseDataObjBean.ContentsBean contentsBean);
}
