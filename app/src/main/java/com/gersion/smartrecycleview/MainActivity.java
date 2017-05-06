package com.gersion.smartrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gersion.smartrecycleviewlibrary.SmartRecycleView;
import com.gersion.smartrecycleviewlibrary.ptr2.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int page = 1;
    private ArrayList<String> mData;
    private RecyclerView mRecycleView;
    private PullToRefreshLayout mPullRereshLayout;
    private ArrayList<Bean> data = new ArrayList<>();
    private PullAdapter mAdapter;
    private SmartRecycleView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new PullAdapter(R.layout.item_show, data);
        mTestView = (SmartRecycleView) findViewById(R.id.testView);
        mTestView.setFirstPage(1)
                .setAutoRefresh(true)
                .setPageSize(20)
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
                    Toast.makeText(MainActivity.this, data.get(i).cotent,Toast.LENGTH_LONG).show();
                }
            });
//        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
//        mPullRereshLayout = (PullToRefreshLayout) findViewById(R.id.pullRereshLayout);
//        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        data = getList(1);
//        mAdapter = new PullAdapter(R.layout.item_show, data);
//        mRecycleView.setAdapter(mAdapter);
////        mPullRereshLayout.setRefreshEnable(true);
////        mPullRereshLayout.setLoadMoreEnable(false);
//        mPullRereshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        page=1;
//                        ArrayList<String> list = getList(1);
//                        mAdapter.setNewData(list);
//                        if (list.size() >= 20) {
//                            mPullRereshLayout.onRefreshSuccess();
//                        }else{
//                            mPullRereshLayout.setNoMoreData(true);
//                        }
////                        mPullRereshLayout.onRefreshErr();
//                    }
//                }, 1000);
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        page++;
//                        ArrayList<String> list = getList(page);
//                        mAdapter.addData(list);
//                        if (list.size() >= 20) {
//                            mPullRereshLayout.onLoadMoreSuccess();
//                        }else{
//                            mPullRereshLayout.setNoMoreData(true);
//                        }
////                        mPullRereshLayout.onLoadMoreErr();
//                    }
//                }, 1000);
//
//            }
//        });
//
//        mPullRereshLayout.setOnRertyListener(new OnRetryListener() {
//            @Override
//            public void onRefreshRetry() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.setNewData(getList(1));
//                        mPullRereshLayout.onRefreshSuccess();
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onLoadMoreRetry() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        page++;
//                        ArrayList<String> list = getList(page);
//                        if (list.size() >= 20) {
//                            mPullRereshLayout.onLoadMoreSuccess();
//                        }else{
//                            mPullRereshLayout.setNoMoreData(true);
//                        }
//                        mAdapter.addData(list);
////                        mPullRereshLayout.onLoadMoreErr();
//                    }
//                }, 3000);
//            }
//        });

//        mPullRereshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListenerAdapter(){
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.setNewData(getList(1));
//                        mPullRereshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
//
//        mPullRereshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListenerAdapter() {
//            @Override
//            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        page++;
//                        mPullRereshLayout.initLoadingView(false,true);
//                        mAdapter.addData(getList(page));
//                    }
//                }, 3000);
//            }
//
//        });


//    mSimpleRefreshView = (SimpleRefreshRecycleView) findViewById(R.id.simpleRefreshView);
//    mData = new ArrayList();
//    View view = LayoutInflater.from(this).inflate(R.layout.view_footer, null);
//    mSimpleRefreshView
//        .init()
//        .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT)
//        .setAdapter(new RBAdapter<String>(this, null, android.R.layout.simple_list_item_1)
//        .bindViewData(this))
//        .refreshEnable(true)
//        .loadMoreEnable(true)
//        .addFooterView()
//        .setPageSize(20)
//        .setRefreshListener(this)
//        .setLoadMoreListener(this)
//        .onRefresh();
//  }
//
//  public void start(View view) {
//    mSimpleRefreshView.onRefresh();
//  }
//
//  public void success(View view) {
//    mSimpleRefreshView.onRefreshComplete(mData);
//  }
//
//  public void failed(View view) {
//    mSimpleRefreshView.onLoadFailure();
//  }
//
//  public void noData(View view) {
//    mData.clear();
//    mSimpleRefreshView.onRefreshComplete(mData);
//  }
//
//  public void loadMore(View view) {
//    mSimpleRefreshView.onLoadMoreComplete(mData);
//  }
//
//  @Override public void onRefresh() {
//    load(true);
//  }
//
//  @Override public void onLoadMore() {
//    load(false);
//  }
//
//  private void load(final boolean isrefresh) {
//    new Thread(new Runnable() {
//      @Override public void run() {
//        Looper.prepare();
//        try {
//          if (isrefresh) {
//            Thread.sleep(2000);
//          } else {
//            Thread.sleep(500);
//          }
//        } catch (Exception e) {
//
//        }
//        runOnUiThread(new Runnable() {
//          @Override public void run() {
//            List<String> list = new ArrayList();
//            for (int i = 0; i < 20; i++) {
//              list.add("           " + i);
//            }
//            mData.clear();
//            mData.addAll(list);
//            if (isrefresh) {
//              mSimpleRefreshView.onRefreshComplete(mData);
//            } else {
//              mSimpleRefreshView.onLoadMoreComplete(mData);
//            }
//            //请求失败调用 refresh.loadFailure();
//          }
//        });
//      }
//    }).start();
//  }
//
//  @Override public void convert(Holder holder, String item) {
//    holder.setText(android.R.id.text1, item);
//  }
    }

    public void getList(final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Bean> list = new ArrayList<>();
                if (page == 3) {
                }else {
                    int num = 20 * page;
                    for (int i = num - 20; i <num ; i++) {
                        Bean bean = new Bean();
                        bean.cotent = "第" + i + "页";
                        list.add(bean);
                    }
                }
                mTestView.handleData(list);
            }
        },3000);
    }

    class Bean {
       public String cotent;
    }
}
