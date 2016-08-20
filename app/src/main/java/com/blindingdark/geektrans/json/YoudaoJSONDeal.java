package com.blindingdark.geektrans.json;

import com.blindingdark.geektrans.bean.ReadableTransResults;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BlindingDark on 2016/8/20 0020.
 */
public class YoudaoJSONDeal {
    public static ReadableTransResults getResults(String JSONResults) {
        ReadableTransResults readableTransResults = new ReadableTransResults();
        String translation = "";
        String basicExplains = "";
        String web = "";

        try {
            JSONObject transJSON = new JSONObject(JSONResults);

            switch (transJSON.getInt("errorCode")) {

                case 0:

                    if (!transJSON.isNull("translation")) {
                        for (int i = 0; i < transJSON.getJSONArray("translation").length(); i++) {
                            translation = translation + transJSON.getJSONArray("translation").getString(i) + "\n";
                        }
                    }

                    if (!transJSON.isNull("basic")) {
                        for (int i = 0; i < transJSON.getJSONObject("basic").getJSONArray("explains").length(); i++) {
                            basicExplains = basicExplains + transJSON.getJSONObject("basic").getJSONArray("explains").getString(i) + "\n";
                        }
                    }

                    if (!transJSON.isNull("web")) {
                        for (int i = 1; i < transJSON.getJSONArray("web").length(); i++) {
                            for (int j = 0; j < transJSON.getJSONArray("web").getJSONObject(i).getJSONArray("value").length(); j++)
                                web = web + transJSON.getJSONArray("web").getJSONObject(i).optString("key") + " : " + transJSON.getJSONArray("web").getJSONObject(i).getJSONArray("value").getString(j) + "\n";
                        }
                    }
                    break;
                case 20:
                    break;
                case 30:
                    break;
                case 40:
                    break;
                case 50:
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!translation.equals("")) {
            translation = translation.substring(0, translation.length() - 1);
        }
        if (!basicExplains.equals("")) {
            basicExplains = basicExplains.substring(0, basicExplains.length() - 1);
        }
        if (!web.equals("")) {
            web = web.substring(0, web.length() - 1);
        }

        readableTransResults.setBasic(basicExplains);
        readableTransResults.setTranslation(translation);
        readableTransResults.setWeb(web);
        return readableTransResults;
    }
}
