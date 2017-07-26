package com.gersion.smartrecycleviewlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gersion.smartrecycleviewlibrary.ptr2.IRVAdapter;
import com.gersion.smartrecycleviewlibrary.ptr2.PullToRefreshLayout;

/**
 * Created by a3266 on 2017/7/9.
 */

public class SmartRecycler {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int mOretation;
    private int mSpanCount;
    private PullToRefreshLayout.OnRefreshListener mRefreshListener;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mFailedView;
    private View mNoDataView;
    private View mLoadingView;
    private int mPageSize = 20;
    private boolean mIsLoadMore;
    private SmartRecycleView.LayoutManagerType mLayoutManagerType;
    private IRVAdapter mAdapter;
    private int currentPage = 0;
    private int firstPage;//第一页的序号
    private boolean isFirstLoad = true;//第一次初始化加载
    private boolean autoRefresh = false;//第一次初始化加载
    private boolean isRefresh = true;//判断是不是下拉刷新，不然就是上拉加载
    private SmartRecycleView mSmartRecycleView;

    private SmartRecycler(Builder builder) {
        this.mContext = builder.mContext;
        this.mFailedView = builder.mFailedView;
        this.mNoDataView = builder.mNoDataView;
        this.mLoadingView = builder.mLoadingView;
        this.mPageSize = builder.mPageSize;
        this.mIsLoadMore = builder.mIsLoadMore;
        this.mLayoutManagerType = builder.mLayoutManagerType;
        this.mAdapter = builder.mAdapter;
        this.firstPage = builder.firstPage;
        this.isFirstLoad = builder.isFirstLoad;
        this.isRefresh = builder.isRefresh;
        this.autoRefresh = builder.autoRefresh;
        this.mRefreshListener = builder.mRefreshListener;
        this.mOretation = builder.mOretation;
        this.mSpanCount = builder.mSpanCount;

        if (mContext == null) {
            throw new NullPointerException("context 不能为空");
        }

        mSmartRecycleView = new SmartRecycleView(mContext);
        mSmartRecycleView.setAdapter(mAdapter)
                .setAutoRefresh(autoRefresh)
                .loadMoreEnable(mIsLoadMore)
                .refreshEnable(isRefresh)
                .setFailedView(mFailedView)
                .setLoadingView(mLoadingView)
                .setNoDataView(mNoDataView)
                .setLayoutManger(mLayoutManagerType,mOretation,mSpanCount)
                .setFirstPage(firstPage)
                .setPageSize(mPageSize)
                .setRefreshListener(mRefreshListener);
    }

    private SmartRecycleView into(ViewGroup container){
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(mSmartRecycleView,params);
        return mSmartRecycleView;
    }

    public static class Builder {
        private Context mContext;
        private View mFailedView;
        private View mNoDataView;
        private View mLoadingView;
        private int mPageSize = 20;
        private int mOretation = VERTICAL;
        private int mSpanCount = 2;
        private boolean mIsLoadMore;
        private SmartRecycleView.LayoutManagerType mLayoutManagerType = SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT;
        private IRVAdapter mAdapter;
        private int firstPage;//第一页的序号
        private boolean isFirstLoad = true;//第一次初始化加载
        private boolean autoRefresh;
        private boolean isRefresh = true;//判断是不是下拉刷新，不然就是上拉加载
        private ViewGroup mContainer;
        private PullToRefreshLayout.OnRefreshListener mRefreshListener;

        public Builder(Context context,ViewGroup container, IRVAdapter adapter) {
            if (context == null) {
                throw new NullPointerException("context 不能为空");
            }
            if (adapter == null) {
                throw new NullPointerException("adapter不能为空");
            }
            if (container==null) {
                throw new NullPointerException(" container 不能为空");
            }
            this.mContext = context;
            this.mAdapter = adapter;
            this.mContainer = container;
        }

        public Builder setFailedView(View failedView) {
            mFailedView = failedView;
            return this;
        }

        public Builder setNoDataView(View noDataView) {
            mNoDataView = noDataView;
            return this;
        }

        public Builder setLoadingView(View loadingView) {
            mLoadingView = loadingView;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            mPageSize = pageSize;
            return this;
        }

        public Builder setLoadMore(boolean loadMore) {
            mIsLoadMore = loadMore;
            return this;
        }

        public Builder setLayoutManagerType(SmartRecycleView.LayoutManagerType layoutManagerType) {
            mLayoutManagerType = layoutManagerType;
            return this;
        }

        public Builder setFirstPage(int firstPage) {
            this.firstPage = firstPage;
            return this;
        }

        public Builder setFirstLoad(boolean firstLoad) {
            isFirstLoad = firstLoad;
            return this;
        }

        public Builder setRefresh(boolean refresh) {
            isRefresh = refresh;
            return this;
        }

        public Builder setAutoRefresh(boolean autoRefresh) {
            this.autoRefresh = autoRefresh;
            return this;
        }

        public Builder setOretation(int oretation) {
            mOretation = oretation;
            return this;
        }

        public Builder setSpanCount(int spanCount) {
            mSpanCount = spanCount;
            return this;
        }

        public Builder setRefreshListener(PullToRefreshLayout.OnRefreshListener refreshListener) {
            mRefreshListener = refreshListener;
            return this;
        }

        public SmartRecycleView build() {
            return new SmartRecycler(this).into(mContainer);
        }
    }
}
