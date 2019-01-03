package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
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
    private Menu menu;
    private SearchView searchview;
    private  View view;

    public Signup(Context context) {
        super(context);
    }

//    @Override
//    public View initView() {
//       view = View.inflate(context, R.layout.activity_signup, null);
//        return view;
//    }

    @Override
    public void initData() {
        super.initData();
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
        //使用菜单填充器获取menu菜单下的资源文件
//        getMenuInflater().inflate(R.menu.search_help_menu,menu);
//        //获取搜索的菜单组件
//        MenuItem menuItem = menu.findItem(R.id.search);
//        searchview = (SearchView) MenuItemCompat.getActionView(menuItem);
//
//        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(context,query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//
    }
}
