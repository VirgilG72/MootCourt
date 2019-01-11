package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.a61979.mootcourt.activity.MainActivity;

import java.lang.ref.WeakReference;


public class SplashActivity extends AppCompatActivity {
    private static final int GO_HOME = 1;
    private static final int GO_GUIDE = 0;
    private MyHandler handler=new MyHandler(SplashActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//                long DELAY = 2000;
//                handler.sendEmptyMessageDelayed(1, DELAY);
        handler.sendEmptyMessage(1);
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
//                                           UMSGUI.showLogin(new OperationCallback<User>() {
//                                               @Override
//                                               public void onSuccess(User user) {
//                                                   super.onSuccess(user);
//                                                   LogUtil.e("user信息======" + user);
//                                                   Toast.makeText(activity, "登陆成功", Toast.LENGTH_SHORT).show();
//                                                   gohome();
//                                               }
//
//                                               @Override
//                                               public void onFailed(Throwable throwable) {
//                                                   super.onFailed(throwable);
//                                                   LogUtil.e("异常信息======", throwable);
//                                                   Toast.makeText(activity, "登陆失败", Toast.LENGTH_SHORT).show();
//
//                                               }
//
//                                               @Override
//                                               public void onCancel() {
//                                                   super.onCancel();
//                                                   LogUtil.e("取消登录======");
//                                                   Toast.makeText(activity, "登陆取消", Toast.LENGTH_SHORT).show();
//
//                                               }
//                                           });
                       gohome();
                       break;
                   case GO_GUIDE:
                       break;

               }
           }
       }

       private void gohome() {
               Intent intent = new Intent(activity, MainActivity.class);
               activity.startActivity(intent);
               activity.finish();

       }
   }
}
