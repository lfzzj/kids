package com.hamitao.kids.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.DialogScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleplanBean;
import com.hamitao.kids.mvp.schedule.ScheduleSettingPresenter;
import com.hamitao.kids.mvp.schedule.ScheduleSettingView;
import com.hamitao.kids.util.DialogListUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

@Route("schedule_setting")
public class ScheduleSettingActivity extends BaseActivity implements ScheduleSettingView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.switch_schedule)
    ImageView switch_schedule;
    @BindView(R.id.tv_schedule_time)
    TextView tv_schedule_time;

    Calendar selectedDate = Calendar.getInstance();
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    private ScheduleSettingPresenter presenter;

    private ScheduleModel.ResponseDataObjBean.CourseScheduleListBean bean;

    private ScheduleModel.ResponseDataObjBean courseScheduleListBean;

    private ScheduleplanBean scheduleplanBean;


    /**
     * 默认打开课程
     */
    private boolean isPause;


    /**
     * 课表ID
     */
    private String scheduleId = "";


    /**
     * 课表创建者
     */
    private String creator = "";


    /**
     * 课表描述
     */
    private String description = "";


    /**
     * 课程开始时间
     */
    private String time;

    private DialogListUtil dialogListUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_setting);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }


    private void initData() {

        creator = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, "");

        dialogListUtil = new DialogListUtil(this);

        presenter = new ScheduleSettingPresenter(this);

        courseScheduleListBean = (ScheduleModel.ResponseDataObjBean) getIntent().getBundleExtra("schedule").getSerializable("schedulebundle");

        bean = courseScheduleListBean.getCourseScheduleList().get(0);

        scheduleplanBean = bean.getScheduleplan();

        scheduleId = bean.getCoursescheduleid();

        description = bean.getDescription();

        if (scheduleplanBean != null){
            time = scheduleplanBean.getStarttime();
        }

        tv_schedule_time.setText(time);

        switch_schedule.setImageResource(bean.getStatus().equals("enable") ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);

        isPause = bean.getStatus().equals("enable") ? true : false;
    }

    private void initView() {
        title.setText("课程设置");
        startDate.set(0, 0, 0, 9, 00);//开始日期
        endDate.set(0, 0, 0, 21, 00);//末尾日期
    }

    @OnClick({R.id.back, R.id.rl_schedule_pause, R.id.tv_schedule_manage, R.id.rl_schedule_time, R.id.tv_schedule_clean, R.id.tv_schedule_share})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_schedule_pause:
                break;
            case R.id.tv_schedule_manage:
                //弹出窗口
                dialogListUtil.showScheduleStatus(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.ll_close) {
                            tDialog.dismiss();
                        }
                    }
                }, new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                        //这里进行发布或删除操作
                        DialogScheduleModel status = dialogListUtil.getScheduleStutas(position);

                        if (status.getOpen() != null && status.getOpen()) {
                            //如果打开，则关删除课表
                            presenter.deletePublishSchedule(position, status.getId(), bean.getCoursescheduleid());
                            ((ImageView) itemView.findViewById(R.id.iv_switch)).setImageResource(R.drawable.icon_switch_n);
                        } else {
                            //如果关闭，则发布课表
                            presenter.publishSchedule(position, status.getId(), UserUtil.user().getCustomerid(),
                                    PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                                    bean.getCoursescheduleid(), bean.getDescription(), bean.getName(), bean.getScheduleContents(), bean.getScheduleplan());
                            ((ImageView) itemView.findViewById(R.id.iv_switch)).setImageResource(R.drawable.icon_switch_p);
                        }
                        //发布课表后推送到机器人
                        presenter.pushMessage(status.getChannelid(), PushParams.UPDATE_COURSE_SCHEDULE, null);
                    }
                });
                break;
            case R.id.rl_schedule_time:
                showDatePickerDialog(this);
                break;
            case R.id.tv_schedule_clean:

                showConfirmDialog(this, "确定清空课表所有内容吗?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                break;
                            case R.id.tv_confirm:
                                //删除课表
                                presenter.deleteSchedule(scheduleId);
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_schedule_share:
                //分享到广场,课表没有封面
                showShareDialog(this, new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            presenter.shareToSquare(UserUtil.user().getCustomerid(), creator, "的课表",
                                    ((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString(), "",
                                    Constant.OFFICIAL_FORUM, null, "coursescheduleid", scheduleId);
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }


    //时间选择弹窗
    private void showDatePickerDialog(final Context context) {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_schedule_time.setText(DateUtil.formatHHmm(date));
                selectedDate.setTime(date);
                time = DateUtil.formatHHmm(date);
                //直接进行课表更新
                scheduleplanBean.setStarttime(DateUtil.formatHHmm(date));
                presenter.updateSchedule(UserUtil.user().getCustomerid(), creator, scheduleId, description, bean.getName(), bean.getScheduleContents(), scheduleplanBean, bean.getStatus());
            }
        })
                .setSubmitColor(Color.parseColor("#ff7781")).setSubmitText("存储").setCancelColor(Color.BLACK).setCancelText("课程开始时间")
                .setRangDate(startDate, endDate).setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒").build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.setDate(selectedDate);
        pvTime.show();
    }

    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void deleteScheduleSuccess() {


        String info = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, "");

        List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> list = GsonUtil.GsonToList(info, DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);

        //通知所有机器人删除了课表
        for (int i = 0; i < list.size(); i++) {
            presenter.pushMessage(list.get(i).getChannelid_listen_on_pushserver(), PushParams.UPDATE_COURSE_SCHEDULE, null);
        }

        ToastUtil.showShort("删除课表成功！");
        setResult(RequestCode.REQUEST_SCHEDULE_DELETE);
        finish();
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        String type = (String) status;

        switch (type) {
            case "publish_schedule":
                ToastUtil.showShort("已开始课程");
                dialogListUtil.setScheduleStutas((Integer) aux, true);
                break;
            case "delete_schedule":
                ToastUtil.showShort("已暂停课程");
                dialogListUtil.setScheduleStutas((Integer) aux, false);
                break;
            case "update_schedule":
//                ToastUtil.showShort("播放时间为:" + (String) data);
                ToastUtil.showShort("课表更新成功！");
                Intent it = new Intent();
                it.putExtra("starttime", (String) data);
                setResult(RequestCode.REQUEST_SCHEDULE_SETTING, it);
                break;
        }
    }


}
