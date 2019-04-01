package com.hamitao.base_module.network;

/**
 * Created by linjianwen on 2018/1/5.
 * <p>
 * 请求结果类
 */

public class NetWorkResult {

    //请求成功：success  请求失败： failure
    private String result;

    //失败原因
    private String cause;

    //失败原因描述
    private String description;

    //创建后的终端数据
    private Object responseDataObj;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(Object responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public boolean isOk() {
        return "success".equals(result);
    }
}
