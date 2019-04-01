package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/6/21.
 */

public class MediaModel {


    /**
     * responseDataObj : {"contents":[{"authorList":["baike"],"categoryList":["幼儿安全知识","安全","儿歌"],"characteristicList":["欢快"],"contentid":"ertonganquanchangshi","contentlang":"zh","contenttype":"file","descriptionI18List":[{"language":"zh","value":"安全儿歌"}],"favourited":"no","imgauthorurl":"","imgauthorurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=ccZ5mV7SR9eWtFoHV3Krf1THK8o%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D","imgtitleurl":"contentstorage/解忧杂货店/安全/儿童安全常识/imgtitle.jpg","imgtitleurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86/imgtitle.jpg?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=buM6i7G%2F6ZDRgIFgZzm0%2BlQQS5I%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D","keywordList":["儿童安全常识"],"likeCount":0,"lyric":"","mediaList":[{"duration":271,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86/001%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86%20%281%29.mp3?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=huxvHBUsG6LhcckpTdPXYrrv2%2Fs%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/安全/儿童安全常识/001儿童安全常识 (1).mp3","mediacontentype":"keyonoss","mediafilename":"","mediaid":"ertonganquanchangshi-001儿童安全常识 (1).mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"001儿童安全常识 (1)","mediatype":"audio","resolution":""}],"mylike_id":"","nameI18List":[{"language":"zh","value":"儿童安全常识"}],"orientUser":{"age_max":5,"age_min":3,"sex":"any"},"quality":"perfect","scenario":"misc","sourceorigin":"internet+http://www.google.com.cn/aaa","status":"enable","target":"app+device","vendor":"none"}]}
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
        private List<ContentsBean> contents;

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public static class ContentsBean {
            /**
             * authorList : ["baike"]
             * categoryList : ["幼儿安全知识","安全","儿歌"]
             * characteristicList : ["欢快"]
             * contentid : ertonganquanchangshi
             * contentlang : zh
             * contenttype : file
             * descriptionI18List : [{"language":"zh","value":"安全儿歌"}]
             * favourited : no
             * imgauthorurl :
             * imgauthorurlhttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=ccZ5mV7SR9eWtFoHV3Krf1THK8o%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D
             * imgtitleurl : contentstorage/解忧杂货店/安全/儿童安全常识/imgtitle.jpg
             * imgtitleurlhttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86/imgtitle.jpg?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=buM6i7G%2F6ZDRgIFgZzm0%2BlQQS5I%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D
             * keywordList : ["儿童安全常识"]
             * likeCount : 0
             * lyric :
             * mediaList : [{"duration":271,"headerimgurl":"","headerimgurlhttpURL":"","httpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86/001%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86%20%281%29.mp3?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=huxvHBUsG6LhcckpTdPXYrrv2%2Fs%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D","lrcformat":"","mediacontent":"contentstorage/解忧杂货店/安全/儿童安全常识/001儿童安全常识 (1).mp3","mediacontentype":"keyonoss","mediafilename":"","mediaid":"ertonganquanchangshi-001儿童安全常识 (1).mp3","medialrc":"","medialrchttpURL":"","mediasubtype":"mp3","mediatitle":"001儿童安全常识 (1)","mediatype":"audio","resolution":""}]
             * mylike_id :
             * nameI18List : [{"language":"zh","value":"儿童安全常识"}]
             * orientUser : {"age_max":5,"age_min":3,"sex":"any"}
             * quality : perfect
             * scenario : misc
             * sourceorigin : internet+http://www.google.com.cn/aaa
             * status : enable
             * target : app+device
             * vendor : none
             */

            private String contentid;
            private String contentlang;
            private String contenttype;
            private String favourited;
            private String imgauthorurl;
            private String imgauthorurlhttpURL;
            private String imgtitleurl;
            private String imgtitleurlhttpURL;
            private int likeCount;
            private String lyric;
            private String mylike_id;
            private String myfavouriteid;
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

            public String getMyfavouriteid() {
                return myfavouriteid;
            }

            public void setMyfavouriteid(String myfavouriteid) {
                this.myfavouriteid = myfavouriteid;
            }

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

            public String getFavourited() {
                return favourited;
            }

            public void setFavourited(String favourited) {
                this.favourited = favourited;
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
                 * age_max : 5
                 * age_min : 3
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
                 * value : 安全儿歌
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
                 * duration : 271
                 * headerimgurl :
                 * headerimgurlhttpURL :
                 * httpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%AE%89%E5%85%A8/%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86/001%E5%84%BF%E7%AB%A5%E5%AE%89%E5%85%A8%E5%B8%B8%E8%AF%86%20%281%29.mp3?Expires=1535182391&OSSAccessKeyId=STS.NKPVaMHHG1k8gPP3g1fnMEVVK&Signature=huxvHBUsG6LhcckpTdPXYrrv2%2Fs%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4jlHdv5pZdmhqnTZXbh12dkauFhqpP9qTz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVPpIizWF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABc9Y%2Bx6ic1rDgjgL06Re%2BtbrYNTJWdq7inAdE1MpnNgEYxhcyjWUJPBdYareLphiR0hWkR2ZOSoqKh19XshxUvNJxQSFCyujAhbVd5LJZp7Yej2gPU%2BYCSJUXb2xoG%2FPUx512g66OGI5zWA274m86romQwi2H53ecc3GsHA5P9d8%3D
                 * lrcformat :
                 * mediacontent : contentstorage/解忧杂货店/安全/儿童安全常识/001儿童安全常识 (1).mp3
                 * mediacontentype : keyonoss
                 * mediafilename :
                 * mediaid : ertonganquanchangshi-001儿童安全常识 (1).mp3
                 * medialrc :
                 * medialrchttpURL :
                 * mediasubtype : mp3
                 * mediatitle : 001儿童安全常识 (1)
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
                 * value : 儿童安全常识
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
    }
}
