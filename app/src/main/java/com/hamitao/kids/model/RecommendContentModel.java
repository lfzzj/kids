package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/7/3.
 */

public class RecommendContentModel {


    /**
     * responseDataObj : {"contents":[{"authorList":["佚名"],"categoryList":["今日推荐","专家推荐","生活常识/启蒙教育","娱乐/儿歌"],"characteristicList":["欢快","动物","喜剧"],"contentid":"EEEEEAAAAA11111111","contentlang":"zh","contenttype":"file","descriptionI18List":[{"language":"zh","value":"幼幼熊系列儿歌"}],"imgtitleurl":"contentstorage/misc/test/儿歌/imgtitle.jpg","imgtitleurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/misc/test/%E5%84%BF%E6%AD%8C/imgtitle.jpg?Expires=1525507762&OSSAccessKeyId=STS.HpzGM8ZvLWmSJBPhcJC1fga3b&Signature=ddJs%2BaL%2BiEUD35rO3QoSWiPGG4k%3D&security-token=CAISjAJ1q6Ft5B2yfSjIqbPPDPeMt6lt4K%2B4SGThjGMfT75KiKSYgDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPcL8t8VCF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABHknP2fuEUjZk2bC9ONRNR%2BcQjQo%2BpSadrEbGrQT4aYgEOc9EGkXCCpwABWS4PvCxaOQJWR0KuWQYPEoYO3EDw%2F81XUoI%2BQtxpr5YneG9Wklt7LLaThZIynI6%2BavnNMhkV5iBnIz8bLRXIeEMxbeb3VHtv0SbE423O7%2FYk0lbYAI%3D","keywordList":["七个小矮人"],"likeCount":22,"lyric":"小呀嘛小呀嘛小二郎","nameI18List":[{"language":"zh","value":"幼幼熊儿歌系列"}],"orientUser":{"age_max":6,"age_min":2,"sex":"any"},"quality":"perfect","sourceorigin":"internet+http://www.google.com.cn/aaa","status":"enable","target":"app+device","vendor":"none"},{"authorList":["wangwu","lisi","zhangsan"],"categoryList":["专家推荐","生活常识/启蒙教育","娱乐/儿歌"],"characteristicList":["悲伤","喜剧"],"contentid":"erge.11111111","contentlang":"zh","contenttype":"file","descriptionI18List":[{"language":"zh","value":"七个小矮人系列"}],"imgtitleurl":"contentstorage/misc/test/儿歌/七个小矮人/imgtitle.jpg","imgtitleurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/misc/test/%E5%84%BF%E6%AD%8C/%E4%B8%83%E4%B8%AA%E5%B0%8F%E7%9F%AE%E4%BA%BA/imgtitle.jpg?Expires=1525507762&OSSAccessKeyId=STS.HpzGM8ZvLWmSJBPhcJC1fga3b&Signature=S1fKaN0eWf01KBDnYDfINGf39yQ%3D&security-token=CAISjAJ1q6Ft5B2yfSjIqbPPDPeMt6lt4K%2B4SGThjGMfT75KiKSYgDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPcL8t8VCF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABHknP2fuEUjZk2bC9ONRNR%2BcQjQo%2BpSadrEbGrQT4aYgEOc9EGkXCCpwABWS4PvCxaOQJWR0KuWQYPEoYO3EDw%2F81XUoI%2BQtxpr5YneG9Wklt7LLaThZIynI6%2BavnNMhkV5iBnIz8bLRXIeEMxbeb3VHtv0SbE423O7%2FYk0lbYAI%3D","keywordList":["七个小矮人"],"likeCount":0,"lyric":"小呀嘛小呀嘛小二郎","nameI18List":[{"language":"zh","value":"七个小矮人系列"}],"orientUser":{"age_max":6,"age_min":2,"sex":"any"},"quality":"perfect","sourceorigin":"internet+http://www.google.com.cn/aaa","status":"enable","target":"app+device","vendor":"none"}]}
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
        private List<ContentTreeNodesBean> contentTreeNodes;
        private RecentPlayModel.ResponseDataObjBean.PaginationInfoBean paginationInfo;

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public List<ContentTreeNodesBean> getContentTreeNodes() {
            return contentTreeNodes;
        }

        public void setContentTreeNodes(List<ContentTreeNodesBean> contentTreeNodes) {
            this.contentTreeNodes = contentTreeNodes;
        }

        public RecentPlayModel.ResponseDataObjBean.PaginationInfoBean getPaginationInfo() {
            return paginationInfo;
        }

        public void setPaginationInfo(RecentPlayModel.ResponseDataObjBean.PaginationInfoBean paginationInfo) {
            this.paginationInfo = paginationInfo;
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

        public static class ContentsBean  {
            /**
             * authorList : ["佚名"]
             * categoryList : ["今日推荐","专家推荐","生活常识/启蒙教育","娱乐/儿歌"]
             * characteristicList : ["欢快","动物","喜剧"]
             * contentid : EEEEEAAAAA11111111
             * contentlang : zh
             * contenttype : file
             * descriptionI18List : [{"language":"zh","value":"幼幼熊系列儿歌"}]
             * imgtitleurl : contentstorage/misc/test/儿歌/imgtitle.jpg
             * imgtitleurlhttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/misc/test/%E5%84%BF%E6%AD%8C/imgtitle.jpg?Expires=1525507762&OSSAccessKeyId=STS.HpzGM8ZvLWmSJBPhcJC1fga3b&Signature=ddJs%2BaL%2BiEUD35rO3QoSWiPGG4k%3D&security-token=CAISjAJ1q6Ft5B2yfSjIqbPPDPeMt6lt4K%2B4SGThjGMfT75KiKSYgDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPcL8t8VCF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABHknP2fuEUjZk2bC9ONRNR%2BcQjQo%2BpSadrEbGrQT4aYgEOc9EGkXCCpwABWS4PvCxaOQJWR0KuWQYPEoYO3EDw%2F81XUoI%2BQtxpr5YneG9Wklt7LLaThZIynI6%2BavnNMhkV5iBnIz8bLRXIeEMxbeb3VHtv0SbE423O7%2FYk0lbYAI%3D
             * keywordList : ["七个小矮人"]
             * likeCount : 22
             * lyric : 小呀嘛小呀嘛小二郎
             * nameI18List : [{"language":"zh","value":"幼幼熊儿歌系列"}]
             * orientUser : {"age_max":6,"age_min":2,"sex":"any"}
             * quality : perfect
             * sourceorigin : internet+http://www.google.com.cn/aaa
             * status : enable
             * target : app+device
             * vendor : none
             */

