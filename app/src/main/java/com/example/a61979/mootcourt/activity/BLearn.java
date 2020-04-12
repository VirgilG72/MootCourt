package com.example.a61979.mootcourt.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a61979.mootcourt.Download.DownloadService;
import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.domain.Laws;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BLearn extends AppCompatActivity {

    private static final String TAG = "Learn";
    private List<Laws> mNewslist =new ArrayList<>();
    public DownloadService.DownloadBinder mDownloadBinder;
    public ServiceConnection mConnection;
    public String[] newsname={"法律援助条例","法官行为规范","人民法院工作人员处分条例","中华人民共和国法官法","中华人民共和国法官职业道德基本准则","中华人民共和国人民法院组织法"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_main);

        initnews();



    }
    private void initnews() {
        BmobQuery<Laws> bmobQuery = new BmobQuery<>();
       // bmobQuery.include("title,url");
        bmobQuery.findObjects(new FindListener<Laws>() {
            @Override
            public void done(List<Laws> object, BmobException e) {
                if (e == null) {
                    mNewslist.addAll(object);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BLearn.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    NewsAdapter newsAdapter = new NewsAdapter(BLearn.this, mNewslist);
                    recyclerView.setAdapter(newsAdapter);
                    Toast.makeText(BLearn.this,"查询成功："+ mNewslist.size(),Toast.LENGTH_SHORT).show();
                 //   Snackbar.make(mBtnQuery, "查询成功：" + categories.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(BLearn.this,"查询失败："+ e.getMessage(),Toast.LENGTH_SHORT).show();
                   // Snackbar.make(mBtnQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });
//        for (int i = 0; i < mNewslist.size(); i++) {
//            Laws laws =  mNewslist.get(i);
//         //   Log.e(TAG,"Title:"+laws.getTitle()+" Url:"+laws.getUrl());
//         //   Toast.makeText(this,laws.getTitle(),Toast.LENGTH_SHORT).show();
//            LogUtil.e("Title:"+laws.getTitle()+" Url:"+laws.getUrl());
//        //    System.out.println(laws.getTitle()+" "+laws.getUrl());
//
//        }

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("service connect successfully===========");
                mDownloadBinder = (DownloadService.DownloadBinder) service;
                System.out.println("mDownloadBinder的值============"+mDownloadBinder.toString());

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(BLearn.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BLearn.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


    }
    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private final List<Laws> mLaws2List;
        public Context mContext;



        class ViewHolder extends RecyclerView.ViewHolder {
            View newsview;
            TextView tv_newstitle;
            Button bt_view;
            Button bt_download;

            public ViewHolder(View view) {
                super(view);
                newsview=view;
                tv_newstitle = (TextView) view.findViewById(R.id.tv_newstitle);
                bt_view = (Button) view.findViewById(R.id.bt_view);
                bt_download = (Button) view.findViewById(R.id.bt_download);

            }
        }
        public  NewsAdapter(Context context, List<Laws> laws2List){
            this.mLaws2List = laws2List;
            this.mContext=context;


        }
        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.laws_item,parent,false);
            final NewsAdapter.ViewHolder holder = new NewsAdapter.ViewHolder(view);
            //            holder.newsview.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    int position=holder.getAdapterPosition();
            //                    Laws2 news = mLaws2List.get(position);
            //                    Toast.makeText (view.getContext(),"你点击了新闻",Toast.LENGTH_SHORT).show();
            //                }
            //            });
            holder.bt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),"查看",Toast.LENGTH_SHORT).show();
                    int position=holder.getAdapterPosition();
                    System.out.println("position============"+position);
                    //System.out.println("http://192.168.43.157:8080/laws/"+ ++position+".html");
                    Intent intent = new Intent(mContext,LawsActivity.class);
                    Laws laws =  mLaws2List.get(position);
                    intent.putExtra("url",laws.getUrl());
                    startActivity(intent);
                }
            });
            holder.bt_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=holder.getAdapterPosition();
                    System.out.println("position============"+position);
                    Laws laws =  mLaws2List.get(position);
                  //  String url = "http://192.168.43.157:8080/laws/"+ ++position+".html";//致敬伟大的比特币
                    mDownloadBinder.startDownload(laws.getUrl());
                    //Toast.makeText(view.getContext(),"下载",Toast.LENGTH_SHORT).show();

                }
            });


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            Laws laws2 = mLaws2List.get(position);
            holder.tv_newstitle.setText(laws2.getTitle());
        }

        @Override
        public int getItemCount() {
            return mLaws2List.size();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

            default:
                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
