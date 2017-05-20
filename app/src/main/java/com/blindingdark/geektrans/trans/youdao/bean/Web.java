package com.blindingdark.geektrans.trans.youdao.bean;

/**
 * Created by blindingdark on 17-5-20.
 */

import android.text.TextUtils;

import java.util.List;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Web {

    @SerializedName("value")
    @Expose
    private List<String> value = null;
    @SerializedName("key")
    @Expose
    private String key;

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        String split = "";
        if (!TextUtils.isEmpty(key)) {
            if (null != value) {
                if (!value.isEmpty()) {
                    split = "\n";
                }
            }
        }

        String values = "";
        if (null != value) {
            for (String s :
                    value) {
                values += s;
                values += "；";
            }
        }

        if (!TextUtils.isEmpty(values)) {
            values = MyStringUnits.cutLast(values);
        }
        return key + split + values;
    }
}