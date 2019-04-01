package com.hamitao.kids.model.scheduler;

import java.util.List;

/**
 * Created by linjianwen on 2018/6/26.
 * <p>
 * 发布课表到机器人的请求Model
 */

public class PublishScheduleModel {


    /**
     * courseSchedule : {"authorid":"花花妈customerid","authorname":"花花妈","coursescheduleid":"coursescheduleid_68f247963ba945de8e174c679d5a36ae","description":"早教系列","name":"name","scheduleContents":[{"info":"recordid_xxxx_xxxx","infotype":"recordid","schedule":{"day":"MON","endtime":"","starttime":"11:00"}},{"info":"xxxxxxxxx","infotype":"contentid","schedule":{"day":"MON","endtime":"","starttime":"12:00"}}],"scheduleplan":{"day":"MON","endtime":"","starttime":"12:00"},"source":"selfmade","status":"enable"}
     * targetid : 大花猫机器人terminalid
     */

    private CourseScheduleBean courseSchedule;
    private String targetid;

    public CourseScheduleBean getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(CourseScheduleBean courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public static class CourseScheduleBean {
        /**
         * authorid : 花花妈customerid
         * authorname : 花花妈
         * coursescheduleid : coursescheduleid_68f247963ba945de8e174c679d5a36ae
         * description : 早教系列
         * name : name
         * scheduleContents : [{"info":"recordid_xxxx_xxxx","infotype":"recordid","schedule":{"day":"MON","endtime":"","starttime":"11:00"}},{"info":"xxxxxxxxx","infotype":"contentid","schedule":{"day":"MON","endtime":"","starttime":"12:00"}}]
         * scheduleplan : {"day":"MON","endtime":"","starttime":"12:00"}
         * source : selfmade
         * status : enable
         */

        private String authorid;
        private String authorname;
        private String coursescheduleid;
        private String description;
        private String name;
        private ScheduleplanBean scheduleplan;
        private String source;
        private String status;
        private List<ScheduleContentsBean> scheduleContents;

        public String getAuthorid() {
            return authorid;
        }

        public void setAuthorid(String authorid) {
            this.authorid = authorid;
        }

        public String getAuthorname() {
            return authorname;
        }

        public void setAuthorname(String authorname) {
            this.authorname = authorname;
        }

        public String getCoursescheduleid() {
            return coursescheduleid;
        }

        public void setCoursescheduleid(String coursescheduleid) {
            this.coursescheduleid = coursescheduleid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ScheduleplanBean getScheduleplan() {
            return scheduleplan;
        }

        public void setScheduleplan(ScheduleplanBean scheduleplan) {
            this.scheduleplan = scheduleplan;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ScheduleContentsBean> getScheduleContents() {
            return scheduleContents;
        }

        public void setScheduleContents(List<ScheduleContentsBean> scheduleContents) {
            this.scheduleContents = scheduleContents;
        }

    }
}
