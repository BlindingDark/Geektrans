package com.blindingdark.geektrans.trans.youdao.bean;

/**
 * Created by blindingdark on 17-5-20.
 */

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YoudaoJSONBean {

    @SerializedName("translation")
    @Expose
    private List<String> translation = null;
    @SerializedName("basic")
    @Expose
    private Basic basic;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;
    @SerializedName("web")
    @Expose
    private List<Web> web = null;


    public List<String> getSoundURLs() {
        if (null == basic) {
            return new ArrayList<>();
        }
        return basic.getSoundURLs();
    }

    private String div = "------";

    public String toString(String div) {
        this.setDiv(div);
        return this.toString();
    }

    @Deprecated
    @Override
    public String toString() {
        String result = "";

        if (null != translation) {
            for (String t :
                    translation) {
                result += t;
                result += "；";
            }
        }

        result = MyStringUnits.cutLast(result);

        if (null != basic) {
            String basicStr = basic.toString();
            if (!TextUtils.isEmpty(basicStr)) {
                result += "\n";
                result += div;
                result += "\n";
                result += query;
                result += "\n";
                result += basicStr;
            }
        }

        if (null != web) {
            if (!web.isEmpty()) {
                result += "\n";
                result += div;
            }
        }
        if (null != web) {
            for (Web w :
                    web) {
                result += "\n";
                result += w.toString();

            }
        }
        return result;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public List<Web> getWeb() {
        return web;
    }

    public void setWeb(List<Web> web) {
        this.web = web;
    }

}