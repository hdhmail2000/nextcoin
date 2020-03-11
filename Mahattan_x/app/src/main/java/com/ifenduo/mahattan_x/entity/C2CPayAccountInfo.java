package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ifenduo.mahattan_x.api.config.URLConfig;
import com.ifenduo.mahattan_x.net.OSSManager;

/**
 * Created by mac on 2018/9/5.
 */

public class C2CPayAccountInfo implements Parcelable {

    /**
     * m : addPayType
     * bank : {"id":"6","uid":"10035","author":"彭俊杰","inputip":"100.117.230.205","inputtime":"1533550266","khh":"中国建设银行","khm":"彭俊杰","zhhm":"621785320002686793","idCard":"500103198902181918","phone":"13983603321"}
     * alipay : {"id":"8","uid":"10035","author":"18580967515","inputip":"100.117.230.204","inputtime":"1533380899","name":null,"username":"375363378@qq.com","qrcode":"/uploadfile/alipay/uid_10035_qrcode_1534660567_56.png"}
     * weixin : {"id":"5","uid":"10035","author":"彭俊杰","inputip":"100.117.230.160","inputtime":"1534659287","name":null,"username":"qq12345","qrcode":"/uploadfile/weixin/uid_10035_qrcode_1534665781_41.png"}
     */

    private String m;
    private BankBean bank;
    private AlipayBean alipay;
    private WeixinBean weixin;

    public static C2CPayAccountInfo objectFromData(String str) {
        return new Gson().fromJson(str, C2CPayAccountInfo.class);
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public BankBean getBank() {
        return bank;
    }

    public void setBank(BankBean bank) {
        this.bank = bank;
    }

    public AlipayBean getAlipay() {
        return alipay;
    }

    public void setAlipay(AlipayBean alipay) {
        this.alipay = alipay;
    }

    public WeixinBean getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinBean weixin) {
        this.weixin = weixin;
    }

    public static class BankBean {
        /**
         * id : 6
         * uid : 10035
         * author : 彭俊杰
         * inputip : 100.117.230.205
         * inputtime : 1533550266
         * khh : 中国建设银行
         * khm : 彭俊杰
         * zhhm : 621785320002686793
         * idCard : 500103198902181918
         * phone : 13983603321
         */

        private String id;
        private String uid;
        private String author;
        private String inputip;
        private String inputtime;
        private String khh;
        private String khm;
        private String zhhm;
        private String idCard;
        private String phone;

        public static BankBean objectFromData(String str) {

            return new Gson().fromJson(str, BankBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getInputip() {
            return inputip;
        }

        public void setInputip(String inputip) {
            this.inputip = inputip;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getKhh() {
            return khh;
        }

        public void setKhh(String khh) {
            this.khh = khh;
        }

        public String getKhm() {
            return khm;
        }

        public void setKhm(String khm) {
            this.khm = khm;
        }

        public String getZhhm() {
            return zhhm;
        }

        public void setZhhm(String zhhm) {
            this.zhhm = zhhm;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class AlipayBean {
        /**
         * id : 8
         * uid : 10035
         * author : 18580967515
         * inputip : 100.117.230.204
         * inputtime : 1533380899
         * name : null
         * username : 375363378@qq.com
         * qrcode : /uploadfile/alipay/uid_10035_qrcode_1534660567_56.png
         */

        private String id;
        private String uid;
        private String author;
        private String inputip;
        private String inputtime;
        private String name;
        private String username;
        private String qrcode;

        public static AlipayBean objectFromData(String str) {

            return new Gson().fromJson(str, AlipayBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getInputip() {
            return inputip;
        }

        public void setInputip(String inputip) {
            this.inputip = inputip;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getName() {
            if (TextUtils.isEmpty(name)) return "";
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getQrcode() {
            if (!TextUtils.isEmpty(qrcode)) {
                if (!qrcode.startsWith("http")) {
                    return URLConfig.HOST_URL + qrcode;
                }
            }
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }

    public static class WeixinBean {
        /**
         * id : 5
         * uid : 10035
         * author : 彭俊杰
         * inputip : 100.117.230.160
         * inputtime : 1534659287
         * name : null
         * username : qq12345
         * qrcode : /uploadfile/weixin/uid_10035_qrcode_1534665781_41.png
         */

        private String id;
        private String uid;
        private String author;
        private String inputip;
        private String inputtime;
        private String name;
        private String username;
        private String qrcode;

        public static WeixinBean objectFromData(String str) {

            return new Gson().fromJson(str, WeixinBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getInputip() {
            return inputip;
        }

        public void setInputip(String inputip) {
            this.inputip = inputip;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getName() {
            if (TextUtils.isEmpty(name)) return "";
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getQrcode() {
            if (!TextUtils.isEmpty(qrcode)) {
                if (!qrcode.startsWith("http")) {
                    return URLConfig.HOST_URL + qrcode;
                }
            }
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m);
    }

    public C2CPayAccountInfo() {
    }

    protected C2CPayAccountInfo(Parcel in) {
        this.m = in.readString();
        this.bank = in.readParcelable(BankBean.class.getClassLoader());
        this.alipay = in.readParcelable(AlipayBean.class.getClassLoader());
        this.weixin = in.readParcelable(WeixinBean.class.getClassLoader());
    }

    public static final Creator<C2CPayAccountInfo> CREATOR = new Creator<C2CPayAccountInfo>() {
        @Override
        public C2CPayAccountInfo createFromParcel(Parcel source) {
            return new C2CPayAccountInfo(source);
        }

        @Override
        public C2CPayAccountInfo[] newArray(int size) {
            return new C2CPayAccountInfo[size];
        }
    };
}
