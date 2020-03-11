package com.ifenduo.mahattan_x.entity;

public class TradingCenterDataEntity {
    int itemType;
    String title;
    Coin coin;

    public TradingCenterDataEntity(int itemType, String title, Coin coin) {
        this.itemType = itemType;
        this.title = title;
        this.coin = coin;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
}
