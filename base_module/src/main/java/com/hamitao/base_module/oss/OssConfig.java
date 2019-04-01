package com.hamitao.base_module.oss;

import com.hamitao.base_module.Constant;

/**
 * Created by linjianwen on 2018/2/27.
 * 阿里云配置
 */

public class OssConfig {


    // To run the sample correctly, the following variables must have valid values.
    // The endpoint value below is just the example. Please use proper value according to your region

    // 访问的endpoint地址
    public static final String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    //callback 测试地址
    public static final String callbackAddress = "http://oss-demo.aliyuncs.com:23450";

    // STS 鉴权服务器地址，使用前请参照文档 https://help.aliyun.com/document_detail/31920.html 介绍配置STS 鉴权服务器地址。
    // 或者根据工程sts_local_server目录中本地鉴权服务脚本代码启动本地STS 鉴权服务器。详情参见sts_local_server 中的脚本内容。
    public static final String STSSERVER = "http://cloud.kidknow.net:8080/proxy/security/querykeyonoss";//STS 地址

    public static final String uploadFilePath = ""; //本地文件上传地址
    public static final String bucket = Constant.bucket;
    public static final String uploadObject = "上传object名称";
    public static final String downloadObject = "下载object名称";

    public static final int DOWNLOAD_SUC = 1; //下载成功
    public static final int DOWNLOAD_Fail = 2;//下载失败
    public static final int UPLOAD_SUC = 3;//上传成功
    public static final int UPLOAD_Fail = 4;//上传失败
    public static final int UPLOAD_PROGRESS = 5;
    public static final int LIST_SUC = 6;
    public static final int HEAD_SUC = 7;
    public static final int RESUMABLE_SUC = 8;
    public static final int SIGN_SUC = 9;
    public static final int BUCKET_SUC = 10;
    public static final int GET_STS_SUC = 11;
    public static final int MULTIPART_SUC = 12;
    public static final int STS_TOKEN_SUC = 13;
    public static final int FAIL = 9999;
    public static final int REQUESTCODE_AUTH = 10111;
    public static final int REQUESTCODE_LOCALPHOTOS = 10112;


    public static final int MESSAGE_UPLOAD_2_OSS = 10002;
}
