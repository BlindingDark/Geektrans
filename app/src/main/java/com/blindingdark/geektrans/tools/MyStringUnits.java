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

    /**
     * 多个 *空白字符* 替换成一个空格，并去掉首尾空格
     *
     * @param str
     * @return
     */
    public static String filterBlankSpace(String str) {
        if (null == str) {
            return "";
        } else {
            return str.trim().replaceAll("\\s+", " ");
        }
    }

    public static boolean isASCII(String str) {
        char tch;
        int tint;
        for (int i = 0; i < str.length(); i++) {
            tch = str.charAt(i);
            tint = (int) tch;
            if (tint > 255) {
                return false;
            }
        }
        return true;
    }
}
