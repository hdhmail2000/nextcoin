package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class CalculationRecord {
    private String id;
    private String fid;
    private String balance;
    private String value;
    private String note;
    private String inputtime;
    private String type;
    private String year;
    private String month;
    private String day;

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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
        if(TextUtils.isEmpty(inputtime)){
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
