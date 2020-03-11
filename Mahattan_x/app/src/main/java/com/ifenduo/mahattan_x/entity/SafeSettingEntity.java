package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

/**
 * Created by ll on 2018/4/4.
 */

public class SafeSettingEntity {

    /**
     * securityLevel : 2
     * device_name : hotcoin--15202353939
     * bindTrade : false
     * bindcount : 2
     * fuser : {"fid":31151,"fshowid":31151,"floginname":"15202353939","fnickname":"15202353939","ftelephone":"15202353939","femail":null,"frealname":null,"fidentityno":null,"fidentitytype":0,"fgoogleurl":"otpauth://totp/hotcoin--15202353939?secret=GPVYPQPYSF7BH3HI","fstatus":1,"fstatus_s":"正常","fhasrealvalidate":false,"fhasrealvalidatetime":null,"fistelephonebind":true,"fismailbind":false,"fgooglebind":false,"fupdatetime":1522830517000,"fareacode":"86","fintrouid":null,"finvalidateintrocount":0,"fiscny":1,"fiscny_s":"正常","fiscoin":1,"fiscoin_s":"正常","fbirth":null,"flastlogintime":1522829247000,"fregistertime":1522823266000,"fqqopenid":null,"funionid":null,"fagentid":0,"fleverlock":0,"ip":null,"score":600,"level":0}
     * identity : null
     * loginName : 152****3939
     * phoneString : +86 152****3939
     * bindLogin : true
     */

    private int securityLevel;
    private String device_name;
    private boolean bindTrade;
    private int bindcount;
    private FuserBean fuser;
    private IdentityBean identity;
    private String loginName;
    private String phoneString;
    private boolean bindLogin;

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public boolean isBindTrade() {
        return bindTrade;
    }

    public void setBindTrade(boolean bindTrade) {
        this.bindTrade = bindTrade;
    }

    public int getBindcount() {
        return bindcount;
    }

    public void setBindcount(int bindcount) {
        this.bindcount = bindcount;
    }

    public FuserBean getFuser() {
        return fuser;
    }

    public void setFuser(FuserBean fuser) {
        this.fuser = fuser;
    }

