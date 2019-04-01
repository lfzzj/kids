package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/16.
 */

public class ShareModel {


    /**
     * responseDataObj : {"topics":[{"creatorheaderimgurl":"创建者头像URL","creatorheaderimgurlHttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/%E5%88%9B%E5%BB%BA%E8%80%85%E5%A4%B4%E5%83%8FURL?Expires=1525783566&OSSAccessKeyId=STS.CqTvkRH14vZ7FeYiGNgsMiXvu&Signature=qpgtacO%2B6qsB7lFOxCN%2B0gjwK3w%3D&security-token=CAISjAJ1q6Ft5B2yfSjIorLhPdHmpe4VwZjcREPojUcba%2Fxhhp3dlzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPQdtv9VGF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABBSlTsfWXqDkYvbkrDlRzf6yHAoxtjgmXdDVEPrV%2FolooDbAJqAMskAHMlKhIyn3ZawkJ5XjK4Oir0tcLgIOdwkD6AoIIt%2B2Rhv%2BcGroszL39urNzBB79T57kbg41A%2BWMAIenwwOckJWaCLT%2FOghUyMYgOszC4wkmUDfRz5K9hEA%3D","creatorid":"sz00001_customer_78d5697acb8848e987fc9c6c0a9b9b4b","creatorname":"creatorname","creatornickname":"创建者的昵称","description":"description","forum_id":"forum_id","headerimgurl":"headerimgurl","headerimgurlHttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/headerimgurl?Expires=1525783567&OSSAccessKeyId=STS.CAitMDmCGLuF8s7oXJpw1FLPr&Signature=jqu3njEQ%2FzPnioMrqpf6jqO9ZlY%3D&security-token=CAISjAJ1q6Ft5B2yfSjIooLcP%2FfwgJxm%2B7etOlWGi1gffPgdqYn7kDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPWd5v9VGF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABT6xRrmor%2FxCBRc1ViphpeSHwmJzCfqetM%2Bb4rS6xtL%2BjG7%2BntvB3FH%2BaWiX2Rj1Mit6Zz4KdBAfYK76J5Nd2LWy5sVZXVb1X9T0ffbOkso7jETMmwQV3JoeBwfBRjCfppvUDN7f%2F7Q0nH2XMN19pA%2FM5DsEwHvXl5k9v2z7VBgs%3D","info":"contentid","infotype":"contentid","keywords":["AAA","BBB"],"likecount":1,"mylike_id":"likes_4ca3cd72-c682-4f7c-a0c1-58c09dab713e","title":"修改后的title","topic_id":"topic_7c152496-a356-4c00-8179-f2367edc0c4e","updatetime":"2018-05-08T11:49:51Z"}]}
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
        private List<TopicsBean> topics;

        public List<TopicsBean> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicsBean> topics) {
            this.topics = topics;
        }

        public static class TopicsBean {
            /**
             * creatorheaderimgurl : 创建者头像URL
             * creatorheaderimgurlHttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/%E5%88%9B%E5%BB%BA%E8%80%85%E5%A4%B4%E5%83%8FURL?Expires=1525783566&OSSAccessKeyId=STS.CqTvkRH14vZ7FeYiGNgsMiXvu&Signature=qpgtacO%2B6qsB7lFOxCN%2B0gjwK3w%3D&security-token=CAISjAJ1q6Ft5B2yfSjIorLhPdHmpe4VwZjcREPojUcba%2Fxhhp3dlzz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPQdtv9VGF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABBSlTsfWXqDkYvbkrDlRzf6yHAoxtjgmXdDVEPrV%2FolooDbAJqAMskAHMlKhIyn3ZawkJ5XjK4Oir0tcLgIOdwkD6AoIIt%2B2Rhv%2BcGroszL39urNzBB79T57kbg41A%2BWMAIenwwOckJWaCLT%2FOghUyMYgOszC4wkmUDfRz5K9hEA%3D
             * creatorid : sz00001_customer_78d5697acb8848e987fc9c6c0a9b9b4b
             * creatorname : creatorname
             * creatornickname : 创建者的昵称
             * description : description
             * forum_id : forum_id
             * headerimgurl : headerimgurl
             * headerimgurlHttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/headerimgurl?Expires=1525783567&OSSAccessKeyId=STS.CAitMDmCGLuF8s7oXJpw1FLPr&Signature=jqu3njEQ%2FzPnioMrqpf6jqO9ZlY%3D&security-token=CAISjAJ1q6Ft5B2yfSjIooLcP%2FfwgJxm%2B7etOlWGi1gffPgdqYn7kDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPWd5v9VGF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABT6xRrmor%2FxCBRc1ViphpeSHwmJzCfqetM%2Bb4rS6xtL%2BjG7%2BntvB3FH%2BaWiX2Rj1Mit6Zz4KdBAfYK76J5Nd2LWy5sVZXVb1X9T0ffbOkso7jETMmwQV3JoeBwfBRjCfppvUDN7f%2F7Q0nH2XMN19pA%2FM5DsEwHvXl5k9v2z7VBgs%3D
             * info : contentid
             * infotype : contentid
             * keywords : ["AAA","BBB"]
             * likecount : 1
             * mylike_id : likes_4ca3cd72-c682-4f7c-a0c1-58c09dab713e
             * title : 修改后的title
             * topic_id : topic_7c152496-a356-4c00-8179-f2367edc0c4e
             * updatetime : 2018-05-08T11:49:51Z
             */

            private String creatorheaderimgurl;
            private String creatorheaderimgurlHttpURL;
            private String creatorid;
            private String creatorname;
            private String creatornickname;
            private String description;
            private String forum_id;
            private String headerimgurl;
            private String headerimgurlHttpURL;
            private String info;
            private String infotype;
            private int likecount;
            private String mylike_id;
            private String title;
            private String topic_id;
            private String updatetime;
            private List<String> keywords;

            public String getCreatorheaderimgurl() {
                return creatorheaderimgurl;
            }

            public void setCreatorheaderimgurl(String creatorheaderimgurl) {
                this.creatorheaderimgurl = creatorheaderimgurl;
            }

            public String getCreatorheaderimgurlHttpURL() {
                return creatorheaderimgurlHttpURL;
            }

            public void setCreatorheaderimgurlHttpURL(String creatorheaderimgurlHttpURL) {
                this.creatorheaderimgurlHttpURL = creatorheaderimgurlHttpURL;
            }

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

            public String getCreatornickname() {
                return creatornickname;
            }

            public void setCreatornickname(String creatornickname) {
                this.creatornickname = creatornickname;
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

            public String getHeaderimgurlHttpURL() {
                return headerimgurlHttpURL;
            }

            public void setHeaderimgurlHttpURL(String headerimgurlHttpURL) {
                this.headerimgurlHttpURL = headerimgurlHttpURL;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getInfotype() {
                return infotype;
            }

            public void setInfotype(String infotype) {
                this.infotype = infotype;
            }

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTopic_id() {
                return topic_id;
            }

            public void setTopic_id(String topic_id) {
                this.topic_id = topic_id;
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
