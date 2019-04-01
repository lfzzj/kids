package com.hamitao.base_module.util;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.hamitao.base_module.enums.ErrorCode;
import com.hamitao.base_module.network.FileUploadNetWordResult;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import static com.hamitao.base_module.util.OkhttpUploadMultipleFileUtil.MyHandler.AFTER;
import static com.hamitao.base_module.util.OkhttpUploadMultipleFileUtil.MyHandler.BEFORE;
import static com.hamitao.base_module.util.OkhttpUploadMultipleFileUtil.MyHandler.PROGRESS;
import static com.hamitao.base_module.util.OkhttpUploadMultipleFileUtil.MyHandler.UPLOAD_FAIL;
import static com.hamitao.base_module.util.OkhttpUploadMultipleFileUtil.MyHandler.UPLOAD_SUCC;


/**
 * Created by Zenfer on 2016/1/28.
 * 多文件表单上传
 */
public class OkhttpUploadMultipleFileUtil {

    public static final String TAG = "OkhttpUploadHelper";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
    private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(300, TimeUnit.SECONDS).writeTimeout(500, TimeUnit.SECONDS).readTimeout(300, TimeUnit.SECONDS).build();

    private MyHandler handler;

    public OkhttpUploadMultipleFileUtil(@NonNull OnUploadListener onUploadListener) {
        handler = new MyHandler(onUploadListener);
    }

