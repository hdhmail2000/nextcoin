package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class C2CCoinType implements Parcelable {
    private String id;
    private String sort_id;
    private String name;
    private String short_name;
    private String symbol;
    private String type;
    private String coin_type;
    private String status;
    private String platform_id;
    private String is_withdraw;
    private String is_recharge;
    private String risk_num;
    private String is_push;
    private String is_finances;
    private String ip;
    private String port;
    private String access_key;
    private String secrt_key;
    private String asset_id;
    private String network_fee;
    private String confirmations;
    private String eth_account;
    private String web_logo;
    private String app_logo;
    private String gmt_create;
    private String gmt_modified;
    private String version;
    private String contract_account;
    private String contract_wei;
    private String explorer_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getIs_withdraw() {
        return is_withdraw;
    }

    public void setIs_withdraw(String is_withdraw) {
        this.is_withdraw = is_withdraw;
    }

    public String getIs_recharge() {
        return is_recharge;
    }

    public void setIs_recharge(String is_recharge) {
        this.is_recharge = is_recharge;
    }

    public String getRisk_num() {
        return risk_num;
    }

    public void setRisk_num(String risk_num) {
        this.risk_num = risk_num;
    }

    public String getIs_push() {
        return is_push;
    }

    public void setIs_push(String is_push) {
        this.is_push = is_push;
    }

    public String getIs_finances() {
        return is_finances;
    }

    public void setIs_finances(String is_finances) {
        this.is_finances = is_finances;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getSecrt_key() {
        return secrt_key;
    }

    public void setSecrt_key(String secrt_key) {
        this.secrt_key = secrt_key;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getNetwork_fee() {
        return network_fee;
    }

    public void setNetwork_fee(String network_fee) {
        this.network_fee = network_fee;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    public String getEth_account() {
        return eth_account;
    }

    public void setEth_account(String eth_account) {
        this.eth_account = eth_account;
    }

    public String getWeb_logo() {
        return web_logo;
    }

    public void setWeb_logo(String web_logo) {
        this.web_logo = web_logo;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(String gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContract_account() {
        return contract_account;
    }

    public void setContract_account(String contract_account) {
        this.contract_account = contract_account;
    }

    public String getContract_wei() {
        return contract_wei;
    }

    public void setContract_wei(String contract_wei) {
        this.contract_wei = contract_wei;
    }

    public String getExplorer_url() {
        return explorer_url;
    }

    public void setExplorer_url(String explorer_url) {
        this.explorer_url = explorer_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sort_id);
        dest.writeString(this.name);
        dest.writeString(this.short_name);
        dest.writeString(this.symbol);
        dest.writeString(this.type);
        dest.writeString(this.coin_type);
        dest.writeString(this.status);
        dest.writeString(this.platform_id);
        dest.writeString(this.is_withdraw);
        dest.writeString(this.is_recharge);
        dest.writeString(this.risk_num);
        dest.writeString(this.is_push);
        dest.writeString(this.is_finances);
        dest.writeString(this.ip);
        dest.writeString(this.port);
        dest.writeString(this.access_key);
        dest.writeString(this.secrt_key);
        dest.writeString(this.asset_id);
        dest.writeString(this.network_fee);
        dest.writeString(this.confirmations);
        dest.writeString(this.eth_account);
        dest.writeString(this.web_logo);
        dest.writeString(this.app_logo);
        dest.writeString(this.gmt_create);
        dest.writeString(this.gmt_modified);
        dest.writeString(this.version);
        dest.writeString(this.contract_account);
        dest.writeString(this.contract_wei);
        dest.writeString(this.explorer_url);
    }

    public C2CCoinType() {
    }

    protected C2CCoinType(Parcel in) {
        this.id = in.readString();
        this.sort_id = in.readString();
        this.name = in.readString();
        this.short_name = in.readString();
        this.symbol = in.readString();
        this.type = in.readString();
        this.coin_type = in.readString();
        this.status = in.readString();
        this.platform_id = in.readString();
        this.is_withdraw = in.readString();
        this.is_recharge = in.readString();
        this.risk_num = in.readString();
        this.is_push = in.readString();
        this.is_finances = in.readString();
        this.ip = in.readString();
        this.port = in.readString();
        this.access_key = in.readString();
        this.secrt_key = in.readString();
        this.asset_id = in.readString();
        this.network_fee = in.readString();
        this.confirmations = in.readString();
        this.eth_account = in.readString();
        this.web_logo = in.readString();
        this.app_logo = in.readString();
        this.gmt_create = in.readString();
        this.gmt_modified = in.readString();
        this.version = in.readString();
        this.contract_account = in.readString();
        this.contract_wei = in.readString();
        this.explorer_url = in.readString();
    }

    public static final Creator<C2CCoinType> CREATOR = new Creator<C2CCoinType>() {
        @Override
        public C2CCoinType createFromParcel(Parcel source) {
            return new C2CCoinType(source);
        }

        @Override
        public C2CCoinType[] newArray(int size) {
            return new C2CCoinType[size];
        }
    };
}
