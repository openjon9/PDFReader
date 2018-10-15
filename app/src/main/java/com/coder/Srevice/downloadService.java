package com.coder.Srevice;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.coder.FileUtil;
import com.coder.pdfreader.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Rey on 2018/10/12.
 */

public class downloadService extends Service {


    private Intent broadcastIntent;
    private String savePath_mp4;
    private String imgPath;
    private String TAG = "coderPDF";
    private HttpConfig httpconfig;
    private KJHttp kjhttp;
    private String mp4;
    private String name;
    private float pro;

    private NotificationManager notificationManager;
    private Notification.Builder builder;
    private Notification notification;
    private RemoteViews mview;
    private int currentpro;


    @Override
    public void onCreate() {
        super.onCreate();

        httpconfig = new HttpConfig();
        // httpconfig.cacheTime = 0;
        kjhttp = new KJHttp(httpconfig);

        broadcastIntent = new Intent("download");

        // broadcastIntent.setComponent(new ComponentName("com.coder.pdfreader","com.coder.pdfreader.downloadBroadcaseReceiver"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        savePath_mp4 = intent.getStringExtra("savePath_mp4");
        imgPath = intent.getStringExtra("imgPath");
        mp4 = intent.getStringExtra("mp4");
        name = intent.getStringExtra("name");

        notifitycation();
        downloadPic(imgPath);
        //下載文件
        downloadFile(savePath_mp4);

        // new DownLoadTask().execute(mp4);


        Log.d(TAG, "onStartCommand");


        return START_REDELIVER_INTENT; //Service被系統殺掉, 重啟且Intent會重傳
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void downloadPic(final String imgPath) {

        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //  String imageName = System.currentTimeMillis() + ".png";

                File dcimFile = FileUtil.getDCIMFile(FileUtil.PATH_PHOTOGRAPH, name + ".png");

                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                }

                //  Toast.makeText(ScrollingActivity.this, "圖片下載完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        //Picasso下载
        Picasso.with(getApplicationContext()).load(imgPath).into(target);

    }


    private void downloadFile(String savePath) {

        kjhttp.download(savePath, mp4, new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();
                //broadcastIntent.putExtra("download", 10001);
                // broadcastIntent.putExtra("download", "開始下載");
                //   sendBroadcast(broadcastIntent);


            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);

                pro = (((float) current / (float) count) * 100);

                //  broadcastIntent.putExtra("download", pro);
                // sendBroadcast(broadcastIntent);

                // currentpro =(int)pro;
                if ((int) pro % 2 == 0) {

                    mview = new RemoteViews(getPackageName(), R.layout.download_notifi); //  需要每次都用 new RemoteViews  不然會卡頓當機
                    builder.setContent(mview);

                    mview.setTextViewText(R.id.tv_download_progress, (int) pro + "%");
                    mview.setProgressBar(R.id.tv_download_progressBar, 100, (int) pro, false);
                    notification = builder.build();
                    //  notification.contentView = mview;
                    notificationManager.notify(100, notification);
                }

            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                // Log.d(TAG, "t:" + t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) { //發生錯誤會自動呼叫完成
                super.onFailure(errorNo, strMsg);

                Toast.makeText(getApplicationContext(), "錯誤", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "errorNo:" + errorNo + "\tstrMsg" + strMsg);

                // broadcastIntent.putExtra("download", 10003);
                // broadcastIntent.putExtra("download", "錯誤");
                //  sendBroadcast(broadcastIntent);

                notificationManager.cancelAll();
                stopSelf();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d(TAG, "下載完成");

                //  broadcastIntent.putExtra("download", 10002);
                // broadcastIntent.putExtra("download", "下載完成");
                //  sendBroadcast(broadcastIntent);

                notificationManager.cancelAll();
                stopSelf();

            }

        });

    }

    private void notifitycation() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > 25) {
            NotificationChannel channel = new NotificationChannel("MyID", "下載通知", NotificationManager.IMPORTANCE_MIN);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, "MyID");
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);

        mview = new RemoteViews(getPackageName(), R.layout.download_notifi);
        builder.setContent(mview);

        notification = builder.build();
        // notificationManager.notify(R.string.app_name, notification); //發起第一次通知
        startForeground(100, notification);

    }

    private class DownLoadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {


            for (int i = 0; i <= 100; i++) {
                //  broadcastIntent.putExtra("progress", i);
                //   sendBroadcast(broadcastIntent);

                SystemClock.sleep(200);

                mview = new RemoteViews(getPackageName(), R.layout.download_notifi);
                builder.setContent(mview);

                mview.setTextViewText(R.id.tv_download_progress, i + "%");
                mview.setProgressBar(R.id.tv_download_progressBar, 100, i, false);
                notification = builder.build();

                //  notification.contentView = mview;
                notificationManager.notify(100, notification);

                if (i == 100) {
                    notificationManager.cancelAll();
                    stopSelf();
                }

            }

            return null;
        }
    }

}
