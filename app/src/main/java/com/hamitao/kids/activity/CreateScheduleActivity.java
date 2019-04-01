package com.hamitao.kids.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.SchedulerAdapter;
import com.hamitao.kids.model.scheduler.ScheduleContentsBean;
import com.hamitao.kids.model.scheduler.ScheduleModel;
import com.hamitao.kids.model.scheduler.ScheduleModel.ResponseDataObjBean.CourseScheduleListBean;
import com.hamitao.kids.model.scheduler.ScheduleplanBean;
import com.hamitao.kids.mvp.schedule.SchedulePresenter;
import com.hamitao.kids.mvp.schedule.ScheduleView;
import com.hamitao.kids.view.ScheduleItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

import static com.hamitao.base_module.RequestCode.REQUEST_EDIT_SCHEDULE;

/**
 * 生成课表
 */

@Route("create_schedule")
public class CreateScheduleActivity extends BaseActivity implements ScheduleView, BGAOnRVItemClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView schedule_setting;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.tv_7)
    TextView tv7;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private SchedulerAdapter adapter;
    private SchedulePresenter presenter;
    private List<ScheduleContentsBean> courseNameList = new ArrayList<>(); //课程内容列表
    private ScheduleModel.ResponseDataObjBean courseScheduleListBean;  //获取到的课程表

    private ScheduleplanBean plan; //课表播放计划
    private String courseName = ""; //加入课表传过来课程名
    private String titleName; //课表标题名
    private String customerid = ""; //来自广场的课表

    private boolean isFromSquare; //是否来自广场

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new SchedulePresenter(this);
        adapter = new SchedulerAdapter(recyclerView);
        isFromSquare = getIntent().getBooleanExtra("fromsquare", false); //是否来自广场
        customerid = getIntent().getStringExtra("customerid"); //点击广场传过来的用户ID
        titleName = getIntent().getStringExtra("titlename");//广场传过来的标题
        courseName = getIntent().getStringExtra("coursename") == null ? "" : getIntent().getStringExtra("coursename"); //获取传过来的课程名称
        if (isFromSquare) {
            presenter.queryMySchedule(customerid);
            title.setText(titleName);
        } else {
            //查询当前用户的课表
            presenter.queryMySchedule(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null); //查询我的课表
            adapter.setOnRVItemClickListener(this);//如果来自广场则不设置点击事件
        }

        initTable(); //初始化表格
    }

    //初始化80个空格子，作为占用数据
    private void initTable() {
        List<ScheduleContentsBean> list = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            list.add(null);
        }
        adapter.setData(list);
    }


    private void initView() {
        title.setText("我的课表");
        schedule_setting.setText(courseName.equals("") ? "课程设置" : "确定");
        schedule_setting.setVisibility(isFromSquare ? View.GONE : View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        recyclerView.addItemDecoration(new ScheduleItemDecoration(this));
        recyclerView.setAdapter(adapter);
        setWeek();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //编辑课表
        if (requestCode == REQUEST_EDIT_SCHEDULE && resultCode == REQUEST_EDIT_SCHEDULE) {
            if (data != null) {
                //此处进行更新课表
                String courseName = data.getStringExtra("courseName"); //获取课程名
                int postion = data.getIntExtra("position", 0); // 获取课程的位置
                String info = data.getStringExtra("info"); //具体ID
                String infotype = data.getStringExtra("infotype"); //内容类型
                setCourse(postion, courseName, info, infotype); //设置课位置

                if (courseScheduleListBean == null){
                    return;
                }
                if (courseScheduleListBean.getCourseScheduleList() == null || courseScheduleListBean.getCourseScheduleList().size() == 0) {
                    // 创建课表
                    createSchedule();
                } else {
                    updateSchedule(courseNameList, courseScheduleListBean.getCourseScheduleList().get(0).getScheduleplan());
                }
            }
        } else if (requestCode == RequestCode.REQUEST_SCHEDULE_SETTING && resultCode == RequestCode.REQUEST_SCHEDULE_DELETE) {
            //将课表设为空
            courseScheduleListBean.setCourseScheduleList(null);
            //设置课表
            initTable();
        } else if (requestCode == RequestCode.REQUEST_SCHEDULE_SETTING && resultCode == RequestCode.REQUEST_SCHEDULE_SETTING) {

            courseScheduleListBean.getCourseScheduleList().get(0).getScheduleplan().setStarttime(data.getStringExtra("starttime"));
        } else if (requestCode == RequestCode.REQUEST_COURSE_DETAIL && resultCode == RequestCode.REQUEST_COURSE_DETAIL) {
            //课表为空则返回
            if (courseScheduleListBean == null){
                return;
            }
            if (courseScheduleListBean.getCourseScheduleList() == null || courseScheduleListBean.getCourseScheduleList().size() == 0) {
                //未创建课表则只清除内容，不更新
                adapter.setItem(data.getIntExtra("position", -1), null);
            } else {
                //更新课表
                if (data != null) {
                    int position = data.getIntExtra("position", -1);
                    if (position != -1) {
                        List<ScheduleContentsBean> list = new ArrayList<>();

                        adapter.setItem(position, null);

                        for (int i = 0; i < adapter.getData().size(); i++) {
                            if (adapter.getData().get(i) != null) {
                                list.add(adapter.getData().get(i));
                            }
                        }
                        updateSchedule(list, courseScheduleListBean.getCourseScheduleList().get(0).getScheduleplan());
                        //避免内存泄漏
                        list.clear();
                        list = null;
                    }
                }
            }
        }
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:

                if (courseScheduleListBean == null) {
                    return;
                }
                //课表设置
                if (schedule_setting.getText().equals("确定")) {
                    //创建课表
                    if (courseScheduleListBean.getCourseScheduleList() == null || courseScheduleListBean.getCourseScheduleList().size() == 0) {
                        createSchedule(); // 创建课表
                    } else {
                        //更新课表
                        updateSchedule(courseNameList, courseScheduleListBean.getCourseScheduleList().get(0).getScheduleplan());
                    }
                } else {
                    if (courseScheduleListBean == null || courseScheduleListBean.getCourseScheduleList() == null || courseScheduleListBean.getCourseScheduleList().size() <= 0) {
                        ToastUtil.showShort("您尚未创建课表");
                    } else {
                        Bundle schedulebundle = new Bundle();
                        schedulebundle.putSerializable("schedulebundle", courseScheduleListBean);

                        Router.build("schedule_setting")
                                .with("schedule", schedulebundle)
                                .requestCode(RequestCode.REQUEST_SCHEDULE_SETTING).go(this);
                    }
                }
                break;
            default:
                break;
        }
    }


    //设置课程表的星期时间
    @SuppressLint("SetTextI18n")
    private void setWeek() {
        List<Spannable> times = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Spannable span = new SpannableString(DateUtil.getWeekData().get(i).getNum() + DateUtil.getWeekData().get(i).getTime());
            span.setSpan(new AbsoluteSizeSpan(16, true), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new ForegroundColorSpan(Color.parseColor("#191919")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            times.add(span);
        }

        tv1.setText(times.get(0));
        tv2.setText(times.get(1));
        tv3.setText(times.get(2));
        tv4.setText(times.get(3));
        tv5.setText(times.get(4));
        tv6.setText(times.get(5));
        tv7.setText(times.get(6));
    }


    /**
     * 设置课程,将模型钟的 description 做为用户的图片连接
     *
     * @param position
     * @param courseName
     * @param info
     * @param infotype
     */
    private void setCourse(int position, String courseName, String info, String infotype) {
        ScheduleContentsBean contentsBean = createCourse(position, courseName, "", info, infotype);
        courseNameList.add(contentsBean);
        adapter.setItem(position, contentsBean);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("coursebean", adapter.getData().get(position));
        //不是第一列
        if (position % 8 != 0) {
            //如果歌单等传进来的课程名字courseName为则跳转到课程表编辑页
            if (!courseName.equals("")) {
                //当课表的某节课为空，创建课程。
                if (adapter.getData().get(position) == null) {
                    String info = getIntent().getStringExtra("info");
                    String infotype = getIntent().getStringExtra("infotype");
                    //将课程加入课程列表中
                    courseNameList.add(createCourse(position, courseName, "", info, infotype));
                    adapter.setItem(position, createCourse(position, courseName, "", info, infotype));
                    courseName = "";
                } else {
                    Router.build("course_detail")
                            .requestCode(RequestCode.REQUEST_COURSE_DETAIL)
                            .with("info", adapter.getData().get(position).getInfo())
                            .with("infotype", adapter.getData().get(position).getInfotype())
                            .with("position", position)
                            .go(this);
                }
            } else {
                //原课表的内容
                if (adapter.getData().get(position) == null) {
                    Router.build("edit_schedule").with("position", position).requestCode(REQUEST_EDIT_SCHEDULE).go(CreateScheduleActivity.this);
                } else {
                    Router.build("course_detail")
                            .requestCode(RequestCode.REQUEST_COURSE_DETAIL)
                            .with("info", adapter.getData().get(position).getInfo())
                            .with("infotype", adapter.getData().get(position).getInfotype())
                            .with("position", position)
                            .go(this);
                }
            }
        }

    }


    /**
     * 创建一节课
     *
     * @param position    课程的位置
     * @param title       课程名
     * @param description 课程描述
     * @param info        ID
     * @param infotype    类型
     * @return
     */
    private ScheduleContentsBean createCourse(int position, String title, String description, String info, String infotype) {
        //构造函数带标题
        ScheduleContentsBean content = new ScheduleContentsBean();
        content.setTitle(title);
        //这个是课程描述
        content.setDescription(description);
        content.setInfotype(infotype);
        content.setInfo(info);
        //课程计划
        ScheduleContentsBean.ScheduleBean scheduleBean = new ScheduleContentsBean.ScheduleBean();
        scheduleBean.setDay("MON");
        scheduleBean.setEndtime("");
        scheduleBean.setStarttime("10:00");
        content.setSchedule(scheduleBean);

        int index = (position / 8);
        //设置课程下标
        scheduleBean.setIndex(index + "");

        switch (position % 8) {
            case 1:
                scheduleBean.setDay("SUN");
                break;
            case 2:
                scheduleBean.setDay("MON");
                break;
            case 3:
                scheduleBean.setDay("TUE");
                break;
            case 4:
                scheduleBean.setDay("WED");
                break;
            case 5:
                scheduleBean.setDay("THU");
                break;
            case 6:
                scheduleBean.setDay("FRI");
                break;
            case 7:
                scheduleBean.setDay("SAT");
                break;
            default:
                break;
        }
        return content;
    }

    //创建课表
    private void createSchedule() {
        //先清空课表中的数据
        courseNameList.clear();
        //在将不为空的数据过滤，进行创建课表
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getData().get(i) != null) {
                courseNameList.add(adapter.getData().get(i));
            }
        }

        if (courseNameList.size() <= 0) {
            ToastUtil.showShort("请先添加课程");
            return;
        }


        //创建默认的播放计划，早上10点开始播放
        plan = new ScheduleplanBean();
        plan.setDay("SUN,MON,TUE,WED,THU,FRI,SAT"); //播放循环
        plan.setEndtime(""); //结束时间
        plan.setStarttime("10:00"); //默认为早上10点播放

        presenter.createSchedule(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                "课表描述", "我的课表", courseNameList, plan);
    }

    //更新课表
    private void updateSchedule(List<ScheduleContentsBean> list, ScheduleplanBean plan) {


        if (schedule_setting.getText().toString().equals("确定")) {
            schedule_setting.setText("课程设置");
        }

        List<ScheduleContentsBean> contentsBeans = new ArrayList<>();
        //在将不为空的数据过滤，进行创建课表
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                contentsBeans.add(list.get(i));
            }
        }
        CourseScheduleListBean bean = courseScheduleListBean.getCourseScheduleList().get(0);
        presenter.updateSchedule(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""),
                bean.getCoursescheduleid(), bean.getDescription(), bean.getName(), contentsBeans, plan);

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
    public void initSchedulerSuccess(ScheduleModel.ResponseDataObjBean bean) {
        //查询课课表获取到的数据
        courseScheduleListBean = bean;
        //先初始化表格，再填充数据
        initTable();
        //获取课程数据
        courseNameList = bean.getCourseScheduleList().get(0).getScheduleContents();
        //根据课程内容数做遍历
        for (int i = 0; i < courseNameList.size(); i++) {
            ScheduleContentsBean a = courseNameList.get(i);
            int index = Integer.parseInt(a.getSchedule().getIndex());
            if (a.getSchedule().getDay() != null) {
                switch (a.getSchedule().getDay()) {
                    case "SUN":
                        adapter.setItem((index) * 8 + 1, a);
                        break;
                    case "MON":
                        adapter.setItem((index) * 8 + 2, a);
                        break;
                    case "TUE":
                        adapter.setItem((index) * 8 + 3, a);
                        break;
                    case "WED":
                        adapter.setItem((index) * 8 + 4, a);
                        break;
                    case "THU":
                        adapter.setItem((index) * 8 + 5, a);
                        break;
                    case "FRI":
                        adapter.setItem((index) * 8 + 6, a);
                        break;
                    case "SAT":
                        adapter.setItem((index) * 8 + 7, a);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void createSchedulerSuccess(ScheduleModel.ResponseDataObjBean courseScheduleListBean) {
        if (schedule_setting.getText().toString().equals("确定")) {
            schedule_setting.setText("课程设置");
        }
        initSchedulerSuccess(courseScheduleListBean);
    }


    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String)status){
            case "update":
                //更新课表
                ToastUtil.showShort("课表更新成功！");
                break;
        }
    }
}

