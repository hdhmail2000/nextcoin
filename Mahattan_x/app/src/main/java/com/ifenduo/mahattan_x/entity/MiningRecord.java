package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class MiningRecord {

    /**
     * id : 10
     * fid : 20343
     * coin_id : 55
     * coin_name : MTH-X
     * value : 63.33333000
     * note : mhtx矿池挖矿
     * inputtime : 1536222688
     * type : 1
     * mineral : mhtx
     * status : 1
     * updatetime :
     * logid :
     */

    private String id;
    private String fid;
    private String coin_id;
    private String coin_name;
    private String value;
    private String note;
    private String inputtime;
    private String type;
    private String mineral;
    private String status;
    private String updatetime;
    private String logid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getCoin_id() {
        return coin_id;
    }

    public void setCoin_id(String coin_id) {
        this.coin_id = coin_id;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getInputtime() {
        if (TextUtils.isEmpty(inputtime)) {
            return 0;
        }
        return Long.parseLong(inputtime);
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMineral() {
        return mineral;
    }

    public void setMineral(String mineral) {
        this.mineral = mineral;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }
}
