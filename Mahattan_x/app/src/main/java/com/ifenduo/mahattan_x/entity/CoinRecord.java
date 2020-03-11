package com.ifenduo.mahattan_x.entity;

import android.text.TextUtils;

import java.math.BigDecimal;

public class CoinRecord {

    /**
     * fid : 1
     * uid : null
     * relationId : 1
     * relationCoinId : 52
     * relationCoinName : ETH
     * amount : 10
     * operation : 1
     * operationDesc : 充值
     * status : 1
     * statusDesc : 成功
     * createDate : 1537268778377
     * updateDate : 1537268778377
     * txId : 0X00000000000000
     * rechargeAddress : 0x111111
     * withdrawAddress : 0x111111
     * memo : test
     * walletOperationDate : 1537268778377
     * fee : 1
     */

//    In(1, "充值"),
//    Out(2, "提现"),
//    Transfer(3,"转账"),
//    Receive(4,"收款"),
//    Other(6,"其他");

    private String fid;
    private String uid;
    private String relationId;
    private String relationCoinId;
    private String relationCoinName;
    private BigDecimal amount;
    private int operation;
    private String operationDesc;
    private int status;
    private String statusDesc;
    private long createDate;
    private long updateDate;
    private String txId;
    private String rechargeAddress;
    private String withdrawAddress;
    private String memo;
    private long walletOperationDate;
    private BigDecimal fee;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationCoinId() {
        return relationCoinId;
    }

    public void setRelationCoinId(String relationCoinId) {
        this.relationCoinId = relationCoinId;
    }

    public String getRelationCoinName() {
        return relationCoinName;
    }

    public void setRelationCoinName(String relationCoinName) {
        this.relationCoinName = relationCoinName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getOperationDesc() {
        if(operation==1){
            return "转入-虚拟币充值";
        }else if(operation==2){
            return "转出-虚拟币提现";
        }else if(operation==3){
            return "转出-平台内部转账";
        }else if(operation==4){
            return "转入-平台内部接受转账";
        }else if(operation==5){
            return "充值-人民币钱包充值";
        }else if(operation==7){
            return "提现-人民币钱包提现";
        }else if(operation==8){
            return "充值-后台手动充值";
        }else {
            return "其他-其他操作";
        }
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        if(!TextUtils.isEmpty(statusDesc)&&statusDesc.equals("取消")){
            return "失败";
        }
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public String getWithdrawAddress() {
        return withdrawAddress;
    }

    public void setWithdrawAddress(String withdrawAddress) {
        this.withdrawAddress = withdrawAddress;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getWalletOperationDate() {
        return walletOperationDate;
    }

    public void setWalletOperationDate(long walletOperationDate) {
        this.walletOperationDate = walletOperationDate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
