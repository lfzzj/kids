package com.hamitao.kids.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.mvp.perfectinfo.PerfectInfoPresenter;
import com.hamitao.kids.mvp.perfectinfo.PerfectInfoView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hamitao.base_module.RequestCode.REQUEST_CAMERA;
import static com.hamitao.base_module.RequestCode.REQUEST_FROM_GALLERY;


/**
 * 完善信息界面
 */

@Route("perfect_info")
public class PerfectInfoActivity extends BaseActivity implements PerfectInfoView {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.iv_set_face)
    CircleImageView iv_face;
    @BindView(R.id.tv_nick)
    TextView tv_Nick;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_bbName)
    TextView tv_bbName;
    @BindView(R.id.tv_bbBirthday)
    TextView tv_bbBirthday;


    Calendar selectedDate = Calendar.getInstance();
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    private PerfectInfoPresenter presenter;

    //默认上传到OSS的头像名称
    private String default_avator = "appstorage/testdir/" + PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "")
            + "/Picture/header.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new PerfectInfoPresenter(this);
    }

    private void initView() {
        more.setText("完成");
        startDate.set(2013, 0, 23);//开始日期
        endDate.set(2019, 11, 28);//末尾日期
        title.setText("完善用户信息");
    }

    @OnClick({R.id.back, R.id.more, R.id.iv_set_face, R.id.rl_nick, R.id.rl_account, R.id.rl_bbName, R.id.rl_bbBirthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //编辑完成
                finish();
                //上传个人信息
//                presenter.uploadUserInfo();
                break;
            case R.id.iv_set_face:
                showCameraDialog(this);
                break;
            case R.id.rl_nick:
                showEditDialog(this, "请输入昵称", tv_Nick);
                break;
            case R.id.rl_account:
                break;
            case R.id.rl_bbName:
                showEditDialog(this, "请宝宝姓名", tv_bbName);
                break;
            case R.id.rl_bbBirthday:
                showDatePickerDialog(this);
                break;
        }
    }

    private File photoFile;//存放相片

    //编辑弹窗
    private void showEditDialog(final Context context, String msg, final TextView textView) {
        final EditText et = new EditText(context);
        new AlertDialog.Builder(context).setTitle("提示").setMessage(msg).setView(et).setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(et.getText().toString());
                    }
                }).show();
    }

    //日期选择弹窗
    private void showDatePickerDialog(final Context context) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(PerfectInfoActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_bbBirthday.setText(DateUtil.formatDate(date));
                selectedDate.setTime(date);
            }
        }).setRangDate(startDate, endDate).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒").build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.setDate(selectedDate);


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

    //拍照弹窗
    private void showCameraDialog(final Context context) {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_take_picture)    //设置弹窗展示的xml布局 //                .setDialogView(view)  //设置弹窗布局,直接传入View
                .setScreenWidthAspect(this, 1f)   //设置弹窗宽度(参数aspect为屏幕宽度比例 0 - 1f)
                .setGravity(Gravity.BOTTOM)     //设置弹窗展示位置
                .setTag("DialogTest")   //设置Tag
                .setDimAmount(0.25f)     //设置弹窗背景透明度(0-1f)
                .setCancelableOutside(true)     //弹窗在界面外是否可以点击取消
                .setCancelable(true)    //弹窗是否可以取消
                .addOnClickListener(R.id.tv_takePhoto, R.id.tv_fromAlbum, R.id.tv_cancel)   //添加进行点击控件的id
                .setOnViewClickListener(new OnViewClickListener() {     //View控件点击事件回调
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_takePhoto:
                                camera();
                                break;
                            case R.id.tv_fromAlbum:
                                //从相册选择
                                selectImg();
                                break;
                        }
                        tDialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                //拍照
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 调用裁剪方法
                    cropRawPhoto(mProviderUri);
                } else {
                    cropRawPhoto(mUri);
                }
                break;
            case REQUEST_FROM_GALLERY:
                //从相册选取
                if (data != null)
                    cropRawPhoto(data.getData());
                break;
            case UCrop.REQUEST_CROP:
                // 成功（返回的是文件地址）
                Glide.with(this)
                        .load(data != null ? UCrop.getOutput(data) : Uri.fromFile(targetFile))
                        .into(iv_face);
                break;
            case UCrop.RESULT_ERROR:
                // 失败
                ToastUtil.showShort(UCrop.getError(data) + "");
                break;
        }
    }

    //-------------------------拍照裁剪-----------------------------

    // 7.0 以上的uri
    private Uri mProviderUri;
    // 7.0 以下的uri
    private Uri mUri;
    // 图片路径
    private String mFilepath = Constant.USER_PIC_LOCAL;

    private File targetFile = new File(mFilepath, +System.currentTimeMillis() + ".png");

    private void camera() {
        photoFile = new File(mFilepath, System.currentTimeMillis() + ".png");
        if (!photoFile.getParentFile().exists()) {
            photoFile.getParentFile().mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            mProviderUri = FileProvider.getUriForFile(this, "com.hamitao.kids", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mProviderUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            mUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        }
        try {
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showShort("摄像头未准备好！");
        }
    }

    private void selectImg() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_FROM_GALLERY);
    }

    public void cropRawPhoto(Uri uri) {
        // 修改配置参数（我这里只是列出了部分配置，并不是全部）
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
        options.setFreeStyleCropEnabled(true);
        // 设置源uri及目标uri
        UCrop.of(uri, Uri.fromFile(targetFile))
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(200, 200)
                // 配置参数
                .withOptions(options)
                .start(this);
    }

    @Override
    public void uploadSuccess() {
        if (targetFile != null) {
            PutObjectRequest put = new PutObjectRequest(Constant.bucket, default_avator, targetFile.getPath());
             BaseApplication.getInstance().getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    dismissProgressDialog();
                    ToastUtil.showShort("保存成功");
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    dismissProgressDialog();
                }
            });
        }
    }



}
