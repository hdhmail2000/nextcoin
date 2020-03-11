package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 4458 on 2018/2/28.
 */

public class HotCoin implements Parcelable {
    String name;
    String code;
    String number;
    String sureNumer;
    String data;
    String beizhu;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSureNumer() {
        return sureNumer;
    }

    public void setSureNumer(String sureNumer) {
        this.sureNumer = sureNumer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HotCoin(Parcel in) {
        name=in.readString();
        code=in.readString();
        data=in.readString();
        number=in.readString();
        sureNumer=in.readString();
        beizhu=in.readString();
    }

    public HotCoin() {

    }
    public static final Creator<HotCoin> CREATOR = new Creator<HotCoin>() {
        @Override
        public HotCoin createFromParcel(Parcel in) {
            return new HotCoin(in);
        }

        @Override
        public HotCoin[] newArray(int size) {
            return new HotCoin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.data);
        dest.writeString(this.number);
        dest.writeString(this.sureNumer);
        dest.writeString(this.beizhu);
    }
}
