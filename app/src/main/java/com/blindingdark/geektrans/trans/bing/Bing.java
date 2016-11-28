package com.blindingdark.geektrans.trans.bing;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.bing.bean.BingSettings;

/**
 * Created by BlindingDark on 2016/8/28 0028.
 */
public class Bing implements TransEngine {

    BingSettings bingSettings;
    public static final String ENGINE_NAME = "com.blindingdark.geektrans.trans.bing.Bing";
    public static final String ENGINE_PACKAGE_NAME = ENGINE_NAME;
    private SharedPreferences preferences;


    @Override
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        this.bingSettings = new BingSettings(preferences);
    }

    @Override
    public SharedPreferences getPreferences() {
        return preferences;
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }

    @Override
    public void trans(String req, Handler handler, SharedPreferences preferences) {
        //new Thread(new TransReqThread(new BingTransReq(bingSettings, req), handler, preferences)).start();
        this.setPreferences(preferences);
        this.trans(req, handler);
    }

    @Override
    public void trans(final String req, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = new BingTransReq(bingSettings, req).getTrans();
                Message message = new Message();
                message.what = result.getWhat();
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }
}
