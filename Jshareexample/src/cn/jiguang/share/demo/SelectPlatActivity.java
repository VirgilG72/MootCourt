package cn.jiguang.share.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.share.android.api.AuthListener;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.model.AccessTokenInfo;
import cn.jiguang.share.android.model.BaseResponseInfo;
import cn.jiguang.share.android.model.UserInfo;
import cn.jiguang.share.android.utils.Logger;

public class SelectPlatActivity extends Activity {
    private static final String TAG="SelectPlatActivity";
    private ProgressDialog progressDialog;
    private int type;
    private ListView listView;
    private List<String> dataList;
    private MyAdapter mMyAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(SelectPlatActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            if (type == Platform.ACTION_AUTHORIZING && mMyAdapter != null) {
                mMyAdapter.notifyDataSetChanged();
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍候");
        type = getIntent().getIntExtra("type", Platform.ACTION_SHARE);
        listView = (ListView) findViewById(R.id.plat_list);
        setTitle((type == Platform.ACTION_SHARE ? "分享" : type == Platform.ACTION_AUTHORIZING ? "授权" : "获取用户信息") + "平台选择");
        dataList = new ArrayList<String>();
        List<String> platformList = JShareInterface.getPlatformList();
        if (platformList == null || platformList.isEmpty()) {
            Toast.makeText(this, "请检查配置文件是否正确", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (type == Platform.ACTION_SHARE) {
            dataList.addAll(platformList);
        } else if (type == Platform.ACTION_AUTHORIZING || type == Platform.ACTION_USER_INFO) {
            for (String platform : platformList) {
                if (JShareInterface.isSupportAuthorize(platform)) {
                    dataList.add(platform);
                }
            }
        }
        listView.setAdapter(mMyAdapter = new MyAdapter());
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    class MyAdapter extends BaseAdapter {

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
            final String platform = dataList.get(i);
            Button textView = new Button(SelectPlatActivity.this);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            if (type == Platform.ACTION_AUTHORIZING) {
                boolean isAuthorize = JShareInterface.isAuthorize(platform);
                if (!isAuthorize) {
                    textView.setText(platform);
                } else {
                    textView.setText(platform + "已授权");
                }
            } else {
                textView.setText(platform);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "platform:" + platform + ",type:" + type);
                    switch (type) {
                        case Platform.ACTION_SHARE://分享
                            Intent intent = new Intent(SelectPlatActivity.this, ShareTypeActivity.class);
                            intent.putExtra(ShareTypeActivity.KEY_PLAT_NAME, platform);
                            startActivity(intent);
                            break;
                        case Platform.ACTION_AUTHORIZING://授权,取消授权
                            progressDialog.show();
                            if (!JShareInterface.isAuthorize(platform)) {
                                JShareInterface.authorize(platform, mAuthListener);
                            } else {
                                JShareInterface.removeAuthorize(platform, mAuthListener);
                            }
                            break;
                        case Platform.ACTION_USER_INFO://获取个人信息
                            progressDialog.show();
                            JShareInterface.getUserInfo(platform, mAuthListener);
                            break;
                    }
                }
            });
            return textView;
        }
    }

    /**
     * 授权、获取个人信息回调
     * action ：Platform.ACTION_AUTHORIZING 授权
     * Platform.ACTION_USER_INFO 获取个人信息
     */
    AuthListener mAuthListener = new AuthListener() {
        @Override
        public void onComplete(Platform platform, int action, BaseResponseInfo data) {
            Logger.dd(TAG, "onComplete:" + platform + ",action:" + action + ",data:" + data);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    if (data instanceof AccessTokenInfo) {        //授权信息
                        String token = ((AccessTokenInfo) data).getToken();//token
                        long expiration = ((AccessTokenInfo) data).getExpiresIn();//token有效时间，时间戳
                        String refresh_token = ((AccessTokenInfo) data).getRefeshToken();//refresh_token
                        String openid = ((AccessTokenInfo) data).getOpenid();//openid
                        //授权原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        toastMsg = "授权成功:" + data.toString();
                        Logger.dd(TAG, "openid:" + openid + ",token:" + token + ",expiration:" + expiration + ",refresh_token:" + refresh_token);
                        Logger.dd(TAG, "originData:" + originData);
                    }
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    toastMsg = "删除授权成功";
                    break;
                case Platform.ACTION_USER_INFO:
                    if (data instanceof UserInfo) {      //第三方个人信息
                        String openid = ((UserInfo) data).getOpenid();  //openid
                        String name = ((UserInfo) data).getName();  //昵称
                        String imageUrl = ((UserInfo) data).getImageUrl();  //头像url
                        int gender = ((UserInfo) data).getGender();//性别, 1表示男性；2表示女性
                        //个人信息原始数据，开发者可自行处理
                        String originData = data.getOriginData();
                        toastMsg = "获取个人信息成功:" + data.toString();
                        Logger.dd(TAG, "openid:" + openid + ",name:" + name + ",gender:" + gender + ",imageUrl:" + imageUrl);
                        Logger.dd(TAG, "originData:" + originData);
                    }
                    break;
            }
            if (handler != null) {
                Message msg = handler.obtainMessage(1);
                msg.obj = toastMsg;
                msg.sendToTarget();
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            Logger.dd(TAG, "onError:" + platform + ",action:" + action + ",error:" + error);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "授权失败";
                    break;
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    toastMsg = "删除授权失败";
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "获取个人信息失败";
                    break;
            }
            if (handler != null) {
                Message msg = handler.obtainMessage(1);
                msg.obj = toastMsg + (error != null ? error.getMessage() : "") + "---" + errorCode;
                msg.sendToTarget();
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            Logger.dd(TAG, "onCancel:" + platform + ",action:" + action);
            String toastMsg = null;
            switch (action) {
                case Platform.ACTION_AUTHORIZING:
                    toastMsg = "取消授权";
                    break;
                // TODO: 2017/6/23 删除授权不存在取消
                case Platform.ACTION_REMOVE_AUTHORIZING:
                    break;
                case Platform.ACTION_USER_INFO:
                    toastMsg = "取消获取个人信息";
                    break;
            }
            if (handler != null) {
                Message msg = handler.obtainMessage(1);
                msg.obj = toastMsg;
                msg.sendToTarget();
            }
        }
    };

}
