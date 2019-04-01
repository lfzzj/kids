package com.hamitao.base_module.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.base_module.R;

/**
 * Created by linjianwen on 2019/1/10.
 */
public class ListItemView extends RelativeLayout {

    private View view;

    private TextView tv_right, tv_left;

    private Context mContext;

    public ListItemView(Context context) {
        super(context);
        initView(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public ListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, this);
        tv_left = view.findViewById(R.id.tv_left);
        tv_right = view.findViewById(R.id.tv_right);
    }


    /**
     * 是否显示右边的箭头
     *
     * @param isShow
     */
    public void isShowRightArrow(boolean isShow) {
        if (isShow) {
            Drawable arrow = ContextCompat.getDrawable(mContext, R.drawable.icon_arrow_right);
            arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
            tv_right.setCompoundDrawables(null, null, arrow, null);
        } else {
            tv_right.setCompoundDrawables(null, null, null, null);
        }
    }


    /**
     * 设置右边的文本
     */
    public void setRightText(String str) {
        tv_right.setText(str);
    }


    /**
     * 设置左边图标
     * @param resourceid
     */
    public void setLeftIcon(int resourceid) {
        Drawable arrow = ContextCompat.getDrawable(mContext, resourceid);
        arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
        tv_right.setCompoundDrawables(null, null, arrow, null);
    }

    /**
     * 设置左边文本
     */
    public void setLeftText(String str){
        tv_left.setText(str);
    }


}
