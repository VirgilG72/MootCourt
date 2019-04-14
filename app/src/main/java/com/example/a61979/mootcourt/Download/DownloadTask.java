package com.example.a61979.mootcourt.Download;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;
    private DownloadListener mDownloadListener;
    private boolean isCancelled = false;
    private boolean isPaused = false;
    private int lastProgress;
    private static final String TAG = "DownloadTask";
    public String fileName;
    public String directory;


    public DownloadTask(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;

    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile savedfile = null;
        File file = null;
        try {
            long downloadlength = 0;
            String downloadUrl = strings[0];
            fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            Log.i(TAG, "doInBackground: ===========filepath:" + directory);
            Log.i(TAG, "doInBackground: ===========filelastpath:" + fileName);

            file = new File(directory + fileName);

            if (file.exists()) {
                downloadlength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            Log.i(TAG, "doInBackground: ===========contentlength:" + contentLength);
            if (contentLength == downloadlength) {
                return TYPE_SUCCESS;
            } else if (contentLength == 0) {

                return TYPE_FAILED;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadlength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedfile = new RandomAccessFile(file, "rw");
                savedfile.seek(downloadlength);
                byte[] b = new byte[1024];
                int total = 0;
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    if (isCancelled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedfile.write(b, 0, len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadlength) * 100 / contentLength);
                        publishProgress(progress);
                    }

                }
            }
            response.body().close();
            return TYPE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedfile != null) {
                    savedfile.close();
                }
                if (isCancelled && file != null) {
                    file.delete();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        switch (status) {
            case TYPE_SUCCESS:
                mDownloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mDownloadListener.onFailed();
                break;
            case TYPE_CANCELED:
                mDownloadListener.onCancel();
                break;
            case TYPE_PAUSED:
                mDownloadListener.onPaused();
                break;
        }
    }

    public void pausedownload() {
        isPaused = true;
    }

    public void canceldownload() {
        isCancelled = true;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (progress > lastProgress) {
            mDownloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    public String getdownloadPath() {
        return directory+fileName;

    }
}
