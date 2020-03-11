package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ll on 2018/4/3.
 */

public class Wallet implements Parcelable {

    /**
     * id : 7569
     * uid : 10687
     * coinId : 1
     * total : 1
     * frozen : 0
     * borrow : 0
     * ico : 0
     * gmtCreate : 1516260125000
     * gmtModified : 1521793532000
     * loginName : null
     * nickName : null
     * realName : null
     * coinName : 比特币
     * shortName : BTC
     * logo : null
     */

    private int id;
    private int uid;
    private long coinId;
    private double total;
    private double frozen;
    private double borrow;
    private int ico;
    private long gmtCreate;
    private long gmtModified;
    private String loginName;
    private String nickName;
    private String realName;
    private String coinName;
    private String shortName;
    private String logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getCoinId() {
        return coinId;
    }

    public void setCoinId(long coinId) {
        this.coinId = coinId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public double getBorrow() {
        return borrow;
    }

    public void setBorrow(int borrow) {
        this.borrow = borrow;
    }

    public int getIco() {
        return ico;
    }

    public void setIco(int ico) {
        this.ico = ico;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.uid);
        dest.writeLong(this.coinId);
        dest.writeDouble(this.total);
        dest.writeDouble(this.frozen);
        dest.writeDouble(this.borrow);
        dest.writeInt(this.ico);
        dest.writeLong(this.gmtCreate);
        dest.writeLong(this.gmtModified);
        dest.writeString(this.loginName);
        dest.writeString(this.nickName);
        dest.writeString(this.realName);
        dest.writeString(this.coinName);
        dest.writeString(this.shortName);
        dest.writeString(this.logo);
    }

    public Wallet() {
    }

    protected Wallet(Parcel in) {
        this.id = in.readInt();
        this.uid = in.readInt();
        this.coinId = in.readLong();
        this.total = in.readDouble();
        this.frozen = in.readDouble();
        this.borrow = in.readDouble();
        this.ico = in.readInt();
        this.gmtCreate = in.readLong();
        this.gmtModified = in.readLong();
        this.loginName = in.readString();
        this.nickName = in.readString();
        this.realName = in.readString();
        this.coinName = in.readString();
        this.shortName = in.readString();
        this.logo = in.readString();
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
}
