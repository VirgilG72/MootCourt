package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.a61979.mootcourt.activity.MainActivity;


public class SplashActivity extends AppCompatActivity {
    private static final int GO_HOME = 1;
    private static final int GO_GUIDE = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
//                    UMSGUI.showLogin(new OperationCallback<User>() {
//                        @Override
//                        public void onSuccess(User user) {
//                            super.onSuccess(user);
//                            LogUtil.e("user信息======" + user);
//                            Toast.makeText(SplashActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                            gohome();
//                        }
//
//                        @Override
//                        public void onFailed(Throwable throwable) {
//                            super.onFailed(throwable);
//                            LogUtil.e("异常信息======", throwable);
//                            Toast.makeText(SplashActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onCancel() {
//                            super.onCancel();
//                            LogUtil.e("取消登录======");
//                            Toast.makeText(SplashActivity.this, "登陆取消", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                    gohome();
                    break;
                case GO_GUIDE:
                    break;

            }
        }
    };

    private void gohome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //        long DELAY = 2000;
        //        handler.sendEmptyMessageDelayed(1, DELAY);
        handler.sendEmptyMessage(1);
    }
    //    public void sendCode(Context context) {
    //        RegisterPage page = new RegisterPage();
    //        //如果使用我们的ui，没有申请模板编号的情况下需传null
    //        page.setTempCode(null);
    //        page.setRegisterCallback(new EventHandler() {
    //            public void afterEvent(int event, int result, Object data) {
    //                if (result == SMSSDK.RESULT_COMPLETE) {
    //                    // 处理成功的结果
    //                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
    //                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
    //                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
    //                    // TODO 利用国家代码和手机号码进行后续的操作
    //                    Toast.makeText(SplashActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
    //                    gohome();
    //                } else{
    //                    // TODO 处理错误的结果
    //                    Toast.makeText(SplashActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
    //                    finish();
    //                }
    //            }
    //        });
    //        page.show(context);
    //    }

}
