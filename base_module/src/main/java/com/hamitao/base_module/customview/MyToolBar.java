package com.hamitao.base_module.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.base_module.R;

/**
 * Created by linjianwen on 2019/1/10.
 */
public class MyToolBar extends RelativeLayout {

    private View view;

    private ImageView rightImg, leftImg;

    private TextView title;

    private RelativeLayout bg;

    private Context mContext;

    public MyToolBar(Context context) {
        super(context);
        initView(context);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    private void initView(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_my_toolbar, this);
        bg = view.findViewById(R.id.rl_bg);
        rightImg = view.findViewById(R.id.img_right);
        leftImg = view.findViewById(R.id.img_left);
        title = view.findViewById(R.id.title);
    }


    /**
     * 是否显示标题
     */
    public void isShowTitle(boolean isShow) {
        title.setVisibility(isShow ? VISIBLE : GONE);
    }


    /**
     * 是否显示右边按钮
     */
    public void isShowRightButton(boolean isShow) {
        leftImg.setVisibility(isShow ? VISIBLE : GONE);
    }


    /**
     * 是否显示左边按钮
     */
    public void isShowLeftButton(boolean isShow) {
        rightImg.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 设置标题
     *
     * @param str
     */
    public void setTitle(String str) {
        title.setText(str);
    }


    /**
     * 设置右按钮图片
     */
    public void setRightImg(int resourceid) {
        rightImg.setImageResource(resourceid);
    }

    /**
     * 设置左按钮图片
     */
    public void setLeftImg(int resourceid) {
        leftImg.setImageResource(resourceid);
    }


    /**
     * 设置toolbar背景颜色
     */
    @SuppressLint("ResourceType")
    public void setBackgroundColor(int resourceid) {
        bg.setBackground(ContextCompat.getDrawable(mContext, resourceid));
    }



}
