package com.blindingdark.geektrans.trans.baidu.bean;

/**
 * Created by blindingdark on 17-5-19.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransResult {

    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("dst")
    @Expose
    private String dst;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

}