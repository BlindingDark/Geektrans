package com.blindingdark.geektrans.global;

import com.blindingdark.geektrans.bean.TransEngineInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BlindingDark on 2016/11/28 0028.
 */

public class TransEngineInfoFactory {

    public static Map<String, TransEngineInfo> packageName_transEngineInfo = new HashMap<>();

    // 添加引擎对应关系
    static {
        TransEngineInfo youdao = new TransEngineInfo();
        youdao.setEnginePackageName(StringMainSettings.YOUDAO_TRANS_ENGINE);
        youdao.setEngineName("有道翻译");
        youdao.setEngineSettingsActivityPackageName("com.blindingdark.geektrans.activity.settings.YoudaoSettingsActivity");
        packageName_transEngineInfo.put(youdao.getEnginePackageName(), youdao);

        TransEngineInfo baidu = new TransEngineInfo();
        baidu.setEnginePackageName(StringMainSettings.BAIDU_TRANS_ENGINE);
        baidu.setEngineName("百度翻译");
        baidu.setEngineSettingsActivityPackageName("com.blindingdark.geektrans.activity.settings.BaiduSettingsActivity");
        packageName_transEngineInfo.put(baidu.getEnginePackageName(), baidu);

        TransEngineInfo jinshan = new TransEngineInfo();
        jinshan.setEnginePackageName(StringMainSettings.JINSHAN_TRANS_ENGINE);
        jinshan.setEngineName("金山词霸");
        jinshan.setEngineSettingsActivityPackageName("com.blindingdark.geektrans.activity.settings.JinshanSettingsActivity");
        packageName_transEngineInfo.put(jinshan.getEnginePackageName(), jinshan);

        TransEngineInfo bing = new TransEngineInfo();
        bing.setEnginePackageName(StringMainSettings.BING_TRANS_ENGINE);
        bing.setEngineName("微软翻译");
        bing.setEngineSettingsActivityPackageName("com.blindingdark.geektrans.activity.settings.BingSettingsActivity");
        packageName_transEngineInfo.put(bing.getEnginePackageName(), bing);
    }

    public static TransEngineInfo getTransEngineInfo(String transEnginePackageName) {
        return packageName_transEngineInfo.get(transEnginePackageName);
    }
}
