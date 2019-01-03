package com.example.a61979.mootcourt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.fragment.ContentFragment;
import com.example.a61979.mootcourt.fragment.LeftmenuFragment;
import com.example.a61979.mootcourt.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;

import java.util.HashMap;

public class MainActivity extends SlidingFragmentActivity{

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";
    private String[] tags=new String[]{"1","2"};
    private MobPushReceiver receiver=new MobPushReceiver() {
        @Override
        public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
            //接收自定义消息
        }
        @Override
        public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
            //接收通知消息
            String messageId = message.getMessageId();
            Toast.makeText(MainActivity.this,"messgeId======"+messageId,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
            //接收通知消息被点击事件
            HashMap<String, String> extrasMap = message.getExtrasMap();
            String url = extrasMap.get("url");
            //打开自定义的Activity
            Intent i = new Intent(context, NewDetailActivity.class);
            i.putExtra("url",url);
            MainActivity.this.startActivity(i);

        }
        @Override
        public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
            //接收tags的增改删查操作
        }
        @Override
        public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
            //接收alias的增改删查操作
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        //初始化Fragm
        initFragment();
        MobPush.setAlias("test1");//设置别名
        MobPush.addTags(tags);//设置标签
        MobPush.addPushReceiver(receiver);
    }

    private void initSlidingMenu() {
        //主页面
        setContentView(R.layout.activity_main);
        //左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);
        //设置显示的模式
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置滑动的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置主页占据的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,200));
    }

    private void initFragment() {
        //1、得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2、开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //3、替换
        ft.replace(R.id.fl_main_content,new ContentFragment(), MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_leftmenu,new LeftmenuFragment(), LEFTMENU_TAG);
        //4、提交
        ft.commit();
    }

    /**
     * 得到左侧菜单的Fragment
     * @return
     */
    public LeftmenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
         return (LeftmenuFragment) fm.findFragmentByTag(LEFTMENU_TAG);

    }

    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (ContentFragment) fm.findFragmentByTag(MAIN_CONTENT_TAG);
    }

}
