package com.hamitao.kids.mvp.contact;

import com.hamitao.kids.model.ContactModel.ResponseDataObjBean.ContactsBean;
import com.hamitao.kids.mvp.CommonView;

import java.util.List;

/**
 * Created by linjianwen on 2018/4/16.
 */

public interface ContactView extends CommonView {


    /**
     * 获取联系人列表
     */
    void getContactList(List<ContactsBean> contacts);

    /**
     * 添加联系人
     */
    void addContact(ContactsBean contactsBean);


    /**
     * 删除联系人后刷新列表，不用再次网络请求刷新列表,  1, 添加联系人  2，
     */
    void deleteContact(int position);

    /**
     * 刷新联系人
     */
    void refreshContact(int position, String name, String phone);
}
