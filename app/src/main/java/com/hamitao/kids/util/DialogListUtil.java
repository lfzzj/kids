package com.hamitao.kids.util;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.hamitao.base_module.adapter.DialogListAdapter;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.DialogScheduleAdapter;
import com.hamitao.kids.model.ClipModel;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.DialogScheduleModel;
import com.hamitao.kids.network.NetworkRequest;
import com.hamitao.kids.player.AudioPlayer;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * Created by linjianwen on 2018/5/9.
 * <p>
 * 投送弹窗工具
 */

public class DialogListUtil {


    /**
     * 静态类单例模式
     */
//    private DialogListUtil() {}
//
//    public static DialogListUtil getInstance() {
//        return DialogListUtilHolder.instance;
//    }
//
//    private static class DialogListUtilHolder {
//        private static final DialogListUtil instance = new DialogListUtil();
//    }


    private BaseActivity activity;

    private DialogListAdapter deviceAdapter, clipAdapter, musicAdapter;

    private DialogScheduleAdapter adapter;

    private List<DialogScheduleModel> isOpenModels = new ArrayList<>();

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> terminalInfos;

    private List<String> deviceNames = new ArrayList<>(); // 机器人名字

    private List<String> clipNames = new ArrayList<>(); //弹窗list

    private List<String> musicName = new ArrayList<>(); //音乐

    private TDialog dialog;

    public DialogListUtil(BaseActivity activity) {
        this.activity = activity;
    }

    public void setIndex(int index) {
        this.index = index;
        if (musicAdapter != null) {
            musicAdapter.setIndex(index);
        }
    }

    private int index = -1; // 当前歌曲正在播放的位置, -1表示没在播放


//    public void init(BaseActivity activity) {
//        this.activity = activity;
//    }

    /**
     * 显示投送到机器人弹窗
     */
    public void showDeliverDialog(final BGAOnRVItemClickListener clickListener) {

        terminalInfos = GsonUtil.GsonToList(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, ""),
                DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);

        if (terminalInfos.size() <= 0) {
            ToastUtil.showShort("还未绑定机器人!");
            return;
        }
        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_item_addcollect, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        deviceAdapter = new DialogListAdapter(recyclerView);
        deviceAdapter.setOnRVItemClickListener(clickListener);
        deviceNames.clear();
        for (int i = 0; i < terminalInfos.size(); i++) {
            deviceNames.add(terminalInfos.get(i).getBindname());
        }
        deviceAdapter.setData(deviceNames);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(deviceAdapter);
        ((TextView) view.findViewById(R.id.dialog_list_title)).setText("机器人列表");

        dialog = new TDialog.Builder(activity.getSupportFragmentManager())
                .setDialogView(view)
                .setScreenHeightAspect(activity, 0.4f)
                .setScreenWidthAspect(activity, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (deviceAdapter != null) {
                            deviceAdapter.clear();
                            deviceAdapter = null;
                        }
                    }
                })
                .setCancelableOutside(true)     //弹窗在界面外是消否可以点击取
                .setCancelable(true)
                .addOnClickListener(R.id.recyclerview, R.id.dialog_list_more)
                .create().show();
    }


    private void showScanDialog() {
        activity.showConfirmDialog(activity, "请先绑定机器人!", new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                if (view.getId() == R.id.tv_confirm) {
                    Router.build("scan").go(activity);
                }
                tDialog.dismiss();
            }
        });
    }


    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 显示搜藏夹列表
     *
     * @param listener
     * @param clickListener
     */
    public void showClipListDialog(final OnViewClickListener listener, final BGAOnRVItemClickListener clickListener) {
        NetworkRequest.queryMyClipRequest(UserUtil.user().getCustomerid(), new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ClipModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ClipModel.ResponseDataObjBean.class);
                View view = LayoutInflater.from(activity).inflate(R.layout.dialog_item_addcollect, null);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
                ((TextView) view.findViewById(R.id.dialog_list_more)).setText("+ 新建");
                clipAdapter = new DialogListAdapter(recyclerView); //收藏夹
                clipAdapter.setShow(true); //显示头像
                clipAdapter.setOnRVItemClickListener(clickListener);
                clipNames.clear();
                for (int i = 0; i < bean.getFavoriteCategorys().size(); i++) {
                    clipNames.add(bean.getFavoriteCategorys().get(i));
                }
                clipAdapter.setData(clipNames);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(clipAdapter);
                dialog = new TDialog.Builder(activity.getSupportFragmentManager())
                        .setDialogView(view)
                        .setScreenHeightAspect(activity, 0.4f)
                        .setScreenWidthAspect(activity, 1f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                        .setGravity(Gravity.BOTTOM)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (clipAdapter != null) {
                                    clipAdapter.clear();
                                    clipAdapter = null;
                                }
                            }
                        })
                        .setDimAmount(0.25f)     //设置弹窗 背景透明度(0-1f)
                        .setCancelableOutside(true)     //弹窗在界面外是消否可以点击取
                        .setCancelable(true)
                        .addOnClickListener(R.id.dialog_list_more)
                        .setOnViewClickListener(listener).create().show();
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                ToastUtil.showShort(msg);
            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }


    /**
     * 显示音乐听单
     *
     * @param listener
     * @param clickListener
     * @param childClickListener
     */
    public void showMusicListDialog(final OnViewClickListener listener, final BGAOnRVItemClickListener clickListener, final BGAOnItemChildClickListener childClickListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_item_addcollect, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
//        ((TextView) view.findViewById(R.id.dialog_list_more)).setText("清空");
        ((TextView) view.findViewById(R.id.tv_title)).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.dialog_list_title)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tv_title)).setText("歌单 (" + AudioPlayer.get().getMusicList().size() + ")");
