package cn.jiguang.share.demo;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;

public class MyApplication extends Application {

    public static String ImagePath;
    public static String VideoPath;

    @Override
    public void onCreate() {
        super.onCreate();
        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
                .setWechat("wxc40e16f3ba6ebabc", "dcad950cd0633a27e353477c4ec12e7a")
                .setQQ("1106011004", "YIbPvONmBQBZUGaN")
                .setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
                .setFacebook("1847959632183996", "JShareDemo")
                .setTwitter("fCm4SUcgYI1wUACGxB2erX5pL", "NAhzwYCgm15FBILWqXYDKxpryiuDlEQWZ5YERnO1D89VBtZO6q")
                .setJchatPro("1847959632183996");
        /**
         * since 1.5.0，1.5.0版本后增加API，支持在代码中设置第三方appKey等信息，当PlatformConfig为null时，或者使用JShareInterface.init(Context)时需要配置assets目录下的JGShareSDK.xml
         **/
        //*
        JShareInterface.init(this, platformConfig);
        /*/
        JShareInterface.init(this);
       /**/

        new Thread(){
            @Override
            public void run() {
                File imageFile =  copyResurces( "jiguang_test_img.png", "test_img.png", 0);
                File videoFile = copyResurces( "jiguang_test.mp4", "jiguang_test.mp4", 0);
                if(imageFile != null){
                    ImagePath = imageFile.getAbsolutePath();
                }

                if(videoFile != null){
                    VideoPath = videoFile.getAbsolutePath();
                }

                super.run();
            }
        }.start();
    }

    private  File copyResurces(String src, String dest, int flag){
        File filesDir = null;
        try {
            if(flag == 0) {//copy to sdcard
                filesDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/jiguang/" + dest);
                File parentDir = filesDir.getParentFile();
                if(!parentDir.exists()){
                    parentDir.mkdirs();
                }
            }else{//copy to data
                filesDir = new File(getFilesDir(), dest);
            }
            if(!filesDir.exists()) {
                filesDir.createNewFile();
                InputStream open = getAssets().open(src);
                FileOutputStream fileOutputStream = new FileOutputStream(filesDir);
                byte[] buffer = new byte[4 * 1024];
                int len = 0;
                while ((len = open.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
                open.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(flag == 0){
                filesDir = copyResurces( src,  dest, 1);
            }
        }
        return filesDir;
    }
}
