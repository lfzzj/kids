package com.hamitao.kids.video;

import android.content.Context;
import android.view.SurfaceView;

import com.hamitao.base_module.util.LogUtil;
import com.peergine.android.livemulti.pgLibLiveMultiRender;
import com.peergine.android.livemulti.pgLibLiveMultiView;
import com.peergine.extvideo.lib.ExtVideo;
import com.peergine.extvideo.lib.OnEvent;
import com.peergine.pgLibView;

import static com.peergine.pgLibError.PG_ERR_Normal;

/**
 * 播放端
 */

public class Render {

    public SurfaceView prvwSurfaceView;

    public SurfaceView otherSurfaceView;

    public SurfaceView mExtVideoPrvw;
    public SurfaceView mLiveWnd;

    public interface OnEventListener {

        void event(String sAct, String sData, String sRender);

        void onGetObjPeer(String sObjPeer, String sParam);

        void onGetObjPeerReply(int uErr, String sParam);

        void onGetObjPeerNotify(String sObjPeer, String sParam);

        int onVideoSync(String sObj, String sAct, String sObjPeer);

        int onVideoStart(String sObj, int uHandle, String sPeer);

        int onVideoStartReply(String sObj, int uErr);

        int onVideoStop(String sObj, String sObjPeer);

        void onVideoFrameStat(String sObj, String sData);

        int onAudioSync(String sObj, String sAct);

        int onAudioStart(String sObj, String sPeer);

        int onAudioStartRelay(String sObj, int uErr);

        int onAudioStop(String sPeer);
    }

    Render() {

    }

    public pgLibLiveMultiRender m_Live = new pgLibLiveMultiRender();
    public ExtVideo extVideo;
    private OnEventListener m_event = null;

    public pgLibLiveMultiRender GetLive() {
        return m_Live;
    }

    public void SetOnEvent(OnEventListener event) {
        m_event = event;
    }


    public boolean Initialize(String account, String password, String address, Context context) {

        extVideo = new ExtVideo();

        extVideo.setOnEvent(ext_event);

        m_Live.SetEventListener(m_OnEvent);

        m_Live.SetNodeEventHook((pgLibLiveMultiRender.NodeEventHook) extVideo.getHook());

        if (m_Live.Initialize(account, password, address, "", 1, "(MaxStream){0}", context) != PG_ERR_Normal) {
            LogUtil.d("pgLiveRander", "LiveStart: Live.Initialize failed!");
            return false;
        }

        //旋转了采集方向(Portrait){1}
//        String sExtVideoParam = "(Code){3}(Mode){2}(Rate){66}(BitRate){100}(Portrait){1}";
        String sExtVideoParam = "(Code){3}(Mode){2}(Rate){66}(BitRate){300}(Portrait){1}";

        m_Live.VideoShowMode(1);

        extVideo.initialize(m_Live.GetNode(), m_Live.GetSelfPeer(), sExtVideoParam, "");

        mLiveWnd = pgLibLiveMultiView.Get("v0");

        prvwSurfaceView = extVideo.getPrewview(320, 240);

        otherSurfaceView = pgLibView.Get("v1");

        return true;
    }

    public void Clean() {

        if (otherSurfaceView != null) {

            pgLibView.Release(otherSurfaceView);
        }
        if (extVideo != null) {

            extVideo.delPrewview();

            extVideo.clean();
        }

        if (m_Live != null) {
//            m_Live.Clean();
            m_Live = null;
        }

    }


    private OnEvent ext_event = new OnEvent() {
        @Override
        public void onGetObjPeer(String sObjPeer, String sParam) {
            if (m_event != null) {
                m_event.onGetObjPeer(sObjPeer, sParam);
            }

        }

        @Override
        public void onGetObjPeerReply(int uErr, String sParam) {

            if (m_event != null) {
                m_event.onGetObjPeerReply(uErr, sParam);
            }

        }

        @Override
        public void onGetObjPeerNotify(String sObjPeer, String sParam) {
            if (m_event != null) {
                m_event.onGetObjPeerNotify(sObjPeer, sParam);
            }
        }

        @Override
        public int onVideoSync(String sObj, String sAct, String sObjPeer) {
            if (m_event != null) {
                return m_event.onVideoSync(sObj, sAct, sObjPeer);
            }
            return 0;
        }

        @Override
        public int onVideoStart(String sObj, int uHandle, String sObjPeer) {
            if (m_event != null) {
                return m_event.onVideoStart(sObj, uHandle, sObjPeer);
            }
            return 0;
        }

        @Override
        public int onVideoStartReply(String sObj, int uErr) {
            if (m_event != null) {
                return m_event.onVideoStartReply(sObj, uErr);
            }
            return 0;
        }

        @Override
        public int onVideoStop(String sObj, String sObjPeer) {

            if (m_event != null) {
                return m_event.onVideoStop(sObj, sObjPeer);
            }

            return 0;
        }

        @Override
        public void onVideoFrameStat(String sObj, String sData) {
            if (m_event != null) {
                m_event.onVideoFrameStat(sObj, sData);
            }
        }

        @Override
        public int onAudioSync(String sObj, String sAct) {
            if (m_event != null) {
                return m_event.onAudioSync(sObj, sAct);
            }
            return 0;
        }

        @Override
        public int onAudioStart(String sObj, String sObjPeer) {

            if (m_event != null) {
                return m_event.onAudioStart(sObj, sObjPeer);
            }
            return 0;
        }

        @Override
        public int onAudioStartRelay(String sObj, int uErr) {
            if (m_event != null) {
                return m_event.onAudioStartRelay(sObj, uErr);
            }

            return 0;
        }

        @Override
        public int onAudioStop(String sObjPeer) {
            if (m_event != null) {
                return m_event.onAudioStop(sObjPeer);
            }
            return 0;
        }

    };


    private pgLibLiveMultiRender.OnEventListener m_OnEvent = new pgLibLiveMultiRender.OnEventListener() {

        @Override
        public void event(String sAct, String sData, String sRender) {
            // TODO Auto-generated method stub
            if (m_event != null) {
                m_event.event(sAct, sData, sRender);
            }

        }
    };

}
