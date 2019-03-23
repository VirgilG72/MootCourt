package com.example.a61979.mootcourt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;

public class NewDetailActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;
    private WebView webview;
    private ProgressBar pbLoading;
    private String url;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-16 16:30:01 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvTitle = (TextView)findViewById( R.id.tv_title );
        ibMenu = (ImageButton)findViewById( R.id.ib_menu );
        ibBack = (ImageButton)findViewById( R.id.ib_back );
        ibTextsize = (ImageButton)findViewById( R.id.ib_textsize );
        ibShare = (ImageButton)findViewById( R.id.ib_share );
        webview = (WebView)findViewById( R.id.webview );
        pbLoading = (ProgressBar)findViewById( R.id.pb_loading );

        tvTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);


        ibBack.setOnClickListener( this );
        ibTextsize.setOnClickListener( this );
        ibShare.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-12-16 16:30:01 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == ibBack ) {
            // Handle clicks for ibMenu
            finish();

        }  else if ( v == ibTextsize ) {
            // Handle clicks for ibTextsize
            Toast.makeText(this,"设置文字大小", Toast.LENGTH_SHORT).show();
        } else if ( v == ibShare ) {
            // Handle clicks for ibShare
            //Toast.makeText(this,"分享", Toast.LENGTH_SHORT).show();
            //showShare();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        findViews();
        getNewsData();
    }
//    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle("模拟法庭");
//        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl("http://gongjiazheng.top/");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("欢迎光临龚家政个人博客");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url在微信、微博，Facebook等平台中使用
//        oks.setUrl("http://gongjiazheng.top/");
//        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
//        // 启动分享GUI
//        oks.show(this);
//    }


    private void getNewsData() {
        url = getIntent().getStringExtra("url");

        //设置支持JS
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        webSettings.setUseWideViewPort(true);
        //增加缩放按钮
        webSettings.setBuiltInZoomControls(true);
        //不让从当前网页跳转到系统的浏览器中
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.loadUrl(url);
        //webview.loadUrl("http://www.atguigu.com/");
    }
}
