package com.example.a61979.mootcourt.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class Learn extends AppCompatActivity {
    //private static final String TAG = "Learn";
    private List<Laws> mNewslist =new ArrayList<>();
    public DownloadService.DownloadBinder mDownloadBinder;
    public ServiceConnection mConnection;
    public String[] newsname={"法律援助条例","法官行为规范","人民法院工作人员处分条例","中华人民共和国法官法","中华人民共和国法官职业道德基本准则","中华人民共和国人民法院组织法"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_main);

        initnews();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(this, mNewslist);
        recyclerView.setAdapter(newsAdapter);


    }
    private void initnews() {
        for (int i = 0; i < 6; i++) {
            Laws laws = new Laws(newsname[i]);
            mNewslist.add(laws);
        }
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
        if (ContextCompat.checkSelfPermission(Learn.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Learn.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


    }
    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private final List<Laws> mLawsList;
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
        public  NewsAdapter(Context context, List<Laws> lawsList){
            this.mLawsList = lawsList;
            this.mContext=context;


        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.laws_item,parent,false);
            final NewsAdapter.ViewHolder holder = new NewsAdapter.ViewHolder(view);
//            holder.newsview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position=holder.getAdapterPosition();
//                    Laws news = mLawsList.get(position);
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
                    intent.putExtra("url","http://192.168.43.157:8080/laws/"+ ++position+".html");
                    startActivity(intent);
                }
            });
            holder.bt_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=holder.getAdapterPosition();
                    System.out.println("position============"+position);
                    String url = "http://192.168.43.157:8080/laws/"+ ++position+".html";//致敬伟大的比特币
                    mDownloadBinder.startDownload(url);
                    //Toast.makeText(view.getContext(),"下载",Toast.LENGTH_SHORT).show();

                }
            });


            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            Laws laws = mLawsList.get(position);
            holder.tv_newstitle.setText(laws.getNewstitle());
        }

        @Override
        public int getItemCount() {
            return mLawsList.size();
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
