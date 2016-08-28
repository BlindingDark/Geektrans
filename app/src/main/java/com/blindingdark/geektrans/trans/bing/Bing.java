package com.blindingdark.geektrans.trans.bing;

import android.content.SharedPreferences;
import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.thread.TransReqThread;
import com.blindingdark.geektrans.trans.bing.bean.BingSettings;

/**
 * Created by BlindingDark on 2016/8/28 0028.
 */
public class Bing implements TransEngine {
    BingSettings bingSettings;
    public static final String engineName = "bing";

    public Bing(BingSettings bingSettings) {
        this.bingSettings = bingSettings;
    }

    @Override
    public String getEngineName() {
        return engineName;
    }

    @Override
    public void trans(String req, Handler handler, SharedPreferences preferences) {
        new Thread(new TransReqThread(new BingTransReq(bingSettings, req), handler, preferences)).start();

    }
}
