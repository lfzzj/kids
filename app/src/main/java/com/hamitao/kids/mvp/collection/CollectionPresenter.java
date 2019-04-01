package com.hamitao.kids.mvp.collection;

import com.hamitao.kids.model.ClipModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;

/**
 * Created by linjianwen on 2018/5/8.
 */

public class CollectionPresenter extends CommonPresenter {


    private CollectionView view;

    public CollectionPresenter(CollectionView view) {
        super(view);
        this.view = view;
    }


    /**
     * 查询我的收藏夹
     */
    public void querMyClip(String customerId) {

        NetworkRequest.queryMyClipRequest(customerId, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                ClipModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ClipModel.ResponseDataObjBean.class);

                view.getList(bean.getFavoriteCategorys());
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


    /**
     * 收藏夹改名
     */
    public void renameClip(String customerid, String oldcategory, final String newcategory, final int position) {
        NetworkRequest.renameClipRequest(customerid, oldcategory, newcategory, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshData("renameClip", newcategory, position);
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


    /**
     * 删除收藏夹
     */
    public void deleteClip(String customerId, final String category, final int position) {

        NetworkRequest.deleteClipRequest(customerId, category, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
//                view.onMessageShow("已经删除 " + category);


                view.refreshData("delete", position, null);
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


}
