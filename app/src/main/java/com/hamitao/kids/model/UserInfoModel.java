package com.hamitao.kids.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linjianwen on 2018/4/3.
 */

public class UserInfoModel {


    /**
     * responseDataObj : {"childInfos":[{"birthday":"2018-04-17","name":"喂得","sex":"boy"}],"myInfo":{"myheaderpic":"appstorage/testdir/18316588439/Picture/header.png","myheaderpic_httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/testdir","mynickname":"詹姆斯"}}
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

    public static class ResponseDataObjBean implements Serializable {
        /**
         * childInfos : [{"birthday":"2018-04-17","name":"喂得","sex":"boy"}]
         * myInfo : {"myheaderpic":"appstorage/testdir/18316588439/Picture/header.png","myheaderpic_httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/testdir","mynickname":"詹姆斯"}
         */

        private MyInfoBean myInfo;
        private List<ChildInfosBean> childInfos;

        public MyInfoBean getMyInfo() {
            return myInfo;
        }

        public void setMyInfo(MyInfoBean myInfo) {
            this.myInfo = myInfo;
        }

        public List<ChildInfosBean> getChildInfos() {
            return childInfos;
        }

        public void setChildInfos(List<ChildInfosBean> childInfos) {
            this.childInfos = childInfos;
        }

        public static class MyInfoBean implements Serializable {
            /**
             * myheaderpic : appstorage/testdir/18316588439/Picture/header.png
             * myheaderpic_httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/testdir
             * mynickname : 詹姆斯
             */

            private String myheaderpic;
            private String myheaderpic_httpURL;
            private String mynickname;

            public String getMyheaderpic() {
                return myheaderpic;
            }

            public void setMyheaderpic(String myheaderpic) {
                this.myheaderpic = myheaderpic;
            }

            public String getMyheaderpic_httpURL() {
                return myheaderpic_httpURL;
            }

            public void setMyheaderpic_httpURL(String myheaderpic_httpURL) {
                this.myheaderpic_httpURL = myheaderpic_httpURL;
            }

            public String getMynickname() {
                return mynickname;
            }

            public void setMynickname(String mynickname) {
                this.mynickname = mynickname;
            }
        }

        public static class ChildInfosBean implements Serializable {
            /**
             * birthday : 2018-04-17
             * name : 喂得
             * sex : boy
             */

            private String birthday;
            private String name;
            private String sex;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }
    }
}
