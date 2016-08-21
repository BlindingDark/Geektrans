package com.blindingdark.geektrans.trans;

import android.os.Handler;

import com.blindingdark.geektrans.api.TransEngine;

/**
 * Created by BlindingDark on 2016/8/21 0021.
 */
public class Translator {
    public static void trans(String req, TransEngine transEngine, Handler handler){
        transEngine.trans(req,handler);
    }
}
