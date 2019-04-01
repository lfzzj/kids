package com.hamitao.base_module.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.base_module.R;

/**
 * Created by linjianwen on 2018/7/28.
 */

public class CornerLinearLayoutView extends RelativeLayout implements View.OnClickListener {

    View view;

    private View line;
    private TextView tv_title;
    private LinearLayout ll1; //第一行内容
    private LinearLayout ll2; //第二行内容

    private TextView tv1, tv2, tv3, tv4, tv5, tv6;

    private String tag;

    private String[] data;


    private ClickListener listener;

    public interface ClickListener {
        Void click(View view, String tag, String[] data, int position);
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public CornerLinearLayoutView(Context context, String tag, int resId, String[] data) {
        super(context);
        this.tag = tag;
        this.data = data;
        initView(context);
        setTitle(tag);
        setData(data);
        setIcon(resId);
    }

    private void setIcon(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_title.setCompoundDrawables(null, drawable, null, null);
    }

    public CornerLinearLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public CornerLinearLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.corner_linearlayout, this);
        tv_title = view.findViewById(R.id.tv_title);
        line = view.findViewById(R.id.line);
        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
    }


    //当只有单条数据的时候设置高度
    public void setHeight(int height) {
        ll1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    //设置标题
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    //设置数据
    public void setData(String[] data) {
        if (data.length == 3) {
            ll2.setVisibility(GONE);
            line.setVisibility(GONE);
            tv1.setText(data[0]);
            tv2.setText(data[1]);
            tv3.setText(data[2]);
        } else if (data.length == 6) {
            ll2.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
            tv1.setText(data[0]);
            tv2.setText(data[1]);
            tv3.setText(data[2]);
            tv4.setText(data[3]);
            tv5.setText(data[4]);
            tv6.setText(data[5]);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv1) {
            listener.click(view, tag, data, 0);
        } else if (i == R.id.tv2) {
            listener.click(view, tag, data, 1);
        } else if (i == R.id.tv3) {
            listener.click(view, tag, data, 2);
        } else if (i == R.id.tv4) {
            listener.click(view, tag, data, 3);
        } else if (i == R.id.tv5) {
            listener.click(view, tag, data, 4);
        } else if (i == R.id.tv6) {
            listener.click(view, tag, data, 5);

        }

    }
}
