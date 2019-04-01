package com.hamitao.kids.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.base_module.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新建群组信息
 */

@Route("new_group")
public class NewGroupActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_groupName)
    EditText etGroupName;
    @BindView(R.id.gv_group)
    GridView gvGroup;
    @BindView(R.id.more)
    TextView more;

    private GroupAdapter adapter;


    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        adapter = new GroupAdapter();

        for (int i = 0; i < 7; i++) {
            list.add("我是：" + i);
        }


    }

    private void initView() {
        title.setText("新建群组信息");

        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        gvGroup.setAdapter(adapter);

    }

    @OnClick({R.id.back,R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //新建群组完成
                break;
        }
    }


    //群组成员适配器
    class GroupAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(NewGroupActivity.this).inflate(R.layout.item_week_list, parent, false);
                holder = new ViewHolder();
                holder.group = (TextView) convertView.findViewById(R.id.tv_week);
                convertView.setTag(holder);   //将Holder存储到convertView中
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.group.setText(list.get(position));
            return convertView;
        }
    }

    static class ViewHolder {
        TextView group;
    }


}
