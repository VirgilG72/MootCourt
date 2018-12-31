package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.Famous;
import com.example.a61979.mootcourt.activity.Learn;
import com.example.a61979.mootcourt.activity.Start;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.utils.DensityUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class HomePager extends BasePager {
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    @ViewInject(R.id.ll_point_group)
    private LinearLayout ll_point_group;
    private ImageButton ib_start;
    private ImageButton ib_famous;
    private ImageButton ib_learn;

    private int[] ImagesIDs;
    private ArrayList<ImageView> imageviews;
    private int preposition = 0;
    private boolean isLoad = false;
    private MyHandler myHandler;
    private Intent intent;

    public HomePager(Context context) {
        super(context);
    }

    /**
     * 初始化视图
     *
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.mytopnews, null);
        ib_start = (ImageButton) view.findViewById(R.id.ib_start);
        ib_famous = (ImageButton) view.findViewById(R.id.ib_famous);
        ib_learn = (ImageButton) view.findViewById(R.id.ib_learn);
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(context, Start.class);
                context.startActivity(intent);
            }

        });
        ib_famous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Famous.class);
                context.startActivity(intent);
            }
        });

        ib_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(context, Learn.class);
                context.startActivity(intent);
            }
        });
        x.view().inject(this, view);
        viewpager.addOnPageChangeListener(new MyHomeOnPageChangeListener());
        return view;

    }


    private class MyHomeOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            ll_point_group.getChildAt(preposition).setEnabled(false);
            ll_point_group.getChildAt(i).setEnabled(true);
            preposition = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        //        LogUtil.e("主页面数据被初始化了。");
        //        //1、设置标题
        //        tv_title.setText("主页面");
        //        //2、联网请求得到数据，创建视图
        //        TextView textView = new TextView(context);
        //
        //        textView.setGravity(Gravity.CENTER);
        //        textView.setTextColor(Color.RED);
        //        textView.setTextSize(25);
        //       fl_content.addView(textView);
        //        //3、绑定数据
        //        textView.setText("主页面内容");

        if (!isLoad) {
            ImagesIDs = new int[]{R.drawable.a1, R.drawable.b, R.drawable.c};
            imageviews = new ArrayList<>();
            ImageView imageView;
            ImageView pointView;
            for (int i = 0; i < ImagesIDs.length; i++) {
                imageView = new ImageView(context);
                pointView = new ImageView(context);
                imageView.setBackgroundResource(ImagesIDs[i]);
                pointView.setBackgroundResource(R.drawable.point_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
                params.leftMargin = DensityUtil.dip2px(context, 10);
                pointView.setEnabled(false);
                ll_point_group.addView(pointView, params);
                imageviews.add(imageView);
            }
            initAdapter();
            isLoad = true;
        }

    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        ll_point_group.getChildAt(0).setEnabled(true);
        viewpager.setAdapter(new MyviewpagerAdapter());
        myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(0, 4000);
    }

    /**
     * 轮播图的实现
     */
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = (viewpager.getCurrentItem() + 1) % imageviews.size();
            viewpager.setCurrentItem(item);
            myHandler.sendEmptyMessageDelayed(0, 4000);
        }
    }

    /**
     * viewpager适配器
     */
    private class MyviewpagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return ImagesIDs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageviews.get(position);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }


    public void start(View view) {


    }


}
