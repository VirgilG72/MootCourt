package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.a61979.mootcourt.R;
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


    private ListView listview;
    private View convertview;

    public Signup(Context context) {

        super(context);
    }


    @Override
    public void initData() {
        super.initData();

        ib_search.setVisibility(View.VISIBLE);
        ib_help.setVisibility(View.VISIBLE);
        initSignupView();
        LogUtil.e("报名页面数据被初始化了。");
        //1、设置标题
        tv_title.setText("报名");



    }
    public View initSignupView(){
        
        View view = View.inflate(context,R.layout.activity_signup,null);
        listview = (ListView) view.findViewById(R.id.signup_listview);


        View headerview = View.inflate(context, R.layout.signup_topview, null);
        listview.addHeaderView(headerview);
        fl_content.addView(view);
        initAdapter();

        return view;

    }

    private void initAdapter() {
        MysignupAdapter adapter = new MysignupAdapter();
        listview.setAdapter(adapter);

    }


    private class MysignupAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            convertview = View.inflate(context, R.layout.item_signup_listview, null);


            return convertview;
        }
    }
}
