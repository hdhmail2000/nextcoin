package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ll on 2018/4/11.
 */

public class TradingArea implements Parcelable {

    /**
     * code : 3
     * name : ETH
     */

    private long code;
    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.code);
        dest.writeString(this.name);
    }

    public TradingArea() {
    }

    protected TradingArea(Parcel in) {
        this.code = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<TradingArea> CREATOR = new Creator<TradingArea>() {
        @Override
        public TradingArea createFromParcel(Parcel source) {
            return new TradingArea(source);
        }

        @Override
        public TradingArea[] newArray(int size) {
            return new TradingArea[size];
        }
    };
}
