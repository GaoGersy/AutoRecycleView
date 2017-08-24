# AutoRecycleView
##### 下拉刷新和上拉加载的库多如牛毛，你为何还要再造一个轮子？

​	是的，这方面的库确实多到看不过来，但是却没有找到一个能够内部判断是下拉刷新还是上拉加载，处理空白页面、错误页面，并且维护了page的轮子。导致每次都要重复写相同的代码。

##### AutoRecycleView有何特点？

1. 可以自定义空白页面和错误页面及加载的页面，如果不设置会调用默认的页面(肯定是很简单的实现啦，项目需求才是王道啊)。加载完成后会有一个透明度动画，让界面切换不再生硬(话说这是借鉴了微信的做法)。
2. 只用将请求后得到的数据传给AutoRecycleView，是不是第一页，是刷新呢，还是加载更多，是不是还可以上拉加载更多,要传给后台的page的值是多少呢...这些你都不用关心，内部已经贴心的帮你处理完成。
3. 可以在Xml中直接当成view使用，也可以使用builder模式在代码中使用。
4. Adapter需要实现IRVAdapter接口

github：[AutoRecycleView](https://github.com/GaoGersy/AutoRecycleView/tree/master)
***
##### 如何使用？
在项目级build.gradle加入
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在module级build.gradle加入
```compile 'com.github.GaoGersy:AutoRecycleView:1.0.3'
```

1.在xml中

```
<com.gersion.smartrecycleviewlibrary.SmartRecycleView
            android:id="@+id/testView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            />
```
```
mSmartRecycleView.setFirstPage(1)
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
  mSmartRecycleView.handleData(list);
```
2.Builder模式

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

>碰到的情况是onRefresh和onLoadMore两个方法里的代码都是一样的，正在考虑提供一个方法的接口回调




##### 内部是如何工作的呢

###### 1、你一定好奇内部是如何判断是刷新还是加载更多的呢，话不多说，上代码！

> 这里就是供给外部调用的代码了，你肯定已经看出来isRefresh 就是判断是否是刷新。

```
public <T> void handleData(List<T> data) {
        if (isRefresh) {
            onRefresh(data);
        } else {
            onLoadMore(data);
        }
    }
```
> 接下来我们来看看isRefresh 是怎么维护的,(我 X ,这里哪来的isRefresh ,你TM 在逗我？) 大兄弟,别着急!这个里面虽然没有isRefresh ，但是这个确实是控制isRefresh 的核心。PullToRefreshLayout这个控件里面是知道用户是在上拉还是下拉的，这样我们就可以根据PullToRefreshLayout里获得的反馈来设置isRefresh 的值。
```
public SmartRecycleView setRefreshListener(final PullToRefreshLayout.OnRefreshListener listener) {
        mRefreshListener = listener;
        PullToRefreshLayout.OnRefreshListener onRefreshListener = 
        new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(int page) {
                setListener(listener, true, page);
            }

            @Override
            public void onLoadMore(int page) {
                setListener(listener, false, page);
            }
        };
        mPullRereshLayout.setOnRefreshListener(onRefreshListener);
        mPullRereshLayout.setOnRertyListener(onRefreshListener);
        return this;
    }
```
>接下来我们看看isRefresh的设置情况，相信这个方法大兄弟你看都不用看就知道是什么了吧？onRefresh和onLoadMore里的代码一样，就抽取出这个公有方法了。
```
private void setListener(PullToRefreshLayout.OnRefreshListener listener, boolean isRefresh, int page) {
        if (isRefresh) {
            this.isRefresh = true;
            if (listener != null) {
                listener.onRefresh(page);
            }
        } else {
            this.isRefresh = false;
            if (listener != null) {
                listener.onLoadMore(page);
            }
        }
    }
```

>相信看完上面的说明，你一定很不屑的说----切，就这，没一点技术含量。哈哈，那我很高兴，因为你已经明白了内部控制的原理了。



##### 关于Adapter

>为了对原项目更小的侵入性，内部没实现BaseAdapter，而是使用IRVAdapter接口，使用Adapter时要实现该接口。
>
>其中必须实现的方法如下(在SmartRecycleView中会使用到,当然我这里的写法不严谨)：

```
public class MyAdapter extends RecyclerView.Adapter<UpdateBean> implements IRVAdapter<UpdateBean> {
	...此处省略与项目有关的代码N行,不要在意不严谨的写法...
	@Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List data) {
        mData = data==null?new ArrayList<UpdateBean>():data;
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
    public List<Bean> getData() {
        return mData;
    }
 }
```



##### 关于过渡动画

>关于加载完数据后的过渡动画，你肯定有自己的方法(ObjectAnimator，ValueAmimator)。这里我提供下我之前看到的比较有意思的写法，也许你会喜欢呢？具体在哪里看到的记不清，这里跟原作者说声抱歉。
>
>这个是View里面提供的，我看了下源码，其实内部也是通过ValueAnimator实现的。这种写法是不是感觉比直接用ValueAnimator简单？
```
private void hideView(final View view){
        if (view.getVisibility()==GONE){
            return;
        }
        view.animate()
                .alpha(0)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(GONE);
                    }
                })
                .start();
}
```



##### 关于Builder模式

> 比较常规的写法，这里就是解释了，想了解详细的看源码吧。这里只提下关于container ViewGroup的定义看看是否符合你们的项目。

```
private SmartRecycleView into(ViewGroup container){
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(mSmartRecycleView,params);
        return mSmartRecycleView;
    }
```
>上面的方法会在这里使用到：
```
public SmartRecycleView build() {
   return new SmartRecycler(this).into(mContainer);
}
```

使用到的开源项目有[SuperSwipeRefreshLayout](https://github.com/nuptboyzhb/SuperSwipeRefreshLayout) ,非常感谢作者的贡献。

[AutoRecycleView](https://github.com/GaoGersy/AutoRecycleView/tree/master)
有好的建议记得联系我哟--gaogersy@163.com
