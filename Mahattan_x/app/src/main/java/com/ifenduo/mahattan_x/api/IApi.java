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
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.entity.WithdrawAddressInfo;
import com.ifenduo.mahattan_x.entity.WithdrawSetting;
import com.ifenduo.mahattan_x.entity.USDTWithdrawRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yangxuefeng on 16/8/16.
 */
public interface IApi {

    void setTag(Object tag);

    void cancel(Object tag);

    /**
     * 钱包
     *
     * @param appKey
     * @param appSecret
     * @param callback
     */
    void fetchBalance(String appKey, String appSecret, Callback<Balance> callback);

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @param type    1：手机  2：邮箱
     */
    void submitLogin(String account, String pwd, String area, int type, Callback<LoginInfo> callback);

    /**
     * 获取谷歌验证码秘钥
     *
     * @param appKey
     * @param callback
     */
    void fetchGoogleDevice(String appKey, Callback<GoogleKeyInfo> callback);

    /**
     * 添加谷歌验证码秘钥
     *
     * @param appKey
     * @param googleCode
     * @param googleKey
     * @param callback
     */
    void submitBindGoogleCode(String appKey, String appSecret, String googleCode, String googleKey, Callback<BaseEntity> callback);

    /**
     * 登录日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    void fetchLoginLog(String appKey, int page, Callback<LoginLog> callback);


    /**
     * 安全设置日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    void fetchSafeLog(String appKey, int page, Callback<LoginLog> callback);

    /**
     * 获取安全设置详情
     *
     * @param appKey
     * @param callback
     */
    void fetchSafeSettingDetail(String appKey, Callback<SafeSettingEntity> callback);

    /**
     * 获取虚拟币充值地址和近十次充值记录
     */
    void fetchRechargeAddressAndRecord(String appKey, String coinId, Callback<RechargeAddressAndRecord> callback);

    /**
     * 修改/绑定，登录和交易密码
     */
    void submitUpdatePassword(String appKey, String appSecret, String oldPassword, String password,
                              String password_, String messageCode, String googleCode, String idCard,
                              int pwdType, Callback<BaseEntity> callback);

    /**
     * 找回交易密码
     */
    void submitFindTransPwd(String area, String phone, String code, String googleCode, String password, String password_, String token, Callback<BaseEntity> callback);

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
    void submitResetLoginPassword(String phone, String area, String code, String googleCode, String password, String password_, Callback<BaseEntity> callback);

    /**
     * 发送绑定手机的短信
     */
    void sendPhoneMessage(String appKey, String area, String phone, Callback<BaseEntity> callback);

    /**
     * 发送绑定手机的短信(已绑定手机号 需要签名)
     *
     * @param appKey
     * @param appSecret
     * @param type
     * @param callback
     */
    void sendPhoneMessage(String appKey, String appSecret, int type, Callback<BaseEntity> callback);

    /**
     * 发送不需要签名的短信 POST (非签名)
     *
     * @param area
     * @param phone
     * @param type     112手机端注册、102绑定手机
     * @param callback
     */
    void sendPhoneMessageNotSigntrue(String area, String phone, int type, Callback<BaseEntity> callback);

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
    void submitRealAuth(String appKey, String appSecret, String name, String identityNo,
                        String identityType, String idCardZmImgURL, String idCardFmImgURL, String idCardScImgURL, final Callback<BaseEntity> callback);

    /**
     * 获取已有的提现地址
     *
     * @param token
     * @param symbol
     * @param callback
     */
    void fetchWithdrawAddress(String token, String symbol, Callback<WithdrawAddressInfo> callback);

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
    void submitWithdrawAddress(String appKey, String appSecret, String symbol, String phoneCode,
                               String googleCode, String password, String address, String remark, Callback<BaseEntity> callback);


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
    void submitWithdrawCoin(String appKey, String appSecret, String symbol, String addressId, String address,
                            String withdrawAmount, String tradePwd, String googleCode, String phoneCode, String btcfeesIndex, Callback<BaseEntity> callback);

    /**
     * 绑定手机
     *
     * @param appKey
     * @param area
     * @param phone
     * @param code
     * @param callback
     */
    void bindPhone(String appKey, String area, String phone, String code, Callback<BaseEntity> callback);

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
    void submitRegister(String area, String account, String regType, String pCode, String eCode, String password, String introCode, Callback<BaseEntity> callback);

