package com.blindingdark.geektrans.thread;

import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.api.TransReq;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class TransReqThread implements Runnable {
    TransReq transReq;
    String strResult = "error";
    Handler handler;


    public TransReqThread(TransReq req,Handler handler) {
        transReq = req;
        this.handler = handler;
    }

    @Override
    public void run() {
        strResult = transReq.getTrans();

        Message message = new Message();
        message.what = 0;
        message.obj = strResult;

        this.handler.sendMessage(message);
    }
}
