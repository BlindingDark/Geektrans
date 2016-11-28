package com.blindingdark.geektrans.trans.jinshan;

import com.blindingdark.geektrans.activitys.TransActivity;
import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.tools.PostAndGet;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanResult;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanSettings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class JinshanTransReq implements TransReq {

    String jinshanTransURL = "http://dict-co.iciba.com/api/dictionary.php";
    JinshanSettings jinshanSettings;
    String req;

    Result beanResult = new Result();
    JinshanResult jinshanResult;


    public JinshanTransReq(JinshanSettings jinshanSettings, String req) {
        beanResult.setOriginalReq(req);
        this.jinshanSettings = jinshanSettings;
        this.req = req;
    }

    @Override
    public Result getTrans() {

        try {
            req = URLEncoder.encode(req.toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String query = "w=" + req + "&type=json&key=" + jinshanSettings.getJinshanKey();

        String result = PostAndGet.sendGet(jinshanTransURL, query, 0);
        jinshanResult = JinshanJSONDeal.getResults(result);// JSON解析

        if (jinshanResult.getSoundURLs().isEmpty()) {
            beanResult.setWhat(TransActivity.normalToast);
        } else {
            beanResult.setWhat(TransActivity.haveSoundToast);
            beanResult.setSoundURLs(jinshanResult.getSoundURLs());
        }
        beanResult.setFromEngineName(Jinshan.ENGINE_NAME);
        beanResult.setStringResult(jinshanResult.toString());

        return beanResult;
    }

}
