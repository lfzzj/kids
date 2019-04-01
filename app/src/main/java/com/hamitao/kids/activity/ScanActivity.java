package com.hamitao.kids.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.FileUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.nfccard.NfcModel;
import com.hamitao.kids.model.requestmodel.NfcRequestModel;
import com.hamitao.kids.mvp.scan.ScanPresenter;
import com.hamitao.kids.mvp.scan.ScanView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

import static com.hamitao.base_module.RequestCode.REQUEST_FROM_GALLERY;

/**
 * 二维码扫面界面
 */

@Route("scan")
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, ScanView {

    private static final String TAG = ScanActivity.class.getSimpleName();

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.scanView)
    QRCodeView scanView;

    private ScanPresenter presenter;

    private boolean isShowDialog = false; //是否正在显示弹窗

    //正则（判断是否为网址）
    private String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @Override
    protected void onDestroy() {
        scanView.onDestroy();
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    private void initData() {
        presenter = new ScanPresenter(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        scanView.startCamera();     //打开相机
        scanView.showScanRect();    //显示矩形扫描框
    }

    @Override
    protected void onStop() {
        scanView.stopCamera();
        super.onStop();
    }

    private void initView() {
        title.setText("扫一扫");
        more.setText("相册");
//        more.setVisibility(View.GONE);
        scanView.setDelegate(this);
        //延迟1秒开始识别
        scanView.startSpotDelay(1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.startSpotDelay(1000);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //从相册中选取
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUEST_FROM_GALLERY);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        scanView.showScanRect();
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_FROM_GALLERY) {
            Uri uri = data.getData();
            scanView.decodeQRCode(FileUtil.getRealPathFromUri(this, uri));

        }
    }


    @Override
    public void onScanQRCodeSuccess(String result) {

        if (result == null) {
            ToastUtil.showShort("未发现二维码");
            return;
        }

        if (result.matches(regex)) {
            //判断是否为网址
            Router.build("webview").with("result", result).go(this);
        } else if (result.matches("[0-9]*")) {
            //判断是否为纯数字
            if (getIntent().getStringExtra("info") != null) {
                //此处进行制卡

                NfcRequestModel model = new NfcRequestModel();
                NfcRequestModel.NfcardBean bean = new NfcRequestModel.NfcardBean();

                bean.setInfo(getIntent().getStringExtra("info"));
                bean.setInfotype(getIntent().getStringExtra("infotype"));
                bean.setTitle(getIntent().getStringExtra("title"));
                bean.setImg(getIntent().getStringExtra("img"));
                bean.setNFCID(result);

                model.setCustomerid(UserUtil.user().getCustomerid());
                model.setNfcard(bean);

                presenter.makeCard(model);

                bean = null;
                model = null;
            } else {
                presenter.queryMyCardByCode(result);
            }
        } else if (result.contains("deviceid=")) {

            String id = "";
            String vendor = Constant.HAMITAO;

            if (result.contains(",")) {
                String str[] = result.split(",");
                id = str[0].split("=")[1];  //deviceid=xxx
                vendor = str[1]; //hamitao
            } else {
                id = result.split("=")[1];
            }

            //绑定机器人
            scanView.stopSpot();
            if (UserUtil.user() == null) {
                showLoginDialog();
            } else {
                if (!vendor.equals(BaseApplication.getInstance().getVendor())) {
                    ToastUtil.showShort("机器人不存在");
                    return;
                }
                //跳转到身份选择页面
                Router.build("identity_select").with("deviceid", id).go(this);
                finish();
            }
        } else if (result.contains("groupid=")) {

            if (UserUtil.user() == null) {
                ToastUtil.showShort("请先登录!");
                return;
            }
            //扫码成功后跳转到群聊页面
            final long id = Long.parseLong(result.substring(8, result.length()));
            JMessageClient.applyJoinGroup(id, null, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        ToastUtil.showShort("加入群聊成功");
                        //申请加入群聊成功
                        Conversation conv = JMessageClient.getGroupConversation(id);
                        if (conv == null) {
                            //创建群聊会话
                            conv = Conversation.createGroupConversation(id);
                        }
                        //刷新会话列表
                        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
                        Router.build("wechat")
                                .with(Constant.CONV_TITLE, conv.getTitle()).with("isSingle", false)
                                .with(Constant.TARGET_APP_KEY, getString(R.string.JPUSH_APPKEY))
                                .with(Constant.GROUP_ID, id)
                                .go(ScanActivity.this);

                    } else {
                        ToastUtil.showShortError(s);
                    }
                }
            });
            finish();
        } else {
            ToastUtil.showShort(result);
        }

        vibrate(200);
        if (isShowDialog) {
            scanView.startSpotDelay(1000);
        }
    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
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
        ToastUtil.showShortError(msg);
    }

    @Override
    public void bindSuccess(NfcModel.ResponseDataObjBean.NFCCardsBean model) {
        finish();
        //卡片绑定成功后跳转到查询页面
        Router.build("bind_nfc").with("infotype", model.getInfotype()).with("info", model.getInfo()).with("cardid", model.getNFCID()).go(this);
    }

    @Override
    public void bindNull() {
        finish();
    }
}
