package com.gersion.smartrecycleviewlibrary;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Gersy on 2017/5/19.
 */

public interface IRVAdapter<T> {
    RecyclerView.Adapter getAdapter();

    void setNewData(List<T> data);

    void addData(List<T> data);

    void removeAll(List<T> data);

    void remove(T data);

    List<T> getData();

    void notifyDataSetChanged();
}
