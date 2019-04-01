package com.hamitao.kids.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.kids.R;
import com.hamitao.kids.adapter.MyCollectAdapter;
import com.hamitao.kids.mvp.collection.CollectionPresenter;
import com.hamitao.kids.mvp.collection.CollectionView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * @author admin
 */
@Route("my_collect")
public class CollectActivity extends BaseActivity implements CollectionView, BGAOnItemChildClickListener, BGAOnRVItemClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.more)
    ImageView more;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tv_none)
    TextView tv_none;

    @BindView(R.id.ll_none)
    LinearLayout ll_none;

    private MyCollectAdapter adapter;

    private CollectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {

        adapter = new MyCollectAdapter(recyclerview);

        adapter.setOnItemChildClickListener(this);

        adapter.setOnRVItemClickListener(this);

        presenter = new CollectionPresenter(this);

        if (UserUtil.user() != null) {

            presenter.querMyClip(UserUtil.user().getCustomerid());

        } else {

            showConfirmDialog(this, "请先登录", new OnViewClickListener() {

                @Override
                public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {

                    if (view.getId() == R.id.tv_confirm) {

                        Router.build("login").go(CollectActivity.this);

                    }
                    tDialog.dismiss();
                }
            });
        }

    }

    private void initView() {

        title.setVisibility(View.VISIBLE);

        title.setText("收藏");

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setAdapter(adapter);
    }


    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                showInputDialog(this, "请输入收藏夹名字", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        ((EditText) viewHolder.getView(R.id.et_name)).setHint("请输入收藏夹名字");
                        EditText et = ((EditText) tDialog.getView().findViewById(R.id.et_name));
                        if (view.getId() == R.id.tv_confirm) {
                            if (et.getText().toString().equals("")) {
                                ToastUtil.showShort("请输入收藏夹名字");
                                return;
                            }
                            boolean hasClip = false;
                            for (int i = 0; i < adapter.getData().size(); i++) {
                                if (adapter.getData().get(i).equals(et.getText().toString())) {
                                    ToastUtil.showShort("该收藏夹已存在");
                                    tDialog.dismiss();
                                    return;
                                }
                            }
                            if (!hasClip) {
                                //新建收藏夹
                                presenter.createClip(UserUtil.user().getCustomerid(), et.getText().toString());
                            }
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void getList(List<String> favoriteCategorys) {
        if (favoriteCategorys.size() <= 0) {
            tv_none.setText("暂无收藏夹");
            ll_none.setVisibility(View.VISIBLE);
        } else {
            ll_none.setVisibility(View.GONE);
            adapter.setData(favoriteCategorys);
        }

    }

    @Override
    public void onError() {
        adapter.clear();
        tv_none.setText("数据请求失败");
        ll_none.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.list_more) {
            showMoreDialog(position);
        }
    }


    /**
     * 显示更多选项弹窗
     */
    private void showMoreDialog(final int position) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_contact)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setTag("DialogTest")
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_edit, R.id.tv_delete, R.id.tv_cancel)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_edit:
                                showInputDialog(CollectActivity.this, "请输入收藏夹名字", new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            EditText et = ((EditText) tDialog.getView().findViewById(R.id.et_name));
                                            //不允许创建同名收藏夹
                                            for (int i = 0; i < adapter.getData().size(); i++) {
                                                if (adapter.getData().get(i).equals(et.getText().toString())) {
                                                    ToastUtil.showShort("该收藏夹已存在");
                                                    tDialog.dismiss();
                                                    return;
                                                } else {
                                                    presenter.renameClip(UserUtil.user().getCustomerid(), adapter.getData().get(position), et.getText().toString(), position);
                                                }
                                            }
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_delete:
                                showConfirmDialog(CollectActivity.this, "确定要删除 " + adapter.getData().get(position) + " 吗?" +
                                        "", new OnViewClickListener() {
                                    @Override
                                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                        if (view.getId() == R.id.tv_confirm) {
                                            presenter.deleteClip(UserUtil.user().getCustomerid(), adapter.getData().get(position), position);
                                        }
                                        tDialog.dismiss();
                                    }
                                });
                                break;
                            case R.id.tv_cancel:
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                })
                .create().show();
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Router.build("collect_content").with("clipname", adapter.getData().get(position)).go(this);
    }


    @Override
    public void refreshData(Object status, Object data, Object aux) {
        switch ((String) status) {
            case "delete":
                ToastUtil.showShort("删除成功");
                adapter.removeItem((int) data);
                break;
            case "createclip":
                //创建收藏
                ToastUtil.showShort("创建成功");
                adapter.addLastItem((String) data);
                break;
            case "renameClip":
                ToastUtil.showShort("修改成功");
                adapter.setItem((int) aux, (String) data);
                break;
            default:
                break;
        }

    }
}
