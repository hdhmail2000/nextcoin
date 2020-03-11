package com.ifenduo.mahattan_x.api;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ll on 2018/4/3.
 */

public class ApiSign {

    /**
     * 签名算法
     *
     * @param appKey
     * @param appSecretKey
     * @param method
     * @param host
     * @param uri
     * @param params
     */
    public static void createSignature(String appKey, String appSecretKey, String method, String host, String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(host.toLowerCase()).append('\n') // Host
                .append(uri).append('\n'); // /path
        if (params == null) {
            params = new HashMap<>();
        }
        params.remove("Signature");
        params.put("AccessKeyId", appKey);
        params.put("SignatureVersion", "2");
        params.put("SignatureMethod", "HmacSHA256");
        params.put("Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        // build signature:
        SortedMap<String, String> map = new TreeMap<>(params);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("token")) continue; //token不参与签名
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append('=').append(urlEncode(value)).append('&');
        }

        sb.deleteCharAt(sb.length() - 1);
        Mac hmacSha256 = null;
        byte[] hash = null;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey =
                    new SecretKeySpec(appSecretKey.getBytes("UTF-8"), "HmacSHA256");
            hmacSha256.init(secKey);
            String payload = sb.toString();
            hash = hmacSha256.doFinal(payload.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncoding key: " + e.getMessage());
        }
        String actualSign = Base64.encodeToString(hash, Base64.NO_WRAP);
        //把签名添加到请求参数中
        params.put("Signature", actualSign);
    }


    /**
     * 请求参数转码
     *
     * @param params
     * @return
     */
    public static String convertQueryString(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> strings = params.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = params.get(key);
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return "?" + stringBuilder.toString();
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }
}
