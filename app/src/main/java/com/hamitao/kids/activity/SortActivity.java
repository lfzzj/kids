package com.hamitao.kids.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.customview.CornerLinearLayoutView;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.CategoryModel;
import com.hamitao.kids.network.NetworkRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分类界面
 */

@Route("sort")
public class SortActivity extends BaseActivity implements CornerLinearLayoutView.ClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;

    int[] drawables = {R.drawable.icon_sort_age, R.drawable.icon_sort_subject, R.drawable.icon_sort_content,
            R.drawable.icon_sort_scene, R.drawable.icon_sort_education, R.drawable.icon_sort_knowledge};

    String sortJson = "{\n" +
            "    \"contentCategories\": [\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"年龄\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"0-1岁\",\n" +
            "                \"1-2岁\",\n" +
            "                \"2-3岁\",\n" +
            "                \"3-5岁\",\n" +
            "                \"5-6岁\",\n" +
            "                \"6+岁\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"科目\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"语文\",\n" +
            "                \"数学\",\n" +
            "                \"英语\",\n" +
            "                \"社会\",\n" +
            "                \"科学\",\n" +
            "                \"艺术\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"内容\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"国学\",\n" +
            "                \"绘本\",\n" +
            "                \"动画\",\n" +
            "                \"儿歌\",\n" +
            "                \"故事\",\n" +
            "                \"常识\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"情景\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"睡觉\",\n" +
            "                \"起床\",\n" +
            "                \"习惯\",\n" +
            "                \"健康\",\n" +
            "                \"安全\",\n" +
            "                \"吃饭\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"育儿\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"科学育儿\",\n" +
            "                \"宝宝健康\",\n" +
            "                \"营养食谱\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"categoryname_level0\": \"教材\",\n" +
            "            \"categoryname_level1\": [\n" +
            "                \"一年级\",\n" +
            "                \"二年级\",\n" +
            "                \"三年级\",\n" +
            "                \"四年级\",\n" +
            "                \"五年级\",\n" +
            "                \"六年级\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
//        queryCategory();
        //分类写死处理
        CategoryModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(sortJson, CategoryModel.ResponseDataObjBean.class);
        initCategory(bean);

    }

    private void initView() {
        title.setText("分类");
    }

    /**
     * 查询分类关键词
     */
    private void queryCategory() {
        NetworkRequest.queryContentCategortRequest(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                CategoryModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CategoryModel.ResponseDataObjBean.class);
                initCategory(bean);
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

    /**
     * 初始化分类
     * @param bean
     */
    private void initCategory(CategoryModel.ResponseDataObjBean bean) {
        for (int i = 0; i < bean.getContentCategories().size(); i++) {
            String[] strArray = bean.getContentCategories().get(i).getCategoryname_level1().toArray(new String[bean.getContentCategories().get(i).getCategoryname_level1().size()]);
            CornerLinearLayoutView linearLayout = new CornerLinearLayoutView(this, bean.getContentCategories().get(i).getCategoryname_level0(), drawables[i], strArray);
            if (bean.getContentCategories().get(i).getCategoryname_level0().equals("育儿")) {
                linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(this,80)));
            }
            linearLayout.setListener(this);
            ll_all.addView(linearLayout);
        }
    }

    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }


    @Override
    public Void click(View view, String tag, String[] data, int index) {
        Router.build("sort_result").with("content", data).with("contenttype", tag).with("index", index).go(this);
        return null;
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
}
