package com.blindingdark.geektrans.tools;

import android.content.ClipData;
import android.content.ClipboardManager;

/**
 * Created by BlindingDark on 2016/8/25 0025.
 */
public class Clip {
    public static void copyResult(String strResult, ClipboardManager clip) {
        //复制到剪切板
        clip.setPrimaryClip(ClipData.newPlainText("transResult", strResult));

    }
}
