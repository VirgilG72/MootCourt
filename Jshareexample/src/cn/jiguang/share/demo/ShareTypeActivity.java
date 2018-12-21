package cn.jiguang.share.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.android.utils.Logger;
import cn.jiguang.share.facebook.Facebook;
import cn.jiguang.share.facebook.messenger.FbMessenger;
import cn.jiguang.share.jchatpro.JChatPro;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.twitter.Twitter;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;

/**
 * Created by cloud on 17/1/9.
 */

public class ShareTypeActivity extends Activity implements AdapterView.OnItemClickListener {
    public static String share_url = "https://www.jiguang.cn";
    public static String share_text = "JShare SDK支持主流社交平台、帮助开发者轻松实现社会化功能！";
    public static String share_title = " 欢迎使用极光社会化组件JShare";
    public static String share_imageurl = "http://img2.3lian.com/2014/f5/63/d/23.jpg";
    public static String share_imageurl_1 = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1308/02/c0/24056523_1375430477597.jpg";
    public static String share_videourl = "http://v.youku.com/v_show/id_XOTQwMDE1ODAw.html?from=s1.8-1-1.2&spm=a2h0k.8191407.0.0";
    public static String share_musicurl = "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3";
    public static String music_shareUrl = "https://y.qq.com/n/yqq/song/109325260_num.html?ADTAG=h5_playsong&no_redirect=1";
    private static final String TAG="ShareTypeActivity";
    public static final String KEY_PLAT_NAME="PLAT_NAME";
    private ProgressDialog progressDialog;
    private ListView listView;
    private String platName;
    private List<ShareType> dataList;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(ShareTypeActivity.this,toastMsg,Toast.LENGTH_SHORT).show();
            if(progressDialog!=null&&progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    };

