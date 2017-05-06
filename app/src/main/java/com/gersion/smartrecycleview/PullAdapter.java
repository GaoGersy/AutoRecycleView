package com.gersion.smartrecycleview;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by a3266 on 2017/5/6.
 */

public class PullAdapter extends BaseQuickAdapter<MainActivity.Bean, BaseViewHolder> {

    public PullAdapter(int layoutResId, @Nullable List<MainActivity.Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MainActivity.Bean bean) {
        baseViewHolder.setText(R.id.tv_content,bean.cotent);
    }
}
