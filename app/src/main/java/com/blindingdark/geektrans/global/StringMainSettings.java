package com.blindingdark.geektrans.global;

import com.blindingdark.geektrans.trans.baidu.Baidu;
import com.blindingdark.geektrans.trans.bing.Bing;
import com.blindingdark.geektrans.trans.jinshan.Jinshan;
import com.blindingdark.geektrans.trans.youdao.Youdao;
/**
 * Created by BlindingDark on 2016/8/22 0022.
 */
public class StringMainSettings {
    public static final String NOW_TRANS_ENGINE = "NOW_TRANS_ENGINE";
    public static final String NOW_ENGINE_LIST = "NOW_ENGINE_LIST";
    public static final String NOW_SOUND_ENGINE_LIST = "NOW_SOUND_ENGINE_LIST";
    public static final String DEFAULT_SOUND_ENGINE = "DEFAULT_SOUND_ENGINE";
    public static final String YOUDAO_TRANS_ENGINE = Youdao.ENGINE_PACKAGE_NAME;
    public static final String BAIDU_TRANS_ENGINE = Baidu.ENGINE_PACKAGE_NAME;
    public static final String JINSHAN_TRANS_ENGINE = Jinshan.ENGINE_PACKAGE_NAME;
    public static final String BING_TRANS_ENGINE = Bing.ENGINE_PACKAGE_NAME;
    public static final String TOAST_TIME = ".TOAST_TIME";
    public static final String DEFAULT_TOAST_TIME = "DEFAULT_TOAST_TIME";
    public static final String IS_AUTO_COPY_OPEN = "IS_AUTO_COPY_OPEN";

}
