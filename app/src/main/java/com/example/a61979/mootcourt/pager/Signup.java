package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.SignupActivity;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.utils.DensityUtil;
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
    public String[] names=new String[]{"夫妻一方以个人名义借款 债务应否共同承担","疫情中以危险方法危害公共安全罪与妨害传染病防治罪如何区分适用","司机在车辆卸沙过程中遭掩埋致死的保险责任认定"};
    public int[] ImagesIDs = new int[]{R.drawable.law1, R.drawable.law2, R.drawable.law3};
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
        fl_content.removeAllViews();
        View view = View.inflate(context,R.layout.activity_signup,null);
        listview = (ListView) view.findViewById(R.id.signup_listview);


        View headerview = View.inflate(context, R.layout.signup_topview, null);
        listview.addHeaderView(headerview);
        listview.setOnItemClickListener(new MyOnItemClickonListener());
        fl_content.addView(view);
        initAdapter();

        return view;//可不return view。

    }

    private void initAdapter() {
        MysignupAdapter adapter = new MysignupAdapter();
        listview.setAdapter(adapter);

    }

    /*
     *设置listview监听事件
     * */
    private class MyOnItemClickonListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.i("!", "onItemClick position:=============== "+position);
            Intent intent=new Intent(context,SignupActivity.class);
            intent.putExtra("url",position);
            context.startActivity(intent);
        }
    }
    private class MysignupAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 3;
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
            ViewHolder viewHolder = new ViewHolder();
            if(view==null){
                LogUtil.e("第一次初始化listview========="+i);
                view = View.inflate(context, R.layout.item_signup_listview, null);
                viewHolder.number=(TextView) view.findViewById(R.id.number);
                viewHolder.pic=(ImageView) view.findViewById(R.id.pic);
                viewHolder.title=(TextView) view.findViewById(R.id.title);
                viewHolder.time=(TextView) view.findViewById(R.id.time);
                viewHolder.bt_status=(Button) view.findViewById(R.id.bt_status);
                view.setTag(viewHolder);


            }
            else
            {
                LogUtil.e("复用listview=========");
                viewHolder  = (ViewHolder) view.getTag();

            }
            viewHolder.number.setText(i+1+"、");//定义序号
            viewHolder.title.setText(names[i]);
            Glide.with(context)
                    .load(ImagesIDs[i])
                    .override(DensityUtil.dip2px(context,100), DensityUtil.dip2px(context,100))
                    .placeholder(R.drawable.news_pic_default)
                    .error(R.drawable.news_pic_default)
                    .into(viewHolder.pic);

            return view;
        }


    }
    static class ViewHolder {
        TextView number;
        ImageView pic;
        TextView title;
        TextView time;
        Button bt_status;


    }
}
