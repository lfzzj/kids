package com.hamitao.kids.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.hamitao.base_module.base.BaseActivity;
import com.hamitao.base_module.util.ToastUtil;
import com.hamitao.base_module.util.UserUtil;
import com.hamitao.kids.R;
import com.hamitao.kids.model.ClipModel;
import com.hamitao.kids.model.CollectContentModel;
import com.hamitao.kids.model.ContentList;
import com.hamitao.kids.model.ContentListItem;
import com.hamitao.kids.model.MenuList;
import com.hamitao.kids.model.MenuListItem;
import com.hamitao.kids.model.RecordModel;
import com.hamitao.kids.mvp.editschedule.EditSchedulePresenter;
import com.hamitao.kids.mvp.editschedule.EditScheduleView;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.base_module.RequestCode.REQUEST_EDIT_SCHEDULE;

/**
 * 编辑课表
 */

@Route("edit_schedule")
public class EditScheduleActivity extends BaseActivity implements EditScheduleView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private final int ITEM_TYPE_MENULIST = 1; //菜单列表项
    private final int ITEM_TYPE_CONTENTLIST = 2;    //内容列表项
    private BaseExpandableAdapter expandableAdapter; //缩放适配器

    private List<MenuList> menulist = new ArrayList<>(); //用于存放菜单选项列表名

    private int coursePosition;//课程位置

    private EditSchedulePresenter presenter;


//    private ExpandableListView expandableListView;
//    private Myadapter myadapter;
//    private HashMap<String, Boolean> statusHashMap;
//    public String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
//    public String[][] childStrings = {
//            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
//            {"宋江", "林冲", "李逵", "鲁智深"},
//            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
//            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        ButterKnife.bind(this);
        initData();
        initView();

//        initExpandList();
    }

