package com.blindingdark.geektrans.global;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhuakuang on 17-12-24.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        //获取Context
        super.onCreate();
        context = getApplicationContext();
    }

    //返回
    public static Context getContextObject() {
        return context;
    }
}
