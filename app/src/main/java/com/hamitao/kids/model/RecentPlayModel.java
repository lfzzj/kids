package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class RecentPlayModel {

    /**
     * responseDataObj : {"paginationInfo":{"current":{"from":0,"to":0},"totalrow":1},"playList":[{"auxinfo":"附加信息","creator":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","description":"description","info":"the contentid of content","infotype":"contentid","ownerid":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","seqid":"f0f0323ce6014eeba8f1b08a5be0fa0b","title":"title","updatetime":"2018-07-17 06:31:31.139"}]}
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
         * paginationInfo : {"current":{"from":0,"to":0},"totalrow":1}
         * playList : [{"auxinfo":"附加信息","creator":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","description":"description","info":"the contentid of content","infotype":"contentid","ownerid":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","seqid":"f0f0323ce6014eeba8f1b08a5be0fa0b","title":"title","updatetime":"2018-07-17 06:31:31.139"}]
         */

        private PaginationInfoBean paginationInfo;
        private List<PlayListBean> playList;

        public PaginationInfoBean getPaginationInfo() {
            return paginationInfo;
        }

        public void setPaginationInfo(PaginationInfoBean paginationInfo) {
            this.paginationInfo = paginationInfo;
        }

        public List<PlayListBean> getPlayList() {
            return playList;
        }

        public void setPlayList(List<PlayListBean> playList) {
            this.playList = playList;
        }

        public static class PaginationInfoBean {
            /**
             * current : {"from":0,"to":0}
             * totalrow : 1
             */

            private CurrentBean current;
            private int totalrow;

            public CurrentBean getCurrent() {
                return current;
            }

            public void setCurrent(CurrentBean current) {
                this.current = current;
            }

            public int getTotalrow() {
                return totalrow;
            }

            public void setTotalrow(int totalrow) {
                this.totalrow = totalrow;
            }

            public static class CurrentBean {
                /**
                 * from : 0
                 * to : 0
                 */

                private int from;
                private int to;

                public int getFrom() {
                    return from;
                }

                public void setFrom(int from) {
                    this.from = from;
                }

                public int getTo() {
                    return to;
                }

                public void setTo(int to) {
                    this.to = to;
                }
            }
        }

        public static class PlayListBean {
            /**
             * auxinfo : 附加信息
             * creator : sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7
             * description : description
             * info : the contentid of content
             * infotype : contentid
             * ownerid : sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7
             * seqid : f0f0323ce6014eeba8f1b08a5be0fa0b
             * title : title
             * updatetime : 2018-07-17 06:31:31.139
             */

            private String auxinfo;
            private String creator;
            private String description;
            private String info;
            private String infotype;
            private String ownerid;
            private String seqid;
            private String title;
            private String updatetime;
            private String headerimgurl;
            private String headerimgurlHttpURL;

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

            public String getAuxinfo() {
                return auxinfo;
            }

            public void setAuxinfo(String auxinfo) {
                this.auxinfo = auxinfo;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getOwnerid() {
                return ownerid;
            }

            public void setOwnerid(String ownerid) {
                this.ownerid = ownerid;
            }

            public String getSeqid() {
                return seqid;
            }

            public void setSeqid(String seqid) {
                this.seqid = seqid;
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
        }
    }
}
