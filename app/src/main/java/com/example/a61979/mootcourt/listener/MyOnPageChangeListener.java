package com.example.a61979.mootcourt.listener;

import android.support.v4.view.ViewPager;

import com.example.a61979.mootcourt.base.BasePager;

import java.util.ArrayList;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private final ArrayList<BasePager> basePagers;

    public MyOnPageChangeListener(ArrayList<BasePager> basePagers) {
        this.basePagers=basePagers;

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**当某个页面被选中的时候
     * @param position
     * 被选中页面的位置
     */
    @Override
    public void onPageSelected(int position) {
        basePagers.get(position).initData();


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

