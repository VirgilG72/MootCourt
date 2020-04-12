package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.a61979.mootcourt.activity.MainActivity;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    private static final int GO_HOME = 1;
    private static final int GO_GUIDE = 0;
    private MyHandler handler=new MyHandler(SplashActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
                long DELAY = 2000;
                handler.sendEmptyMessageDelayed(1, DELAY);
       // handler.sendEmptyMessage(1);
    }
   private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;
        SplashActivity activity;
        public MyHandler (SplashActivity activity){
            mActivity=new WeakReference<SplashActivity>(activity);
       }

       @Override
       public void handleMessage(Message msg) {
          activity=mActivity.get();
           if (activity!=null){
               switch (msg.what) {
                   case GO_HOME:
                       gohome();
                       break;
                   case GO_GUIDE:
                       break;

               }
           }
       }

       private void gohome() {
   //         Intent intent = new Intent(activity, Loginactivity2.class);
          Intent intent = new Intent(activity, MainActivity.class);
               activity.startActivity(intent);
         activity.finish();

         }
         }
         }
