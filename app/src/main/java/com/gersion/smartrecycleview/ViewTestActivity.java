package com.gersion.smartrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gersion.smartrecycleviewlibrary.SmartRecycleView;
import com.gersion.smartrecycleviewlibrary.ptr2.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewTestActivity extends AppCompatActivity {

    private ArrayList<Bean> data = new ArrayList<>();
    private PullAdapter mAdapter;
    private SmartRecycleView mSmartRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        mAdapter = new PullAdapter(R.layout.item_show, data);
        mSmartRecycleView = (SmartRecycleView) findViewById(R.id.testView);

        mSmartRecycleView.setFirstPage(1)
                .setAutoRefresh(true)
                .setPageSize(17)
                .setAdapter(mAdapter)
                .loadMoreEnable(true)
                .refreshEnable(true)
                .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT)
                .setRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh(int page) {
                        getList(page);
                    }

                    @Override
                    public void onLoadMore(final int page) {
                        getList(page);
                    }
                });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                List<Bean> data = baseQuickAdapter.getData();
                Toast.makeText(ViewTestActivity.this, data.get(i).cotent, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getList(final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Bean> list = new ArrayList<>();
                if (page == 3) {
                } else {
                    int num = 17 * page;
                    for (int i = num - 17; i < num; i++) {
                        Bean bean = new Bean();
                        bean.cotent = "第" + i + "页";
                        list.add(bean);
                    }
                }
                mSmartRecycleView.handleData(list);
            }
        }, 3000);
    }


}
