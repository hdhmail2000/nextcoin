package com.ifenduo.mahattan_x.entity;

import java.util.List;

public class CalculationTask {

    /**
     * fability : 2
     * invite : [{"num":1,"total":0},{"num":0,"total":0}]
     */

    private String fability;
    private List<InviteBean> invite;

    public String getFability() {
        return fability;
    }

    public void setFability(String fability) {
        this.fability = fability;
    }

    public List<InviteBean> getInvite() {
        return invite;
    }

    public void setInvite(List<InviteBean> invite) {
        this.invite = invite;
    }

    public static class InviteBean {
        /**
         * num : 1
         * total : 0
         */

        private int num;
        private int total;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
