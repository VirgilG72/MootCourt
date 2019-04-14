package com.example.a61979.mootcourt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a61979.mootcourt.RequestInterface.login_interface;
import com.example.a61979.mootcourt.activity.MainActivity;
import com.example.a61979.mootcourt.domain.LoginBean;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == 2)) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            et_user.setText(data.getStringExtra("id"));
            et_password.setText(data.getStringExtra("psw"));
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_JUDGE: {
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    boolean result = bundle.getBoolean("result", false);
                    Log.i("1", "handleMessage: =======" + result);
                    //Resultbean bean = new Gson().fromJson(result, Resultbean.class);
                    //int userid = bean.getUserid();

                    if (result) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

            }
            return false;
        }
    });

    private void initView() {
        et_password = (EditText) findViewById(R.id.et_password);
        et_user = (EditText) findViewById(R.id.et_user);
    }

    public void login(View view) {
        final String id = et_user.getText().toString();
        final String password = et_password.getText().toString();
        if ((id.length() == 0) || (password.length() == 0)) {
            Toast.makeText(this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //                OkHttpClient sClient = new OkHttpClient();
                    //                SSLContext sc = null;
                    //                try {
                    //                    sc = SSLContext.getInstance("SSL");
                    //                    sc.init(null, new TrustManager[]{new X509TrustManager() {
                    //                        @Override
                    //                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                    //
                    //                        }
                    //
                    //                        @Override
                    //                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                    //
                    //                        }
                    //
                    //                        @Override
                    //                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    //                            return null;
                    //                        }
                    //                    }}, new SecureRandom());
                    //                } catch (Exception e) {
                    //                    e.printStackTrace();
                    //                }
                    //
                    //                HostnameVerifier hv1 = new HostnameVerifier() {
                    //                    @Override
                    //                    public boolean verify(String hostname, SSLSession session) {
                    //                        return true;
                    //                    }
                    //                };
                    //
                    //                String workerClassName="okhttp3.OkHttpClient";
                    //                try {
                    //                    Class workerClass = Class.forName(workerClassName);
                    //                    Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
                    //                    hostnameVerifier.setAccessible(true);
                    //                    hostnameVerifier.set(sClient, hv1);
                    //
                    //                    Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
                    //                    sslSocketFactory.setAccessible(true);
                    //                    sslSocketFactory.set(sClient, sc.getSocketFactory());
                    //                } catch (Exception e) {
                    //                    e.printStackTrace();
                    //                }
                    //                RetrofitCall.request();
                    //                String result=RetrofitCall.result;

                    Retrofit retrofit = new Retrofit.Builder()
                            //.baseUrl("https://thunder.zebibyte.cn/")
                            .baseUrl("http://www.mocky.io/v2/5c98cecd3200004b00d9061e/")
                            //.client(sClient)
                            //.addConverterFactory(GsonConverterFactory.create())
                            .build();
                    login_interface request = retrofit.create(login_interface.class);
                    Call<ResponseBody> call = request.getCall("http://www.mocky.io/v2/5c98cecd3200004b00d9061e/", id, password);
                    //Call<ResponseBody> call = request.getCall("http://www.mocky.io/v2/5c98cecd3200004b00d9061e/");
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
        }
    }


    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, RequestCode);

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
        msg.what = LOGIN_JUDGE;
        handler.sendMessage(msg);

    }
}
