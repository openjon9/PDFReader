package com.coder.pdfreader;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity {

    private VideoView video;
    private YouTubeActivity context;
    private String URL = "https://www.youtube.com/watch?v=3Y0Ut5ozaKs";
    private MediaController controller;


    private String TAG = "coderPDF";
    private YouTubePlayerView youtube_view;


    //public static final String DEVELOPER_KEY = "AIzaSyBY07t2BLSPFsURF8T_d2o5DR4f69d_PAo";
    public static final String DEVELOPER_KEY = "1";
    private String vid = "3Y0Ut5ozaKs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        context = this;

        findview();


        // Youtube VideoView
        youtube_view.initialize(DEVELOPER_KEY, new YoutubeOnInitializedListener());

    }


    private void findview() {


        youtube_view = (YouTubePlayerView) findViewById(R.id.youtube_view);

    }

    private class YoutubeOnInitializedListener implements YouTubePlayer.OnInitializedListener {
        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
            Toast.makeText(context, "Youtube onInitializationFailure !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

            player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onPlaying() {

                }

                @Override
                public void onPaused() {

                }

                @Override
                public void onStopped() {

                }

                @Override
                public void onBuffering(boolean b) {

                }

                @Override
                public void onSeekTo(int i) {

                }
            });


            player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading() { //讀取中
                    Log.d(TAG, "onLoading");
                }

                @Override
                public void onLoaded(String s) { //讀取完成後得到的影片
                    Log.d(TAG, "onLoaded:" + s);
                }

                @Override
                public void onAdStarted() { //撥放廣告
                    Log.d(TAG, "onAdStarted");
                }

                @Override
                public void onVideoStarted() {//開始撥放
                    Log.d(TAG, "onVideoStarted");
                }

                @Override
                public void onVideoEnded() { //撥放完成
                    Log.d(TAG, "onVideoEnded");
                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {
                    Log.d(TAG, "errorReason:" + errorReason);
                }
            });

            if (!wasRestored) {
                player.loadVideo(vid);


            }
        }
    }

}