            private String contentid;
            private String contentlang;
            private String contenttype;
            private String imgtitleurl;
            private String imgtitleurlhttpURL;
            private int likeCount;
            private String lyric;
            private OrientUserBean orientUser;
            private String quality;
            private String sourceorigin;
            private String status;
            private String target;
            private String vendor;
            private List<String> authorList;
            private List<String> categoryList;
            private List<String> characteristicList;private List<DescriptionI18ListBean> descriptionI18List;
            private List<String> keywordList;
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

            public List<NameI18ListBean> getNameI18List() {
                return nameI18List;
            }

            public void setNameI18List(List<NameI18ListBean> nameI18List) {
                this.nameI18List = nameI18List;
            }


            public static class OrientUserBean {
                /**
                 * age_max : 6
                 * age_min : 2
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
                 * value : 幼幼熊系列儿歌
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

            public static class NameI18ListBean {
                /**
                 * language : zh
                 * value : 幼幼熊儿歌系列
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


        public static class ContentTreeNodesBean {
            /**
             * children : []
             * contentid :
             * nodedesc :
             * nodeheaderimgurl :
             * nodeid : /跟着小淘学安全知识
             * nodename : 跟着小淘学安全知识
             * nodetype : nonleaf
             * parentnodeid :
             * scenario : 小淘课堂
             * contentDesc : {"authorList":["hmeet"],"categoryList":["小淘课堂","艺术","国学"],"characteristicList":["押韵"],"contentid":"genzhexiaotaoxuehanzi","contentlang":"zh","contenttype":"file","descriptionI18List":[{"language":"zh","value":"汉字记忆"}],"imgauthorurl":"","imgauthorurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/?Expires=1534833440&OSSAccessKeyId=STS.NHRo58HriBCZmjHRXWmZbgKjv&Signature=neMlKEt7bNZhsBFSsvuX0nGD9w8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4vnJI%2BMpa1I9YGxb0z5tlgCYdVOiI7BlDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVIUH0jeF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABFtvEBLR4W0VS2iM3hBYJmoyj6%2BGGYGCAcGx4a%2FremuRZYjVnPLzwni1bRsZSatluxUr8SqpgAiqD1YUnRW2qjolplqFNS5jf%2FE6xWBm6q6DaRjm7RGYp1FEpAhjnl86jzCv52koT%2Fwi1sEvxDRAhVkqyaMm9oikTrkX0OFL8FGE%3D","imgtitleurl":"contentstorage/解忧杂货店/小淘课堂/跟着小淘学汉字/imgtitle.png","imgtitleurlhttpURL":"http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%B0%8F%E6%B7%98%E8%AF%BE%E5%A0%82/%E8%B7%9F%E7%9D%80%E5%B0%8F%E6%B7%98%E5%AD%A6%E6%B1%89%E5%AD%97/imgtitle.png?Expires=1534833440&OSSAccessKeyId=STS.NHRo58HriBCZmjHRXWmZbgKjv&Signature=Lel%2B%2FhYoFbGubarBp1MIJnxF9hs%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4vnJI%2BMpa1I9YGxb0z5tlgCYdVOiI7BlDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVIUH0jeF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABFtvEBLR4W0VS2iM3hBYJmoyj6%2BGGYGCAcGx4a%2FremuRZYjVnPLzwni1bRsZSatluxUr8SqpgAiqD1YUnRW2qjolplqFNS5jf%2FE6xWBm6q6DaRjm7RGYp1FEpAhjnl86jzCv52koT%2Fwi1sEvxDRAhVkqyaMm9oikTrkX0OFL8FGE%3D","keywordList":["汉字","跟着小淘学汉字"],"lyric":"","mediaList":[],"nameI18List":[{"language":"zh","value":"跟着小淘学汉字"}],"orientUser":{"age_max":100,"age_min":5,"sex":"any"},"quality":"perfect","scenario":"misc","sourceorigin":"internet+http://www.google.com.cn/aaa","status":"enable","target":"app+device","vendor":"none"}
             */

