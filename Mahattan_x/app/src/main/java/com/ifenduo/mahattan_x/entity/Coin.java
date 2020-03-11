package com.ifenduo.mahattan_x.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by ll on 2018/4/11.
 */

public class Coin implements Parcelable {
    private long id;
    private long sortId;
    private int type;
    private int status;
    private long buyCoinId;
    private String buySymbol;
    private String buyName;
    private String buyShortName;
    private String buyWebLogo;
    private String buyAppLogo;
    private long sellCoinId;
    private String sellSymbol;
    private String sellName;
    private String sellShortName;
    private String sellWebLogo;
    private String sellAppLogo;
    private boolean isShare;
    private boolean isStop;
    private long openTime;
    private long stopTime;
    private double buyFee;
    private double sellFee;
    private long remoteId;
    private double priceWave;
    private double priceRange;
    private BigDecimal minCount;
    private BigDecimal maxCount;
    private double minPrice;
    private double maxPrice;
    private String amountOffset;
    private String priceOffset;
    private String digit;
    private double openPrice;
    private long agentId;
    private long gmtCreate;
    private long gmtModified;
    private double version;
    private String typeName;
    private String coin;
    private String blockName;

    private CoinBean coinBean;
    private String thumb;
    private String balance;
    private String app_logo;

    public static Coin objectFromData(String str) {

        return new Gson().fromJson(str, Coin.class);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getBuyCoinId() {
        return buyCoinId;
    }

    public void setBuyCoinId(long buyCoinId) {
        this.buyCoinId = buyCoinId;
    }

    public String getBuySymbol() {
        return buySymbol;
    }

    public void setBuySymbol(String buySymbol) {
        this.buySymbol = buySymbol;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getBuyShortName() {
        return buyShortName;
    }

    public void setBuyShortName(String buyShortName) {
        this.buyShortName = buyShortName;
    }

    public String getBuyWebLogo() {
        return buyWebLogo;
    }

    public void setBuyWebLogo(String buyWebLogo) {
        this.buyWebLogo = buyWebLogo;
    }

    public String getBuyAppLogo() {
        return buyAppLogo;
    }

    public void setBuyAppLogo(String buyAppLogo) {
        this.buyAppLogo = buyAppLogo;
    }

    public long getSellCoinId() {
        return sellCoinId;
    }

    public void setSellCoinId(long sellCoinId) {
        this.sellCoinId = sellCoinId;
    }

    public String getSellSymbol() {
        return sellSymbol;
    }

    public void setSellSymbol(String sellSymbol) {
        this.sellSymbol = sellSymbol;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getSellShortName() {
        return sellShortName;
    }

    public void setSellShortName(String sellShortName) {
        this.sellShortName = sellShortName;
    }

    public String getSellWebLogo() {
        return sellWebLogo;
    }

    public void setSellWebLogo(String sellWebLogo) {
        this.sellWebLogo = sellWebLogo;
    }

    public String getSellAppLogo() {
        return sellAppLogo;
    }

    public void setSellAppLogo(String sellAppLogo) {
        this.sellAppLogo = sellAppLogo;
    }

    public boolean isIsShare() {
        return isShare;
    }

    public void setIsShare(boolean isShare) {
        this.isShare = isShare;
    }

    public boolean isIsStop() {
        return isStop;
    }

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public double getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(double buyFee) {
        this.buyFee = buyFee;
    }

    public double getSellFee() {
        return sellFee;
    }

    public void setSellFee(double sellFee) {
        this.sellFee = sellFee;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }

    public double getPriceWave() {
        return priceWave;
    }

    public void setPriceWave(double priceWave) {
        this.priceWave = priceWave;
    }

    public double getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(double priceRange) {
        this.priceRange = priceRange;
    }

    public int getMinCount() {
        return minCount.intValue();
    }

    public void setMinCount(int minCount) {
        this.minCount = BigDecimal.valueOf(minCount);
    }

    public int getMaxCount() {
        return maxCount.intValue();
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = BigDecimal.valueOf(maxCount);
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getAmountOffset() {
        return amountOffset;
    }

    public void setAmountOffset(String amountOffset) {
        this.amountOffset = amountOffset;
    }

    public String getPriceOffset() {
        return priceOffset;
    }

    public void setPriceOffset(String priceOffset) {
        this.priceOffset = priceOffset;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public CoinBean getCoinBean() {
        return coinBean;
    }

    public void setCoinBean(CoinBean coinBean) {
        this.coinBean = coinBean;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public static class CoinBean implements Parcelable {

        /**
         * symbol :
         * sellSymbol : LTC
         * total : 0
         * buySymbol : BTC
         * p_open : 0.00955
         * p_new : 0.00955
         * buy : 0
         * sell : 0
         */

        private String symbol;
        private String sellSymbol;
        private double total;
        private String buySymbol;
        private double p_open;
        private double p_new;
        private double buy;
        private double sell;
        private double chg;
        private String tradeId;
        private String sellAppLogo;
        private String is_collect;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getSellSymbol() {
            return sellSymbol;
        }

        public void setSellSymbol(String sellSymbol) {
            this.sellSymbol = sellSymbol;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public String getBuySymbol() {
            return buySymbol;
        }

        public void setBuySymbol(String buySymbol) {
            this.buySymbol = buySymbol;
        }

        public double getP_open() {
            return p_open;
        }

        public void setP_open(double p_open) {
            this.p_open = p_open;
        }

        public double getP_new() {
            return p_new;
        }

        public void setP_new(double p_new) {
            this.p_new = p_new;
        }

        public double getBuy() {
            return buy;
        }

        public void setBuy(double buy) {
            this.buy = buy;
        }

        public double getSell() {
            return sell;
        }

        public void setSell(double sell) {
            this.sell = sell;
        }

        public String getTradeId() {
            if(TextUtils.isEmpty(tradeId)){
                return "-1";
            }
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getSellAppLogo() {
            return sellAppLogo;
        }

        public void setSellAppLogo(String sellAppLogo) {
            this.sellAppLogo = sellAppLogo;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public boolean isCollect() {
            return "1".equals(getIs_collect());
        }

        public double getChg() {
            return chg;
        }

        public void setChg(double chg) {
            this.chg = chg;
        }

        public CoinBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.symbol);
            dest.writeString(this.sellSymbol);
            dest.writeDouble(this.total);
            dest.writeString(this.buySymbol);
            dest.writeDouble(this.p_open);
            dest.writeDouble(this.p_new);
            dest.writeDouble(this.buy);
            dest.writeDouble(this.sell);
            dest.writeDouble(this.chg);
            dest.writeString(this.tradeId);
            dest.writeString(this.sellAppLogo);
            dest.writeString(this.is_collect);
        }

        protected CoinBean(Parcel in) {
            this.symbol = in.readString();
            this.sellSymbol = in.readString();
            this.total = in.readDouble();
            this.buySymbol = in.readString();
            this.p_open = in.readDouble();
            this.p_new = in.readDouble();
            this.buy = in.readDouble();
            this.sell = in.readDouble();
            this.chg = in.readDouble();
            this.tradeId = in.readString();
            this.sellAppLogo = in.readString();
            this.is_collect = in.readString();
        }

        public static final Creator<CoinBean> CREATOR = new Creator<CoinBean>() {
            @Override
            public CoinBean createFromParcel(Parcel source) {
                return new CoinBean(source);
            }

            @Override
            public CoinBean[] newArray(int size) {
                return new CoinBean[size];
            }
        };
    }

    public Coin() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.sortId);
        dest.writeInt(this.type);
        dest.writeInt(this.status);
        dest.writeLong(this.buyCoinId);
        dest.writeString(this.buySymbol);
        dest.writeString(this.buyName);
        dest.writeString(this.buyShortName);
        dest.writeString(this.buyWebLogo);
        dest.writeString(this.buyAppLogo);
        dest.writeLong(this.sellCoinId);
        dest.writeString(this.sellSymbol);
        dest.writeString(this.sellName);
        dest.writeString(this.sellShortName);
        dest.writeString(this.sellWebLogo);
        dest.writeString(this.sellAppLogo);
        dest.writeByte(this.isShare ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isStop ? (byte) 1 : (byte) 0);
        dest.writeLong(this.openTime);
        dest.writeLong(this.stopTime);
        dest.writeDouble(this.buyFee);
        dest.writeDouble(this.sellFee);
        dest.writeLong(this.remoteId);
        dest.writeDouble(this.priceWave);
        dest.writeDouble(this.priceRange);
        dest.writeSerializable(this.minCount);
        dest.writeSerializable(this.maxCount);
        dest.writeDouble(this.minPrice);
        dest.writeDouble(this.maxPrice);
        dest.writeString(this.amountOffset);
        dest.writeString(this.priceOffset);
        dest.writeString(this.digit);
        dest.writeDouble(this.openPrice);
        dest.writeLong(this.agentId);
        dest.writeLong(this.gmtCreate);
        dest.writeLong(this.gmtModified);
        dest.writeDouble(this.version);
        dest.writeString(this.typeName);
        dest.writeString(this.coin);
        dest.writeString(this.blockName);
        dest.writeParcelable(this.coinBean, flags);
        dest.writeString(this.thumb);
        dest.writeString(this.balance);
        dest.writeString(this.app_logo);
    }

    protected Coin(Parcel in) {
        this.id = in.readLong();
        this.sortId = in.readLong();
        this.type = in.readInt();
        this.status = in.readInt();
        this.buyCoinId = in.readLong();
        this.buySymbol = in.readString();
        this.buyName = in.readString();
        this.buyShortName = in.readString();
        this.buyWebLogo = in.readString();
        this.buyAppLogo = in.readString();
        this.sellCoinId = in.readLong();
        this.sellSymbol = in.readString();
        this.sellName = in.readString();
        this.sellShortName = in.readString();
        this.sellWebLogo = in.readString();
        this.sellAppLogo = in.readString();
        this.isShare = in.readByte() != 0;
        this.isStop = in.readByte() != 0;
        this.openTime = in.readLong();
        this.stopTime = in.readLong();
        this.buyFee = in.readDouble();
        this.sellFee = in.readDouble();
        this.remoteId = in.readLong();
        this.priceWave = in.readDouble();
        this.priceRange = in.readDouble();
        this.minCount = (BigDecimal) in.readSerializable();
        this.maxCount = (BigDecimal) in.readSerializable();
        this.minPrice = in.readDouble();
        this.maxPrice = in.readDouble();
        this.amountOffset = in.readString();
        this.priceOffset = in.readString();
        this.digit = in.readString();
        this.openPrice = in.readDouble();
        this.agentId = in.readLong();
        this.gmtCreate = in.readLong();
        this.gmtModified = in.readLong();
        this.version = in.readDouble();
        this.typeName = in.readString();
        this.coin = in.readString();
        this.blockName = in.readString();
        this.coinBean = in.readParcelable(CoinBean.class.getClassLoader());
        this.thumb = in.readString();
        this.balance = in.readString();
        this.app_logo = in.readString();
    }

    public static final Creator<Coin> CREATOR = new Creator<Coin>() {
        @Override
        public Coin createFromParcel(Parcel source) {
            return new Coin(source);
        }

        @Override
        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };
}
