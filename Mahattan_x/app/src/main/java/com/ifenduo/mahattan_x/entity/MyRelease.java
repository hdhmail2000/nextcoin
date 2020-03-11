package com.ifenduo.mahattan_x.entity;

import java.util.List;

public class MyRelease {
    private String m;
    private String id;
    private int type;
    private int pagecount;
    private int page;
    private List<MyReleaseItemEntity> data;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MyReleaseItemEntity> getData() {
        return data;
    }

    public void setData(List<MyReleaseItemEntity> data) {
        this.data = data;
    }


}
