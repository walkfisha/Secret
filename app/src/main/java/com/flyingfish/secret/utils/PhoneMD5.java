package com.flyingfish.secret.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by AMOBBS on 2017/3/30.
 */

public class PhoneMD5 {
    public static String md5(String string, String slat) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((string + slat).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
