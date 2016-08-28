package com.blindingdark.geektrans.thread;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.activitys.StringMainSettings;
import com.blindingdark.geektrans.activitys.TransActivity;
import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.jinshan.Jinshan;
import com.blindingdark.geektrans.trans.jinshan.JinshanTransReq;
import com.blindingdark.geektrans.trans.jinshan.StringJinshanSettings;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;
import com.blindingdark.geektrans.trans.youdao.Youdao;
import com.blindingdark.geektrans.trans.youdao.YoudaoTransReq;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 * <p/>
 * 这个东西有添加声音引擎的功能。。。属于当初设计的时候没设计好。
 */
public class TransReqThread implements Runnable {
    TransReq transReq;
    Result result = new Result();
    Handler handler;

    SharedPreferences preferences;

    public TransReqThread(TransReq req, Handler handler, SharedPreferences preferences) {
        transReq = req;
        this.handler = handler;
        this.preferences = preferences;

    }

    @Override
    public void run() {
        result = transReq.getTrans();
        Message message = new Message();


        String nowSoundEngine = preferences.getString(StringMainSettings.defaultSoundEngine, "");
        if (!"".equals(nowSoundEngine)) {
            // 新的朗读引擎在这里加
            if (nowSoundEngine.equals(Jinshan.engineName)) {
                if (!result.getFromEngineName().equals(Jinshan.engineName)) {
                    Result soundResult = new JinshanTransReq(new JinshanSettings(preferences), result.getOriginalReq()).getTrans();
                    if (!soundResult.getSoundURLs().isEmpty()) {
                        result.setSoundURLs(soundResult.getSoundURLs());
                        result.setWhat(TransActivity.haveSoundToast);
                    }
                }
            }
            if (nowSoundEngine.equals(Youdao.engineName)) {
                if (!result.getFromEngineName().equals(Youdao.engineName)) {
                    Result soundResult = new YoudaoTransReq(new YoudaoSettings(preferences), result.getOriginalReq()).getTrans();
                    if (!soundResult.getSoundURLs().isEmpty()) {
                        result.setSoundURLs(soundResult.getSoundURLs());
                        result.setWhat(TransActivity.haveSoundToast);
                    }
                }
            }

        }

        message.what = result.getWhat();
        message.obj = result;

        this.handler.sendMessage(message);
    }
}
