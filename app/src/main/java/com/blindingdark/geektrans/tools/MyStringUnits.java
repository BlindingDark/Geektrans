package com.blindingdark.geektrans.tools;

import android.text.TextUtils;

/**
 * Created by BlindingDark on 2016/8/24 0024.
 */
public class MyStringUnits {
    public static String cutLast(String string) {

        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }
}
