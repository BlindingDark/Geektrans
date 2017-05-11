package com.blindingdark.geektrans.trans.jinshan.bean;

/**
 * Created by blindingdark on 17-5-10.
 */

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SymbolZh {
    /**
     * 拼音
     */
    @SerializedName("word_symbol")
    @Expose
    private String wordSymbol;
    @SerializedName("symbol_mp3")
    @Expose
    private String symbolMp3;
    @SerializedName("parts")
    @Expose
    private List<PartZh> parts = null;
    @SerializedName("ph_am_mp3")
    @Expose
    private String phAmMp3;
    @SerializedName("ph_en_mp3")
    @Expose
    private String phEnMp3;
    @SerializedName("ph_tts_mp3")
    @Expose
    private String phTtsMp3;
    @SerializedName("ph_other")
    @Expose
    private String phOther;

    @Override
    public String toString() {
        String result = "";
        if (!TextUtils.isEmpty(wordSymbol)) {
            result += "[" + wordSymbol + "]" + "\n";
        }

        if (null != parts) {
            for (PartZh part : parts) {
                if (!TextUtils.isEmpty(part.toString())){
                    result += part.toString() + "\n";
                }
            }
            result = MyStringUnits.cutLast(result);
        }

        return result;
    }

    public List<String> getSoundURLs() {
        List<String> soundURLs = new ArrayList<>();
        soundURLs.add(getPhAmMp3());
        soundURLs.add(getPhEnMp3());
        soundURLs.add(getPhTtsMp3());
        soundURLs.add(getSymbolMp3());
        return soundURLs;
    }

    public String getWordSymbol() {
        return wordSymbol;
    }

    public void setWordSymbol(String wordSymbol) {
        this.wordSymbol = wordSymbol;
    }

    public String getSymbolMp3() {
        return symbolMp3;
    }

    public void setSymbolMp3(String symbolMp3) {
        this.symbolMp3 = symbolMp3;
    }

    public List<PartZh> getParts() {
        return parts;
    }

    public void setParts(List<PartZh> parts) {
        this.parts = parts;
    }

    public String getPhAmMp3() {
        return phAmMp3;
    }

    public void setPhAmMp3(String phAmMp3) {
        this.phAmMp3 = phAmMp3;
    }

    public String getPhEnMp3() {
        return phEnMp3;
    }

    public void setPhEnMp3(String phEnMp3) {
        this.phEnMp3 = phEnMp3;
    }

    public String getPhTtsMp3() {
        return phTtsMp3;
    }

    public void setPhTtsMp3(String phTtsMp3) {
        this.phTtsMp3 = phTtsMp3;
    }

    public String getPhOther() {
        return phOther;
    }

    public void setPhOther(String phOther) {
        this.phOther = phOther;
    }
}
