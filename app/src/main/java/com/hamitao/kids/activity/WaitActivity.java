package com.hamitao.kids.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.PushMessageModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.service.MyMessageReceiver;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频等待页面，来电与去电
 */

@Route("wait")
public class WaitActivity extends BaseActivity {

    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_accept)
    TextView tvAccept;

    private String guid;
    private String token;

    private String channelid;

    private boolean isReceiveMsg = false;

    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;
    private Timer timer;

    //接受收到的推送消息
    private MyMessageReceiver myMessageReceiver = new MyMessageReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String action = intent.getAction();
            switch (action) {
                case "GET_PUSHMESSAGE":
                    isReceiveMsg = true;
                    //接收广播传过来的guid和token
                    String message = intent.getStringExtra("message");//json数据，需要解析
                    PushMessageModel messageModel = GsonUtil.GsonToBean(message, PushMessageModel.class);
                    if (messageModel.getResponseDataObj() != null) {
                        //这里对Content中的Json进行解析
                        VideoInfoModel model = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), VideoInfoModel.class);
                        guid = model.getGuid();
                        token = model.getToken();
                    }

                    finish();
                    Router.build("p2p_video").go(WaitActivity.this);
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myMessageReceiver);

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initView() {
        //判断是来电还是去电
        if (getIntent().getStringExtra("status").equals("go")) {
            tvRefuse.setVisibility(View.VISIBLE);
            tvAccept.setVisibility(View.GONE);
        } else {
            tvRefuse.setVisibility(View.GONE);
            tvAccept.setVisibility(View.VISIBLE);
        }
    }


    private void initData() {
        timer = new Timer();

        //一分钟未接收到推送消息直接挂断处理
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isReceiveMsg) {
                    ToastUtil.showShort("暂时无法接通");
                    finish();
                }
            }
        }, 10000);


        //调用铃声
        mediaPlayer = new MediaPlayer();
        try {
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("ring.aac");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            mediaPlayer.setLooping(true); //设置循环
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        channelid = getIntent().getStringExtra("channelid");

        NetworkRequest.pushMessageRequest(channelid, "VIDEO_CHAT", null, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {
                ToastUtil.showShort("请耐性等待");
            }
        }));

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("GET_PUSHMESSAGE");
        registerReceiver(myMessageReceiver, filter);
    }


    @OnClick({R.id.tv_refuse, R.id.tv_accept})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_refuse:
                finish();
                break;
            case R.id.tv_accept:
                Router.build("p2p_video").go(WaitActivity.this);

//                //这里进行网络请求
//                NetworkRequest.initP2PRequest(guid, token, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
//                    @Override
//                    public void onSuccess(NetWorkResult result) {
//                        //请求成后进入视频页面
//                        Router.build("p2p_video").go(WaitActivity.this);
//                    }
//
//                    @Override
//                    public void onFail(NetWorkResult result, String msg) {
//
//                    }
//
//                    @Override
//                    public void onBegin() {
//
//                    }
//
//                    @Override
//                    public void onEnd() {
//
//                    }
//                }));
                break;
        }
    }

    public class VideoInfoModel {

        String guid;
        String token;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
