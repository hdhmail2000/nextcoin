package com.ifenduo.mahattan_x.api;


import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.Balance;
import com.ifenduo.mahattan_x.entity.BankCard;
import com.ifenduo.mahattan_x.entity.BankCardType;
import com.ifenduo.mahattan_x.entity.Banner;
import com.ifenduo.mahattan_x.entity.C2CCoinType;
import com.ifenduo.mahattan_x.entity.C2COrderListEntity;
import com.ifenduo.mahattan_x.entity.C2CPayAccountInfo;
import com.ifenduo.mahattan_x.entity.C2CReleaseDetailEntity;
import com.ifenduo.mahattan_x.entity.C2CTransationPriceEntity;
import com.ifenduo.mahattan_x.entity.C2cTransactionEntity;
import com.ifenduo.mahattan_x.entity.CalculationRecord;
import com.ifenduo.mahattan_x.entity.CalculationTask;
import com.ifenduo.mahattan_x.entity.ChargeRecord;
import com.ifenduo.mahattan_x.entity.Coin;
import com.ifenduo.mahattan_x.entity.CoinRecord;
import com.ifenduo.mahattan_x.entity.DepthEntity;
import com.ifenduo.mahattan_x.entity.ExchangeRate;
import com.ifenduo.mahattan_x.entity.GoogleKeyInfo;
import com.ifenduo.mahattan_x.entity.Help;
import com.ifenduo.mahattan_x.entity.HideCoinEntity;
import com.ifenduo.mahattan_x.entity.LineChartEntity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.LoginLog;
import com.ifenduo.mahattan_x.entity.MineAreaStatus;
import com.ifenduo.mahattan_x.entity.MiningArea;
import com.ifenduo.mahattan_x.entity.MiningRecord;
import com.ifenduo.mahattan_x.entity.MoneyOfCoinEntity;
import com.ifenduo.mahattan_x.entity.MyRelease;
import com.ifenduo.mahattan_x.entity.OrderEntity;
import com.ifenduo.mahattan_x.entity.PersonalInfo;
import com.ifenduo.mahattan_x.entity.RechargeAddressAndRecord;
import com.ifenduo.mahattan_x.entity.RechargeRecordEntity;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.entity.TradingArea;
import com.ifenduo.mahattan_x.entity.USDTWithdrawRecord;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.entity.WithdrawAddressInfo;
import com.ifenduo.mahattan_x.entity.WithdrawSetting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxuefeng on 16/8/16.
 */
