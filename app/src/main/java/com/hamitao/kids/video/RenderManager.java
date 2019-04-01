package com.hamitao.kids.video;

import android.content.Context;
import android.view.SurfaceView;

/**
 * @data on 2018/5/24 17:28
 * @describe:
 */

public class RenderManager {
    private Context mContext;
    private Render render;
    private String mCapID;


    public RenderManager(Context context, String capId) {
        this.mContext = context;
        this.mCapID = capId;
        init();
    }


    private void init() {
        render = new Render();
    }

    /**
     * 直播实例初始化函数
     *
     * @param account  账号
     * @param password 密码
     * @param address  P2P服务器地址
     * @return
     */
    public boolean Initialize(String account, String password, String address) {
        return render.Initialize(account, password, address, mContext);
    }

    /**
     * 设置监听
     *
     * @param onEvent
     */
    public void SetOnEvent(Render.OnEventListener onEvent) {
        render.SetOnEvent(onEvent);
    }

    public SurfaceView getPrvwSurfaceView() {
        return render.prvwSurfaceView;
    }

    public SurfaceView getLiveWnd() {
        return render.mLiveWnd;
    }

    public SurfaceView getOtherSurfaceView() {
        return render.otherSurfaceView;
    }

    public int videoHandle(String sObjV, int uErrCode, int uHandle, String sObjPeer, SurfaceView peerView, boolean bPrvw) {
        return render.extVideo.videoHandle(sObjV, uErrCode, uHandle, sObjPeer, peerView, bPrvw);
    }

    public void Connect(String sID, boolean isVoice) {
        render.m_Live.Connect(sID);

        if (!isVoice)
            render.m_Live.VideoStart(sID, 0, "", render.mLiveWnd);
        render.m_Live.AudioStart(sID, 0, "");
    }

    public int videoStart() {
        return render.extVideo.videoStart(render.GetLive().GetSelfPeer(), render.otherSurfaceView, true);
    }

    public int start(String sObjPeer) {
        return render.extVideo.start(sObjPeer);
    }

    public int sendGetObjPeerNotify(String sObjPeer, String sParam) {
        return render.extVideo.sendGetObjPeerNotify(sObjPeer, sParam);
    }

    public int sendGetObjPeer(String msCapID, String s) {
        return render.extVideo.sendGetObjPeer(msCapID, "1");
    }

    public void liveStop(String msCapID) {
        render.m_Live.AudioStop(msCapID, 0);
        render.m_Live.VideoStop(msCapID, 0);
        render.m_Live.Disconnect(msCapID);
        render.SetOnEvent(null);
        render.Clean();
    }

}