    public void upload(String url, Map<String, File> files, final Map<String, String> values) throws NetworkErrorException {
        handler.sendEmptyMessage(BEFORE);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (files != null) {
            for (String key : files.keySet()) {
                File file = files.get(key);
                if (!file.exists()) {
                    uploadFail("Some files is not exists !");
                }
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE, file));
            }
        } else {
            uploadFail(" files is null !");
        }
        if (values != null) {
            for (String key : values.keySet()) {
                builder.addFormDataPart(key, values.get(key));
            }
        }
        RequestBody requestBody = builder.build();

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(new ProgressRequestBody(requestBody, handler)).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                uploadFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                FileUploadNetWordResult result = GsonUtil.GsonToBean(json, FileUploadNetWordResult.class);
                if (result == null) {
                    uploadFail(json);
                    return;
                }
                if (result.isOk()) {
                    //请求成功
                    //token未过期
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putSerializable("result", result);
                    msg.setData(b);
                    msg.what = UPLOAD_SUCC;
                    handler.sendMessage(msg);
                    handler.sendEmptyMessage(AFTER);
                } else {
                    //请求失败
                    String message = "未知错误";
                    if (!StringUtil.isBlank(result.getError().getMessage())) {
                        message = result.getError().getMessage();
                    }
                    if (result.getError().getCode() == ErrorCode.LOGIN_TOKEN_INVALID.getType()) {
                        //token过期
                        //身份异常,重新登录
//                        ReloginUtil.relogin();
                    }
                    uploadFail(message);
                }

            }
        });
    }

    private void uploadFail(String error) {
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("error", error);
        msg.setData(b);
        msg.what = UPLOAD_FAIL;
        handler.sendMessage(msg);
        handler.sendEmptyMessage(AFTER);
    }

    static class MyHandler extends Handler {
        static final int BEFORE = 0x1000;
        static final int UPLOAD_SUCC = 0x1001;
        static final int UPLOAD_FAIL = 0x1002;
        static final int AFTER = 0x1003;
        static final int PROGRESS = 0x1004;
        private WeakReference<OnUploadListener> weakReference;

        MyHandler(OnUploadListener listener) {
            weakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OnUploadListener onUploadListener = weakReference.get();
            switch (msg.what) {
                case BEFORE:
                    if (onUploadListener != null) {
                        onUploadListener.onBeforeUpload();
                    }
                    break;
                case UPLOAD_SUCC:
                    Bundle b_succ = msg.getData();
                    if (b_succ != null) {
                        FileUploadNetWordResult result = (FileUploadNetWordResult) b_succ.getSerializable("result");
                        if (result != null && onUploadListener != null) {
                            onUploadListener.onUploadSuccess(result);
                        }
                    }
                    break;
                case UPLOAD_FAIL:
                    Bundle b_fail = msg.getData();
                    if (b_fail != null) {
                        String error = b_fail.getString("error");
                        if (StringUtil.isNotBlank(error) && onUploadListener != null) {
                            onUploadListener.onUploadFail(error);
                        }
                    }
                    break;
                case AFTER:
                    if (onUploadListener != null)
                        onUploadListener.onAfterUpload();
                    break;
                case PROGRESS:
                    ProgressModel model = (ProgressModel) msg.obj;
                    if (onUploadListener != null && model != null) {
                        onUploadListener.onProgress(model.getCurrentBytes(), model.getContentLength(), model.isDone());
                    }
                    break;
            }
        }
    }


    /**
     * 包装的请求体，处理进度
     */
    public class ProgressRequestBody extends RequestBody {
        //实际的待包装请求体
        private final RequestBody requestBody;
        //进度回调接口
        private final MyHandler myHandler;
        //包装完成的BufferedSink
        private BufferedSink bufferedSink;

        /**
         * 构造函数，赋值
         *
         * @param requestBody 待包装的请求体
         */
        public ProgressRequestBody(RequestBody requestBody, MyHandler myHandler) {
            this.requestBody = requestBody;
            this.myHandler = myHandler;
        }

        /**
         * 重写调用实际的响应体的contentType
         *
         * @return MediaType
         */
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }

        /**
         * 重写调用实际的响应体的contentLength
         *
         * @return contentLength
         * @throws IOException 异常
         */
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }

        /**
         * 重写进行写入
         *
         * @param sink BufferedSink
         * @throws IOException 异常
         */
        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                //包装
                bufferedSink = Okio.buffer(sink(sink));
            }
            //写入
            requestBody.writeTo(bufferedSink);
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();

        }

        /**
         * 写入，回调进度接口
         *
         * @param sink Sink
         * @return Sink
         */
        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {

                //当前写入字节数
                long bytesWritten = 0L;
                //总字节长度，避免多次调用contentLength()方法
                long contentLength = 0L;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        //获得contentLength的值，后续不再调用
                        contentLength = contentLength();
                    }
                    //增加当前写入的字节数
                    bytesWritten += byteCount;
                    //回调
                    if (myHandler != null) {
                        Message message = Message.obtain();
                        message.obj = new ProgressModel(bytesWritten, contentLength, bytesWritten == contentLength);
                        message.what = PROGRESS;
                        myHandler.sendMessage(message);
                    }
                }
            };
        }
    }

    public class ProgressModel implements Serializable {
        //当前读取字节长度
        private long currentBytes;
        //总字节长度
        private long contentLength;
        //是否读取完成
        private boolean done;

        public ProgressModel(long currentBytes, long contentLength, boolean done) {
            this.currentBytes = currentBytes;
            this.contentLength = contentLength;
            this.done = done;
        }

        public long getCurrentBytes() {
            return currentBytes;
        }

        public void setCurrentBytes(long currentBytes) {
            this.currentBytes = currentBytes;
        }

        public long getContentLength() {
            return contentLength;
        }

        public void setContentLength(long contentLength) {
            this.contentLength = contentLength;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        @Override
        public String toString() {
            return "ProgressModel{" +
                    "currentBytes=" + currentBytes +
                    ", contentLength=" + contentLength +
                    ", done=" + done +
                    '}';
        }
    }

    public interface OnUploadListener {
        void onBeforeUpload();

        void onUploadSuccess(FileUploadNetWordResult result);

        void onUploadFail(String error);

        void onAfterUpload();

        void onProgress(long currentBytes, long contentLength, boolean done);
    }
}
