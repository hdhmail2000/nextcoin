package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/4/2.
 */

public class LoginInfo {
    String token;
    User userinfo;
    String secretKey;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(User userinfo) {
        this.userinfo = userinfo;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
