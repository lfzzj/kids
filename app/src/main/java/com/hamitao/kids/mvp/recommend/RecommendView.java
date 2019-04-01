package com.hamitao.kids.mvp.recommend;

import com.hamitao.base_module.base.BaseView;
import com.hamitao.kids.model.RecommendContentModel;

import java.util.List;

/**
 * Created by linjianwen on 2018/1/17.
 */

public interface RecommendView extends BaseView {

    /**
     * 分层数据
     *
     * @param bean
     * @param position
     */
    void setLayerData(List<RecommendContentModel.ResponseDataObjBean.ContentTreeNodesBean> bean, int position);


    /**
     * 普通内容数据
     *
     * @param bean
     */
    void setContentData(List<RecommendContentModel.ResponseDataObjBean.ContentsBean> bean);


    /**
     * banner数据
     *
     * @param bean
     */
    void setBannerData(List<RecommendContentModel.ResponseDataObjBean.ContentsBean> bean);


    /**
     * 当数据请求完成后设置数据
     */
    void setData();


    /**
     * 请求失败
     */
    void onError(String cause);
}
