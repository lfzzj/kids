package com.hamitao.base_module.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by linjianwen on 2018/2/28.
 * 设置 RecyclerView 的间距
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {


    //上下间距
    private int topBottomSpace;

    //左右间距
    private int leftRightSpace;

    public SpacesItemDecoration(int topBottomSpace, int leftRightSpace) {
        this.topBottomSpace = topBottomSpace;
        this.leftRightSpace = leftRightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = leftRightSpace;
        outRect.right = leftRightSpace;
        outRect.bottom = topBottomSpace;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = topBottomSpace;
    }

}
