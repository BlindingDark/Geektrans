package com.blindingdark.geektrans.trans.jinshan;

import android.text.TextUtils;

import com.blindingdark.geektrans.tools.MyStringUnits;
import com.blindingdark.geektrans.trans.jinshan.bean.JinshanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class JinshanJSONDeal {

    public static JinshanResult getResults(String JSONResult) {
        JinshanResult jinshanResult = new JinshanResult();
        JSONObject transJSON = null;

        try {
            transJSON = new JSONObject(JSONResult);

            if (!transJSON.isNull("symbols")) {
                JSONArray symbolsJSONArray = transJSON.getJSONArray("symbols");
                for (int i = 0; i < symbolsJSONArray.length(); i++) {
                    JSONObject symbolsJSONObject = symbolsJSONArray.getJSONObject(i);

                    if (null != symbolsJSONObject) {
                        // 2016/8/23 0023 获取发音
                        String[] mp3URLs = {"ph_en_mp3", "ph_am_mp3", "ph_tts_mp3", "symbol_mp3", "ph_other"};

                        for (String mp3 : mp3URLs) {
                            if (!symbolsJSONObject.isNull(mp3)) {
                                String temp = symbolsJSONObject.getString(mp3);
                                if (!TextUtils.isEmpty(temp)) {
                                    jinshanResult.addSoundURL(temp);
                                }
                            }
                        }

                        if (!symbolsJSONObject.isNull("parts")) {
                            JSONArray partsJSONArray = symbolsJSONObject.getJSONArray("parts");

                            for (int j = 0; j < partsJSONArray.length(); j++) {
                                JSONObject part = partsJSONArray.getJSONObject(j);

                                if (!part.isNull("part")) {//英文的情况
                                    String onePart = part.getString("part");
                                    String oneMeans = "";
                                    for (int k = 0; k < part.getJSONArray("means").length(); k++) {
                                        oneMeans += part.getJSONArray("means").getString(k);
                                        oneMeans += "，";
                                    }
                                    oneMeans = MyStringUnits.cutLast(oneMeans);
                                    jinshanResult.addPart(onePart + " " + oneMeans);
                                }

                                if (!part.isNull("part_name")) {//中文
                                    String onePart = part.getString("part_name");
                                    String oneMeans = "";
                                    for (int k = 0; k < part.getJSONArray("means").length(); k++) {
                                        oneMeans += part.getJSONArray("means").getJSONObject(k).getString("word_mean");
                                        oneMeans += "，";
                                    }
                                    oneMeans = MyStringUnits.cutLast(oneMeans);
                                    jinshanResult.addPart(onePart + " " + oneMeans);
                                }

                            }
                        }

                    }


                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
            return jinshanResult;
        }

        return jinshanResult;
    }

}