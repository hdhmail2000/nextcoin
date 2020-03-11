package com.qkwl.common.util;

import com.qkwl.common.exceptions.BCException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

public class PassWordTest {
    public static String MD5(String content) throws BCException {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BCException();
        }
        sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
        String retString = baseEncoder.encode(md5.digest(content.getBytes()));
        return retString;
    }

    public static String base64Change(String in) {
        return new String(Base64.getEncoder().encode(in.getBytes()));
    }

    public static void main(String[] args) {
        try {
            System.out.println(base64Change("kkk"));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String pass = "521wuyu";
            byte[] utf8 = pass.getBytes("UTF-8");
            pass = new String(utf8, "UTF-8");
            md5.update(pass.getBytes());
            byte[] data = md5.digest();

            String res = "";
            for (int i = 0; i < data.length; i++) {
                int temp = data[i] & 0xFF;
                if (temp <= 0XF) {
                    res += "0";
                }
                res += Integer.toHexString(temp);
            }
            System.out.println(res);
            String md5code = new BigInteger(1, data).toString(16);
            //String bs = new String(data);
            System.out.println(md5code);
            sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
            String retString = baseEncoder.encode(md5code.getBytes());
            System.out.println(retString);

            String retString2 = baseEncoder.encode(data);
            System.out.println(retString2);
            //String passmd5Base = PassWordTest.MD5(pass);
            //System.out.println(passmd5Base);
            //System.out.println(md5.digest(pass.getBytes()));
            //System.out.println(passmd5);
            //String passmd5Base = PassWordTest.MD5(md5code);


            //System.out.println(passmd5Base);
        }catch (Exception e) {
            System.out.println("error");
        }
    }
}
