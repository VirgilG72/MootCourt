package cn.jiguang.share.demo.uitool;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.ShareParams;

/**
 * Created by cloud on 17/1/13.
 */

public class ShareBoard {
    private String mFrom = null;
    private ShareBoardlistener boardlistener = null;
    private Activity activity;
    private List<SnsPlatform> platformlist = new ArrayList();
    private List<ShareParams> contentlist = new ArrayList();
    private View showatView = null;
    private ShareBoardView mShareBoardView;
    private ShareBoardConfig mConfig = null;

    public ShareBoard(Activity activity) {
        if (activity != null) {
            this.activity = (Activity) (new WeakReference(activity)).get();
        }

    }

    public ShareBoard(Activity activity, ShareBoardConfig config) {
        mConfig = config;
        if (activity != null) {
            this.activity = (Activity) (new WeakReference(activity)).get();
        }

    }

    public String getFrom() {
        return this.mFrom;
    }


    public ShareBoard setShareboardclickCallback(ShareBoardlistener listener) {
        this.boardlistener = listener;
        return this;
    }

    public ShareBoard setPlatformList(List<SnsPlatform> list) {
        if(list != null) {
            this.platformlist.clear();
            Iterator var2 = list.iterator();
            while (var2.hasNext()) {
                SnsPlatform temp = (SnsPlatform) var2.next();
                addPlatform(temp);
            }
        }
        return this;
    }

    public ShareBoard addPlatform(SnsPlatform snsPlatform) {
        if (snsPlatform != null && !platformlist.contains(snsPlatform)) {
            this.platformlist.add(snsPlatform);
        }
        return this;
    }

    public ShareBoard clearPlatform(){
        this.platformlist.clear();
        return this;
    }


    public ShareBoard addButton(String showword, String Keyword, String icon, String Grayicon) {
        this.platformlist.add(createSnsPlatform(showword, Keyword, icon, Grayicon, 0));
        return this;
    }

    public static SnsPlatform createSnsPlatform(String showword, String Keyword, String icon, String Grayicon, int indext) {
        SnsPlatform var5 = new SnsPlatform(Keyword);
        var5.mShowWord = showword;
        var5.mIcon = icon;
        var5.mGrayIcon = Grayicon;
        var5.mIndex = indext;
        return var5;
    }


    public void show() {
        this.mShareBoardView = new ShareBoardView(this.activity, this.platformlist, mConfig);
        if (this.boardlistener != null) {
            this.mShareBoardView.setShareBoardlistener(this.boardlistener);
        }
        this.mShareBoardView.setFocusable(true);
        this.mShareBoardView.setBackgroundDrawable(new BitmapDrawable());
        if (this.showatView == null) {
            this.showatView = this.activity.getWindow().getDecorView();
        }
        this.mShareBoardView.showAtLocation(this.showatView, Gravity.BOTTOM, 0, 0);
    }

    public void close() {
        if (this.mShareBoardView != null) {
            this.mShareBoardView.dismiss();
            this.mShareBoardView = null;
        }

    }

}
