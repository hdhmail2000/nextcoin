package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class BankCard implements Parcelable {

    /**
     * fid : 173
     * fuid : 10687
     * fname : 中国建设银行
     * fbanknumber : 6217003760035061910
     * fbanktype : 1
     * fbanktype_s : 中国建设银行
     * fcreatetime : 1524137477000
     * fstatus : 1
     * version : 0
     * init : true
     * faddress : 万寿路
     * frealname : 杨雪峰
     * fprov : 重庆市
     * fcity : 南岸区
     * ftype : 0
     * fdist :
     */

    private long fid;
    private long fuid;
    private String fname;
    private String fbanknumber;
    private long fbanktype;
    private String fbanktype_s;
    private long fcreatetime;
    private long fstatus;
    private double version;
    private boolean init;
    private String faddress;
    private String frealname;
    private String fprov;
    private String fcity;
    private long ftype;
    private String fdist;

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getFuid() {
        return fuid;
    }

    public void setFuid(long fuid) {
        this.fuid = fuid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFbanknumber() {
        return fbanknumber;
    }

    public void setFbanknumber(String fbanknumber) {
        this.fbanknumber = fbanknumber;
    }

    public long getFbanktype() {
        return fbanktype;
    }

    public void setFbanktype(long fbanktype) {
        this.fbanktype = fbanktype;
    }

    public String getFbanktype_s() {
        return fbanktype_s;
    }

    public void setFbanktype_s(String fbanktype_s) {
        this.fbanktype_s = fbanktype_s;
    }

    public long getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(long fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public long getFstatus() {
        return fstatus;
    }

    public void setFstatus(long fstatus) {
        this.fstatus = fstatus;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getFrealname() {
        return frealname;
    }

    public void setFrealname(String frealname) {
        this.frealname = frealname;
    }

    public String getFprov() {
        return fprov;
    }

    public void setFprov(String fprov) {
        this.fprov = fprov;
    }

    public String getFcity() {
        return fcity;
    }

    public void setFcity(String fcity) {
        this.fcity = fcity;
    }

    public long getFtype() {
        return ftype;
    }

    public void setFtype(long ftype) {
        this.ftype = ftype;
    }

    public String getFdist() {
        return fdist;
    }

    public void setFdist(String fdist) {
        this.fdist = fdist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fid);
        dest.writeLong(this.fuid);
        dest.writeString(this.fname);
        dest.writeString(this.fbanknumber);
        dest.writeLong(this.fbanktype);
        dest.writeString(this.fbanktype_s);
        dest.writeLong(this.fcreatetime);
        dest.writeLong(this.fstatus);
        dest.writeDouble(this.version);
        dest.writeByte(this.init ? (byte) 1 : (byte) 0);
        dest.writeString(this.faddress);
        dest.writeString(this.frealname);
        dest.writeString(this.fprov);
        dest.writeString(this.fcity);
        dest.writeLong(this.ftype);
        dest.writeString(this.fdist);
    }

    public BankCard() {
    }

    protected BankCard(Parcel in) {
        this.fid = in.readLong();
        this.fuid = in.readLong();
        this.fname = in.readString();
        this.fbanknumber = in.readString();
        this.fbanktype = in.readLong();
        this.fbanktype_s = in.readString();
        this.fcreatetime = in.readLong();
        this.fstatus = in.readLong();
        this.version = in.readDouble();
        this.init = in.readByte() != 0;
        this.faddress = in.readString();
        this.frealname = in.readString();
        this.fprov = in.readString();
        this.fcity = in.readString();
        this.ftype = in.readLong();
        this.fdist = in.readString();
    }

    public static final Creator<BankCard> CREATOR = new Creator<BankCard>() {
        @Override
        public BankCard createFromParcel(Parcel source) {
            return new BankCard(source);
        }

        @Override
        public BankCard[] newArray(int size) {
            return new BankCard[size];
        }
    };
}
