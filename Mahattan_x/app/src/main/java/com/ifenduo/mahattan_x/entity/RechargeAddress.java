package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RechargeAddress implements Parcelable {

    /**
     * fid : 179
     * fcoinid : 1
     * fadderess : 19QV6D8C2h9iUXyLZkf3Qpktk1rSj5PCQs
     * fuid : 31151
     * fcreatetime : 1522903630000
     * version : 0
     * init : true
     * fremark : testAddress1
     */

    private long fid;
    private long fcoinid;
    private String fadderess;
    private long fuid;
    private long fcreatetime;
    private long version;
    private boolean init;
    private String fremark;
    private String appLogo;

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getFcoinid() {
        return fcoinid;
    }

    public void setFcoinid(long fcoinid) {
        this.fcoinid = fcoinid;
    }

    public String getFadderess() {
        return fadderess;
    }

    public void setFadderess(String fadderess) {
        this.fadderess = fadderess;
    }

    public long getFuid() {
        return fuid;
    }

    public void setFuid(long fuid) {
        this.fuid = fuid;
    }

    public long getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(long fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public RechargeAddress() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fid);
        dest.writeLong(this.fcoinid);
        dest.writeString(this.fadderess);
        dest.writeLong(this.fuid);
        dest.writeLong(this.fcreatetime);
        dest.writeLong(this.version);
        dest.writeByte(this.init ? (byte) 1 : (byte) 0);
        dest.writeString(this.fremark);
        dest.writeString(this.appLogo);
    }

    protected RechargeAddress(Parcel in) {
        this.fid = in.readLong();
        this.fcoinid = in.readLong();
        this.fadderess = in.readString();
        this.fuid = in.readLong();
        this.fcreatetime = in.readLong();
        this.version = in.readLong();
        this.init = in.readByte() != 0;
        this.fremark = in.readString();
        this.appLogo = in.readString();
    }

    public static final Creator<RechargeAddress> CREATOR = new Creator<RechargeAddress>() {
        @Override
        public RechargeAddress createFromParcel(Parcel source) {
            return new RechargeAddress(source);
        }

        @Override
        public RechargeAddress[] newArray(int size) {
            return new RechargeAddress[size];
        }
    };
}
