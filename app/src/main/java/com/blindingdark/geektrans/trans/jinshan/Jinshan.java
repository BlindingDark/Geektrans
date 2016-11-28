package com.blindingdark.geektrans.trans.jinshan;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;


/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class Jinshan implements TransEngine {

    JinshanSettings jinshanSettings;
    public final static String ENGINE_NAME = "com.blindingdark.geektrans.trans.jinshan.Jinshan";
    public final static String ENGINE_PACKAGE_NAME = ENGINE_NAME;
    private SharedPreferences peferences;

    @Override
    public void trans(final String req, final Handler handler, SharedPreferences preferences) {
        //new Thread(new TransReqThread(new JinshanTransReq(jinshanSettings, req), handler,preferences)).start();
        this.setPreferences(preferences);
        this.trans(req, handler);
    }

    @Override
    public void trans(final String req, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = new JinshanTransReq(jinshanSettings, req).getTrans();
                Message message = new Message();
                message.what = result.getWhat();
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void setPreferences(SharedPreferences preferences) {
        this.peferences = preferences;
        this.jinshanSettings = new JinshanSettings(preferences);

    }

    @Override
    public SharedPreferences getPreferences() {
        return this.peferences;
    }

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }
}
