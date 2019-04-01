package com.hamitao.kids.model.scheduler;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linjianwen on 2018/5/8.
 */

public class ScheduleModel implements Serializable {
    /**
     * responseDataObj : {"courseScheduleList":[{"authorid":"童童妈customerid","authorname":"童童妈","coursescheduleid":"coursescheduleid_5602d63210b94e55b0d0a80430350b4b","description":"早教系列","name":"name","scheduleContents":[{"description":"内容description","info":"xxxxxxxxx","infotype":"contentid","schedule":{"day":"MON,FEB,SAT","endtime":"","index":"2222","starttime":"12:00"},"title":"内容title"},{"description":"录音description","info":"recordid_xxxx_xxxx","infotype":"recordid","schedule":{"day":"MON,FEB","endtime":"","index":"3333","starttime":"11:00"},"title":"录音title"}],"scheduleplan":{"day":"MON,FEB,WED","endtime":"","index":"111","starttime":"12:00"},"source":"selfmade","status":"enable"}]}
     * result : success
     */

    private ResponseDataObjBean responseDataObj;
    private String result;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ResponseDataObjBean implements Serializable {

        private List<CourseScheduleListBean> courseScheduleList;

        public List<CourseScheduleListBean> getCourseScheduleList() {
            return courseScheduleList;
        }

        public void setCourseScheduleList(List<CourseScheduleListBean> courseScheduleList) {
            this.courseScheduleList = courseScheduleList;
        }

        public static class CourseScheduleListBean implements Serializable {
            /**
             * authorid : 童童妈customerid
             * authorname : 童童妈
             * coursescheduleid : coursescheduleid_5602d63210b94e55b0d0a80430350b4b
             * description : 早教系列
             * name : name
             * scheduleContents : [{"description":"内容description","info":"xxxxxxxxx","infotype":"contentid","schedule":{"day":"MON,FEB,SAT","endtime":"","index":"2222","starttime":"12:00"},"title":"内容title"},{"description":"录音description","info":"recordid_xxxx_xxxx","infotype":"recordid","schedule":{"day":"MON,FEB","endtime":"","index":"3333","starttime":"11:00"},"title":"录音title"}]
             * scheduleplan : {"day":"MON,FEB,WED","endtime":"","index":"111","starttime":"12:00"}
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


}
