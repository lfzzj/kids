package com.hamitao.base_module.network;

/**
 * Created by linjianwen on 2018/1/5.
 *
 *  自定义错误
 */

public class Error {
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
