package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ll on 2018/4/10.
 */

public class ChargeRecord implements Parcelable {

    /**
     * fid : 435
     * fuid : 31101
     * fcoinid : 17
     * famount : 0.001
     * ffees : 0
     * ftype : 1
     * ftype_s : 充值
     * fstatus : 3
     * fstatus_s : 充值成功
     * fwithdrawaddress : null
     * frechargeaddress : 34a9ro7SfDEPW3CMBSJbSw4UAnZFA4L3Nh
     * funiquenumber : fa3639f37628362e8f58efb9bf647a29620571156733dd77bcbb8421a5f4d408_0
     * fconfirmations : 1
     * fhasowner : true
     * fblocknumber : 0
     * fcreatetime : 1523110421000
     * fupdatetime : 1523110640000
     * txTime : null
     * version : 1
     * fadminid : null
     * fbtcfees : 0
     * fnonce : null
     * fsource : 1
     * fsource_s : WEB
     * fplatform : null
     * fplatform_s : null
     * memo :
     * floginname : null
     * fnickname : null
     * frealname : null
     * fadminname : null
     * fcoinname : null
     * fagentid : 0
     * level : 0
     */

    private int fid;
    private int fuid;
    private int fcoinid;
    private double famount;
    private double ffees;
    private int ftype;
    private String ftype_s;
    private int fstatus;
    private String fstatus_s;
    private String fwithdrawaddress;
    private String frechargeaddress;
    private String funiquenumber;
    private int fconfirmations;
    private boolean fhasowner;
    private int fblocknumber;
    private long fcreatetime;
    private long fupdatetime;
    private long txTime;
    private int version;
    private String fadminid;
    private double fbtcfees;
    private String fnonce;
    private int fsource;
    private String fsource_s;
    private String fplatform;
    private String fplatform_s;
    private String memo;
    private String floginname;
    private String fnickname;
    private String frealname;
    private String fadminname;
    private String fcoinname;
    private long fagentid;
    private int level;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFuid() {
        return fuid;
    }

    public void setFuid(int fuid) {
        this.fuid = fuid;
    }

    public int getFcoinid() {
        return fcoinid;
    }

    public void setFcoinid(int fcoinid) {
        this.fcoinid = fcoinid;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }

    public double getFfees() {
        return ffees;
    }

    public void setFfees(double ffees) {
        this.ffees = ffees;
    }

