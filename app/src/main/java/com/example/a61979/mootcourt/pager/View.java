package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.utils.LogUtil;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class View extends BasePager {
    public View(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("视图页面数据被初始化了。");
        //1、设置标题
        tv_title.setText("视界");
        //2、联网请求得到数据，创建视图
        TextView textView = new TextView(context);

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
        //3、绑定数据
        textView.setText("视界内容");

    }
}
