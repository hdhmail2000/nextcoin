package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class LineChartEntity {
    /**
     * id : 2
     * title : 3
     * symbolName : MHT-X
     * uid : 1
     * author : admincopay
     * inputip : 100.117.230.214
     * inputtime : 1536911514
     * displayorder : 0
     * tableid : 0
     */

    private String id;
    private String title;
    private String symbolName;
    private String uid;
    private String author;
    private String inputip;
    private String inputtime;
    private String displayorder;
    private String tableid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTitle() {
        if (TextUtils.isEmpty(title)) {
            return 0f;
        }
        return Float.parseFloat(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInputip() {
        return inputip;
    }

    public void setInputip(String inputip) {
        this.inputip = inputip;
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

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }
}
