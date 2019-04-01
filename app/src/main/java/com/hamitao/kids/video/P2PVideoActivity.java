package com.hamitao.kids.video;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.PushParams;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.model.PushMessageModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.service.MyMessageReceiver;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.VideoInfoModel;
import com.hamitao.kids.model.VideoParamsModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.network.VideoServiceApi;
import com.hamitao.kids.player.AudioPlayer;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.eventbus.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.peergine.pgLibError.PG_ERR_Normal;

/**
 * 播放端
 */

@Route("p2p_video")
public class P2PVideoActivity extends BaseActivity {


    private String TAG = "视频聊天";

    @BindView(R.id.iv_hang_up)
    TextView ivHangUp;

    @BindView(R.id.layout_video)
    LinearLayout layoutVideo;

    @BindView(R.id.layout_prvw)
    LinearLayout layoutPrvw;

    @BindView(R.id.tv_refuse)
    TextView refuse;

    @BindView(R.id.tv_accept)
    TextView accept;

    @BindView(R.id.rl_wait)
    RelativeLayout rl_wait;

    @BindView(R.id.ll_timer)
    LinearLayout ll_timer;

    @BindView(R.id.chronometer)
    Chronometer mChronometer;

    @BindView(R.id.rl_bg)
    RelativeLayout rl_bg;

    @BindView(R.id.tv_name)
    TextView tv_name; //机器人名称
    @BindView(R.id.tv_tips)
    TextView tv_tips;//呼叫时的提示

    //播放本地音频文件
    private MediaPlayer mediaPlayer;
    //读取本地的音频文件
    private AssetManager assetManager;
    private Timer timer;
    //播放端管理器
    private RenderManager manager;
    private boolean isConnected = false;
    //机器人通讯的状态
    private String status;
    //用于向P2P服务器请求账号密码进行视频，来自机器人端
    private String guid;
    private String token;
    private String channelid = "";
    private String capId = "";
    private String sVideoPeer = "";
    private String deviceName;

    /**
     * 控制挂断按钮的显示隐藏
     */
    private Handler timerHandler;
    private Runnable timerRunnable;

    /**
     * 是否来自机器人的呼叫
     */
    private boolean isFromDevice = false;


    //接受收到的推送消息
    private MyMessageReceiver myMessageReceiver = new MyMessageReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String action = intent.getAction();
            String message = intent.getStringExtra("message");
            PushMessageModel messageModel = GsonUtil.GsonToBean(message, PushMessageModel.class);

