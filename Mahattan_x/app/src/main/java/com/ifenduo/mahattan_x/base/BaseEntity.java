package com.ifenduo.mahattan_x.base;

/**
 * Created by yangxuefeng on 16/10/26.
 */

public class BaseEntity {

    private int code;
    private String msg;
    private String status;
    private String jsonData;

    public boolean isSuccess() {
        return (code == 200 || code == 1);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
