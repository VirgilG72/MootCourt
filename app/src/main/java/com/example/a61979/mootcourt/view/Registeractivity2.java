package com.example.a61979.mootcourt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.domain.BUser;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Registeractivity2 extends AppCompatActivity {
    private static final int REGISTER_JUDGE = 2;
    private static final int SAMEERROR = -1;
    private static final int LENGTHERROR = -2;
    private EditText et_regispsw;
    private EditText et_regisemail;
    private EditText et_regisname;
    private EditText et_regispsw2;
    private String psw;
    private String psw2;
    private int ResultCode = 2;
    private String id;
    private String email;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
    }
    private void initView() {
        et_regispsw = (EditText) findViewById(R.id.et_regispsw);
        et_regisemail = (EditText) findViewById(R.id.et_regisemail);
        et_regisname = (EditText) findViewById(R.id.et_regisname);
        et_regispsw2 = (EditText) findViewById(R.id.et_regispsw2);

    }
    public void registerT(View view) {
        psw = et_regispsw.getText().toString();
        psw2 = et_regispsw2.getText().toString();
        id = et_regisname.getText().toString();
        email = et_regisemail.getText().toString();
        if ((psw.length() < 8) || (psw.length() > 15)) {
            Toast.makeText(Registeractivity2.this, "密码字符数须8~15个", Toast.LENGTH_SHORT).show();

        } else if (psw.equals(psw2)) {
            BUser user=new BUser();
            user.setEmail(email);
            user.setPassword(psw);
            user.setUsername(id);
            user.signUp(new SaveListener<BUser>() {

                @Override
                public void done(BUser bUser, BmobException e) {
                    if (e == null) {
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        intent.putExtra("psw", psw);
                        setResult(ResultCode, intent);
                        finish();
                        Toast.makeText(Registeractivity2.this, "注册成功:" +user.getUsername(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Registeractivity2.this, "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {

            Toast.makeText(Registeractivity2.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }


    }
    public void back(View view) {
        finish();
    }
}
