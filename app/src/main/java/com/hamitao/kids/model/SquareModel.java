package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/25.
 */

public class SquareModel {

    /**
     * responseDataObj : {"forums":[{"creatorid":"system","creatorname":"system","description":"这是哈蜜淘官方论坛，开放讨论区和技术支持区","forum_id":"forum_hamitao_official","headerimgurl":"","keywords":["官方论坛","技术支持"],"title":"哈蜜淘官方论坛","updatetime":"2018-08-08 08:08:08"}]}
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
        private List<ForumsBean> forums;

        public List<ForumsBean> getForums() {
            return forums;
        }

        public void setForums(List<ForumsBean> forums) {
            this.forums = forums;
        }

        public static class ForumsBean {
            /**
             * creatorid : system
             * creatorname : system
             * description : 这是哈蜜淘官方论坛，开放讨论区和技术支持区
             * forum_id : forum_hamitao_official
             * headerimgurl :
             * keywords : ["官方论坛","技术支持"]
             * title : 哈蜜淘官方论坛
             * updatetime : 2018-08-08 08:08:08
             */

            private String creatorid;
            private String creatorname;
            private String description;
            private String forum_id;
            private String headerimgurl;
            private String title;
            private String updatetime;
            private List<String> keywords;

            public String getCreatorid() {
                return creatorid;
            }

            public void setCreatorid(String creatorid) {
                this.creatorid = creatorid;
            }

            public String getCreatorname() {
                return creatorname;
            }

            public void setCreatorname(String creatorname) {
                this.creatorname = creatorname;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getForum_id() {
                return forum_id;
            }

            public void setForum_id(String forum_id) {
                this.forum_id = forum_id;
            }

            public String getHeaderimgurl() {
                return headerimgurl;
            }

            public void setHeaderimgurl(String headerimgurl) {
                this.headerimgurl = headerimgurl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public List<String> getKeywords() {
                return keywords;
            }

            public void setKeywords(List<String> keywords) {
                this.keywords = keywords;
            }
        }
    }
}
