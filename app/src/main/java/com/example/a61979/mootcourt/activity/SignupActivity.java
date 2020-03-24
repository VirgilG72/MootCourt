package com.example.a61979.mootcourt.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a61979.mootcourt.R;

public class SignupActivity extends AppCompatActivity {

    private WebView wv_gzharticle;
    private String[] Articles = new String[]{"https://www.chinacourt.org/article/detail/2020/03/id/4864213.shtml", "https://www.chinacourt.org/article/detail/2020/03/id/4855877.shtml"
            , "https://www.chinacourt.org/article/detail/2020/03/id/4839296.shtml"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzharticle);
        findViews();
        getArticleData();
    }

    private void getArticleData() {
        int i = getIntent().getIntExtra("url", 0);

        WebSettings mSettings = wv_gzharticle.getSettings();
 //       settings.setJavaScriptEnabled(true);

//        //webView.setListener(this, this);
//        WebSettings mSettings = webView.getSettings();
//        // 支持获取手势焦点
//        webView.requestFocusFromTouch();
//        webView.setHorizontalFadingEdgeEnabled(true);
//        webView.setVerticalFadingEdgeEnabled(false);
//        webView.setVerticalScrollBarEnabled(false);
        // 支持JS
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(true);
        mSettings.setLoadWithOverviewMode(true);
        // 支持插件
        mSettings.setPluginState(WebSettings.PluginState.ON);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        // 支持缩放
        mSettings.setSupportZoom(true);//就是这个属性把我搞惨了，
        // 隐藏原声缩放控件
        mSettings.setDisplayZoomControls(false);
        // 支持内容重新布局
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        mSettings.setSupportMultipleWindows(true);
        // 设置缓存模式
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setAppCacheEnabled(true);
        //mSettings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());
        // 设置可访问文件
        mSettings.setAllowFileAccess(true);
        mSettings.setNeedInitialFocus(true);
        // 支持自定加载图片
        if (Build.VERSION.SDK_INT >= 19) {
 mSettings.setLoadsImagesAutomatically(true);
        } else {
mSettings.setLoadsImagesAutomatically(false);
        }
        mSettings.setNeedInitialFocus(true);
        // 设定编码格式
        mSettings.setDefaultTextEncodingName("UTF-8");

        wv_gzharticle.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        wv_gzharticle.loadUrl(Articles[i - 1]);

    }


    private void findViews() {
        wv_gzharticle = (WebView) findViewById(R.id.wv_gzharticle);
    }
}
