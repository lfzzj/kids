
package com.hamitao.kids.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.KeywordModel;
import com.hamitao.kids.network.NetworkRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linjianwen on 2018/3/22.
 */


public class TagView extends FrameLayout {


    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout; //热搜

    @BindView(R.id.flexbox_search_record)
    FlexboxLayout record_layout; //搜索记录

    @BindView(R.id.tv_search_record)
    TextView tv_search_record;

    @BindView(R.id.rl_search_record)
    RelativeLayout rl_search_record;

    @BindView(R.id.line)
    View line;

    @BindView(R.id.tv_change)
    TextView tv_change;


    private String[] hotTags; //热门搜索标签
    private String[] recordTags; //搜索记录标签
    private String recordStr = "";//搜索记录

    public TagListener listener;

    public interface TagListener {
        void getTag(String str); //搜索后切换
    }

    public void setTagListener(TagListener listener) {
        this.listener = listener;
    }

    public TagView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_tags, this);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        //获取热词
        getKeywoeds();
        //第一次进入页面读取本地的历史搜索记录
        recordStr = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Search_Record, "");
        //初始化标签
        initTags(recordStr);
    }


    public void initTags(String recordStr) {
        record_layout.removeAllViews();
        recordTags = recordStr.split(",");
        if (recordStr.equals("")) {
            record_layout.setVisibility(View.GONE);
            rl_search_record.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            record_layout.setVisibility(View.VISIBLE);
            rl_search_record.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            for (int i = recordTags.length - 1; i >= 0; i--) {
                //只显示10条历史记录
                if (recordTags.length - i < 11) {
                    addLable(recordTags[i], 2);
                }

            }
        }
    }


    /**
     * 添加标签
     */
    public void addLable(final String label, int type) {
        // 通过代码向FlexboxLayout添加View
        final TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.label_bg_shape);
        textView.setText(label);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_default_d));

        //点击事件
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getTag(textView.getText().toString());
            }
        });
        if (type == 1) {
            flexboxLayout.addView(textView);
        } else {
            record_layout.addView(textView);
        }
        //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        if (params instanceof FlexboxLayout.LayoutParams) {
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
//            layoutParams.setFlexBasisPercent(0.3f);
            layoutParams.setMargins(15, 20, 15, 20);
        }

    }


    @OnClick({R.id.tv_change, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change:
                getKeywoeds();
                break;
            case R.id.iv_delete:
                //清空搜索历史记录
                PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Search_Record, "");
                record_layout.removeAllViews();
                record_layout.setVisibility(View.GONE);
                rl_search_record.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                break;

        }
    }

    /**
     * 热门搜索词
     */
    public String[] getHotWord() {
        String[] tags = {"小猪佩奇", "西游记", "七个小矮人", "小苹果", "巧虎", "哆啦A梦", "超人", "简爱", "国学经典", "早教歌曲", "儿歌三百首"};
        return tags;
    }

    private void getKeywoeds() {

        NetworkRequest.queryKeywordRequest(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                KeywordModel.ResponseDataObjBean model = GsonUtil.GsonToBean(result.getResponseDataObj(), KeywordModel.ResponseDataObjBean.class);
                if (model.getKeywordList().size() > 0) {

                    //热词随机处理，随机获取10条热词
                    if (flexboxLayout.getChildCount() != 0) {
                        flexboxLayout.removeAllViews();
                    }

                    List<String> list = createRandomList(model.getKeywordList(), 10);
                    for (int i = 0; i < list.size(); i++) {
                        addLable(list.get(i), 1);
                    }

                }
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }


    //生成随机list
    private static List createRandomList(List list, int n) {
        // TODO Auto-generated method stub
        Map map = new HashMap();
        List listNew = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    System.out.println(random + "===========" + list.get(random));
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
    }


}