    private PlatActionListener mPlatActionListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if(handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                handler.sendMessage(message);
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            if(handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + (error != null ? error.getMessage() : "") + "---" + errorCode;
                Logger.dd(TAG,message.obj+"");
                handler.sendMessage(message);
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if(handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                handler.sendMessage(message);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharetype);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候...");
        listView = (ListView) findViewById(R.id.share_list);
        platName = getIntent().getStringExtra(KEY_PLAT_NAME);
        setTitle(platName+"分享类型选择");
        dataList = new ArrayList<>();
        if(JChatPro.Name.equalsIgnoreCase(platName)){
            dataList.add(new ShareType("文本", 0));
            dataList.add(new ShareType("图片", 1));
            dataList.add(new ShareType("图文", 3));


        }else {
            if (platName.equals(SinaWeiboMessage.Name)) {
                dataList.add(new ShareType("链接", 3));
            } else {
                if (!platName.equals(QQ.Name) && !platName.equals(Facebook.Name) && !platName.equals(FbMessenger.Name)) {
                    dataList.add(new ShareType("纯文本", 0));
                }
                dataList.add(new ShareType("纯图片本地", 1));
                if (platName.equals(QZone.Name)) {
                    dataList.add(new ShareType("纯图片url", 2));
                }
                dataList.add(new ShareType("链接", 3));
                if (!platName.equals(Facebook.Name) && !platName.equals(FbMessenger.Name) && !platName.equals(Twitter.Name)) {
                    dataList.add(new ShareType("音乐", 4));
                }
                dataList.add(new ShareType("视频", 5));
                if (platName.equals(Wechat.Name)) {
                    dataList.add(new ShareType("emoji表情", 6));
                    dataList.add(new ShareType("文件分享", 8));
                }
                if(platName.equals(QQ.Name)){
                    dataList.add(new ShareType("app分享", 7));
                }
            }
        }

        listView.setAdapter(new ShareTypeAdapter());
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ShareType shareType = dataList.get(i);
        ShareParams shareParams = new ShareParams();
        if(JChatPro.Name.equalsIgnoreCase(platName)){
            shareParams.setUrl(share_url);
            shareParams.setTargetPkg("com.tencent.mm");
            shareParams.setTargetClass("com.tencent.mm.ui.LauncherUI");
            shareParams.setExtra("this message from jshare.");
        }
        switch (shareType.type){
            case 0:
                shareParams.setTitle(share_title);
                shareParams.setText(share_text);
                shareParams.setShareType(Platform.SHARE_TEXT);
                break;
            case 1:
                shareParams.setUrl(share_url);
                shareParams.setShareType(Platform.SHARE_IMAGE);
                //twitter支持单张、多张（最多4张本地图片）
                if(Twitter.Name.equals(platName)){
                    String[] array = new String[]{ MyApplication.ImagePath, MyApplication.ImagePath};
                    shareParams.setImageArray(array);
                    //shareParams.setImagePath(MyApplication.ImagePath);
                }else{
                    shareParams.setImagePath(MyApplication.ImagePath);
                }
                break;
            case 2:
                shareParams.setShareType(Platform.SHARE_IMAGE);
                //QQ空间支持多张图片，超出9张后，会变成上传相册，上传相册时只支持本地图片
                if(platName.equals(QZone.Name)){
                    String[] array = new String[]{ share_imageurl, share_imageurl_1};
                    shareParams.setImageArray(array);
                }else{
                    shareParams.setImageUrl(share_imageurl);
                }
                break;
            case 3:
                if(JChatPro.Name.equalsIgnoreCase(platName)){
                    shareParams.setShareType(Platform.SHARE_IMAGETEXT);
                    shareParams.setTitle(share_title);
                    shareParams.setText(share_text);
                }else {
                    shareParams.setTitle(share_title);
                    shareParams.setText(share_text);
                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
                    shareParams.setUrl(share_url);
                    shareParams.setImagePath(MyApplication.ImagePath);

                }
                break;
            case 4:
                shareParams.setTitle(share_title);
                shareParams.setText(share_text);
                shareParams.setShareType(Platform.SHARE_MUSIC);
                if(platName.equals(SinaWeibo.Name)){
                    shareParams.setUrl(share_musicurl);
                }else{
                    shareParams.setMusicUrl(share_musicurl);
                    shareParams.setUrl(music_shareUrl);
                    shareParams.setImagePath(MyApplication.ImagePath);
                }
                break;
            case 5:
                //QQ空间支持本地视频
                shareParams.setShareType(Platform.SHARE_VIDEO);
                if(platName.equals(QZone.Name) || platName.equals(Facebook.Name) || platName.equals(FbMessenger.Name) || platName.equals(Twitter.Name)){
                    shareParams.setVideoPath(MyApplication.VideoPath);
                }else{
                    shareParams.setTitle(share_title);
                    shareParams.setText(share_text);
                    shareParams.setUrl(share_videourl);
                    shareParams.setImagePath(MyApplication.ImagePath);
                }
                break;
            case 6:
                //只有微信支持表情
                shareParams.setShareType(Platform.SHARE_EMOJI);
                shareParams.setImagePath(MyApplication.ImagePath);
                shareParams.setFilePath(MyApplication.ImagePath);
                break;
            case 7:
                //只有支持QQ APP
                shareParams.setShareType(Platform.SHARE_APPS);
                shareParams.setImagePath(MyApplication.ImagePath);
                break;
            case 8:
                //只有微信支持文件
                shareParams.setShareType(Platform.SHARE_FILE);
                shareParams.setFilePath(MyApplication.ImagePath);
                break;
            default:
                shareParams.setShareType(Platform.SHARE_IMAGE);
                shareParams.setImageUrl(share_imageurl);
        }
        JShareInterface.share(platName, shareParams, mPlatActionListener);
        progressDialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    class ShareType{
        public int type;
        public String name;
        public ShareType(String name,int type){
            this.name = name;
            this.type = type;
        }
    }



    class ShareTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ShareType shareType = dataList.get(i);
            ListItemView listItemView = new ListItemView(ShareTypeActivity.this);
            listItemView.init(shareType.name);
            return listItemView;
        }
    }
}
