package com.blindingdark.geektrans.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BlindingDark on 2016/8/23 0023.
 */
public class Number {
    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }


    public static boolean isDecimal(String orginal) {
        return isMatch("\\d+\\.?\\d+|\\d", orginal);
    }

    public static boolean isLegalToastTime(String time) {
        if (Number.isDecimal(time)) {
            if (Float.parseFloat(time) != 0) {
                if (Float.parseFloat(time) * 1000 >= 1) {
                    return true;

                }
            }
        }

        return false;

    }

}
