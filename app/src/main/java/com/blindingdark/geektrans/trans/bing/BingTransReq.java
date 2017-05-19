package com.blindingdark.geektrans.trans.bing;

import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.bing.bean.BingSettings;

/**
 * Created by BlindingDark on 2016/8/28 0028.
 */
public class BingTransReq{
    BingSettings bingSettings;
    Result result = new Result();
    String req;
    Handler handler;

    public BingTransReq(BingSettings bingSettings, String req, Handler handler) {
        this.bingSettings = bingSettings;
        result.setOriginalReq(req);
        this.req = req;
        this.handler = handler;
    }

    public void trans() {
        String translatedText = "啊咧…微软翻译正在施工中……";

        result.setStringResult(translatedText);
        result.setFromEngineName(Bing.ENGINE_NAME);
        result.setWhat(0);

        Message message = new Message();
        message.what = result.getWhat();
        message.obj = result;
        handler.sendMessage(message);
    }
}
