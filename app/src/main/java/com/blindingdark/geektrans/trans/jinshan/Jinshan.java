package com.blindingdark.geektrans.trans.jinshan;

import android.content.SharedPreferences;
import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;


/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class Jinshan implements TransEngine {

    JinshanSettings jinshanSettings;
    public final static String ENGINE_NAME = "com.blindingdark.geektrans.trans.jinshan.Jinshan";
    public final static String ENGINE_PACKAGE_NAME = ENGINE_NAME;
    private SharedPreferences preferences;

    @Override
    public void trans(final String req, final Handler handler, SharedPreferences preferences) {
        this.setPreferences(preferences);
        this.trans(req, handler);
    }

    @Override
    public void trans(final String req, final Handler handler) {
        new JinshanTransReq(jinshanSettings, req, handler).trans();
    }

    @Override
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
        this.jinshanSettings = new JinshanSettings(preferences);

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
