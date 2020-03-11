package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/3/15.
 */

public class DepthItemEntity {
    double price;
    double num;
    double sum;
    float progress;

    public DepthItemEntity(double price, double num, double sum,float progress) {
        this.price = price;
        this.num = num;
        this.sum = sum;
        this.progress=progress;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