            private String contentid;
            private String nodedesc;
            private String nodeheaderimgurl;
            private String nodeheaderimgurlhttpURL;

            public String getNodeheaderimgurlhttpURL() {
                return nodeheaderimgurlhttpURL;
            }

            public void setNodeheaderimgurlhttpURL(String nodeheaderimgurlhttpURL) {
                this.nodeheaderimgurlhttpURL = nodeheaderimgurlhttpURL;
            }

            private String nodeid;
            private String nodename;
            private String nodetype;
            private String parentnodeid;
            private String scenario;
            private ContentDescBean contentDesc;
            private List<?> children;

            public String getContentid() {
                return contentid;
            }

            public void setContentid(String contentid) {
                this.contentid = contentid;
            }

            public String getNodedesc() {
                return nodedesc;
            }

            public void setNodedesc(String nodedesc) {
                this.nodedesc = nodedesc;
            }

            public String getNodeheaderimgurl() {
                return nodeheaderimgurl;
            }

            public void setNodeheaderimgurl(String nodeheaderimgurl) {
                this.nodeheaderimgurl = nodeheaderimgurl;
            }

            public String getNodeid() {
                return nodeid;
            }

            public void setNodeid(String nodeid) {
                this.nodeid = nodeid;
            }

            public String getNodename() {
                return nodename;
            }

            public void setNodename(String nodename) {
                this.nodename = nodename;
            }

            public String getNodetype() {
                return nodetype;
            }

            public void setNodetype(String nodetype) {
                this.nodetype = nodetype;
            }

            public String getParentnodeid() {
                return parentnodeid;
            }

            public void setParentnodeid(String parentnodeid) {
                this.parentnodeid = parentnodeid;
            }

            public String getScenario() {
                return scenario;
            }

            public void setScenario(String scenario) {
                this.scenario = scenario;
            }

            public ContentDescBean getContentDesc() {
                return contentDesc;
            }

            public void setContentDesc(ContentDescBean contentDesc) {
                this.contentDesc = contentDesc;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }

