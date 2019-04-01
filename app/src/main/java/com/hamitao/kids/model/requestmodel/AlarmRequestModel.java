package com.hamitao.kids.model.requestmodel;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class AlarmRequestModel {
    /**
     * id : fbe2fce589154fb1811033b002318f29
     * idx : 38755395
     * name : 早上起床
     * ownerid : sz10001_kidsrobot_5eb7e864a63a4041b262d5a3f2acfa21
     * status : enable
     * timers : [{"day":"SUN,MON,TUE,WED,THU,FRI,SAT","starttime":"25:000"}]
     */

    private String id;
    private String idx;
    private String name;
    private String ownerid;
    private String status;
    private List<TimersBean> timers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TimersBean> getTimers() {
        return timers;
    }

    public void setTimers(List<TimersBean> timers) {
        this.timers = timers;
    }

    public static class TimersBean {
        /**
         * day : SUN,MON,TUE,WED,THU,FRI,SAT
         * starttime : 25:000
         */

        private String day;
        private String starttime;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }
    }


//    /**
//     * id : 4fd11810a79a4f6bbf3041845899e496
//     * name : 早上起床
//     * ownerid : sz10001_kidsrobot_5eb7e864a63a4041b262d5a3f2acfa21
//     * status : disable
//     * timerAlarmSchedules : {"timers":[{"days":["SUN","MON","TUE","WED","THU","FRI","SAT"],"starttime":"25:000"},{"days":["SUN","MON"],"starttime":"11:00"}]}
//     */
//
//    private String id;
//    private String name;
//    private String ownerid;
//    private String status;
//    private TimerAlarmSchedulesBean timerAlarmSchedules;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getOwnerid() {
//        return ownerid;
//    }
//
//    public void setOwnerid(String ownerid) {
//        this.ownerid = ownerid;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public TimerAlarmSchedulesBean getTimerAlarmSchedules() {
//        return timerAlarmSchedules;
//    }
//
//    public void setTimerAlarmSchedules(TimerAlarmSchedulesBean timerAlarmSchedules) {
//        this.timerAlarmSchedules = timerAlarmSchedules;
//    }
//
//    public static class TimerAlarmSchedulesBean {
//        private List<TimersBean> timers;
//
//        public List<TimersBean> getTimers() {
//            return timers;
//        }
//
//        public void setTimers(List<TimersBean> timers) {
//            this.timers = timers;
//        }
//
//        public static class TimersBean {
//            /**
//             * days : ["SUN","MON","TUE","WED","THU","FRI","SAT"]
//             * starttime : 25:000
//             */
//
//            private String starttime;
//            private List<String> days;
//
//            public String getStarttime() {
//                return starttime;
//            }
//
//            public void setStarttime(String starttime) {
//                this.starttime = starttime;
//            }
//
//            public List<String> getDays() {
//                return days;
//            }
//
//            public void setDays(List<String> days) {
//                this.days = days;
//            }
//        }
//    }



}
