package com.example.a61979.mootcourt;

import android.app.Application;

import com.mob.MobSDK;

import org.xutils.x;

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
        MobSDK.init(this);

    }
}
