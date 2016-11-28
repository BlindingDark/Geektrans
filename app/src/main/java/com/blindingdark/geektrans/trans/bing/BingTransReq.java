package com.blindingdark.geektrans.trans.bing;

import com.blindingdark.geektrans.api.TransReq;
import com.blindingdark.geektrans.bean.Result;
import com.blindingdark.geektrans.trans.bing.bean.BingSettings;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by BlindingDark on 2016/8/28 0028.
 */
public class BingTransReq implements TransReq {
    BingSettings bingSettings;
    Result result = new Result();
    String req;

    public BingTransReq(BingSettings bingSettings, String req) {
        this.bingSettings = bingSettings;
        result.setOriginalReq(req);
        this.req = req;
    }

    @Override
    public Result getTrans() {
        Translate.setClientId(bingSettings.getBingAppId());
        Translate.setClientSecret(bingSettings.getBingKey());
        String translatedText = req;
        try {
            translatedText = Translate.execute(req, Language.fromString(bingSettings.getBingFrom()), Language.fromString(bingSettings.getBingTo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setStringResult(translatedText);
        result.setFromEngineName(Bing.ENGINE_NAME);
        result.setWhat(0);
        return result;
    }
}
