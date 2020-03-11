package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/3/15.
 */

public class TradeDetailTickEntity {

    /**
     * id : 601595423
     * price : 10195.64
     * time : 1494495711
     * amount : 0.243
     * direction : buy
     * tradeId : 601595423
     * ts : 1494495711000
     */

    private long id;
    private double price;
    private long time;
    private double amount;
    private String direction;
    private long tradeId;
    private long ts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
