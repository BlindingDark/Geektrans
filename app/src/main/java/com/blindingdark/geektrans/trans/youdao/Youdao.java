package com.blindingdark.geektrans.trans.youdao;

import android.content.SharedPreferences;
import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.thread.TransReqThread;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Youdao implements TransEngine {
    YoudaoSettings settings;

    public Youdao(YoudaoSettings settings) {
        this.settings = settings;
    }

    @Override
    public void trans(String req, Handler handler,SharedPreferences preferences) {
        new Thread(new TransReqThread(new YoudaoTransReq(settings,req),handler,preferences)).start();
    }
}
