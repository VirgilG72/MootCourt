package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView tv_mine_name;
    private TextView tv_mine_account;
    private ImageView iv_mine_avatar;

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
        tv_mine_name = (TextView) view.findViewById(R.id.tv_mine_name);
        tv_mine_account = (TextView) view.findViewById(R.id.tv_mine_account);
        iv_mine_avatar = (ImageView) view.findViewById(R.id.iv_mine_avatar);


        //使用示例，通过Java代码来创建MyOnelineView
        //icon + 文字 + 箭头
        //使用示例，通过Java代码来创建MyOnelineView
        //icon + 文字 + 箭头
        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "Blog详情", "", true)
                .setOnRootClickListener(this, 4));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "CSDN详情", "", true)
                .setOnRootClickListener(this, 5));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "Github详情", "", true)
                .setOnRootClickListener(this, 6));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_version_update_icon, "更改头像", "", true)
                .setDividerTopColor(R.color.gray2)
                .showDivider(true,true)
                .setDividerTopHigiht(10)
                .setOnRootClickListener(this, 1));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_about_icon, "更改昵称", "", true)
                .setOnRootClickListener(this, 2));

        ll_mine_item.addView(new MyOneLineView(context)
                .initMine(R.drawable.mine_account_setting_icon, "更改简介", "", true)
                .setOnRootClickListener(this, 3));
        fl_content.addView(view);
        return view;
    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 4:
                WebViewActivity.startWebViewActivity(context,"http://virgilg72.github.io/");
                break;
            case 5:
                WebViewActivity.startWebViewActivity(context,"https://me.csdn.net/weixin_43325818");
                break;
            case 6:
                WebViewActivity.startWebViewActivity(context,"https://github.com/VirgilG72");
                break;
            case 1:
                iv_mine_avatar.setImageResource(R.drawable.user_other);
                //Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                tv_mine_name.setText("法律至上");
                //Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                tv_mine_account.setText("真理与正义永远不会缺席");
                //Toast.makeText(context, "规划中", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onArrowClick(View view) {

    }
}
