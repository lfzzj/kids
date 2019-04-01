package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.adapter.ViewPagerAdapter;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean.ContentsBean;
import com.hamitao.kids.mvp.search.SearchPresenter;
import com.hamitao.kids.mvp.search.SearchView;
import com.hamitao.kids.view.ResultView;
import com.hamitao.kids.view.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;


@Route("search")
public class SearchActivity extends BaseActivity implements SearchView {


    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.tv_btn)
    TextView tvbtn;

    @BindView(R.id.iv_player)
    ImageView iv_player;

    private TagView tagView; //标签

    private ResultView resultView; //搜索结果

    private SearchPresenter presenter;

    private List<View> views = new ArrayList<>();

    //播放器相关——————————————————————————————————————————————————
    @BindView(R.id.videoview)
    Jzvd videoview;
    //播放器相关——————————————————————————————————————————————————


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();

    }


    private void initData() {
        presenter = new SearchPresenter(this);
        tagView = new TagView(this);
        resultView = new ResultView(this);
        tagView.setTagListener(new TagView.TagListener() {
            @Override
            public void getTag(String tag) {
                etSearch.setText(tag);
                etSearch.setSelection(tag.length());
                //点击标签后直接进行搜索
                viewpager.setCurrentItem(1);
                String recordStr = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Search_Record, "");
                //搜索记录不包含该关键字
                if (!recordStr.contains(etSearch.getText().toString().trim())) {
                    //,保存搜索记录，这里使用 ， 来分割搜索记录
                    LogUtil.d("recordStr", "recordStr=" + recordStr + etSearch.getText().toString().trim() + ",");
                    PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Search_Record, recordStr + etSearch.getText().toString().trim() + ",");
                    String str = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Search_Record, "");
                    tagView.initTags(str);
                }
                presenter.searchContent(new String[]{etSearch.getText().toString()});
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入框改变的时候，切换到第一个页面
                tvbtn.setText("搜索");
                viewpager.setCurrentItem(0);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        resultView.setCallback(new ResultView.PlayVideoCallback() {
            @Override
            public void statVieo(String url, String title) {
                videoview.setVisibility(View.VISIBLE);
                Jzvd.startFullscreen(SearchActivity.this, Jzvd.class, url, title);
            }
        });


    }

    private void initView() {

        etSearch.setEnabled(true);

        editTextable(etSearch, true);

        tvbtn.setText("搜索");

        tvbtn.setBackground(null);

        title.setText("搜索");

        views.add(tagView);

        views.add(resultView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(views, null);

        viewpager.setAdapter(adapter);
    }

    /**
     * 设置EditText是否可编辑
     */
    private void editTextable(EditText editText, boolean editable) {
        if (!editable) {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            editText.setClickable(false);
        } else {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setClickable(true);
        }
    }


    @OnClick({R.id.back, R.id.tv_btn, R.id.tv_search, R.id.iv_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_btn:
                if (tvbtn.getText().equals("取消")) {
                    tvbtn.setText("搜索");

                    viewpager.setCurrentItem(0);
                } else {
                    //搜索
                    //如果关键字已存在历史记录中则不存入本地
                    if (!etSearch.getText().toString().equals("")) {
                        String recordStr = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Search_Record, "");
                        //搜索记录不包含该关键字
                        if (!recordStr.contains(etSearch.getText().toString().trim())) {
                            //,保存搜索记录，这里使用 ， 来分割搜索记录
                            LogUtil.d("recordStr", "recordStr=" + recordStr + etSearch.getText().toString().trim() + ",");
                            PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Search_Record, recordStr + etSearch.getText().toString().trim() + ",");
                            String str = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Search_Record, "");
                            tagView.initTags(str);
                        }
                        presenter.searchContent(new String[]{etSearch.getText().toString()});
                    } else {
                        ToastUtil.showShort("请输入您要搜索的内容");
                    }
                }
                break;
            case R.id.tv_search:
                //语音识别
                ToastUtil.showShort("语音识别");
                if (viewpager.getCurrentItem() == 1) {
                    viewpager.setCurrentItem(0);
                }
                break;
            case R.id.iv_player:
                PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void setSearchData(List<ContentsBean> list) {
        if (list != null && list.size() > 0) {
            tvbtn.setText("取消");
            viewpager.setCurrentItem(1);
            resultView.refreshData("clean", null, null);
            resultView.setData(list, etSearch.getText().toString()); //传入搜索到的数据
        } else {
            ToastUtil.showShort("抱歉，没有找到符合的数据");
            resultView.setData(null, null);
        }
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {

    }
}
