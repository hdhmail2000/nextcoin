package com.ifenduo.mahattan_x.entity;

/**
 * Created by ll on 2018/4/13.
 */

/**
 * 交易对的用户可用资产
 */
public class MoneyOfCoinEntity {

    /**
     * score : 861147
     * buyCoin : {"total":0.4659662,"frozen":0,"borrow":0,"id":9}
     * sellCoin : {"total":1,"frozen":0,"borrow":0,"id":1}
     */

    private double score;
    private BuyCoinBean buyCoin;
    private SellCoinBean sellCoin;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public BuyCoinBean getBuyCoin() {
        return buyCoin;
    }

    public void setBuyCoin(BuyCoinBean buyCoin) {
        this.buyCoin = buyCoin;
    }

    public SellCoinBean getSellCoin() {
        return sellCoin;
    }

    public void setSellCoin(SellCoinBean sellCoin) {
        this.sellCoin = sellCoin;
    }

    public static class BuyCoinBean {
        /**
         * total : 0.4659662
         * frozen : 0
         * borrow : 0
         * id : 9
         */

        private double total;
        private double frozen;
        private double borrow;
        private long id;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getFrozen() {
            return frozen;
        }

        public void setFrozen(double frozen) {
            this.frozen = frozen;
        }

        public double getBorrow() {
            return borrow;
        }

        public void setBorrow(double borrow) {
            this.borrow = borrow;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public static class SellCoinBean {
        /**
         * total : 1
         * frozen : 0
         * borrow : 0
         * id : 1
         */

        private double total;
        private double frozen;
        private double borrow;
        private long id;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getFrozen() {
            return frozen;
        }

        public void setFrozen(double frozen) {
            this.frozen = frozen;
        }

        public double getBorrow() {
            return borrow;
        }

        public void setBorrow(double borrow) {
            this.borrow = borrow;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
