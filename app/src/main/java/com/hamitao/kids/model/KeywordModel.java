package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/4.
 */

public class KeywordModel {


    /**
     * responseDataObj : {"keywordList":["九个小矮人","关键字","八个小矮人","小小童","七个小矮人"]}
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
        private List<String> keywordList;

        public List<String> getKeywordList() {
            return keywordList;
        }

        public void setKeywordList(List<String> keywordList) {
            this.keywordList = keywordList;
        }
    }
}
