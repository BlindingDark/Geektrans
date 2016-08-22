package com.blindingdark.geektrans.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by BlindingDark on 2016/8/22 0022.
 * 参考：http://stackoverflow.com/questions/5494447/what-will-be-the-android-java-equivalent-of-md5-function-in-php
 * <p/>
 * 在线测试MD5：http://www.cmd5.com/
 * <p/>
 * http://www.cnblogs.com/bobli/archive/2012/03/26/2418694.html
 * 作者：黎波
 * 博客：http://bobli.cnblogs.com/
 * 日期：2012年3月26日
 */
public class MD5 {
    public static String getMd5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

}
