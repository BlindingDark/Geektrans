package com.blindingdark.geektrans.trans.youdao;

import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.activity.TransActivity;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoJSONBean;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class YoudaoTransReq {
    String query;
    YoudaoSettings youdaoSettings;
    Result beanResult = new Result();
    Handler handler;

    public YoudaoTransReq(YoudaoSettings settings, String query, Handler handler) {
        beanResult.setOriginalReq(query);
        this.youdaoSettings = settings;
        this.handler = handler;
        this.query = query;
    }

    public void trans() {
        String youdaoTransURL = "http://fanyi.youdao.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(youdaoTransURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YoudaoAPI api = retrofit.create(YoudaoAPI.class);
        api.getResult(query,
                youdaoSettings.getKey(),
                youdaoSettings.getKeyfrom())
                .enqueue(new Callback<YoudaoJSONBean>() {
                    @Override
                    public void onResponse(Call<YoudaoJSONBean> call, Response<YoudaoJSONBean> response) {
                        YoudaoJSONBean res = response.body();
                        String errorCode = res.getErrorCode().toString();
                        switch (errorCode) {
                            case "0": {
                                List<String> sounds = res.getSoundURLs();
                                if (sounds.isEmpty()) {
                                    beanResult.setWhat(TransActivity.normalToast);
                                } else {
                                    beanResult.setWhat(TransActivity.haveSoundToast);
                                    beanResult.setSoundURLs(sounds);
                                }
                                beanResult.setStringResult(res.toString(youdaoSettings.getDivisionLine()));
                                break;
                            }
                            case "20":
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("要翻译的文本过长");
                                break;
                            case "30":
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("无法进行有效的翻译");
                                break;
                            case "40":
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("不支持的语言类型");
                                break;
                            case "50":
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("无效的key");
                                break;
                            case "60":
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("无词典结果");
                                break;
                            default:
                                beanResult.setWhat(TransActivity.normalToast);
                                beanResult.setStringResult("");
                                break;
                        }
                        sendMsg();
                    }

                    @Override
                    public void onFailure(Call<YoudaoJSONBean> call, Throwable t) {
                        beanResult.setWhat(TransActivity.normalToast);
                        beanResult.setStringResult("出现了点意外... 翻译失败...");

                        sendMsg();
                    }
                });
    }

    private void sendMsg() {
        beanResult.setFromEngineName(Youdao.ENGINE_NAME);
        Message message = new Message();
        message.what = beanResult.getWhat();
        message.obj = beanResult;
        handler.sendMessage(message);
    }

}
