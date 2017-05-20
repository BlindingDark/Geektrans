package com.blindingdark.geektrans.trans.youdao;

import android.content.SharedPreferences;
import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Youdao implements TransEngine {
    YoudaoSettings settings;
    SharedPreferences preferences;
    public final static String ENGINE_NAME = "com.blindingdark.geektrans.trans.youdao.Youdao";
    public final static String ENGINE_PACKAGE_NAME = ENGINE_NAME;

    @Override
    public void trans(String req, Handler handler, SharedPreferences preferences) {
        this.setPreferences(preferences);
        this.trans(req, handler);
    }

    @Override
    public void trans(final String req, final Handler handler) {
        new YoudaoTransReq(settings, req, handler).trans();
    }

    @Override
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        this.settings = new YoudaoSettings(preferences);
    }

    @Override
    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }
}
