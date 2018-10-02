package com.coder.pdfreader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.coder.Data.data_f1;
import com.coder.Fragment.Fragment1;
import com.coder.Fragment.Fragment2;
import com.coder.Fragment.Fragment3;
import com.coder.Fragment.Fragment4;
import com.coder.Fragment.Fragment5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    private LinearLayout linear1, linear2, linear3, linear4, linear5, linear6;
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
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        setTitle("Home");

        mFm = getSupportFragmentManager();

        findview();
        initData();
        initEvent();

        f1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list_f1);
        f1.setArguments(bundle);
        mContent = f1;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, f1, "tag1").commit();
        }


        // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //  navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        list_f1 = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
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
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear5 = (LinearLayout) findViewById(R.id.linear5);
        linear6 = (LinearLayout) findViewById(R.id.linear6);


    }

    @Override
    public void onBackPressed() {
        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if ( mContent.getTag() == "tag1") {
//            setTitle("Home");
//        } else if (mContent.getTag() == "tag2") {
//            setTitle("My Books");
//        } else if (mContent.getTag() == "tag3") {
//            setTitle("My Free Book");
//        } else if (mContent.getTag() == "tag4") {
//            setTitle("Downloaded Books");
//        } else if (mContent.getTag() == "tag5") {
//            setTitle("Expired Books");
//        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.linear1:

                    switchContent(mContent, f1, "tag1");


//                    linear1.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear6.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
                case R.id.linear2:
                    if (f2 == null) {
                        f2 = new Fragment2();
                    }
                    switchContent(mContent, f2, "tag2");


//                    linear2.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear1.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear6.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
                case R.id.linear3:
                    if (f3 == null) {
                        f3 = new Fragment3();
                    }
                    switchContent(mContent, f3, "tag3");

//
//                    linear3.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear1.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear6.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
                case R.id.linear4:
                    if (f4 == null) {
                        f4 = new Fragment4();
                    }
                    switchContent(mContent, f4, "tag4");


//                    linear4.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear1.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear6.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
                case R.id.linear5:
                    if (f5 == null) {
                        f5 = new Fragment5();
                    }
                    switchContent(mContent, f5, "tag5");


//                    linear5.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear1.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear6.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
                case R.id.linear6:


//                    linear6.setBackgroundColor(getResources().getColor(R.color.gray));
//
//                    linear2.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear3.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear4.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear5.setBackgroundColor(getResources().getColor(R.color.wihte));
//                    linear1.setBackgroundColor(getResources().getColor(R.color.wihte));

                    break;
            }
        }
    }

    private void switchContent(Fragment from, Fragment to, String tag) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.container, to, tag).addToBackStack(to.getClass().getSimpleName()).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState); Activity被回收時再啟動後 防止Fragment重疊


    }
}