    /**
     * K线数据
     *
     * @param symbol
     * @param step
     * @param callback
     */
    void fetchKLine(String symbol, String step, Callback<List<List<BigDecimal>>> callback);

    /**
     * 获取交易区
     *
     * @param callback
     */
    void fetchTradingArea(Callback<List<TradingArea>> callback);

    /**
     * 交易区币种列表
     *
     * @param symbol
     * @param callback
     */
    void fetchTradingAreaCoinList(long symbol, Callback<List<Coin>> callback);

    /**
     * 交易区币种列表（币的信息）
     *
     * @param symbols
     * @param callback
     */
    void fetchTradingAreaCoinInfo(String symbols, Callback<List<Coin.CoinBean>> callback);

    /**
     * 获取自选列表
     *
     * @param fid
     * @param callback
     */
    void fetchSelfChooseCoin(String fid, Callback<List<Coin.CoinBean>> callback);

    /**
     * 获取交易对的用户可用资产
     *
     * @param symbols
     * @param appKey
     * @param callback
     */
    void fetchMoneyOfCoin(String symbols, String appKey, String appSecret, Callback<MoneyOfCoinEntity> callback);

    /**
     * 深度、盘口
     *
     * @param symbol
     * @param callback
     */
    void fetchDepth(String symbol, Callback<DepthEntity> callback);

    /**
     * 下单
     *
     * @param symbol
     * @param tradeAmount
     * @param tradePrice
     * @param tradePwd
     * @param type        buy 表示买，sell表示卖
     */
    void submitOrder(String appKey, String appSecret, String symbol, String tradeAmount, String tradePrice, String tradePwd, String type, Callback<BaseEntity> callback);

    /**
     * 获取订单
     *
     * @param appKey
     * @param appSecret
     * @param symbol
     * @param type
     * @param callback
     */
    void fetchOrder(String appKey, String appSecret, String symbol, String type, Callback<OrderEntity> callback);

    /**
     * 取消定单
     *
     * @param appKey
     * @param appSecret
     * @param orderId
     * @param callback
     */
    void cancelOrder(String appKey, String appSecret, String orderId, Callback<BaseEntity> callback);

    /**
     * 充值码兑换
     *
     * @param token
     * @param code
     * @param callback
     */
    void submitCode(String token, String code, Callback<BaseEntity> callback);

    /**
     * 充值码兑换列表
     *
     * @param token
     * @param callback
     */
    void fetchCodeList(String token, Callback<RechargeRecordEntity> callback);

    /**
     * 发送绑定邮件的验证码
     *
     * @param appKey
     * @param emailAddress
     * @param callback
     */
    void sendEmail(String appKey, String emailAddress, Callback<BaseEntity> callback);

    /**
     * 绑定邮件
     *
     * @param appKey
     * @param code
     * @param callback
     */
    void bindEmail(String appKey, String code, Callback<BaseEntity> callback);

    /**
     * 用户信息
     *
     * @param appKey
     * @param callback
     */
    void fetchUserInfo(String appKey, Callback<User> callback);

    /**
     * 人民币充值
     *
     * @param appKey
     * @param coinId
     * @param amount
     * @param callback
     */
    void submitCCNYRecharge(String appKey, String coinId, String amount, Callback<String> callback);

    /**
     * 汇率
     *
     * @param callback
     */
    void fetchExchangeRate(Callback<ExchangeRate> callback);

    /**
     * 自己已经绑定的银行卡
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    void fetchBankCardList(String appKey, String coinId, Callback<List<BankCard>> callback);

    /**
     * 获取系统银行卡类型列表
     *
     * @param appKey
     * @param callback
     */
    void fetchBankCardType(String appKey, Callback<List<BankCardType>> callback);

    /**
     * 绑定银行卡和人民币提现都需要这个验证码
     *
     * @param appKey
     * @param callback
     */
    void submitBankCardSendMessage(String appKey, Callback<BaseEntity> callback);

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
    void submitBindBankCard(String appKey, String bankAccount, String cardType, String prov, String city,
                            String area, String name, String address, String code, String googleCode, Callback<BaseEntity> callback);