//    private void initExpandList() {
//
//        myadapter = new Myadapter();
//        expandableListView = findViewById(R.id.expandlist);
//
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        expandableListView.setIndicatorBounds(width-40, width-10);
//
//
//        expandableListView.setAdapter(myadapter);
//
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
//
//                int gourpsSum = myadapter.getGroupCount();
//
//                for (int i = 0; i < gourpsSum; i++) {
//
//                    int childsSum = myadapter.getChildrenCount(i);
//
//                    for (int j = 0; j < childsSum; j++) {
//
//                        boolean isLast = false;
//
//                        if (j == (childsSum - 1)) {
//                            isLast = true;
//                        }
//
//                        CheckBox cBox = ((CheckBox) myadapter.getChildView(i, j, isLast, null, null).findViewById(R.id.cb_content));
//
//                        if (cBox.isChecked()){
//                            cBox.setChecked(false);
//                        }else {
//                            cBox.setChecked(true);
//                        }
//
//                        boolean itemIsCheck = cBox.isChecked();
//
//                        TextView textView = ((TextView) myadapter.getChildView(i, j, isLast, null, null).findViewById(R.id.tv_content));
//                        String itemName = textView.getText().toString();
//
//                        if (i == groupPosition && j == childPosition) {
//                            statusHashMap.put(itemName, itemIsCheck);
//                        } else {
//                            statusHashMap.put(itemName, false);
//                        }
//
//                        ((BaseExpandableListAdapter) myadapter).notifyDataSetChanged();//通知数据发生了变化
//                    }
//                }
////                ((CheckBox) myadapter.getChildView(groupPosition, childPosition, false, null, null).findViewById(R.id.cb_content)).setChecked(true);
//                return true;
//            }
//        });
//    }

    private void initData() {
        coursePosition = getIntent().getIntExtra("position", 0);
        presenter = new EditSchedulePresenter(this);
        presenter.queryMyAllCollection(UserUtil.user().getCustomerid()); //查询所有收藏
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.stop();
            presenter = null;
            System.gc();
        }
        super.onDestroy();
    }

    //创建菜单
    private MenuList createMenu(String menuName, boolean isExpandDefault, List<ContentList> contentLists) {
        MenuList menu = new MenuList();
        menu.name = menuName;
        menu.mContentList = contentLists;
        menu.mExpanded = isExpandDefault;
        return menu;
    }

    private void initView() {
        title.setText("添加课程");
    }

    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                if (getCourse() != null) {
                    String courseName = getCourse().getContent_name().getTitle();
                    String info = getCourse().getContent_name().getInfo();
                    String infotype = getCourse().getContent_name().getInfotype();
                    Intent it = new Intent();
                    it.putExtra("position", coursePosition);
                    it.putExtra("courseName", courseName);
                    it.putExtra("info", info);
                    it.putExtra("infotype", infotype);
                    setResult(REQUEST_EDIT_SCHEDULE, it);
                }
                finish();
                break;
            default:
                break;
        }
    }


    private ContentList getCourse() {
        //当网络请求失败的时候，exandableAdapter 为空，需做判空处理
        if (expandableAdapter == null) {
            return null;
        }
        List<Object> list = (List<Object>) expandableAdapter.getDataList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof MenuList) {
                for (int j = 0; j < ((MenuList) list.get(i)).getChildItemList().size(); j++) {
                    if (((MenuList) list.get(i)).getChildItemList().get(j).isSelected()) {
                        return ((MenuList) list.get(i)).getChildItemList().get(j);
                    }
                }
            }
        }
        return null;
    }


    @Override
    public void onBegin() {
        showProgressDialog();
    }

    @Override
    public void onFinish() {
        dismissProgressDialog();
    }

    @Override
    public void onMessageShow(String msg) {
        ToastUtil.showShort(msg);
    }


    /**
     * 初始化收藏夹
     * @param clip
     * @param collection
     * @param record
     */
    @Override
    public void initClip(ClipModel.ResponseDataObjBean clip, CollectContentModel.ResponseDataObjBean collection, RecordModel.ResponseDataObjBean record) {
        List<ContentList> list;
        for (int i = 0; i < clip.getFavoriteCategorys().size(); i++) {
            // 有多少个收藏夹就创建多少个菜单列表
            list = new ArrayList<>();
            for (int j = 0; j < collection.getFavorites().size(); j++) {
                if (clip.getFavoriteCategorys().get(i).equals(collection.getFavorites().get(j).getCategory())) {
                    list.add(new ContentList(collection.getFavorites().get(j)));
                }
            }
            MenuList menu = new MenuList();
            menu.name = clip.getFavoriteCategorys().get(i) + "(" + list.size() + "首)";
            menu.mExpanded = false;
            menu.mContentList = list;
            menulist.add(menu);
        }


        list = new ArrayList<>();

        //新建一个录音夹菜单
        for (int i = 0; i < record.getVoiceRecordings().size(); i++) {
            RecordModel.ResponseDataObjBean.VoiceRecordingsBean recordingsBean = record.getVoiceRecordings().get(i);
            CollectContentModel.ResponseDataObjBean.FavoritesBean bean = new CollectContentModel.ResponseDataObjBean.FavoritesBean();
            bean.setTitle(recordingsBean.getName());
            bean.setCategory("录音夹");
            bean.setDescription(recordingsBean.getDescription());
            bean.setInfo(recordingsBean.getId());
            bean.setInfotype("recordid");
            bean.setOwnerid(recordingsBean.getOwnerid());
            list.add(new ContentList(bean));
        }
        menulist.add(createMenu("录音夹(" + record.getVoiceRecordings().size() + "首)", false, list));


        expandableAdapter = new BaseExpandableAdapter(menulist) {
            @NonNull
            @Override
            public AbstractAdapterItem<Object> getItemView(Object type) {
                int itemType = (int) type;
                switch (itemType) {
                    case ITEM_TYPE_MENULIST:
                        //菜单
                        MenuListItem item = new MenuListItem();
                        item.setChangeListener(new MenuListItem.ChangeListener() {
                            @Override
                            public void onChange(int contentSize) {
                                if (contentSize<= 0){
                                    ToastUtil.showShort(item.getMenuName().contains("录音夹")?"录音夹为空":"该收藏夹为空");
                                }else {
                                    expandableAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        return item;
                    case ITEM_TYPE_CONTENTLIST:
                        //内容
                        final ContentListItem listItem = new ContentListItem();
                        listItem.setClearListener(new ContentListItem.ClearListener() {
                            @Override
                            public void clear(int position) {
                                //清空所有已选中的内容
                                for (int i = 0; i < expandableAdapter.getDataList().size(); i++) {
                                    if (expandableAdapter.getDataList().get(i) instanceof ContentList) {
                                        ((ContentList) expandableAdapter.getDataList().get(i)).setSelected(false);
                                    }
                                }
                                ((ContentList) expandableAdapter.getDataList().get(position)).setSelected(true);

                                expandableAdapter.notifyDataSetChanged();

                            }
                        });
                        return listItem;
                }
                return null;
            }

            //必须要这个方法，否则报空
            @Override
            public Object getItemViewType(Object t) {
                if (t instanceof MenuList) {
                    return ITEM_TYPE_MENULIST;
                } else if (t instanceof ContentList)
                    return ITEM_TYPE_CONTENTLIST;
                return -1;
            }
        };

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setVerticalScrollBarEnabled(true);//垂直滑动条
        recyclerview.setAdapter(expandableAdapter);
    }


/*    class Myadapter extends BaseExpandableListAdapter {

        public Myadapter() {
            statusHashMap = new HashMap<String, Boolean>();
            for (int i = 0; i < childStrings.length; i++) {// 初始时,让所有的子选项均未被选中
                for (int j = 0; j < childStrings[i].length; j++) {
                    statusHashMap.put(childStrings[i][j], false);
                }
            }
        }

        @Override
        public int getGroupCount() {
            return groupStrings.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return childStrings.length;
        }

        @Override
        public Object getGroup(int i) {
            return groupStrings[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return childStrings[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(EditScheduleActivity.this).inflate(R.layout.item_menu_name, parent, false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_menu_name);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            groupViewHolder.tvTitle.setText(groupStrings[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(EditScheduleActivity.this).inflate(R.layout.item_contentlist, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_content);
                childViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_content);
                Boolean nowStatus = statusHashMap.get((childStrings[groupPosition][childPosition]));//当前状态
                childViewHolder.checkBox.setChecked(nowStatus);
                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.tvTitle.setText(childStrings[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        private class ChildViewHolder {
            TextView tvTitle;
            CheckBox checkBox;
        }

        private class GroupViewHolder {
            TextView tvTitle;

        }
    }*/
}