//        ((TextView) view.findViewById(R.id.tv_title)).setText("歌单");
        musicAdapter = new DialogListAdapter(recyclerView); //收藏夹
        musicAdapter.setShow(false); //是否显示删除按钮
        musicAdapter.setOnRVItemClickListener(clickListener);
        musicAdapter.setOnItemChildClickListener(childClickListener);
        musicName.clear();
        for (int i = 0; i < AudioPlayer.get().getMusicList().size(); i++) {
            ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean bean = AudioPlayer.get().getMusicList().get(i);
            musicName.add(bean.getMediatitle());
        }
        musicAdapter.setMusicList(true, AudioPlayer.get().getPlayPosition());
        musicAdapter.setData(musicName);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(musicAdapter);
        dialog = new TDialog.Builder(activity.getSupportFragmentManager())
                .setDialogView(view)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (musicAdapter != null) {
                            musicAdapter.clear();
                            musicAdapter = null;
                        }

                    }
                })
                .setScreenHeightAspect(activity, 0.5f)
                .setScreenWidthAspect(activity, 1f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.BOTTOM)     //设置弹窗展示位置
                .setDimAmount(0.25f)     //设置弹窗 背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是消否可以点击取
                .setCancelable(true)
                .addOnClickListener(R.id.dialog_list_more)
                .setOnViewClickListener(listener)
                .create().show();
    }


    /**
     * 显示课程表弹窗
     *
     * @param listener
     * @param clickListener
     */
    public void showScheduleStatus(final OnViewClickListener listener, final BGAOnRVItemClickListener clickListener) {
        terminalInfos = GsonUtil.GsonToList(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, ""),
                DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);
        if (terminalInfos.size() <= 0) {
            ToastUtil.showShort("还未绑定机器人！");
            return;
        }


        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_item_addcollect, null);
        view.findViewById(R.id.ll_close).setVisibility(View.VISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new DialogScheduleAdapter(recyclerView);
        adapter.setOnRVItemClickListener(clickListener);
        isOpenModels.clear();


        //遍历用户绑定的机器人
        for (int i = 0; i < terminalInfos.size(); i++) {
            final DialogScheduleModel model = new DialogScheduleModel();
            model.setNickname(terminalInfos.get(i).getBindname());
            model.setId(terminalInfos.get(i).getTerminalid());
            model.setChannelid(terminalInfos.get(i).getChannelid_listen_on_pushserver());
            isOpenModels.add(model);
        }


        adapter.setData(isOpenModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        ((TextView) view.findViewById(R.id.dialog_list_title)).setText("机器人列表");

        dialog = new TDialog.Builder(activity.getSupportFragmentManager())
                .setDialogView(view)
                .setScreenHeightAspect(activity, 0.4f)
                .setScreenWidthAspect(activity, 1f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.BOTTOM)     //设置弹窗展示位置
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (adapter != null) {
                            adapter.clear();
                            adapter = null;
                        }
                    }
                })
                .setDimAmount(0.25f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是消否可以点击取
                .setCancelable(true)
                .addOnClickListener(R.id.recyclerview, R.id.dialog_list_more, R.id.ll_close)
                .setOnViewClickListener(listener)
                .create().show();


    }


    /**
     * 获取课表状态
     *
     * @param position
     * @return
     */
    public DialogScheduleModel getScheduleStutas(int position) {

        return adapter.getData().get(position);
    }


    /**
     * 设置课表状态
     *
     * @param position
     * @param isOpen
     */
    public void setScheduleStutas(int position, boolean isOpen) {
        adapter.getData().get(position).setOpen(isOpen);
    }


    /**
     * 添加搜藏夹
     *
     * @param clipName
     */
    public void addClip(String clipName) {
        clipAdapter.addLastItem(clipName);
    }


    /**
     * 清空播放听单
     */
    public void deleteAll() {
        AudioPlayer.get().deleteAll();
        musicAdapter.clear();
        musicAdapter.notifyDataSetChanged();
    }

    /**
     * 删除指定音频
     *
     * @param position
     */
    public void deleteMusic(int position) {
        AudioPlayer.get().delete(position);
        musicName.remove(position);
        musicAdapter.notifyDataSetChanged();
    }


    //获取机器人id
    public String getTerminalId(int position) {
        return terminalInfos.get(position).getTerminalid();
    }

    //获取设的信息
    public List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> getTerminalInfo() {
        return terminalInfos;
    }


    //获取推送渠道
    public String getTargetChannel(int position) {
        return terminalInfos.get(position).getChannelid_listen_on_pushserver();
    }

    //获取选中的收藏夹名称
    public String getClipName(int position) {
        return clipNames.get(position);
    }

    //获取点击的音乐
    public ContentModel.ResponseDataObjBean.ContentsBean.MediaListBean getMusic(int position) {
        return AudioPlayer.get().getMusicList().get(position);
    }


    /**
     * 判断是否有同名收藏夹
     *
     * @param name
     * @return
     */
    public boolean isSameClip(String name) {

        return clipAdapter.getData().contains(name);
    }


    //新建收藏夹后刷新列表
    public void notyfyClipList(String clipName) {
        clipAdapter.addLastItem(clipName);
    }


    public DialogListAdapter getMusicAdapter() {
        return musicAdapter != null ? musicAdapter : null;
    }


}
