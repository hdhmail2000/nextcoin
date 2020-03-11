package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.ifenduo.mahattan_x.api.config.URLConfig;

public class C2COrder implements Parcelable {
    private String id;
    private String sn;
    private String pay_no;
    private String mid;
    private String mid_id;
    private String buy_uid;
    private String buy_username;
    private String buy_step;
    private String sell_uid;
    private String sell_username;
    private String order_volume;
    private String order_time;
    private String order_status;
    private String price;
    private String order_price;
    private String order_score;
    private String pay_type;
    private String pay_id;
    private String pay_status;
    private String pay_time;
    private String tableid;
    private String flag;
    private String description;
    private String imgUrl;
    private String symbolName;
    private BankBean bank;
    private AlipayBean alipay;
    private WeixinBean weixin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMid_id() {
        return mid_id;
    }

    public void setMid_id(String mid_id) {
        this.mid_id = mid_id;
    }

    public String getBuy_uid() {
        return buy_uid;
    }

    public void setBuy_uid(String buy_uid) {
        this.buy_uid = buy_uid;
    }

    public String getBuy_username() {
        return buy_username;
    }

    public void setBuy_username(String buy_username) {
        this.buy_username = buy_username;
    }

    public String getBuy_step() {
        return buy_step;
    }

    public void setBuy_step(String buy_step) {
        this.buy_step = buy_step;
    }

    public String getSell_uid() {
        return sell_uid;
    }

    public void setSell_uid(String sell_uid) {
        this.sell_uid = sell_uid;
    }

    public String getSell_username() {
        return sell_username;
    }

    public void setSell_username(String sell_username) {
        this.sell_username = sell_username;
    }

    public String getOrder_volume() {
        return order_volume;
    }

    public void setOrder_volume(String order_volume) {
        this.order_volume = order_volume;
    }

    public long getOrder_time() {
        if (TextUtils.isEmpty(order_time)) {
            return 0;
        }
        return Long.parseLong(order_time);
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getStrOrderStatus() {
        if ("1".equals(order_status)) {
            return "未付款";
        } else if ("2".equals(order_status)) {
            return "等待卖方确认";
        } else if ("3".equals(order_status)) {
            return "已完成";
        } else if ("4".equals(order_status)) {
            return "申诉中";
        } else if ("9".equals(order_status)) {
            return "已取消";
        } else {
            return "";
        }
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_score() {
        return order_score;
    }

    public void setOrder_score(String order_score) {
        this.order_score = order_score;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public static class BankBean implements Parcelable {
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
            if (TextUtils.isEmpty(khh)) {
                return "";
            }
            return khh;
        }

        public void setKhh(String khh) {
            this.khh = khh;
        }

        public String getKhm() {
            if (TextUtils.isEmpty(khm)) {
                return "";
            }
            return khm;
        }

        public void setKhm(String khm) {
            this.khm = khm;
        }

        public String getZhhm() {
            if (TextUtils.isEmpty(zhhm)) {
                return "";
            }
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.author);
            dest.writeString(this.inputip);
            dest.writeString(this.inputtime);
            dest.writeString(this.khh);
            dest.writeString(this.khm);
            dest.writeString(this.zhhm);
            dest.writeString(this.idCard);
            dest.writeString(this.phone);
        }

        public BankBean() {
        }

        protected BankBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.author = in.readString();
            this.inputip = in.readString();
            this.inputtime = in.readString();
            this.khh = in.readString();
            this.khm = in.readString();
            this.zhhm = in.readString();
            this.idCard = in.readString();
            this.phone = in.readString();
        }

        public static final Creator<BankBean> CREATOR = new Creator<BankBean>() {
            @Override
            public BankBean createFromParcel(Parcel source) {
                return new BankBean(source);
            }

            @Override
            public BankBean[] newArray(int size) {
                return new BankBean[size];
            }
        };
    }

