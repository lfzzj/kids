package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/7/9.
 */

public class ProductInfoModel {


    /**
     * responseDataObj : {"technicalSupportInfo":{"aboutDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","legalNoticesDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","serviceContractURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","userGuideDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html"}}
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
         * technicalSupportInfo : {"aboutDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","legalNoticesDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","serviceContractURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html","userGuideDocURL":"http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html"}
         */

        private TechnicalSupportInfoBean technicalSupportInfo;

        public TechnicalSupportInfoBean getTechnicalSupportInfo() {
            return technicalSupportInfo;
        }

        public void setTechnicalSupportInfo(TechnicalSupportInfoBean technicalSupportInfo) {
            this.technicalSupportInfo = technicalSupportInfo;
        }

        public static class TechnicalSupportInfoBean {
            /**
             * aboutDocURL : http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html
             * legalNoticesDocURL : http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html
             * serviceContractURL : http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html
             * userGuideDocURL : http://cloud.kidknow.net:8888/appdoc/about_hamitao_zh.html
             */

            private String aboutDocURL;
            private String legalNoticesDocURL;
            private String serviceContractURL;
            private String userGuideDocURL;

            public String getAboutDocURL() {
                return aboutDocURL;
            }

            public void setAboutDocURL(String aboutDocURL) {
                this.aboutDocURL = aboutDocURL;
            }

            public String getLegalNoticesDocURL() {
                return legalNoticesDocURL;
            }

            public void setLegalNoticesDocURL(String legalNoticesDocURL) {
                this.legalNoticesDocURL = legalNoticesDocURL;
            }

            public String getServiceContractURL() {
                return serviceContractURL;
            }

            public void setServiceContractURL(String serviceContractURL) {
                this.serviceContractURL = serviceContractURL;
            }

            public String getUserGuideDocURL() {
                return userGuideDocURL;
            }

            public void setUserGuideDocURL(String userGuideDocURL) {
                this.userGuideDocURL = userGuideDocURL;
            }
        }
    }
}
