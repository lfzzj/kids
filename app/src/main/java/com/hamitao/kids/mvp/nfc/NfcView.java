package com.hamitao.kids.mvp.nfc;

import com.hamitao.kids.model.nfccard.NfcModel.ResponseDataObjBean.NFCCardsBean;
import com.hamitao.base_module.base.BaseView;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/28.
 */

public interface NfcView extends BaseView {


    void setList(List<NFCCardsBean> NFCCards);

    void deldteNfc(int position);



}
