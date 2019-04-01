package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/7/30.
 */

public class BindDeviceModel {


    /**
     * responseDataObj : {"terminalInfo":{"channelid_listen_on_pushserver":"a360f61681f24715ae6b196673dcb37a","comment":"","dagcluster":"hamitao_kidsrobot","deviceid":"3333333333","hosttype":"","mygatewayserver":"","mygatewayserver1":"","mygatewayserverhttpport":"8080","mygatewayserverhttpport1":"8080","nickname":"","socketport":"","terminalid":"sz10002_kidsrobot_a360f61681f24715ae6b196673dcb37a","terminaltype":"kidsrobot","token":"2A0A02490C0B051D1D1613405CDC8FBF1351464F540C1C4D544E15040C44501A1E424410166C7A414F4150324B0D09542B135C43520A53DE8AB411075C5017465C150D16085E40D5B6B5132606015242125E47120B45190F1B124400010C111D4145575E55455A5E571345455A574E","updatetime":"2018-07-30 03:57:53.858"}}
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
         * terminalInfo : {"channelid_listen_on_pushserver":"a360f61681f24715ae6b196673dcb37a","comment":"","dagcluster":"hamitao_kidsrobot","deviceid":"3333333333","hosttype":"","mygatewayserver":"","mygatewayserver1":"","mygatewayserverhttpport":"8080","mygatewayserverhttpport1":"8080","nickname":"","socketport":"","terminalid":"sz10002_kidsrobot_a360f61681f24715ae6b196673dcb37a","terminaltype":"kidsrobot","token":"2A0A02490C0B051D1D1613405CDC8FBF1351464F540C1C4D544E15040C44501A1E424410166C7A414F4150324B0D09542B135C43520A53DE8AB411075C5017465C150D16085E40D5B6B5132606015242125E47120B45190F1B124400010C111D4145575E55455A5E571345455A574E","updatetime":"2018-07-30 03:57:53.858"}
         */

        private TerminalInfoBean terminalInfo;

        public TerminalInfoBean getTerminalInfo() {
            return terminalInfo;
        }

        public void setTerminalInfo(TerminalInfoBean terminalInfo) {
            this.terminalInfo = terminalInfo;
        }

        public static class TerminalInfoBean {
            /**
             * channelid_listen_on_pushserver : a360f61681f24715ae6b196673dcb37a
             * comment :
             * dagcluster : hamitao_kidsrobot
             * deviceid : 3333333333
             * hosttype :
             * mygatewayserver :
             * mygatewayserver1 :
             * mygatewayserverhttpport : 8080
             * mygatewayserverhttpport1 : 8080
             * nickname :
             * socketport :
             * terminalid : sz10002_kidsrobot_a360f61681f24715ae6b196673dcb37a
             * terminaltype : kidsrobot
             * token : 2A0A02490C0B051D1D1613405CDC8FBF1351464F540C1C4D544E15040C44501A1E424410166C7A414F4150324B0D09542B135C43520A53DE8AB411075C5017465C150D16085E40D5B6B5132606015242125E47120B45190F1B124400010C111D4145575E55455A5E571345455A574E
             * updatetime : 2018-07-30 03:57:53.858
             */

            private String channelid_listen_on_pushserver;
            private String comment;
            private String dagcluster;
            private String deviceid;
            private String hosttype;
            private String mygatewayserver;
            private String mygatewayserver1;
            private String mygatewayserverhttpport;
            private String mygatewayserverhttpport1;
            private String nickname;
            private String socketport;
            private String terminalid;
            private String terminaltype;
            private String token;
            private String updatetime;

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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
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
