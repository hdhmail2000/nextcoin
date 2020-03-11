package com.ifenduo.mahattan_x.api;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.reflect.TypeToken;
import com.ifenduo.common.log.XCLOG;
import com.ifenduo.mahattan_x.api.config.RequestConfig;
import com.ifenduo.mahattan_x.api.config.URLConfig;
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
import com.ifenduo.mahattan_x.net.OSSManager;
import com.ifenduo.mahattan_x.net.OkHttpEx;
import com.ifenduo.lib_okhttp.callback.OkCallback;
import com.ifenduo.lib_okhttp.parser.OkTextParser;
import com.ifenduo.mahattan_x.tools.SignTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.ifenduo.mahattan_x.api.JsonParse.gson;
import static com.ifenduo.mahattan_x.api.config.URLConfig.HOST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.HOST_URL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_ADD_ALIPAY_ACCOUNT;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_ADD_BANK_ACCOUNT;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_ADD_WECHAT_ACCOUNT;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_ADD_WITHDRAW_ADDRESS;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BANK_CARD_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BANK_CARD_TYPE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BIND_BANK_CARD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BIND_BANK_SEND_MESSAGE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BIND_EMAIL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BIND_GOOGLE_CODE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_BIND_PHONE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_APPLY;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_BALANCE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_BUY;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_BUY_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_CANCEL_ORDER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_CANCEL_RELEASE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_COIN_TYPE_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_MY_RELEASE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_ORDER_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_PAY_INFO;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_RELEASE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_RELEASE_DETAIL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_SELL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_SELL_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_SET_HAS_PAY;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_SET_HAS_RECEVED;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_C2C_TRANSATION_PRICE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_CALCULATION_TASK;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_CANCEL_ORDER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_CCNY_RECHARGE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_CODE_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_COLLECT_TRADING_CENTER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_DELETE_C2C_ACCOUNT;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_DEPTH;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_FETCH_EXCHANGE_RATE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_FETCH_IS_COLLECT_TRADING;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_FETCH_ORDER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_FIND_TRANS_PASSWORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_GSET_WITHDRAW;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_HELP_CENTER_DETAIL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_HELP_CENTER_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_HIDE_COIN;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_HIDE_COIN_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_HOME_BANNER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_K_LINE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_LINE_CHART;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_LOGIN;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_LOGIN_LOG;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINE_CALCULATION_RECORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINE_MINING;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINE_STATUS;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINING_AREA;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINING_AREA_PUT;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MINING_RECORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_MONEY_OF_COIN;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_OPEN_ONE_KEY;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_PERSONAL_INFO;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_PHONE_REGISTER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_RECHARGE_ADDRESS_AND_RECORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_REGISTER_PHP;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_REL_AUTH;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_RESET_LOGIN_PASSWORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SAFE_LOG;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SAFE_SETTING_DETAIL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SELF_CHOOSE_COIN_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SEND_EMAIL;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SEND_PHONE_MESSAGE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SEND_PHONE_MESSAGE_NOT_SIGNTRUE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SEND_PHONE_MESSAGE_SIGNTRUE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SUBMIT_CODE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_SUBMIT_ORDER;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_TEST_SIGNTRUE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_TRADING_AREA;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_TRADING_AREA_COIN_INFO;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_TRADING_AREA_COIN_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_TRANSACTION_LIST_BY_COIN_ID;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_UPDATE_PASSWORD;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_UPDATE_PERSONAL_INFO;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_USER_INFO;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_WITHDRAW_ADDRESS_LIST;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_WITHDRAW_COIN;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_WITHDRAW_FEE;
import static com.ifenduo.mahattan_x.api.config.URLConfig.URL_WITHDRAW_RECORD_LIST;


/**
 * Created by yangxuefeng on 16/8/16.
 */
public class OkHttpApi implements IApi {

    public final static String NETWORK_ERROR = "网络错误";

    @Override
    public void setTag(Object tag) {
    }

    @Override
    public void cancel(Object tag) {
        if (tag == null) return;
        OkHttpEx.cancel(tag);
    }


    private static DataResponse buildResponseWithData(BaseEntity data) {
        DataResponse response = new DataResponse<>();
        response.code = data.getCode();
        response.msg = data.getMsg();
        return response;
    }

    private static String buildPageParams(int page) {
        if (page >= 1) {
            return "&start=" + (page - 1) * RequestConfig.PAGE_COUNT + "&num=" + RequestConfig.PAGE_COUNT;
        }
        return "";
    }

