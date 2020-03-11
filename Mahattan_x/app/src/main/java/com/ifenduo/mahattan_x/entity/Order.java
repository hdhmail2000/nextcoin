package com.ifenduo.mahattan_x.entity;

import com.ifenduo.mahattan_x.tools.MathUtils;

import java.math.BigDecimal;

/**
 * Created by ll on 2018/4/13.
 */

public class Order {

    /**
     * types : 0
     * leftcount : 0.0012
     * fees : 0
     * last : 0
     * count : 0.0012
     * successamount : 0
     * source : APP
     * type : 0
     * price : 0.05
     * buysymbol :
     * id : 6798344
     * time : 2018-04-13 21:17:07
     * sellsymbol :
     * status : 1
     */

    private BigDecimal types;
    private BigDecimal leftcount;
    private double fees;
    private double last;
    private BigDecimal count;
    private BigDecimal successamount;
    private String source;
    private int type;
    private BigDecimal price;
    private String buysymbol;
    private long id;
    private String time;
    private String sellsymbol;
    private int status;

    public BigDecimal getTypes() {
        return types;
    }

    public void setTypes(BigDecimal types) {
        this.types = types;
    }

    public BigDecimal getLeftcount() {
        return leftcount;
    }

    public void setLeftcount(BigDecimal leftcount) {
        this.leftcount = leftcount;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getSuccessamount() {
        return successamount;
    }

    public void setSuccessamount(BigDecimal successamount) {
        this.successamount = successamount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBuysymbol() {
        return buysymbol;
    }

    public void setBuysymbol(String buysymbol) {
        this.buysymbol = buysymbol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSellsymbol() {
        return sellsymbol;
    }

    public void setSellsymbol(String sellsymbol) {
        this.sellsymbol = sellsymbol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getleftCountString() {
        return leftcount.stripTrailingZeros().toPlainString();
    }

    public String getCountString() {
        return count.stripTrailingZeros().toPlainString();
    }

    public String getSumMoney() {
        return MathUtils.mul(count, price).stripTrailingZeros().toPlainString();
    }

    public String getPriceString() {
        return price.stripTrailingZeros().toPlainString();
    }

    public String getSuccessAmountString() {
        return successamount.stripTrailingZeros().toPlainString();
    }


    public String getHistoryOrderStatus(){
        String strStatus="";
        if(status==1){
            strStatus="未成交";
        }else if(status==2){
            strStatus="部分成交";
        }else if(status==3){
            strStatus="完全成交";
        }else if(status==4){
            strStatus="撤单处理中";
        }else if(status==5){
            strStatus="已撤销";
        }
        return strStatus;
    }
}
