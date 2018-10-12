package com.coder.Srevice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.coder.FileUtil;
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


    @Override
    public void onCreate() {
        super.onCreate();

        httpconfig = new HttpConfig();
        // httpconfig.cacheTime = 0;
        kjhttp = new KJHttp(httpconfig);

        broadcastIntent = new Intent("download");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        savePath_mp4 = intent.getStringExtra("savePath_mp4");
        imgPath = intent.getStringExtra("imgPath");
        mp4 = intent.getStringExtra("mp4");
        name = intent.getStringExtra("name");

        downloadPic(savePath_mp4, imgPath);

        Log.d(TAG,"123456");

        return super.onStartCommand(intent, flags, startId);
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

    private void downloadPic(final String savePath_mp4, final String imgPath) {

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

                //下載文件
                downloadFile(savePath_mp4);
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
                Log.d(TAG, "開始下載中1");
              //  ga_downloading.performAnimation();
                broadcastIntent.putExtra("download","開始下載");
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);

                pro = (((float) current / (float) count) * 100);

                broadcastIntent.putExtra("pro", pro);
                sendBroadcast(broadcastIntent);

                //   ga_downloading.updateProgress((int) pro);

                // Log.d(TAG, "pro進度:" + pro);
                // Log.d(TAG, "current進度:" + current + "\tcount:" + count);

            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
               // Log.d(TAG, "t:" + t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
             //   Log.d(TAG, "errorNo:" + errorNo + "\nstrMsg:" + strMsg);
                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT).show();

                broadcastIntent.putExtra("download","錯誤");
                sendBroadcast(broadcastIntent);

              //  ga_downloading.onFail();
              //  ga_downloading.setVisibility(View.GONE);
                //  isdownLoad = false;

                stopSelf();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d(TAG, "下載完成");

               // Toast.makeText(getApplicationContext(), "下載完成", Toast.LENGTH_SHORT).show();
               // ga_downloading.setVisibility(View.GONE);
                // isdownLoad = false;

                broadcastIntent.putExtra("download","下載完成");
                sendBroadcast(broadcastIntent);

                stopSelf();

            }


        });

    }


}
