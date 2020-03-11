package com.ifenduo.mahattan_x.api.config;

/**
 * Created by yangxuefeng on 16/10/20.
 */

public class URLConfig {

    public static final String PROTOCOL = "http://";
    public static final String HOST = "www.mhtx.io";
    public final static String HOST_URL = PROTOCOL + HOST;

    /*********************************************************************************************
     *                                           交易所                                           *
     *********************************************************************************************/
    /**
     * 测试签名
     */
    public static final String URL_TEST_SIGNTRUE = "/v1/user/test.html";
    /**
     * 用户资产余额
     */
    public static final String URL_BALANCE = "/v1/user/balance.html";
    /**
     * 登录
     */
    public static final String URL_LOGIN = "/v1/user/login.html";
    /**
     * 获取谷歌验证码秘钥
     */
    public static final String URL_GOOGLE_DEVICE = "/v1/user/google_device.html";
    /**
     * 添加谷歌验证码秘钥
     */
    public static final String URL_BIND_GOOGLE_CODE = "/v1/user/google_auth.html";
    /**
     * 登录日志
     */
    public static final String URL_LOGIN_LOG = "/v1/log/login.html";
    /**
     * 安全设置日志
     */
    public static final String URL_SAFE_LOG = "/v1/log/setting.html";
    /**
     * 获取安全设置详情
     */
    public static final String URL_SAFE_SETTING_DETAIL = "/v1/user/security.html";
    /**
     * 获取虚拟币充值地址和近十次充值记录
     */
    public static final String URL_RECHARGE_ADDRESS_AND_RECORD = "/v1/deposit/coin.html";
    /**
     * 修改/绑定，登录和交易密码
     */
    public static final String URL_UPDATE_PASSWORD = "/v1/user/password.html";
    /**
     * 找回交易密码接口
     */
    public static final String URL_FIND_TRANS_PASSWORD = "/v1/user/findtradepwd.html";
    /**
     * 找回登录密码
     */
    public static final String URL_RESET_LOGIN_PASSWORD = "/v1/user/resetpwd.html";
    /**
     * 发送绑定手机的短信
     */
    public static final String URL_SEND_PHONE_MESSAGE = "/v1/user/bind_msg.html";
    /**
     * 实名认证
     */
    public static final String URL_REL_AUTH = "/v1/user/real_auth.html";
    /**
     * 获取已有的提现地址
     */
    public static final String URL_WITHDRAW_ADDRESS_LIST = "/v1/coin/list_address.html";
    /**
     * 添加提现地址
     */
    public static final String URL_ADD_WITHDRAW_ADDRESS = "/v1/coin/address.html";
    /**
     * 发短信（需要签名）
     */
    public static final String URL_SEND_PHONE_MESSAGE_SIGNTRUE = "/v1/validate/sign.html";
    /**
     * 发送不需要签名的短信 POST (非签名)
     */
    public static final String URL_SEND_PHONE_MESSAGE_NOT_SIGNTRUE = "/v1/validate/send.html";
    /**
     * 虚拟币提现
     */
    public static final String URL_WITHDRAW_COIN = "/v1/coin/withdraw.html";
    /**
     * 绑定手机
     */
    public static final String URL_BIND_PHONE = "/v1/user/bind_phone.html";
    /**
     * 手机注册
     */
    public static final String URL_PHONE_REGISTER = "/v1/user/register.html";
    /**
     * K线数据
     */
    public static final String URL_K_LINE = "/kline/fullperiod.html";
    /**
     * 获取交易区
     */
    public static final String URL_TRADING_AREA = "/v1/market/area.html";
    /**
     * 交易区币种列表
     */
    public static final String URL_TRADING_AREA_COIN_LIST = "/v1/market/list.html";
    /**
     * 获取自选列表
     */
    public static final String URL_SELF_CHOOSE_COIN_LIST = "/index.php?c=app&m=collectionLists";
    /**
     * 交易区币种列表（币的信息）
     */
    public static final String URL_TRADING_AREA_COIN_INFO = "/real/markets.html";
    /**
     * 获取交易对的用户可用资产
     */
    public static final String URL_MONEY_OF_COIN = "/v1/market/userassets.html";
    /**
     * 深度、盘口
     */
    public static final String URL_DEPTH = "/kline/fulldepth.html";
    /**
     * 下单
     */
    public static final String URL_SUBMIT_ORDER = "/v1/entrust/place.html";
    /**
     * 获取订单
     */
    public static final String URL_FETCH_ORDER = "/v1/entrust/list.html";
    /**
     * 取消订单
     */
    public static final String URL_CANCEL_ORDER = "/v1/entrust/cancel.html";
    /**
     * 充值码兑换
     */
    public static final String URL_SUBMIT_CODE = "/v1/activity/exchange.html";
    /**
     * 充值码列表
     */
    public static final String URL_CODE_LIST = "/v1/activity/index.html";
    /**
     * 发送绑定邮件的验证码
     */
    public static final String URL_SEND_EMAIL = "/v1/email/send.html";
    /**
     * 绑定邮件
     */
    public static final String URL_BIND_EMAIL = "/v1/email/add.html";
    /**
     * 用户信息
     */
    public static final String URL_USER_INFO = "/v1/user/info.html";
    /**
     * 人民币充值
     */
    public static final String URL_CCNY_RECHARGE = "/v1/rmb/recharge.html";

