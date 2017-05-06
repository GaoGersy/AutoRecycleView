package com.gersion.smartrecycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.gersion.smartrecycleviewlibrary.SmartRecycleView;


/**
 * Created by a3266 on 2017/5/6.
 */

public class TestView extends SmartRecycleView {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View setFailedView() {
        return null;
    }

    @Override
    public View setNoDataView() {
        return null;
    }

    @Override
    public View setLoadingView() {
        return null;
    }
}
