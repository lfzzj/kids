package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/4/8.
 */

public class VersionModel {


    /**
     * responseDataObj : {"uptodateVersion":{"appversion":"1.0.0","force":true,"osversion":">10.3","tip":"更新内容","title":"标题","url":"aaa/bbb/ccc","urltype":"osskey"}}
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
         * uptodateVersion : {"appversion":"1.0.0","force":true,"osversion":">10.3","tip":"更新内容","title":"标题","url":"aaa/bbb/ccc","urltype":"osskey"}
         */

        private UptodateVersionBean uptodateVersion;

        public UptodateVersionBean getUptodateVersion() {
            return uptodateVersion;
        }

        public void setUptodateVersion(UptodateVersionBean uptodateVersion) {
            this.uptodateVersion = uptodateVersion;
        }

        public static class UptodateVersionBean {

            /**
             * appversion : 1.0.0
             * force : true
             * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/aaa/bbb/ccc?Expires=1532418485&OSSAccessKeyId=STS.NJLtrySiGQXVKCW5QCWFgL7Mw&Signature=pVUbpZmE%2BMUNjlLgWXecn82S2pw%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4n5P8jNvrZm5pq9SWXm0VEWW8lLo%2FLmlTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPI7FA0S6F6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABgXKyxlxDSTZ%2BMZiJkCEqcD9xWwc61aaytfyo11v%2FykbKQ5WrVnP5%2FJixznmr4AmX%2FGE89SVodmFxUPYKMD4%2FVh8tvkyWJz5AnCl9DQNt%2BjrjzaabdW0ci86fCOC2p%2BBcuuams4IWBDmz7pnxufznii8QwOOR7yVyJtlvNyw0wXk%3D
             * osversion : >10.3
             * tip : 更新内容
             * title : 标题
             * url : aaa/bbb/ccc
             * urltype : keyonoss
             * versioncode : 1
             * vendor:hamitao
             */

            private String appversion;
            private boolean force;
            private String httpURL;
            private String osversion;
            private String tip;
            private String title;
            private String url;
            private String urltype;
            private String versioncode;

            public String getAppversion() {
                return appversion;
            }

            public void setAppversion(String appversion) {
                this.appversion = appversion;
            }

            public boolean isForce() {
                return force;
            }

            public void setForce(boolean force) {
                this.force = force;
            }

            public String getHttpURL() {
                return httpURL;
            }

            public void setHttpURL(String httpURL) {
                this.httpURL = httpURL;
            }

            public String getOsversion() {
                return osversion;
            }

            public void setOsversion(String osversion) {
                this.osversion = osversion;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrltype() {
                return urltype;
            }

            public void setUrltype(String urltype) {
                this.urltype = urltype;
            }

            public String getVersioncode() {
                return versioncode;
            }

            public void setVersioncode(String versioncode) {
                this.versioncode = versioncode;
            }
        }
    }
}
