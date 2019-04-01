package com.hamitao.kids.network;

import com.hamitao.base_module.network.NetWorkResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by linjianwen on 2018/7/11.
 */

public interface VideoServiceApi {


    //------------------------------------------视频通讯相关----------------------------------------------
//    @POST(VideoServiceApi.Api.GET_P2P)
//    Observable<NetWorkResult> getP2P(@Body RequestBody requestBody);

    @GET(VideoServiceApi.Api.GET_P2P)
    Observable<NetWorkResult> getP2P(@Query("guid") String guid, @Query("imei") String imei, @Query("wifimac") String wifimac, @Query("groupid") String groupid);


    @GET(VideoServiceApi.Api.INIT_P2P)
    Observable<NetWorkResult> initP2P(@Query("guid") String guid, @Query("token") String token);

    @GET(VideoServiceApi.Api.RELEASE_P2P)
    Observable<NetWorkResult> releaseP2P(@Query("guid") String guid, @Query("token") String token);

    class Api {

        //龙域的正式服务器，用于获取视频通讯的 捕获段或播放端 ID
        public static final String VIDEO_BASE_URL = "https://worldhttps.hubaoxing.cn";

        /*------------------------------------------视频通讯相关----------------------------------------------*/
        public static final String GET_P2P = "/user/getP2P_by_Guid.gz";  //采集端
        public static final String RELEASE_P2P = "/user/releaseP2P_by_Guid.gz"; //采集端关闭的时候必须调用，传入guid,token
        public static final String INIT_P2P = "/user/getP2P_by_token.gz"; //播放端
    }
}
