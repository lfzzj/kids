package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/18.
 */

public class DeliverModel {


    /**
     * responseDataObj : {"airDropList":[{"auxinfo":"附加信息","creator":"sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3","description":"description","info":"the contentid of content","infotype":"contentid","seqid":"42136b03762c401db842cdb1a4df1fe8","sourceid":"sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3","targetid":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","title":"title","updatetime":"2018-07-17 06:31:31.487"}],"paginationInfo":{"current":{"from":0,"to":0},"totalrow":1}}
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
         * airDropList : [{"auxinfo":"附加信息","creator":"sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3","description":"description","info":"the contentid of content","infotype":"contentid","seqid":"42136b03762c401db842cdb1a4df1fe8","sourceid":"sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3","targetid":"sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7","title":"title","updatetime":"2018-07-17 06:31:31.487"}]
         * paginationInfo : {"current":{"from":0,"to":0},"totalrow":1}
         */

        private PaginationInfoBean paginationInfo;
        private List<AirDropListBean> airDropList;

        public PaginationInfoBean getPaginationInfo() {
            return paginationInfo;
        }

        public void setPaginationInfo(PaginationInfoBean paginationInfo) {
            this.paginationInfo = paginationInfo;
        }

        public List<AirDropListBean> getAirDropList() {
            return airDropList;
        }

        public void setAirDropList(List<AirDropListBean> airDropList) {
            this.airDropList = airDropList;
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

        public static class AirDropListBean {
            /**
             * auxinfo : 附加信息
             * creator : sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3
             * description : description
             * info : the contentid of content
             * infotype : contentid
             * seqid : 42136b03762c401db842cdb1a4df1fe8
             * sourceid : sz00002_customer_0193ac174b9a4e0996f47e98dcbf75d3
             * targetid : sz10002_kidsrobot_b854b4bec8ca479abf9b82295fed17e7
             * title : title
             * updatetime : 2018-07-17 06:31:31.487
             */

            private String auxinfo;
            private String creator;
            private String description;
            private String info;
            private String infotype;
            private String seqid;
            private String sourceid;
            private String targetid;
            private String title;
            private String updatetime;

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

            public String getSeqid() {
                return seqid;
            }

            public void setSeqid(String seqid) {
                this.seqid = seqid;
            }

            public String getSourceid() {
                return sourceid;
            }

            public void setSourceid(String sourceid) {
                this.sourceid = sourceid;
            }

            public String getTargetid() {
                return targetid;
            }

            public void setTargetid(String targetid) {
                this.targetid = targetid;
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
