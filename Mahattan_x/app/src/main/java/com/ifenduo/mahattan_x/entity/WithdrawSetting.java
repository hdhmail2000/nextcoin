package com.ifenduo.mahattan_x.entity;

public class WithdrawSetting {

    /**
     * id : 5
     * coinId : 1
     * levelVip : 4
     * withdrawMax : 0
     * withdrawMin : 1.0E-4
     * withdrawFee : 0.001
     * withdrawTimes : 5
     * withdrawDayLimit : 20
     * gmtCreate : 1506482238000
     * gmtModified : 1520250081000
     * version : 2
     */

    private long id;
    private long coinId;
    private long levelVip;
    private double withdrawMax;
    private double withdrawMin;
    private double withdrawFee;
    private long withdrawTimes;
    private long withdrawDayLimit;
    private long gmtCreate;
    private long gmtModified;
    private double version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoinId() {
        return coinId;
    }

    public void setCoinId(long coinId) {
        this.coinId = coinId;
    }

    public long getLevelVip() {
        return levelVip;
    }

    public void setLevelVip(long levelVip) {
        this.levelVip = levelVip;
    }

    public double getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(double withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public double getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(double withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public double getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public long getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(long withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public long getWithdrawDayLimit() {
        return withdrawDayLimit;
    }

    public void setWithdrawDayLimit(long withdrawDayLimit) {
        this.withdrawDayLimit = withdrawDayLimit;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
