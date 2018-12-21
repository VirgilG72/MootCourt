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
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
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
        long DELAY = 2000;
        handler.sendEmptyMessageDelayed(1, DELAY);
    }
}
