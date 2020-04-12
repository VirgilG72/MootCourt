package com.example.a61979.mootcourt.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a61979.mootcourt.R;

public class GZHarticleActivity extends AppCompatActivity {

    private WebView wv_gzharticle;
    private String[] Articles=new String[]{"https://mp.weixin.qq.com/s/suwFL87kB2ROMrYB8cmuqw","https://mp.weixin.qq.com/s/Bagx7ba6lBpnwlIjgcz50g"
            ,"https://mp.weixin.qq.com/s/vS0tzcQDNOsgVFssqhcHRw","https://mp.weixin.qq.com/s/v-3s311m5b2mp45dH6EIFw"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzharticle);
        findViews();
        getArticleData();
    }

    private void getArticleData() {
        int i=getIntent().getIntExtra("url",0);

        WebSettings settings = wv_gzharticle.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_gzharticle.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        wv_gzharticle.loadUrl(Articles[i-2]);

    }


    private void findViews() {
        wv_gzharticle = (WebView) findViewById(R.id.wv_gzharticle);
    }
}
