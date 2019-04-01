package com.hamitao.kids.model.nfccard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linjianwen on 2018/3/28
 *
 *
 */

public class NfcModel implements Serializable {

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


            private String NFCID;
            private String creator;
            private String info;
            private String infotype;
            private String seqid;
            private String title;
            private ContentDescBean contentDesc;
            private VoiceRecordingDescBean voiceRecordingDesc;

            public String getNFCID() {
                return NFCID;
            }

            public void setNFCID(String NFCID) {
                this.NFCID = NFCID;
            }

            public ContentDescBean getContentDesc() {
                return contentDesc;
            }

            public void setContentDesc(ContentDescBean contentDesc) {
                this.contentDesc = contentDesc;
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

            public VoiceRecordingDescBean getVoiceRecordingDesc() {
                return voiceRecordingDesc;
            }

            public void setVoiceRecordingDesc(VoiceRecordingDescBean voiceRecordingDesc) {
                this.voiceRecordingDesc = voiceRecordingDesc;
            }

            public static class ContentDescBean {

                private String contentid;
                private String contentlang;
                private String contenttype;
                private String imgauthorurl;
                private String imgauthorurlhttpURL;
                private String imgtitleurl;
                private String imgtitleurlhttpURL;
                private int likeCount;
                private String lyric;
                private String mylike_id;
                private OrientUserBean orientUser;
                private String quality;
                private String scenario;
                private String sourceorigin;
                private String status;
                private String target;
                private String vendor;
                private List<String> authorList;
                private List<String> categoryList;
                private List<String> characteristicList;
                private List<DescriptionI18ListBean> descriptionI18List;
                private List<String> keywordList;
                private List<MediaListBean> mediaList;
                private List<NameI18ListBean> nameI18List;

                public String getContentid() {
                    return contentid;
                }

                public void setContentid(String contentid) {
                    this.contentid = contentid;
                }

                public String getContentlang() {
                    return contentlang;
                }

                public void setContentlang(String contentlang) {
                    this.contentlang = contentlang;
                }

                public String getContenttype() {
                    return contenttype;
                }

                public void setContenttype(String contenttype) {
                    this.contenttype = contenttype;
                }

                public String getImgauthorurl() {
                    return imgauthorurl;
                }

                public void setImgauthorurl(String imgauthorurl) {
                    this.imgauthorurl = imgauthorurl;
                }

                public String getImgauthorurlhttpURL() {
                    return imgauthorurlhttpURL;
                }

                public void setImgauthorurlhttpURL(String imgauthorurlhttpURL) {
                    this.imgauthorurlhttpURL = imgauthorurlhttpURL;
                }

                public String getImgtitleurl() {
                    return imgtitleurl;
                }

                public void setImgtitleurl(String imgtitleurl) {
                    this.imgtitleurl = imgtitleurl;
                }

                public String getImgtitleurlhttpURL() {
                    return imgtitleurlhttpURL;
                }

                public void setImgtitleurlhttpURL(String imgtitleurlhttpURL) {
                    this.imgtitleurlhttpURL = imgtitleurlhttpURL;
                }

                public int getLikeCount() {
                    return likeCount;
                }

                public void setLikeCount(int likeCount) {
                    this.likeCount = likeCount;
                }

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public String getMylike_id() {
                    return mylike_id;
                }

                public void setMylike_id(String mylike_id) {
                    this.mylike_id = mylike_id;
                }

                public OrientUserBean getOrientUser() {
                    return orientUser;
                }

                public void setOrientUser(OrientUserBean orientUser) {
                    this.orientUser = orientUser;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getScenario() {
                    return scenario;
                }

                public void setScenario(String scenario) {
                    this.scenario = scenario;
                }

                public String getSourceorigin() {
                    return sourceorigin;
                }

                public void setSourceorigin(String sourceorigin) {
                    this.sourceorigin = sourceorigin;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getTarget() {
                    return target;
                }

                public void setTarget(String target) {
                    this.target = target;
                }

                public String getVendor() {
                    return vendor;
                }

                public void setVendor(String vendor) {
                    this.vendor = vendor;
                }

                public List<String> getAuthorList() {
                    return authorList;
                }

                public void setAuthorList(List<String> authorList) {
                    this.authorList = authorList;
                }

                public List<String> getCategoryList() {
                    return categoryList;
                }

                public void setCategoryList(List<String> categoryList) {
                    this.categoryList = categoryList;
                }

                public List<String> getCharacteristicList() {
                    return characteristicList;
                }

                public void setCharacteristicList(List<String> characteristicList) {
                    this.characteristicList = characteristicList;
                }

                public List<DescriptionI18ListBean> getDescriptionI18List() {
                    return descriptionI18List;
                }

                public void setDescriptionI18List(List<DescriptionI18ListBean> descriptionI18List) {
                    this.descriptionI18List = descriptionI18List;
                }

                public List<String> getKeywordList() {
                    return keywordList;
                }

                public void setKeywordList(List<String> keywordList) {
                    this.keywordList = keywordList;
                }

                public List<MediaListBean> getMediaList() {
                    return mediaList;
                }

                public void setMediaList(List<MediaListBean> mediaList) {
                    this.mediaList = mediaList;
                }

                public List<NameI18ListBean> getNameI18List() {
                    return nameI18List;
                }

                public void setNameI18List(List<NameI18ListBean> nameI18List) {
                    this.nameI18List = nameI18List;
                }

                public static class OrientUserBean {
                    /**
                     * age_max : 7
                     * age_min : 4
                     * sex : any
                     */

                    private int age_max;
                    private int age_min;
                    private String sex;

                    public int getAge_max() {
                        return age_max;
                    }

                    public void setAge_max(int age_max) {
                        this.age_max = age_max;
                    }

                    public int getAge_min() {
                        return age_min;
                    }

                    public void setAge_min(int age_min) {
                        this.age_min = age_min;
                    }

                    public String getSex() {
                        return sex;
                    }

                    public void setSex(String sex) {
                        this.sex = sex;
                    }
                }

                public static class DescriptionI18ListBean {
                    /**
                     * language : zh
                     * value : 科普故事
                     */

                    private String language;
                    private String value;

                    public String getLanguage() {
                        return language;
                    }

                    public void setLanguage(String language) {
                        this.language = language;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }

                public static class MediaListBean {
                    /**
                     * duration : 238
                     * headerimgurl :
                     * headerimgurlhttpURL :
                     * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E7%99%BE%E7%A7%91/%E4%B8%96%E7%95%8C%E4%B8%8A%E6%9C%80%E8%BD%AF%E6%9C%80%E8%BD%AF%E7%9A%84%E7%89%A9%E7%90%86%E4%B9%A6/%E4%B8%96%E7%95%8C%E4%B8%8A%E6%9C%80%E8%BD%AF%E6%9C%80%E8%BD%AF%E7%9A%84%E7%89%A9%E7%90%86%E4%B9%A6001.%E6%83%AF%E6%80%A7%E6%98%AF%E4%BF%9D%E6%8C%81%E7%89%A9%E4%BD%93%E5%8E%9F%E6%9D%A5%E7%8A%B6%E6%80%81%E7%9A%84%E6%80%A7%E8%B4%A8.mp3?Expires=1533540508&OSSAccessKeyId=STS.NJWwgJRGe7psJMpFM9iadSgLt&Signature=igGNhkqX0B2nPrJBDNmAn2F0XwQ%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4niPN3%2Bv5hEgLKYSGvBok1sZe5IvKLnljz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPQ65F%2BjKF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABpqBFJ%2B0rYFT3d3%2FInXkRunWfIdgsNcMrqIq2v%2BAn6jbbVex%2BvuykX6e%2BBjUXiTEs6CnZxIlDrsIbfCHrR%2BMNjN%2FvdcNfXNJQPfEKQYgCoMElob8iczVgPSyrvcmQoADuD8eEt7Pq4sArphlzNpXRsHq8LcVFPWl5tyAIOsG0AiA%3D
                     * lrcformat :
                     * mediacontent : contentstorage/解忧杂货店/百科/世界上最软最软的物理书/世界上最软最软的物理书001.惯性是保持物体原来状态的性质.mp3
                     * mediacontentype : keyonoss
                     * mediafilename :
                     * mediaid : erge.shijieshangzuiruanzuiruandewulishu-世界上最软最软的物理书001.惯性是保持物体原来状态的性质.mp3
                     * medialrc :
                     * medialrchttpURL :
                     * mediasubtype : mp3
                     * mediatitle : 惯性是保持物体原来状态的性质
                     * mediatype : audio
                     * resolution :
                     */

                    private int duration;
                    private String headerimgurl;
                    private String headerimgurlhttpURL;
                    private String httpURL;
                    private String lrcformat;
                    private String mediacontent;
                    private String mediacontentype;
                    private String mediafilename;
                    private String mediaid;
                    private String medialrc;
                    private String medialrchttpURL;
                    private String mediasubtype;
                    private String mediatitle;
                    private String mediatype;
                    private String resolution;

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public String getHeaderimgurl() {
                        return headerimgurl;
                    }

                    public void setHeaderimgurl(String headerimgurl) {
                        this.headerimgurl = headerimgurl;
                    }

                    public String getHeaderimgurlhttpURL() {
                        return headerimgurlhttpURL;
                    }

                    public void setHeaderimgurlhttpURL(String headerimgurlhttpURL) {
                        this.headerimgurlhttpURL = headerimgurlhttpURL;
                    }

                    public String getHttpURL() {
                        return httpURL;
                    }

                    public void setHttpURL(String httpURL) {
                        this.httpURL = httpURL;
                    }

                    public String getLrcformat() {
                        return lrcformat;
                    }

                    public void setLrcformat(String lrcformat) {
                        this.lrcformat = lrcformat;
                    }

                    public String getMediacontent() {
                        return mediacontent;
                    }

                    public void setMediacontent(String mediacontent) {
                        this.mediacontent = mediacontent;
                    }

                    public String getMediacontentype() {
                        return mediacontentype;
                    }

                    public void setMediacontentype(String mediacontentype) {
                        this.mediacontentype = mediacontentype;
                    }

                    public String getMediafilename() {
                        return mediafilename;
                    }

                    public void setMediafilename(String mediafilename) {
                        this.mediafilename = mediafilename;
                    }

                    public String getMediaid() {
                        return mediaid;
                    }

                    public void setMediaid(String mediaid) {
                        this.mediaid = mediaid;
                    }

                    public String getMedialrc() {
                        return medialrc;
                    }

                    public void setMedialrc(String medialrc) {
                        this.medialrc = medialrc;
                    }

                    public String getMedialrchttpURL() {
                        return medialrchttpURL;
                    }

                    public void setMedialrchttpURL(String medialrchttpURL) {
                        this.medialrchttpURL = medialrchttpURL;
                    }

                    public String getMediasubtype() {
                        return mediasubtype;
                    }

                    public void setMediasubtype(String mediasubtype) {
                        this.mediasubtype = mediasubtype;
                    }

                    public String getMediatitle() {
                        return mediatitle;
                    }

                    public void setMediatitle(String mediatitle) {
                        this.mediatitle = mediatitle;
                    }

                    public String getMediatype() {
                        return mediatype;
                    }

                    public void setMediatype(String mediatype) {
                        this.mediatype = mediatype;
                    }

                    public String getResolution() {
                        return resolution;
                    }

                    public void setResolution(String resolution) {
                        this.resolution = resolution;
                    }
                }

                public static class NameI18ListBean {
                    /**
                     * language : zh
                     * value : 世界上最软最软的物理书
                     */

                    private String language;
                    private String value;

                    public String getLanguage() {
                        return language;
                    }

                    public void setLanguage(String language) {
                        this.language = language;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }

            public static class VoiceRecordingDescBean {
                /**
                 * description :
                 * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/appstorage/testdir/18576406338/Record/%E5%A4%A7%E5%8F%B8%E7%A9%BA.mp3?Expires=1533540579&OSSAccessKeyId=STS.NHTSS9wA4JQzfMuybudhySEe4&Signature=eapCZD8nSI7gJJoG2X4wYnpDLH8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4vhGOmNmp4V%2FZORZGvEnWIgaOdVvIDO1jz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPEJlZ%2BjKF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABVOOF98lJVToyi%2BU5uoI3irGgmJ5lTv8mY%2F5thkiA%2BJPVZ5%2FwSuWMEA5QugN2GjtTMKfROaBdw71RVC5omuD6JVwYOQAhdHlGROnhPkPe5IM4zgsyKy3yY8cP4VJOiYHshHXAK8w9e1g5bkIbBEcc14hMgd5P%2FzGkodF5SJYbwas%3D
                 * id : voicerecord_37a2a1aa1fe14dddbfe25923a9a776b4
                 * name : 大司空
                 * ownerid : sz00002_customer_528bda9b6c324ffb9bc3ffd7c103a2cd
                 * ownername :
                 * selectStatus : yes
                 * source : DEVICE
                 * url : appstorage/testdir/18576406338/Record/大司空.mp3
                 */

                private String description;
                private String httpURL;
                private String id;
                private String name;
                private String ownerid;
                private String ownername;
                private String selectStatus;
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

                public String getSelectStatus() {
                    return selectStatus;
                }

                public void setSelectStatus(String selectStatus) {
                    this.selectStatus = selectStatus;
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
