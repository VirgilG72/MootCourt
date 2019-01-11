package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
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
public class Signup extends BasePager {


    public Signup(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();

        ib_search.setVisibility(View.VISIBLE);
        ib_help.setVisibility(View.VISIBLE);

        LogUtil.e("报名页面数据被初始化了。");
        //1、设置标题
        tv_title.setText("报名");
        //2、联网请求得到数据，创建视图
        TextView textView = new TextView(context);

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
        //3、绑定数据
        textView.setText("报名内容");




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_help_menu,menu);
//        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(context,query,Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(context,"onQueryTextChange========",Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
}
