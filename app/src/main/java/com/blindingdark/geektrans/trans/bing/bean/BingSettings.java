package com.blindingdark.geektrans.trans.bing.bean;

import android.content.SharedPreferences;

import com.blindingdark.geektrans.trans.bing.StringBingSettings;

/**
 * Created by BlindingDark on 2016/8/28 0028.
 */
public class BingSettings {
    String bingAppId = "";
    String bingKey = "";
    String bingFrom = "";
    String bingTo = "";

    public BingSettings(SharedPreferences preferences) {
        this.bingAppId = preferences.getString(StringBingSettings.bingAppId, "");
        this.bingKey =  preferences.getString(StringBingSettings.bingKey, "");
        this.bingFrom = preferences.getString(StringBingSettings.bingFrom, "0_auto");
        this.bingTo = preferences.getString(StringBingSettings.bingTo, "0_zh-CHS");
    }

    public String getBingAppId() {
        return bingAppId;
    }

    public void setBingAppId(String bingAppId) {
        this.bingAppId = bingAppId;
    }

    public String getBingKey() {
        return bingKey;
    }

    public void setBingKey(String bingKey) {
        this.bingKey = bingKey;
    }

    public String getBingFrom() {
        if ("0_auto".equals(this.bingFrom)){
            return "";
        }
        return this.bingFrom.split("_")[1];
    }

    public void setBingFrom(String bingFrom) {
        this.bingFrom = bingFrom;
    }

    public String getBingTo() {
        return bingTo.split("_")[1];
    }

    public void setBingTo(String bingTo) {
        this.bingTo = bingTo;
    }
}
