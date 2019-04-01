package com.hamitao.kids.model.scheduler;


import java.io.Serializable;

/**
 * 单节课的课程内容
 */
public class ScheduleContentsBean implements Serializable {
    /**
     * description : 内容description
     * info : xxxxxxxxx
     * infotype : contentid
     * schedule : {"day":"MON,FEB,SAT","endtime":"","index":"2222","starttime":"12:00"}
     * title : 内容title
     */


    private String description;
    private String info;
    private String infotype;
    private ScheduleBean schedule;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public ScheduleBean getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleBean schedule) {
        this.schedule = schedule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ScheduleBean implements Serializable {
        /**
         * day : MON,FEB,SAT
         * endtime :
         * index : 2222
         * starttime : 12:00
         */

        private String day;
        private String endtime;
        private String index;
        private String starttime;

        public ScheduleBean(String day, String endtime, String index, String starttime) {
            this.day = day;
            this.endtime = endtime;
            this.index = index;
            this.starttime = starttime;
        }

        public ScheduleBean() {
        }

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
}