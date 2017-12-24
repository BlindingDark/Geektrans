package com.blindingdark.geektrans.tools;

import java.util.List;

/**
 * Created by zhuakuang on 17-12-25.
 */

public class EnumUtils {
    public static boolean isEmpty(List list) {
        if (null == list) {
            return true;
        } else {
            return list.isEmpty();
        }
    }
}
