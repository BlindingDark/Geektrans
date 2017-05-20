package com.blindingdark.geektrans.trans.youdao.bean;

/**
 * Created by blindingdark on 17-5-20.
 */

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("uk-speech")
    @Expose
    private String ukSpeech;
    @SerializedName("us-phonetic")
    @Expose
    private String usPhonetic;
    @SerializedName("speech")
    @Expose
    private String speech;
    @SerializedName("phonetic")
    @Expose
    private String phonetic;
    @SerializedName("uk-phonetic")
    @Expose
    private String ukPhonetic;
    @SerializedName("us-speech")
    @Expose
    private String usSpeech;
    @SerializedName("explains")
    @Expose
    private List<String> explains = null;

    public List<String> getSoundURLs(){
        Set<String> sounds = new HashSet<>();

        if (!TextUtils.isEmpty(usSpeech)){
            sounds.add(usSpeech);
        }
        if (!TextUtils.isEmpty(ukSpeech)){
            sounds.add(ukSpeech);
        }
        if (!TextUtils.isEmpty(speech)){
            sounds.add(speech);
        }

        return new ArrayList<>(sounds);

    }

    @Override
    public String toString() {
        String ph = "";
        if (!TextUtils.isEmpty(usPhonetic)) {
            ph += ("美[" + usPhonetic + "]");
            ph += " ";
        }
        if (!TextUtils.isEmpty(ukPhonetic)) {
            ph += ("英[" + ukPhonetic + "]");
            ph += " ";
        }

        if (TextUtils.isEmpty(ph)) {
            if (!TextUtils.isEmpty(phonetic)) {
                ph += ("[" + phonetic + "]");
                ph += " ";
            }
        }

        if (!TextUtils.isEmpty(ph)) {
            ph = MyStringUnits.cutLast(ph);
        }

        String ex = "";
        if (null != explains) {
            if (!explains.isEmpty()) {
                for (String s :
                        explains) {
                    ex += s;
                    ex += "\n";
                }
                ex = MyStringUnits.cutLast(ex);
            }
        }

        String spliter = "";
        if (!TextUtils.isEmpty(ph)) {
            if (!TextUtils.isEmpty(ex)) {
                spliter = "\n";
            }
        }

        return ph + spliter + ex;
    }

    public String getUkSpeech() {
        return ukSpeech;
    }

    public void setUkSpeech(String ukSpeech) {
        this.ukSpeech = ukSpeech;
    }

    public String getUsPhonetic() {
        return usPhonetic;
    }

    public void setUsPhonetic(String usPhonetic) {
        this.usPhonetic = usPhonetic;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getUkPhonetic() {
        return ukPhonetic;
    }

    public void setUkPhonetic(String ukPhonetic) {
        this.ukPhonetic = ukPhonetic;
    }

    public String getUsSpeech() {
        return usSpeech;
    }

    public void setUsSpeech(String usSpeech) {
        this.usSpeech = usSpeech;
    }

    public List<String> getExplains() {
        return explains;
    }

    public void setExplains(List<String> explains) {
        this.explains = explains;
    }

}
