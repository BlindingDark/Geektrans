package com.blindingdark.geektrans.trans.youdao;

import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.thread.TransReqThread;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Youdao implements TransEngine {

    @Override
    public void trans(String req, Handler handler) {
        new Thread(new TransReqThread(new YoudaoTransReq(req),handler)).start();
    }
}
