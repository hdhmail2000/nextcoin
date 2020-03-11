package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class HelpBean implements Parcelable {

    /**
     * id : 2
     * title : 费率标准
     * description : 费率标准
     * inputtime : 1533802198
     */

    private String id;
    private String title;
    private String description;
    private String inputtime;

    public static HelpBean objectFromData(String str) {

        return new Gson().fromJson(str, HelpBean.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.inputtime);
    }

    public HelpBean() {
    }

    protected HelpBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.inputtime = in.readString();
    }

    public static final Parcelable.Creator<HelpBean> CREATOR = new Parcelable.Creator<HelpBean>() {
        @Override
        public HelpBean createFromParcel(Parcel source) {
            return new HelpBean(source);
        }

        @Override
        public HelpBean[] newArray(int size) {
            return new HelpBean[size];
        }
    };
}
