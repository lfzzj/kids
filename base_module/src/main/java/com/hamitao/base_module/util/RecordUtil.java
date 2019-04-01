package com.hamitao.base_module.util;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by linjianwen on 2018/1/12.
 * <p>
 * 使用MediaRecoder 实现音频的录制
 * <p>
 * 录音管理工具
 */

public class RecordUtil {

    private static final String tag = RecordUtil.class.getSimpleName();

    //静态内部类实现单例模式，线程安全，避免同步带来的性能限制。
    private static class RecordHolder {
        private static final RecordUtil INSTANCE = new RecordUtil();
    }

    private RecordUtil() {
        defaultSavePath();
    }

    public static final RecordUtil getInstance() {
        return RecordHolder.INSTANCE;
    }

//    ————————————————————————————————变量对象————————————————————————————————————————————

    private MediaRecorder mediaRecorder;

    private long startTime, endTime;

    //录音日期，格式为：yyyy-mm-dd
    private String recordDate;

    //录音所保存的文件
    private File mAudioFile;

    //录音文件保存位置
    private String savePtah = "";

//    ————————————————————————————————变量对象————————————————————————————————————————————


    /**
     * 设置录音文件的存放路径
     *
     * @param savePath
     */
    public void setSavePath(String savePath) {
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }
        this.savePtah = savePath;
    }


    /**
     * 默认的录音文件存放路径
     */
    public void defaultSavePath() {
        savePtah = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record/";
    }

    /**
     * 获取存放录音的文件夹路径
     */
    public String getRecordDirtory() {
        return savePtah;
    }


    /**
     * 获取录音保存的文件
     */
    public File getRecordFile() {
        return mAudioFile != null ? mAudioFile : null;
    }


    /**
     * 开始录音
     */
    public void startRecord() {
        //创建MediaRecorder对象
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();

            //如果录音文件存在则先删除
            if (mAudioFile != null && mAudioFile.exists()) {
                mAudioFile.delete();
            }
            //创建录音文件.aac格式
//            mAudioFile = new File(savePtah + DateUtil.formatyyyyMMdd(System.currentTimeMillis()) + ".aac");
            mAudioFile = new File(savePtah + DateUtil.formatyyyyMMdd(System.currentTimeMillis()) + ".mp3");
            //创建父文件夹
            mAudioFile.getParentFile().mkdirs();
            try {
                //创建文件
                mAudioFile.createNewFile();
                //配置mMediaRecorder相应参数
                //从麦克风采集声音数据
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置保存文件格式为AAC
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                //设置采样频率,44100是所有安卓机器人都支持的频率,频率越高，音质越好，当然文件越大
                mediaRecorder.setAudioSamplingRate(44100);
                //设置声音数据编码格式,音频通用格式是AAC
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                //设置编码频率
                mediaRecorder.setAudioEncodingBitRate(96000);
                //设置录音保存的文件
                mediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
                //开始录音
                mediaRecorder.prepare();
                mediaRecorder.start();
                //正在录音
                //记录开始录音时间
                startTime = System.currentTimeMillis();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(tag, "录音失败");
            }


        }
    }


    /**
     * 暂停录音
     * 实现思路：参考 https://www.2cto.com/kf/201207/138795.html
     * 1、先录A，保存
     * 2、暂停
     * 3、录B，保存
     * 4、合并A、B
     * 涉及的技术：文件的保存读取、音频的合并等
     */
    public void pauseRecord() {

    }


    /**
     * 停止录音
     */
    public void stopRecord() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            //记录停止时间
            endTime = System.currentTimeMillis();
            //记录录音日期
            recordDate = String.valueOf(System.currentTimeMillis());
            //释放了录音资源
            releaseRecorder();
        }
    }


    /**
     * 释放录音相关资源
     */
    private void releaseRecorder() {
        if (null != mediaRecorder) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    /**
     * 获取录音的时长 格式：mm-ss
     */
    public String getRecordDuration() {
        return DateUtil.formatHHmm(endTime - startTime);
    }


    /**
     * 获取指定路径的所有文件
     */
    public File[] scanRecordFile() {

        File fileDir = new File(savePtah);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        return fileDir.listFiles();
    }

}

