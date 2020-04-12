package com.example.a61979.mootcourt.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.a61979.mootcourt.base.BasePager;

import java.util.ArrayList;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ContentFragmentAdapter extends PagerAdapter {

    private final ArrayList<BasePager> basePagers;

    public ContentFragmentAdapter(ArrayList<BasePager> basePagers){
        this.basePagers=basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = basePagers.get(position);
        View rootview = basePager.rootview;
        //调用各个页面的initData()
        // basePager.initData();//初始化数据
        container.addView(rootview);
        return rootview;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
