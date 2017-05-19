package com.gersion.smartrecycleview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gersion.smartrecycleviewlibrary.IRVAdapter;

import java.util.List;

/**
 * Created by a3266 on 2017/5/6.
 */

public class PullAdapter extends BaseQuickAdapter<MainActivity.Bean, BaseViewHolder> implements IRVAdapter<MainActivity.Bean> {

    public PullAdapter(int layoutResId, @Nullable List<MainActivity.Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MainActivity.Bean bean) {
        baseViewHolder.setText(R.id.tv_content,bean.cotent);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void removeAll(List<MainActivity.Bean> data) {
        getData().removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(MainActivity.Bean data) {
        getData().remove(data);
        notifyDataSetChanged();
    }
}
