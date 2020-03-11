package com.ifenduo.mahattan_x.entity;

import java.util.List;

/**
 * Created by ll on 2018/4/3.
 */

public class LoginLog {

    /**
     * flogs : {"totalRows":4,"pageSize":10,"currentPage":1,"totalPages":1,"data":[{"uuid":"29232d26-b49a-40af-b17c-19f5d8cf4408","fid":19849,"fuid":10687,"fagentid":null,"ftype":11,"ftype_s":"设置交易密码","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"113.249.195.209","fupdatetime":1521567198000,"fcreatetime":1521567198000,"num":null},{"uuid":"f333f3c1-8f6f-4cc7-8ada-d2213bd3d3c6","fid":16110,"fuid":10687,"fagentid":null,"ftype":11,"ftype_s":"设置交易密码","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"113.249.195.56","fupdatetime":1521567183000,"fcreatetime":1521567183000,"num":null},{"uuid":"bfd691c3-6268-4f9a-8b9f-97730616289d","fid":4865,"fuid":10687,"fagentid":null,"ftype":3,"ftype_s":"绑定邮箱","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"125.84.176.108","fupdatetime":1516268766000,"fcreatetime":1516268766000,"num":null},{"uuid":"3df86644-bb65-48cc-8f3c-ba5b73a7ef36","fid":4844,"fuid":10687,"fagentid":null,"ftype":5,"ftype_s":"绑定手机","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"125.84.176.108","fupdatetime":1516260125000,"fcreatetime":1516260125000,"num":null}],"redirectUrl":"/user/user_settinglog.html?"}
     */

    private FlogsBean flogs;

    public FlogsBean getFlogs() {
        return flogs;
    }

    public void setFlogs(FlogsBean flogs) {
        this.flogs = flogs;
    }

    public static class FlogsBean {
        /**
         * totalRows : 4
         * pageSize : 10
         * currentPage : 1
         * totalPages : 1
         * data : [{"uuid":"29232d26-b49a-40af-b17c-19f5d8cf4408","fid":19849,"fuid":10687,"fagentid":null,"ftype":11,"ftype_s":"设置交易密码","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"113.249.195.209","fupdatetime":1521567198000,"fcreatetime":1521567198000,"num":null},{"uuid":"f333f3c1-8f6f-4cc7-8ada-d2213bd3d3c6","fid":16110,"fuid":10687,"fagentid":null,"ftype":11,"ftype_s":"设置交易密码","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"113.249.195.56","fupdatetime":1521567183000,"fcreatetime":1521567183000,"num":null},{"uuid":"bfd691c3-6268-4f9a-8b9f-97730616289d","fid":4865,"fuid":10687,"fagentid":null,"ftype":3,"ftype_s":"绑定邮箱","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"125.84.176.108","fupdatetime":1516268766000,"fcreatetime":1516268766000,"num":null},{"uuid":"3df86644-bb65-48cc-8f3c-ba5b73a7ef36","fid":4844,"fuid":10687,"fagentid":null,"ftype":5,"ftype_s":"绑定手机","fdatatype":null,"fdatatype_s":"未知","fcapitaltype":null,"fdata":null,"ffees":null,"fbtcfees":null,"fcontent":"","fip":"125.84.176.108","fupdatetime":1516260125000,"fcreatetime":1516260125000,"num":null}]
         * redirectUrl : /user/user_settinglog.html?
         */

        private int totalRows;
        private int pageSize;
        private int currentPage;
        private int totalPages;
        private String redirectUrl;
        private List<DataBean> data;

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

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * uuid : 29232d26-b49a-40af-b17c-19f5d8cf4408
             * fid : 19849
             * fuid : 10687
             * fagentid : null
             * ftype : 11
             * ftype_s : 设置交易密码
             * fdatatype : null
             * fdatatype_s : 未知
             * fcapitaltype : null
             * fdata : null
             * ffees : null
             * fbtcfees : null
             * fcontent :
             * fip : 113.249.195.209
             * fupdatetime : 1521567198000
             * fcreatetime : 1521567198000
             * num : null
             */

            private String uuid;
            private String fid;
            private String fuid;
            private String fagentid;
            private String ftype;
            private String ftype_s;
            private String fdatatype;
            private String fdatatype_s;
            private String fcapitaltype;
            private String fdata;
            private String ffees;
            private String fbtcfees;
            private String fcontent;
            private String fip;
            private long fupdatetime;
            private long fcreatetime;
            private String num;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getFuid() {
                return fuid;
            }

            public void setFuid(String fuid) {
                this.fuid = fuid;
            }

            public String getFagentid() {
                return fagentid;
            }

            public void setFagentid(String fagentid) {
                this.fagentid = fagentid;
            }

            public String getFtype() {
                return ftype;
            }

            public void setFtype(String ftype) {
                this.ftype = ftype;
            }

            public String getFtype_s() {
                return ftype_s;
            }

            public void setFtype_s(String ftype_s) {
                this.ftype_s = ftype_s;
            }

            public String getFdatatype() {
                return fdatatype;
            }

            public void setFdatatype(String fdatatype) {
                this.fdatatype = fdatatype;
            }

            public String getFdatatype_s() {
                return fdatatype_s;
            }

            public void setFdatatype_s(String fdatatype_s) {
                this.fdatatype_s = fdatatype_s;
            }

            public String getFcapitaltype() {
                return fcapitaltype;
            }

            public void setFcapitaltype(String fcapitaltype) {
                this.fcapitaltype = fcapitaltype;
            }

            public String getFdata() {
                return fdata;
            }

            public void setFdata(String fdata) {
                this.fdata = fdata;
            }

            public String getFfees() {
                return ffees;
            }

            public void setFfees(String ffees) {
                this.ffees = ffees;
            }

            public String getFbtcfees() {
                return fbtcfees;
            }

            public void setFbtcfees(String fbtcfees) {
                this.fbtcfees = fbtcfees;
            }

            public String getFcontent() {
                return fcontent;
            }

            public void setFcontent(String fcontent) {
                this.fcontent = fcontent;
            }

            public String getFip() {
                return fip;
            }

            public void setFip(String fip) {
                this.fip = fip;
            }

            public long getFupdatetime() {
                return fupdatetime;
            }

            public void setFupdatetime(long fupdatetime) {
                this.fupdatetime = fupdatetime;
            }

            public long getFcreatetime() {
                return fcreatetime;
            }

            public void setFcreatetime(long fcreatetime) {
                this.fcreatetime = fcreatetime;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
