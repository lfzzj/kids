package com.hamitao.kids.view;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by linjianwen on 2018/2/26.
 * 倒计时
 */

public class MyCountDownTimer extends CountDownTimer {


    private TimerListener listener;

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public interface TimerListener {

        void onComplete();
    }

    private TextView textView;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        //重新给Button设置文字
        textView.setText("重新获取");
        //设置可点击
        textView.setClickable(true);

        listener.onComplete();
    }
}
