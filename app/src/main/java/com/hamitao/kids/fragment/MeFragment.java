
package com.hamitao.kids.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chenenyu.router.Router;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.base.BaseFragment;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.kids.mvp.me.MePresenter;
import com.hamitao.kids.mvp.me.MeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * Created by linjianwen on 2018/1/4.
 * <p>
 * TAB 我的
 */

public class MeFragment extends BaseFragment implements MeView {

    @BindView(R.id.face)
    CircleImageView face;

    @BindView(R.id.tv_nick)
    TextView tv_nick;

    private View view;

    private Unbinder unbinder;

    private MePresenter presenter;

    private BaseActivity baseActivity;

    private List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> terminalInfos = new ArrayList<>();

    @OnClick({R.id.face, R.id.tv_signIn, R.id.rl_machine, R.id.rl_schedule, R.id.rl_nfc, R.id.rl_setting,
            R.id.tv_collection, R.id.tv_record, R.id.tv_recentPlay, R.id.rl_share, R.id.btn_connectMachine})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.face:
                //用户信息
                if (UserUtil.user() == null) {
                    Router.build("login_guide").go(this);
                } else {
                    if ("登录/注册".equals(tv_nick.getText())){
                        Router.build("user_info").requestCode(RequestCode.REQUEST_USERINFO).go(this);
                    }else {
                        Router.build("user_info").go(this);
                    }

                }
                break;
            case R.id.rl_machine:
                //我的机器人
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    // 查询绑定关系
                    presenter.queryDeviceRelation(UserUtil.user().getCustomerid());
                }
                break;
            case R.id.rl_schedule:
                //我的课表
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    if (terminalInfos.size() <= 0) {
                        ToastUtil.showShort("请先绑定机器人");
                    } else {

                        if (!BaseApplication.getInstance().isPublicVendor()) {
                            ToastUtil.showShort(getResources().getString(R.string.still_develop));
                            return;
                        }
                        Router.build("create_schedule").go(this);
                    }

                }
                break;
            case R.id.rl_nfc:
                //制卡管理
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    if (terminalInfos.size() <= 0) {
                        ToastUtil.showShort("请先绑定机器人");
                    } else {
                        if (!BaseApplication.getInstance().isPublicVendor()) {
                            ToastUtil.showShort(getResources().getString(R.string.still_develop));
                            return;
                        }
                        Router.build("make_card").go(this);
                    }

                }
                break;
            case R.id.rl_setting:
                //设置
                Router.build("setting").go(this);
                break;
            case R.id.tv_collection:
                //我的收藏
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    Router.build("my_collect").go(this);
                }
                break;
            case R.id.tv_record:
                //我的录音
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    Router.build("my_record").go(this);
                }
                break;
            case R.id.tv_recentPlay:
                //最近播放
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    Router.build("recent_play").go(this);
                }
                break;
            case R.id.rl_share:
                if (UserUtil.user() == null) {
                    baseActivity.showLoginDialog();
                } else {
                    Router.build("my_share").go(this);
                }
                break;
            case R.id.btn_connectMachine:
                //连接机器人
                Router.build("scan").go(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQUEST_USERINFO){
            presenter.updateUserinfo(UserUtil.user().getCustomerid());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_me, container, false);

        unbinder = ButterKnife.bind(this, view);

        initData();

        return view;
    }


    private void initData() {

        presenter = new MePresenter(this);

        baseActivity = (BaseActivity) getActivity();


        RxBus.getInstance().toObservable(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String type) throws Exception {
                switch (type) {
                    case RxBusEvent.LOGIN_SUCCESS:
                        if (UserUtil.user() != null)
                            presenter.updateUserinfo(UserUtil.user().getCustomerid());
                        break;
                    case RxBusEvent.LOGOUT:
                        face.setImageResource(R.drawable.icon_head_default);
                        tv_nick.setText("登录/注册");
                        break;
                    case RxBusEvent.MEFRAGMENT_REFRESH_DEVICE_INFO:
                        getDeviceInfo();
                        break;
                    default:
                        break;
                }
            }
        });

        if (UserUtil.user() == null) {
            tv_nick.setText("登录/注册");
        } else {
            presenter.updateUserinfo(UserUtil.user().getCustomerid());
        }
    }

    @Override
    public void onResume() {
        getDeviceInfo();
        super.onResume();

    }


    private void getDeviceInfo() {
        //更新机器人的绑定情况
        terminalInfos = GsonUtil.GsonToList(PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Device_Relation, ""),
                DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean.class);
    }


    @Override
    public void getUserInfo(UserInfoModel.ResponseDataObjBean model) {
        String url = model.getMyInfo().getMyheaderpic_httpURL();
        Glide.with(this).load(url).error(R.drawable.icon_mother).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(face);
        tv_nick.setText(model.getMyInfo().getMynickname());
    }


    @Override
    public void getDeviceRelation(List<DeviceRelationModel.ResponseDataObjBean.RelationBean.TerminalInfosBean> bean) {
        if (bean.size() < 1) {
//            ((BaseActivity) getActivity()).showConfirmDialog(getActivity(), "请先绑定机器人!", new OnViewClickListener() {
//                @Override
//                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
//                    if (view.getId() == R.id.tv_confirm) {
//                        Router.build("scan").go(getActivity());
//                    }
//                    tDialog.dismiss();
//                }
//            });
            ToastUtil.showShort("请先绑定机器人");
        } else {
            Router.build("machine").go(getActivity());
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroyView();
    }

    @Override
    public void onBegin() {
        ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void onFinish() {
        ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }
}
