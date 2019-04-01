package com.hamitao.kids.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.RequestCode;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.base.BaseApplication;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.DateUtil;
import com.hamitao.base_module.util.LogUtil;
import com.hamitao.base_module.util.PropertiesUtil;
import com.hamitao.base_module.util.ScreenUtil;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.UserInfoModel;
import com.hamitao.kids.mvp.userinfo.UserInfoPresenter;
import com.hamitao.kids.mvp.userinfo.UserInfoView;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hamitao.base_module.RequestCode.REQUEST_CAMERA;
import static com.hamitao.base_module.RequestCode.REQUEST_FROM_GALLERY;

@Route("user_info")
public class UserInfoActivity extends BaseActivity implements UserInfoView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_face)
    RelativeLayout rlFace;
    @BindView(R.id.rl_nick)
    RelativeLayout rlNick;
    @BindView(R.id.rl_bbName)
    RelativeLayout rlBbName;
    @BindView(R.id.rl_bbBirth)
    RelativeLayout rlbbBirth;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.iv_face)
    CircleImageView iv_face;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_bbSex)
    TextView tv_bbSex;
    @BindView(R.id.tv_birth)
    TextView tv_birth;
    @BindView(R.id.tv_bbName)
    TextView tv_bbName;
    @BindView(R.id.more)
    TextView complete;

    Calendar selectedDate = Calendar.getInstance();
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    private boolean isRegister = false; //是否新注册
    private boolean isSave = false; // 是否保存了信息
    private String nickName = "";
    private UserInfoPresenter presenter;
    private Uri uri;  //裁剪后获取到的uri
    private UserInfo userinfo;

    private boolean isSaveInfo = false;

    private String avator_url = "";

    private String loginName = "";

    private int EDIT_NICKNAME = 1;
    private int EDIT_BABY_NAME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        presenter = new UserInfoPresenter(this);
        avator_url = "appstorage/testdir/" + PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "") + "/Picture/header.png";

        loginName = PropertiesUtil.getInstance().getString(PropertiesUtil.SpKey.Login_Name, "");

        //注册名完成后跳转到这里
        if (Constant.XIAOSHUAI.equals(BaseApplication.getInstance().getVendor())) {
            tv_account.setText(loginName.replace(Constant.XIAOSHUAI + "_", ""));
        } else if (Constant.JINGUOWEI.equals(BaseApplication.getInstance().getVendor())) {
            tv_account.setText(loginName.replace(Constant.JINGUOWEI + "_", ""));
        } else {
            tv_account.setText(loginName);
        }

        isRegister = getIntent().getBooleanExtra("isregister", false);
        userinfo = JMessageClient.getMyInfo();

        if (isRegister) {
            initUserInfo();  //新注册
        } else {
            presenter.queryInfo(UserUtil.user().getCustomerid());
        }

    }

    private void initUserInfo() {
        userinfo = JMessageClient.getMyInfo();
        tv_nick.setText("淘妈妈");
        userinfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == 0) {
                    iv_face.setImageBitmap(bitmap);
                } else {
                    iv_face.setImageResource(R.drawable.icon_head_default);
                }
            }
        });
        //上传用户默认信息
        presenter.uploadPartUserInfo("", "淘妈妈");
    }

    private void initView() {
        complete.setText("完成");
        startDate.set(2013, 0, 23);//开始日期
        endDate.set(2019, 11, 28);//末尾日期
        title.setText("当前用户");
    }


    //网络请求查询用户信息
    @Override
    public void getUserInfo(UserInfoModel.ResponseDataObjBean bean) {
        String imgUrl = bean.getMyInfo().getMyheaderpic_httpURL();
        Glide.with(this).load(imgUrl).error(R.drawable.icon_head_default).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).dontAnimate().into(iv_face);
        tv_nick.setText(isRegister ? "淘妈妈" : bean.getMyInfo().getMynickname());
        tv_bbName.setText(bean.getChildInfos().get(0).getName());
        tv_birth.setText(bean.getChildInfos().get(0).getBirthday());
    }

    @Override
    public void onBegin() {
        showProgressDialog("资料保存中");
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }

        //页面销毁时，删除临时的头像
        if (targetFile != null && targetFile.exists()) {
            targetFile.delete();
        }

        //刚刚注册登录成功后，没有保存资料就销毁页面，发送事件刷新界面
        if (isRegister && !isSaveInfo) {
            RxBus.getInstance().post(RxBusEvent.LOGIN_SUCCESS);
        }
        super.onDestroy();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }

    @OnClick({R.id.back, R.id.rl_face, R.id.rl_nick, R.id.rl_bbName, R.id.rl_bbBirth, R.id.rl_bbSex, R.id.rl_logout, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                setResult(RequestCode.REQUEST_USERINFO);
                finish();
                break;
            case R.id.rl_face:
                showCameraDialog();
                break;
            case R.id.rl_bbSex:
                showSexDialog(tv_bbSex.getText().toString().equals("小王子") ? "小王子" : "小公主");
                break;
            case R.id.rl_nick:
                showEditDailog(this, "修改昵称", EDIT_NICKNAME);
                break;
            case R.id.rl_bbName:
                //宝宝名字
                showEditDailog(this, "宝宝名字", EDIT_BABY_NAME);
                break;
            case R.id.rl_bbBirth:
                //时间选择器
                showDatePickerDialog(this);
                break;
            case R.id.rl_logout:
                //退出登录
                showConfirmDialog(this, "确定要退出登录吗？", new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                break;
                            case R.id.tv_confirm:
                                userLogout();
                                break;
                            default:
                                break;
                        }
                        tDialog.dismiss();
                    }
                });
                break;
            case R.id.more:
                if (userinfo == null) {
                    ToastUtil.showShort("您的账号已在别的地方登录，重新登录后才能修改信息");
                } else {
                    isSaveInfo = true;
                    saveInfo();
                }
                break;
            default:
                break;

        }
    }

    private void saveInfo() {
        //编辑完成
        if (!tv_nick.getText().equals("")) {
            presenter.uploadUserInfo(avator_url, tv_nick.getText().toString(), tv_bbName.getText().toString(),
                    tv_bbSex.getText().toString().equals("小王子") ? "boy" : "girl", tv_birth.getText().toString());
        } else {
            ToastUtil.showShort("昵称不可为空");
            return;
        }
    }


    /**
     * 编辑弹窗
     *
     * @param context
     * @param title
     * @param type
     */
    private void showEditDailog(final Context context, String title, final int type) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_input, null);
        final EditText et = view.findViewById(R.id.et_name);
        TextView tv = view.findViewById(R.id.title);
        tv.setText(title);
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setScreenWidthAspect(this, 0.8f)
                .setGravity(Gravity.CENTER)
                .setTag("DialogTest")
                .setDimAmount(0.25f)
                .setCancelableOutside(false)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                            case R.id.tv_confirm:
                                if (et.getText().toString().isEmpty()){
                                    ToastUtil.showShort("昵称不可为空");
                                    return;
                                }
                                if (type == EDIT_NICKNAME) {
                                    tv_nick.setText(et.getText().toString());
                                    nickName = et.getText().toString();
                                } else {
                                    tv_bbName.setText(et.getText().toString());
                                }
                                tDialog.dismiss();
                                break;



                            default:
                                break;
                        }
                    }
                }).create().show();
    }


    /**
     * 宝宝性别
     *
     * @param sex
     */
    private void showSexDialog(final String sex) {
        new TDialog.Builder(getSupportFragmentManager())
                .setDialogView(LayoutInflater.from(this).inflate(R.layout.dialog_sex_select, null, false))
                .setScreenWidthAspect(this, 0.7f)
                .setGravity(Gravity.CENTER)
                .setTag("DialogTest")
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        Drawable boy = getResources().getDrawable(R.drawable.icon_sex_boy);
                        boy.setBounds(0, 0, ScreenUtil.dip2px(45), ScreenUtil.dip2px(45));
                        ((RadioButton) viewHolder.getView(R.id.rb_boy)).setCompoundDrawables(null, boy, null, null);

                        Drawable girl = getResources().getDrawable(R.drawable.icon_sex_girl);
                        girl.setBounds(0, 0, ScreenUtil.dip2px(45), ScreenUtil.dip2px(45));
                        ((RadioButton) viewHolder.getView(R.id.rb_girl)).setCompoundDrawables(null, girl, null, null);


                        RadioGroup rg = viewHolder.getView(R.id.rg);
                        rg.check(sex.equals("小王子") ? R.id.rb_boy : R.id.rb_girl);
                    }
                })
                .addOnClickListener(R.id.tv_cancel, R.id.tv_confirm)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                            case R.id.tv_confirm:
                                RadioGroup rg = (RadioGroup) tDialog.getView().findViewById(R.id.rg);
                                int select = rg.getCheckedRadioButtonId();
                                switch (select) {
                                    case R.id.rb_boy:
                                        tv_bbSex.setText("小王子");
                                        break;
                                    case R.id.rb_girl:
                                        tv_bbSex.setText("小公主");
                                        break;
                                    default:
                                        break;
                                }
                                tDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();


    }

    /**
     * 日期选择弹窗
     * @param context
     */
    private void showDatePickerDialog(final Context context) {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_birth.setText(DateUtil.formatDate(date));
                selectedDate.setTime(date);
            }
        })
                .setSubmitColor(Color.parseColor("#ff7781")).setSubmitText("确定").setCancelColor(Color.BLACK).setCancelText("取消")
                .setRangDate(startDate, endDate).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒").build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.setDate(selectedDate);
        pvTime.show();

    }



    /**
     * 退出登录
     */
    public void userLogout() {
        UserUtil.clear(); // 清除用户本地数据
        JMessageClient.logout(); //极光IM退出登录
        PropertiesUtil.getInstance().setBoolean(PropertiesUtil.SpKey.isLogin, false);//登录状态存储在本地
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.Device_Relation);//将机器人信息清空
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.Other_Product);
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.isBindDevice);
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.Nickname);
        PropertiesUtil.getInstance().remove(PropertiesUtil.SpKey.Login_Name);
        RxBus.getInstance().post(RxBusEvent.LOGOUT);
        RxBus.getInstance().post(RxBusEvent.REFRESH_CONVERSATION_LIST);
        finish();
    }

    /**
     * 显示拍照弹窗
     */
    private void showCameraDialog() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_take_picture)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setDimAmount(0.25f)
                .setCancelableOutside(true)
                .setCancelable(true)
                .addOnClickListener(R.id.tv_takePhoto, R.id.tv_fromAlbum, R.id.tv_cancel)
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
                            default:
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
                if (resultCode == RESULT_CANCELED) {
                    //取消拍照
                    return;
                } else if (resultCode != RESULT_OK) {
                    ToastUtil.showShort("拍照失败");
                } else {
                    //如果指定了目标uri，data就没有数据，如果没有指定uri，则data就返回有数据
                    //进行裁剪
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // 调用裁剪方法
                        if (mProviderUri == null) {
                            return;
                        }
                        cropRawPhoto(mProviderUri);
                    } else {
                        if (mUri == null) {
                            return;
                        }
                        cropRawPhoto(mUri);
                    }
                }
                break;
            case REQUEST_FROM_GALLERY:
                //从相册选取
                if (data != null && data.getData() != null) {
                    cropRawPhoto(data.getData());
                }
                break;
            case UCrop.REQUEST_CROP:
                //裁剪图片成功后执行，如果从相册中选中图片裁剪后，则会返回 data 数据，拍照裁剪不会返回
                //删除临时保存照片的文件
                if (photoFile != null && photoFile.exists()) {
                    photoFile.delete();
                }
                if (data != null) {
                    uri = UCrop.getOutput(data);
                    Glide.with(this).load(uri).error(R.drawable.icon_head_default).into(iv_face);
                }
                break;
            case UCrop.RESULT_ERROR:
                // 失败
                ToastUtil.showShort(UCrop.getError(data) + "");
                break;
            default:
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
    private File photoFile;//拍照后存放相片
    private File targetFile;

    /**
     * 拍照
     */
    private void camera() {
        //创建一个文件来存放照片
        photoFile = new File(mFilepath, System.currentTimeMillis() + ".png");
        //如果该文件已存在则删除
        if (!photoFile.getParentFile().exists()) {
            photoFile.getParentFile().mkdirs();
        }
        //打开相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            mProviderUri = FileProvider.getUriForFile(this, Constant.PACKAGE, photoFile);
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

    /**
     * 选择相片
     */
    private void selectImg() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_FROM_GALLERY);
    }

    /**
     * 裁剪相片
     *
     * @param uri
     */
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

        //页面销毁时，删除临时的头像
        if (targetFile != null && targetFile.exists()) {
            targetFile.delete();
        }

        targetFile = new File(mFilepath, System.currentTimeMillis() + ".png");

        UCrop.of(uri, Uri.fromFile(targetFile))
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(200, 200)
                // 配置参数
                .withOptions(options)
                .start(this);
    }

    /**
     * 上传成功
     */
    @Override
    public void uploadSuccess() {
        if (!tv_nick.getText().equals("")) {
            userinfo.setNickname(tv_nick.getText().toString());
        } else {
            ToastUtil.showShort("昵称不可为空");
            return;
        }
        JMessageClient.updateMyInfo(UserInfo.Field.nickname, userinfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                //上传用户信息后，上传用户的头像
                if (i != 0) {
                    ToastUtil.showShort("用户信保存失败");
                } else {
                    if (targetFile != null) {
                        JMessageClient.updateUserAvatar(targetFile, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                    LogUtil.d("userinfo", "gotResult: 个人页头像上传成功");
                                    //更新头像成功后，上传到阿里云
                                    BaseApplication.getInstance().getOss().asyncPutObject(new PutObjectRequest(Constant.bucket, avator_url, targetFile.getPath()), new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                                        @Override
                                        public void onSuccess(PutObjectRequest request, final PutObjectResult result) {
                                            //保存头像到本地
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dismissProgressDialog();
                                                    ToastUtil.showShort("更新信息完成");
                                                }
                                            });
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dismissProgressDialog();
                                                    ToastUtil.showShort("更新信息失败");
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        dismissProgressDialog();
                        ToastUtil.showShort("更新信息完成");
                        finish();
                    }
                    //昵称保存在本地
                    PropertiesUtil.getInstance().setString(PropertiesUtil.SpKey.Nickname, nickName);
                    RxBus.getInstance().post(RxBusEvent.LOGIN_SUCCESS);
                }
            }
        });


    }


}
