package com.example.a61979.mootcourt.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.BaseActivity;
import com.example.a61979.mootcourt.activity.MainActivity;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BasePager extends BaseActivity{
    /**
     * 上下文
     */
    public final Context context;//MainActivity

    /**
     * 视图，代表各个不同的页面
     */
    public View rootview;

    public TextView tv_title;

    public ImageButton ib_menu;

    public ImageButton ib_search;

    public ImageButton ib_help;

    public FrameLayout fl_content;



    public BasePager(Context context)
    {
        this.context=context;

        rootview=initView();
    }

    /**
     * 用于初始化公共部分视图，并且初始化子视图的Framelayout
     * @return
     */
    public View initView() {
        View view = View.inflate(context, R.layout.base_pager, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        ib_search = (ImageButton) view.findViewById(R.id.ib_search);
        ib_help = (ImageButton) view.findViewById(R.id.ib_help);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);

        //x.view().inject(this,view);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关<->开
            }
        });
        return view;
    }

    /**
     * 初始化数据；当孩子需要初始化数据；或者绑定数据；联网请求数据并且绑定的时候，重写该方法
     */
    public void initData(){

    }

}
