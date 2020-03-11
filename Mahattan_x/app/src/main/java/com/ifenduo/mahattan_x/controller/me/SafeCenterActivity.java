package com.ifenduo.mahattan_x.controller.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_SET_TRADE_PWD_SUCCESS;
import static com.ifenduo.mahattan_x.event.BaseEvent.EVENT_CODE_REAL_AUTH_SUCCESS;

public class SafeCenterActivity extends BaseActivity {

    SafeSettingEntity mSafeSettingEntity;
    @BindView(R.id.lin_set_psd)
    LinearLayout linSetPsd;
    @BindView(R.id.password_status_text_view)
    TextView mPasswordStatusTextView;
    @BindView(R.id.text_safe_center_rel_name)
    TextView mVerifiedStatusTextView;
    @BindView(R.id.text_google_code)
    TextView mGoogleStatusTextView;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_safe_center;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchSafeSetting();
    }

    @Override
    protected boolean toolbarIsEnable() {
        return true;
    }

    @OnClick({R.id.lin_set_psd, R.id.lin_update_psd, R.id.lin_verify_real_name, R.id.lin_google_code})
    public void onViewClicked(View view) {
        if (mSafeSettingEntity == null) return;
        switch (view.getId()) {
            case R.id.lin_set_psd:
                openActivity(this, SetPsdActivity.class, null);
                break;
            case R.id.lin_update_psd:
                //交易密码
                if (mSafeSettingEntity.isBindTrade()) {
                    openActivity(this, ReSetTransactionPsdActivity.class, null);
                } else {
                    openActivity(this, SetTransactionPsdStep1Activity.class, null);
                }
                break;
            case R.id.lin_verify_real_name:
//                if (mSafeSettingEntity != null && mSafeSettingEntity.getFuser() != null) {
//                    SafeSettingEntity.FuserBean fuser = mSafeSettingEntity.getFuser();
//                    if (!fuser.isFhasrealvalidate() && mSafeSettingEntity.getIdentity() == null) {
//                        openActivity(this, VerifiedActivity.class, null);
//                    }
//                }
                if (mSafeSettingEntity == null) return;
                SafeSettingEntity.FuserBean fuser = mSafeSettingEntity.getFuser();
                if (fuser.isFhasrealvalidate()) {
                    VerifiedSuccessActivity.openActivity(this, VerifiedSuccessActivity.class, null);
                } else {
                    if (mSafeSettingEntity.getIdentity() == null) {
                        openActivity(SafeCenterActivity.this, VerifiedActivity.class, null);
                    } else if (mSafeSettingEntity.getIdentity() != null && mSafeSettingEntity.getIdentity().getFstatus() == 2) {
                        openActivity(SafeCenterActivity.this, VerifiedActivity.class, null);
                    } else if (mSafeSettingEntity.getIdentity() != null && mSafeSettingEntity.getIdentity().getFstatus() == 0) {
                        showToast("正在审核中，请耐心等待");
                    } else {
                        openActivity(SafeCenterActivity.this, VerifiedActivity.class, null);
                    }
                }
                break;
            case R.id.lin_google_code:
                if (mSafeSettingEntity == null) return;
                Bundle bundle = new Bundle();
                bundle.putString(Constant.BUNDLE_KEY_COMMON, mSafeSettingEntity.getDevice_name());
                openActivity(SafeCenterActivity.this, BindGoogleCodeActivity.class, bundle);
                break;
        }
    }

    /**
     * 获取安全设置状态
     */
    private void fetchSafeSetting() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().fetchSafeSettingDetail(loginInfo.getToken(), new Callback<SafeSettingEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<SafeSettingEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    mSafeSettingEntity = response.data;
                    bindUI(response.data);
                } else {
                    if (code == 401) {
                        showToast("登录已失效请重新登录");
                        openLogin();
                    } else {
                        showToast(msg);
                    }
                }
            }
        });
    }

    private void bindUI(SafeSettingEntity safeSettingEntity) {
        if (safeSettingEntity != null && safeSettingEntity.getFuser() != null) {
            SafeSettingEntity.FuserBean fuser = safeSettingEntity.getFuser();
            //实名认证
            if (fuser.isFhasrealvalidate()) {
                mVerifiedStatusTextView.setText("已认证");
            } else {
                if (mSafeSettingEntity.getIdentity() == null) {
                    mVerifiedStatusTextView.setText("未认证");
                } else if (mSafeSettingEntity.getIdentity() != null && mSafeSettingEntity.getIdentity().getFstatus() == 2) {
                    mVerifiedStatusTextView.setText("已驳回");
                } else if (mSafeSettingEntity.getIdentity() != null && mSafeSettingEntity.getIdentity().getFstatus() == 0) {
                    mVerifiedStatusTextView.setText("待审核");
                } else {
                    mVerifiedStatusTextView.setText("未知状态");
                }

            }
            //google 验证码
            if (fuser.isFgooglebind()) {
                mGoogleStatusTextView.setText("已绑定");
            } else {
                mGoogleStatusTextView.setText("未绑定");
            }

            //交易密码
            if (safeSettingEntity.isBindTrade()) {
                mPasswordStatusTextView.setText("已开启");
            } else {
                mPasswordStatusTextView.setText("未开启");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doAction(BaseEvent baseEvent) {
        if (baseEvent.getCode() == EVENT_CODE_REAL_AUTH_SUCCESS) {//实名认证成功
            mVerifiedStatusTextView.setText("审核中");
        } else if (baseEvent.getCode() == EVENT_CODE_SET_TRADE_PWD_SUCCESS) {//设置交易密码成功
            if (mSafeSettingEntity != null) {
                mSafeSettingEntity.setBindTrade(true);
                mPasswordStatusTextView.setText("已开启");
            }
        }else if (baseEvent.getCode() == BaseEvent.EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS) {//绑定谷歌成功
            mGoogleStatusTextView.setText("已绑定");
        }
    }

}
