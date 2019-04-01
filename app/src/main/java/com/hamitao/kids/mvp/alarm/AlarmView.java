package com.hamitao.kids.mvp.alarm;

import com.hamitao.kids.model.AlarmModel;
import com.hamitao.kids.mvp.CommonView;

/**
 * Created by linjianwen on 2018/4/16.
 */

public interface AlarmView extends CommonView {


    //获取闹钟列表
    void getAlarmList(AlarmModel.ResponseDataObjBean model);

}
