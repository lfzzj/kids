package com.hamitao.kids.mvp;

import com.hamitao.base_module.base.BasePresenter;
import com.hamitao.base_module.base.BaseView;
import com.hamitao.base_module.model.DeviceRelationModel;
import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.rxbus.RxBus;
import com.hamitao.base_module.rxbus.RxBusEvent;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.base_module.util.StringUtil;
import com.hamitao.kids.model.CollectionModel;
import com.hamitao.kids.model.ContentInfoModel;
import com.hamitao.kids.model.ContentModel;
import com.hamitao.kids.model.LikeModel;
import com.hamitao.kids.model.MediaModel;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.model.requestmodel.ContentInfoRequestModel;
import com.hamitao.kids.network.NetworkRequest;

import java.util.List;

/**
 * Created by linjianwen on 2018/5/9.
 * <p>
 * 该类提供通用的方法：
 * 1-分享
 * 2-创建收藏夹
 * 3-添加收藏
 * 4-删除收藏
 * 5-投送
 * 6-推消息到机器人（极光）
 */

public class CommonPresenter implements BasePresenter {

    private CommonView view;

    public CommonPresenter(BaseView view) {
        this.view = (CommonView) view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        view = null;
        System.gc();
    }

    /**
     * 创建主题（发帖子）分享到广场
     *
     * @param creatorid   customerid
     * @param createName  用户昵称
     * @param title       标题
     * @param description 描述（用户输入的）
     * @param imgUrl      专辑图片
     * @param forumId     forum_id,哪个论坛下面的主题
     * @param keywords    关键字
     * @param infoType    可选值："contentid"/"recordid"/"coursesheduleid" 即 内容id/录音id/课程id
     * @param info        具体 ID
     */
    public void shareToSquare(String creatorid, String createName, String title, String description, String imgUrl, String forumId, String[] keywords, String infoType, String info) {
        NetworkRequest.createTopicRequest(creatorid, createName, title, description, imgUrl, forumId, keywords, infoType, info, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.onMessageShow("分享成功");
                //分享成功后刷新广场数据
                RxBus.getInstance().post(RxBusEvent.REFRESH_SQUARE_LIST);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
            }
        }));
    }


    /**
     * 创建收藏夹
     */
    public void createClip(String customerId, final String clipName) {
        NetworkRequest.createClipRequest(customerId, clipName, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                //这里做偷懒，直接刷新列表
                view.refreshData("createclip", clipName, null);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }


    /**
     * 添加收藏
     */
    public void addCollection(final String category, String description, String img, String info, final String infotype, String mediatype, String ownerid, String title) {
        NetworkRequest.addCollection(category, description, img, info, infotype, mediatype, ownerid, StringUtil.deleteNumber(title), new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                CollectionModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), CollectionModel.ResponseDataObjBean.class);

                if (bean != null && bean.getFavorites().size() == 0) {
                    view.onMessageShow("内容已存在！");
                }
                view.refreshData(infotype.equals("contentid") ? "addcollect" : "addcollectmedia", bean.getFavorites().get(0).getFavoriteid(), category);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }


    /**
     * 删除收藏
     */
    public void deleteCollection(String ownerid, String favoriteid) {
        NetworkRequest.deleteCollectionRequest(ownerid, favoriteid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.refreshData("deletecollect", null, null);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }

    /**
     * 添加投送
     *
     * @param creator     这里指  customerid
     * @param targetid    这里指  terminalid
     * @param infotype    投送的信息类型，可以为： contentid/recordid 等
     * @param title       标题
     * @param description 描述
     */
    public void addDeliver(String creator, String targetid, String infotype, String info, String title, String description, final String targetChannelId, final String[] pushContentid) {
        NetworkRequest.addDeliverRequest(creator, description, infotype, info, title, targetid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                view.onMessageShow("投送成功");
                pushMessage(targetChannelId, "PLAY_CONTENT", pushContentid);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }

    /**
     * 消息推送远程请求
     *
     * @param targetChannelid
     * @param actionType
     * @param contentid       这里是播放内容，OSS上的名字
     */
    public void pushMessage(String targetChannelid, String actionType, String[] contentid) {
        NetworkRequest.pushMessageRequest(targetChannelid, actionType, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }

            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
            }
        }));
    }

    /**
     * 点赞
     *
     * @param creatorid
     * @param createName
     * @param description
     * @param onwhat_infotype
     * @param onwhat_info
     */
    public void like(String creatorid, String createName, String description, String onwhat_infotype, String onwhat_info, final int position) {
        NetworkRequest.likeRequest(creatorid, createName, description, onwhat_infotype, onwhat_info, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                LikeModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), LikeModel.ResponseDataObjBean.class);
                view.refreshData("like", bean.getMylike_id(), position);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


    /**
     * 取消点赞
     *
     * @param like_id
     */
    public void unlike(String like_id, final int positino) {
        NetworkRequest.unlikeRequest(like_id, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }

                view.refreshData("unlike", null, positino);

            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }


    /**
     * 添加到播放记录
     *
     * @param ownid
     * @param infotype
     * @param info
     * @param title
     * @param description
     */
    public void addPlayRecord(String ownid, String infotype, String info, String auxinfo, String title, String headerimgurl, String description) {
        NetworkRequest.addPlayRecordRequest(ownid, infotype, info, auxinfo, title, headerimgurl, description, ownid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
//                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
//                view.onFinish();
            }
        }));
    }


    public void queryRecordById(final String recordid) {
        NetworkRequest.queryRecordByIdRequest(recordid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                RecordModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), RecordModel.ResponseDataObjBean.class);
                view.refreshData("record", bean, null);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


    /**
     * 通过ID查音乐
     */
    public void queryMediaById(String customerid, String id, final int position) {
        NetworkRequest.queryMediaByIdRequest(customerid, id, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                MediaModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), MediaModel.ResponseDataObjBean.class);
                //查询成功后播放音乐
                view.refreshData("media", bean, position);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }


    /**
     * 获取音乐列表
     *
     * @param contentid
     */
    public void queryMediaList(String customerid, String contentid, final int position) {
        NetworkRequest.queryContentByIdRequest(customerid, contentid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ContentModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ContentModel.ResponseDataObjBean.class);
                view.refreshData("content", bean, position);
            }


            @Override
            public void onFail(NetWorkResult result, String msg) {
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                view.onBegin();
            }

            @Override
            public void onEnd() {
                view.onFinish();
            }
        }));
    }


    public void queryContentByInfo(final List<ContentInfoRequestModel> models) {

        NetworkRequest.queryContentByInfoRequest(models, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ContentInfoModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ContentInfoModel.ResponseDataObjBean.class);
                view.refreshData("info", bean, models.get(0).getInfotype());
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {
                if (view == null) {
                    return;
                }
                view.onMessageShow(msg);
            }

            @Override
            public void onBegin() {
                if (view == null) {
                    return;
                }
                view.onBegin();
            }

            @Override
            public void onEnd() {
                if (view == null) {
                    return;
                }
                view.onFinish();
            }
        }));
    }


    /**
     * 查询绑定关系
     *
     * @param customerid
     */
    public void queryRelationById(String customerid) {
        NetworkRequest.queryDeviceRelationRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {

                DeviceRelationModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), DeviceRelationModel.ResponseDataObjBean.class);

                view.refreshData("query_Relation", bean, null);
            }

            @Override
            public void onFail(NetWorkResult result, String msg) {

            }

            @Override
            public void onBegin() {

            }

            @Override
            public void onEnd() {

            }
        }));
    }


}
