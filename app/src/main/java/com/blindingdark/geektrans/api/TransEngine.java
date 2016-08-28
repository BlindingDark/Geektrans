package com.blindingdark.geektrans.api;

import android.content.SharedPreferences;
import android.os.Handler;


/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public interface TransEngine {

    String getEngineName();
    /**
     * @param req
     * @param handler
     */
    void trans(String req, Handler handler,SharedPreferences preferences);
}
