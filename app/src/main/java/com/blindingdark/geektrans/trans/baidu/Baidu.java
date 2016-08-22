package com.blindingdark.geektrans.trans.baidu;

import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;
import com.blindingdark.geektrans.thread.TransReqThread;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduSettings;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Baidu implements TransEngine {
    BaiduSettings baiduSettings;

    public Baidu(BaiduSettings baiduSettings) {
        this.baiduSettings = baiduSettings;
    }

    @Override
    public void trans(String req, Handler handler) {
        new Thread(new TransReqThread(new BaiduTransReq(this.baiduSettings, req),handler)).start();

    }
}
