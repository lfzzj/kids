package com.hamitao.kids.mvp.editschedule;

import com.hamitao.kids.model.ClipModel;
import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.base_module.base.BaseView;

/**
 * Created by linjianwen on 2018/5/16.
 */

public interface EditScheduleView extends BaseView {
    void initClip(ClipModel.ResponseDataObjBean clip, CollectContentModel.ResponseDataObjBean collection, RecordModel.ResponseDataObjBean record);
}
