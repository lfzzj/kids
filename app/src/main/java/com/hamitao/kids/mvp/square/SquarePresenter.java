package com.hamitao.kids.mvp.square;

import com.hamitao.kids.model.ForumModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/1/19.
 */

public class SquarePresenter extends CommonPresenter implements BasePresenter {

    private SquareView view;


    public SquarePresenter(SquareView view) {
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
     * 查询广场
     */
    public void getListRequest(final String customerid, final String max, final int requestType) {
//        NetworkRequest.querySquareRequest(new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
//            @Override
//            public void onSuccess(NetWorkResult result) {
//                if (view == null) {
//                    return;
//                }
//                SquareModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), SquareModel.ResponseDataObjBean.class);
//                //查询论坛
//                queryForum(bean.getForums().get(0).getForum_id(), customerid, max, requestType);
//            }
//
//
//            @Override
//            public void onFail(NetWorkResult result, String msg) {
//                if (view == null) {
//                    return;
//                }
//                view.onMessageShow(msg);
//            }
//
//            @Override
//            public void onBegin() {
//                if (view == null) {
//                    return;
//                }
////                view.onBegin();
//            }
//
//            @Override
//            public void onEnd() {
//                if (view == null) {
//                    return;
//                }
//                view.onFinish();
//            }
//        }));

        //当前版本只有一个论坛，故作写死处理
        queryForum(Constant.OFFICIAL_FORUM, customerid, max, requestType);
    }


    /**
     * 查询论坛列表
     *
     * @param forumId
     * @param max     //每次加载的数据条数
     */
    public void queryForum(String forumId, String customerid, String max, final int requestType) {
        NetworkRequest.queryForumRequest(forumId, customerid, max, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ForumModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ForumModel.ResponseDataObjBean.class);
                view.getList(bean, requestType);
            }


            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onError();
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
}
