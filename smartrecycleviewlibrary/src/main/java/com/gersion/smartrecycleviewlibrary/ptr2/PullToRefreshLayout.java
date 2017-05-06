package com.gersion.smartrecycleviewlibrary.ptr2;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;


/**
 * 下拉刷新控件，可以配合 RecyclerView，Scrollview，ListView
 * Created by fish on 16/5/17.
 */
public class PullToRefreshLayout extends SuperSwipeRefreshLayout {

    private boolean mLoadMoreEnable;
    private boolean mNoMoreData;
    private CustomLoadLayout refreshView;
    private CustomLoadLayout loadMoreView;
    private OnRefreshListener listener;
    private int mFirstPage;
    private int mCurrentPage;

    public PullToRefreshLayout(Context context) {
        super(context);
        initLoadingView(true, true);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLoadingView(true, true);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    //一般用于进页面第一次刷新
    public void autoRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                setRefreshing(true);
//                refreshView.onRefreshing();
                if (listener != null) {
                    listener.onRefresh(mFirstPage);
                }
            }
        }, 100);
    }

    public void onLoadMoreSuccess() {
        loadMoreView.onLoadSuccess();
        setLoadMore(false);
    }

    public void onLoadMoreNoData() {
        loadMoreView.onLoadNoData();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setLoadMore(false);
            }
        },1000);
    }

    public void onRefreshSuccess() {
        refreshView.onRefreshSuccess();
        setRefreshing(false);
        mNoMoreData = false;
    }

    public void onLoadMoreErr() {
        loadMoreView.onLoadError();
    }

    public void onRefreshErr() {
        refreshView.onRefreshError();
    }

    public void initLoadingView(boolean pullDown, boolean pullUp) {
        if (pullDown) {
            refreshView = new CustomLoadLayout(getContext());
            setHeaderView(refreshView);
            setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                @Override
                public void onRefresh() {
                    refreshView.onRefreshing();
                    if (listener != null) {
                        listener.onRefresh(mFirstPage);
                    }
                }

                @Override
                public void onPullDistance(int distance) {
//                    if (distance == 0) {
//                        refreshView.reset();
//                    }
//                    refreshView.onPull(distance * 1.0f / refreshView.getContentSize());
                }

                @Override
                public void onPullEnable(boolean enable) {
                    refreshView.onPullRefreshEnable(enable);
                }
            });
        }

        if (pullUp) {
            loadMoreView = new CustomLoadLayout(getContext());
            setFooterView(loadMoreView);
            setOnPushLoadMoreListener(new OnPushLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (mNoMoreData) {
                        onLoadMoreNoData();
                    } else {
                        loadMoreView.onLoading();
                        if (listener != null) {
                            listener.onLoadMore(mCurrentPage);
                        }
                    }
                }

                @Override
                public void onPushDistance(int distance) {
//                    if (distance == 0) {
//                        loadMoreView.reset();
//                    }
//                    loadMoreView.onPull(distance * 1.0f / loadMoreView.getContentSize());
                }

                @Override
                public void onPushEnable(boolean enable) {
                    if (mNoMoreData) {
                        loadMoreView.onPullLoadMoreNoData();
                    } else {
                        loadMoreView.onPullLoadMoreEnable(enable);
                    }
                }
            });
        }

    }

    /*
    * ~~ 时间：2017/5/6 16:36 ~~
    * 设置网络请求第一页的数字
    **/
    public void setFirstPage(int firstPage){
        mFirstPage = firstPage;
    }

    /*
    * ~~ 时间：2017/5/6 16:39 ~~
    * 设置当前的页数
    **/
    public void setCurrentPage(int currentPage){
        mCurrentPage = currentPage;
    }

    /*
    * ~~ 时间：2017/5/6 15:09 ~~
    * 开启上拉加载
    **/
    public void setLoadMoreEnable(boolean enable) {
        mLoadMoreEnable = enable;
        setPullUpEnable(enable);
    }

    /*
    * ~~ 时间：2017/5/6 15:10 ~~
    * 开启下拉刷新
    **/
    public void setRefreshEnable(boolean enable) {
        setPullDownEnable(enable);
    }

    /*
    * ~~ 时间：2017/5/6 14:49 ~~
    * 设置是否还有更多数据
    **/
    public void setNoMoreData(boolean noMoreData) {
        mNoMoreData = noMoreData;
        setLoadMore(false);
    }

    public void setOnRertyListener(final OnRefreshListener listener) {
        refreshView.setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRefreshRetry() {
                refreshView.onRefreshing();
                if (listener != null) {
                    listener.onRefresh(mFirstPage);
                }
            }

            @Override
            public void onLoadMoreRetry() {

            }
        });
        loadMoreView.setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRefreshRetry() {

            }

            @Override
            public void onLoadMoreRetry() {
                loadMoreView.onLoading();
                if (listener != null) {
                    listener.onLoadMore(mCurrentPage);
                }
            }
        });
    }

    public interface OnRefreshListener {
        void onRefresh(int page);

        void onLoadMore(int page);
    }

}
