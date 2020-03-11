package com.ifenduo.mahattan_x;

import android.Manifest;

//import com.ifenduo.futurewallet.entity.Coin;
//import com.ifenduo.futurewallet.entity.Friend;

import com.ifenduo.mahattan_x.entity.Coin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ll on 2018/2/23.
 */

public class Constant {
    public static final String SIGN = "future493nm=lnrfu90h";

    public static final String COIN_NAME_ETH = "ETH";

    public static Map<String, Double> RMB_PRICE_CACHE = new HashMap<>();
    public static Map<String, List<Coin>> TRADING_AREA_CACHE = new LinkedHashMap<>();

    public static final String[] CAMERA_AND_CONTACTS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String K_LINE_TIME_INTERVAL_1_MIN = "60";
    public static final String K_LINE_TIME_INTERVAL_5_MIN = "300";
    public static final String K_LINE_TIME_INTERVAL_15_MIN = "900";
    public static final String K_LINE_TIME_INTERVAL_30_MIN = "1800";
    public static final String K_LINE_TIME_INTERVAL_60_MIN = "3600";
    public static final String K_LINE_TIME_INTERVAL_1_DAY = "86400";
    public static final String K_LINE_TIME_INTERVAL_1_WEEK = "604800";
    public static final String K_LINE_TIME_INTERVAL_1_MON = "2592000";

    public static final String ORDER_STATUS_ALL = "0";//订单状态 所有订单
    public static final String ORDER_STATUS_NOW = "1";//订单状态 当前订单
    public static final String ORDER_STATUS_HISTORY = "2";//订单状态 历史订单

    public static final String ORDER_TYPE_SELL = "1";//订单类型 卖出
    public static final String ORDER_TYPE_BUY = "0";//订单类型  买入

    public static final String SET_PAY_PSD_STATUS_NO = "1"; //第一次登陆未设置支付密码
    public static final String SET_PAY_PSD_STATUS_SET = "2";//设置了修改支付密码
    public static final String SET_PAY_PSD_STATUS_FIND = "3";//设置了找回支付密码

    public static final int REQUEST_CODE_COMMON = 1025;
    public static final int GO_TO_GOOGLE_CODE_FROM_ADD_ADDRESS = 1026;
    public static final int REQUEST_CAMERA_PERMISSION = 1027;
    public static final int REQUEST_CODE_SIGN = 1001;//个性签名
    public static final int REQUEST_CODE_NICK = 1002;//昵称
    public static final int REQUEST_CODE_CHOOSE_ADDRESS = 1003;//选择提币地址
    public static final int REQUEST_CODE_COIN_TYPE = 1004;//币种
    public static final int REQUEST_CODE_SCAN_ADDRESS = 1005;//扫一扫钱包地址


    public static final String BUNDLE_KEY_COMMON = "bundle_key_common";
    public static final String BUNDLE_KEY_ADDRESS = "bundle_key_address";
    public static final String BUNDLE_KEY_QR_CODE = "bundle_key_qr_code";
    public static final String BUNDLE_KEY_FROM_WHERE = "bundle_key_from_where";
    public static final String BUNDLE_KEY_SYMBOL_LIST = "bundle_key_symbol_list";
    public static final String BUNDLE_KEY_IS_RELOGIN = "bundle_key_is_relogin";
    public static final String BUNDLE_KEY_PAGE_TITLE = "bundle_key_page_title";
    public static final String BUNDLE_KEY_SELL_COIN = "bundle_key_sell_coin";
    public static final String BUNDLE_KEY_BUY_COIN = "bundle_key_buy_coin";
    public static final String BUNDLE_KEY_SHOW_PAGE_INDEX = "bundle_key_show_page_index";
    public static final String BUNDLE_KEY_C2C_TYPE = "bundle_key_c2c_type";
    public static final String BUNDLE_KEY_SIGN = "bundle_key_sign";//个性签名Key
    public static final String BUNDLE_KEY_NICK = "bundle_key_nick";//昵称key
    public static final String BUNDLE_KEY_SET_PAY_PSD_STATUS = "bundle_key_set_pay_psd_status";//设置支付密码状态
    public static final String BUNDLE_KEY_WITHDRAW_ADDRESS = "bundle_key_withdraw_address";//添加地址
    public static final String BUNDLE_KEY_COIN_TYPE = "bundle_key_coin_type";//币种
    public static final String BUNDLE_KEY_FRIEND_ID = "bundle_key_friend_id";//好友ID
    public static final String BUNDLE_KEY_PAGE_TYPE = "bundle_key_page_type";

//    public static Map<String, Friend> mCheckedItemList = new HashMap<>();//存放选中的好友


    //1销量降序 2销量升序 3价格降序 4价格升序 5时间降序 6时间升序
    public static final String PRODUCT_ORDER_SALES_VOLUME_DOWN = "1";//销量降序
    public static final String PRODUCT_ORDER_SALES_VOLUME_UP = "2";  //销量升序
    public static final String PRODUCT_ORDER_PRICE_DOWN = "3";       //价格降序
    public static final String PRODUCT_ORDER_PRICE_UP = "4";         //价格升序
    public static final String PRODUCT_ORDER_TIME_DOWN = "5";        //时间降序
    public static final String PRODUCT_ORDER_TIME_UP = "6";          //时间升序
    public static final String PRODUCT_ORDER_NORMAL = "";            //默认排序

    public static final int SCAN_ACTION_ADD_ADDRESS = 1024;          //扫描二维码添加地址
    public static final int SCAN_ACTION_PAY = 1025;                  //扫描二维码付款

}
