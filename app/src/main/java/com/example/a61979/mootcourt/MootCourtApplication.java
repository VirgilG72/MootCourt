package com.example.a61979.mootcourt;

import android.app.Application;

import com.mob.MobSDK;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MootCourtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);

        //初始化极光推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        MobSDK.init(this,"2968bac5959b8","cd00f3416991565e2c97900d198153a5");

    }
}