    public IdentityBean getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityBean identity) {
        this.identity = identity;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public boolean isBindLogin() {
        return bindLogin;
    }

    public void setBindLogin(boolean bindLogin) {
        this.bindLogin = bindLogin;
    }

    public static class FuserBean {
        /**
         * fid : 31151
         * fshowid : 31151
         * floginname : 15202353939
         * fnickname : 15202353939
         * ftelephone : 15202353939
         * femail : null
         * frealname : null
         * fidentityno : null
         * fidentitytype : 0
         * fgoogleurl : otpauth://totp/hotcoin--15202353939?secret=GPVYPQPYSF7BH3HI
         * fstatus : 1
         * fstatus_s : 正常
         * fhasrealvalidate : false
         * fhasrealvalidatetime : null
         * fistelephonebind : true
         * fismailbind : false
         * fgooglebind : false
         * fupdatetime : 1522830517000
         * fareacode : 86
         * fintrouid : null
         * finvalidateintrocount : 0
         * fiscny : 1
         * fiscny_s : 正常
         * fiscoin : 1
         * fiscoin_s : 正常
         * fbirth : null
         * flastlogintime : 1522829247000
         * fregistertime : 1522823266000
         * fqqopenid : null
         * funionid : null
         * fagentid : 0
         * fleverlock : 0
         * ip : null
         * score : 600
         * level : 0
         */

        private int fid;
        private int fshowid;
        private String floginname;
        private String fnickname;
        private String ftelephone;
        private String femail;
        private String frealname;
        private String fidentityno;
        private int fidentitytype;
        private String fgoogleurl;
        private int fstatus;
        private String fstatus_s;
        private boolean fhasrealvalidate;
        private String fhasrealvalidatetime;
        private boolean fistelephonebind;
        private boolean fismailbind;
        private boolean fgooglebind;
        private long fupdatetime;
        private String fareacode;
        private String fintrouid;
        private int finvalidateintrocount;
        private int fiscny;
        private String fiscny_s;
        private int fiscoin;
        private String fiscoin_s;
        private String fbirth;
        private long flastlogintime;
        private long fregistertime;
        private String fqqopenid;
        private String funionid;
        private int fagentid;
        private int fleverlock;
        private String ip;
        private int score;
        private int level;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getFshowid() {
            return fshowid;
        }

        public void setFshowid(int fshowid) {
            this.fshowid = fshowid;
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

        public String getFtelephone() {
            return ftelephone;
        }
        public String getFormtPhone() {
            if (!TextUtils.isEmpty(ftelephone) && ftelephone.length() >= 11) {
                String reStr = ftelephone.substring(ftelephone.length() - 4, ftelephone.length());
                String preStr = ftelephone.substring(0, ftelephone.length() - 8);
                StringBuilder sb = new StringBuilder();
                sb.append(preStr).append("****").append(reStr);
                return sb.toString();
            }
            return TextUtils.isEmpty(ftelephone) ? "" : ftelephone;
        }

        public void setFtelephone(String ftelephone) {
            this.ftelephone = ftelephone;
        }

        public String getFemail() {
            return femail;
        }

        public void setFemail(String femail) {
            this.femail = femail;
        }

        public String getFrealname() {
            return frealname;
        }

        public void setFrealname(String frealname) {
            this.frealname = frealname;
        }

        public String getFidentityno() {
            return fidentityno;
        }

        public void setFidentityno(String fidentityno) {
            this.fidentityno = fidentityno;
        }

        public int getFidentitytype() {
            return fidentitytype;
        }

        public void setFidentitytype(int fidentitytype) {
            this.fidentitytype = fidentitytype;
        }

        public String getFgoogleurl() {
            return fgoogleurl;
        }

        public void setFgoogleurl(String fgoogleurl) {
            this.fgoogleurl = fgoogleurl;
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

        public boolean isFhasrealvalidate() {
            return fhasrealvalidate;
        }

        public void setFhasrealvalidate(boolean fhasrealvalidate) {
            this.fhasrealvalidate = fhasrealvalidate;
        }

        public String getFhasrealvalidatetime() {
            return fhasrealvalidatetime;
        }

        public void setFhasrealvalidatetime(String fhasrealvalidatetime) {
            this.fhasrealvalidatetime = fhasrealvalidatetime;
        }

        public boolean isFistelephonebind() {
            return fistelephonebind;
        }

        public void setFistelephonebind(boolean fistelephonebind) {
            this.fistelephonebind = fistelephonebind;
        }

        public boolean isFismailbind() {
            return fismailbind;
        }

        public void setFismailbind(boolean fismailbind) {
            this.fismailbind = fismailbind;
        }

        public boolean isFgooglebind() {
            return fgooglebind;
        }

        public void setFgooglebind(boolean fgooglebind) {
            this.fgooglebind = fgooglebind;
        }

        public long getFupdatetime() {
            return fupdatetime;
        }

        public void setFupdatetime(long fupdatetime) {
            this.fupdatetime = fupdatetime;
        }

        public String getFareacode() {
            return fareacode;
        }

        public void setFareacode(String fareacode) {
            this.fareacode = fareacode;
        }

        public String getFintrouid() {
            return fintrouid;
        }

        public void setFintrouid(String fintrouid) {
            this.fintrouid = fintrouid;
        }

        public int getFinvalidateintrocount() {
            return finvalidateintrocount;
        }

        public void setFinvalidateintrocount(int finvalidateintrocount) {
            this.finvalidateintrocount = finvalidateintrocount;
        }

        public int getFiscny() {
            return fiscny;
        }

        public void setFiscny(int fiscny) {
            this.fiscny = fiscny;
        }

        public String getFiscny_s() {
            return fiscny_s;
        }

        public void setFiscny_s(String fiscny_s) {
            this.fiscny_s = fiscny_s;
        }

        public int getFiscoin() {
            return fiscoin;
        }

        public void setFiscoin(int fiscoin) {
            this.fiscoin = fiscoin;
        }

        public String getFiscoin_s() {
            return fiscoin_s;
        }

        public void setFiscoin_s(String fiscoin_s) {
            this.fiscoin_s = fiscoin_s;
        }

        public String getFbirth() {
            return fbirth;
        }

        public void setFbirth(String fbirth) {
            this.fbirth = fbirth;
        }

        public long getFlastlogintime() {
            return flastlogintime;
        }

        public void setFlastlogintime(long flastlogintime) {
            this.flastlogintime = flastlogintime;
        }

        public long getFregistertime() {
            return fregistertime;
        }

        public void setFregistertime(long fregistertime) {
            this.fregistertime = fregistertime;
        }

        public String getFqqopenid() {
            return fqqopenid;
        }

        public void setFqqopenid(String fqqopenid) {
            this.fqqopenid = fqqopenid;
        }

        public String getFunionid() {
            return funionid;
        }

        public void setFunionid(String funionid) {
            this.funionid = funionid;
        }

        public int getFagentid() {
            return fagentid;
        }

        public void setFagentid(int fagentid) {
            this.fagentid = fagentid;
        }

        public int getFleverlock() {
            return fleverlock;
        }

        public void setFleverlock(int fleverlock) {
            this.fleverlock = fleverlock;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    public static class IdentityBean{


        /**
         * fid : 15358
         * fuid : 31151
         * fcountry : 光电园总部基地
         * fname : 张三
         * fcode : 500785489850965856
         * ftype : 0
         * fstatus : 0
         * fcreatetime : 1522837412000
         * fupdatetime : null
         * fstatus_s : null
         * ftype_s : null
         * ip : null
         */

        private int fid;
        private int fuid;
        private String fcountry;
        private String fname;
        private String fcode;
        private int ftype;
        private int fstatus;
        private long fcreatetime;
        private long fupdatetime;
        private String fstatus_s;
        private String ftype_s;
        private String ip;

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

        public String getFcountry() {
            return fcountry;
        }

        public void setFcountry(String fcountry) {
            this.fcountry = fcountry;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getFcode() {
            return fcode;
        }

        public void setFcode(String fcode) {
            this.fcode = fcode;
        }

        public int getFtype() {
            return ftype;
        }

        public void setFtype(int ftype) {
            this.ftype = ftype;
        }

        public int getFstatus() {
            return fstatus;
        }

        public void setFstatus(int fstatus) {
            this.fstatus = fstatus;
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

        public String getFstatus_s() {
            return fstatus_s;
        }

        public void setFstatus_s(String fstatus_s) {
            this.fstatus_s = fstatus_s;
        }

        public String getFtype_s() {
            return ftype_s;
        }

        public void setFtype_s(String ftype_s) {
            this.ftype_s = ftype_s;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
