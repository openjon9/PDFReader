package com.coder;

import android.app.Application;
import org.xutils.x;

/**
 * Created by Rey on 2018/10/8.
 */

public class MyApp  extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this); //初始化


    }
}
