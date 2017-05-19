package com.blindingdark.geektrans.trans.baidu;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduSettings;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Baidu implements TransEngine {
    BaiduSettings baiduSettings;
    public final static String ENGINE_NAME = "百度翻译";
    public final static String ENGINE_PACKAGE_NAME = "com.blindingdark.geektrans.trans.baidu.Baidu";

    private SharedPreferences preferences;

    @Override
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        this.baiduSettings = new BaiduSettings(preferences);
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
        this.setPreferences(preferences);
        this.trans(req, handler);
    }

    @Override
    public void trans(final String req, final Handler handler) {
        new BaiduTransReq(baiduSettings, req, handler).trans();
    }
}
