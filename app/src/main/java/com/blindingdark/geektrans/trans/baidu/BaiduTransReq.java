package com.blindingdark.geektrans.trans.baidu;

import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.MD5;
import com.blindingdark.geektrans.tools.PostAndGet;
import com.blindingdark.geektrans.trans.baidu.bean.BaiduSettings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by BlindingDark on 2016/8/22 0022.
 */
public class BaiduTransReq implements TransReq {
    BaiduSettings baiduSettings;
    String req;
    String sign;
    long salt;
    Result beanResult = new Result();

    public BaiduTransReq(BaiduSettings baiduSettings, String req) {
        beanResult.setOriginalReq(req);
        this.baiduSettings = baiduSettings;
        this.req = req;
        salt = System.currentTimeMillis();
        String str1 = baiduSettings.getBaiduAppId() + req + salt + baiduSettings.getBaiduKey();
        sign = MD5.getMd5(str1);

    }

    @Override
    public Result getTrans() {
        String baiduTransURL = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        try {
            req = URLEncoder.encode(req, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String query = "q=" + req + "&from=" + baiduSettings.getBaiduFrom() + "&to=" + baiduSettings.getBaiduTo() + "&appid=" + baiduSettings.getBaiduAppId() + "&salt=" + salt + "&sign=" + sign;

        String result;
        result = PostAndGet.sendGet(baiduTransURL, query, 0);

        String readableResults = BaiduJSONDeal.getResults(result);// 这里指定解析JSON的类
        beanResult.setStringResult(readableResults).setWhat(0);

        return beanResult;

    }


}
