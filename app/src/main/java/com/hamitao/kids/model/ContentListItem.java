package com.hamitao.kids.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hamitao.kids.R;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

/**
 * Created by linjianwen on 2018/1/30.
 */

public class ContentListItem extends AbstractAdapterItem implements View.OnClickListener {

    private TextView contentName;
    private CheckBox checkBox;
    private ContentList contentList;
    private ClearListener listener;
    private int position;

    @Override
    public void onClick(View v) {
        listener.clear(position);//清除其他已选的课程
    }

    public interface ClearListener {
        void clear(int position);
    }

    public void setClearListener(ClearListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_contentlist;
    }

    @Override
    public void onBindViews(final View root) {
        contentName = (TextView) root.findViewById(R.id.tv_content);
        checkBox = (CheckBox) root.findViewById(R.id.cb_content);
    }

    @Override
    public void onSetViews() {

    }


    //更新View
    @Override
    public void onUpdateViews(Object model, int position) {
        this.position = position;
        if (model instanceof ContentList) {
            contentList = (ContentList) model;
            contentName.setText(contentList.content_name.getTitle());
            checkBox.setChecked(contentList.isSelected());
            checkBox.setOnClickListener(this);
        }
    }


}
