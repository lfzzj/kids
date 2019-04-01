package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.base_module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.kids.activity.AddAlarmActivity.SELECT_WEEK;

@Route("select_week")
public class SelectWeekActivity extends BaseActivity {

    @BindView(R.id.cb1)
    CheckBox cb1;
    @BindView(R.id.cb2)
    CheckBox cb2;
    @BindView(R.id.cb3)
    CheckBox cb3;
    @BindView(R.id.cb4)
    CheckBox cb4;
    @BindView(R.id.cb5)
    CheckBox cb5;
    @BindView(R.id.cb6)
    CheckBox cb6;
    @BindView(R.id.cb7)
    CheckBox cb7;

    private StringBuilder stringBuilder = new StringBuilder();


    private String weekStr ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_week);
        ButterKnife.bind(this);

        weekStr = getIntent().getStringExtra("weekStr");
        if (!weekStr.isEmpty()){
            initCb(weekStr);
        }
    }

    private void initCb(String weekStr) {

        if (weekStr.contains("一")){
            cb1.setChecked(true);
        }
        if (weekStr.contains("二")){
            cb2.setChecked(true);
        }
        if (weekStr.contains("三")){
            cb3.setChecked(true);
        }
        if (weekStr.contains("四")){
            cb4.setChecked(true);
        }
        if (weekStr.contains("五")){
            cb5.setChecked(true);
        }
        if (weekStr.contains("六")){
            cb6.setChecked(true);
        }
        if (weekStr.contains("日")){
            cb7.setChecked(true);
        }


    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                backPressed();
                break;
            case R.id.more:
                break;
        }
    }


    /**
     * 退出页面
     */
    private void backPressed() {

        selectWeek();

        Intent it = new Intent();

        it.putExtra("selectWeek", stringBuilder.toString());

        setResult(SELECT_WEEK, it);

        finish();
    }


    @Override
    public void onBackPressed() {
        backPressed();
        super.onBackPressed();

    }

    private void selectWeek() {

        if (cb1.isChecked()) {
            stringBuilder.append("MON,");
        }

        if (cb2.isChecked()) {
            stringBuilder.append("TUE,");
        }

        if (cb3.isChecked()) {
            stringBuilder.append("WED,");
        }
        if (cb4.isChecked()) {
            stringBuilder.append("THU,");
        }

        if (cb5.isChecked()) {
            stringBuilder.append("FRI,");
        }

        if (cb6.isChecked()) {
            stringBuilder.append("SAT,");
        }
        if (cb7.isChecked()) {
            stringBuilder.append("SUN,");
        }

    }


}
