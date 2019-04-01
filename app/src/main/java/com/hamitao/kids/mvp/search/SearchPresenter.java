package com.hamitao.kids.mvp.search;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.ContentModel.ResponseDataObjBean;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/3/14.
 */

public class SearchPresenter extends CommonPresenter implements BasePresenter {


    private SearchView view;

    public SearchPresenter(SearchView view) {
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
     * 搜索内容
     */
    public void searchContent( String[] keyWord) {

        NetworkRequest.searchContentRequest(keyWord, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ResponseDataObjBean.class);
                view.setSearchData(bean.getContents());
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
