package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

import com.ifenduo.mahattan_x.api.config.URLConfig;

public class PersonalInfo {

    /**
     * fid : 10039
     * floginname : 18883966624
     * fnickname : 这个昵称有点6
     * favatar : https://futureex.cc/statics/avatar/avatartest.jpeg
     * ftelephone : 18883966624
     * fsex : 女
     */

    private String fid;
    private String floginname;
    private String fnickname;
    private String favatar;
    private String ftelephone;
    private String fsex;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFloginname() {
        if (TextUtils.isEmpty(floginname)) {
            return "";
        }
        return floginname;
    }

    public void setFloginname(String floginname) {
        this.floginname = floginname;
    }

    public String getFnickname() {
        if (TextUtils.isEmpty(fnickname)) {
            return "";
        }
        return fnickname;
    }

    public void setFnickname(String fnickname) {
        this.fnickname = fnickname;
    }

    public String getFavatar() {
        if (TextUtils.isEmpty(favatar)) {
            return "";
        } else {
            if (!favatar.startsWith("http")) {
                return URLConfig.HOST_URL + favatar;
            }
        }
        return favatar;
    }

    public void setFavatar(String favatar) {
        this.favatar = favatar;
    }

    public String getFtelephone() {
        if (TextUtils.isEmpty(ftelephone)) {
            return "";
        }
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

    public String getFsex() {
        if (TextUtils.isEmpty(fsex)) {
            return "";
        }
        return fsex;
    }

    public void setFsex(String fsex) {
        this.fsex = fsex;
    }
}
