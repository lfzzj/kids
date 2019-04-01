package com.hamitao.kids.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/19.
 */

public class CollectionModel {

    /**
     * responseDataObj : {"favorites":[{"category":"所在的收藏夹","description":"description","favoriteid":"favorite_05be15eebba14334a555ee53638e6ab0","info":"content","infotype":"contentid","ownerid":"sz00003_customer_0be17b4dae334821b6c2cf8dc4db5f4b","title":"title","updatetime":"2018-04-26 13:53:34"}]}
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
        private List<FavoritesBean> favorites;

        public List<FavoritesBean> getFavorites() {
            return favorites;
        }

        public void setFavorites(List<FavoritesBean> favorites) {
            this.favorites = favorites;
        }

        public static class FavoritesBean {
            /**
             * category : 所在的收藏夹
             * description : description
             * favoriteid : favorite_05be15eebba14334a555ee53638e6ab0
             * info : content
             * infotype : contentid
             * ownerid : sz00003_customer_0be17b4dae334821b6c2cf8dc4db5f4b
             * title : title
             * updatetime : 2018-04-26 13:53:34
             * headerimgurl
             */

            private String category;
            private String description;
            private String favoriteid;
            private String info;
            private String infotype;
            private String ownerid;
            private String title;
            private String updatetime;
            private String headerimgurl;

            public String getHeaderimgurl() {
                return headerimgurl;
            }

            public void setHeaderimgurl(String headerimgurl) {
                this.headerimgurl = headerimgurl;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFavoriteid() {
                return favoriteid;
            }

            public void setFavoriteid(String favoriteid) {
                this.favoriteid = favoriteid;
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
