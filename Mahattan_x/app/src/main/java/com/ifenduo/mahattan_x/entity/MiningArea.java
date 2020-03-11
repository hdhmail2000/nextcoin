package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class MiningArea {
    private String fid;
    private String mhtx;
    private String usdt;
    private String is_complete_mhtx;
    private String is_complete_usdt;
    private String is_onekey_mhtx;
    private String is_onekey_usdt;
    private String mhtxmor;
    private String usdtmor;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public double getMhtx() {
        if (TextUtils.isEmpty(mhtx)) {
            return 0;
        }
        return Double.parseDouble(mhtx);
    }

    public void setMhtx(String mhtx) {
        this.mhtx = mhtx;
    }

    public double getUsdt() {
        if (TextUtils.isEmpty(usdt)) {
            return 0;
        }
        return Double.parseDouble(usdt);
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getIs_complete_mhtx() {
        return is_complete_mhtx;
    }

    public void setIs_complete_mhtx(String is_complete_mhtx) {
        this.is_complete_mhtx = is_complete_mhtx;
    }

    public boolean isMhtOpen() {
//        return "1".equals(is_complete_mhtx);
        return true;
    }

    public boolean isUsdtOpen() {
        return "1".equals(is_complete_usdt);
    }

    public String getIs_complete_usdt() {
        return is_complete_usdt;
    }

    public void setIs_complete_usdt(String is_complete_usdt) {
        this.is_complete_usdt = is_complete_usdt;
    }

    public String getIs_onekey_mhtx() {
        return is_onekey_mhtx;
    }

    public void setIs_onekey_mhtx(String is_onekey_mhtx) {
        this.is_onekey_mhtx = is_onekey_mhtx;
    }

    public String getIs_onekey_usdt() {
        return is_onekey_usdt;
    }

    public void setIs_onekey_usdt(String is_onekey_usdt) {
        this.is_onekey_usdt = is_onekey_usdt;
    }

    public boolean isUsdtOneKey(){
        return "1".equals(is_onekey_usdt);
    }

    public boolean isMhtOneKey(){
        return "1".equals(is_onekey_mhtx);
    }

    public double getMhtxmor() {
        if (TextUtils.isEmpty(mhtxmor)) {
            return 0;
        }
        return Double.parseDouble(mhtxmor);
    }

    public void setMhtxmor(String mhtxmor) {
        this.mhtxmor = mhtxmor;
    }

    public double getUsdtmor() {
        if (TextUtils.isEmpty(usdtmor)) {
            return 0;
        }
        return Double.parseDouble(usdtmor);
    }

    public void setUsdtmor(String usdtmor) {
        this.usdtmor = usdtmor;
    }


}
