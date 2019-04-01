package com.hamitao.base_module.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.DeleteObjectResult;

/**
 * Created by linjianwen on 2018/10/13.
 */
public class OssTool {

    public OSS mOss;
    private String mBucket;

    public OssTool(OSS mOss, String mBucket) {
        this.mOss = mOss;
        this.mBucket = mBucket;
    }


    /**
     * 删除文件
     */
    public void deleteObject(String objectKey) {
        // 创建删除请求
        DeleteObjectRequest delete = new DeleteObjectRequest(mBucket, objectKey);
        // 异步删除
        OSSAsyncTask deleteTask = mOss.asyncDeleteObject(delete, new OSSCompletedCallback<DeleteObjectRequest, DeleteObjectResult>() {
            @Override
            public void onSuccess(DeleteObjectRequest request, DeleteObjectResult result) {
                Log.d("asyncCopyAndDelObject", "success!");

            }

            @Override
            public void onFailure(DeleteObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }

        });
    }


    public String getUrlByObjectName(final String objectName) {
        try {
            return mOss.presignConstrainedObjectURL(mBucket, objectName, 30 * 60);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "";
    }


}
