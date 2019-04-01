package com.hamitao.kids.mvp.schedule;

import com.hamitao.kids.model.scheduler.ScheduleModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/3/26.
 */

public interface ScheduleView extends CommonView {


    void initSchedulerSuccess(ScheduleModel.ResponseDataObjBean courseScheduleListBean); //查询课表成功

    void createSchedulerSuccess(ScheduleModel.ResponseDataObjBean courseScheduleListBean); //创建课表成功


}
