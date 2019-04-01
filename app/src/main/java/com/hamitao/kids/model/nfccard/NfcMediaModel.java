package com.hamitao.kids.model.nfccard;

import java.util.List;

/**
 * Created by linjianwen on 2018/8/14.
 */

public class NfcMediaModel {


    private List<NFCCardsBean> NFCCards;

    public List<NFCCardsBean> getNFCCards() {
        return NFCCards;
    }

    public void setNFCCards(List<NFCCardsBean> NFCCards) {
        this.NFCCards = NFCCards;
    }

    public static class NFCCardsBean {
        /**
         * NFCID : 6936281404241
         * creator : sz00001_customer_8efa7f4231884619a5da1dc8c5a53c59
         * info : erge.anquanxiaoerge-交通安全歌-段丽阳彭野.mp3
         * infotype : mediaid
         * seqid : fe0d02f3af28418e928d1e8bf91add0d
         * title : 交通安全歌-段丽阳彭野
         *
         */

        private String NFCID;
        private String creator;
        private String info;
        private String infotype;
        private String seqid;
        private String title;
        private String imgHttpURL;

        public String getImgHttpURL() {
            return imgHttpURL;
        }

        public void setImgHttpURL(String imgHttpURL) {
            this.imgHttpURL = imgHttpURL;
        }

        public String getNFCID() {
            return NFCID;
        }

        public void setNFCID(String NFCID) {
            this.NFCID = NFCID;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
