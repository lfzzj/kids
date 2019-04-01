package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.kids.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("add_course")
public class AddCourseActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_schedule_name)
    TextView tvScheduleName;

    private String courseName;
    private String info; //内容具体ID
    private String infotype; //内容类型
    private String imgUrl;  //图片地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        courseName = getIntent().getStringExtra("coursename");
        info = getIntent().getStringExtra("info");
        infotype = getIntent().getStringExtra("infotype");
        imgUrl = getIntent().getStringExtra("imgurl");
    }

    private void initView() {
        title.setText("添加课程");
        tvScheduleName.setText("我的课表");
    }

    @OnClick({R.id.back, R.id.tv_add_course})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_add_course:
                Router.build("create_schedule")
                        .with("coursename", courseName)
                        .with("info", info)
                        .with("infotype", infotype)
                        .with("imgurl", imgUrl)
                        .go(this);
                break;
            default:
                break;
        }
    }
}
