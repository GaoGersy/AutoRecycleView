package com.gersion.smartrecycleviewlibrary.ptr2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gersion.smart.R;


/**
 * Created by a3266 on 2017/5/6.
 */

public class CustomLoadLayout extends LinearLayout {

    private TextView mTvRefresh;
    private OnRetryListener mListener;
    private ImageView mIvNarrow;
    private ProgressBar mPbLoading;
    private boolean isLoadMoreRetry;//上拉加载重试
    private boolean isRefreshRetry;//下拉刷新重试
    private boolean isCanRetry;//下拉刷新重试

    public CustomLoadLayout(Context context) {
        this(context,null);
    }

    public CustomLoadLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_load_more, this);
        mTvRefresh = (TextView) findViewById(R.id.tv_load_more);
        mPbLoading = (ProgressBar) findViewById(R.id.footer_pb_view);
        mIvNarrow = (ImageView) findViewById(R.id.footer_image_view);

        init();
        initListener();
    }

    private void init() {
        mIvNarrow.setVisibility(View.VISIBLE);
        mIvNarrow.setImageResource(R.drawable.down_arrow);
        mPbLoading.setVisibility(View.GONE);
    }

    private void initListener() {
        mTvRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanRetry) {
                    isCanRetry = false;
                    if (mListener != null) {
                        if (isLoadMoreRetry) {
                            mListener.onLoadMoreRetry();
                        }else if (isRefreshRetry){
                            mListener.onRefreshRetry();
                        }
                    }
                }
            }
        });
    }

    public void onPullRefreshEnable(boolean enable){
        mTvRefresh.setText(enable ? "松开刷新" : "下拉刷新");
        mIvNarrow.setVisibility(View.VISIBLE);
        mIvNarrow.setRotation(enable ? 180:0);
    }

    public void onPullLoadMoreEnable(boolean enable){
        mTvRefresh.setText(enable ? "松开加载" : "上拉加载");
        mIvNarrow.setVisibility(View.VISIBLE);
        mIvNarrow.setRotation(enable ? 0 : 180);
    }

    public void onPullLoadMoreNoData(){
        mPbLoading.setVisibility(GONE);
        mIvNarrow.setVisibility(GONE);
        mTvRefresh.setText("没有更多数据了");
    }

    public void onRefreshing(){
        mIvNarrow.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.VISIBLE);
        mTvRefresh.setText("正在刷新...");
    }

    public void onRefreshError(){
        setRetry(true);
        mPbLoading.setVisibility(GONE);
        mTvRefresh.setText("刷新失败，点击重试");
    }

    private void setRetry(boolean isRefresh) {
        isCanRetry = true;
        isLoadMoreRetry = !isRefresh;
        isRefreshRetry = isRefresh;
    }

    public void onRefreshSuccess(){
        mIvNarrow.setVisibility(View.VISIBLE);
        mPbLoading.setVisibility(View.GONE);
        mTvRefresh.setText("刷新完成");
    }

    public void onLoading(){
        mIvNarrow.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.VISIBLE);
        mTvRefresh.setText("正在加载...");
    }

    public void onLoadError(){
        setRetry(false);
        mPbLoading.setVisibility(GONE);
        mTvRefresh.setText("加载失败，点击重试");
    }

    public void onLoadNoData(){
        mIvNarrow.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.GONE);
        mTvRefresh.setText("没有更多数据了");
    }

    public void onLoadSuccess(){
        mIvNarrow.setVisibility(View.VISIBLE);
        mPbLoading.setVisibility(View.GONE);
        mTvRefresh.setText("加载完成");
    }

    public void setOnRetryListener(OnRetryListener listener){
        mListener = listener;
    }
}
