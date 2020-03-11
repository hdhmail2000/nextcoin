package com.ifenduo.mahattan_x.event;

/**
 * Created by ll on 2018/3/7.
 */

public class BaseEvent {
    /**
     * 提币 新增地址 google验证成功
     */
    public static final int EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS = 1;
    /**
     * 获取k线数据
     */
    public static final int EVENT_CODE_REQUEST_K_LINE_DATA = 2;
    /**
     * 实名认证成功
     */
    public static final int EVENT_CODE_REAL_AUTH_SUCCESS = 3;
    /**
     * 设置交易密码成功
     */
    public static final int EVENT_CODE_SET_TRADE_PWD_SUCCESS = 4;
    /**
     * 绑定手机成功
     */
    public static final int EVENT_CODE_BIND_PHONE_SUCCESS = 5;
    /**
     * 刷新行情列表单价
     */
    public static final int REFRESH_QUOTES_LIST_PRICE = 6;
    /**
     * 刷新订单列表
     */
    public static final int REFRESH_ORDER_LIST = 7;
    /**
     * 绑定邮箱成功
     */
    public static final int BIND_EMAIL_SUCCESS = 8;
    /**
     * 点赞成功
     */
    public static final int BIND_LIKE_SUCCESS = 9;

    /**
     * 修改C2C订单状态
     */
    public static final int EVENT_CODE_CHANGE_C2C_ORDER_STATUS = 10;
    /**
     * C2C申诉成功
     */
    public static final int EVENT_CODE_C2C_APPLY_SUCCESS = 11;
    /**
     * 注册后自动登录成功
     */
    public static final int EVENT_CODE_AUTO_LOGIN_SUCCESS = 12;
    /**
     * 提现成功
     */
    public static final int EVENT_CODE_WITHDRAW_SUCCESS=13;


    private int code;
    private Object obj;

    public BaseEvent(int code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
