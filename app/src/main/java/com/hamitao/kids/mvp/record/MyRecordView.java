package com.hamitao.kids.mvp.record;

import com.hamitao.kids.model.RecordModel.ResponseDataObjBean.VoiceRecordingsBean;
import com.hamitao.kids.mvp.CommonView;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/18.
 */

public interface MyRecordView extends CommonView {

    void getMyRecordList(List<VoiceRecordingsBean> voiceRecordings);

    void deliverSuccess();
}
