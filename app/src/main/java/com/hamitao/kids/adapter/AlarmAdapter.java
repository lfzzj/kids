package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.R;
import com.hamitao.kids.model.AlarmModel.ResponseDataObjBean.TimerAlarmsBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/2/3.
 */

public class AlarmAdapter extends BGARecyclerViewAdapter<TimerAlarmsBean> {


    public AlarmAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_alarm);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, TimerAlarmsBean timer) {

        helper.getTextView(R.id.tv_time).setText(timer.getTimers().get(0).getStarttime());

        helper.getTextView(R.id.tv_cycle).setText(formatDay(timer.getTimers().get(0).getDay().toString()));

        if ("".equals(timer.getName())) {
            helper.getTextView(R.id.tv_lable).setText("");
        } else {
            if (!helper.getTextView(R.id.tv_cycle).getText().toString().equals("")) {
                helper.getTextView(R.id.tv_lable).setText(timer.getName() + ",");
            } else {
                helper.getTextView(R.id.tv_lable).setText(timer.getName());
            }
        }

        helper.getImageView(R.id.iv_isopen).setImageResource(timer.getStatus().equals("enable") ? R.drawable.icon_switch_p : R.drawable.icon_switch_n);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.iv_isopen);
    }


    //格式化天数
    private String formatDay(String day) {

        String result = "";

        String[] a = day.split(",");

        String[] b = new String[a.length];

        for (int i = 0; i < a.length; i++) {

            switch (a[i]) {
                case "SUN":
                    b[i] = "周日";
                    break;
                case "MON":
                    b[i] = "周一";
                    break;
                case "TUE":
                    b[i] = "周二";
                    break;
                case "WED":
                    b[i] = "周三";
                    break;
                case "THU":
                    b[i] = "周四";
                    break;
                case "FRI":
                    b[i] = "周五";
                    break;
                case "SAT":
                    b[i] = "周六";
                    break;

            }
        }

        return com.qiniu.android.jpush.utils.StringUtils.join(b, ",");
    }
}
