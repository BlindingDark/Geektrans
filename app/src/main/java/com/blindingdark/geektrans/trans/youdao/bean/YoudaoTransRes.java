package com.blindingdark.geektrans.trans.youdao.bean;

import android.text.TextUtils;

import com.blindingdark.geektrans.tools.EnumUtils;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.WebExplain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhuakuang on 17-12-24.
 */

public class YoudaoTransRes {
    private Translate translate;

    public YoudaoTransRes(Translate translate) {
        this.translate = translate;
    }

    public List<String> getSoundURLs() {
        Set<String> sounds = new HashSet<>();
        try {
            Field ukSpeakUrlField = Translate.class.getDeclaredField("UKSpeakUrl");
            Field usSpeakUrlField = Translate.class.getDeclaredField("USSpeakUrl");
            Field speakUrlField = Translate.class.getDeclaredField("speakUrl");

            ukSpeakUrlField.setAccessible(true);
            usSpeakUrlField.setAccessible(true);
            speakUrlField.setAccessible(true);

            String usSpeak = (String) usSpeakUrlField.get(translate);
            String ukSpeak = (String) ukSpeakUrlField.get(translate);
            String speak = (String) speakUrlField.get(translate);

            if (!TextUtils.isEmpty(usSpeak)){
                sounds.add(usSpeak);
            }
            if (!TextUtils.isEmpty(ukSpeak)){
                sounds.add(ukSpeak);
            }
            if (!TextUtils.isEmpty(speak)){
                sounds.add(speak);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            return new ArrayList<>(sounds);
        }
    }

    @Deprecated
    @Override
    public String toString() {
        List<String> results = new ArrayList<>();

        // 翻译
        List<String> translations = translate.getTranslations();
        if (!EnumUtils.isEmpty(translations)) {
            results.add(TextUtils.join("；", translations));
        }

        List<String> explainsBlock = new ArrayList<>();
        // 原始请求
        explainsBlock.add(translate.getQuery());

        // 音标 start
        String ukPhonetic = translate.getUkPhonetic();
        String usPhonetic = translate.getUsPhonetic();
        String phonetic = translate.getPhonetic();

        if (!TextUtils.isEmpty(usPhonetic)) {
            usPhonetic = "美[" + usPhonetic + "]";
        }
        if (!TextUtils.isEmpty(ukPhonetic)) {
            ukPhonetic = ("英[" + ukPhonetic + "]");
        }
        if (!TextUtils.isEmpty(phonetic)) {
            phonetic = ("[" + phonetic + "]");
        }

        List<String> phs = new ArrayList<>();
        phs.add(usPhonetic == null ? "" : usPhonetic);
        phs.add(ukPhonetic == null ? "" : ukPhonetic);
        if (usPhonetic == null || ukPhonetic == null) {
            phs.add(phonetic == null ? "" : phonetic);
        }

        if (!EnumUtils.isEmpty(phs)) {
            explainsBlock.add(TextUtils.join(" ", phs));
        }
        // 音标 end

        // 单词的详细解释
        List<String> explains = translate.getExplains();
        if (!EnumUtils.isEmpty(explains)) {
            explainsBlock.add(TextUtils.join("\n", explains));
        }

        results.add(TextUtils.join("\n", explainsBlock));

        // web explain start
        List<WebExplain> webExplains = translate.getWebExplains();
        if (!EnumUtils.isEmpty(webExplains)) {
            List<String> webExp = new ArrayList<>();

            for (WebExplain webExplain : webExplains) {
                webExp.add(formatWebExplain(webExplain));
            }

            results.add(TextUtils.join("\n", webExp));
        }
        // web explain end

        return TextUtils.join("\n" + getDiv() + "\n", results);
    }

    public String toString(String div) {
        this.setDiv(div);
        return this.toString();
    }

    private String div = "------";

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    private String formatWebExplain(WebExplain webExplain) {
        if (null == webExplain) {
            return "";
        }

        String key = webExplain.getKey();
        String means = TextUtils.join("；", webExplain.getMeans());

        return key + "\n" + means;
    }
}
