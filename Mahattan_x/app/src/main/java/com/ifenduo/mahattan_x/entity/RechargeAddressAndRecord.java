package com.ifenduo.mahattan_x.entity;

import java.util.List;

/**
 * Created by ll on 2018/4/3.
 */

public class RechargeAddressAndRecord {

    /**
     * coinType : {"id":1,"name":"比特币","type":2,"shortname":"BTC","weblogo":"https://hotcoin-static.oss-cn-hangzhou.aliyuncs.com/hotcoin/upload/coin/8304633eec1c47a1878dfb7ee4e7cab2timg (2).jpg","applogo":"https://hotcoin-static.oss-cn-hangzhou.aliyuncs.com/hotcoin/upload/coin/50e9ad1babac4e9c8e7e7f6fe49ef8fetimg (2).jpg","symbol":"","status":1,"withdraw":true,"recharge":true}
     * rechargeAddress : {"fid":767,"fcoinid":1,"fadderess":"1Ba4Mg1zqcs1paodEniuJTsjYFsBEirQmQ","fuid":10687,"fcreatetime":1516260608000,"fshortname":null}
     * page : {"totalRows":0,"pageSize":10,"currentPage":1,"totalPages":1,"data":[],"pagin":""}
     */

    private CoinTypeBean coinType;
    private RechargeAddressBean rechargeAddress;
    private PageBean page;

    public CoinTypeBean getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinTypeBean coinType) {
        this.coinType = coinType;
    }

    public RechargeAddressBean getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(RechargeAddressBean rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class CoinTypeBean {
        /**
         * id : 1
         * name : 比特币
         * type : 2
         * shortname : BTC
         * weblogo : https://hotcoin-static.oss-cn-hangzhou.aliyuncs.com/hotcoin/upload/coin/8304633eec1c47a1878dfb7ee4e7cab2timg (2).jpg
         * applogo : https://hotcoin-static.oss-cn-hangzhou.aliyuncs.com/hotcoin/upload/coin/50e9ad1babac4e9c8e7e7f6fe49ef8fetimg (2).jpg
         * symbol :
         * status : 1
         * withdraw : true
         * recharge : true
         */

        private int id;
        private String name;
        private int type;
        private String shortname;
        private String weblogo;
        private String applogo;
        private String symbol;
        private int status;
        private boolean withdraw;
        private boolean recharge;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getWeblogo() {
            return weblogo;
        }

        public void setWeblogo(String weblogo) {
            this.weblogo = weblogo;
        }

        public String getApplogo() {
            return applogo;
        }

        public void setApplogo(String applogo) {
            this.applogo = applogo;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isWithdraw() {
            return withdraw;
        }

        public void setWithdraw(boolean withdraw) {
            this.withdraw = withdraw;
        }

        public boolean isRecharge() {
            return recharge;
        }

        public void setRecharge(boolean recharge) {
            this.recharge = recharge;
        }
    }

    public static class RechargeAddressBean {
        /**
         * fid : 767
         * fcoinid : 1
         * fadderess : 1Ba4Mg1zqcs1paodEniuJTsjYFsBEirQmQ
         * fuid : 10687
         * fcreatetime : 1516260608000
         * fshortname : null
         */

        private int fid;
        private int fcoinid;
        private String fadderess;
        private int fuid;
        private long fcreatetime;
        private Object fshortname;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getFcoinid() {
            return fcoinid;
        }

        public void setFcoinid(int fcoinid) {
            this.fcoinid = fcoinid;
        }

        public String getFadderess() {
            return fadderess;
        }

        public void setFadderess(String fadderess) {
            this.fadderess = fadderess;
        }

        public int getFuid() {
            return fuid;
        }

        public void setFuid(int fuid) {
            this.fuid = fuid;
        }

        public long getFcreatetime() {
            return fcreatetime;
        }

        public void setFcreatetime(long fcreatetime) {
            this.fcreatetime = fcreatetime;
        }

        public Object getFshortname() {
            return fshortname;
        }

        public void setFshortname(Object fshortname) {
            this.fshortname = fshortname;
        }
    }

    public static class PageBean {
        /**
         * totalRows : 0
         * pageSize : 10
         * currentPage : 1
         * totalPages : 1
         * data : []
         * pagin :
         */

        private int totalRows;
        private int pageSize;
        private int currentPage;
        private int totalPages;
        private String pagin;
        private List<ChargeRecord> data;

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public String getPagin() {
            return pagin;
        }

        public void setPagin(String pagin) {
            this.pagin = pagin;
        }

        public List<ChargeRecord> getData() {
            return data;
        }

        public void setData(List<ChargeRecord> data) {
            this.data = data;
        }
    }
}