    /**
     * 获取相关币种的提现手续费
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    void fetchWithdrawFee(String appKey, String coinId, Callback<WithdrawSetting> callback);

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
    void submitGSETWithdraw(String appKey, String money, String cardId, String password, String code, String googleCode, String coinId, Callback<BaseEntity> callback);

    /**
     * 人民币提现记录
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    void fetchWithdrawRecordList(String appKey, String coinId, Callback<List<USDTWithdrawRecord>> callback);

    /**
     * 交易记录
     *
     * @param token
     * @param coinID
     * @param callback
     */
    void fetchTransactionRecord(String token, String coinID, Callback<List<CoinRecord>> callback);

    /***************************************************** OSS *****************************************************/
    /**
     * OSS上传图片
     *
     * @param path
     * @param callback
     */
    void ossUploadImage(String path, Callback<String> callback);

    /***************************************************** C2C *****************************************************/
    /**
     * 获取C2C币种列表
     *
     * @param callback
     */
    void fetchC2CCoinTypeList(Callback<List<C2CCoinType>> callback);

    /**
     * C2C发布
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
    void submitC2CRelease(String uid, String type, String country, String coinID, String coinName, String price, String num, String minNum, String maxNum, String payType, String pwd, Callback<String> callback);

    /**
     * 获取C2C币的余额
     *
     * @param uid
     * @param CoinID
     * @param callback
     */
    void fetchC2CBalance(String uid, String CoinID, Callback<String> callback);

    /**
     * 我的发布
     *
     * @param uid
     * @param page
     * @param callback
     */
    void fetchMyRelease(String uid, int page, Callback<MyRelease> callback);

    /**
     * C2C下线
     *
     * @param uid
     * @param id
     * @param callback
     */
    void submitCancelRelease(String uid, String id, Callback<BaseEntity> callback);

    /**
     * C2C发布详情
     *
     * @param type     releaseSell卖出 releaseBuy买入
     * @param uid
     * @param id
     * @param page
     * @param callback
     */
    void fetchC2CReleaseDetail(String type, String uid, String id, int page, Callback<C2CReleaseDetailEntity> callback);

    /**
     * 取消C2C订单
     *
     * @param uid
     * @param id
     * @param callback
     */
    void submitCancelC2COrder(String uid, String id, Callback<BaseEntity> callback);

    /**
     * 标记为我已付款
     *
     * @param uid
     * @param id
     */
    void submitSetHasPay(String uid, String id, Callback<BaseEntity> callback);

    /**
     * 标记为我已收款
     *
     * @param uid
     * @param id
     */
    void submitSetHasReceved(String uid, String id, Callback<BaseEntity> callback);

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
    void submitC2CBuy(String uid, String volume, String price, String tradeId, String pwd, Callback<BaseEntity> callback);

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
    void submitC2CSell(String uid, String volume, String price, String tradeId, String pwd, Callback<BaseEntity> callback);

    /**
     * C2C卖出、买入账单
     *
     * @param type     类型 1、买入 2、卖出 默认为1
     * @param uid
     * @param page
     * @param callback
     */
    void fetchC2COrderList(String type, String uid, String page, Callback<C2COrderListEntity> callback);

    /**
     * 获取C2C购买列表
     *
     * @param id
     * @param callback
     */
    void fetchC2CBuyList(int page, String id, Callback<C2cTransactionEntity> callback);

    /**
     * 获取C2C卖出列表
     *
     * @param id
     * @param callback
     */
    void fetchC2CSellList(int page, String id, Callback<C2cTransactionEntity> callback);

    /**
     * 获取C2C支付账户信息
     *
     * @param uid
     * @param callback
     */
    void fetchC2CPayAccountInfo(String uid, Callback<C2CPayAccountInfo> callback);

    /**
     * 添加支付宝账号
     *
     * @param uid
     * @param name
     * @param account
     * @param qrImageUrl
     */
    void submitAddAlipay(String uid, String name, String account, String qrImageUrl, Callback<BaseEntity> callback);

    /**
     * 添加微信账号
     *
     * @param uid
     * @param name
     * @param account
     * @param qrImageUrl
     * @param callback
     */
    void submitAddWechat(String uid, String name, String account, String qrImageUrl, Callback<BaseEntity> callback);

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
    void submitAddBankAccount(String uid, String khm, String idCard, String zhhm, String khh, String phone, Callback<BaseEntity> callback);

