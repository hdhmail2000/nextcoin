package com.ifenduo.mahattan_x.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.entity.User;


/**
 * Created by xuefengyang on 2016/2/19.
 */
public class SharedPreferencesTool {
    public static final String SP_NAME_LOGIN_INFO = "sp_name_login_info";
    public static final String SP_KEY_LOGIN_INFO = "sp_key_login_info";
    public static final String SP_NAME_SAFE_SETTING = "sp_name_safe_setting";
    public static final String SP_KEY_SAFE_SETTING = "sp_key_safe_setting";

    private static User mUser;
    private static LoginInfo mLoginInfo;
    private static SafeSettingEntity mSafeSettingEntity;

    public static void saveLoginInfo(Context context, LoginInfo loginInfo) {
        if (loginInfo != null) {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME_LOGIN_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Gson gson = new Gson();
            editor.putString(SP_KEY_LOGIN_INFO, gson.toJson(loginInfo, LoginInfo.class));
            editor.commit();
            mUser = loginInfo.getUserinfo();
            mLoginInfo = loginInfo;
        }
    }

    public static LoginInfo getLoginInfo(Context context) {
        if (mLoginInfo == null) {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME_LOGIN_INFO, Context.MODE_PRIVATE);
            String loginJson = sp.getString(SP_KEY_LOGIN_INFO, "");
            if (!TextUtils.isEmpty(loginJson)) {
                Gson gson = new Gson();
                mLoginInfo = gson.fromJson(loginJson, LoginInfo.class);
                if (mLoginInfo != null) {
                    mUser = mLoginInfo.getUserinfo();
                }
            }
        }
        return mLoginInfo;
    }

    public static User getUser(Context context) {
        if (mUser == null) {
            getLoginInfo(context);
        }
        return mUser;
    }

    public static void saveSafeSetting(Context context, SafeSettingEntity safeSettingEntity) {
        mSafeSettingEntity = safeSettingEntity;
        if (mSafeSettingEntity != null) {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME_SAFE_SETTING, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Gson gson = new Gson();
            editor.putString(SP_KEY_SAFE_SETTING, gson.toJson(mSafeSettingEntity, SafeSettingEntity.class));
            editor.commit();
        } else {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME_SAFE_SETTING, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SP_KEY_SAFE_SETTING, "");
            editor.commit();
        }
    }

    public static SafeSettingEntity getSafeSetting(Context context) {
        if (mSafeSettingEntity == null) {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME_SAFE_SETTING, Context.MODE_PRIVATE);
            String json = sp.getString(SP_KEY_SAFE_SETTING, "");
            if (!TextUtils.isEmpty(json)) {
                Gson gson = new Gson();
                mSafeSettingEntity = gson.fromJson(json, SafeSettingEntity.class);
            }
        }
        return mSafeSettingEntity;
    }

    public static void clearLoginInfo(Context context) {
        SharedPreferences accountSp = context.getSharedPreferences(SP_NAME_LOGIN_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor accountEd = accountSp.edit();
        accountEd.clear();
        accountEd.commit();
    }

    public static void clearSafeSetting(Context context) {
        SharedPreferences safeSp = context.getSharedPreferences(SP_NAME_SAFE_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor safeEd = safeSp.edit();
        safeEd.clear();
        safeEd.commit();
    }

    public static void loginOut(Context context) {
        clearLoginInfo(context);
        clearSafeSetting(context);
        mUser = null;
        mLoginInfo = null;
    }
}
