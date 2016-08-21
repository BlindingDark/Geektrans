package com.blindingdark.geektrans.api;

import android.os.Handler;


/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public interface TransEngine {

    /**
     * 由于 android 限制，请在 TransReqThread 中请求翻译数据。
     * @param req
     * @param handler
     */
    void trans(String req, Handler handler);
}
