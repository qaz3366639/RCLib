package com.rc.rclib.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by richard on 16/3/18.
 */
public abstract class BaseAdapter<T extends AbsBaseViewHolder, V> extends android.widget.BaseAdapter {

    protected List<V> dataList;

    protected Context mContext;

    public BaseAdapter() {
    }

    public BaseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseAdapter(Context mContext, List<V> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    public BaseAdapter(Context mContext, V... dataList) {
        this.dataList = Arrays.asList(dataList);
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<V> getDataList() {
        return dataList;
    }

    public void setDataList(List<V> dataList) {
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T holder;
        if (convertView == null) {
            holder = getHolder(position, parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (T) convertView.getTag();
        }
        showView(position, convertView, parent, holder);
        return convertView;
    }

    public abstract @NonNull
    T  getHolder(int position, ViewGroup parent);

    public abstract void showView(int position, View convertView, ViewGroup parent,@NonNull T holder);

}