            public static class ContentDescBean {
                /**
                 * authorList : ["hmeet"]
                 * categoryList : ["小淘课堂","艺术","国学"]
                 * characteristicList : ["押韵"]
                 * contentid : genzhexiaotaoxuehanzi
                 * contentlang : zh
                 * contenttype : file
                 * descriptionI18List : [{"language":"zh","value":"汉字记忆"}]
                 * imgauthorurl :
                 * imgauthorurlhttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/?Expires=1534833440&OSSAccessKeyId=STS.NHRo58HriBCZmjHRXWmZbgKjv&Signature=neMlKEt7bNZhsBFSsvuX0nGD9w8%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4vnJI%2BMpa1I9YGxb0z5tlgCYdVOiI7BlDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVIUH0jeF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABFtvEBLR4W0VS2iM3hBYJmoyj6%2BGGYGCAcGx4a%2FremuRZYjVnPLzwni1bRsZSatluxUr8SqpgAiqD1YUnRW2qjolplqFNS5jf%2FE6xWBm6q6DaRjm7RGYp1FEpAhjnl86jzCv52koT%2Fwi1sEvxDRAhVkqyaMm9oikTrkX0OFL8FGE%3D
                 * imgtitleurl : contentstorage/解忧杂货店/小淘课堂/跟着小淘学汉字/imgtitle.png
                 * imgtitleurlhttpURL : http://hamitaocontent.oss-cn-hangzhou.aliyuncs.com/contentstorage/%E8%A7%A3%E5%BF%A7%E6%9D%82%E8%B4%A7%E5%BA%97/%E5%B0%8F%E6%B7%98%E8%AF%BE%E5%A0%82/%E8%B7%9F%E7%9D%80%E5%B0%8F%E6%B7%98%E5%AD%A6%E6%B1%89%E5%AD%97/imgtitle.png?Expires=1534833440&OSSAccessKeyId=STS.NHRo58HriBCZmjHRXWmZbgKjv&Signature=Lel%2B%2FhYoFbGubarBp1MIJnxF9hs%3D&security-token=CAISjAJ1q6Ft5B2yfSjIr4vnJI%2BMpa1I9YGxb0z5tlgCYdVOiI7BlDz2IHxFfXBvA%2BgbvvwxlW5S7P8elqVoRoReREvCKM1565kPVIUH0jeF6aKP9rUhpMCPKwr6UmzGvqL7Z%2BH%2BU6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj%2BwIDLkQRRLqL0AFZrFsKxBltdUROFbIKP%2BpKWSKuGfLC1dysQcO7gEa4K%2BkkMqH8Uic3h%2BoiM1t%2Ft6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB%2B%2FjPKg8qQGVE54W%2Fdb7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD%2FEVAk6c2t8rCjDGoABFtvEBLR4W0VS2iM3hBYJmoyj6%2BGGYGCAcGx4a%2FremuRZYjVnPLzwni1bRsZSatluxUr8SqpgAiqD1YUnRW2qjolplqFNS5jf%2FE6xWBm6q6DaRjm7RGYp1FEpAhjnl86jzCv52koT%2Fwi1sEvxDRAhVkqyaMm9oikTrkX0OFL8FGE%3D
                 * keywordList : ["汉字","跟着小淘学汉字"]
                 * lyric :
                 * mediaList : []
                 * nameI18List : [{"language":"zh","value":"跟着小淘学汉字"}]
                 * orientUser : {"age_max":100,"age_min":5,"sex":"any"}
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
                private String imgauthorurl;
                private String imgauthorurlhttpURL;
                private String imgtitleurl;
                private String imgtitleurlhttpURL;
                private String lyric;
                private ContentsBean.OrientUserBean orientUser;
                private String quality;
                private String scenario;
                private String sourceorigin;
                private String status;
                private String target;
                private String vendor;
                private List<String> authorList;
                private List<String> categoryList;
                private List<String> characteristicList;
                private List<ContentsBean.DescriptionI18ListBean> descriptionI18List;
                private List<String> keywordList;
                private List<?> mediaList;
                private List<ContentsBean.NameI18ListBean> nameI18List;

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

                public String getLyric() {
                    return lyric;
                }

                public void setLyric(String lyric) {
                    this.lyric = lyric;
                }

                public ContentsBean.OrientUserBean getOrientUser() {
                    return orientUser;
                }

                public void setOrientUser(ContentsBean.OrientUserBean orientUser) {
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

                public List<ContentsBean.DescriptionI18ListBean> getDescriptionI18List() {
                    return descriptionI18List;
                }

                public void setDescriptionI18List(List<ContentsBean.DescriptionI18ListBean> descriptionI18List) {
                    this.descriptionI18List = descriptionI18List;
                }

                public List<String> getKeywordList() {
                    return keywordList;
                }

                public void setKeywordList(List<String> keywordList) {
                    this.keywordList = keywordList;
                }

                public List<?> getMediaList() {
                    return mediaList;
                }

                public void setMediaList(List<?> mediaList) {
                    this.mediaList = mediaList;
                }

                public List<ContentsBean.NameI18ListBean> getNameI18List() {
                    return nameI18List;
                }

                public void setNameI18List(List<ContentsBean.NameI18ListBean> nameI18List) {
                    this.nameI18List = nameI18List;
                }
            }
        }




    }

}