    public int getFtype() {
        return ftype;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    public String getFtype_s() {
        return ftype_s;
    }

    public void setFtype_s(String ftype_s) {
        this.ftype_s = ftype_s;
    }

    public int getFstatus() {
        return fstatus;
    }

    public void setFstatus(int fstatus) {
        this.fstatus = fstatus;
    }

    public String getFstatus_s() {
        return fstatus_s;
    }

    public void setFstatus_s(String fstatus_s) {
        this.fstatus_s = fstatus_s;
    }

    public String getFwithdrawaddress() {
        return fwithdrawaddress;
    }

    public void setFwithdrawaddress(String fwithdrawaddress) {
        this.fwithdrawaddress = fwithdrawaddress;
    }

    public String getFrechargeaddress() {
        return frechargeaddress;
    }

    public void setFrechargeaddress(String frechargeaddress) {
        this.frechargeaddress = frechargeaddress;
    }

    public String getFuniquenumber() {
        return funiquenumber;
    }

    public void setFuniquenumber(String funiquenumber) {
        this.funiquenumber = funiquenumber;
    }

    public int getFconfirmations() {
        return fconfirmations;
    }

    public void setFconfirmations(int fconfirmations) {
        this.fconfirmations = fconfirmations;
    }

    public boolean isFhasowner() {
        return fhasowner;
    }

    public void setFhasowner(boolean fhasowner) {
        this.fhasowner = fhasowner;
    }

    public int getFblocknumber() {
        return fblocknumber;
    }

    public void setFblocknumber(int fblocknumber) {
        this.fblocknumber = fblocknumber;
    }

    public long getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(long fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public long getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(long fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public long getTxTime() {
        return txTime;
    }

    public void setTxTime(long txTime) {
        this.txTime = txTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFadminid() {
        return fadminid;
    }

    public void setFadminid(String fadminid) {
        this.fadminid = fadminid;
    }

    public double getFbtcfees() {
        return fbtcfees;
    }

    public void setFbtcfees(double fbtcfees) {
        this.fbtcfees = fbtcfees;
    }

    public String getFnonce() {
        return fnonce;
    }

    public void setFnonce(String fnonce) {
        this.fnonce = fnonce;
    }

    public int getFsource() {
        return fsource;
    }

    public void setFsource(int fsource) {
        this.fsource = fsource;
    }

    public String getFsource_s() {
        return fsource_s;
    }

    public void setFsource_s(String fsource_s) {
        this.fsource_s = fsource_s;
    }

    public String getFplatform() {
        return fplatform;
    }

    public void setFplatform(String fplatform) {
        this.fplatform = fplatform;
    }

    public String getFplatform_s() {
        return fplatform_s;
    }

    public void setFplatform_s(String fplatform_s) {
        this.fplatform_s = fplatform_s;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFloginname() {
        return floginname;
    }

    public void setFloginname(String floginname) {
        this.floginname = floginname;
    }

    public String getFnickname() {
        return fnickname;
    }

    public void setFnickname(String fnickname) {
        this.fnickname = fnickname;
    }

    public String getFrealname() {
        return frealname;
    }

    public void setFrealname(String frealname) {
        this.frealname = frealname;
    }

    public String getFadminname() {
        return fadminname;
    }

    public void setFadminname(String fadminname) {
        this.fadminname = fadminname;
    }

    public String getFcoinname() {
        return fcoinname;
    }

    public void setFcoinname(String fcoinname) {
        this.fcoinname = fcoinname;
    }

    public long getFagentid() {
        return fagentid;
    }

    public void setFagentid(long fagentid) {
        this.fagentid = fagentid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fid);
        dest.writeInt(this.fuid);
        dest.writeInt(this.fcoinid);
        dest.writeDouble(this.famount);
        dest.writeDouble(this.ffees);
        dest.writeInt(this.ftype);
        dest.writeString(this.ftype_s);
        dest.writeInt(this.fstatus);
        dest.writeString(this.fstatus_s);
        dest.writeString(this.fwithdrawaddress);
        dest.writeString(this.frechargeaddress);
        dest.writeString(this.funiquenumber);
        dest.writeInt(this.fconfirmations);
        dest.writeByte(this.fhasowner ? (byte) 1 : (byte) 0);
        dest.writeInt(this.fblocknumber);
        dest.writeLong(this.fcreatetime);
        dest.writeLong(this.fupdatetime);
        dest.writeLong(this.txTime);
        dest.writeInt(this.version);
        dest.writeString(this.fadminid);
        dest.writeDouble(this.fbtcfees);
        dest.writeString(this.fnonce);
        dest.writeInt(this.fsource);
        dest.writeString(this.fsource_s);
        dest.writeString(this.fplatform);
        dest.writeString(this.fplatform_s);
        dest.writeString(this.memo);
        dest.writeString(this.floginname);
        dest.writeString(this.fnickname);
        dest.writeString(this.frealname);
        dest.writeString(this.fadminname);
        dest.writeString(this.fcoinname);
        dest.writeLong(this.fagentid);
        dest.writeInt(this.level);
    }

    public ChargeRecord() {
    }

    protected ChargeRecord(Parcel in) {
        this.fid = in.readInt();
        this.fuid = in.readInt();
        this.fcoinid = in.readInt();
        this.famount = in.readDouble();
        this.ffees = in.readInt();
        this.ftype = in.readInt();
        this.ftype_s = in.readString();
        this.fstatus = in.readInt();
        this.fstatus_s = in.readString();
        this.fwithdrawaddress = in.readString();
        this.frechargeaddress = in.readString();
        this.funiquenumber = in.readString();
        this.fconfirmations = in.readInt();
        this.fhasowner = in.readByte() != 0;
        this.fblocknumber = in.readInt();
        this.fcreatetime = in.readLong();
        this.fupdatetime = in.readLong();
        this.txTime = in.readLong();
        this.version = in.readInt();
        this.fadminid = in.readString();
        this.fbtcfees = in.readInt();
        this.fnonce = in.readString();
        this.fsource = in.readInt();
        this.fsource_s = in.readString();
        this.fplatform = in.readString();
        this.fplatform_s = in.readString();
        this.memo = in.readString();
        this.floginname = in.readString();
        this.fnickname = in.readString();
        this.frealname = in.readString();
        this.fadminname = in.readString();
        this.fcoinname = in.readString();
        this.fagentid = in.readInt();
        this.level = in.readInt();
    }

    public static final Creator<ChargeRecord> CREATOR = new Creator<ChargeRecord>() {
        @Override
        public ChargeRecord createFromParcel(Parcel source) {
            return new ChargeRecord(source);
        }

        @Override
        public ChargeRecord[] newArray(int size) {
            return new ChargeRecord[size];
        }
    };
}
