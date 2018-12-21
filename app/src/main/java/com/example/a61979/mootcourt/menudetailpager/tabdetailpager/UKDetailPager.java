package com.example.a61979.mootcourt.menudetailpager.tabdetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.base.MenuDetailBasePager;
import com.example.a61979.mootcourt.domain.DiscoverPagerBean2;
import com.example.a61979.mootcourt.domain.TabDetailPagerBean;
import com.example.a61979.mootcourt.utils.CacheUtils;
import com.example.a61979.mootcourt.utils.Constants;
import com.example.a61979.mootcourt.utils.DensityUtil;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.example.a61979.mootcourt.view.HorizontalScrollViewPager;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}页签详情页面
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UKDetailPager extends MenuDetailBasePager {

    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView listview;
    private ImageOptions imageOptions;


    private final DiscoverPagerBean2.DetailPagerData.ChildrenData childrenData;

    private String url;
    private List<TabDetailPagerBean.DataBean.TopnewsData> topnews;
    private List<TabDetailPagerBean.DataBean.NewsData> news;
    /**
     * 下一页联网路径
     */
    private String moreUrl;
    /**
     * 是否加载更多
     */
    private boolean isLoadMore=false;
    private List<TabDetailPagerBean.DataBean.NewsData> morenews;
    private MyTabDetailListViewsAdapter adapter;
    private PullToRefreshListView mPullRefreshListView;


    public UKDetailPager(Context context, DiscoverPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData=childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(context,100), DensityUtil.dip2px(context,100))
                .setRadius(DensityUtil.dip2px(context,5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();

    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.topicdetail_pager, null);

        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);

        listview=mPullRefreshListView.getRefreshableView();

        View topNewsView = View.inflate(context, R.layout.topnews, null);
        viewpager = (HorizontalScrollViewPager) topNewsView.findViewById(R.id.viewpager);
        tv_title=(TextView) topNewsView.findViewById(R.id.tv_title);
        ll_point_group=(LinearLayout) topNewsView.findViewById(R.id.ll_point_group);
        //把顶部轮播图部分视图，以头的方式添加到listview
         listview.addHeaderView(topNewsView);
        //listview.addTopNewsView(topNewsView);

        //设置监听下拉刷新
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (TextUtils.isEmpty(moreUrl)) {
                    //没有更多数据
                    Toast.makeText(context,"没有更多数据", Toast.LENGTH_SHORT).show();
                    //listview.onRefreshFinish(false);
                    mPullRefreshListView.onRefreshComplete();
                }else {
                    getMoreDataFromNet();
                }
            }
        });


        return view;
    }


    private void getMoreDataFromNet() {
        RequestParams params=new RequestParams(moreUrl);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("加载更多联网成功=="+result);
                //把这个放在前面
                isLoadMore = true;
                // 解析数据
                processData(result);
                //listview.onRefreshFinish(false);
                mPullRefreshListView.onRefreshComplete();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("加载更多联网失败onError=="+ex.getMessage());
                //listview.onRefreshFinish(false);
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("加载更多联网失败oncancelled=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("加载更多联网onFinished");

            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        url = Constants.BASE_URL + childrenData.getUrl();
        String saveJson=CacheUtils.getString(context,url);

        if (!TextUtils.isEmpty(saveJson)){
            //解析和处理显示数据
            processData(saveJson);
        }
        //LogUtil.e(childrenData.getTitle()+"的联网地址=="+url);
        else
        //联网请求
        getDataFromNet();


    }

    private void getDataFromNet() {
        RequestParams params=new RequestParams(url);
        params.setConnectTimeout(4000);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context,url,result);
                LogUtil.e(childrenData.getTitle()+"页面数据请求成功==");

                //解析和处理显示数据
                processData(result);

                //隐藏下拉刷新控件-重写数据更新时间
                //listview.onRefreshFinish(true);
                mPullRefreshListView.onRefreshComplete();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求失败=="+ex.getMessage());
                //隐藏下拉刷新控件-不更新时间
                //listview.onRefreshFinish(true);
                mPullRefreshListView.onRefreshComplete();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求失败=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle()+"页面数据请求完成");

            }
        });
    }

    /**
     * 之前点高亮显示的位置
     */
    private int prePosition=0;

    private void processData(String json) {
        TabDetailPagerBean bean=parsedJson(json);
        LogUtil.e(childrenData.getTitle()+"解析成功=="+bean.getData().getNews().get(0).getTitle());

        moreUrl = bean.getData().getMore();
        if(TextUtils.isEmpty(bean.getData().getMore())){
            moreUrl="";
        }
        else{
            moreUrl =Constants.BASE_URL+moreUrl;
        }
        LogUtil.e("加载更多的地址:"+moreUrl);
        //默认和加载更多
        if (!isLoadMore){
            //默认
            topnews = bean.getData().getTopnews();//顶部轮廓图数据
            //设置viewpager的适配器
            viewpager.setAdapter(new MyTabDetailTopNewsAdapter());

            //添加点
            addPoint();

            //监听页面的改变，设置红点变化和文本变化
            viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
            tv_title.setText(topnews.get(prePosition).getTitle());//设置默认的文本显示

            //准备listview对应的集合数据
            news = bean.getData().getNews();
            //设置listview的适配器
            adapter = new MyTabDetailListViewsAdapter();
            listview.setAdapter(adapter);
        }else{
            //加载更多
            isLoadMore =false;//必须重置状态!
            morenews = bean.getData().getNews();
            //添加到原来的集合中
            news.addAll(morenews);
            //刷新适配器
            adapter.notifyDataSetChanged();

        }



    }
    private class MyTabDetailListViewsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder viewholder=new ViewHolder();
            if (convertView==null){
                convertView=View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewholder.iv_icon=(ImageView) convertView.findViewById(R.id.iv_icon);
                viewholder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
                viewholder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(viewholder);
            }
            else{
                viewholder= (ViewHolder) convertView.getTag();//复用convertview
            }

            //根据位置得到数据
            TabDetailPagerBean.DataBean.NewsData newsData = news.get(position);
            String imgUrl=Constants.BASE_URL+newsData.getListimage();
            //请求图片
            x.image().bind(viewholder.iv_icon,imgUrl,imageOptions);

            //设置标题
            viewholder.tv_title.setText(newsData.getTitle());
            //设置时间
            viewholder.tv_time.setText(newsData.getPubdate());



            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;


    }

    private void addPoint() {
        ll_point_group.removeAllViews();//移除所有的红点

        for (int i = 0; i < topnews.size(); i++) {
            ImageView imageView = new ImageView(context);
            //设置背景选择器
            imageView.setBackgroundResource(R.drawable.point_selector);


            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(context,5),DensityUtil.dip2px(context,5));
            if (i==0){
                 imageView.setEnabled(true);

            }
            else{
                imageView.setEnabled(false);
                params.leftMargin=DensityUtil.dip2px(context,8);
            }



            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);

        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            //1.设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2.对应页面的红点高亮
            //把之前的变成灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把当前设置红色
            ll_point_group.getChildAt(position).setEnabled(true);

            prePosition=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,TabDetailPagerBean.class);
    }

    private class MyTabDetailTopNewsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.news_pic_default);
            //x轴和y轴拉伸
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            TabDetailPagerBean.DataBean.TopnewsData topnewsData = topnews.get(position);
           //图片请求地址
            String imgUrl = Constants.BASE_URL+topnewsData.getTopimage();

            //联网请求图片
            x.image().bind(imageView,imgUrl);

            //把图片添加到容器viewpager中
            container.addView(imageView);


            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }



}
