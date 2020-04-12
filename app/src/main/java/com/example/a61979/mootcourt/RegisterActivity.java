package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a61979.mootcourt.RequestInterface.register_interface;
import com.example.a61979.mootcourt.domain.LoginBean;
import com.example.a61979.mootcourt.domain.RegisterPost;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

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


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case REGISTER_JUDGE: {
                    try {
                        Bundle bundle = new Bundle();
                        bundle = msg.getData();
                        boolean result = bundle.getBoolean("result");

                        Log.i(TAG, "handleMessage: ======" + id + "&" + psw);
                        if (result) {

                            Intent intent = new Intent();
                            intent.putExtra("id", id);
                            intent.putExtra("psw", psw);
                            setResult(ResultCode, intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
                case SAMEERROR: {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();

                }
                break;
                case LENGTHERROR: {
                    Toast.makeText(RegisterActivity.this, "密码字符数须8~15个", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        }
    });

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

        RegisterPost registerpost = new RegisterPost();
        registerpost.setEmail(email);
        registerpost.setPassword(psw);
        registerpost.setUsername(id);
        Gson gson = new Gson();
        final String route = gson.toJson(registerpost);
        Log.i(TAG, "registerT: ===============" + route);

        Log.i(TAG, "register: =======" + "psw1" + psw + "psw2" + psw2);
        if ((psw.length() < 8) || (psw.length() > 15)) {
            Message msg = new Message();
            msg.what = LENGTHERROR;
            handler.sendMessage(msg);

        } else if (psw.equals(psw2)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://www.mocky.io/v2/5c98cecd3200004b00d9061e/")
                            //.addConverterFactory(GsonConverterFactory.create())
                            .build();
                    register_interface request = retrofit.create(register_interface.class);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), route);
                    Call<ResponseBody> call = request.getCall(body);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String result = new String(response.body().bytes());
                                processData(result);
                                Log.i(TAG, "onResponse:=========result======" + result);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.w(TAG, "onFailure: =========" + t.getMessage(), t);
                        }
                    });
                }
            }.start();


        } else {
            Message msg = new Message();
            msg.what = SAMEERROR;
            handler.sendMessage(msg);
        }

    }

    public void back(View view) {
        finish();
    }


    /**
     * 解析json数据
     *
     * @param json
     * @return
     */
    private void processData(String json) {
        LoginBean bean = new Gson().fromJson(json, LoginBean.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("result", bean.isStatus());
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = REGISTER_JUDGE;
        handler.sendMessage(msg);

    }
}
