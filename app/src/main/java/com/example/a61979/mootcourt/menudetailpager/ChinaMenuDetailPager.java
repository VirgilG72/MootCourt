package com.example.a61979.mootcourt.menudetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.base.MenuDetailBasePager;
import com.example.a61979.mootcourt.domain.DiscoverPagerBean2;
import com.example.a61979.mootcourt.menudetailpager.tabdetailpager.TabDetailPager;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ChinaMenuDetailPager extends MenuDetailBasePager {

    private TabPageIndicator tabPageIndicator;

    private ViewPager viewpager;

    private ImageButton ib_tab_next;


    /**
     * 页签页面的数据集合
     */
    private List<DiscoverPagerBean2.DetailPagerData.ChildrenData> children;
    /**
     * 页签页面的集合
     */
    private ArrayList<TabDetailPager> tableDetailPagers;
    public ChinaMenuDetailPager(Context context, DiscoverPagerBean2.DetailPagerData detailPagerData) {
        super(context);
        children=detailPagerData.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.chinamenu_detail_pager, null);
        tabPageIndicator= (TabPageIndicator) view.findViewById(R.id.tabPageIndicator);
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);
        ib_tab_next=(ImageButton) view.findViewById(R.id.ib_tab_next);
        //设置点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("中国页面数据被初始化了");


        //准备新闻详情页面的数据
        tableDetailPagers=new ArrayList<>();

        for (int i = 0; i < children.size(); i++) {
            tableDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }
        //设置viewpager的适配器
        viewpager.setAdapter(new MyChinaMenuDetailPagerAdapter());
        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewpager);

        //注意：以后监听页面的变化 就用tabPageIndicator监听页面的变化
        tabPageIndicator.setOnPageChangeListener(new MyOnpageChangeListener());
    }
    private class MyOnpageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position==0) {
                //slidingmenu可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);

            }
            else
            {
                //slidingmenu不可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }

        }
        private void isEnableSlidingMenu(int touchmodeFullscreen){
            MainActivity mainActivity=(MainActivity)context;
            mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyChinaMenuDetailPagerAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();

        }

        @Override
        public int getCount() {
            return tableDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tableDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            tabDetailPager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
