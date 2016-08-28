package com.blindingdark.geektrans.bean;

import java.util.ArrayList;

/**
 * Created by BlindingDark on 2016/8/24 0024.
 */
public class Result {
    public String getOriginalReq() {
        return originalReq;
    }

    public Result setOriginalReq(String originalReq) {
        this.originalReq = originalReq;
        return this;
    }

    String originalReq = "";

    int what = 0;
    String stringResult = "";
    ArrayList<String> soundURLs = new ArrayList<>();

    public int getWhat() {
        return what;
    }

    public Result setWhat(int what) {
        this.what = what;
        return this;
    }

    public String getStringResult() {
        return stringResult;
    }

    public Result setStringResult(String stringResult) {
        this.stringResult = stringResult;
        return this;
    }

    public ArrayList<String> getSoundURLs() {
        if (soundURLs != null) {
            return soundURLs;
        } else {
            return new ArrayList<>();
        }
    }

    public Result setSoundURLs(ArrayList<String> soundURLs) {
        this.soundURLs = soundURLs;
        return this;
    }

    String fromEngineName = "";

    public void setFromEngineName(String fromEngineName) {
        this.fromEngineName = fromEngineName;
    }

    public String getFromEngineName() {

        return fromEngineName;
    }
}
