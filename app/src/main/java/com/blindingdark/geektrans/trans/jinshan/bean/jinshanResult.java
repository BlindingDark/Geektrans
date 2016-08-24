package com.blindingdark.geektrans.trans.jinshan.bean;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class JinshanResult {


    ArrayList<String> parts = new ArrayList<>();
    ArrayList<String> soundURLs = new ArrayList<>();

    public ArrayList<String> getSoundURLs() {
        return soundURLs;
    }

    public void addSoundURL(String soundURL) {
        this.soundURLs.add(soundURL);
    }

    public ArrayList<String> getParts() {
        return parts;
    }

    public void addPart(String part) {
        parts.add(part);
    }

    @Override
    public String toString() {
        String temp = "";
        for (String s : this.getParts()) {
            temp += s;
            temp += "\n";
        }
        if (!TextUtils.isEmpty(temp)) {
            temp = temp.substring(0, temp.length() - 1);
        }

        return temp;
    }
}
