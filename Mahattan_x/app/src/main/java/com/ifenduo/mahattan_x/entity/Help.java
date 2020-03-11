package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

public class Help  implements Parcelable {


    private String service_phone;
    private List<HelpBean> data;
    /**
     * id : 1
     * catid : 1
     * title : BTC/USD
     * inputtime : 1535456012
     * uid : 1
     * gua : 0.2%
     * mai : 0.3%
     * huobiziliao : null
     * da : null
     */

    private String id;
    private String catid;
    private String title;
    private String inputtime;
    private String uid;
    private String gua;
    private String mai;
    private String huobiziliao;
    private String da;
    private String name;
    public static Help objectFromData(String str) {

        return new Gson().fromJson(str, Help.class);
    }


    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public List<HelpBean> getData() {
        return data;
    }

    public void setData(List<HelpBean> data) {
        this.data = data;
    }

    public Help() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGua() {
        return gua;
    }

    public void setGua(String gua) {
        this.gua = gua;
    }

    public String getMai() {
        return mai;
    }

    public void setMai(String mai) {
        this.mai = mai;
    }

    public String getHuobiziliao() {
        return huobiziliao;
    }

    public void setHuobiziliao(String huobiziliao) {
        this.huobiziliao = huobiziliao;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
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
        dest.writeString(this.service_phone);
        dest.writeTypedList(this.data);
        dest.writeString(this.id);
        dest.writeString(this.catid);
        dest.writeString(this.title);
        dest.writeString(this.inputtime);
        dest.writeString(this.uid);
        dest.writeString(this.gua);
        dest.writeString(this.mai);
        dest.writeString(this.huobiziliao);
        dest.writeString(this.da);
        dest.writeString(this.name);
    }

    protected Help(Parcel in) {
        this.service_phone = in.readString();
        this.data = in.createTypedArrayList(HelpBean.CREATOR);
        this.id = in.readString();
        this.catid = in.readString();
        this.title = in.readString();
        this.inputtime = in.readString();
        this.uid = in.readString();
        this.gua = in.readString();
        this.mai = in.readString();
        this.huobiziliao = in.readString();
        this.da = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Help> CREATOR = new Creator<Help>() {
        @Override
        public Help createFromParcel(Parcel source) {
            return new Help(source);
        }

        @Override
        public Help[] newArray(int size) {
            return new Help[size];
        }
    };
}
