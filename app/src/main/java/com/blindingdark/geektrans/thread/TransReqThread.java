package com.blindingdark.geektrans.thread;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blindingdark.geektrans.activitys.StringMainSettings;
import com.blindingdark.geektrans.activitys.TransActivity;
import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.jinshan.Jinshan;
import com.blindingdark.geektrans.trans.jinshan.JinshanJSONDeal;
import com.blindingdark.geektrans.trans.jinshan.JinshanTransReq;
import com.blindingdark.geektrans.trans.jinshan.StringJinshanSettings;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanResult;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class TransReqThread implements Runnable {
    TransReq transReq;
    Result result = new Result();
    Handler handler;

    SharedPreferences preferences;

    public TransReqThread(TransReq req, Handler handler,SharedPreferences preferences) {
        transReq = req;
        this.handler = handler;
        this.preferences = preferences;

    }

    @Override
    public void run() {
        result = transReq.getTrans();
        Message message = new Message();
        // 确定是哪种翻译结果

        if (result.getWhat() == TransActivity.normalToast) {
            Set<String> nowSoundEngine = preferences.getStringSet(StringMainSettings.nowSoundEngineList, new HashSet<String>());
            if (!nowSoundEngine.isEmpty()) {
                // 新的朗读引擎在这里加
                if (nowSoundEngine.contains(new Jinshan().getSoundEngineName())) {
                    String jinshanKey = preferences.getString(StringJinshanSettings.jinshanKey, "");
                    Result soundResult = new JinshanTransReq(new JinshanSettings(jinshanKey), result.getOriginalReq()).getTrans();
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
