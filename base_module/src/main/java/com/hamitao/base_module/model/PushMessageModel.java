package com.hamitao.base_module.model;

/**
 * Created by linjianwen on 2018/4/13.
 */

public class PushMessageModel {

    /**
     * responseDataObj : {"channelMsg":{"actionResult":{"action":"TAKE_PHOTO","contents":"{\"GSMIntensity\":0,\"battery\":83,\"deviceState\":true,\"deviceid\":\"1234567\",\"direction\":0,\"gpslocatestatus\":0,\"horizonalaccuracy\":0,\"mileage\":0,\"precision\":0,\"satellitenum\":0,\"speed\":0,\"verticalaccuracy\":0}","httpURL":"","urltype":"keyonoss"},"source_channelid":"f03721ab13bb4ec8ad58db508d7a35eb","target_channelid":"5fe6ed4db733476296ff4e891a3114b6"}}
     */

    private ResponseDataObjBean responseDataObj;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public static class ResponseDataObjBean {
        /**
         * channelMsg : {"actionResult":{"action":"TAKE_PHOTO","contents":"{\"GSMIntensity\":0,\"battery\":83,\"deviceState\":true,\"deviceid\":\"1234567\",\"direction\":0,\"gpslocatestatus\":0,\"horizonalaccuracy\":0,\"mileage\":0,\"precision\":0,\"satellitenum\":0,\"speed\":0,\"verticalaccuracy\":0}","httpURL":"","urltype":"keyonoss"},"source_channelid":"f03721ab13bb4ec8ad58db508d7a35eb","target_channelid":"5fe6ed4db733476296ff4e891a3114b6"}
         */

        private ChannelMsgBean channelMsg;

        public ChannelMsgBean getChannelMsg() {
            return channelMsg;
        }

        public void setChannelMsg(ChannelMsgBean channelMsg) {
            this.channelMsg = channelMsg;
        }

        public static class ChannelMsgBean {
            /**
             * actionResult : {"action":"TAKE_PHOTO","contents":"{\"GSMIntensity\":0,\"battery\":83,\"deviceState\":true,\"deviceid\":\"1234567\",\"direction\":0,\"gpslocatestatus\":0,\"horizonalaccuracy\":0,\"mileage\":0,\"precision\":0,\"satellitenum\":0,\"speed\":0,\"verticalaccuracy\":0}","httpURL":"","urltype":"keyonoss"}
             * source_channelid : f03721ab13bb4ec8ad58db508d7a35eb
             * target_channelid : 5fe6ed4db733476296ff4e891a3114b6
             */

            private ActionResultBean actionResult;
            private String source_channelid;
            private String target_channelid;

            public ActionResultBean getActionResult() {
                return actionResult;
            }

            public void setActionResult(ActionResultBean actionResult) {
                this.actionResult = actionResult;
            }

            public String getSource_channelid() {
                return source_channelid;
            }

            public void setSource_channelid(String source_channelid) {
                this.source_channelid = source_channelid;
            }

            public String getTarget_channelid() {
                return target_channelid;
            }

            public void setTarget_channelid(String target_channelid) {
                this.target_channelid = target_channelid;
            }

            public static class ActionResultBean {
                /**
                 * action : TAKE_PHOTO
                 * contents : {"GSMIntensity":0,"battery":83,"deviceState":true,"deviceid":"1234567","direction":0,"gpslocatestatus":0,"horizonalaccuracy":0,"mileage":0,"precision":0,"satellitenum":0,"speed":0,"verticalaccuracy":0}
                 * httpURL :
                 * urltype : keyonoss
                 */

                private String action;
                private String contents;
                private String httpURL;
                private String urltype;

                public String getAction() {
                    return action;
                }

                public void setAction(String action) {
                    this.action = action;
                }

                public String getContents() {
                    return contents;
                }

                public void setContents(String contents) {
                    this.contents = contents;
                }

                public String getHttpURL() {
                    return httpURL;
                }

                public void setHttpURL(String httpURL) {
                    this.httpURL = httpURL;
                }

                public String getUrltype() {
                    return urltype;
                }

                public void setUrltype(String urltype) {
                    this.urltype = urltype;
                }
            }
        }
    }
}
