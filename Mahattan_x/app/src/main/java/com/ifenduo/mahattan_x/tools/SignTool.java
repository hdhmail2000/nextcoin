package com.ifenduo.mahattan_x.tools;

import android.text.TextUtils;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class SignTool {
    private static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String[] getSign() {
        String[] strArr = new String[2];
        long time = Calendar.getInstance().getTimeInMillis();
        strArr[0] = String.valueOf(getTimeSign(time));
        strArr[1] = md5(md5(String.valueOf(time / 1000) + "mhtx"));
        return strArr;
    }

    private static String getTimeSign(long time) {
        return Base64.encodeToString(String.valueOf(time / 1000 * 2).getBytes(), Base64.NO_WRAP);
    }

    public static String getSignParam() {
        String[] strArr = getSign();
        String time = strArr[0];
        String sign = strArr[1];
        StringBuilder builder = new StringBuilder("&timestamp=");
        builder.append(time);
        builder.append("&sign=");
        builder.append(sign);
        return builder.toString();
    }
}
