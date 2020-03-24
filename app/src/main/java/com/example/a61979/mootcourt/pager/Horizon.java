package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.content.Intent;

import com.example.a61979.mootcourt.ForumActivity;
import com.example.a61979.mootcourt.base.BasePager;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Horizon extends BasePager {


    public Horizon(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        Intent intent = new Intent(context, ForumActivity.class);
        context.startActivity(intent);


//        LogUtil.e("视图页面数据被初始化了。");
//        //1、设置标题
//        tv_title.setText("视界");
//        //2、联网请求得到数据，创建视图
//        TextView textView = new TextView(context);
//
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        fl_content.addView(textView);
//        //3、绑定数据
//        textView.setText("视界内容");

    }


}
