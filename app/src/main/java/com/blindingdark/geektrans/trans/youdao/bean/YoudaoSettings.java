package com.blindingdark.geektrans.trans.youdao.bean;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class YoudaoSettings {
    String key;
    String keyfrom;
    String divisionLine;

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
