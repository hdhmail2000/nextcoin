package com.ifenduo.mahattan_x.entity;

public class ExchangeRate {


    /**
     * CNY : 6.35
     */

    private String CNY;

    public double getCNY() {
        try {
            return Double.parseDouble(CNY);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setCNY(String CNY) {
        this.CNY = CNY;
    }
}
