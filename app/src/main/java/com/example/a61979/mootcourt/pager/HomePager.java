package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.BLearn;
import com.example.a61979.mootcourt.activity.Famous;
import com.example.a61979.mootcourt.activity.GZHarticleActivity;
import com.example.a61979.mootcourt.activity.Start;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.utils.DensityUtil;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.example.a61979.mootcourt.view.HeaderGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class HomePager extends BasePager {
    private static final String TAG = "HomePager";
    private ViewPager viewpager;
    private LinearLayout ll_point_group;
    private HeaderGridView gridview;

    private ImageButton ib_start;
    private ImageButton ib_famous;
    private ImageButton ib_learn;
    private ImageButton ib_law;
    private int[] ImagesIDs;
    private ArrayList<ImageView> imageviews;
    private int preposition = 0;
    private boolean isLoad = false;
    private MyHandler myHandler;
    private Intent intent;
   private List<Map<String,Object>> data =new ArrayList<Map<String,Object>>();
    public int[] ids=new int[]{R.drawable.law1,R.drawable.law2,R.drawable.law3,R.drawable.law4};
    public String[] names=new String[]{"雷庭君提问箱正式上线","法条整理（1、2期）","来自雷庭君的网购小tips","大家期待已久的《电子商务法》要来啦"};

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
        gridview = (HeaderGridView) view.findViewById(R.id.gridview);

        View headerview = View.inflate(context, R.layout.homepagertop, null);
        viewpager=(ViewPager) headerview.findViewById(R.id.viewpager);
        ll_point_group=(LinearLayout) headerview.findViewById(R.id.ll_point_group);
        ib_start = (ImageButton) headerview.findViewById(R.id.ib_start);
        ib_famous = (ImageButton) headerview.findViewById(R.id.ib_famous);
        ib_learn = (ImageButton) headerview.findViewById(R.id.ib_learn);
        ib_law = (ImageButton) headerview.findViewById(R.id.ib_law);
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
                intent = new Intent(context, BLearn.class);
                context.startActivity(intent);
            }
        });
        ib_law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, BLearn.class);
                context.startActivity(intent);
            }
        });

        viewpager.addOnPageChangeListener(new MyHomeOnPageChangeListener());//轮播图变化监听，更改红点
        gridview.addHeaderView(headerview);//精髓精髓精髓精髓精髓精髓精髓精髓精髓精髓精髓精髓
        gridview.setOnItemClickListener(new MyOnItemClickListener());//Html

        return view;

    }
    private  class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "onItemClick position:=============== "+position);
            Intent intent=new Intent(context,GZHarticleActivity.class);
            intent.putExtra("url",position);
            context.startActivity(intent);

        }
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
            ImagesIDs = new int[]{R.drawable.lbt1, R.drawable.lbt2, R.drawable.lbt3};
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
            for (int i = 0; i <ids.length; i++) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("id",ids[i]);
                map.put("name",names[i]);
                data.add(map);
            }
            initAdapter();
            isLoad = true;
        }


    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        //设置默认轮播图与红点
        ll_point_group.getChildAt(0).setEnabled(true);
        viewpager.setAdapter(new MyviewpagerAdapter());
        myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(0, 4000);

       //SimpleAdapter simpleAdapter = new SimpleAdapter(context, data, R.layout.item_gridview, new String[]{"id", "name"}, new int[]{R.id.iv_gridview, R.id.tv_gridview});
        MyHomepagerAdapter adapter=new MyHomepagerAdapter();//设置gridview
        gridview.setAdapter(adapter);

    }
    private class MyHomepagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
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
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if(view==null){
                LogUtil.e("第一次初始化listview========="+position);
                view = View.inflate(context, R.layout.item_gridview, null);
                viewHolder.tv_gridview=(TextView) view.findViewById(R.id.tv_gridview);
                viewHolder.iv_gridview=(ImageView) view.findViewById(R.id.iv_gridview);
                view.setTag(viewHolder);


            }
            else
            {
                LogUtil.e("复用listview=========");
                viewHolder  = (ViewHolder) view.getTag();

            }
            viewHolder.tv_gridview.setText(names[position]);
            Glide.with(context)
                    .load(ids[position])
                    .override(DensityUtil.dip2px(context,100), DensityUtil.dip2px(context,100))
                    .placeholder(R.drawable.news_pic_default)
                    .error(R.drawable.news_pic_default)
                    .into(viewHolder.iv_gridview);
            return view;
        }
    }
    static class ViewHolder {
        ImageView iv_gridview;
        TextView tv_gridview;
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



}
