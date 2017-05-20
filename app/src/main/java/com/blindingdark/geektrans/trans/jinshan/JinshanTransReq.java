package com.blindingdark.geektrans.trans.jinshan;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.blindingdark.geektrans.activity.TransActivity;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.MyStringUnits;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanJSONBean;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanJSONBeanZh;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;
import com.blindingdark.geektrans.trans.jinshan.bean.Symbol;
import com.blindingdark.geektrans.trans.jinshan.bean.SymbolZh;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class JinshanTransReq {

    String jinshanTransURL = "http://dict-co.iciba.com/";
    JinshanSettings jinshanSettings;
    String req;

    Result beanResult = new Result();
    Handler handler;

    public JinshanTransReq(JinshanSettings jinshanSettings, String req, Handler handler) {
        beanResult.setOriginalReq(req);
        this.jinshanSettings = jinshanSettings;
        this.req = req.toLowerCase();
        this.handler = handler;
    }

    public void trans() {
        // TODO: 17-5-11 英文如果有过去式，还需要单独检测
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(jinshanTransURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JinshanAPI api = retrofit.create(JinshanAPI.class);
        // if req is eng
        if (MyStringUnits.isASCII(req)) {
            api.getResult(req, jinshanSettings.getJinshanKey())
                    .enqueue(new Callback<JinshanJSONBean>() {
                        @Override
                        public void onResponse(Call<JinshanJSONBean> call,
                                               Response<JinshanJSONBean> response) {
                            JinshanJSONBean res = response.body();
                            // 内容
                            List<Symbol> symbols = res.getSymbols();
                            List<String> sounds = new ArrayList<>();

                            for (Symbol symbol : symbols) {
                                List<String> soundsTemp = symbol.getSoundURLs();
                                for (String sound : soundsTemp) {
                                    if (!TextUtils.isEmpty(sound)) {
                                        sounds.add(sound);
                                    }
                                }
                            }
                            callback(sounds, res.toString());
                        }

                        @Override
                        public void onFailure(Call<JinshanJSONBean> call, Throwable t) {
                            Log.d("retrofitOnFailure", t.toString());
                            callbackOnFailure();
                        }
                    });
        } else {
            api.getZhResult(req, jinshanSettings.getJinshanKey())
                    .enqueue(new Callback<JinshanJSONBeanZh>() {
                        @Override
                        public void onResponse(Call<JinshanJSONBeanZh> call,
                                               Response<JinshanJSONBeanZh> response) {
                            JinshanJSONBeanZh res = response.body();
                            // 内容
                            List<SymbolZh> symbols = res.getSymbols();
                            List<String> sounds = new ArrayList<>();

                            for (SymbolZh symbol : symbols) {
                                List<String> soundsTemp = symbol.getSoundURLs();
                                for (String sound : soundsTemp) {
                                    if (!TextUtils.isEmpty(sound)) {
                                        sounds.add(sound);
                                    }
                                }
                            }
                            callback(sounds, res.toString());
                        }

                        @Override
                        public void onFailure(Call<JinshanJSONBeanZh> call, Throwable t) {
                            Log.d("retrofitOnFailure", t.toString());
                            callbackOnFailure();
                        }
                    });
        }

    }

    private void callback(List<String> sounds, String stringResult) {
        if (sounds.isEmpty()) {
            beanResult.setWhat(TransActivity.normalToast);
        } else {
            beanResult.setWhat(TransActivity.haveSoundToast);
            beanResult.setSoundURLs(sounds);
        }

        beanResult.setFromEngineName(Jinshan.ENGINE_NAME);

        if (TextUtils.isEmpty(stringResult)) {
            stringResult = "金山词霸未得到翻译结果";
        }
        beanResult.setStringResult(stringResult);

        sendMsg();
    }

    private void callbackOnFailure() {
        beanResult.setWhat(TransActivity.normalToast);
        beanResult.setFromEngineName(Jinshan.ENGINE_NAME);
        beanResult.setStringResult("出现了点意外... 翻译失败...");

        sendMsg();
    }

    private void sendMsg() {
        Message message = new Message();
        message.what = beanResult.getWhat();
        message.obj = beanResult;
        handler.sendMessage(message);
    }


}
