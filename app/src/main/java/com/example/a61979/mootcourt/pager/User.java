package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.WebViewActivity;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.example.a61979.mootcourt.view.My.MyOneLineView;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class User extends BasePager implements MyOneLineView.OnRootClickListener,MyOneLineView.OnArrowClickListener{

    private LinearLayout ll_mine_item;

    public User(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        LogUtil.e("用户页面数据被初始化了。");
        //1、设置标题
       tv_title.setText("我的");
        //        //2、联网请求得到数据，创建视图
        //        TextView textView = new TextView(context);
        //
        //        textView.setGravity(Gravity.CENTER);
        //        textView.setTextColor(Color.RED);
        //        textView.setTextSize(25);
        //        fl_content.addView(textView);
        //        //3、绑定数据
        //        textView.setText("我的内容");

        //打开我的资料页面。如果此前没有登录，则会先执行登录，在登录完成后才显示资料页面
        //UMSGUI.showProfilePage();
        initUserView();



    }


    public View initUserView() {
        fl_content.removeAllViews();
        View view = View.inflate(context, R.layout.mine_fragment, null);
        ll_mine_item = (LinearLayout) view.findViewById(R.id.ll_mine_item);
        //使用示例，通过Java代码来创建MyOnelineView
        //icon + 文字 + 箭头
        //使用示例，通过Java代码来创建MyOnelineView
        //icon + 文字 + 箭头
        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "收藏", "", true)
                .setOnRootClickListener(this, 1));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "已阅", "", true)
                .setOnRootClickListener(this, 2));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "未阅", "", true)
                .setOnRootClickListener(this, 3));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "用户须知", "", true)
                .setDividerTopColor(R.color.gray2)
                .showDivider(true,true)
                .setDividerTopHigiht(10)
                .setOnRootClickListener(this, 4));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_version_update_icon, "用户反馈", "", true)
                .setOnRootClickListener(this, 5));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_account_setting_icon, "账户设置", "", true)
                .setOnRootClickListener(this, 6));
        fl_content.addView(view);
        return view;
    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                WebViewActivity.startWebViewActivity(context,"http://gongjiazheng.top/");
                break;
            case 2:
                WebViewActivity.startWebViewActivity(context,"https://blog.csdn.net/");
                break;
            case 3:
                WebViewActivity.startWebViewActivity(context,"https://github.com/VirgilG72");
                break;
            case 4:
                Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onArrowClick(View view) {

    }
}
