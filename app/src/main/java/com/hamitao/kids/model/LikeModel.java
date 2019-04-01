package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/5/19.
 */

public class LikeModel {

    /**
     * responseDataObj : {"likecount":1,"mylike_id":"likes_3f261d1e-3df0-4448-9fd9-f8d39b717835"}
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
        /**
         * likecount : 1
         * mylike_id : likes_3f261d1e-3df0-4448-9fd9-f8d39b717835
         */

        private int likecount;
        private String mylike_id;

        public int getLikecount() {
            return likecount;
        }

        public void setLikecount(int likecount) {
            this.likecount = likecount;
        }

        public String getMylike_id() {
            return mylike_id;
        }

        public void setMylike_id(String mylike_id) {
            this.mylike_id = mylike_id;
        }
    }
}
