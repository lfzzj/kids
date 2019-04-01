package com.hamitao.kids.videoplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.network.NetworkRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZUserAction;
import cn.jzvd.Jzvd;


@Route("videoplayer")
public class VideoPlayActivity extends BaseActivity {


    @BindView(R.id.videoview)
    Jzvd videoview;

    private String title;
    private String url;
    private String info; //id
    private String img;//图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
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


    private void initData() {
        info = getIntent().getStringExtra("info");
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        img = getIntent().getStringExtra("img");

        Jzvd.SAVE_PROGRESS = false;
        videoview.setUp(url,StringUtil.deleteNumber(title), Jzvd.SCREEN_WINDOW_NORMAL);
        videoview.startVideo();

        Jzvd.setJzUserAction(new JZUserAction() {
            @Override
            public void onEvent(int type, Object url, int screen, Object... objects) {
                switch (type) {
                    case JZUserAction.ON_CLICK_START_ICON:
                        LogUtil.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_START_ERROR:
                        LogUtil.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
                        LogUtil.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_PAUSE:
                        LogUtil.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_CLICK_RESUME:
                        LogUtil.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_SEEK_POSITION:
                        LogUtil.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_AUTO_COMPLETE:
                        LogUtil.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_ENTER_FULLSCREEN:
                        LogUtil.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_QUIT_FULLSCREEN:
                        LogUtil.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_ENTER_TINYSCREEN:
                        LogUtil.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_QUIT_TINYSCREEN:
                        LogUtil.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                        LogUtil.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                        LogUtil.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                        break;
                    default:
                        LogUtil.i("USER_EVENT", "unknow");
                        break;
                }
            }

        });

        //加入播放记录
        if (UserUtil.user() != null) {
            addPlayRecord(UserUtil.user().getCustomerid(), "mediaid", info, "video", StringUtil.deleteNumber(title), img, "");
        }
    }

    private void initView() {

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


    /**
     * 添加播放记录
     */

    //播放后添加到播放记录
    private void addPlayRecord(String ownid, String infotype, String info, String auxinfo, String title, String headerimgurl, String description) {

        NetworkRequest.addPlayRecordRequest(ownid, infotype, info, auxinfo, title, headerimgurl, description, ownid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                Log.d("AudipPlayer", "加入播放记录");
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                Log.d("AudioPlayer", "添加播记录失败");
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
