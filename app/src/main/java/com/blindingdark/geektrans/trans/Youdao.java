package com.blindingdark.geektrans.trans;

import android.os.Handler;
import android.os.Message;

import com.blindingdark.geektrans.GetTransTextActivity;
import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.tools.PostAndGet;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class Youdao implements TransReq {
    String query;

    public Youdao(String query) {
        this.query = query;
    }

    @Override
    public String getTrans() {
        String youdaoAPI = "http://fanyi.youdao.com/openapi.do";
        String youdaoAPIArgs = "keyfrom=blindingdark&key=1309205149&type=data&doctype=json&version=1.1&q=" + query;

        String result;
        result = PostAndGet.sendGet(youdaoAPI, youdaoAPIArgs, 0);
        return result;
    }

}