    /**
     * 删除C2C银行卡、支付宝、微信账号
     *
     * @param uid
     * @param type
     * @param id
     * @param callback
     */
    void submitDeleteC2CAccount(String uid, String type, String id, Callback<BaseEntity> callback);

    /**
     * C2C申诉
     *
     * @param uid
     * @param orderID
     * @param content
     * @param imageBase64
     * @param callback
     */
    void submitC2CApply(String uid, String orderID, String content, String imageBase64, Callback<BaseEntity> callback);

    /**
     * 帮助详情
     *
     * @param catid
     * @param callback
     */
    void fetchHelpCenterDetailList(String catid, Callback<List<Help>> callback);

    /**
     * 帮助中心列表
     *
     * @param callback
     */
    void fetchHelpCenterList(Callback<List<Help>> callback);

    /**
     * 折线图数据
     *
     * @param callback
     */
    void fetchLineChartData(String coinName, String time, Callback<List<LineChartEntity>> callback);

    /**
     * 获取C2C交易价格
     *
     * @param coinName
     * @param callback
     */
    void fetchC2CTransactionPrice(String coinName, Callback<C2CTransationPriceEntity> callback);

    /***************************************************** 矿池 *****************************************************/

    /**
     * PHP端注册JAVA端注册成功过后调用
     *
     * @param account
     * @param password
     * @param password_
     * @param introCode
     * @param callback
     */
    void submitRegisterPHP(String account, String password, String password_, String introCode, Callback<BaseEntity> callback);

    /**
     * 算力记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    void fetchCalculationRecord(String uid, int page, Callback<List<CalculationRecord>> callback);

    /**
     * 算力任务
     *
     * @param uid
     * @param callback
     */
    void fetchCalculationTask(String uid, Callback<CalculationTask> callback);

    /**
     * 矿区首页
     *
     * @param uid
     * @param callback
     */
    void fetchMineAreaStatus(String uid, Callback<List<MineAreaStatus>> callback);

    /**
     * 挖矿
     *
     * @param uid
     * @param id
     * @param callback
     */
    void submitMining(String uid, String id, Callback<BaseEntity> callback);

    /**
     * 挖矿记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    void fetchMiningRecord(String uid, int page, Callback<List<MiningRecord>> callback);

    /**
     * 开矿区
     *
     * @param uid
     * @param callback
     */
    void fetchMiningArea(String uid, Callback<MiningArea> callback);

    /**
     * 一键复投
     *
     * @param uid
     * @param type
     * @param isOpen
     * @param callback
     */
    void submitOneKey(String uid, String type, boolean isOpen, Callback<BaseEntity> callback);

    /**
     * 开矿区投入接口
     *
     * @param uid
     * @param type
     * @param num
     * @param coinID
     * @param callback
     */
    void submitPutMiningArea(String uid, String type, String num, String coinID, Callback<BaseEntity> callback);

    /**
     * 首页banner接口
     *
     * @param callback
     */
    void fetchBannerList(Callback<List<Banner>> callback);

    /**
     * 隐藏币种
     *
     * @param uid
     * @param coinName
     * @param callback
     */
    void submitHideCoin(String uid, String coinName, Callback<BaseEntity> callback);

    /**
     * 隐藏币种列表
     *
     * @param uid
     * @param callback
     */
    void fetchHideCoinList(String uid, Callback<List<HideCoinEntity>> callback);

    /**
     * 判断交易是否收藏
     *
     * @param fid
     * @param treadId
     * @param callback
     */
    void fetchIsCollectTrading(String fid, String treadId, Callback<Coin.CoinBean> callback);

    /**
     * 收藏交易
     *
     * @param fid
     * @param tradeId
     * @param callback
     */
    void submitCollectTrading(String fid, String tradeId, Callback<BaseEntity> callback);

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
    void updatePersonalInfo(String uid, String avatarUrl, String nick, String sex, String phone, Callback<BaseEntity> callback);

    /**
     * 获取本人信息接口
     *
     * @param uid
     * @param callback
     */
    void fetchPersonalInfo(String uid, Callback<PersonalInfo> callback);
}

