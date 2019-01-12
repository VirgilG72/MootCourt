package com.example.a61979.mootcourt.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.adapter.ContentFragmentAdapter;
import com.example.a61979.mootcourt.base.BaseFragment;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.listener.MyOnCheckedChangeListener;
import com.example.a61979.mootcourt.listener.MyOnPageChangeListener;
import com.example.a61979.mootcourt.pager.Discover;
import com.example.a61979.mootcourt.pager.HomePager;
import com.example.a61979.mootcourt.pager.Horizon;
import com.example.a61979.mootcourt.pager.Signup;
import com.example.a61979.mootcourt.pager.User;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.example.a61979.mootcourt.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

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
public class ContentFragment extends BaseFragment {
    //2、初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewpager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化了");
        View view = View.inflate(context, R.layout.content_fragment, null);
        //1、把视图注入到框架中，让此类和view关联起来
        x.view().inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");

        //初始化五个页面，并且放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager((context)));
        basePagers.add(new Discover((context)));
        basePagers.add(new Signup((context)));
        basePagers.add(new Horizon(context));
        basePagers.add(new User((context)));


        //设置viewPager的适配器
        viewpager.setAdapter(new ContentFragmentAdapter(basePagers));
        //设置radiogroup的选中状态改变监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener(viewpager, context));
        //监听某个页面被选中，初始化对应的页面的数据
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener(basePagers));

        //设置默认首选项
        rg_main.check(R.id.rb_home);

        basePagers.get(0).initData();
        //设置模式SlidingMenu不可以滑动
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);

    }

    public Discover getDiscoverPager() {
        return (Discover) basePagers.get(1);
    }
    private void isEnableSlidingMenu(int touchmodeFullscreen){
        MainActivity mainActivity=(MainActivity)context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }
}

