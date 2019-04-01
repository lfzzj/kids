package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.kids.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route("schedule_info")
public class NewScheduleInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_info);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("新课表信息");

    }

    @OnClick({R.id.back, R.id.btn_create, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_create:

                break;
            case R.id.btn_share:
                break;
        }
    }
}
