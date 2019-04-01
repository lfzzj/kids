package com.hamitao.kids.mvp.scan;

import com.hamitao.base_module.base.BaseView;
import com.hamitao.kids.model.nfccard.NfcModel;

/**
 * Created by linjianwen on 2018/4/4.
 */

public interface ScanView extends BaseView {


    /**
     * scanType : 扫描类型  0，歌单或单曲制卡扫描  1，直接扫描条码
     *
     * @param model
     */
    void bindSuccess(NfcModel.ResponseDataObjBean.NFCCardsBean model);

    /**
     * 尚未绑定任何内容
     */
    void bindNull();
}
