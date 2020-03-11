package com.ifenduo.mahattan_x.entity;

import java.util.List;

public class C2COrderListEntity {
    private String m;
    private int type;
    private int page;
    private int pagecount;
    private List<C2COrder> mr;
    private List<C2COrder> mc;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<C2COrder> getMr() {
        return mr;
    }

    public void setMr(List<C2COrder> mr) {
        this.mr = mr;
    }

    public List<C2COrder> getMc() {
        return mc;
    }

    public void setMc(List<C2COrder> mc) {
        this.mc = mc;
    }
}
