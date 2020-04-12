package com.example.a61979.mootcourt.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a61979.mootcourt.R;

public class LawsActivity extends AppCompatActivity {

    private WebView webview;
    //private static final String TAG = "LawsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);
        findViews();
        getLawsData();


    }

    private void getLawsData() {
        String url = getIntent().getStringExtra("url");

        //设置支持JS
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//        //设置双击变大变小
//        settings.setUseWideViewPort(true);
//        //增加缩放按钮
//        settings.setBuiltInZoomControls(true);
        //不让当前网页跳转到系统的浏览器
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webview.loadUrl(url);

    }

    private void findViews() {
        webview = (WebView) findViewById(R.id.webview);

    }
}