public class Api implements
        IApi {

    private volatile static Api sInstance;

    private IApi mProxyApi;

    private Api() {
        mProxyApi = new OkHttpApi();
    }

    public static Api getInstance() {
        if (sInstance == null) {
            synchronized (Api.class) {
                if (sInstance == null) {
                    sInstance = new Api();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setTag(Object tag) {
    }

    @Override
    public void cancel(Object tag) {
        mProxyApi.cancel(tag);
    }

    /**
     * 钱包
     *
     * @param appKey
     * @param appSecret
     * @param callback
     */
    @Override
    public void fetchBalance(String appKey, String appSecret, Callback<Balance> callback) {
        mProxyApi.fetchBalance(appKey, appSecret, callback);
    }

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @param type    1：手机  2：邮箱
     */
    @Override
    public void submitLogin(String account, String pwd, String area, int type, Callback<LoginInfo> callback) {
        mProxyApi.submitLogin(account, pwd, area, type, callback);
    }

    /**
     * 获取谷歌验证码秘钥
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchGoogleDevice(String appKey, Callback<GoogleKeyInfo> callback) {
        mProxyApi.fetchGoogleDevice(appKey, callback);
    }

    /**
     * 登录日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    @Override
    public void fetchLoginLog(String appKey, int page, Callback<LoginLog> callback) {
        mProxyApi.fetchLoginLog(appKey, page, callback);
    }

    /**
     * 安全设置日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    @Override
    public void fetchSafeLog(String appKey, int page, Callback<LoginLog> callback) {
        mProxyApi.fetchSafeLog(appKey, page, callback);
    }

    /**
     * 添加谷歌验证码秘钥
     *
     * @param appKey
     * @param googleCode
     * @param googleKey
     * @param callback
     */
    @Override
    public void submitBindGoogleCode(String appKey, String appSecret, String googleCode, String googleKey, Callback<BaseEntity> callback) {
        mProxyApi.submitBindGoogleCode(appKey, appSecret, googleCode, googleKey, callback);
    }

    /**
     * 获取安全设置详情
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchSafeSettingDetail(String appKey, Callback<SafeSettingEntity> callback) {
        mProxyApi.fetchSafeSettingDetail(appKey, callback);
    }

    /**
     * 获取虚拟币充值地址和近十次充值记录
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchRechargeAddressAndRecord(String appKey, String coinId, Callback<RechargeAddressAndRecord> callback) {
        mProxyApi.fetchRechargeAddressAndRecord(appKey, coinId, callback);
    }

    /**
     * 修改/绑定，登录和交易密码
     *
     * @param appKey
     * @param appSecret
     * @param oldPassword
     * @param password
     * @param password_
     * @param messageCode
     * @param googleCode
     * @param idCard
     * @param pwdType
     * @param callback
     */
    @Override
    public void submitUpdatePassword(String appKey, String appSecret, String oldPassword, String password, String password_, String messageCode, String googleCode, String idCard, int pwdType, Callback<BaseEntity> callback) {
        mProxyApi.submitUpdatePassword(appKey, appSecret, oldPassword, password, password_, messageCode, googleCode, idCard, pwdType, callback);
    }

    /**
     * 找回交易密码
     *
     * @param area
     * @param phone
     * @param code
     * @param googleCode
     * @param password
     * @param password_
     * @param token
     */
    @Override
    public void submitFindTransPwd(String area, String phone, String code, String googleCode, String password, String password_, String token, Callback<BaseEntity> callback) {
        mProxyApi.submitFindTransPwd(area, phone, code, googleCode, password, password_, token, callback);
    }

    /**
     * 重置登录密码
     *
     * @param phone
     * @param area
     * @param code
     * @param googleCode
     * @param password
     * @param password_
     * @param callback
     */
    @Override
    public void submitResetLoginPassword(String phone, String area, String code, String googleCode, String password, String password_, Callback<BaseEntity> callback) {
        mProxyApi.submitResetLoginPassword(phone, area, code, googleCode, password, password_, callback);
    }

    /**
     * 发送绑定手机的短信
     *
     * @param appKey
     * @param area
     * @param phone
     * @param callback
     */
    @Override
    public void sendPhoneMessage(String appKey, String area, String phone, Callback<BaseEntity> callback) {
        mProxyApi.sendPhoneMessage(appKey, area, phone, callback);
    }

    /**
     * 发送绑定手机的短信(已绑定手机号 需要签名)
     *
     * @param appKey
     * @param appSecret
     * @param type
     * @param callback
     */
    @Override
    public void sendPhoneMessage(String appKey, String appSecret, int type, Callback<BaseEntity> callback) {
        mProxyApi.sendPhoneMessage(appKey, appSecret, type, callback);
    }

    /**
     * 发送不需要签名的短信 POST (非签名)
     *
     * @param area
     * @param phone
     * @param type     112手机端注册、102绑定手机
     * @param callback
     */
    @Override
    public void sendPhoneMessageNotSigntrue(String area, String phone, int type, Callback<BaseEntity> callback) {
        mProxyApi.sendPhoneMessageNotSigntrue(area, phone, type, callback);
    }

    /**
     * 实名认证
     *
     * @param appKey
     * @param appSecret
     * @param name
     * @param identityNo
     * @param identityType
     * @param idCardZmImgURL
     * @param idCardFmImgURL
     * @param idCardScImgURL
     * @param callback
     */
    @Override
    public void submitRealAuth(String appKey, String appSecret, String name, String identityNo,
                               String identityType, String idCardZmImgURL, String idCardFmImgURL, String idCardScImgURL, final Callback<BaseEntity> callback) {
        mProxyApi.submitRealAuth(appKey, appSecret, name, identityNo, identityType, idCardZmImgURL, idCardFmImgURL, idCardScImgURL, callback);
    }

    /**
     * 获取已有的提现地址
     *
     * @param token
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchWithdrawAddress(String token, String symbol, Callback<WithdrawAddressInfo> callback) {
        mProxyApi.fetchWithdrawAddress(token, symbol, callback);
    }

    /**
     * 添加提现地址
     *
     * @param appKey
     * @param appSecret
     * @param symbol
     * @param phoneCode
     * @param googleCode
     * @param password
     * @param address
     * @param remark
     * @param callback
     */
    @Override
    public void submitWithdrawAddress(String appKey, String appSecret, String symbol, String phoneCode,
                                      String googleCode, String password, String address, String remark, Callback<BaseEntity> callback) {
        mProxyApi.submitWithdrawAddress(appKey, appSecret, symbol, phoneCode, googleCode, password, address, remark, callback);
    }

    /**
     * 虚拟币提现
     *
     * @param appKey
     * @param appSecret
     * @param symbol
     * @param addressId
     * @param address
     * @param withdrawAmount
     * @param tradePwd
     * @param googleCode
     * @param phoneCode
     * @param btcfeesIndex   默认0
     */
    @Override
    public void submitWithdrawCoin(String appKey, String appSecret, String symbol, String addressId,
                                   String address, String withdrawAmount, String tradePwd, String googleCode,
                                   String phoneCode, String btcfeesIndex, Callback<BaseEntity> callback) {
        mProxyApi.submitWithdrawCoin(appKey, appSecret, symbol, addressId, address, withdrawAmount, tradePwd, googleCode, phoneCode, btcfeesIndex, callback);
    }

    /**
     * 绑定手机
     *
     * @param appKey
     * @param area
     * @param phone
     * @param code
     * @param callback
     */
    @Override
    public void bindPhone(String appKey, String area, String phone, String code, Callback<BaseEntity> callback) {
        mProxyApi.bindPhone(appKey, area, phone, code, callback);
    }

    /**
     * 手机注册 POST (非签名)
     *
     * @param area
     * @param account
     * @param regType
     * @param pCode
     * @param eCode
     * @param password
     * @param introCode
     * @param callback
     */
    @Override
    public void submitRegister(String area, String account, String regType, String pCode, String eCode, String password, String introCode, Callback<BaseEntity> callback) {
        mProxyApi.submitRegister(area, account, regType, pCode, eCode, password, introCode, callback);
    }

    /**
     * K线数据
     *
     * @param symbol
     * @param step
     * @param callback
     */
    @Override
    public void fetchKLine(String symbol, String step, Callback<List<List<BigDecimal>>> callback) {
        mProxyApi.fetchKLine(symbol, step, callback);
    }

    /**
     * 获取交易区
     *
     * @param callback
     */
    @Override
    public void fetchTradingArea(Callback<List<TradingArea>> callback) {
        mProxyApi.fetchTradingArea(callback);
    }

    /**
     * 交易区币种列表
     *
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchTradingAreaCoinList(long symbol, Callback<List<Coin>> callback) {
        mProxyApi.fetchTradingAreaCoinList(symbol, callback);
    }

    /**
     * 交易区币种列表（币的信息）
     *
     * @param symbols
     * @param callback
     */
    @Override
    public void fetchTradingAreaCoinInfo(String symbols, Callback<List<Coin.CoinBean>> callback) {
        mProxyApi.fetchTradingAreaCoinInfo(symbols, callback);
    }

    /**
     * 获取自选列表
     *
     * @param fid
     * @param callback
     */
    @Override
    public void fetchSelfChooseCoin(String fid, Callback<List<Coin.CoinBean>> callback) {
        mProxyApi.fetchSelfChooseCoin(fid, callback);
    }

    /**
     * 获取交易对的用户可用资产
     *
     * @param symbols
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchMoneyOfCoin(String symbols, String appKey, String appSecret, Callback<MoneyOfCoinEntity> callback) {
        mProxyApi.fetchMoneyOfCoin(symbols, appKey, appSecret, callback);
    }

    /**
     * 深度、盘口
     *
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchDepth(String symbol, Callback<DepthEntity> callback) {
        mProxyApi.fetchDepth(symbol, callback);
    }

    /**
     * 下单
     *
     * @param symbol
     * @param tradeAmount
     * @param tradePrice
     * @param tradePwd
     * @param type        buy 表示买，sell表示卖
     */
    @Override
    public void submitOrder(String appKey, String appSecret, String symbol, String tradeAmount, String tradePrice, String tradePwd, String type, Callback<BaseEntity> callback) {
        mProxyApi.submitOrder(appKey, appSecret, symbol, tradeAmount, tradePrice, tradePwd, type, callback);
    }

    /**
     * 获取订单
     *
     * @param appKey
     * @param appSecret
     * @param symbol
     * @param type
     * @param callback
     */
    @Override
    public void fetchOrder(String appKey, String appSecret, String symbol, String type, Callback<OrderEntity> callback) {
        mProxyApi.fetchOrder(appKey, appSecret, symbol, type, callback);
    }

    /**
     * 取消订单
     *
     * @param appKey
     * @param appSecret
     * @param orderId
     * @param callback
     */
    @Override
    public void cancelOrder(String appKey, String appSecret, String orderId, Callback<BaseEntity> callback) {
        mProxyApi.cancelOrder(appKey, appSecret, orderId, callback);
    }

    /**
     * 充值码兑换
     *
     * @param token
     * @param code
     * @param callback
     */
    @Override
    public void submitCode(String token, String code, Callback<BaseEntity> callback) {
        mProxyApi.submitCode(token, code, callback);
    }

    /**
     * 充值码列表
     *
     * @param token
     * @param callback
     */
    @Override
    public void fetchCodeList(String token, Callback<RechargeRecordEntity> callback) {
        mProxyApi.fetchCodeList(token, callback);
    }

    /**
     * 发送绑定邮件的验证码
     *
     * @param appKey
     * @param emailAddress
     * @param callback
     */
    @Override
    public void sendEmail(String appKey, String emailAddress, Callback<BaseEntity> callback) {
        mProxyApi.sendEmail(appKey, emailAddress, callback);
    }

    /**
     * 绑定邮件
     *
     * @param appKey
     * @param code
     * @param callback
     */
    @Override
    public void bindEmail(String appKey, String code, Callback<BaseEntity> callback) {
        mProxyApi.bindEmail(appKey, code, callback);
    }

    /**
     * 用户信息
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchUserInfo(String appKey, Callback<User> callback) {
        mProxyApi.fetchUserInfo(appKey, callback);
    }

    /**
     * 人民币充值
     *
     * @param appKey
     * @param coinId
     * @param amount
     * @param callback
     */
    @Override
    public void submitCCNYRecharge(String appKey, String coinId, String amount, Callback<String> callback) {
        mProxyApi.submitCCNYRecharge(appKey, coinId, amount, callback);
    }

    /**
     * 汇率
     *
     * @param callback
     */
    @Override
    public void fetchExchangeRate(Callback<ExchangeRate> callback) {
        mProxyApi.fetchExchangeRate(callback);
    }

    /**
     * 自己已经绑定的银行卡
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchBankCardList(String appKey, String coinId, Callback<List<BankCard>> callback) {
        mProxyApi.fetchBankCardList(appKey, coinId, callback);
    }

    /**
     * 获取系统银行卡类型列表
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchBankCardType(String appKey, Callback<List<BankCardType>> callback) {
        mProxyApi.fetchBankCardType(appKey, callback);
    }

    /**
     * 绑定银行卡和人民币提现都需要这个验证码
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void submitBankCardSendMessage(String appKey, Callback<BaseEntity> callback) {
        mProxyApi.submitBankCardSendMessage(appKey, callback);
    }

    /**
     * 绑定银行卡
     *
     * @param appKey
     * @param bankAccount
     * @param cardType
     * @param prov
     * @param city
     * @param area
     * @param name
     * @param address
     * @param code
     * @param googleCode
     * @param callback
     */
    @Override
    public void submitBindBankCard(String appKey, String bankAccount, String cardType, String prov, String city, String area, String name, String address, String code, String googleCode, Callback<BaseEntity> callback) {
        mProxyApi.submitBindBankCard(appKey, bankAccount, cardType, prov, city, area, name, address, code, googleCode, callback);
    }

    /**
     * 获取相关币种的提现手续费
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchWithdrawFee(String appKey, String coinId, Callback<WithdrawSetting> callback) {
        mProxyApi.fetchWithdrawFee(appKey, coinId, callback);
    }

    /**
     * 人民币提现申请
     *
     * @param appKey
     * @param money
     * @param cardId
     * @param password
     * @param code
     * @param googleCode
     * @param coinId
     * @param callback
     */
    @Override
    public void submitGSETWithdraw(String appKey, String money, String cardId, String password, String code, String googleCode, String coinId, Callback<BaseEntity> callback) {
        mProxyApi.submitGSETWithdraw(appKey, money, cardId, password, code, googleCode, coinId, callback);
    }

    /**
     * 人民币提现记录
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchWithdrawRecordList(String appKey, String coinId, Callback<List<USDTWithdrawRecord>> callback) {
        mProxyApi.fetchWithdrawRecordList(appKey, coinId, callback);
    }

    /**
     * 交易记录
     *
     * @param token
     * @param coinID
     * @param callback
     */
    @Override
    public void fetchTransactionRecord(String token, String coinID, Callback<List<CoinRecord>> callback) {
        mProxyApi.fetchTransactionRecord(token, coinID, callback);
    }

    /***************************************************** OSS *****************************************************/
    /**
     * OSS上传图片
     *
     * @param path
     * @param callback
     */
    @Override
    public void ossUploadImage(String path, Callback<String> callback) {
        mProxyApi.ossUploadImage(path, callback);
    }

    /***************************************************** C2C *****************************************************/

    /**
     * 获取C2C币种列表
     *
     * @param callback
     */
    @Override
    public void fetchC2CCoinTypeList(Callback<List<C2CCoinType>> callback) {
        mProxyApi.fetchC2CCoinTypeList(callback);
    }

    /**
     * 发布C2C
     *
     * @param uid
     * @param type
     * @param country
     * @param coinID
     * @param price
     * @param num
     * @param minNum
     * @param maxNum
     * @param payType
     * @param callback
     */
    @Override
    public void submitC2CRelease(String uid, String type, String country, String coinID, String coinName, String price, String num, String minNum, String maxNum, String payType, String pwd, Callback<String> callback) {
        mProxyApi.submitC2CRelease(uid, type, country, coinID, coinName, price, num, minNum, maxNum, payType, pwd, callback);
    }

    /**
     * 获取C2C币的余额
     *
     * @param uid
     * @param CoinID
     * @param callback
     */
    @Override
    public void fetchC2CBalance(String uid, String CoinID, Callback<String> callback) {
        mProxyApi.fetchC2CBalance(uid, CoinID, callback);
    }

    /**
     * 我的发布
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchMyRelease(String uid, int page, Callback<MyRelease> callback) {
        mProxyApi.fetchMyRelease(uid, page, callback);
    }

    /**
     * C2C下线
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitCancelRelease(String uid, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitCancelRelease(uid, id, callback);
    }

    /**
     * C2C发布详情
     *
     * @param type     releaseSell卖出 releaseBuy买入
     * @param uid
     * @param id
     * @param page
     * @param callback
     */
    @Override
    public void fetchC2CReleaseDetail(String type, String uid, String id, int page, Callback<C2CReleaseDetailEntity> callback) {
        mProxyApi.fetchC2CReleaseDetail(type, uid, id, page, callback);
    }

    /**
     * 取消C2C订单
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitCancelC2COrder(String uid, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitCancelC2COrder(uid, id, callback);
    }

    /**
     * 标记为我已付款
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitSetHasPay(String uid, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitSetHasPay(uid, id, callback);
    }

    /**
     * 标记为我已收款
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitSetHasReceved(String uid, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitSetHasReceved(uid, id, callback);
    }

    /**
     * 购买C2C
     *
     * @param uid
     * @param volume
     * @param price
     * @param tradeId
     * @param pwd
     * @param callback
     */
    @Override
    public void submitC2CBuy(String uid, String volume, String price, String tradeId, String pwd, Callback<BaseEntity> callback) {
        mProxyApi.submitC2CBuy(uid, volume, price, tradeId, pwd, callback);
    }

    /**
     * 卖出C2C
     *
     * @param uid
     * @param volume
     * @param price
     * @param tradeId
     * @param pwd
     * @param callback
     */
    @Override
    public void submitC2CSell(String uid, String volume, String price, String tradeId, String pwd, Callback<BaseEntity> callback) {
        mProxyApi.submitC2CSell(uid, volume, price, tradeId, pwd, callback);
    }

    /**
     * C2C卖出、买入账单
     *
     * @param type     类型 1、买入 2、卖出 默认为1
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchC2COrderList(String type, String uid, String page, Callback<C2COrderListEntity> callback) {
        mProxyApi.fetchC2COrderList(type, uid, page, callback);
    }

    /**
     * 获取C2C购买列表
     *
     * @param id
     * @param callback
     */
    @Override
    public void fetchC2CBuyList(int page, String id, Callback<C2cTransactionEntity> callback) {
        mProxyApi.fetchC2CBuyList(page, id, callback);
    }

    /**
     * 获取C2C卖出列表
     *
     * @param page
     * @param id
     * @param callback
     */
    @Override
    public void fetchC2CSellList(int page, String id, Callback<C2cTransactionEntity> callback) {
        mProxyApi.fetchC2CSellList(page, id, callback);
    }

    /**
     * 获取C2C支付账户信息
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchC2CPayAccountInfo(String uid, Callback<C2CPayAccountInfo> callback) {
        mProxyApi.fetchC2CPayAccountInfo(uid, callback);
    }

    /**
     * 添加支付宝账号
     *
     * @param uid
     * @param name
     * @param account
     * @param qrImageUrl
     * @param callback
     */
    @Override
    public void submitAddAlipay(String uid, String name, String account, String qrImageUrl, Callback<BaseEntity> callback) {
        mProxyApi.submitAddAlipay(uid, name, account, qrImageUrl, callback);
    }

    /**
     * 添加微信账号
     *
     * @param uid
     * @param name
     * @param account
     * @param qrImageUrl
     * @param callback
     */
    @Override
    public void submitAddWechat(String uid, String name, String account, String qrImageUrl, Callback<BaseEntity> callback) {
        mProxyApi.submitAddWechat(uid, name, account, qrImageUrl, callback);
    }

    /**
     * 添加银行卡
     *
     * @param uid
     * @param khm
     * @param idCard
     * @param zhhm
     * @param khh
     * @param phone
     * @param callback
     */
    @Override
    public void submitAddBankAccount(String uid, String khm, String idCard, String zhhm, String khh, String phone, Callback<BaseEntity> callback) {
        mProxyApi.submitAddBankAccount(uid, khm, idCard, zhhm, khh, phone, callback);
    }

    /**
     * 删除C2C银行卡、支付宝、微信账号
     *
     * @param uid
     * @param type
     * @param id
     * @param callback
     */
    @Override
    public void submitDeleteC2CAccount(String uid, String type, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitDeleteC2CAccount(uid, type, id, callback);
    }

    /**
     * C2C申诉
     *
     * @param uid
     * @param orderID
     * @param content
     * @param imageBase64
     * @param callback
     */
    @Override
    public void submitC2CApply(String uid, String orderID, String content, String imageBase64, Callback<BaseEntity> callback) {
        mProxyApi.submitC2CApply(uid, orderID, content, imageBase64, callback);
    }

    /**
     * 获取C2C交易价格
     *
     * @param coinName
     * @param callback
     */
    @Override
    public void fetchC2CTransactionPrice(String coinName, Callback<C2CTransationPriceEntity> callback) {
        mProxyApi.fetchC2CTransactionPrice(coinName, callback);
    }

    /**
     * 折线图数据
     *
     * @param coinName
     * @param callback
     */
    @Override
    public void fetchLineChartData(String coinName, String time, Callback<List<LineChartEntity>> callback) {
        mProxyApi.fetchLineChartData(coinName, time, callback);
    }

    /**
     * 帮助中心详情
     *
     * @param catid
     * @param callback
     */
    @Override
    public void fetchHelpCenterDetailList(String catid, Callback<List<Help>> callback) {
        mProxyApi.fetchHelpCenterDetailList(catid, callback);
    }

    /**
     * 帮助中心
     *
     * @param callback
     */
    @Override
    public void fetchHelpCenterList(Callback<List<Help>> callback) {
        mProxyApi.fetchHelpCenterList(callback);
    }


    /**
     * PHP端注册JAVA端注册成功过后调用
     *
     * @param account
     * @param password
     * @param password_
     * @param introCode
     * @param callback
     */
    @Override
    public void submitRegisterPHP(String account, String password, String password_, String introCode, Callback<BaseEntity> callback) {
        mProxyApi.submitRegisterPHP(account, password, password_, introCode, callback);
    }

    /**
     * 算力记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchCalculationRecord(String uid, int page, Callback<List<CalculationRecord>> callback) {
        mProxyApi.fetchCalculationRecord(uid, page, callback);
    }

    /**
     * 算力任务
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchCalculationTask(String uid, Callback<CalculationTask> callback) {
        mProxyApi.fetchCalculationTask(uid, callback);
    }

    /**
     * 矿区首页
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchMineAreaStatus(String uid, Callback<List<MineAreaStatus>> callback) {
        mProxyApi.fetchMineAreaStatus(uid, callback);
    }

    /**
     * 挖矿
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitMining(String uid, String id, Callback<BaseEntity> callback) {
        mProxyApi.submitMining(uid, id, callback);
    }

    /**
     * 挖矿记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchMiningRecord(String uid, int page, Callback<List<MiningRecord>> callback) {
        mProxyApi.fetchMiningRecord(uid, page, callback);
    }

    /**
     * 开矿区
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchMiningArea(String uid, Callback<MiningArea> callback) {
        mProxyApi.fetchMiningArea(uid, callback);
    }

    /**
     * 一键复投
     *
     * @param uid
     * @param type
     * @param isOpen
     * @param callback
     */
    @Override
    public void submitOneKey(String uid, String type, boolean isOpen, Callback<BaseEntity> callback) {
        mProxyApi.submitOneKey(uid, type, isOpen, callback);
    }

    /**
     * 开矿区投入接口
     *
     * @param uid
     * @param type
     * @param num
     * @param coinID
     * @param callback
     */
    @Override
    public void submitPutMiningArea(String uid, String type, String num, String coinID, Callback<BaseEntity> callback) {
        mProxyApi.submitPutMiningArea(uid, type, num, coinID, callback);
    }

    /**
     * 首页banner接口
     *
     * @param callback
     */
    @Override
    public void fetchBannerList(Callback<List<Banner>> callback) {
        mProxyApi.fetchBannerList(callback);
    }

    /**
     * 隐藏币种
     *
     * @param uid
     * @param coinName
     * @param callback
     */
    @Override
    public void submitHideCoin(String uid, String coinName, Callback<BaseEntity> callback) {
        mProxyApi.submitHideCoin(uid, coinName, callback);
    }

    /**
     * 隐藏币种列表
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchHideCoinList(String uid, Callback<List<HideCoinEntity>> callback) {
        mProxyApi.fetchHideCoinList(uid, callback);
    }

    /***
     * 判断交易是否收藏
     * @param fid
     * @param treadId
     * @param callback
     */
    @Override
    public void fetchIsCollectTrading(String fid, String treadId, Callback<Coin.CoinBean> callback) {
        mProxyApi.fetchIsCollectTrading(fid, treadId, callback);
    }

    /**
     * 收藏交易
     *
     * @param fid
     * @param tradeId
     * @param callback
     */
    @Override
    public void submitCollectTrading(String fid, String tradeId, Callback<BaseEntity> callback) {
        mProxyApi.submitCollectTrading(fid, tradeId, callback);
    }

    /**
     * 修改用户信息
     *
     * @param uid
     * @param avatarUrl
     * @param nick
     * @param sex
     * @param phone
     * @param callback
     */
    @Override
    public void updatePersonalInfo(String uid, String avatarUrl, String nick, String sex, String phone, Callback<BaseEntity> callback) {
        mProxyApi.updatePersonalInfo(uid, avatarUrl, nick, sex, phone, callback);
    }

    /**
     * 获取本人信息接口
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchPersonalInfo(String uid, Callback<PersonalInfo> callback) {
        mProxyApi.fetchPersonalInfo(uid, callback);
    }
}
