package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/7/28.
 */

public class CategoryModel {

    /**
     * responseDataObj : {"contentCategories":[{"categoryname_level0":"年龄","categoryname_level1":["0-1岁","1-2岁","2-3岁","3-5岁","5-6岁","6+岁"]},{"categoryname_level0":"科目","categoryname_level1":["语文","数学","英语","社会","科学","艺术"]},{"categoryname_level0":"内容","categoryname_level1":["国学","绘本","动画","儿歌","故事","常识"]},{"categoryname_level0":"情景","categoryname_level1":["睡觉","起床","习惯","健康","安全","吃饭"]}]}
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
        private List<ContentCategoriesBean> contentCategories;

        public List<ContentCategoriesBean> getContentCategories() {
            return contentCategories;
        }

        public void setContentCategories(List<ContentCategoriesBean> contentCategories) {
            this.contentCategories = contentCategories;
        }

        public static class ContentCategoriesBean {
            /**
             * categoryname_level0 : 年龄
             * categoryname_level1 : ["0-1岁","1-2岁","2-3岁","3-5岁","5-6岁","6+岁"]
             */

            private String categoryname_level0;
            private List<String> categoryname_level1;

            public String getCategoryname_level0() {
                return categoryname_level0;
            }

            public void setCategoryname_level0(String categoryname_level0) {
                this.categoryname_level0 = categoryname_level0;
            }

            public List<String> getCategoryname_level1() {
                return categoryname_level1;
            }

            public void setCategoryname_level1(List<String> categoryname_level1) {
                this.categoryname_level1 = categoryname_level1;
            }
        }
    }
}
