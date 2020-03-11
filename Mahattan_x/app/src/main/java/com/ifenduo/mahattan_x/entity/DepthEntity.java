package com.ifenduo.mahattan_x.entity;

import java.util.List;

/**
 * Created by ll on 2018/3/15.
 */

public class DepthEntity {


    private long date;
    private List<List<Double>> asks;
    private List<List<Double>> bids;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<List<Double>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Double>> asks) {
        this.asks = asks;
    }

    public List<List<Double>> getBids() {
        return bids;
    }

    public void setBids(List<List<Double>> bids) {
        this.bids = bids;
    }
}
