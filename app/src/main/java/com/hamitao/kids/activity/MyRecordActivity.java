package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.RecordAdapter;
import com.hamitao.kids.fragment.PlayFragment;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.record.MyRecordPresenter;
import com.hamitao.kids.mvp.record.MyRecordView;
import com.hamitao.kids.player.AudioPlayer;
import com.hamitao.kids.util.DialogListUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的录音
 *
 * @author linjianwen
 */
@Route("my_record")
public class MyRecordActivity extends BaseActivity implements MyRecordView, BGAOnItemChildClickListener, BGAOnRVItemClickListener {

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_record)
    TextView tvRecord;

    @BindView(R.id.tv_manager)
    TextView tvManager;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;

    /**
     * 录音文件数
     */
    @BindView(R.id.tv_count)
    TextView tvCount;

    private MyRecordPresenter presenter;

    private RecordAdapter adapter;

    private DialogListUtil dialogListUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        ButterKnife.bind(this);
        initData();
        initView();
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

        dialogListUtil = new DialogListUtil(this);

        presenter = new MyRecordPresenter(this);

        //查询录音列表
        presenter.queryMyRecordList(UserUtil.user().getCustomerid());

        adapter = new RecordAdapter(recyclerView);

        adapter.setOnItemChildClickListener(this);

        adapter.setOnRVItemClickListener(this);
    }

    private void initView() {

        title.setText("录音");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_RECORD && resultCode == RequestCode.REQUEST_RECORD) {
            //重新查询录音
            presenter.queryMyRecordList(UserUtil.user().getCustomerid());
        }
    }

    @OnClick({R.id.back, R.id.ll_record, R.id.tv_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_record:
                //进入录音界面
                Router.build("record").requestCode(RequestCode.REQUEST_RECORD).go(this);
                break;
            case R.id.tv_manager:
                //管理录音文件
                break;
            default:
                break;
        }
    }


    /**
     * 点击子View
     */
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, final int position) {
        if (childView.getId() == R.id.list_more) {
            showMoreDialog(position);
        }
    }


    private void showMoreDialog(final int position) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_item_record_more)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_rename, R.id.tv_put, R.id.tv_share, R.id.tv_nfc, R.id.dialog_list_more, R.id.tv_cancel, R.id.tv_delete)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_rename:
                                showRenameDialog(adapter.getData().get(position).getName(), position);
                                break;
                            case R.id.tv_put:
                                //投送到机器人
                                dialogListUtil.showDeliverDialog(new BGAOnRVItemClickListener() {
                                    @Override
                                    public void onRVItemClick(ViewGroup parent, View itemView, int index) {
                                        RecordModel.ResponseDataObjBean.VoiceRecordingsBean bean = adapter.getData().get(position);
                                        presenter.addDeliver(UserUtil.user().getCustomerid(), dialogListUtil.getTerminalId(index), "recordid", bean.getId(), bean.getName(),
                                                bean.getDescription(), dialogListUtil.getTargetChannel(index), new String[]{"recordid", bean.getId()});
                                        dialogListUtil.dismissDialog();
                                    }
                                });
                                break;
                            case R.id.tv_share:
                                showShareDialog(MyRecordActivity.this, new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        RecordModel.ResponseDataObjBean.VoiceRecordingsBean bean = adapter.getData().get(position);
                                        if (view.getId() == R.id.tv_confirm) {

                                            if (((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString().trim().equals("")) {
                                                ToastUtil.showShort("请输入您的心得");
                                            } else {
                                                //录音没有封面

                                                presenter.shareToSquare(UserUtil.user().getCustomerid(), PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Nickname, ""), bean.getName(),
                                                        ((EditText) tDialog.getView().findViewById(R.id.dialog_comment_content)).getText().toString(), "", Constant.OFFICIAL_FORUM,
                                                        new String[]{}, "recordid", bean.getId());
                                            }
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_nfc:
                                if (!BaseApplication.getInstance().isPublicVendor()) {
                                    ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                    return;
                                }

                                Router.build("scan").with("info", adapter.getData().get(position).getId()).with("infotype", "recordid").go(MyRecordActivity.this);
                                break;
                            case R.id.dialog_list_more:
                                if (UserUtil.user() == null) {
                                    showLoginDialog();
                                } else {

                                    if (!BaseApplication.getInstance().isPublicVendor()) {
                                        ToastUtil.showShort(getResources().getString(R.string.still_develop));
                                        return;
                                    }
                                    Router.build("add_course")
                                            .with("coursename", adapter.getData().get(position).getName())
                                            .with("info", adapter.getData().get(position).getId())
                                            .with("infotype", "recordid")
                                            .with("imgurl", "")
                                            .go(MyRecordActivity.this);
                                }
                                break;
                            case R.id.tv_delete:
                                showConfirmDialog(MyRecordActivity.this, "确定要删除录音吗?", new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            String id = adapter.getData().get(position).getId();
                                            presenter.deleteMyRecord(new String[]{id}, adapter.getData().get(position).getUrl(), position);
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_cancel:
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                }).create().show();
    }

    private void showRenameDialog(String oldname, final int position) {
        View view = LayoutInflater.from(this).inflate(com.hamitao.base_module.R.layout.dialog_item_input, null);
        ((TextView) view.findViewById(com.hamitao.base_module.R.id.title)).setText("请输入录音名称");
        EditText et = ((EditText) view.findViewById(com.hamitao.base_module.R.id.et_name));
        et.setText(oldname);
        et.setSelection(et.getText().toString().length());
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.8f)
                .setGravity(Gravity.CENTER)
                .setTag("DialogTest")
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(com.hamitao.base_module.R.id.tv_cancel, com.hamitao.base_module.R.id.tv_confirm)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.tv_confirm) {
                            if (((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString().trim().equals("")) {
                                ToastUtil.showShort("名称不可为空");
                                return;
                            } else {
                                RecordModel.ResponseDataObjBean.VoiceRecordingsBean bean = adapter.getData().get(position);
                                presenter.renameRecord(bean.getDescription(), bean.getId(), ((EditText) tDialog.getView().findViewById(R.id.et_name)).getText().toString(), UserUtil.user().getCustomerid(), bean.getUrl(), position);
                            }
                        }
                        tDialog.dismiss();
                    }
                })
                .create()   //创建TDialog
                .show();    //展示
    }

    /**
     * 点击父View
     *
     * @param parent
     * @param itemView
     * @param position
     */
    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        //点击播放录音
        ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean music = new ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean();
        music.setHttpURL(adapter.getData().get(position).getHttpURL());
        music.setMediaid(adapter.getData().get(position).getId());
        music.setMediatitle(adapter.getData().get(position).getName());
        music.setMediasubtype("record");
        if (AudioPlayer.get().getMusicList().size() > 0) {
            AudioPlayer.get().getMusicList().clear();
        }
        //播放音乐
        AudioPlayer.get().play(music);
        PlayFragment.getInstance().show(getSupportFragmentManager(), "player");
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
    public void getMyRecordList(List<RecordModel.ResponseDataObjBean.VoiceRecordingsBean> voiceRecordings) {
        adapter.setData(voiceRecordings);
        tvCount.setText(adapter.getData().size() == 0 ? "我的录音(" + (0) + ")" : "我的录音(" + (adapter.getData().size()) + ")");
    }

    @Override
    public void deliverSuccess() {
        ToastUtil.showShort("投送成功");
    }


    @Override
    public void refreshData(Object status, Object data, Object aux) {
        String result = (String) status;
        if (result.equals("delete")) {
            //这里删除OSS上的录音
            BaseApplication.getInstance().getOssTool().deleteObject((String) data);
            //删除录音
            adapter.removeItem((int) aux);
            tvCount.setText(adapter.getData().size() == 0 ? "我的录音(" + (0) + ")" : "我的录音(" + (adapter.getData().size()) + ")");
            ToastUtil.showShort("删除成功！");
        } else if (result.equals("rename")) {
            //更新录音
            ToastUtil.showShort("修改成功！");
            adapter.setItem((int) aux, (RecordModel.ResponseDataObjBean.VoiceRecordingsBean) data);
        }
    }
}
