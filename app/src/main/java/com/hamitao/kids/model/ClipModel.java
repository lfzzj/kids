package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/5/8.
 */

import java.util.List;

/**
 * 收藏夹
 */
public class ClipModel {


    /**
     * responseDataObj : {"favoriteCategorys":["所在的收藏夹"]}
     * result : success
     */

    private ResponseDataObjBean responseDataObj;
    private String result;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ResponseDataObjBean {
        private List<String> favoriteCategorys;

        public List<String> getFavoriteCategorys() {
            return favoriteCategorys;
        }

        public void setFavoriteCategorys(List<String> favoriteCategorys) {
            this.favoriteCategorys = favoriteCategorys;
        }
    }
}