            switch (action) {
                case PushParams.PHONE_VIDEO_DEVICE:
                    status = action;
                    //获取收到的推送消息
                    LogUtil.d(TAG, "手机呼叫机器人");
                    if (messageModel.getResponseDataObj() != null) {
                        VideoInfoModel model = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), VideoInfoModel.class);
                        getVideo(model.getGuid(), model.getToken(), PushParams.PHONE_VIDEO_DEVICE);
                        stopMusic(); //初始化时停止播放器
                    }
                    break;
                case PushParams.PHONE_VOICE_DEVICE:
                    status = action;
                    if (messageModel.getResponseDataObj() != null) {
                        VideoInfoModel model = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), VideoInfoModel.class);
                        getVideo(model.getGuid(), model.getToken(), PushParams.PHONE_VOICE_DEVICE);
                        stopMusic();   //初始化时停止播放器
                    }
                    break;
                case PushParams.DEVICE_VIDEO_PHONE:
                    break;
                case PushParams.PHONE_MONITOR_DEVICE:
                    status = action;
                    //监控
                    if (messageModel.getResponseDataObj() != null) {
                        VideoInfoModel model = GsonUtil.GsonToBean(messageModel.getResponseDataObj().getChannelMsg().getActionResult().getContents(), VideoInfoModel.class);
                        getVideo(model.getGuid(), model.getToken(), PushParams.PHONE_MONITOR_DEVICE);
                        stopMusic();   //初始化时停止播放器
                    }
                    break;
                case PushParams.HANG_UP:
                    //收到对方的挂断消息
                    //是否由机器人发起的通话
                    if (!isFromDevice) {
                        EventBus.getDefault().post(isConnected ? new VideoChatMessageModel(RxBusEvent.CHAT_MESSAGE_DURATION, mChronometer.getText().toString()) : new VideoChatMessageModel(RxBusEvent.CHAT_MESSAGE_REFUSE, null));
                    }
                    ToastUtil.showShort("对方已挂断");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000); // 对方挂断后两秒钟退出界面
                    break;
                case PushParams.LINE_BUSY:
                    EventBus.getDefault().post(new VideoChatMessageModel(RxBusEvent.CHAT_LINE_BUSY, null));
                    ToastUtil.showShort("对方忙");
                    exitP2P();
                    break;
                default:
                    break;
            }
        }
    };


    private void exitP2P(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000); // 对方挂断后两秒钟退出界面
    }


    //播放铃声
    private void playMusic() {
        try {
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("ring.aac");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            //设置循环
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //停止铃声
    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onDestroy() {
        //注销广播
        unregisterReceiver(myMessageReceiver);
        //清空视频阅览
        layoutVideo.removeAllViews();
        layoutPrvw.removeAllViews();

        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }

        if (manager != null) {
            //断开视频连接
            manager.liveStop(capId);

        }
        if (mediaPlayer != null) {
            //释放播放器
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timerRunnable != null)
        timerHandler.removeCallbacks(timerRunnable);
        super.onDestroy();
    }


    private void initData() {

        if (AudioPlayer.get().isPlaying()) {
            AudioPlayer.get().pausePlayer();
        }

        //是否来自专辑的呼叫
        isFromDevice = getIntent().getBooleanExtra("fomedevice", false);

        //呼叫一分钟未接听自动挂断退出
        timer = new Timer();
        mediaPlayer = new MediaPlayer();

        status = getIntent().getStringExtra("status");
        channelid = getIntent().getStringExtra("channelid");

        if (isFromDevice) {

            //当机器人找APP视频通话，遍历绑定的机器人，获取机器人名字
            List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> terminalInfos = GsonUtil.GsonToList(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, ""),
                    DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);

            for (int i = 0; i < terminalInfos.size(); i++) {
                if (terminalInfos.get(i).getChannelid_listen_on_pushserver().equals(channelid)) {
                    deviceName = terminalInfos.get(i).getBindname();
                }
            }
//            tv_tips.setText(deviceName + "正在呼叫你...");
            tv_tips.setText("正在邀请你视频通话...");
        } else {
            deviceName = getIntent().getStringExtra("name");
//            tv_tips.setText("正在呼叫" + deviceName + "...");
            tv_tips.setText("等待对方接受邀请...");
        }

        tv_name.setText(deviceName);

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(PushParams.DEVICE_VIDEO_PHONE);
        filter.addAction(PushParams.DEVICE_VOICE_PHONE);
        filter.addAction(PushParams.PHONE_VIDEO_DEVICE);
        filter.addAction(PushParams.PHONE_VOICE_DEVICE);
        filter.addAction(PushParams.PHONE_MONITOR_DEVICE);
        filter.addAction(PushParams.HANG_UP);
        filter.addAction(PushParams.LINE_BUSY);
        registerReceiver(myMessageReceiver, filter);

        //当机器人呼叫手机
        guid = getIntent().getStringExtra("guid");
        token = getIntent().getStringExtra("token");
        if (guid != null && !guid.equals("")) {
            LogUtil.d(TAG, "机器人呼叫手机");
            accept.setVisibility(View.VISIBLE);
        }
        playMusic();//进入页面就开始播放音乐

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isConnected) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShort("暂时无法接通");
                        }
                    });
                    pushMessage(channelid, PushParams.HANG_UP, null);
                    finish();
                }
            }
        }, 60000); //60秒钟未接听就挂断

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (ll_timer.isShown())
                    ll_timer.setVisibility(View.GONE);
            }
        };
    }


    /**
     * 初始化视频
     *
     * @param username 采集端ID
     * @param password
     * @param address  服务器地址
     * @param status   状态
     */
    public void initVideo(String username, String password, String address, String status) {
        rl_wait.setVisibility(View.GONE);
        checkCamera(); //检查摄像头权限
        capId = username;
        manager = new RenderManager(this, capId);
        if (!manager.Initialize(capId, password, address)) {
            Log.d(TAG, "初始化失败");
            ToastUtil.showShort("初始化失败");
            pushMessage(channelid, PushParams.HANG_UP, null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000); // 对方挂断后1秒钟退出界面
            return;
        }
        manager.SetOnEvent(mOnEvent);
        layoutPrvw.removeAllViews();
        layoutVideo.removeAllViews();
        if (status.equals(PushParams.PHONE_VOICE_DEVICE)) {
            //语音聊天
            layoutPrvw.setVisibility(View.GONE);
            rl_wait.setVisibility(View.VISIBLE);
            refuse.setVisibility(View.GONE);
            ll_timer.setVisibility(View.VISIBLE);//计时器
            mChronometer.setTextColor(ContextCompat.getColor(this, R.color.text_default_d));
            tv_tips.setText("通话中...");
            manager.Connect(capId, true);

        } else if (status.equals(PushParams.PHONE_MONITOR_DEVICE)) {
            //监控
            ll_timer.setVisibility(View.VISIBLE);//计时器
            layoutVideo.addView(manager.getLiveWnd());
            manager.Connect(capId, false);
            hindBtn();
        } else {
            //视频通话
            ll_timer.setVisibility(View.VISIBLE);//计时器
            layoutPrvw.setVisibility(View.VISIBLE);
            layoutPrvw.addView(manager.getPrvwSurfaceView());
            layoutVideo.addView(manager.getLiveWnd());
            manager.getPrvwSurfaceView().setZOrderOnTop(true);
            manager.Connect(capId, false);
            hindBtn();
        }

        isConnected = true;


    }


    /**
     * 检查摄像头权限
     */
    private void checkCamera() {
        if (Checker.CameraCheck(this)) {
            LogUtil.d(TAG, "打开摄像头成功");
        } else {
            ToastUtil.showShort("打开摄像头失败。请检查摄像头权限");
        }
        if (Checker.RecordAudioCheck()) {
            LogUtil.d(TAG, "打开录音机器人成功");
        } else {
            ToastUtil.showShort("打开录音机器人失败。请检查录音机器人权限");
        }
    }


    /**
     * 事件处理，仅仅用于统计，不做逻辑处理
     */
    private Render.OnEventListener mOnEvent = new Render.OnEventListener() {
        @Override
        public void event(String sAct, String sData, String sRender) {
            // 事件 请不要启动阻塞，返回-1 为异步。
            if ("VideoStatus".equals(sAct)) {
                // Video status report
            } else if ("Notify".equals(sAct)) {
                // Receive the notify from capture side
                String sInfo = "Receive notify: data=" + sData;
                Log.d(TAG, sInfo);
            } else if ("RenderJoin".equals(sAct)) {
                // A render join
                String sInfo = "Render join: render=" + sRender;
                Log.d(TAG, sInfo);
            } else if ("RenderLeave".equals(sAct)) {
                // A render leave
                String sInfo = "Render leave: render=" + sRender;
                // Render 和采集端断开连接。
                Log.d(TAG, sInfo);
                if (("_RND_" + sRender).equals(sVideoPeer)) {
                    //prvwVideoStop();
                    //非正常退出重启
                }
            } else if ("Message".equals(sAct)) {
                // Receive the message from render or capture

                String sInfo = "Receive msg: data=" + sData + ", render=" + sRender;
                Log.d(TAG, sInfo);
                ;
            } else if ("Login".equals(sAct)) {
                // Login reply
                if ("0".equals(sData)) {
                    String sInfo = "Login success";
                    Log.d(TAG, sInfo);
                } else {
                    String sInfo = "Login failed, error=" + sData;
                    Log.d(TAG, sInfo);
                }
            } else if ("Logout".equals(sAct)) {
                // Logout
                String sInfo = "Logout";
                Log.d(TAG, sInfo);
            } else if ("Connect".equals(sAct)) {
                // Connect to capture

                String sInfo = "Connect to capture";

                Log.d(TAG, sInfo);
                int iErr = manager.sendGetObjPeer(capId, "1");
                if (iErr > PG_ERR_Normal) {
                    Log.d(TAG, "sendGetObjPeerNotify : iErr = " + iErr);
                }
            } else if ("Disconnect".equals(sAct)) {
                // Disconnect from capture
                String sInfo = "Diconnect from capture";
                exitP2P();
                Log.d(TAG, sInfo);
            } else if ("Offline".equals(sAct)) {
                // The capture is offline.
                String sInfo = "Capture offline";
//                exitP2P();
                Log.d(TAG, sInfo);
            } else if ("LanScanResult".equals(sAct)) {
                // Lan scan result.
                String sInfo = "Lan scan result: " + sData;
                Log.d(TAG, sInfo);
            } else if ("ForwardAllocReply".equals(sAct)) {
                String sInfo = "Forward alloc relpy: error=" + sData;
                Log.d(TAG, sInfo);
            } else if ("ForwardFreeReply".equals(sAct)) {
                String sInfo = "Forward free relpy: error=" + sData;
                Log.d(TAG, sInfo);
            } else if ("VideoCamera".equals(sAct)) {
                String sInfo = "The picture is save to: " + sData;
                Log.d(TAG, sInfo);
            } else if ("SvrNotify".equals(sAct)) {
                String sInfo = "Receive server notify: " + sData;
                Log.d(TAG, sInfo);
            }

            Log.d("pgLiveCapExter", "OnEvent: Act=" + sAct + ", Data=" + sData + ", Render=" + sRender);
        }

        @Override
        public void onGetObjPeer(String sObjPeer, String sParam) {
            sVideoPeer = sObjPeer;

            int iErr = manager.start(sObjPeer);
            if (iErr > PG_ERR_Normal) {
                Log.d(TAG, "extVideo.Connect : iErr = " + iErr);
            }
            iErr = manager.sendGetObjPeerNotify(sObjPeer, sParam);
            if (iErr > PG_ERR_Normal) {
                Log.d(TAG, "extVideo.Connect : iErr = " + iErr);
            }
        }

        @Override
        public void onGetObjPeerReply(int uErr, String sParam) {
            if (uErr > PG_ERR_Normal) {
                Log.d(TAG, "onGetObjPeerReply uErr = " + uErr + " sParam = " + sParam);
            }else {
                //复位计时器，停止计时
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start(); // 开始计时
                Log.d(TAG, "视频打开成功");
            }
        }

        @Override
        public void onGetObjPeerNotify(String sObjPeer, String sParam) {
            Log.d("EXT_VIDEO", "onGetObjPeerReply uErr = " + sObjPeer + " sParam = " + sParam);
            sVideoPeer = sObjPeer;

            int iErr = manager.start(sObjPeer);
            if (iErr > PG_ERR_Normal) {
                Log.d(TAG, "extVideo.start : iErr = " + iErr);
            }
        }

        @Override
        public int onVideoSync(String sObj, String sAct, String sObjPeer) {
            Log.d("EXT_VIDEO", "onVideoSync sPeer = " + sObj + " sParam = " + sObjPeer);
            if ("0".equals(sAct)) {
                //msObjPeer = "";
                //extVideo.stop();
            } else if ("1".equals(sAct)) {
                String sPeer = sObj.substring(10);
                if (capId.equals(sPeer)) {
                    int iErr = manager.videoStart();
                    if (iErr > PG_ERR_Normal) {
                        Log.d(TAG, "videoStart : iErr = " + iErr);
                    }
                }
            }
            return 0;
        }

        @Override
        public int onVideoStart(String sObj, int uHandle, String sPeer) {
            Log.d("EXT_VIDEO", "onVideoStart sPeer = " + sObj + " sPeer = " + sPeer);
            int iErr = manager.videoHandle(sObj, PG_ERR_Normal, uHandle, sPeer, manager.getOtherSurfaceView(), true);
            if (iErr > PG_ERR_Normal) {
                Log.d(TAG, "extVideo.start : iErr = " + iErr);
            }
            return 0;
        }

        @Override
        public int onVideoStartReply(String sObj, int uErr) {
            if (uErr > PG_ERR_Normal) {
                Log.d(TAG, "onVideoStartReply sPeer = " + sObj + " uErr = " + uErr);
            }
            return 0;
        }

        @Override
        public int onVideoStop(String sObj, String sObjPeer) {
            Log.d("EXT_VIDEO", "onVideoStop sPeer = " + sObj + " sParam = " + sObjPeer);
            return 0;
        }

        @Override
        public void onVideoFrameStat(String sObj, String sData) {
            Log.d("EXT_VIDEO", "onVideoFrameStat sObj = " + sObj + " sData = " + sData);

        }

        @Override
        public int onAudioSync(String sObj, String sAct) {
            Log.d("EXT_VIDEO", "onAudioSync sPeer = " + sObj + " sParam = " + sAct);
            return 0;
        }

        @Override
        public int onAudioStart(String sObj, String sPeer) {
            Log.d("EXT_VIDEO", "onAudioStart sPeer = " + sObj + " sPeer = " + sPeer);
            return -1;
        }

        @Override
        public int onAudioStartRelay(String sObj, int uErr) {
            Log.d("EXT_VIDEO", "onAudioStartRelay sPeer = " + sObj + " sParam = " + uErr);
            return 0;
        }

        @Override
        public int onAudioStop(String sPeer) {
            Log.d("EXT_VIDEO", "onAudioStop sPeer = " + sPeer);
            return 0;
        }
    };

    @OnClick({R.id.tv_refuse, R.id.tv_accept, R.id.iv_hang_up, R.id.layout_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refuse:
                view.setClickable(false);
                //还未进入视频通话时的挂断
                pushMessage(channelid, PushParams.HANG_UP, null);
                if (!isFromDevice) {
                    if (!status.equals(PushParams.PHONE_MONITOR_DEVICE)) {
                        EventBus.getDefault().post(new VideoChatMessageModel(RxBusEvent.CHAT_MESSAGE_CANCEL, ""));
                    }
                }
                //关闭音乐，进行网络请求
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);  //结束后1秒关闭页面
                break;
            case R.id.tv_accept:
                //关闭音乐，进行网络请求
                stopMusic();
                //计时器开始计时
                getVideo(guid, token, status);
                break;
            case R.id.iv_hang_up:
                //视频通话时的挂断
                ToastUtil.showShort("视频结束，通话时长：" + mChronometer.getText().toString());
                if (!isFromDevice) {
                    if (!status.equals(PushParams.PHONE_MONITOR_DEVICE)) {
                        //监控的时候不发送消息
                        EventBus.getDefault().post(new VideoChatMessageModel(RxBusEvent.CHAT_MESSAGE_DURATION, mChronometer.getText().toString()));
                    }

                }

                pushMessage(channelid, PushParams.HANG_UP, null);
                finish();  //视频钟通话挂断
                break;
            case R.id.layout_video:

                if (status.equals(PushParams.PHONE_VOICE_DEVICE)) return;

                if (ll_timer.isShown()) {
                    ll_timer.setVisibility(View.GONE);
                    timerHandler.removeCallbacks(timerRunnable);
                } else {
                    hindBtn();  //显示后，开始几时，5秒钟关闭
                }
                break;
            default:
                break;
        }
    }


    /**
     * 禁止侧滑删除
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    private void hindBtn() {
        ll_timer.setVisibility(View.VISIBLE);
        timerHandler.postDelayed(timerRunnable, 5000);
    }


    /**
     * 请求视频
     *
     * @param guid
     * @param token
     * @param status
     */
    public void getVideo(final String guid, final String token, final String status) {
        String url = VideoServiceApi.Api.VIDEO_BASE_URL + VideoServiceApi.Api.INIT_P2P + "?guid=" + guid + "&token=" + token;

        Observable.create(new ObservableOnSubscribe<VideoParamsModel>() {
            @Override
            public void subscribe(final ObservableEmitter<VideoParamsModel> e) throws Exception {

                Request request = new Request.Builder()
                        .url(url)//声明网站访问的网址
                        .build();//创建Request

                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        VideoParamsModel model = GsonUtil.GsonToBean(str, VideoParamsModel.class);
                        if (model != null) {
                            e.onNext(model);
                        }
                    }
                });

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<VideoParamsModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(VideoParamsModel model) {
                //初始化视频
                initVideo(model.getP2p_id(), model.getPasswd(), model.getAddress(), status);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort("请求失败");
            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 推送消息
     *
     * @param targetChannelid
     * @param actionType
     * @param contentid
     */
    public void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
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

            }
        }));
    }


}
