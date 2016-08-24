package com.blindingdark.geektrans.api;

import com.blindingdark.geektrans.bean.Result;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public interface TransReq {


    /**
     * 由于 android 限制，请在新线程中请求网络。
     * @return
     */


    Result getTrans();
}
