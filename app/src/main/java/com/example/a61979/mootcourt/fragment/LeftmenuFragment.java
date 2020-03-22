package com.example.a61979.mootcourt.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.base.BaseFragment;
import com.example.a61979.mootcourt.domain.DiscoverPagerBean2;
import com.example.a61979.mootcourt.pager.Discover;
import com.example.a61979.mootcourt.utils.DensityUtil;
import com.example.a61979.mootcourt.utils.LogUtil;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class LeftmenuFragment extends BaseFragment {


    private List<DiscoverPagerBean2.DetailPagerData> data;

    private ListView listview;

    private int prePosition;//点击的位置
    private LeftmenuFragmentAdapter adapter;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        listview = new ListView(context);
        listview.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        //listview.setDividerHeight(0);//设置分割线高度为0；
        //listview.setCacheColorHint(Color.TRANSPARENT);

        //设置按下listview的item不变色
        //listview.setSelector(android.R.color.transparent);

        //设置item的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                prePosition=position;
                adapter.notifyDataSetChanged();//执行getcount与getview方法

                //2.把左侧菜单关闭
                MainActivity mainActivity= (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关<->开
                //3.切换到对应的详情页面;
                switchPager(prePosition);
            }
        });

        return listview;
    }

    /**
     * 根据位置切换不同详情页面
     * @param position
     */
    private void switchPager(int position) {
        MainActivity mainActivity= (MainActivity) context;//由LeftmenuFragment跳转到MainActivity
        ContentFragment contentFragment = mainActivity.getContentFragment();//由MainActivity跳转到ContentFragment
        Discover discover= contentFragment.getDiscoverPager();//由ContentFragment跳转到DiscoverPager
        discover.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");

    }

    /**
     * 接收数据
     *
     * @param data
     */
    public void setData(List<DiscoverPagerBean2.DetailPagerData> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            LogUtil.e("title==" + data.get(i).getTitle());
        }

        //设置适配器
        adapter = new LeftmenuFragmentAdapter();//only change the color
        listview.setAdapter(adapter);
    //设置默认页面,真正的切换
        switchPager(prePosition);
    }

    private class LeftmenuFragmentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);
            textView.setText(data.get(position).getTitle());
            if (position==prePosition)
            {
                //设置红色
                textView.setEnabled(true);
            }else{
                textView.setEnabled(false);
            }
            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}

