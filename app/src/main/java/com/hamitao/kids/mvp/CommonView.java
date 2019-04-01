package com.hamitao.kids.mvp;

import com.hamitao.base_module.base.BaseView;

/**
 * Created by linjianwen on 2018/5/9.
 * <p>
 * 通用的view ，提供常用方法的操作， 分享、收藏、投送等等。
 */

public interface CommonView extends BaseView {


    /**
     * 刷新数据
     *
     * @param status 状态
     * @param data   数据
     * @param aux    附加信息
     */
    void refreshData(Object status, Object data, Object aux);

}
