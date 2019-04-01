package com.hamitao.kids.model;

import com.hamitao.kids.model.scheduler.ScheduleContentsBean;

/**
 * Created by linjianwen on 2018/7/31.
 */

public class AddCourseModel {

    ScheduleContentsBean bean;
    int position;

    public ScheduleContentsBean getBean() {
        return bean;
    }

    public void setBean(ScheduleContentsBean bean) {
        this.bean = bean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
