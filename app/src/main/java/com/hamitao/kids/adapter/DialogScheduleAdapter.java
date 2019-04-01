package com.hamitao.kids.adapter;

import android.support.v7.widget.RecyclerView;

import com.hamitao.kids.model.DialogScheduleModel;
import com.hamitao.kids.model.scheduler.QueryPublishScheduleModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.base_module.R;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.UserUtil;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * Created by linjianwen on 2018/6/28.
 */

public class DialogScheduleAdapter extends BGARecyclerViewAdapter<DialogScheduleModel> {


    public DialogScheduleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_list_img_text_switch);
    }

    @Override
    protected void fillData(final BGAViewHolderHelper helper, final int position, DialogScheduleModel model) {

        helper.getTextView(R.id.tv_text).setText(model.getNickname());
        //此处做一次网络请求，判断课表是否开启
        NetworkRequest.queryPublishScheduleRequest(model.getId(), new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                QueryPublishScheduleModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), QueryPublishScheduleModel.ResponseDataObjBean.class);

                for (int i = 0; i < bean.getCourseScheduleList().size(); i++) {
                    //有当前用户发布的课程表
                    if (bean.getCourseScheduleList().get(i).getAuthorid().equals(UserUtil.user().getCustomerid())) {
                        helper.getImageView(R.id.iv_switch).setImageResource(R.drawable.icon_switch_p);
                        getData().get(position).setOpen(true);
                        return;
                    } else {
                        helper.getImageView(R.id.iv_switch).setImageResource(R.drawable.icon_switch_n);
                        getData().get(position).setOpen(false);
                    }
                }


            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));

    }


}
