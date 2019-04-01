package com.hamitao.base_module.rxbus;

/**
 * Created by linjianwen on 2018/8/28.
 */
public class RxBusContentModel {

    private String status;

    private Object data;

    public RxBusContentModel(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
