package com.blindingdark.geektrans.global;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by BlindingDark on 2016/11/30 0030.
 */

public class SeqMainSettings {

    public static Set<String> getDefaultEngines() {
        Set<String> DEFAULT_ENGINES = new HashSet<>();
        DEFAULT_ENGINES.add(StringMainSettings.YOUDAO_TRANS_ENGINE);

        return DEFAULT_ENGINES;
    }

    public static Set<String> getAllEngines() {
        Set<String> ALL_ENGINES = new HashSet<>();
        ALL_ENGINES.add(StringMainSettings.YOUDAO_TRANS_ENGINE);
        ALL_ENGINES.add(StringMainSettings.JINSHAN_TRANS_ENGINE);
        ALL_ENGINES.add(StringMainSettings.BAIDU_TRANS_ENGINE);
        ALL_ENGINES.add(StringMainSettings.BING_TRANS_ENGINE);

        return ALL_ENGINES;
    }

}
