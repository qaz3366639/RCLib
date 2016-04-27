package com.rc.rclib.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by richard on 16/3/18.
 */
public abstract class BaseRecycleAdapter<H, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<H> dataList;

    protected Context mContext;

    private IRecycleItemClickListener itemClickListener;

    public int lastChoicePosition = -1;//上一次点击的position

    protected int pageMaxItemSize = 6;//每页显示几个,默认6个

    protected boolean isMultiChoiceEnable = false;//是否为多选

    protected boolean isSupportRepeat = true;//是否可以重复点击

    protected SparseBooleanArray sparseBooleanArray;

    public BaseRecycleAdapter() {
    }

    public BaseRecycleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRecycleAdapter(List<H> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    public BaseRecycleAdapter(List<H> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public List<H> getDataList() {
        return dataList;
    }

    public void setDataList(List<H> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(final V v, int i) {
        if (itemClickListener != null) {
            v.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performItemClickListener(v.getAdapterPosition(), view);
                }
            });
        }
        boolean isSelected = isMultiChoiceEnable ? sparseBooleanArray.get(i, false) :
                lastChoicePosition == i;
        onBindViewHolder(v, i, isSelected);
    }

    public abstract void onBindViewHolder(final V v, int i, boolean isSelected);

    @Override
    public V onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        return null;
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    public boolean isMultiChoiceEnable() {
        return isMultiChoiceEnable;
    }

    public void setMultiChoiceEnable(boolean multiChoiceEnable) {
        if (isMultiChoiceEnable && sparseBooleanArray == null) {
            sparseBooleanArray = new SparseBooleanArray();
        }
        isMultiChoiceEnable = multiChoiceEnable;
    }

    public void setItemClickListener(IRecycleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void performItemClickListener(int position) {
        performItemClickListener(position, null);
    }

    private void performItemClickListener(int position, View view) {
        if (itemClickListener == null) {
            return;
        }
        if (!isSupportRepeat && position == lastChoicePosition) {
            return;
        }
        if (itemClickListener.onClick(view, position) && position != lastChoicePosition) {
            if (isMultiChoiceEnable) {
                sparseBooleanArray.put(position, !sparseBooleanArray.get(position, false));
            }
            lastChoicePosition = position;
            notifyDataSetChanged();
        }
    }

    public void setSupportRepeat(boolean supportRepeat) {
        isSupportRepeat = supportRepeat;
    }

    public void setPageMaxItemSize(int pageMaxItemSize) {
        this.pageMaxItemSize = pageMaxItemSize;
    }

    public int getNextPage() {
        int nextPage = getItemCount() / pageMaxItemSize;
        return nextPage == 0 ? nextPage : ++nextPage;
    }

    public interface IRecycleItemClickListener {
        /**
         * item点击事件回调,如果是手动调用的itemClick则view返回的是null
         * @param view 点击的view
         * @param position 点击位置
         * @return 返回true:已经消费该点击事件,会记录该点击位置false则不会
         */
        boolean onClick(View view, int position);
    }
}