    /**
     * 费率
     */
    public static final String URL_RATES_PAGE = "/app/fee.html";
    /**
     * 货币资料
     */
    public static final String URL_COIN_INFO_PAGE = "/app/coin.html";
    /**
     * 帮助中心
     */
    public static final String URL_HELP_CENTER_PAGE = "/app/help.html";
    /**
     * 个人中心分享
     */
    public static final String URL_PERSONAL_CENTER_SHARE=HOST_URL+"/index.php?s=exc&c=userController&m=textmark&intro=";
    /**
     * 人民币充值
     */
    public static final String URL_CCNY_RECHARGE_PAGE = "https://pay.zgzop.com/cashier/";
    /**
     * 汇率
     */
    public static final String URL_FETCH_EXCHANGE_RATE = "/market/rate.html";
    /**
     * 自己已经绑定的银行卡
     */
    public static final String URL_BANK_CARD_LIST = "/v1/user/bankinfo.html";
    /**
     * 获取系统银行卡类型列表
     */
    public static final String URL_BANK_CARD_TYPE = "/v1/system/bankinfo.html";
    /**
     * 绑定银行卡和人民币提现都需要这个验证码
     */
    public static final String URL_BIND_BANK_SEND_MESSAGE = "/v1/user/send_bank_sms.html";
    /**
     * 绑定银行卡
     */
    public static final String URL_BIND_BANK_CARD = "/v1/user/save_bankinfo.html";
    /**
     * 获取相关币种的提现手续费
     */
    public static final String URL_WITHDRAW_FEE = "/v1/system/fee.html";
    /**
     * 人民币提现申请
     */
    public static final String URL_GSET_WITHDRAW = "/v1/user/cny_manual.html";
    /**
     * 人民币提现记录
     */
    public static final String URL_WITHDRAW_RECORD_LIST = "/v1/user/cny_list.html";
    /**
     * 交易记录
     */
    public static final String URL_TRANSACTION_LIST_BY_COIN_ID = "/v1/user/operation.html";


    /*********************************************************************************************
     *                                           C2C                                             *
     *********************************************************************************************/

    /**
     * C2C币种列表
     */
    public static final String URL_C2C_COIN_TYPE_LIST = "/index.php?s=trade&c=api&m=getCoinList";
    /**
     * C2C发布
     */
    public static final String URL_C2C_RELEASE = "/index.php?s=trade&c=api&m=fabu&flag=app&type=";
    /**
     * C2C币种剩余数量
     */
    public static final String URL_C2C_BALANCE = "/index.php?s=trade&c=api&m=total_of_symbol";
    /**
     * C2C我的发布
     */
    public static final String URL_C2C_MY_RELEASE = "/index.php?s=trade&c=api&m=c2cReleaseList";
    /**
     * C2C下线
     */
    public static final String URL_C2C_CANCEL_RELEASE = "/index.php?s=trade&c=api&m=underline";
    /**
     * 取消C2C订单
     */
    public static final String URL_C2C_CANCEL_ORDER = "/index.php?s=trade&c=api&m=cancel_order";
    /**
     * C2C订单标记为我已付款
     */
    public static final String URL_C2C_SET_HAS_PAY = "/index.php?s=trade&c=api&m=mark_pay";
    /**
     * C2C订单标记为已付款
     */
    public static final String URL_C2C_SET_HAS_RECEVED = "/index.php?s=trade&c=api&m=mark_receved";
    /**
     * C2C发布详情
     */
    public static final String URL_C2C_RELEASE_DETAIL = "/index.php?s=trade&c=api&m=";
    /**
     * 购买C2C
     */
    public static final String URL_C2C_BUY = "/index.php?s=trade&c=api&m=trade_buy";
    /**
     * 卖出C2C
     */
    public static final String URL_C2C_SELL = "/index.php?s=trade&c=api&m=trade_sell";
    /**
     * C2C卖出、买入账单
     */
    public static final String URL_C2C_ORDER_LIST = "/index.php?s=trade&c=api&m=c2cMyorder";
    /**
     * C2C购买列表
     */
    public static final String URL_C2C_BUY_LIST = "/index.php?s=trade&c=api&m=c2cBuy";
    /**
     * C2C出售列表
     */
    public static final String URL_C2C_SELL_LIST = "/index.php?s=trade&c=api&m=c2cSell";
    /**
     * C2C用户付款方式信息
     */
    public static final String URL_C2C_PAY_INFO = "/index.php?s=trade&c=api&m=addPayType";
    /**
     * C2C添加支付宝
     */
    public static final String URL_ADD_ALIPAY_ACCOUNT = "/index.php?s=trade&c=api&m=alipay_submit";
    /**
     * C2C添加微信
     */
    public static final String URL_ADD_WECHAT_ACCOUNT = "/index.php?s=trade&c=api&m=weixin_submit";
    /**
     * C2C添加银行卡
     */
    public static final String URL_ADD_BANK_ACCOUNT = "/index.php?s=trade&c=api&m=bank_submit";
    /**
     * 删除C2C银行卡、支付宝、微信账号
     */
    public static final String URL_DELETE_C2C_ACCOUNT = "/index.php?s=trade&c=api&m=";
    /**
     * C2C申诉
     */
    public static final String URL_C2C_APPLY = "/index.php?s=trade&c=api&m=shensu";

