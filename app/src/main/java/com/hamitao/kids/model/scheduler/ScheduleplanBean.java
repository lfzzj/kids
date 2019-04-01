package com.hamitao.kids.model.scheduler;


import java.io.Serializable;

/**
 * 单节课的课程播放计划
 */
public class ScheduleplanBean implements Serializable {
    /**
     * day : MON,FEB,WED
     * endtime :
     * index : 111
     * starttime : 12:00
     */

    private String day;
    private String endtime;
    private String index;
    private String starttime;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
}