package com.hamitao.kids.mvp.schedule;

import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.requestmodel.CreateScheduleRequestModel;
import com.hamitao.kids.model.requestmodel.UpdateScheduleRequestModel;
import com.hamitao.kids.model.scheduler.PublishScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleContentsBean;
import com.hamitao.kids.model.scheduler.ScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleplanBean;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/26.
 */

public class SchedulePresenter extends CommonPresenter{

    private ScheduleView view;

    public SchedulePresenter(ScheduleView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
    }

    public void queryMySchedule(String customerid) {

        NetworkRequest.queryMyScheduleRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {

            @Override
            public void onSuccess(NetWorkResult result) {
                ScheduleModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ScheduleModel.ResponseDataObjBean.class);
                view.initSchedulerSuccess(bean);// 传入得到的第一个课表（我的课表），初始化课表
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


    /**
     * 更新课程表
     *
     * @param authorid         作者ID,这里指customerid
     * @param authorname       作者名
     * @param coursescheduleid 课程表id
     * @param description      课程表描述
     * @param name             课程表名字
     * @param list             课表内容
     */
    public void updateSchedule(final String authorid, final String authorname, final String coursescheduleid, final String description, final String name, final List<ScheduleContentsBean> list, final ScheduleplanBean bean) {
        UpdateScheduleRequestModel model = new UpdateScheduleRequestModel();
        model.setAuthorid(authorid);
        model.setAuthorname(authorname);
        model.setCoursescheduleid(coursescheduleid);
        model.setDescription(description);
        model.setName(name);
        model.setScheduleContents(list);
        model.setSource("selfmade");
        model.setStatus("enable");
        model.setScheduleplan(bean);
        NetworkRequest.updateScheduleRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("update",null,0);

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }

    /**
     * 创建课程表
     *
     * @param customerid
     * @param authorname
     * @param description
     * @param scheduleName
     * @param list         课程内容
     * @param plan         课程播放计划
     */
    public void createSchedule(String customerid, String authorname, String description, String scheduleName, List<ScheduleContentsBean> list, ScheduleplanBean plan) {
        CreateScheduleRequestModel model = new CreateScheduleRequestModel();
        model.setAuthorid(customerid);
        model.setAuthorname(authorname);
        model.setDescription(description);
        model.setName(scheduleName);
        model.setScheduleContents(list);
        model.setSource("selfmade");
        model.setStatus("enable");
        model.setScheduleplan(plan);
        NetworkRequest.crateScheduleRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.onMessageShow("创建课表成功");
                ScheduleModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ScheduleModel.ResponseDataObjBean.class);
                view.createSchedulerSuccess(bean);
                //发布课程表到机器人
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


    /**
     * 发布课程表到机器人
     */
    public void publishSchedule(String targetid, String authorid, String authorname, String coursescheduleid, String description, String name, List<ScheduleContentsBean> list, ScheduleplanBean plan) {
        PublishScheduleModel model = new PublishScheduleModel();
        model.setTargetid(targetid);
        PublishScheduleModel.CourseScheduleBean bean = new PublishScheduleModel.CourseScheduleBean();
        bean.setAuthorid(authorid);
        bean.setAuthorname(authorname);
        bean.setCoursescheduleid(coursescheduleid);
        bean.setDescription(description);
        bean.setName(name);
        bean.setScheduleContents(list);
        bean.setScheduleplan(plan);
        model.setCourseSchedule(bean);
        NetworkRequest.publishScheduleRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.onMessageShow("课程表已发送到机器人！");
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


    /**
     * 删除发布到机器人的课程表
     */
    public void deletePublishSchedule(String terminalid, String coursescheduleid) {

        NetworkRequest.deletePublishScheduleRequest(terminalid, coursescheduleid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.onMessageShow("删除机器人的课程表成功！");
            }


            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


}
