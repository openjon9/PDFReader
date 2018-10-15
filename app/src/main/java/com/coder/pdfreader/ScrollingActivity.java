package com.coder.pdfreader;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ServiceUtils;
import com.coder.FileUtil;
import com.coder.Fragment.Fragment2;
import com.coder.Fragment.Fragment4;
import com.coder.Srevice.downloadService;
import com.gastudio.downloadloadding.library.GADownloadingView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScrollingActivity extends AppCompatActivity {


    private String imgPath;
    private ImageView img;
    private String TAG = "coderPDF";
    private ScrollingActivity context;
    private String mp4;
    private TextView download;

    private String name;
    private HttpConfig httpconfig;
    private KJHttp kjhttp;

    private float pro;
    private GADownloadingView ga_downloading;
    private boolean isdownLoad = false;
    private HttpURLConnection conn;
    private downloadBroadcaseReceiver receiver;
    private int str;
    private Intent downIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        httpconfig = new HttpConfig();
        // httpconfig.cacheTime = 0;
        kjhttp = new KJHttp(httpconfig);
        // x.view().inject(context); //xUtils3注解模块的使用


        receiver = new downloadBroadcaseReceiver();
        registerReceiver(receiver, new IntentFilter("download"));

        findView();

        imgPath = getIntent().getStringExtra("imgPath");
        mp4 = getIntent().getStringExtra("mp4");
        name = getIntent().getStringExtra("name");

        if (imgPath != null) {

            Picasso.with(context)
                    .load(imgPath)
                    .placeholder(R.drawable.preset_img) //圖片下載完成前的預設本地圖片
                    .error(R.drawable.preset_img) //加載失敗顯示的預設圖
                    .resize(480, 200) //服务端可能给我们一些奇怪的尺寸的图片，我们可以使用resize(int w,int hei) 来重新设置尺寸。
                    .into(img);

            Log.d(TAG, "imgPath:" + imgPath);

        }

        Log.d(TAG, "mp4:" + mp4);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, YouTubeActivity.class);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ServiceUtils.isServiceRunning(context, "com.coder.Srevice.downloadService")) {

            if (!mp4.equals("")) {
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!isdownLoad) {
                            isdownLoad = true;

                            String savePath_mp4 = "";

                            if (mp4.contains("mp4")) {
                                savePath_mp4 = Environment.getExternalStorageDirectory() + "/PDFReader/PDFReaderFile/" + name + ".mp4";

                            } else if (mp4.contains("PDF")) {
                                savePath_mp4 = Environment.getExternalStorageDirectory() + "/PDFReader/PDFReaderFile/" + name + ".pdf";

                            } else {
                                isdownLoad = false;
                                return;
                            }


                            downIntent = new Intent(context, downloadService.class);
                            downIntent.putExtra("savePath_mp4", savePath_mp4);
                            downIntent.putExtra("imgPath", imgPath);
                            downIntent.putExtra("mp4", mp4);
                            downIntent.putExtra("name", name);
                            startService(downIntent);


                            //   downloadFile(savePath_mp4);
                            //下載
                            // downloadPic(savePath_mp4, imgPath);

                        }
                    }
                });
            }
        }else {
            Log.d(TAG,"999");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }

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
        Picasso.with(context).load(imgPath).into(target);

    }

    private void downloadFile(String savePath) {

        kjhttp.download(savePath, mp4, new HttpCallBack() {
            @Override
            public void onPreStart() {
                super.onPreStart();

                Log.d(TAG, "開始下載中");
                ga_downloading.performAnimation();
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Log.d(TAG, "t:" + t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Log.d(TAG, "errorNo:" + errorNo + "\nstrMsg:" + strMsg);
                Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
                ga_downloading.onFail();
                ga_downloading.setVisibility(View.GONE);
                isdownLoad = false;

            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.d(TAG, "下載完成");

                Toast.makeText(context, "下載完成", Toast.LENGTH_SHORT).show();
                ga_downloading.setVisibility(View.GONE);
                isdownLoad = false;


            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);

                pro = (((float) current / (float) count) * 100);
                ga_downloading.updateProgress((int) pro);

                Log.d(TAG, "pro進度:" + pro);
                Log.d(TAG, "current進度:" + current + "\tcount:" + count);

            }
        });

    }

    private void findView() {

        img = (ImageView) findViewById(R.id.img);


        download = (TextView) findViewById(R.id.download);

        ga_downloading = (GADownloadingView) findViewById(R.id.ga_downloading);

    }

    private class downloadBroadcaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            str = intent.getIntExtra("download", -1);
//
//            switch (str) {
//                case 10001:
//                    // Log.d(TAG, "開始下載中2");
//                    ga_downloading.performAnimation();
//                    break;
//                case 10002:
//                    Toast.makeText(context, "下載完成", Toast.LENGTH_SHORT).show();
//                    ga_downloading.setVisibility(View.GONE);
//                    isdownLoad = false;
//
//                    break;
//                case 10003:
//                    ga_downloading.onFail();
//                    ga_downloading.setVisibility(View.GONE);
//                    isdownLoad = false;
//
//                    break;
//                default:
//                    float pp = intent.getFloatExtra("download", 0);
//                    Log.d(TAG, "pp:" + pp);
//                    ga_downloading.updateProgress((int) pp);
//                    break;
//            }

            int pp = intent.getIntExtra("progress", 0);
            ga_downloading.updateProgress(pp);

            if (pp == 0) {
                ga_downloading.performAnimation();
            }

            if (pp == 100) {
                ga_downloading.setVisibility(View.GONE);
                isdownLoad = false;
                Toast.makeText(context, "下載完成", Toast.LENGTH_SHORT).show();

                //  stopService(downIntent);

            }


        }

    }


    //新增點選返回箭頭事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();

        //  overridePendingTransition(R.anim.activity_close, 0); // 動畫關閉 上到下

        overridePendingTransition(R.anim.activity_close, R.anim.activity_open); //淡出淡入
    }


}
