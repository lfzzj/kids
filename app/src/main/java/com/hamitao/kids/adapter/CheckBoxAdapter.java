package com.hamitao.kids.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamitao.kids.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by linjianwen on 2018/4/2.
 */

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.MyViewHolder> {


    //这个是checkbox的Hashmap集合
    private final HashMap<Integer, Boolean> map;

    private List<String> list;
    private List<String> name;


    private boolean isSingle;


    /**
     * 构造函数
     *
     * @param isSingle 是否单选
     */
    public CheckBoxAdapter(boolean isSingle) {
        this.isSingle = isSingle;
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    public void initDeviceId(List<String> ids, List<String> name) {
        this.name = name;
        this.list = ids;
        for (int i = 0; i < ids.size(); i++) {
            map.put(i, false);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //初始化布局文件
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_text_checkbox, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //放入集合中的值
        holder.txt.setText(name.get(position));
        holder.img.setVisibility(View.VISIBLE);
        holder.img.setImageResource(R.drawable.icon_device_logo);
        holder.checkBox.setChecked(map.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                map.put(position, !map.get(position));
                //刷新适配器
                notifyDataSetChanged();
                //单选
                if (isSingle)
                    singlesel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void clearData() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }

    }

    /**
     * 全选
     */
    public void All() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        boolean shouldall = false;
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            if (!value) {
                shouldall = true;
                break;
            }
        }
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldall);
        }
        notifyDataSetChanged();
    }

    /**
     * 反选
     */
    public void neverall() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(!entry.getValue());
        }
        notifyDataSetChanged();
    }

    /**
     * 单选
     *
     * @param postion
     */
    public void singlesel(int postion) {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        map.put(postion, true);
        notifyDataSetChanged();
    }


    //返回已选中的单选项
    public int getCheckedPosition() {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            if (entry.getValue())
                return entry.getKey();
        }
        return 0;
    }


    public Map<Integer, Boolean> getCheckedMap() {
        return map;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        private ImageView img;
        private TextView txt;
        private CheckBox checkBox;

        //初始化控件
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            img = (ImageView) itemView.findViewById(R.id.iv_img);
            txt = (TextView) itemView.findViewById(R.id.tv_content);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_content);
        }
    }

}
