package com.example.a61979.mootcourt.Download;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.activity.Learn;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask mDownloadTask;

    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Download...", progress));
        }

        @Override
        public void onSuccess() {
            String path= mDownloadTask.getdownloadPath();
            mDownloadTask = null;
            //下载成功后将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success... Filepath:"+path, -1));
            Toast.makeText(DownloadService.this, "Download Success...", Toast.LENGTH_SHORT).show();
            Toast.makeText(DownloadService.this, "Filepath:"+path, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask = null;
            //下载失败后将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            mDownloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();

        }
    };
    private String downloadurl;

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, Learn.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager = getNotificationManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//设置通知右上角的灯
            channel.setLightColor(Color.GREEN);
            channel.setShowBadge(true);//显示通知圆点
            //设置通知出现时不震动
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[] {0});
            notificationManager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setContentTitle(title)
                .setNumber(3);

        if (progress >= 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }

        return builder.build();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    DownloadBinder mBinder = new DownloadBinder();

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloadurl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(downloadurl);
                startForeground(1, getNotification("Downloading", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pausedownload();
                Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
            }
        }

        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.canceldownload();

            }
            if (downloadurl != null) {
                //取消下载时需将文件删除，并将通知关闭
                String fileName = downloadurl.substring(downloadurl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();

            }


        }
    }


}
