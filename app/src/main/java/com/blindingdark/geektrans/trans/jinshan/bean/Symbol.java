package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */

import android.text.TextUtils;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Symbol {

    /**
     * 英式发音的音标
     */
    @SerializedName("ph_en")
    @Expose
    private String phEn;

    /**
     * 美式发音的音标
     */
    @SerializedName("ph_am")
    @Expose
    private String phAm;
    /**
     * 其它音标
     */
    @SerializedName("ph_other")
    @Expose
    private String phOther;
    @SerializedName("ph_en_mp3")
    @Expose
    private String phEnMp3;
    @SerializedName("ph_am_mp3")
    @Expose
    private String phAmMp3;
    @SerializedName("ph_tts_mp3")
    @Expose
    private String phTtsMp3;
    @SerializedName("parts")
    @Expose
    private List<Part> parts = null;

    public List<String> getSoundURLs() {
        List<String> soundURLs = new ArrayList<>();
        soundURLs.add(getPhAmMp3());
        soundURLs.add(getPhEnMp3());
        soundURLs.add(getPhTtsMp3());
        return soundURLs;
    }

    @Override
    public String toString() {
        String result = "";
        List<String> temp = new ArrayList<>();

        if (!TextUtils.isEmpty(phAm)) {
            temp.add("美[" + phAm + "]");
        }
        if (!TextUtils.isEmpty(phEn)) {
            temp.add("英[" + phEn + "]");
        }
        if (!TextUtils.isEmpty(phOther)) {
            temp.add("其它[" + phOther + "]");
        }

        for (String s : temp) {
            result += s;
            result += " ";
        }

        if (!TextUtils.isEmpty(result)) {
            result = MyStringUnits.cutLast(result) + "\n";
        }

        if (null != parts) {
            for (Part part : parts) {
                result += part.toString() + "\n";
            }
            result = MyStringUnits.cutLast(result);
        }

        return result;
    }

    public String getPhEn() {
        return phEn;
    }

    public void setPhEn(String phEn) {
        this.phEn = phEn;
    }

    public String getPhAm() {
        return phAm;
    }

    public void setPhAm(String phAm) {
        this.phAm = phAm;
    }

    public String getPhOther() {
        return phOther;
    }

    public void setPhOther(String phOther) {
        this.phOther = phOther;
    }

    public String getPhEnMp3() {
        return phEnMp3;
    }

    public void setPhEnMp3(String phEnMp3) {
        this.phEnMp3 = phEnMp3;
    }

    public String getPhAmMp3() {
        return phAmMp3;
    }

    public void setPhAmMp3(String phAmMp3) {
        this.phAmMp3 = phAmMp3;
    }

    public String getPhTtsMp3() {
        return phTtsMp3;
    }

    public void setPhTtsMp3(String phTtsMp3) {
        this.phTtsMp3 = phTtsMp3;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

}