package com.hamitao.base_module.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/9.
 */

public class DeviceRelationModel {


    /**
     * responseDataObj : {"relation":{"circleInfos":[],"customerInfos":[],"terminalInfos":[{"channelid_listen_on_pushserver":"5a7862688d6349d2bce06467a1bcf624","comment":"","dagcluster":"hamitao_kidsrobot","deviceid":"333333","hosttype":"","mygatewayserver":"","mygatewayserver1":"","mygatewayserverhttpport":"8080","mygatewayserverhttpport1":"8080","nickname":"小明","socketport":"","terminalid":"sz10001_kidsrobot_5a7862688d6349d2bce06467a1bcf624","terminaltype":"kidsrobot","updatetime":"2018-04-09 19:10:14.275"}]}}
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
         * relation : {"circleInfos":[],"customerInfos":[],"terminalInfos":[{"channelid_listen_on_pushserver":"5a7862688d6349d2bce06467a1bcf624","comment":"","dagcluster":"hamitao_kidsrobot","deviceid":"333333","hosttype":"","mygatewayserver":"","mygatewayserver1":"","mygatewayserverhttpport":"8080","mygatewayserverhttpport1":"8080","nickname":"小明","socketport":"","terminalid":"sz10001_kidsrobot_5a7862688d6349d2bce06467a1bcf624","terminaltype":"kidsrobot","updatetime":"2018-04-09 19:10:14.275"}]}
         */

        private RelationBean relation;

        public RelationBean getRelation() {
            return relation;
        }

        public void setRelation(RelationBean relation) {
            this.relation = relation;
        }

        public static class RelationBean {
            private List<?> circleInfos;
            private List<CustomerInfosBean> customerInfos;
            private List<TerminalInfosBean> terminalInfos;

            public List<?> getCircleInfos() {
                return circleInfos;
            }

            public void setCircleInfos(List<?> circleInfos) {
                this.circleInfos = circleInfos;
            }

            public List<CustomerInfosBean> getCustomerInfos() {
                return customerInfos;
            }

            public void setCustomerInfos(List<CustomerInfosBean> customerInfos) {
                this.customerInfos = customerInfos;
            }

            public List<TerminalInfosBean> getTerminalInfos() {
                return terminalInfos;
            }

            public void setTerminalInfos(List<TerminalInfosBean> terminalInfos) {
                this.terminalInfos = terminalInfos;
            }

            public static class TerminalInfosBean {

                /**
                 * bindname : 小明
                 * channelid_listen_on_pushserver : 5a7862688d6349d2bce06467a1bcf624
                 * comment :
                 * dagcluster : hamitao_kidsrobot
                 * deviceid : 333333
                 * hosttype :
                 * mygatewayserver :
                 * mygatewayserver1 :
                 * mygatewayserverhttpport : 8080
                 * mygatewayserverhttpport1 : 8080
                 * socketport :
                 * terminalid : sz10001_kidsrobot_5a7862688d6349d2bce06467a1bcf624
                 * terminaltype : kidsrobot
                 * updatetime : 2018-04-12 11:14:37.312
                 */

                private String bindname;
                private String channelid_listen_on_pushserver;
                private String comment;
                private String dagcluster;
                private String deviceid;
                private String hosttype;
                private String mygatewayserver;
                private String mygatewayserver1;
                private String mygatewayserverhttpport;
                private String mygatewayserverhttpport1;
                private String socketport;
                private String terminalid;
                private String terminaltype;
                private String vendor;

                public String getVendor() {
                    return vendor;
                }

                public void setVendor(String vendor) {
                    this.vendor = vendor;
                }

                private String updatetime;

                public String getBindname() {
                    return bindname;
                }

                public void setBindname(String bindname) {
                    this.bindname = bindname;
                }

                public String getChannelid_listen_on_pushserver() {
                    return channelid_listen_on_pushserver;
                }

                public void setChannelid_listen_on_pushserver(String channelid_listen_on_pushserver) {
                    this.channelid_listen_on_pushserver = channelid_listen_on_pushserver;
                }

                public String getComment() {
                    return comment;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }

                public String getDagcluster() {
                    return dagcluster;
                }

                public void setDagcluster(String dagcluster) {
                    this.dagcluster = dagcluster;
                }

                public String getDeviceid() {
                    return deviceid;
                }

                public void setDeviceid(String deviceid) {
                    this.deviceid = deviceid;
                }

                public String getHosttype() {
                    return hosttype;
                }

                public void setHosttype(String hosttype) {
                    this.hosttype = hosttype;
                }

                public String getMygatewayserver() {
                    return mygatewayserver;
                }

                public void setMygatewayserver(String mygatewayserver) {
                    this.mygatewayserver = mygatewayserver;
                }

                public String getMygatewayserver1() {
                    return mygatewayserver1;
                }

                public void setMygatewayserver1(String mygatewayserver1) {
                    this.mygatewayserver1 = mygatewayserver1;
                }

                public String getMygatewayserverhttpport() {
                    return mygatewayserverhttpport;
                }

                public void setMygatewayserverhttpport(String mygatewayserverhttpport) {
                    this.mygatewayserverhttpport = mygatewayserverhttpport;
                }

                public String getMygatewayserverhttpport1() {
                    return mygatewayserverhttpport1;
                }

                public void setMygatewayserverhttpport1(String mygatewayserverhttpport1) {
                    this.mygatewayserverhttpport1 = mygatewayserverhttpport1;
                }

                public String getSocketport() {
                    return socketport;
                }

                public void setSocketport(String socketport) {
                    this.socketport = socketport;
                }

                public String getTerminalid() {
                    return terminalid;
                }

                public void setTerminalid(String terminalid) {
                    this.terminalid = terminalid;
                }

                public String getTerminaltype() {
                    return terminaltype;
                }

                public void setTerminaltype(String terminaltype) {
                    this.terminaltype = terminaltype;
                }

                public String getUpdatetime() {
                    return updatetime;
                }

                public void setUpdatetime(String updatetime) {
                    this.updatetime = updatetime;
                }
            }


            public static class CustomerInfosBean {

                /**
                 * bindname : 妈妈
                 * channelid_listen_on_pushserver : f934668ba4ee4bb2a1a7e92a8efba992
                 * customerid : sz00003_customer_f934668ba4ee4bb2a1a7e92a8efba992
                 * loginname : ssssss
                 * name : 111111mocktestname11111
                 * updatetime : 2018-04-12 11:14:37.289
                 */

                private String bindname;
                private String channelid_listen_on_pushserver;
                private String customerid;
                private String loginname;
                private String name;
                private String updatetime;

                public String getBindname() {
                    return bindname;
                }

                public void setBindname(String bindname) {
                    this.bindname = bindname;
                }

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

                public String getLoginname() {
                    return loginname;
                }

                public void setLoginname(String loginname) {
                    this.loginname = loginname;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUpdatetime() {
                    return updatetime;
                }

                public void setUpdatetime(String updatetime) {
                    this.updatetime = updatetime;
                }
            }


        }
    }
}
