package com.blindingdark.geektrans.trans.jinshan;

import android.content.SharedPreferences;
import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.thread.TransReqThread;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;


/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class Jinshan implements TransEngine {

    JinshanSettings jinshanSettings;
    public final static String engineName = "jinshan";

    public Jinshan(JinshanSettings jinshanSettings) {
        this.jinshanSettings = jinshanSettings;
    }

    public Jinshan() {
    }

    @Override
    public void trans(String req, Handler handler,SharedPreferences preferences) {
        new Thread(new TransReqThread(new JinshanTransReq(jinshanSettings, req), handler,preferences)).start();

    }

    @Override
    public String getEngineName() {
        return engineName;
    }
}
