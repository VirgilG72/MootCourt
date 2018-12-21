package com.example.a61979.mootcourt.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.a61979.mootcourt.base.MenuDetailBasePager;
import com.example.a61979.mootcourt.utils.LogUtil;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class JapanMenuDetailPager extends MenuDetailBasePager {

    private TextView textview;

    public JapanMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textview = new TextView(context);

        textview.setGravity(Gravity.CENTER);
        textview.setTextColor(Color.RED);
        textview.setTextSize(25);
        return textview;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("日本页面数据被初始化了");
        textview.setText("日本页面内容");
    }
}
