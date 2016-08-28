package com.blindingdark.geektrans.trans.jinshan.bean;

import android.content.SharedPreferences;

import com.blindingdark.geektrans.trans.jinshan.StringJinshanSettings;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class JinshanSettings {
    String jinshanKey = "";

    public JinshanSettings(String jinshanKey) {
        this.jinshanKey = jinshanKey;
    }

    public JinshanSettings(SharedPreferences preferences) {
        jinshanKey = preferences.getString(StringJinshanSettings.jinshanKey, "");
    }

    public String getJinshanKey() {
        return jinshanKey;
    }

    public void setJinshanKey(String jinshanKey) {
        this.jinshanKey = jinshanKey;
    }
}
