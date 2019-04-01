package com.hamitao.kids.model.requestmodel;

import com.hamitao.kids.model.scheduler.ScheduleContentsBean;
import com.hamitao.kids.model.scheduler.ScheduleplanBean;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/29.
 * 更新课表请求字段
 */

public class UpdateScheduleRequestModel {


    /**
     * authorid : 童童妈customerid
     * authorname : 童童妈
     * coursescheduleid : coursescheduleid_3cb08413d25d42828e8c592b744a81a9
     * description : 早教系列
     * name : name
     * scheduleContents : [{"info":"修改后的内容1","infotype":"recordid","description":"修改的description1","schedule":{"day":"MON","endtime":"","starttime":"11:00"},"title":"修改的title1"},{"info":"修改后的内容2","infotype":"contentid","description":"修改的description2","schedule":{"day":"MON","endtime":"","starttime":"12:00"},"title":"修改的title2"}]
     * scheduleplan : {"day":"MON","endtime":"","starttime":"12:00"}
     * source : selfmade
     * status : disable
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
