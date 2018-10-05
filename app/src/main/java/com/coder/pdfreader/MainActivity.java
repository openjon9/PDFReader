package com.coder.pdfreader;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.Data.data_f1;
import com.coder.Fragment.Fragment1;
import com.coder.Fragment.Fragment2;
import com.coder.Fragment.Fragment3;
import com.coder.Fragment.Fragment4;
import com.coder.Fragment.Fragment5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linear0, linear1, linear2, linear3, linear4, linear5, linear6;
    private TextView name;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private MainActivity context;
    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;
    private Fragment4 f4;
    private Fragment5 f5;
    private Fragment mContent;

    List<data_f1> list_f1;
    private FragmentManager fragmentManager;
    private String TAG = "coderPDF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        findview();
        initData();
        initEvent();

        f1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list_f1);
        f1.setArguments(bundle);
        mContent = f1;


        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            // fragmentManager.addOnBackStackChangedListener(this);
            fragmentManager.beginTransaction().add(R.id.container, f1, "tag1").commit();
            linear1.setBackgroundColor(getResources().getColor(R.color.gray));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case 100:
                    try {
                        Uri uri = data.getData();
                        if (uri != null) {
                            Intent intent = new Intent(context, PDFActivity.class);
                            intent.putExtra("uri", uri.toString());
                            startActivity(intent);
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 200:

                    break;
                case 300:

                    break;
                case 400:
                    try {
                        Uri uri = data.getData();
                        if (uri != null) {
                            Intent intent = new Intent(context, VideoActivity.class);
                            intent.putExtra("uri", uri.toString());
                            startActivity(intent);
                        }
                    } catch (Exception e) {

                    }
                    break;
                case 500:

                    break;


            }

        }

    }

    private void initData() {
        list_f1 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list_f1.add(new data_f1());

        }

    }

    private void initEvent() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        linear1.setOnClickListener(new MyClickListener());
        linear2.setOnClickListener(new MyClickListener());
        linear3.setOnClickListener(new MyClickListener());
        linear4.setOnClickListener(new MyClickListener());
        linear5.setOnClickListener(new MyClickListener());
        linear6.setOnClickListener(new MyClickListener());
    }

    private void findview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        name = (TextView) findViewById(R.id.name);


        linear0 = (LinearLayout) findViewById(R.id.linear0);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        linear6 = (LinearLayout) findViewById(R.id.linear6);


    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            hideAll();

            if (mContent.getTag() == "tag1") {
                finish();
            } else {
                hideAllColor();
                linear1.setBackgroundColor(getResources().getColor(R.color.gray));
                fragmentManager.beginTransaction().show(f1).commit();
                mContent = f1;
            }
            // super.onBackPressed();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void hideAll() {
        if (f1 != null) {
            fragmentManager.beginTransaction().hide(f1).commit();
        }
        if (f2 != null) {
            fragmentManager.beginTransaction().hide(f2).commit();
        }
        if (f3 != null) {
            fragmentManager.beginTransaction().hide(f3).commit();
        }
        if (f4 != null) {
            fragmentManager.beginTransaction().hide(f4).commit();
        }
        if (f5 != null) {
            fragmentManager.beginTransaction().hide(f5).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //  getMenuInflater().inflate(R.menu.main, menu);
        // SearchView searchView = (SearchView) menu.findItem(R.id.Search).getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            hideAll();

            hideAllColor();


            switch (v.getId()) {
                case R.id.linear1:
                    switchContent(mContent, f1, "tag1");
                    fragmentManager.beginTransaction().show(f1).commit();

                    linear1.setBackgroundColor(getResources().getColor(R.color.gray));

                    break;
                case R.id.linear2:
                    if (f2 == null) {
                        f2 = new Fragment2();

                    }
                    switchContent(mContent, f2, "tag2");
                    fragmentManager.beginTransaction().show(f2).commit();

                    linear2.setBackgroundColor(getResources().getColor(R.color.gray));

                    break;
                case R.id.linear3:
                    if (f3 == null) {
                        f3 = new Fragment3();
                    }
                    switchContent(mContent, f3, "tag3");
                    fragmentManager.beginTransaction().show(f3).commit();

                    linear3.setBackgroundColor(getResources().getColor(R.color.gray));

                    break;
                case R.id.linear4:
                    if (f4 == null) {
                        f4 = new Fragment4();
                    }
                    switchContent(mContent, f4, "tag4");
                    fragmentManager.beginTransaction().show(f4).commit();

                    linear4.setBackgroundColor(getResources().getColor(R.color.gray));

                    break;
                case R.id.linear5:
                    if (f5 == null) {
                        f5 = new Fragment5();

                    }
                    switchContent(mContent, f5, "tag5");
                    fragmentManager.beginTransaction().show(f5).commit();

                    linear5.setBackgroundColor(getResources().getColor(R.color.gray));

                    break;
                case R.id.linear6:
                    fragmentManager.beginTransaction().show(mContent).commit();
                    linear6.setBackgroundColor(getResources().getColor(R.color.gray));

                    new AlertDialog.Builder(context)
                            .setMessage("確定登出?")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();

                    break;
            }

        }
    }

    private void hideAllColor() {
        linear1.setBackgroundColor(getResources().getColor(R.color.wihte));
        linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
        linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
        linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
        linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
        linear6.setBackgroundColor(getResources().getColor(R.color.wihte));
    }

    private void switchContent(Fragment from, Fragment to, String tag) {

        if (mContent != to) {
            mContent = to;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.container, to, tag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }

//            else {
//                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//            }
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState); Activity被回收時再啟動後 防止Fragment重疊


    }
}
