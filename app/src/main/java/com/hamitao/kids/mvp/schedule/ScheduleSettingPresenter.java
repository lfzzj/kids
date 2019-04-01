package com.hamitao.kids.mvp.schedule;

import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.kids.model.requestmodel.UpdateScheduleRequestModel;
import com.hamitao.kids.model.scheduler.PublishScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleContentsBean;
import com.hamitao.kids.model.scheduler.ScheduleplanBean;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/28.
 */

public class ScheduleSettingPresenter extends CommonPresenter {

    private ScheduleSettingView view;

    public ScheduleSettingPresenter(ScheduleSettingView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }


    /**
     * 删除课表
     */
    public void deleteSchedule(String coursescheduleid) {
        NetworkRequest.deleteScheduleRequest(coursescheduleid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.deleteScheduleSuccess();
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
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
    public void updateSchedule(String authorid, String authorname, String coursescheduleid, String description, String name, List<ScheduleContentsBean> list, final ScheduleplanBean scheduleplanBean, String status) {
        UpdateScheduleRequestModel model = new UpdateScheduleRequestModel();
        model.setAuthorid(authorid);
        model.setAuthorname(authorname);
        model.setCoursescheduleid(coursescheduleid);
        model.setDescription(description);
        model.setName(name);
        model.setScheduleContents(list);
        model.setSource("selfmade");
        model.setStatus(status);
        model.setScheduleplan(scheduleplanBean);
        NetworkRequest.updateScheduleRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                view.refreshData("update_schedule", scheduleplanBean.getStarttime(), null);
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
    public void publishSchedule(final int position, String targetid, String authorid, String authorname, String coursescheduleid, String description, String name, List<ScheduleContentsBean> list, ScheduleplanBean plan) {
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
        bean.setSource("selfmade");
        bean.setStatus("enable");
        NetworkRequest.publishScheduleRequest(model, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("publish_schedule", null, position);
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
    public void deletePublishSchedule(final int position, String terminalid, String coursescheduleid) {

        NetworkRequest.deletePublishScheduleRequest(terminalid, coursescheduleid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("delete_schedule", null, position);

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
