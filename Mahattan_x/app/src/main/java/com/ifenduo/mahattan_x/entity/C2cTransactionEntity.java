package com.ifenduo.mahattan_x.entity;

import java.util.List;

public class C2cTransactionEntity {

    /**
     * m : c2cBuy
     * id : 36
     * type : 1
     * data : [{"id":"49","catid":"0","title":"张轲","thumb":null,"keywords":null,"description":null,"hits":null,"uid":"10017","author":"张轲","status":"9","url":null,"link_id":"0","tableid":"0","inputip":"100.117.230.215","inputtime":"1534639437","updatetime":"1534639437","comments":"0","favorites":"0","displayorder":"0","order_price":"0.25","order_volume":"180000.000000","deal_type":"1","order_city":null,"min_value":"2000.000000","max_value":"20000.000000","symbol":"36","symbol_name":"F1","pay_type":"支付宝,微信","trade_total":"200000.000000","success_total":"0.000000"}]
     * pagecount : 1
     * page : 1
     */

    private String m;
    private String id;
    private int type;
    private int pagecount;
    private int page;
    private List<C2CTransactionListEntity> data;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<C2CTransactionListEntity> getData() {
        return data;
    }

    public void setData(List<C2CTransactionListEntity> data) {
        this.data = data;
    }


}
