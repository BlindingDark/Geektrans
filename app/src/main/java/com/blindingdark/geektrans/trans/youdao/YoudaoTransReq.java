package com.blindingdark.geektrans.trans.youdao;

import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.PostAndGet;
import com.blindingdark.geektrans.trans.youdao.bean.ReadableTransResults;
import com.blindingdark.geektrans.trans.youdao.bean.YoudaoSettings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class YoudaoTransReq implements TransReq {
    String query;
    YoudaoSettings youdaoSettings;
    Result beanResult = new Result();

    public YoudaoTransReq(YoudaoSettings settings, String query) {
        beanResult.setOriginalReq(query);
        this.youdaoSettings = settings;
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            this.query = encodedQuery;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.query = query;
        }
    }

    @Override
    public Result getTrans() {
        if (query.length() > 200) {
            beanResult.setWhat(0);
            beanResult.setStringResult("有道翻译单次查询长度不能大于 200");
            return beanResult;
        }

        String youdaoAPI = "http://fanyi.youdao.com/openapi.do";
        String youdaoAPIArgs = "keyfrom=" + youdaoSettings.getKeyfrom() + "&key=" + youdaoSettings.getKey() + "&type=data&doctype=json&version=1.2&q=" + query;

        String result;
        result = PostAndGet.sendGet(youdaoAPI, youdaoAPIArgs, 0);

        ReadableTransResults readableResults = YoudaoJSONDeal.getResults(result);// 这里指定解析JSON的类
        readableResults.setYoudaoSettings(youdaoSettings);

        if (readableResults.getSoundURLs().isEmpty()) {
            beanResult.setWhat(0);
        } else {
            beanResult.setSoundURLs(readableResults.getSoundURLs());
            beanResult.setWhat(1);
        }

        beanResult.setFromEngineName(Youdao.ENGINE_NAME);
        beanResult.setStringResult(readableResults.toString());
        return beanResult;
    }


}
