package com.rc.rclib.view.adapter;

import android.view.View;

/**
 * Created by richard on 16/3/21.
 */
public abstract class AbsBaseViewHolder {

    protected View itemView;

    public AbsBaseViewHolder(View itemView) {
        if(itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        } else {
            this.itemView = itemView;
        }
    }

    public <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }
}
