package com.example.a61979.mootcourt;

import android.app.Application;

import cn.bmob.v3.Bmob;

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
        Bmob.initialize(this, "d0793781b1426b1b5dad4871b0da1686");
//        x.Ext.setDebug(true);
//        x.Ext.init(this);
//        MobSDK.init(this);

    }
}
