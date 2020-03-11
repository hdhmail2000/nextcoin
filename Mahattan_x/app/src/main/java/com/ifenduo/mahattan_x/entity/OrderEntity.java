package com.ifenduo.mahattan_x.entity;

import java.util.List;

/**
 * Created by ll on 2018/4/13.
 */

public class OrderEntity {
    private List<Order> entrutsHis;
    private List<Order> entrutsCur;

    public List<Order> getEntrutsHis() {
        return entrutsHis;
    }

    public void setEntrutsHis(List<Order> entrutsHis) {
        this.entrutsHis = entrutsHis;
    }

    public List<Order> getEntrutsCur() {
        return entrutsCur;
    }

    public void setEntrutsCur(List<Order> entrutsCur) {
        this.entrutsCur = entrutsCur;
    }
}
