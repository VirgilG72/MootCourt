package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.domain.BUser;
import com.example.a61979.mootcourt.view.Registeractivity2;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class Loginactivity2 extends AppCompatActivity {
    private static final int LOGIN_JUDGE = 1;
    private EditText et_password;
    private EditText et_user;
    private int RequestCode = 1;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
    }
    private void initView() {
        et_password = (EditText) findViewById(R.id.et_password);
        et_user = (EditText) findViewById(R.id.et_user);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == 2)) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            et_user.setText(data.getStringExtra("id"));
            et_password.setText(data.getStringExtra("psw"));
        }
    }
    public void login(View view){
        final String id = et_user.getText().toString();
        final String password = et_password.getText().toString();
        if ((id.length() == 0) || (password.length() == 0)) {
            Toast.makeText(this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            BUser.loginByAccount(id, password, new LogInListener<BUser>() {
                @Override
                public void done(BUser user, BmobException e) {
                    if (e == null) {
                        Intent intent = new Intent(Loginactivity2.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(Loginactivity2.this, "登录成功:" +user.getUsername(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Loginactivity2.this, "登录失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void register(View view) {
        Intent intent = new Intent(this, Registeractivity2.class);
        startActivityForResult(intent, RequestCode);

    }
}
