package com.coder.pdfreader;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity implements View.OnTouchListener {

    private VideoActivity context;
    private MediaController controller;

    private String TAG = "coderPDF";

    private Uri uri;
    private String str;
    private VideoView video;
    private ImageView paly_img;
    private ImageView full_img;
    private TextView progress_now_text;
    private TextView progress_all_text;
    private LinearLayout linear;
    private boolean isHide = false;

    private int position = 0;
    private Timer timer;
    private MySeekBarTask task;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        context = this;

        findview();

        uri = Uri.parse(getIntent().getStringExtra("uri"));
        // str = getIntent().getStringExtra("uri");
        //  Log.d(TAG, "uri:" + uri);
        //  Log.d(TAG, "str:" + str);

        if (savedInstanceState != null) {

            Toast.makeText(context, "123456", Toast.LENGTH_SHORT).show();
            video.seekTo(position);
            seekBar.setProgress(position);
        }

        setVideo();

        initEvent();


    }

    @Override
    protected void onPause() {
        super.onPause();

        position = video.getCurrentPosition();
        //outState.putInt("currentposition", position);
        video.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        video.seekTo(position);
        seekBar.setProgress(position);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Toast.makeText(context, "123", Toast.LENGTH_SHORT).show();

        position = video.getCurrentPosition();
        outState.putInt("currentposition", position);
        video.pause();
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        position = savedInstanceState.getInt("currentposition");
        video.seekTo(position);
        seekBar.setProgress(position);
        // Toast.makeText(context, "456", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (video != null) {
            video.suspend(); //釋放資源
        }

    }

    private void initEvent() {
        full_img.setOnClickListener(new MyOnclickListener());
        paly_img.setOnClickListener(new MyOnclickListener());
    }

    private void findview() {

        video = (VideoView) findViewById(R.id.video);

        linear = (LinearLayout) findViewById(R.id.linear);

        paly_img = (ImageView) findViewById(R.id.paly_img);
        full_img = (ImageView) findViewById(R.id.full_img);

        progress_now_text = (TextView) findViewById(R.id.progress_now_text);
        progress_all_text = (TextView) findViewById(R.id.progress_all_text);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    private void setVideo() {

        // controller = new MediaController(context);
        // controller.setAnchorView(video); //控制器與 VideoView 互相連結

        //video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.wgperformance));
        // File file = new File(str);
        video.setVideoURI(uri);
        //  video.setVideoPath(file.getAbsolutePath());
        //  video.setMediaController(controller);
        video.setKeepScreenOn(true);
        video.setOnTouchListener(this);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //监听视频装载完成的事件。
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                int time = mediaPlayer.getDuration();
                int minutes_total = time / 1000 / 60;
                int seconds_total = (int) ((time / 1000 / 60F - minutes_total) * 60);

                seekBar.setMax(time);
                seekBar.setProgress(position);
                seekBar.setOnSeekBarChangeListener(new MySeekBarChangeListener());

                progress_all_text.setText(minutes_total + ":" + seconds_total);

                video.seekTo(position);

                if (position == 0) {
                    video.start();

                    timer = new Timer();
                    task = new MySeekBarTask();
                    timer.schedule(task, 0, 1000);
                } else {
                    video.pause();
                    paly_img.setImageResource(R.drawable.ic_play_arrow);
                }


//                Log.d(TAG, "time:" + time);
//                Log.d(TAG, "minutes_total:" + minutes_total);
//                Log.d(TAG, "seconds_total:" + seconds_total);

            }
        });

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { //监听播放完成的事件。
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });

        video.setOnErrorListener(new MediaPlayer.OnErrorListener() { //监听播放发生错误时候的事件。
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

                Log.d(TAG, "ERROR:" + what);
                // Toast.makeText(context, "ERROR:" + what, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            //setSystemUiHide();// 隐藏最上面那一栏

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);


            video.setLayoutParams(layoutParams);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
            // setSystemUiShow();// 显示最上面那一栏

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            video.setLayoutParams(layoutParams);

        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (!isHide) {
            linear.setVisibility(View.VISIBLE);
            isHide = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        isHide = false;
                        linear.setVisibility(View.INVISIBLE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        return false;
    }

    private class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (fromUser && video != null) {
                video.seekTo(progress);
            } else if (!fromUser) {

                int minutes_play = progress / 1000 / 60;
                int seconds_play = (int) ((progress / 1000 / 60F - minutes_play) * 60);

                progress_now_text.setText(minutes_play + ":" + seconds_play);

            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class MySeekBarTask extends TimerTask {

        @Override
        public void run() {

            seekBar.setProgress(video.getCurrentPosition());

            if (!video.isPlaying()) {
                timer.cancel();
            }

        }
    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.full_img:
                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                    break;

                case R.id.paly_img:

                    if (video.isPlaying()) {
                        video.pause();
                        paly_img.setImageResource(R.drawable.ic_play_arrow);
                    } else {
                        video.start();
                        paly_img.setImageResource(R.drawable.ic_pause);

                        timer = new Timer();
                        task = new MySeekBarTask();
                        timer.schedule(task, 0, 1000);
                    }

                    break;


            }
        }
    }

}
