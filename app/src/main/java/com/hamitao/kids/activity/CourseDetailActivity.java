package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.customview.RoundCornerImageView;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.mvp.CommonView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 课程详情
 */
@Route("course_detail")
public class CourseDetailActivity extends BaseActivity implements CommonView {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.iv_face)
    RoundCornerImageView ivFace;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_music_list)
    TextView tv_music_list;

    @BindView(R.id.tv_descrpiption)
    TextView tvDescrpiption;

//    private ScheduleContentsBean bean; //课表中传过来的课程

    private CommonPresenter presenter;

    private String img;
    private String info;
    private String infotype;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        presenter = new CommonPresenter(this);
        info = getIntent().getStringExtra("info");
        infotype = getIntent().getStringExtra("infotype");
        position = getIntent().getIntExtra("position", 0);

        queryInfo(infotype);
    }

    private void queryInfo(String infotype) {

        switch (infotype) {
            case "mediaid":
                presenter.queryMediaById(UserUtil.user() != null ? UserUtil.user().getCustomerid() : null, info, position);
                break;
            case "recordid":
                presenter.queryRecordById(info);
                break;
            case "contentid":
                presenter.queryMediaList(UserUtil.user().getCustomerid(), info, position);
                break;
            default:
                break;

        }
    }


    private void initView() {
        title.setText("课程详情");
        tv_music_list.setVisibility(infotype.equals("contentid") ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.back, R.id.iv_delete, R.id.tv_music_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_delete:
                showConfirmDialog(this, "确定要删除此课程吗?", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            setResult(RequestCode.REQUEST_COURSE_DETAIL, new Intent().putExtra("position", position));
                            finish();
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_music_list:
                Router.build("music_list").with("contentid", info).go(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshData(Object status, Object data, Object aux) {
        String code = (String) status;
        if (code.equals("record")) {
            RecordModel.ResponseDataObjBean bean = (RecordModel.ResponseDataObjBean) data;
            tvTitle.setText(bean.getVoiceRecordings().get(0).getName() + "[录音]");
            tvDescrpiption.setText(bean.getVoiceRecordings().get(0).getDescription());
        } else if (code.equals("media")) {
            MediaModel.ResponseDataObjBean musicbean = (MediaModel.ResponseDataObjBean) data;
            tvTitle.setText(StringUtil.deleteNumber(musicbean.getContents().get(0).getMediaList().get(0).getMediatitle()));
            Glide.with(this).load(musicbean.getContents().get(0).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(ivFace);
        } else if (code.equals("content")) {
            ContentModel.ResponseDataObjBean bean = (ContentModel.ResponseDataObjBean) data;
            tvTitle.setText(bean.getContents().get(0).getNameI18List().get(0).getValue() + "[专辑]");
            tvDescrpiption.setText(bean.getContents().get(0).getDescriptionI18List().get(0).getValue());
            Glide.with(this).load(bean.getContents().get(0).getImgtitleurlhttpURL()).error(R.drawable.default_cover).into(ivFace);
        }

    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }
}
