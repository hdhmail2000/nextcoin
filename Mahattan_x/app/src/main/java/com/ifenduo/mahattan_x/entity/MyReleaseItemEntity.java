package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

public class MyReleaseItemEntity {

    /**
     * id : 60
     * catid : 0
     * title : 彭俊杰
     * thumb : null
     * keywords : null
     * description : null
     * hits : null
     * uid : 10035
     * author : 彭俊杰
     * status : 9
     * url : null
     * link_id : 0
     * tableid : 0
     * inputip : 100.117.230.162
     * inputtime : 1534746981
     * updatetime : 1534746981
     * comments : 0
     * favorites : 0
     * displayorder : 0
     * order_price : 0.25
     * order_volume : 2.000000
     * deal_type : 2
     * order_city : null
     * min_value : 1.000000
     * max_value : 2.000000
     * symbol : 36
     * symbol_name : F1
     * pay_type : 银行卡
     * trade_total : 2.000000
     * success_total : 0.000000
     */

    private String id;
    private String catid;
    private String title;
    private String thumb;
    private String keywords;
    private String description;
    private String hits;
    private String uid;
    private String author;
    private String status;
    private String url;
    private String link_id;
    private String tableid;
    private String inputip;
    private String inputtime;
    private String updatetime;
    private String comments;
    private String favorites;
    private String displayorder;
    private String order_price;
    private String order_volume;
    private String deal_type;
    private String order_city;
    private String min_value;
    private String max_value;
    private String symbol;
    private String symbol_name;
    private String pay_type;
    private String trade_total;
    private String success_total;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getInputip() {
        return inputip;
    }

    public void setInputip(String inputip) {
        this.inputip = inputip;
    }

    public long getInputtime() {
        if(TextUtils.isEmpty(inputtime)){
            return 0;
        }else {
            return Long.parseLong(inputtime);
        }
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_volume() {
        return order_volume;
    }

    public void setOrder_volume(String order_volume) {
        this.order_volume = order_volume;
    }

    public String getDeal_type() {
        return deal_type;
    }

    public void setDeal_type(String deal_type) {
        this.deal_type = deal_type;
    }

    public String getOrder_city() {
        return order_city;
    }

    public void setOrder_city(String order_city) {
        this.order_city = order_city;
    }

    public String getMin_value() {
        return min_value;
    }

    public void setMin_value(String min_value) {
        this.min_value = min_value;
    }

    public String getMax_value() {
        return max_value;
    }

    public void setMax_value(String max_value) {
        this.max_value = max_value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol_name() {
        return symbol_name;
    }

    public void setSymbol_name(String symbol_name) {
        this.symbol_name = symbol_name;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTrade_total() {
        return trade_total;
    }

    public void setTrade_total(String trade_total) {
        this.trade_total = trade_total;
    }

    public String getSuccess_total() {
        return success_total;
    }

    public void setSuccess_total(String success_total) {
        this.success_total = success_total;
    }

    public String getStrStatus() {
        if ("9".equals(status)) {
            return "发布中";
        } else if ("10".equals(status)) {
            return "已取消";
        } else if ("0".equals(status)) {
            return "已完成";
        } else {
            return "";
        }
    }
}
