package com.ifenduo.mahattan_x.tools;

public class FromatTool {
    /**
     *      * 手机号码格式化微182****5716
     *      *
     *      * @param phone
     *      * @return
     *     
     */
    public static String phoneNumberFormat(String phone) {
        String reStr = phone.substring(phone.length() - 4, phone.length());
        String preStr = phone.substring(0, phone.length() - 8);
        StringBuilder sb = new StringBuilder();
        sb.append(preStr).append("****").append(reStr);
        return sb.toString();
    }
}