    public static class AlipayBean implements Parcelable {
        private String id;
        private String uid;
        private String author;
        private String inputip;
        private String inputtime;
        private String name;
        private String username;
        private String qrcode;

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
            if (TextUtils.isEmpty(name)) {
                return "";
            }
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            if (TextUtils.isEmpty(username)) {
                return "";
            }
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.author);
            dest.writeString(this.inputip);
            dest.writeString(this.inputtime);
            dest.writeString(this.name);
            dest.writeString(this.username);
            dest.writeString(this.qrcode);
        }

        public AlipayBean() {
        }

        protected AlipayBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.author = in.readString();
            this.inputip = in.readString();
            this.inputtime = in.readString();
            this.name = in.readString();
            this.username = in.readString();
            this.qrcode = in.readString();
        }

        public static final Creator<AlipayBean> CREATOR = new Creator<AlipayBean>() {
            @Override
            public AlipayBean createFromParcel(Parcel source) {
                return new AlipayBean(source);
            }

            @Override
            public AlipayBean[] newArray(int size) {
                return new AlipayBean[size];
            }
        };
    }

    public static class WeixinBean implements Parcelable {

        /**
         * id : 15
         * uid : 10050
         * author : 15802321522
         * inputip : 100.117.230.146
         * inputtime : 1536202751
         * name : null
         * username : 2222255
         * qrcode : https://www.FutureEX.cc/uploadfile/app/201809/f763bccb37dd.jpg
         */

        private String id;
        private String uid;
        private String author;
        private String inputip;
        private String inputtime;
        private String name;
        private String username;
        private String qrcode;

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
            if (TextUtils.isEmpty(name)) {
                return "";
            }
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            if (TextUtils.isEmpty(username)) {
                return "";
            }
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uid);
            dest.writeString(this.author);
            dest.writeString(this.inputip);
            dest.writeString(this.inputtime);
            dest.writeString(this.name);
            dest.writeString(this.username);
            dest.writeString(this.qrcode);
        }

        public WeixinBean() {
        }

        protected WeixinBean(Parcel in) {
            this.id = in.readString();
            this.uid = in.readString();
            this.author = in.readString();
            this.inputip = in.readString();
            this.inputtime = in.readString();
            this.name = in.readString();
            this.username = in.readString();
            this.qrcode = in.readString();
        }

        public static final Creator<WeixinBean> CREATOR = new Creator<WeixinBean>() {
            @Override
            public WeixinBean createFromParcel(Parcel source) {
                return new WeixinBean(source);
            }

            @Override
            public WeixinBean[] newArray(int size) {
                return new WeixinBean[size];
            }
        };
    }

    public C2COrder() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sn);
        dest.writeString(this.pay_no);
        dest.writeString(this.mid);
        dest.writeString(this.mid_id);
        dest.writeString(this.buy_uid);
        dest.writeString(this.buy_username);
        dest.writeString(this.buy_step);
        dest.writeString(this.sell_uid);
        dest.writeString(this.sell_username);
        dest.writeString(this.order_volume);
        dest.writeString(this.order_time);
        dest.writeString(this.order_status);
        dest.writeString(this.price);
        dest.writeString(this.order_price);
        dest.writeString(this.order_score);
        dest.writeString(this.pay_type);
        dest.writeString(this.pay_id);
        dest.writeString(this.pay_status);
        dest.writeString(this.pay_time);
        dest.writeString(this.tableid);
        dest.writeString(this.flag);
        dest.writeString(this.description);
        dest.writeString(this.imgUrl);
        dest.writeString(this.symbolName);
        dest.writeParcelable(this.bank, flags);
        dest.writeParcelable(this.alipay, flags);
        dest.writeParcelable(this.weixin, flags);
    }

    protected C2COrder(Parcel in) {
        this.id = in.readString();
        this.sn = in.readString();
        this.pay_no = in.readString();
        this.mid = in.readString();
        this.mid_id = in.readString();
        this.buy_uid = in.readString();
        this.buy_username = in.readString();
        this.buy_step = in.readString();
        this.sell_uid = in.readString();
        this.sell_username = in.readString();
        this.order_volume = in.readString();
        this.order_time = in.readString();
        this.order_status = in.readString();
        this.price = in.readString();
        this.order_price = in.readString();
        this.order_score = in.readString();
        this.pay_type = in.readString();
        this.pay_id = in.readString();
        this.pay_status = in.readString();
        this.pay_time = in.readString();
        this.tableid = in.readString();
        this.flag = in.readString();
        this.description = in.readString();
        this.imgUrl = in.readString();
        this.symbolName = in.readString();
        this.bank = in.readParcelable(BankBean.class.getClassLoader());
        this.alipay = in.readParcelable(AlipayBean.class.getClassLoader());
        this.weixin = in.readParcelable(WeixinBean.class.getClassLoader());
    }

    public static final Creator<C2COrder> CREATOR = new Creator<C2COrder>() {
        @Override
        public C2COrder createFromParcel(Parcel source) {
            return new C2COrder(source);
        }

        @Override
        public C2COrder[] newArray(int size) {
            return new C2COrder[size];
        }
    };
}
