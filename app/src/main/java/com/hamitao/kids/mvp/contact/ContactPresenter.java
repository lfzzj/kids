package com.hamitao.kids.mvp.contact;

import com.hamitao.base_module.network.NetWorkCallBack;
import com.hamitao.base_module.network.NetWorkResult;
import com.hamitao.base_module.util.GsonUtil;
import com.hamitao.kids.model.ContactModel;
import com.hamitao.kids.mvp.CommonPresenter;
import com.hamitao.kids.network.NetworkRequest;

/**
 * Created by linjianwen on 2018/4/16.
 */

public class ContactPresenter extends CommonPresenter {

    private ContactView view;

    public ContactPresenter(ContactView view) {
        super(view);
        this.view = view;
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
     * 查询联系人
     */
    public void queryContact(String customerid) {
        NetworkRequest.queryContactRequest(customerid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                if (view == null) {
                    return;
                }
                ContactModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ContactModel.ResponseDataObjBean.class);
                view.getContactList(bean.getContacts());
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
     * 添加联系人
     */
    public void addContact(String customerid, String contactName, String phoneNum, int type) {
        NetworkRequest.addContactRequest(customerid, contactName, phoneNum, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                ContactModel.ResponseDataObjBean bean = GsonUtil.GsonToBean(result.getResponseDataObj(), ContactModel.ResponseDataObjBean.class);
                view.addContact(bean.getContacts().get(0));
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


    /**
     * 删除联系人
     */
    public void deleteContact(String customerid, String contactid, final int position) {
        NetworkRequest.deleteContactRequest(customerid, contactid, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.deleteContact(position);
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


    /**
     * 更新联系人
     */
    public void updateContact(final int position, String customerid, String contactid, final String contactName, final String phoneNum) {
        NetworkRequest.updateContactRequest(customerid, contactid, contactName, phoneNum, new NetWorkCallBack(new NetWorkCallBack.BaseCallBack() {
            @Override
            public void onSuccess(NetWorkResult result) {
                view.refreshContact(position, contactName, phoneNum);
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
}
