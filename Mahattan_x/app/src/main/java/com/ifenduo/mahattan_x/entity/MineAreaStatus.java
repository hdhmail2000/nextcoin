package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class MineAreaStatus {
    public static final String MINE_SHORT_NAME_USDT = "usdt";
    public static final String MINE_SHORT_NAME_CANDY = "candyx";
    public static final String MINE_SHORT_NAME_MHT = "mhtx";
    public static final String MINE_SHORT_NAME_BANNER_LEFT = "ad";
    public static final String MINE_SHORT_NAME_BANNER_RIGHT = "ad2";
    public static final String MINE_SHORT_NAME_M = "m";

    private String title;
    private String shortname;
    private String num;
    private int status;
    private String getid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public double getNum() {
        if (TextUtils.isEmpty(num)) {
            return 0;
        }
        return Double.parseDouble(num);
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGetid() {
        return getid;
    }

    public void setGetid(String getid) {
        this.getid = getid;
    }

    public boolean isOpen() {
        return status == 1;
    }
}
