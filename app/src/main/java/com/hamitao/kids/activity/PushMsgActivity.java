package com.hamitao.kids.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.PushMsgAdapter;
import com.hamitao.kids.network.NetworkRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * 消息推送平台
 */

@Route("push_msg")
public class PushMsgActivity extends BaseActivity implements BGAOnRVItemClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.expandlist)
    ExpandableListView expandableListView;


    private PushMsgAdapter adapter;

    private List<String> msg = new ArrayList<>();

    private String channelid = "";

    private Myadapter myadapter;
    public String[] menu = {"起床", "洗漱", "吃饭", "学习", "洗澡", "睡觉"};
    public String[][] content = {
            {"小闹钟叮当响，小朋友起床啦。", "小宝宝，起得早，睁开眼，眯眯笑"},
            {"小牙刷，手中拿，刷刷刷，沙沙沙", "洗小手，别忘记，认真搓来，认真洗"},
            {"吃饭啦，一口饭，一口菜", "自己吃，不用喂，吃干净，不浪费"},
            {"小宝宝，年龄小，从小学习很重要", "不爱哭，不爱闹，小小年纪爱学习"},
            {"我爱洗澡，皮肤好好", "小船跑，大船叫，宝宝洗澡好热闹"},
            {"小船儿轻轻摇，小宝宝啊要睡觉", "月亮伴我入梦乡，小摇床，轻轻晃，小星星，挂天上"}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_msg);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {

        channelid = getIntent().getStringExtra("channelid");

        myadapter = new Myadapter();

        expandableListView.setAdapter(myadapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //推送消息到机器人
                pushMessage(channelid, "PUSH_HABIT", new String[]{"push_habit", content[i][i1]});
                ToastUtil.showShort("投送成功！");
                return true;
            }
        });

//        adapter = new PushMsgAdapter(recyclerview);
//
//        adapter.setOnRVItemClickListener(this);

    }

    private void initView() {

        title.setText("习惯培养");

//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        recyclerview.setAdapter(adapter);

//        adapter.setData(msg);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }


    /**
     * 消息推送远程请求
     *
     * @param targetChannelid
     * @param actionType
     * @param contentid       这里是播放内容，OSS上的名字
     */
    public void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                Log.d("推送", "推送成功");

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }

    class Myadapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return menu.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return content[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return menu[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return content[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(PushMsgActivity.this).inflate(R.layout.item_menu_name, parent, false);
                convertView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_menu_name);
                groupViewHolder.ivArrow = (ImageView)convertView.findViewById(R.id.list_more);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
//            groupViewHolder.ivArrow.setVisibility(View.GONE);
            groupViewHolder.tvTitle.setText(menu[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(PushMsgActivity.this).inflate(R.layout.item_single_text, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_text);

                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.tvTitle.setText(content[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        private class ChildViewHolder {
            TextView tvTitle;

        }

        private class GroupViewHolder {
            TextView tvTitle;
            ImageView ivArrow;
        }
    }
}
