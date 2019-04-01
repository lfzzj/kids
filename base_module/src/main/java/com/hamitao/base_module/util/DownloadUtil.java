package com.hamitao.base_module.util;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.hamitao.base_module.R;
import com.hamitao.base_module.base.BaseApplication;

import java.io.File;

public class DownloadUtil {

    private DownloadManager downloadManager;
    private Context context;
    private OnDownloadListener onDownloadListener;

    public DownloadUtil(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        context.registerReceiver(downloadReceiver, filter);
    }

    /**
     * 销毁,主要是注销BroadcastReceiver
     */
    public void destroy() {
        context.unregisterReceiver(downloadReceiver);
    }

    /**
     * 开始下载,公共文件
     *
     * @param url      下载的url
     * @param path     不需要再加上Environment.getExternalStorageDirectory(),只需要后面的路径,例如"/SugarLover/Downloader/pic/"
     * @param fileName 文件名,带后缀
     */
    public void downloadPublicFile(String url, String path, String fileName) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(path, fileName);
        //获取下载管理器
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }


    public void downloadAPK(String url, String path, String fileName) {
        //已存在 -- 删除
        File apkFile = new File(context.getExternalFilesDir(path), fileName);
        if (apkFile != null && apkFile.exists()) {
            apkFile.delete();
        }
        //下载 （AppCont.Update_URL 是你的app下载地址）
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置title
        request.setTitle(context.getResources().getString(R.string.app_name));
        // 完成后显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置存储路径 --这个是应用专用的,软件卸载后，下载的文件将随着卸载全部被删除
        request.setDestinationInExternalFilesDir(context, path, fileName);
//        request.setDestinationInExternalFilesDir(context, path, fileName);
        //获取下载管理器
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //设置 文件类型
        request.setMimeType("application/vnd.android.package-archive");
        downloadManager.enqueue(request);
    }




    /**
     * 下载文件,私有文件,只有应用本身可用
     *
     * @param url      下载的url
     * @param path     不需要再加上Environment.getExternalStorageDirectory(),只需要后面的路径,例如"/SugarLover/Downloader/pic/"
     * @param fileName 文件名,带后缀
     */
    public void downloadPrivateFile(String url, String path, String fileName) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalFilesDir(BaseApplication.getInstance(), Environment.DIRECTORY_PICTURES, path + "/" + fileName);
        //获取下载管理器
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                checkDownloadStatus(id);
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Toast.makeText(context, "别瞎点！！！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 检查下载状态
     *
     * @param id 当前下载任务的id
     */
    private void checkDownloadStatus(long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    LogUtil.d("download", ">>>下载暂停");
                    if (onDownloadListener != null)
                        onDownloadListener.onPause(downloadManager);
                    break;
                case DownloadManager.STATUS_PENDING:
                    LogUtil.d("download", ">>>下载延迟");
                    if (onDownloadListener != null)
                        onDownloadListener.onPending(downloadManager);
                    break;
                case DownloadManager.STATUS_RUNNING:
                    LogUtil.d("download", ">>>正在下载");
                    if (onDownloadListener != null)
                        onDownloadListener.onRunning(downloadManager);
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    String address = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    LogUtil.d("download", ">>>下载完成 address:" + address);
                    if (onDownloadListener != null)
                        onDownloadListener.onSuccess(address);

                    installApk(id);
                    //下载完成安装APK
//                    downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + versionName;
                    break;
                case DownloadManager.STATUS_FAILED:
                    LogUtil.d("download", ">>>下载失败");
                    if (onDownloadListener != null)
                        onDownloadListener.onFail(downloadManager);
                    break;
            }
        }
    }



    /**
     * 根据任务id打开安装界面
     *
     * @param downloadApkId
     */
    public void installApk(long downloadApkId) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadApkId);//获取下载文件路径
        if (downloadFileUri != null) {
            if (Build.VERSION.SDK_INT >= 24){ //7.0以上版本
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            try {
                context.startActivity(install);//打开安装界面
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public BroadcastReceiver getDownloadReceiver() {
        return downloadReceiver;
    }

    public interface OnDownloadListener {
        void onPause(DownloadManager downloadManager);

        void onPending(DownloadManager downloadManager);

        void onRunning(DownloadManager downloadManager);

        void onSuccess(String path);

        void onFail(DownloadManager downloadManager);
    }
}