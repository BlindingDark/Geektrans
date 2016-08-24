package com.blindingdark.geektrans.trans.baidu;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BlindingDark on 2016/8/22 0022.
 */
public class BaiduJSONDeal {

    public static String getResults(String JSONResult) {


        String result = "";
        JSONObject transJSON = null;

        try {
            transJSON = new JSONObject(JSONResult);

            if (!transJSON.isNull("error_code")) {
                switch (transJSON.getString("error_code")) {
                    case "52003":
                        result = "未授权用户，检查您的appid是否正确";
                        break;

                    default:
                        return result;
                }
            }

            if (!transJSON.isNull("trans_result")) {
                JSONObject ob = transJSON.getJSONArray("trans_result").getJSONObject(0);
                return ob.getString("dst");
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
