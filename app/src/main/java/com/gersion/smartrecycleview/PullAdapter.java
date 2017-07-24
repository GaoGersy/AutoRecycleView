package com.gersion.smartrecycleview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gersion.smartrecycleviewlibrary.ptr2.IRVAdapter;

import java.util.List;

/**
 * Created by a3266 on 2017/5/6.
 */

public class PullAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> implements IRVAdapter {

    public PullAdapter(int layoutResId, @Nullable List<Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Bean bean) {
        baseViewHolder.setText(R.id.tv_content,bean.cotent);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List data) {
        mData.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Object data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public List<Bean> getData() {
        return mData;
    }
}
