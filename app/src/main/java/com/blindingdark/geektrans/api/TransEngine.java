package com.blindingdark.geektrans.api;

import android.content.SharedPreferences;
import android.os.Handler;


/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public interface TransEngine {

    void setPreferences(SharedPreferences preferences);

    SharedPreferences getPreferences();


    String getEngineName();

    /**
     * @param req
     * @param handler
     */
    void trans(String req, Handler handler, SharedPreferences preferences);

    /**
     *
     * @param req 要翻译的内容
     * @param handler 回调
     */
    void trans(String req, Handler handler);
}
