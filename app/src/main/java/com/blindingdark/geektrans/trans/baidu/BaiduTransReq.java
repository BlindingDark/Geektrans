package com.blindingdark.geektrans.trans.baidu;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.MD5;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduJSONBean;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduSettings;
import com.blindingdark.geektrans.trans.baidu.bean.TransResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BlindingDark on 2016/8/22 0022.
 */
public class BaiduTransReq {
    BaiduSettings baiduSettings;
    String req;
    String sign;
    long salt;
    Handler handler;
    Result beanResult = new Result();

    public BaiduTransReq(BaiduSettings baiduSettings, String req, Handler handler) {
        beanResult.setOriginalReq(req);
        this.baiduSettings = baiduSettings;
        this.req = req;
        this.handler = handler;
        salt = System.currentTimeMillis();
        String str1 = baiduSettings.getBaiduAppId() + req + salt + baiduSettings.getBaiduKey();
        sign = MD5.getMd5(str1);

    }

    public void trans() {
        String baiduTransURL = "http://api.fanyi.baidu.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baiduTransURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BaiduAPI api = retrofit.create(BaiduAPI.class);
        // Retrofit 自动 URL 编码，不需要手动。两次编码就错了。
        api.getResult(req,
                baiduSettings.getBaiduFrom(),
                baiduSettings.getBaiduTo(),
                baiduSettings.getBaiduAppId(),
                salt,
                sign)
                .enqueue(new Callback<BaiduJSONBean>() {
                    @Override
                    public void onResponse(Call<BaiduJSONBean> call, Response<BaiduJSONBean> response) {
                        BaiduJSONBean bean = response.body();
                        String result = "";

                        if (!TextUtils.isEmpty(bean.getErrorMsg())) {
                            switch (bean.getErrorCode()) {
                                case "52000":
                                    result = "成功...?！";
                                    break;
                                case "52001":
                                    result = "请求超时，请稍后重试。";
                                    break;
                                case "52002":
                                    result = "系统错误，请稍后重试。";
                                    break;
                                case "52003":
                                    result = "未授权用户，检查您的appid是否正确。";
                                    break;
                                case "54000":
                                    result = "必填参数为空，检查是否少传参数。";
                                    break;
                                case "58000":
                                    result = "客户端IP非法，检查您填写的IP地址是否正确\n" +
                                            "可修改您填写的服务器IP地址。";
                                    break;
                                case "54001":
                                    result = "签名错误，请检查您的签名生成方法。";
                                    break;
                                case "54003":
                                    result = "访问频率受限，请降低您的调用频率。";
                                    break;
                                case "58001":
                                    result = "译文语言方向不支持，检查译文语言是否在语言列表里。";
                                    break;
                                case "54004":
                                    result = "账户余额不足，前往管理控制台为账户充值。";
                                    break;
                                case "54005":
                                    result = "长query请求频繁，请降低长query的发送频率，3s后再试。";
                                    break;

                                default:
                                    result = "what...?！";
                                    break;
                            }
                        }
                        List<TransResult> transResult = bean.getTransResult();
                        if (null != transResult) {
                            for (TransResult r : transResult) {
                                result += r.getDst();
                            }
                        }

                        beanResult.setStringResult(result).setWhat(0);
                        sendMsg();
                    }

                    @Override
                    public void onFailure(Call<BaiduJSONBean> call, Throwable t) {
                        beanResult.setStringResult("出现了点意外... 翻译失败...").setWhat(0);
                        sendMsg();
                    }
                });

    }

    private void sendMsg() {
        beanResult.setFromEngineName(Baidu.ENGINE_NAME);

        Message message = new Message();
        message.what = beanResult.getWhat();
        message.obj = beanResult;
        handler.sendMessage(message);
    }


}
