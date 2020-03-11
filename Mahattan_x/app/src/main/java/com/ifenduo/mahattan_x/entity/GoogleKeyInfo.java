package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/4/3.
 */

public class GoogleKeyInfo {


    /**
     * qecode : otpauth://totp/hotcoin--15823488981?secret=SVYP6DE4LB37BU4U
     * code : 0
     * totpKey : SVYP6DE4LB37BU4U
     */

    private String qecode;
    private long code;
    private String totpKey;
    private String device_name;

    public String getQecode() {
        return qecode;
    }

    public void setQecode(String qecode) {
        this.qecode = qecode;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTotpKey() {
        return totpKey;
    }

    public void setTotpKey(String totpKey) {
        this.totpKey = totpKey;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
}
