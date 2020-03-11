package com.ifenduo.mahattan_x.entity;

public class BankCardType {

    /**
     * fid : 1
     * fcnname : 中国建设银行
     * ftwname : 中国建设银行
     * fenname : CCB
     * flogo : null
     * ftype : 1
     * fsort : 1
     * fstate : true
     * bankCode : 0
     */

    private long fid;
    private String fcnname;
    private String ftwname;
    private String fenname;
    private String flogo;
    private long ftype;
    private long fsort;
    private boolean fstate;
    private String bankCode;

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getFcnname() {
        return fcnname;
    }

    public void setFcnname(String fcnname) {
        this.fcnname = fcnname;
    }

    public String getFtwname() {
        return ftwname;
    }

    public void setFtwname(String ftwname) {
        this.ftwname = ftwname;
    }

    public String getFenname() {
        return fenname;
    }

    public void setFenname(String fenname) {
        this.fenname = fenname;
    }

    public String getFlogo() {
        return flogo;
    }

    public void setFlogo(String flogo) {
        this.flogo = flogo;
    }

    public long getFtype() {
        return ftype;
    }

    public void setFtype(long ftype) {
        this.ftype = ftype;
    }

    public long getFsort() {
        return fsort;
    }

    public void setFsort(long fsort) {
        this.fsort = fsort;
    }

    public boolean isFstate() {
        return fstate;
    }

    public void setFstate(boolean fstate) {
        this.fstate = fstate;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
