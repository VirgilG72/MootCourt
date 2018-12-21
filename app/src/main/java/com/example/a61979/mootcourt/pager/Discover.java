package com.example.a61979.mootcourt.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.base.BasePager;
import com.example.a61979.mootcourt.base.MenuDetailBasePager;
import com.example.a61979.mootcourt.domain.DiscoverPagerBean2;
import com.example.a61979.mootcourt.fragment.LeftmenuFragment;
import com.example.a61979.mootcourt.menudetailpager.ChinaMenuDetailPager;
import com.example.a61979.mootcourt.menudetailpager.FranceMenuDetailPager;
import com.example.a61979.mootcourt.menudetailpager.GermanyMenuDetailPager;
import com.example.a61979.mootcourt.menudetailpager.JapanMenuDetailPager;
import com.example.a61979.mootcourt.menudetailpager.UKMenuDetailPager;
import com.example.a61979.mootcourt.menudetailpager.USAMenuDetailPager;
import com.example.a61979.mootcourt.utils.CacheUtils;
import com.example.a61979.mootcourt.utils.Constants;
import com.example.a61979.mootcourt.utils.LogUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

//import com.example.a61979.mootcourt.domain.DiscoverPagerBean;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Discover extends BasePager {

    /**
     * 左侧菜单对应的数据集合
     */
    private List<DiscoverPagerBean2.DetailPagerData> data;

    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetailBasePager> detailBasepagers;

    public Discover(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("发现页面数据被初始化了。");
        ib_menu.setVisibility(View.VISIBLE);
        //1、设置标题
        tv_title.setText("发现");
        //2、联网请求得到数据，创建视图
        TextView textView = new TextView(context);

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
        //3、绑定数据
        textView.setText("发现内容");
        //4、获取缓存数据
        String saveJson = CacheUtils.getString(context, Constants.DISCOVER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }

        //联网请求数据
        getDataFromNet();
        //用volley联网请求数据
        //getDataFromNetByVolley();

    }

//    /**
//     * 用volley联网请求数据
//     */
//    private void getDataFromNetByVolley() {
//
//    }

    /**
     * 使用xUtils3联网请求
     */
    private void getDataFromNet() {
        RequestParams params=new RequestParams(Constants.DISCOVER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,Constants.DISCOVER_PAGER_URL,result);

                processData(result);
                //设置适配器
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xUtils3联网请求失败=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用xUtils3联网取消=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用xUtils3联网请求完成");
            }
        });
    }

    /**
     * 解析json数据和显示数据
     * @param json
     */
    private void processData(String json) {
       // DiscoverPagerBean bean= parsedJson(json);
        DiscoverPagerBean2 bean= parsedJson2(json);

        //String title = bean.getData().get(0).getChildren().get(1).getTitle();
        //LogUtil.e("使用Gson解析json数据成功-title=="+title);
        String title2 = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson解析json数据成功-title2---------=="+title2);

        //给左侧菜单传递数据
        data = bean.getData();

        MainActivity mainActivity= (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment=mainActivity.getLeftMenuFragment();

        //添加详情页面
        detailBasepagers =new ArrayList<>();
        detailBasepagers.add(new ChinaMenuDetailPager(context,data.get(0)));
        detailBasepagers.add(new UKMenuDetailPager(context,data.get(0)));
        detailBasepagers.add(new USAMenuDetailPager(context));
        detailBasepagers.add(new JapanMenuDetailPager(context));
        detailBasepagers.add(new GermanyMenuDetailPager(context));
        detailBasepagers.add(new FranceMenuDetailPager(context));
        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);



    }

    private DiscoverPagerBean2 parsedJson2(String json) {
        DiscoverPagerBean2 bean2 = new DiscoverPagerBean2();
        try {
            JSONObject object = new JSONObject(json);


            int retcode = object.optInt("retcode");
            bean2.setRetcode(retcode);//retcode字段解析成功

            JSONArray data = object.optJSONArray("data");
            if(data!=null&&data.length()>0){

                List<DiscoverPagerBean2.DetailPagerData> detailPagerDatas = new ArrayList<>();
                //设置列表数据
                bean2.setData(detailPagerDatas);
                //for循环，解析每条数据
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = (JSONObject) data.get(i);

                    DiscoverPagerBean2.DetailPagerData detailPagerData = new DiscoverPagerBean2.DetailPagerData();
                    //添加到集合中
                    detailPagerDatas.add(detailPagerData);

                    int id = jsonObject.optInt("id");
                    detailPagerData.setId(id);
                    int type= jsonObject.optInt("type");
                    detailPagerData.setType(type);
                    String title= jsonObject.optString("title");
                    detailPagerData.setTitle(title);
                    String url= jsonObject.optString("url");
                    detailPagerData.setUrl(url);
                    String url1= jsonObject.optString("url1");
                    detailPagerData.setUrl1(url1);
                    String dayurl= jsonObject.optString("dayurl");
                    detailPagerData.setDayurl(dayurl);
                    String excurl= jsonObject.optString("excurl");
                    detailPagerData.setExcurl(excurl);
                    String weekurl= jsonObject.optString("weekurl");
                    detailPagerData.setWeekurl(weekurl);


                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children!=null&&children.length()>0){

                        List<DiscoverPagerBean2.DetailPagerData.ChildrenData> childrenDatas=new ArrayList<>();
                        //设置集合ChildrenData
                        detailPagerData.setChildren(childrenDatas);


                        for (int j = 0; j <children.length(); j++) {
                            JSONObject childrenItem = (JSONObject) children.get(j);

                            DiscoverPagerBean2.DetailPagerData.ChildrenData childrenData = new DiscoverPagerBean2.DetailPagerData.ChildrenData();
                            //添加到集合汇总
                            childrenDatas.add(childrenData);

                            int childrenid = childrenItem.optInt("id");
                            childrenData.setId(childrenid);
                            String childrentitle = childrenItem.optString("title");
                            childrenData.setTitle(childrentitle);
                            int childrentype = childrenItem.optInt("type");
                            childrenData.setType(childrentype);
                            String childrenurl = childrenItem.optString("url");
                            childrenData.setUrl(childrenurl);




                        }
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bean2;

    }

    /**
     * 解析json数据:1、使用系统API解析json 2、使用第三方框架解析json数据，例如Gson，fastjson
     * @param json
     * @return
     */
    private DiscoverPagerBean2 parsedJson(String json) {
        Gson gson=new Gson();
        DiscoverPagerBean2 bean=gson.fromJson(json,DiscoverPagerBean2.class);
        return bean;

    }

    /**
     * 根据位置切换详情页面
     * @param position
     */
    public void switchPager(int position) {
        //1.设置标题
        tv_title.setText(data.get(position).getTitle());
        //2.移除之前内容
        fl_content.removeAllViews();

        //3.添加新内容
        MenuDetailBasePager detailBasePager = detailBasepagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData();//初始化数据

        fl_content.addView(rootView);
    }
}