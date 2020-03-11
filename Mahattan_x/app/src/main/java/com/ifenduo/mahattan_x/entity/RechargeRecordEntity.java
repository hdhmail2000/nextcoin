package com.ifenduo.mahattan_x.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ll on 2018/4/14.
 */

public class RechargeRecordEntity {

    /**
     * pagin : null
     * frewardcodes : [{"fid":6,"fuid":10687,"floginname":"","ftype":1,"ftype_s":"BTC","fcode":"BDF5E8wP4fhTy6CG","famount":1,"fstate":true,"fstate_s":"","fcreatetime":1523516123000,"fupdatetime":1523516166000,"version":1,"fbatch":"","fislimituser":true,"fislimituse":"","fusenum":"","fusedate":""}]
     */

    private Object pagin;
    private List<FrewardcodesBean> frewardcodes;

    public Object getPagin() {
        return pagin;
    }

    public void setPagin(Object pagin) {
        this.pagin = pagin;
    }

    public List<FrewardcodesBean> getFrewardcodes() {
        return frewardcodes;
    }

    public void setFrewardcodes(List<FrewardcodesBean> frewardcodes) {
        this.frewardcodes = frewardcodes;
    }

    public static class FrewardcodesBean {
        /**
         * fid : 6
         * fuid : 10687
         * floginname :
         * ftype : 1
         * ftype_s : BTC
         * fcode : BDF5E8wP4fhTy6CG
         * famount : 1
         * fstate : true
         * fstate_s :
         * fcreatetime : 1523516123000
         * fupdatetime : 1523516166000
         * version : 1
         * fbatch :
         * fislimituser : true
         * fislimituse :
         * fusenum :
         * fusedate :
         */

        private long fid;
        private long fuid;
        private String floginname;
        private int ftype;
        private String ftype_s;
        private String fcode;
        private BigDecimal famount;
        private boolean fstate;
        private String fstate_s;
        private long fcreatetime;
        private long fupdatetime;
        private int version;
        private String fbatch;
        private boolean fislimituser;
        private String fislimituse;
        private String fusenum;
        private String fusedate;

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

        public String getFloginname() {
            return floginname;
        }

        public void setFloginname(String floginname) {
            this.floginname = floginname;
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

        public String getFcode() {
            return fcode;
        }

        public void setFcode(String fcode) {
            this.fcode = fcode;
        }

        public BigDecimal getFamount() {
            return famount;
        }

        public void setFamount(BigDecimal famount) {
            this.famount = famount;
        }

        public boolean isFstate() {
            return fstate;
        }

        public void setFstate(boolean fstate) {
            this.fstate = fstate;
        }

        public String getFstate_s() {
            return fstate_s;
        }

        public void setFstate_s(String fstate_s) {
            this.fstate_s = fstate_s;
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

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getFbatch() {
            return fbatch;
        }

        public void setFbatch(String fbatch) {
            this.fbatch = fbatch;
        }

        public boolean isFislimituser() {
            return fislimituser;
        }

        public void setFislimituser(boolean fislimituser) {
            this.fislimituser = fislimituser;
        }

        public String getFislimituse() {
            return fislimituse;
        }

        public void setFislimituse(String fislimituse) {
            this.fislimituse = fislimituse;
        }

        public String getFusenum() {
            return fusenum;
        }

        public void setFusenum(String fusenum) {
            this.fusenum = fusenum;
        }

        public String getFusedate() {
            return fusedate;
        }

        public void setFusedate(String fusedate) {
            this.fusedate = fusedate;
        }
    }
}