    /**
     * 钱包
     *
     * @param appKey
     * @param appSecret
     * @param callback
     */
    @Override
    public void fetchBalance(String appKey, String appSecret, final Callback<Balance> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
//        ApiSign.createSignature(appKey,appSecret,"GET",HOST,URL_TEST_SIGNTRUE,params);
        OkHttpEx.get()
                .url(HOST_URL + URLConfig.URL_BALANCE + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.d("TAG", "fetchBalance s=" + s);
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), Balance.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }


    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @param type    1：手机  2：邮箱
     */
    @Override
    public void submitLogin(String account, String pwd, String area, int type, final Callback<LoginInfo> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_LOGIN)
                .addParams("loginName", account)
                .addParams("password", pwd)
                .addParams("type", String.valueOf(type))
                .addParams("area", area)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.e("TAG", "submitLogin2222221--======: " + s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), LoginInfo.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取谷歌验证码秘钥
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchGoogleDevice(String appKey, final Callback<GoogleKeyInfo> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        OkHttpEx.get()
                .url(HOST_URL + URLConfig.URL_GOOGLE_DEVICE + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), GoogleKeyInfo.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 登录日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    @Override
    public void fetchLoginLog(String appKey, int page, final Callback<LoginLog> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("currentPage", String.valueOf(page));
        OkHttpEx.get()
                .url(HOST_URL + URL_LOGIN_LOG + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), LoginLog.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 安全设置日志
     *
     * @param appKey
     * @param page
     * @param callback
     */
    @Override
    public void fetchSafeLog(String appKey, int page, final Callback<LoginLog> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("currentPage", String.valueOf(page));
        OkHttpEx.get()
                .url(HOST_URL + URL_SAFE_LOG + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), LoginLog.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitBindGoogleCode(String appKey, String appSecret, String googleCode, String googleKey, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("code", googleCode);
        params.put("token", appKey);
        params.put("totpKey", googleKey);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_BIND_GOOGLE_CODE, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_BIND_GOOGLE_CODE)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取安全设置详情
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchSafeSettingDetail(String appKey, final Callback<SafeSettingEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        OkHttpEx.get()
                .url(HOST_URL + URL_SAFE_SETTING_DETAIL + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), SafeSettingEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取虚拟币充值地址和近十次充值记录
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchRechargeAddressAndRecord(String appKey, String coinId, final Callback<RechargeAddressAndRecord> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("symbol", coinId);
        OkHttpEx.get()
                .url(HOST_URL + URL_RECHARGE_ADDRESS_AND_RECORD + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), RechargeAddressAndRecord.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitUpdatePassword(String appKey, String appSecret, String oldPassword, String password, String password_, String messageCode, String googleCode, String idCard, int pwdType, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("originPwd", oldPassword);
        params.put("newPwd", password);
        params.put("reNewPwd", password_);
        params.put("phoneCode", messageCode);
        params.put("totpCode", googleCode);
        params.put("identityCode", idCard);
        params.put("pwdType", String.valueOf(pwdType));
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_UPDATE_PASSWORD, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_UPDATE_PASSWORD)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.d("TAG", "submitUpdatePassword=" + s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitFindTransPwd(String area, String phone, String code, String googleCode, String password, String password_, String token, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_FIND_TRANS_PASSWORD)
                .addParams("area", area)
                .addParams("phone", phone)
                .addParams("code", code)
                .addParams("totpCode", googleCode)
                .addParams("newPassword", password)
                .addParams("newPassword2", password_)
                .addParams("token", token)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.d("TAG", "submitFindTransPwd=" + s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitResetLoginPassword(String phone, String area, String code, String googleCode, String password, String password_, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_RESET_LOGIN_PASSWORD)
                .addParams("area", area)
                .addParams("phone", phone)
                .addParams("code", code)
                .addParams("totpCode", googleCode)
                .addParams("newPassword", password)
                .addParams("newPassword2", password_)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void sendPhoneMessage(String appKey, String area, String phone, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("area", area);
        params.put("phone", phone);
        OkHttpEx.get()
                .url(HOST_URL + URL_SEND_PHONE_MESSAGE + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void sendPhoneMessage(String appKey, String appSecret, int type, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("type", String.valueOf(type));
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_SEND_PHONE_MESSAGE_SIGNTRUE, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_SEND_PHONE_MESSAGE_SIGNTRUE)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void sendPhoneMessageNotSigntrue(String area, String phone, int type, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("type", String.valueOf(type));
        params.put("area", area);
        params.put("phone", phone);
        OkHttpEx.post()
                .url(HOST_URL + URL_SEND_PHONE_MESSAGE_NOT_SIGNTRUE)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 实名认证
     *
     * @param appKey
     * @param appSecret
     * @param name
     * @param identityNo
     * @param identityType
     * @param callback
     */
    @Override
    public void submitRealAuth(String appKey, String appSecret, String name, String identityNo,
                               String identityType, String idCardZmImgURL, String idCardFmImgURL, String idCardScImgURL, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("realname", name);
        params.put("identitytype", identityType);
        params.put("identityno", identityNo);
        params.put("address", "");
        params.put("idCardZmImgURL", idCardZmImgURL);
        params.put("idCardFmImgURL", idCardFmImgURL);
        params.put("idCardScImgURL", idCardScImgURL);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_REL_AUTH, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_REL_AUTH)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取已有的提现地址
     *
     * @param token
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchWithdrawAddress(String token, String symbol, final Callback<WithdrawAddressInfo> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("symbol", symbol);
        OkHttpEx.get()
                .url(HOST_URL + URL_WITHDRAW_ADDRESS_LIST + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), WithdrawAddressInfo.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
                                      String googleCode, String password, String address, String remark, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("symbol", symbol);
        params.put("phoneCode", phoneCode);
        params.put("totpCode", googleCode);
        params.put("withdrawAddr", address);
        params.put("password", password);
        params.put("remark", remark);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_ADD_WITHDRAW_ADDRESS, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_ADD_WITHDRAW_ADDRESS)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
                                   String phoneCode, String btcfeesIndex, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("symbol", symbol);
        params.put("phoneCode", phoneCode);
        params.put("totpCode", googleCode);
        params.put("withdrawAddr", addressId);
        params.put("memo", address);
        params.put("address", address);
        params.put("tradePwd", tradePwd);
        params.put("btcfeesIndex", btcfeesIndex);
        params.put("withdrawAmount", withdrawAmount);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_WITHDRAW_COIN, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_WITHDRAW_COIN)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void bindPhone(String appKey, String area, String phone, String code, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("phone", phone);
        params.put("code", code);
        params.put("area", area);
        OkHttpEx.post()
                .url(HOST_URL + URL_BIND_PHONE)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitRegister(String area, String account, String regType, String pCode, String eCode, String password, String introCode, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("regName", account);
        params.put("regType", regType);
        params.put("pcode", pCode);
        params.put("ecode", eCode);
        params.put("area", area);
        params.put("intro_user", introCode);
        OkHttpEx.post()
                .url(HOST_URL + URL_PHONE_REGISTER)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * K线数据
     *
     * @param symbol
     * @param step
     * @param callback
     */
    @Override
    public void fetchKLine(String symbol, String step, final Callback<List<List<BigDecimal>>> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("step", step);
        params.put("symbol", symbol);
        OkHttpEx.get()
                .url(HOST_URL + URL_K_LINE + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
//                        XCLOG.d(s);
                        BaseEntity baseEntity = new BaseEntity();
                        baseEntity.setCode(200);
                        baseEntity.setMsg("");
                        baseEntity.setJsonData(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<ArrayList<BigDecimal>>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取交易区
     *
     * @param callback
     */
    @Override
    public void fetchTradingArea(final Callback<List<TradingArea>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_TRADING_AREA)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.d("TAG", "fetchTradingArea------------->" + s);
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getBaseEntityForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<TradingArea>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 交易区币种列表
     *
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchTradingAreaCoinList(long symbol, final Callback<List<Coin>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_TRADING_AREA_COIN_LIST + "?symbol=" + symbol)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getBaseEntityForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<Coin>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取自选列表
     *
     * @param fid
     * @param callback
     */
    @Override
    public void fetchSelfChooseCoin(String fid, final Callback<List<Coin.CoinBean>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_SELF_CHOOSE_COIN_LIST + "&fid=" + fid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchSelfChooseCoin: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<List<Coin.CoinBean>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 交易区币种列表（币的信息）
     *
     * @param symbols
     * @param callback
     */
    @Override
    public void fetchTradingAreaCoinInfo(String symbols, final Callback<List<Coin.CoinBean>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_TRADING_AREA_COIN_INFO + "?symbol=" + symbols)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
//                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getBaseEntityForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<Coin.CoinBean>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取交易对的用户可用资产
     *
     * @param symbols
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchMoneyOfCoin(String symbols, String appKey, String appSecret, final Callback<MoneyOfCoinEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("tradeid", symbols);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_MONEY_OF_COIN, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_MONEY_OF_COIN)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), MoneyOfCoinEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 深度、盘口
     *
     * @param symbol
     * @param callback
     */
    @Override
    public void fetchDepth(String symbol, final Callback<DepthEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("symbol", symbol);
        OkHttpEx.get()
                .url(HOST_URL + URL_DEPTH + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        try {
                            JSONObject jsonObject = new JSONObject(baseEntity.getJsonData());
                            JSONObject depthJson = jsonObject.optJSONObject("depth");
                            dataResponse.data = gson.fromJson(depthJson.toString(), DepthEntity.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitOrder(String appKey, String appSecret, String symbol, String tradeAmount, String tradePrice, String tradePwd, String type, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("symbol", symbol);
        params.put("tradeAmount", tradeAmount);
        params.put("tradePrice", tradePrice);
        params.put("type", type);
        params.put("tradePwd", tradePwd);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_SUBMIT_ORDER, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_SUBMIT_ORDER)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.d("TAG", "submitOrder=" + s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void fetchOrder(String appKey, String appSecret, String symbol, String type, final Callback<OrderEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("symbol", symbol);
        params.put("type", type);
        OkHttpEx.get()
                .url(HOST_URL + URL_FETCH_ORDER + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.d("TAG", "fetchOrder: " + s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), OrderEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void cancelOrder(String appKey, String appSecret, String orderId, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        params.put("id", orderId);
        ApiSign.createSignature(appKey, appSecret, "POST", HOST, URL_CANCEL_ORDER, params);
        OkHttpEx.post()
                .url(HOST_URL + URL_CANCEL_ORDER)
                .setParams(params)
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 充值码兑换
     *
     * @param token
     * @param code
     * @param callback
     */
    @Override
    public void submitCode(String token, String code, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_SUBMIT_CODE)
                .addParams("token", token)
                .addParams("code", code)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 充值码列表
     *
     * @param token
     * @param callback
     */
    @Override
    public void fetchCodeList(String token, final Callback<RechargeRecordEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_CODE_LIST)
                .addParams("token", token)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), RechargeRecordEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 发送绑定邮件的验证码
     *
     * @param appKey
     * @param emailAddress
     * @param callback
     */
    @Override
    public void sendEmail(String appKey, String emailAddress, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_SEND_EMAIL)
                .addParams("token", appKey)
                .addParams("address", emailAddress)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 绑定邮件
     *
     * @param appKey
     * @param code
     * @param callback
     */
    @Override
    public void bindEmail(String appKey, String code, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_BIND_EMAIL)
                .addParams("token", appKey)
                .addParams("code", code)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 用户信息
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchUserInfo(String appKey, final Callback<User> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_USER_INFO + "?token=" + appKey)
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), User.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitCCNYRecharge(String appKey, String coinId, String amount, final Callback<String> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_CCNY_RECHARGE)
                .addParams("coinId", coinId)
                .addParams("amount", amount)
                .addParams("token", appKey)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        JSONObject jsonObject = null;
                        try {
                            if (baseEntity.getJsonData() != null) {
                                jsonObject = new JSONObject(baseEntity.getJsonData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonObject != null) {
                            dataResponse.data = jsonObject.optString("seriesNumber", "");
                        } else {
                            dataResponse.data = "";
                        }
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 汇率
     *
     * @param callback
     */
    @Override
    public void fetchExchangeRate(final Callback<ExchangeRate> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_FETCH_EXCHANGE_RATE)
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), ExchangeRate.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 自己已经绑定的银行卡
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchBankCardList(String appKey, String coinId, final Callback<List<BankCard>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_BANK_CARD_LIST + "?token=" + appKey)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        JSONArray jsonArray = null;
                        if (!TextUtils.isEmpty(baseEntity.getJsonData())) {
                            try {
                                JSONObject jsonObject = new JSONObject(baseEntity.getJsonData());
                                jsonArray = jsonObject.optJSONArray("bankinfo");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (jsonArray != null) {
                            dataResponse.data = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<BankCard>>() {
                            }.getType());
                        } else {
                            dataResponse.data = null;
                        }
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取系统银行卡类型列表
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void fetchBankCardType(String appKey, final Callback<List<BankCardType>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_BANK_CARD_TYPE + "?token=" + appKey)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        JSONArray jsonArray = null;
                        if (!TextUtils.isEmpty(baseEntity.getJsonData())) {
                            try {
                                JSONObject jsonObject = new JSONObject(baseEntity.getJsonData());
                                jsonArray = jsonObject.optJSONArray("bankinfo");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (jsonArray != null) {
                            dataResponse.data = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<BankCardType>>() {
                            }.getType());
                        } else {
                            dataResponse.data = null;
                        }
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 绑定银行卡和人民币提现都需要这个验证码
     *
     * @param appKey
     * @param callback
     */
    @Override
    public void submitBankCardSendMessage(String appKey, final Callback<BaseEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("token", appKey);
        OkHttpEx.get()
                .url(HOST_URL + URL_BIND_BANK_SEND_MESSAGE + ApiSign.convertQueryString(params))
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitBindBankCard(String appKey, String bankAccount, String cardType, String prov, String city, String area, String name, String address, String code, String googleCode, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_BIND_BANK_CARD)
                .addParams("token", appKey)
                .addParams("phoneCode", code)
                .addParams("totpCode", googleCode)
                .addParams("openBankType", cardType)
                .addParams("account", bankAccount)
                .addParams("address", address)
                .addParams("prov", prov)
                .addParams("city", city)
                .addParams("dist", area)
                .addParams("payeeAddr", name)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取相关币种的提现手续费
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchWithdrawFee(String appKey, String coinId, final Callback<WithdrawSetting> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("token", appKey);
        map.put("symbol", coinId);
        OkHttpEx.get()
                .url(HOST_URL + URL_WITHDRAW_FEE + ApiSign.convertQueryString(map))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        JSONObject feeJson = null;
                        if (!TextUtils.isEmpty(baseEntity.getJsonData())) {
                            try {
                                JSONObject jsonObject = new JSONObject(baseEntity.getJsonData());
                                feeJson = jsonObject.optJSONObject("withdrawSetting");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (feeJson != null) {
                            dataResponse.data = gson.fromJson(feeJson.toString(), WithdrawSetting.class);
                        } else {
                            dataResponse.data = null;
                        }
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitGSETWithdraw(String appKey, String money, String cardId, String password, String code, String googleCode, String coinId, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_GSET_WITHDRAW)
                .addParams("token", appKey)
                .addParams("phoneCode", code)
                .addParams("totpCode", googleCode)
                .addParams("withdrawBalance", money)
                .addParams("withdrawBlank", cardId)
                .addParams("tradePwd", password)
                .addParams("symbol", coinId)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getCommonBaseEntityForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 人民币提现记录
     *
     * @param appKey
     * @param coinId
     * @param callback
     */
    @Override
    public void fetchWithdrawRecordList(String appKey, String coinId, final Callback<List<USDTWithdrawRecord>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("token", appKey);
        map.put("symbol", coinId);
        OkHttpEx.get()
                .url(HOST_URL + URL_WITHDRAW_RECORD_LIST + ApiSign.convertQueryString(map))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        BaseEntity baseEntity = JsonParse.getBaseEntityForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<USDTWithdrawRecord>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 交易记录
     *
     * @param token
     * @param coinID
     * @param callback
     */
    @Override
    public void fetchTransactionRecord(String token, String coinID, final Callback<List<CoinRecord>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("coinId", coinID);
        OkHttpEx.get()
                .url(HOST_URL + URL_TRANSACTION_LIST_BY_COIN_ID + ApiSign.convertQueryString(map))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        XCLOG.d(s);
                        Log.d("TAG", "fetchTransactionRecord: " + s);
                        BaseEntity baseEntity = JsonParse.getBaseEntityForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<CoinRecord>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /***************************************************** OSS *****************************************************/
    /**
     * OSS上传图片
     *
     * @param path
     * @param callback
     */
    @Override
    public void ossUploadImage(String path, final Callback<String> callback) {
        String objectKey = "upload/image/" + UUID.randomUUID().toString() + ".jpg";
        PutObjectRequest put = new PutObjectRequest(OSSManager.OSS_BUCKET_NAME, objectKey, path);
        OSSManager.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TAG", "--------->" + result.getServerCallbackReturnBody());
                        DataResponse dataResponse = new DataResponse();
                        dataResponse.data = OSSManager.OSS_BUCKET + request.getObjectKey();
                        dataResponse.code = 1;
                        dataResponse.msg = "上传成功";
                        callback.onComplete(true, 1, dataResponse.msg, dataResponse);
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DataResponse dataResponse = new DataResponse();
                        dataResponse.data = "";
                        dataResponse.code = 0;
                        dataResponse.msg = "上传失败";
                        callback.onComplete(false, dataResponse.code, dataResponse.msg, dataResponse);
                    }
                });
            }
        });
    }

    /***************************************************** C2C *****************************************************/
    /**
     * 获取C2C币种列表
     *
     * @param callback
     */
    @Override
    public void fetchC2CCoinTypeList(final Callback<List<C2CCoinType>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_C2C_COIN_TYPE_LIST)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CCoinTypeList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<List<C2CCoinType>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

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
    @Override
    public void submitC2CRelease(String uid, String type, String country, String coinID, String coinName, String price, String num, String minNum, String maxNum, String payType, String pwd, final Callback<String> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_RELEASE + type)
                .addParams("uid", uid)
                .addParams("data[deal_psw]", pwd)
                .addParams("data[country]", country)
                .addParams("data[min_value]", minNum)
                .addParams("data[max_value]", maxNum)
                .addParams("data[pay_type]", payType)
                .addParams("data[symbolName]", coinName)
                .addParams("1".equals(type) ? "data[order_volume_sell]" : "data[order_volume_buy]", num)
                .addParams("1".equals(type) ? "data[order_price_sell]" : "data[order_price_buy]", price)
                .addParams("1".equals(type) ? "data[symbol_sell]" : "data[symbol_buy]", coinID)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitC2CRelease: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForSting(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = baseEntity.getJsonData();
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取C2C币的余额
     *
     * @param uid
     * @param coinID
     * @param callback
     */
    @Override
    public void fetchC2CBalance(String uid, String coinID, final Callback<String> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_BALANCE)
                .addParams("uid", uid)
                .addParams("symbol", coinID)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CBalance: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForSting(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = baseEntity.getJsonData();
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 我的发布
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchMyRelease(String uid, int page, final Callback<MyRelease> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_MY_RELEASE)
                .addParams("uid", uid)
                .addParams("page", String.valueOf(page))
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchMyRelease: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), MyRelease.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * C2C下线
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitCancelRelease(String uid, String id, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_CANCEL_RELEASE)
                .addParams("uid", uid)
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitCancelRelease: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void fetchC2CReleaseDetail(String type, String uid, String id, int page, final Callback<C2CReleaseDetailEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_RELEASE_DETAIL + type)
                .addParams("uid", uid)
                .addParams("page", String.valueOf(page))
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CReleaseDetail: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2CReleaseDetailEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 取消C2C订单
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitCancelC2COrder(String uid, String id, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_CANCEL_ORDER)
                .addParams("uid", uid)
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitCancelC2COrder: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 标记为我已付款
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitSetHasPay(String uid, String id,
                                final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_SET_HAS_PAY)
                .addParams("uid", uid)
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitSetHasPay: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 标记为我已收款
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitSetHasReceved(String uid, String id,
                                    final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_SET_HAS_RECEVED)
                .addParams("uid", uid)
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitSetHasReceved: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitC2CBuy(String uid, String volume, String price, String tradeId, String pwd, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_BUY)
                .addParams("uid", uid)
                .addParams("volume", volume)
                .addParams("price", price)
                .addParams("trade_id", tradeId)
                .addParams("deal_psw", pwd)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitC2CBuy: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitC2CSell(String uid, String volume, String price, String tradeId, String pwd, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_SELL)
                .addParams("uid", uid)
                .addParams("volume", volume)
                .addParams("price", price)
                .addParams("trade_id", tradeId)
                .addParams("deal_psw", pwd)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitC2CSell: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void fetchC2COrderList(String type, String uid, String page, final Callback<C2COrderListEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_ORDER_LIST)
                .addParams("uid", uid)
                .addParams("page", String.valueOf(page))
                .addParams("type", type)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2COrderList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2COrderListEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取C2C购买列表
     *
     * @param id
     * @param callback
     */
    @Override
    public void fetchC2CBuyList(int page, String id, final Callback<C2cTransactionEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_C2C_BUY_LIST + "&id=" + id + "&page=" + page)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CBuyList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2cTransactionEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取C2C卖出列表
     *
     * @param id
     * @param callback
     */
    @Override
    public void fetchC2CSellList(int page, String id, final Callback<C2cTransactionEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_C2C_SELL_LIST + "&id=" + id + "&page=" + page)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CSellList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2cTransactionEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取C2C付款账户信息
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchC2CPayAccountInfo(String uid, final Callback<C2CPayAccountInfo> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_PAY_INFO)
                .addParams("uid", uid)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CPayInfo: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2CPayAccountInfo.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitAddAlipay(String uid, String name, String account, String qrImageUrl, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_ADD_ALIPAY_ACCOUNT)
                .addParams("uid", uid)
                .addParams("data[name]", name)
                .addParams("data[username]", account)
                .addParams("data[qrcode]", qrImageUrl)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitAddAlipay: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitAddWechat(String uid, String name, String account, String qrImageUrl, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_ADD_WECHAT_ACCOUNT)
                .addParams("uid", uid)
                .addParams("data[name]", name)
                .addParams("data[username]", account)
                .addParams("data[qrcode]", qrImageUrl)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitAddWechat: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitAddBankAccount(String uid, String khm, String idCard, String zhhm, String khh, String phone, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_ADD_BANK_ACCOUNT)
                .addParams("uid", uid)
                .addParams("data[khm]", khm)
                .addParams("data[idCard]", idCard)
                .addParams("data[zhhm]", zhhm)
                .addParams("data[khh]", khh)
                .addParams("data[phone]", phone)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitAddBank: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 删除C2C银行卡、支付宝、微信账号
     *
     * @param uid
     * @param type     del_alipay支付宝 del_bank银行卡 del_weixin微信
     * @param id
     * @param callback
     */
    @Override
    public void submitDeleteC2CAccount(String uid, String type, String id, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_DELETE_C2C_ACCOUNT + type)
                .addParams("uid", uid)
                .addParams("id", id)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitDeleteC2CAccount: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitC2CApply(String uid, String orderID, String content, String imageBase64, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_APPLY)
                .addParams("uid", uid)
                .addParams("orderId", orderID)
                .addParams("imgUrl", imageBase64)
                .addParams("description", content)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitC2CApply: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 折线图数据
     *
     * @param coinName
     * @param callback
     */
    @Override
    public void fetchLineChartData(String coinName, String time, final Callback<List<LineChartEntity>> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_LINE_CHART)
                .addParams("symbolName", coinName)
                .addParams("time", time)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchLineChartData: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<List<LineChartEntity>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 帮助中心详情
     *
     * @param catid
     * @param callback
     */
    @Override
    public void fetchHelpCenterDetailList(String catid, final Callback<List<Help>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_HELP_CENTER_DETAIL + "&catid=" + catid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchHelpCenterDetailList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<List<Help>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 帮助中心
     *
     * @param callback
     */
    @Override
    public void fetchHelpCenterList(final Callback<List<Help>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_HELP_CENTER_LIST + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchHelpCenterList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<List<Help>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取C2C交易价格
     *
     * @param coinName
     * @param callback
     */
    @Override
    public void fetchC2CTransactionPrice(String coinName, final Callback<C2CTransationPriceEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_C2C_TRANSATION_PRICE)
                .addParams("symbolName", coinName)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchC2CTransactionPrice: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), C2CTransationPriceEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

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
    @Override
    public void submitRegisterPHP(String account, String password, String password_, String introCode, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_REGISTER_PHP + SignTool.getSignParam())
                .addParams("data[username]", account)
                .addParams("data[password]", password)
                .addParams("data[password2]", password_)
                .addParams("data[type]", String.valueOf(2))
                .addParams("data[fintrouid]", introCode)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitRegisterPHP: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 算力记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchCalculationRecord(String uid, int page, final Callback<List<CalculationRecord>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_MINE_CALCULATION_RECORD + uid +"&page="+page+ SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {

                    @Override
                    public void onSuccess(int code, String s) {
                        Log.d("TAG", "fetchCalculationRecord:" + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<CalculationRecord>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 算力任务
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchCalculationTask(String uid, final Callback<CalculationTask> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_CALCULATION_TASK + uid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchCalculationTask: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), CalculationTask.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 矿区首页
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchMineAreaStatus(String uid, final Callback<List<MineAreaStatus>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_MINE_STATUS + uid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.d("TAG", "fetchMineAreaStatus:" + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<MineAreaStatus>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 挖矿
     *
     * @param uid
     * @param id
     * @param callback
     */
    @Override
    public void submitMining(String uid, String id, final Callback<BaseEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_MINE_MINING + uid + "&getid=" + id + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitMining: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 挖矿记录
     *
     * @param uid
     * @param page
     * @param callback
     */
    @Override
    public void fetchMiningRecord(String uid, int page, final Callback<List<MiningRecord>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_MINING_RECORD + "&fid=" + uid + "&page=" + page + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.d("TAG", "fetchMiningRecord:" + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<MiningRecord>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 开矿区
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchMiningArea(String uid, final Callback<MiningArea> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_MINING_AREA + uid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchMiningArea: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), MiningArea.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitOneKey(String uid, String type, boolean isOpen, final Callback<BaseEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_OPEN_ONE_KEY + "&fid=" + uid + "&type=" + type + "&status=" + (isOpen ? "1" : "0") + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitOneKey: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void submitPutMiningArea(String uid, String type, String num, String coinID, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_MINING_AREA_PUT + SignTool.getSignParam())
                .addParams("data[fid]", uid)
                .addParams("data[type]", type)
                .addParams("data[num]", num)
                .addParams("data[coin_id]", coinID)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitPutMiningArea: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 首页banner接口
     *
     * @param callback
     */
    @Override
    public void fetchBannerList(final Callback<List<Banner>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_HOME_BANNER + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchBannerList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<Banner>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 隐藏币种
     *
     * @param uid
     * @param coinName
     * @param callback
     */
    @Override
    public void submitHideCoin(String uid, String coinName, final Callback<BaseEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_HIDE_COIN + "&fid=" + uid + "&coin_name=" + coinName + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitHideCoin: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 隐藏币种列表
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchHideCoinList(String uid, final Callback<List<HideCoinEntity>> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_HIDE_COIN_LIST + uid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchHideCoinList: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonArray(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), new TypeToken<ArrayList<HideCoinEntity>>() {
                        }.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 判断交易是否收藏
     *
     * @param fid
     * @param treadId
     * @param callback
     */
    @Override
    public void fetchIsCollectTrading(String fid, String treadId, final Callback<Coin.CoinBean> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_FETCH_IS_COLLECT_TRADING + "&fid=" + fid + "&treadId=" + treadId + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchIsCollectTrading: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), Coin.CoinBean.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 收藏交易
     *
     * @param fid
     * @param tradeId
     * @param callback
     */
    @Override
    public void submitCollectTrading(String fid, String tradeId, final Callback<BaseEntity> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_COLLECT_TRADING_CENTER + "&fid=" + fid + "&treadId=" + tradeId + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "submitCollectTrading: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
//                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(),new TypeToken<List<Coin.CoinBean>>(){}.getType());
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
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
    public void updatePersonalInfo(String uid, String avatarUrl, String nick, String sex, String phone, final Callback<BaseEntity> callback) {
        OkHttpEx.post()
                .url(HOST_URL + URL_UPDATE_PERSONAL_INFO + SignTool.getSignParam())
                .addParams("data[fid]", uid)
                .addParams("data[fnickname]", nick)
                .addParams("data[favatar]", avatarUrl)
                .addParams("data[fsex]", sex)
                .addParams("data[ftelephone]", phone)
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "updatePersonalInfo: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), BaseEntity.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }

    /**
     * 获取本人信息接口
     *
     * @param uid
     * @param callback
     */
    @Override
    public void fetchPersonalInfo(String uid, final Callback<PersonalInfo> callback) {
        OkHttpEx.get()
                .url(HOST_URL + URL_PERSONAL_INFO + uid + SignTool.getSignParam())
                .enqueue(new OkCallback<String>(new OkTextParser()) {
                    @Override
                    public void onSuccess(int code, String s) {
                        Log.e("TAG", "fetchPersonalInfo: " + s);
                        BaseEntity baseEntity = JsonParse.getReturnForJsonObject(s);
                        DataResponse dataResponse = buildResponseWithData(baseEntity);
                        dataResponse.data = gson.fromJson(baseEntity.getJsonData(), PersonalInfo.class);
                        callback.onComplete(baseEntity.isSuccess(), baseEntity.getCode(), baseEntity.getMsg(), dataResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        callback.onComplete(false, 0, NETWORK_ERROR, buildResponseWithData(new BaseEntity()));
                    }
                });
    }
}
