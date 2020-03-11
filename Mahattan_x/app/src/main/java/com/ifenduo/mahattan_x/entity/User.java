package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class User {
    private String fid;
    private int fshowid;
    private String floginname;
    private String fnickname;
    private String floginpassword;
    private String ftradepassword;
    private String ftelephone;
    private String femail;
    private String frealname;
    private String fidentityno;
    private int fidentitytype;
    private String fgoogleauthenticator;
    private String fgoogleurl;
    private int fstatus;
    private String fstatus_s;
    private boolean fhasrealvalidate;
    private long fhasrealvalidatetime;
    private boolean fistelephonebind;
    private boolean fismailbind;
    private boolean fgooglebind;
    private Object isVideo;
    private Object videoTime;
    private long fupdatetime;
    private String fareacode;
    private int version;
    private int fintrouid;
    private int finvalidateintrocount;
    private int fiscny;
    private String fiscny_s;
    private int fiscoin;
    private String fiscoin_s;
    private long fbirth;
    private long flastlogintime;
    private long fregistertime;
    private long ftradepwdtime;
    private int fleverlock;
    private Object fqqopenid;
    private Object funionid;
    private int fagentid;
    private Object folduid;
    private int fplatform;
    private String ip;
    private int score;
    private int level;
    private long flastip;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
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

    public String getFloginpassword() {
        return floginpassword;
    }

    public void setFloginpassword(String floginpassword) {
        this.floginpassword = floginpassword;
    }

    public String getFtradepassword() {
        return ftradepassword;
    }

    public void setFtradepassword(String ftradepassword) {
        this.ftradepassword = ftradepassword;
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

    public String getFgoogleauthenticator() {
        return fgoogleauthenticator;
    }

    public void setFgoogleauthenticator(String fgoogleauthenticator) {
        this.fgoogleauthenticator = fgoogleauthenticator;
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

    public long getFhasrealvalidatetime() {
        return fhasrealvalidatetime;
    }

    public void setFhasrealvalidatetime(long fhasrealvalidatetime) {
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

    public Object getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Object isVideo) {
        this.isVideo = isVideo;
    }

    public Object getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(Object videoTime) {
        this.videoTime = videoTime;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getFintrouid() {
        return fintrouid;
    }

    public void setFintrouid(int fintrouid) {
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

    public long getFbirth() {
        return fbirth;
    }

    public void setFbirth(long fbirth) {
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

    public long getFtradepwdtime() {
        return ftradepwdtime;
    }

    public void setFtradepwdtime(long ftradepwdtime) {
        this.ftradepwdtime = ftradepwdtime;
    }

    public int getFleverlock() {
        return fleverlock;
    }

    public void setFleverlock(int fleverlock) {
        this.fleverlock = fleverlock;
    }

    public Object getFqqopenid() {
        return fqqopenid;
    }

    public void setFqqopenid(Object fqqopenid) {
        this.fqqopenid = fqqopenid;
    }

    public Object getFunionid() {
        return funionid;
    }

    public void setFunionid(Object funionid) {
        this.funionid = funionid;
    }

    public int getFagentid() {
        return fagentid;
    }

    public void setFagentid(int fagentid) {
        this.fagentid = fagentid;
    }

    public Object getFolduid() {
        return folduid;
    }

    public void setFolduid(Object folduid) {
        this.folduid = folduid;
    }

    public int getFplatform() {
        return fplatform;
    }

    public void setFplatform(int fplatform) {
        this.fplatform = fplatform;
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

    public long getFlastip() {
        return flastip;
    }

    public void setFlastip(long flastip) {
        this.flastip = flastip;
    }

}
