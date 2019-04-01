package com.hamitao.kids.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.Constant;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.customview.RoundCornerImageView;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.kids.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建群聊二维码
 */
@Route("create_code")
public class CreateCodeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_code)
    RoundCornerImageView ivCode;

    @BindView(R.id.tv_saveImg)
    TextView tv_saveImg;

    private Bitmap codeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        createQRcode("groupid=" + String.valueOf(getIntent().getLongExtra("groupId", 0)));
    }

    private void initView() {
        title.setText("生成群二维码");
        ivCode.setCorner(50, 50);
    }


    //生成二维码
    private void createQRcode(final String msg) {

        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {

                //生成带logo的二维码
//                Bitmap logoBitmap = BitmapFactory.decodeResource(CreateCodeActivity.this.getResources(), R.drawable.app_logo);
//                e.onNext(QRCodeEncoder.syncEncodeQRCode(msg, BGAQRCodeUtil.dp2px(CreateCodeActivity.this, 150), Color.BLACK, Color.WHITE, logoBitmap));

                e.onNext(QRCodeEncoder.syncEncodeQRCode(msg, BGAQRCodeUtil.dp2px(CreateCodeActivity.this, 150)));


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                if (bitmap != null) {
                    ivCode.setImageBitmap(bitmap);
                    codeImg = bitmap;
                } else {
                    ToastUtil.showShort("生成二维码失败");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.back, R.id.more, R.id.tv_saveImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
            case R.id.tv_saveImg:
                //点击保存图片
                saveImg(this, codeImg);
                break;
        }
    }

    private void saveImg(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Constant.USER_PIC_LOCAL);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

        ToastUtil.showShort("图片已保存在：" + appDir.getPath());
    }
}
