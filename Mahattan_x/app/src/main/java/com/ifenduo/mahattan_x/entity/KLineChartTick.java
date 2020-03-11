package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/3/13.
 */

public class KLineChartTick {

    private double open;
    private double close;
    private double low;
    private double high;
    private double vol;
    private long time;




    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public KLineChartTick(double open, double close, double low, double high, double vol, long time) {
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.vol = vol;
        this.time = time;
    }
}