    /**
     * 帮助中心列表
     */
    public static final String URL_HELP_CENTER_LIST = "/index.php?s=apphelp&c=app";
    /**
     * 帮助列表详情
     */
    public static final String URL_HELP_CENTER_DETAIL = "/index.php?s=apphelp&c=app&m=lists";

    public static final String URL_LINE_CHART = "/index.php?s=trade&c=api&m=lineChart";

    /**
     * 发布交易-当日价格
     */
    public static final String URL_C2C_TRANSATION_PRICE = "/index.php?s=trade&c=api&m=low_high";
    /*********************************************************************************************
     *                                           矿池                                            *
     *********************************************************************************************/
    /**
     * PHP端注册JAVA端注册成功过后调用
     */
    public static final String URL_REGISTER_PHP = "/index.php?s=member&c=register";
    /**
     * 算力记录
     */
    public static final String URL_MINE_CALCULATION_RECORD = "/index.php?s=mineral&c=Mining&m=abilityLog&fid=";
    /**
     * 算力任务
     */
    public static final String URL_CALCULATION_TASK = "/index.php?s=mineral&c=Mining&m=ability&fid=";
    /**
     * 矿区首页
     */
    public static final String URL_MINE_STATUS = "/index.php?s=mineral&c=mining&fid=";
    /**
     * 挖矿
     */
    public static final String URL_MINE_MINING = "/index.php?s=mineral&c=mining&m=getIncome&fid=";
    /**
     * 挖矿记录
     */
    public static final String URL_MINING_RECORD = "/index.php?s=mineral&c=Mining&m=miningLog";
    /**
     * 开矿区
     */
    public static final String URL_MINING_AREA = "/index.php?s=mineral&c=mining&m=miningArea&fid=";
    /**
     * 一键复投
     */
    public static final String URL_OPEN_ONE_KEY = "/index.php?s=mineral&c=mining&m=reCast";
    /**
     * 开矿区投入
     */
    public static final String URL_MINING_AREA_PUT = "/index.php?s=mineral&c=mining&m=inputMiningArea";
    /**
     * 首页banner接口
     */
    public static final String URL_HOME_BANNER = "/index.php?s=mineral&c=Mining&m=banner";
    /**
     * 隐藏币种
     */
    public static final String URL_HIDE_COIN = "/index.php?s=mineral&c=mining&m=hideCoin";

    public static final String URL_HIDE_COIN_LIST = "/index.php?s=mineral&c=mining&m=hideCoinList&fid=";
    /**
     * 判断是否收藏交易
     */
    public static final String URL_FETCH_IS_COLLECT_TRADING = "/index.php?c=app&m=isCollect";
    /**
     * 收藏交易
     */
    public static final String URL_COLLECT_TRADING_CENTER = "/index.php?c=app&m=collectionMarkets";
    /**
     * 修改用户信息
     */
    public static final String URL_UPDATE_PERSONAL_INFO = "/index.php?s=mineral&c=Mining&m=updateInfo";
    /**
     * 获取用户信息
     */
    public static final String URL_PERSONAL_INFO = "/index.php?s=mineral&c=Mining&m=getUserInfo&fid=";

    public static final String URL_SHARE = HOST_URL + "/index.php?s=exc&c=userController&m=appsharereg&intro=";

}
