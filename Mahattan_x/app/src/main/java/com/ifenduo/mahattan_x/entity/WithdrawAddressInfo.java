package com.ifenduo.mahattan_x.entity;


import java.util.List;

public class WithdrawAddressInfo {
   private List<RechargeAddress> withdrawAddress;
   private String appLogo;

    public List<RechargeAddress> getWithdrawAddress() {
        return withdrawAddress;
    }

    public void setWithdrawAddress(List<RechargeAddress> withdrawAddress) {
        this.withdrawAddress = withdrawAddress;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }
}
