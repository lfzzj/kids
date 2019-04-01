package com.hamitao.kids.mvp.CollectContent;

import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/5/9.
 */

public class CollectContentPresenter extends CommonPresenter implements BasePresenter {

    private CollectContentView view;

    public CollectContentPresenter(CollectContentView view) {
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
     * 查询收藏夹中的内容
     */
    public void queryCollection(String costomerid, String clipName) {
        NetworkRequest.queryMyCollectionRequest(costomerid, clipName, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {

            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                CollectContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CollectContentModel.ResponseDataObjBean.class);
                view.getList(bean.getFavorites());

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }

//
//    /**
//     * 通过ID查音乐
//     */
//    public void queryMediaById(String customerid, String id) {
//        NetworkRequest.queryMediaByIdRequest(customerid, id, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
//            @Override
//            public void onSuccess(NetWorkResult result) {
//                MediaModel.ResponseDataObjBean model = GsonUtil.GsonToBean(result.getResponseDataObj(), MediaModel.ResponseDataObjBean.class);
//
//                //查询成功后播放音乐
//
//                PlayerUtil.getInstance().playMusic(model.getContents().get(0).getMediaList().get(0).getHttpURL());
//            }
//
//            @Override
//            public void onFail(NetWorkResult result, String msg) {
//
//            }
//
//            @Override
//            public void onBegin() {
//
//            }
//
//            @Override
//            public void onEnd() {
//
//            }
//        }));
//    }


}
