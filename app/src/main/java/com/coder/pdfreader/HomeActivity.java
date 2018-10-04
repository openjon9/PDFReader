package com.coder.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import me.toptas.fancyshowcase.AnimationListener;
import me.toptas.fancyshowcase.FancyShowCaseView;

public class HomeActivity extends AppCompatActivity {

    private HomeActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;


        new FancyShowCaseView.Builder(this)
                .animationListener(new AnimationListener() {
                    @Override
                    public void onEnterAnimationEnd() {
                        permission();
                        // Toast.makeText(HomeActivity.this, "開始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onExitAnimationEnd() {
                        // Toast.makeText(HomeActivity.this, "結束", Toast.LENGTH_SHORT).show();
                    }
                })
                .title("測試123")
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .build()
                .show();


    }

    private void permission() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }else {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }else {

                permission();
            }
        }


    }
}
