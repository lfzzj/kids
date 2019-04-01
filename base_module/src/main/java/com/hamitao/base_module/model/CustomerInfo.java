package com.hamitao.base_module.model;

/**
 * Created by linjianwen on 2018/3/13.
 */

public class CustomerInfo {


    /**
     * responseDataObj : {"customerInfo":{"channelid_listen_on_pushserver":"sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992","customerid":"sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992","token":"2D1A07540003090B4E444D16529CCFFF5311064F431C1D54524D111A0C44501A1E434410166D7A495356572F490A147F124B5C41545A5D8DDDB845045A064245084509170F564B82B8E74620045A58474802451900520A1A0A13541D0504491243445E565642505E5F1247405F5F"}}
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
         * customerInfo : {"channelid_listen_on_pushserver":"sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992","customerid":"sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992","token":"2D1A07540003090B4E444D16529CCFFF5311064F431C1D54524D111A0C44501A1E434410166D7A495356572F490A147F124B5C41545A5D8DDDB845045A064245084509170F564B82B8E74620045A58474802451900520A1A0A13541D0504491243445E565642505E5F1247405F5F"}
         */

        private CustomerInfoBean customerInfo;

        public CustomerInfoBean getCustomerInfo() {
            return customerInfo;
        }

        public void setCustomerInfo(CustomerInfoBean customerInfo) {
            this.customerInfo = customerInfo;
        }

        public static class CustomerInfoBean {
            /**
             * channelid_listen_on_pushserver : sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992
             * customerid : sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992
             * token : 2D1A07540003090B4E444D16529CCFFF5311064F431C1D54524D111A0C44501A1E434410166D7A495356572F490A147F124B5C41545A5D8DDDB845045A064245084509170F564B82B8E74620045A58474802451900520A1A0A13541D0504491243445E565642505E5F1247405F5F
             */

            private String channelid_listen_on_pushserver;
            private String customerid = "";
            private String token;

            public String getChannelid_listen_on_pushserver() {
                return channelid_listen_on_pushserver;
            }

            public void setChannelid_listen_on_pushserver(String channelid_listen_on_pushserver) {
                this.channelid_listen_on_pushserver = channelid_listen_on_pushserver;
            }

            public String getCustomerid() {
                return customerid;
            }

            public void setCustomerid(String customerid) {
                this.customerid = customerid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
