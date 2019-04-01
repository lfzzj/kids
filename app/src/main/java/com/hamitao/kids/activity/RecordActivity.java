package com.hamitao.kids.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.RecordUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.record.RecordPresenter;
import com.hamitao.kids.mvp.record.RecordView;
import com.hamitao.kids.player.AudioPlayer;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 录音室界面
 *
 * @author linjianwen
 */

@Route("record")
public class RecordActivity extends BaseActivity implements RecordView {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.btn_startRecord)
    ImageView btnStartRecord;

    @BindView(R.id.tv_saveRecord)
    TextView tvSaveRecord;

    @BindView(R.id.ll_tips)
    LinearLayout ll_tips;


    /**
     * 提示用耳机
     */
    @BindView(R.id.tv_tips)
    TextView tvTips;


    /**
     * 录音时长
     */
    @BindView(R.id.tv_recordTime)
    Chronometer tvRecordTime;


    /**
     * 试听录音时长
     */
    @BindView(R.id.tv_playTime)
    Chronometer tv_playTime;

    /**
     * 试听
     */
    @BindView(R.id.tv_audition)
    TextView tvAudition;

    private RecordPresenter presenter;


    /**
     * 是否正在录音
     */
    private boolean isRecording = false;

    /**
     * 是否正在试听
     */
    private boolean isAudition = false;

    /**
     * 用于播放录音
     */
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
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

        RecordUtil.getInstance().stopRecord(); //停止录音，始放资源

        mediaPlayer.release();
        mediaPlayer = null;

        deleteRecord(); //如果录音文件不为空则先删除文件
        super.onDestroy();
    }

    private void initData() {
        presenter = new RecordPresenter(this, this);
        //初始化媒体播放器，用于播放试听的录音
        mediaPlayer = new MediaPlayer();

    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("录音");
    }

    @OnClick({R.id.back, R.id.btn_startRecord, R.id.tv_audition, R.id.tv_saveRecord, R.id.iv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                deleteRecord(); // 删除录音的临时文件
                finish();
                break;
            case R.id.btn_startRecord:
                //开始录音
                if (isRecording) {
                    stopRecord();
                    isRecording = false;
                    showButton();
                } else {
                    if (isAudition) {
                        ToastUtil.showShort("正在试听");
                        //这里可以开启停止试听，重新开始录音
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            isAudition = false;
                        }
                    }
                    ll_tips.setVisibility(View.GONE);

                    startRecord();
                    isRecording = true;
                    hideButton();
                }
                break;
            case R.id.tv_audition:
                //试听
                audition();
                break;
            case R.id.tv_saveRecord:
                //保存录音
                savaRecord();
                break;
            case R.id.iv_cancle:
                //提示
                ll_tips.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    /**
     * 开始录音
     */
    public void startRecord() {
        //录音时请求焦点
        AudioPlayer.get().getAudioFocusManager().requestAudioFocus(); //请求焦点

        recordReset();
        auditionReset();

        //录音前判断播放器是是否正在播放
        if (AudioPlayer.get().isPlaying()) {
            AudioPlayer.get().pausePlayer(); //停止播放
        }

        //每次开始录音前都要先复位
        recordReset();
        RecordUtil.getInstance().startRecord(); //开始录音
        tvRecordTime.start();//计时开始
        ToastUtil.showShort("开始录音");
        btnStartRecord.setImageResource(R.drawable.icon_record_pause);
    }

    /**
     * 结束录音
     */
    public void stopRecord() {
        RecordUtil.getInstance().stopRecord();
        tvRecordTime.stop();
        btnStartRecord.setImageResource(R.drawable.icon_record_start);
    }

    /**
     * 保存录音
     */
    public void savaRecord() {
        RecordUtil.getInstance().stopRecord(); //结束录音
        tv_playTime.setVisibility(View.GONE);
        showInputDialog(this, "请输入新建录音的名字", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                EditText et = (EditText) tDialog.getView().findViewById(R.id.et_name);
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        tDialog.dismiss();
                        break;
                    case R.id.tv_confirm:
                        if (et.getText().toString().trim().equals("")) {
                            ToastUtil.showShort("名称不可为空");
                        } else {
                            presenter.addRecord(et.getText().toString().trim(), UserUtil.user().getCustomerid());
                            tDialog.dismiss();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 试听录音
     */
    public void audition() {

        if (AudioPlayer.get().isPlaying()) {
            AudioPlayer.get().pausePlayer();
        }

        AudioPlayer.get().getAudioFocusManager().requestAudioFocus(); //请求焦点

        //进行试听 ： 停止录音、录音复位、修改图标、开始播放计时
        ToastUtil.showShort("开始试听");
        isAudition = true;
        stopRecord();
        auditionReset();
        tv_playTime.setVisibility(View.VISIBLE);
        tv_playTime.start();
        if (RecordUtil.getInstance().getRecordFile() != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(RecordUtil.getInstance().getRecordFile().getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tv_playTime.stop();
                ToastUtil.showShort("试听结束！");
                isAudition = false;
            }
        });
    }

    /**
     * 录音计时器复位
     */
    @Override
    public void recordReset() {
        //计时停止
        tvRecordTime.stop();
        tvRecordTime.setBase(SystemClock.elapsedRealtime());
    }


    /**
     * 试听计时器复位
     */
    private void auditionReset() {
        tv_playTime.stop();
        tv_playTime.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * 删除当前录音
     */
    public void deleteRecord() {
        if (RecordUtil.getInstance().getRecordFile() != null) {
            RecordUtil.getInstance().getRecordFile().delete();
        }
    }

    /**
     * 显示试听、保存按钮
     */
    public void showButton() {
        tvSaveRecord.setVisibility(View.VISIBLE);
        tvAudition.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏试听、保存按钮
     */
    public void hideButton() {
        tvSaveRecord.setVisibility(View.GONE);
        tvAudition.setVisibility(View.GONE);
        //隐藏试听计时器
        tv_playTime.setVisibility(View.GONE);
        auditionReset();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
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
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "success":
                ToastUtil.showShort("录音保存成功");
                setResult(RequestCode.REQUEST_RECORD);
                finish();
                break;
            case "failure":
                ToastUtil.showShort("录音保存失败");
                setResult(RequestCode.REQUEST_RECORD);
                finish();
                break;
                default:break;
        }
    }
}
