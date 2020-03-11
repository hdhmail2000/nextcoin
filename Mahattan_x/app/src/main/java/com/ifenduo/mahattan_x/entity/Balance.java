package com.ifenduo.mahattan_x.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by ll on 2018/3/28.
 */

public class Balance implements Parcelable {

    private double netassets;//净资产
    private double totalassets;//总资产
    private List<Wallet> wallet;

    public double getNetassets() {
        return netassets;
    }

    public void setNetassets(double netassets) {
        this.netassets = netassets;
    }

    public double getTotalassets() {
        return totalassets;
    }

    public void setTotalassets(double totalassets) {
        this.totalassets = totalassets;
    }

    public List<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.netassets);
        dest.writeDouble(this.totalassets);
        dest.writeTypedList(this.wallet);
    }

    public Balance() {
    }

    protected Balance(Parcel in) {
        this.netassets = in.readDouble();
        this.totalassets = in.readDouble();
        this.wallet = in.createTypedArrayList(Wallet.CREATOR);
    }

    public static final Creator<Balance> CREATOR = new Creator<Balance>() {
        @Override
        public Balance createFromParcel(Parcel source) {
            return new Balance(source);
        }

        @Override
        public Balance[] newArray(int size) {
            return new Balance[size];
        }
    };
}
