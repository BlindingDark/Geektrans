package com.blindingdark.geektrans.trans.youdao.bean;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.blindingdark.geektrans.trans.youdao.YoudaoSettingsString;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class YoudaoSettings {
    String key = "";
    String keyfrom = "";
    String divisionLine = "";

    public YoudaoSettings(SharedPreferences preferences) {
        key = preferences.getString(YoudaoSettingsString.youdaoKey, "1063467812");
        keyfrom = preferences.getString(YoudaoSettingsString.youdaoKeyfrom, "geektrans");
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(keyfrom)) {
            key = "1063467812";
            keyfrom = "geektrans";
        }
        divisionLine = preferences.getString(YoudaoSettingsString.divisionLine, YoudaoSettingsString.defaultDivLine);

    }

    public YoudaoSettings(String key, String keyfrom, String divisionLine) {
        this.key = key;
        this.keyfrom = keyfrom;
        this.divisionLine = divisionLine;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyfrom() {
        return keyfrom;
    }

    public void setKeyfrom(String keyfrom) {
        this.keyfrom = keyfrom;
    }

    public String getDivisionLine() {
        return divisionLine;
    }

    public void setDivisionLine(String divisionLine) {
        this.divisionLine = divisionLine;
    }


}
