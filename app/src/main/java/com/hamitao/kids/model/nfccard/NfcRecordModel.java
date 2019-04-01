package com.hamitao.kids.model.nfccard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linjianwen on 2018/6/8.
 */

public class NfcRecordModel implements Serializable {


    /**
     * responseDataObj : {"NFCCards":[{"NFCID":"barcode1111111","creator":"sz00002_customer_8eaed3c66ed745fda86774876bbef85a","info":"voicerecord_8f7eacfaa3704a1985d3e187f0ddaa9b","infotype":"recordid","seqid":"83b3157649754fb58a262b9ea00e892f","voiceRecordingDesc":{"description":"我的录音描述","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/myvoicerecording/11111.mp3?Expires=1524483396&OSSAccessKeyId=STS.MG5sGSBF4WAGVqRd2ezKsw99T&Signature=sMTemV0QsxkyvjSjp1jHS85Jm8Y%3D&security-token=CAISjAJ1q6Ft5B2yfSjIrISAOP3nr5kV4IOsVFfjgDIwdsRfmPyStjz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPNMhjmU2F6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABmsZVv7V5C%2BdUvIIYVyRE9UBefH2d2%2FE31wgpgr8%2Ff0T7xl20F5jozKAPE5IH3QEMRZb18566IWrHLYdW%2B7FFXrlzA%2F5lH4HfEOXvSFl4iTJO08xCQ%2Bgrt%2BB9fuI9RcCI0ojpwg8SrpXUs0%2FOXtBlZJGEAAwRTsU0mQkOe8SxfyI%3D","id":"voicerecord_8f7eacfaa3704a1985d3e187f0ddaa9b","name":"给小朋友的祝福","ownerid":"sz00002_customer_8eaed3c66ed745fda86774876bbef85a","ownername":"","source":"DEVICE","url":"appstorage/myvoicerecording/11111.mp3"}}]}
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
        private List<NFCCardsBean> NFCCards;

        public List<NFCCardsBean> getNFCCards() {
            return NFCCards;
        }

        public void setNFCCards(List<NFCCardsBean> NFCCards) {
            this.NFCCards = NFCCards;
        }

        public static class NFCCardsBean {
            /**
             * NFCID : barcode1111111
             * creator : sz00002_customer_8eaed3c66ed745fda86774876bbef85a
             * info : voicerecord_8f7eacfaa3704a1985d3e187f0ddaa9b
             * infotype : recordid
             * seqid : 83b3157649754fb58a262b9ea00e892f
             * voiceRecordingDesc : {"description":"我的录音描述","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/myvoicerecording/11111.mp3?Expires=1524483396&OSSAccessKeyId=STS.MG5sGSBF4WAGVqRd2ezKsw99T&Signature=sMTemV0QsxkyvjSjp1jHS85Jm8Y%3D&security-token=CAISjAJ1q6Ft5B2yfSjIrISAOP3nr5kV4IOsVFfjgDIwdsRfmPyStjz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPNMhjmU2F6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABmsZVv7V5C%2BdUvIIYVyRE9UBefH2d2%2FE31wgpgr8%2Ff0T7xl20F5jozKAPE5IH3QEMRZb18566IWrHLYdW%2B7FFXrlzA%2F5lH4HfEOXvSFl4iTJO08xCQ%2Bgrt%2BB9fuI9RcCI0ojpwg8SrpXUs0%2FOXtBlZJGEAAwRTsU0mQkOe8SxfyI%3D","id":"voicerecord_8f7eacfaa3704a1985d3e187f0ddaa9b","name":"给小朋友的祝福","ownerid":"sz00002_customer_8eaed3c66ed745fda86774876bbef85a","ownername":"","source":"DEVICE","url":"appstorage/myvoicerecording/11111.mp3"}
             */

            private String NFCID;
            private String creator;
            private String info;
            private String infotype;
            private String seqid;
            private VoiceRecordingDescBean voiceRecordingDesc;

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

            public VoiceRecordingDescBean getVoiceRecordingDesc() {
                return voiceRecordingDesc;
            }

            public void setVoiceRecordingDesc(VoiceRecordingDescBean voiceRecordingDesc) {
                this.voiceRecordingDesc = voiceRecordingDesc;
            }

            public static class VoiceRecordingDescBean {
                /**
                 * description : 我的录音描述
                 * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/myvoicerecording/11111.mp3?Expires=1524483396&OSSAccessKeyId=STS.MG5sGSBF4WAGVqRd2ezKsw99T&Signature=sMTemV0QsxkyvjSjp1jHS85Jm8Y%3D&security-token=CAISjAJ1q6Ft5B2yfSjIrISAOP3nr5kV4IOsVFfjgDIwdsRfmPyStjz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPNMhjmU2F6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABmsZVv7V5C%2BdUvIIYVyRE9UBefH2d2%2FE31wgpgr8%2Ff0T7xl20F5jozKAPE5IH3QEMRZb18566IWrHLYdW%2B7FFXrlzA%2F5lH4HfEOXvSFl4iTJO08xCQ%2Bgrt%2BB9fuI9RcCI0ojpwg8SrpXUs0%2FOXtBlZJGEAAwRTsU0mQkOe8SxfyI%3D
                 * id : voicerecord_8f7eacfaa3704a1985d3e187f0ddaa9b
                 * name : 给小朋友的祝福
                 * ownerid : sz00002_customer_8eaed3c66ed745fda86774876bbef85a
                 * ownername :
                 * source : DEVICE
                 * url : appstorage/myvoicerecording/11111.mp3
                 */

                private String description;
                private String httpURL;
                private String id;
                private String name;
                private String ownerid;
                private String ownername;
                private String source;
                private String url;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getHttpURL() {
                    return httpURL;
                }

                public void setHttpURL(String httpURL) {
                    this.httpURL = httpURL;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getOwnerid() {
                    return ownerid;
                }

                public void setOwnerid(String ownerid) {
                    this.ownerid = ownerid;
                }

                public String getOwnername() {
                    return ownername;
                }

                public void setOwnername(String ownername) {
                    this.ownername = ownername;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
