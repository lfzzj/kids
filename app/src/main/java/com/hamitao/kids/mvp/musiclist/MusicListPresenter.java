package com.hamitao.kids.mvp.musiclist;

import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/5/4.
 */

public class MusicListPresenter extends CommonPresenter {

    private MusicListView view;

    public MusicListPresenter(MusicListView view) {
        super(view);
        this.view = view;
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }


    /**
     * 获取音乐列表
     *
     * @param contentid
     */
    public void getMusiclist(String customerid, String contentid) {
        NetworkRequest.queryContentByIdRequest(customerid, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ContentModel.ResponseDataObjBean.class);
                view.getContent(bean.getContents().get(0));
            }


            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }






}
