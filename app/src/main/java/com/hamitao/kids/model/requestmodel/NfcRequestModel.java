package com.hamitao.kids.model.requestmodel;

import java.io.Serializable;

/**
 * Created by linjianwen on 2018/3/28.
 */

public class NfcRequestModel implements Serializable {

    /**
     * customerid : sz00002_customer_8eaed3c66ed745fda86774876bbef85a
     * nfcard : {"NFCID":"barcode1111","info":"2222","infotype":"recordid","title":"早上的太阳","mediatype":"mp4","auxinfo":""}
     */

    private String customerid;
    private NfcardBean nfcard;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public NfcardBean getNfcard() {
        return nfcard;
    }

    public void setNfcard(NfcardBean nfcard) {
        this.nfcard = nfcard;
    }

    public static class NfcardBean {

        /**
         * NFCID : barcode1111
         * info : 2222
         * infotype : recordid
         * title : 早上的太阳
         * mediatype : mp4
         * auxinfo :
         * img : xxx
         */

        private String NFCID;
        private String info;
        private String infotype;
        private String title;
        private String mediatype;
        private String auxinfo;
        private String img;

        public String getNFCID() {
            return NFCID;
        }

        public void setNFCID(String NFCID) {
            this.NFCID = NFCID;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMediatype() {
            return mediatype;
        }

        public void setMediatype(String mediatype) {
            this.mediatype = mediatype;
        }

        public String getAuxinfo() {
            return auxinfo;
        }

        public void setAuxinfo(String auxinfo) {
            this.auxinfo = auxinfo;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
