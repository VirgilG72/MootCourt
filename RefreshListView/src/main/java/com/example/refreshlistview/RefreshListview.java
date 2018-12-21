package com.example.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RefreshListview extends ListView {


    /**
     * 下拉刷新和顶部轮播图
     */
    private LinearLayout headerview;

    /**
     * 下拉刷新控件
     */
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_status;
    private TextView tv_time;
    /**
     * 下拉刷新控件的高
     */
    private int headerviewheight;
    /**
     * 加载更多控件的高
     */
    private int footviewheight;
    /**
     * 下拉刷新
     */
    private static final int PULL_DOWN_REFRESH = 0;
    /**
     * 手松刷新
     */
    private static final int RELEASE_REFRESH = 1;
    /**
     * 正在刷新
     */
    public static final int REFRESHING = 2;

    /**
     * 当前状态
     */
    private int currentStatus = PULL_DOWN_REFRESH;
    /**
     * 加载更多的视图
     */
    private LinearLayout footview;
    /**
     * 是否已经加载更多视图
     */
    private boolean isLoadMore = false;
    /**
     * 顶部轮播图部分
     */
    private View topNewsView;
    /**
     * listview的Y轴坐标
     */
    private int listviewonscreenY=-1;



    public RefreshListview(Context context) {
        this(context, null);
    }

    public RefreshListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);

    }

    private void initFooterView(Context context) {
        footview = (LinearLayout) View.inflate(context, R.layout.refresh_footer, null);
        footview.measure(0, 0);
        footviewheight = footview.getMeasuredHeight();

        footview.setPadding(0, -footviewheight, 0, 0);
        //listview添加footer
        addFooterView(footview);

        //监听listview的滚动监听
        setOnScrollListener(new MyOnScrollListener());

    }

    /**
     * 添加顶部轮播图
     * @param topNewsView
     */
    public void addTopNewsView(View topNewsView) {
        if(topNewsView!=null){
            this.topNewsView=topNewsView;
            headerview.addView(topNewsView);
        }

    }

    private class MyOnScrollListener implements OnScrollListener {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当静止或者惯性滚动的时候
            if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_IDLE) {
                //并且是最后一条可见的
                if (getLastVisiblePosition() == getCount()-1) {
                    //1.显示加载更多布局
                    footview.setPadding(0, 0, 0, 0);//padding为8

                    //2.状态改变
                    isLoadMore = true;
                    //3.回调接口
                    if (mOnRefreshListener !=null){
                        mOnRefreshListener.onLoadMore();

                    }
                }
            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    private Animation upAnimation;
    private Animation downAnimation;

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(0, -360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        headerview = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ll_pull_down_refresh = (LinearLayout) headerview.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerview.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerview.findViewById(R.id.pb_status);
        tv_status = (TextView) headerview.findViewById(R.id.tv_status);
        tv_time = (TextView) headerview.findViewById(R.id.tv_time);

        //测量
        ll_pull_down_refresh.measure(0, 0);
        headerviewheight = ll_pull_down_refresh.getMeasuredHeight();

        //默认隐藏下拉刷新控件
        ll_pull_down_refresh.setPadding(0, -headerviewheight, 0, 0);

        //添加Listview头
        addHeaderView(headerview);

    }

    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录起始坐标
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }
                //判断顶部轮播图是否完全显示,只有完全显示,才会有下拉刷新

                boolean isDisplayTopNews = isDisplayTopNews();
                if(!isDisplayTopNews){
                    //加载更多
                    break;
                }

                //正在刷新的时候，不准下拉
                if (currentStatus == REFRESHING) {
                    break;
                }
                //2、来到新的坐标
                float endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {//下拉
                    int paddingTop = (int) (-headerviewheight + distanceY);
                    if (paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH) {
                        //下拉刷新状态
                        currentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();
                    } else if (paddingTop > 0 && currentStatus != RELEASE_REFRESH) {
                        //手松刷新状态
                        currentStatus = RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();
                    }

                    ll_pull_down_refresh.setPadding(0, paddingTop, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentStatus == PULL_DOWN_REFRESH) {

                    ll_pull_down_refresh.setPadding(0, -headerviewheight, 0, 0);//完全隐藏
                } else if (currentStatus == RELEASE_REFRESH) {
                    //设置状态为正在刷新
                    currentStatus = REFRESHING;
                    refreshViewState();
                    ll_pull_down_refresh.setPadding(0, 0, 0, 0);//完全显示

                    //回调接口
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断是否完全显示顶部轮播图
     * @return
     */
    private boolean isDisplayTopNews() {
        if (topNewsView!=null){
            //1.得到listview在屏幕上的坐标
            int[] location=new int[2];
            if (listviewonscreenY==-1){
                getLocationOnScreen(location);//存放x轴/y轴坐标
                listviewonscreenY = location[1];
            }

            //2.得到顶部轮播图在屏幕上的坐标
            topNewsView.getLocationOnScreen(location);
            int topNewsViewOnScreenY = location[1];


            return listviewonscreenY <= topNewsViewOnScreenY;
        } else {
            return true;
        }


    }

    private void refreshViewState() {
        switch (currentStatus) {
            case PULL_DOWN_REFRESH:
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新...");

                break;

            case RELEASE_REFRESH:
                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("手松刷新...");
                break;

            case REFRESHING:
                tv_status.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;

        }
    }

    /**
     * 当联网成功和失败的时候回调该方法
     * 用户刷新状态的还原
     *
     * @param success
     */
    public void onRefreshFinish(boolean success) {
       if (isLoadMore){
           //加载更多
           isLoadMore=false;//必须重置状态
           //隐藏加载更多布局
           footview.setPadding(0,-footviewheight,0,0);
       }else{
           //下拉刷新
           tv_status.setText("下拉刷新...");
           currentStatus = PULL_DOWN_REFRESH;
           iv_arrow.clearAnimation();
           pb_status.setVisibility(GONE);
           iv_arrow.setVisibility(VISIBLE);
           //隐藏下拉刷新控件
           ll_pull_down_refresh.setPadding(0, -headerviewheight, 0, 0);//完全隐藏

           if (success) {
               //设置最新的更新时间
               tv_time.setText("上次更新时间：" + getSystemTime());
           }
       }

    }

    /**
     * 得到当前安卓系统时间
     *
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format1 = format.format(new Date());

        return format1;
    }

    ;

    public interface OnRefreshListener {
        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();
        public void onLoadMore();
    }

    private OnRefreshListener mOnRefreshListener;

    /**
     * 设置监听刷新，由外界调用
     *
     * @param l
     */
    public void setOnRefreshListener(OnRefreshListener l) {

        this.mOnRefreshListener = l;
    }
}


