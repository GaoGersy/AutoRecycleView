# AutoRecycleView
##### 下拉刷新和上拉加载的库多如牛毛，你为何还要再造一个轮子？

> 是的，这方面的库确实多到看不过来，但是却没有找到一个能够内部判断是下拉刷新还是上拉加载，处理空白页面、错误页面，并且维护了page的轮子。导致每次都要重复写相同的代码。



##### AutoRecycleView有何特点？

> 1. 可以自定义空白页面和错误页面及加载的页面，如果不设置会调用默认的页面(肯定是很简单的实现啦，项目需求才是王道啊)。加载完成后会有一个透明度动画，让界面切换不再生硬(话说这是借鉴了微信的做法)。
> 2. 只用将请求后得到的数据传给AutoRecycleView，是不是第一页，是刷新呢，还是加载更多，是不是还可以上拉加载更多,要传给后台的page的值是多少呢...这些你都不用关心，内部已经贴心的帮你处理完成。
> 3. 可以在Xml中直接当成view使用，也可以使用builder模式在代码中使用。
> 4. Adapter需要实现IRVAdapter接口

github：[AutoRecycleView](https://github.com/GaoGersy/AutoRecycleView/tree/master)
***
##### 如何使用？

###### 1.在xml中

```
<com.gersion.smartrecycleviewlibrary.SmartRecycleView
            android:id="@+id/testView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            />
```
```
mTestView.setFirstPage(1)
        .setAutoRefresh(true)
        .setPageSize(20)
        .setAdapter(mAdapter)
        .loadMoreEnable(true)
        .refreshEnable(true)
        .setLayoutManger(SmartRecycleView.LayoutManagerType.GRID_LAYOUT)
        .setRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int page) {
                //加载数据
            }

            @Override
            public void onLoadMore(int page) {
                //加载数据
            }
        });
        
  //在加载服务器数据完成后调用      
  mTestView.handleData(list);
```
####### 2.Builder模式

```
mSmartRecycleView = new SmartRecycler.Builder(this, container, mAdapter)
                .setFirstPage(1)
                .setAutoRefresh(true)
                .setPageSize(20)
                .setLoadMore(true)
                .setRefresh(true)
                .setLayoutManagerType(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT)
                .setRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh(int page) {
                        //加载数据
                    }

                    @Override
                    public void onLoadMore(final int page) {
                        //加载数据
                    }
                })
                .build();
                
//在加载服务器数据完成后调用      
mSmartRecycleView .handleData(list);
```
[AutoRecycleView](https://github.com/GaoGersy/AutoRecycleView/tree/master)
有好的建议记得联系我哟--gaogersy@163.com
