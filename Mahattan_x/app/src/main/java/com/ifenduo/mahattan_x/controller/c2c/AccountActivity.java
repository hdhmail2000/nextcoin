package com.ifenduo.mahattan_x.controller.c2c;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.C2CPayAccountInfo;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.AddC2CAccountDialog;
import com.ifenduo.mahattan_x.widget.dialog.ShowQrDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity implements AddC2CAccountDialog.OnItemViewOnClickListener {
    @BindView(R.id.bank_account_name_text_view)
    TextView bankAccountNameTextView;
    @BindView(R.id.bank_name_text_view)
    TextView bankNameTextView;
    @BindView(R.id.bank_account_text_view)
    TextView bankAccountTextView;
    @BindView(R.id.bank_container)
    RelativeLayout bankContainer;
    @BindView(R.id.alipay_name_text_view)
    TextView alipayNameTextView;
    @BindView(R.id.alipay_account_text_view)
    TextView alipayAccountTextView;
    @BindView(R.id.alipay_container)
    RelativeLayout alipayContainer;
    @BindView(R.id.wechat_name_text_view)
    TextView wechatNameTextView;
    @BindView(R.id.wechat_account_text_view)
    TextView wechatAccountTextView;
    @BindView(R.id.wechat_container)
    RelativeLayout wechatContainer;
    @BindView(R.id.text_add_account)
    TextView addButton;

    User mUser;
    AddC2CAccountDialog mAddC2CAccountDialog;
    C2CPayAccountInfo mC2CPayAccountInfo;
    ShowQrDialog mShowQrDialog;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_account;
    }

    @Override
    protected int getToolbarColor() {
        return Color.parseColor("#363672");
    }

    @Override
    protected int getStatusBarColor() {
        return Color.parseColor("#363672");
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setNavigationCenter("收款账号(C2C)");

        bankContainer.setVisibility(View.GONE);
        alipayContainer.setVisibility(View.GONE);
        wechatContainer.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        fetchPayAccount();
    }

    private void fetchPayAccount() {
        if (mUser == null) return;
        Api.getInstance().fetchC2CPayAccountInfo(mUser.getFid(), new Callback<C2CPayAccountInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<C2CPayAccountInfo> response) {
                mC2CPayAccountInfo = response.data;
                bindData(response.data);
            }
        });
    }

    private void deleteAccount(String type, String id) {
        mUser = SharedPreferencesTool.getUser(getApplicationContext());
        if (mUser == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().submitDeleteC2CAccount(mUser.getFid(), type, id, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                dismissProgress();
                if (isSuccess) {
                    fetchPayAccount();
                } else {
                    showToast(msg);
                }
            }
        });
    }

    private void bindData(C2CPayAccountInfo c2CPayAccountInfo) {
        if (c2CPayAccountInfo != null) {
            if (c2CPayAccountInfo.getBank() != null) {
                bankContainer.setVisibility(View.VISIBLE);
                bankAccountNameTextView.setText(c2CPayAccountInfo.getBank().getKhm());
                bankNameTextView.setText(c2CPayAccountInfo.getBank().getKhh());
                bankAccountTextView.setText(c2CPayAccountInfo.getBank().getZhhm());
            } else {
                bankContainer.setVisibility(View.GONE);
            }

            if (c2CPayAccountInfo.getAlipay() == null) {
                alipayContainer.setVisibility(View.GONE);
            } else {
                alipayContainer.setVisibility(View.VISIBLE);
                alipayNameTextView.setText(c2CPayAccountInfo.getAlipay().getName());
                alipayAccountTextView.setText(c2CPayAccountInfo.getAlipay().getUsername());
            }

            if (c2CPayAccountInfo.getWeixin() == null) {
                wechatContainer.setVisibility(View.GONE);
            } else {
                wechatContainer.setVisibility(View.VISIBLE);
                wechatNameTextView.setText(c2CPayAccountInfo.getWeixin().getName());
                wechatAccountTextView.setText(c2CPayAccountInfo.getWeixin().getUsername());
            }
        } else {
            bankContainer.setVisibility(View.GONE);
            alipayContainer.setVisibility(View.GONE);
            wechatContainer.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
        }

        if (bankContainer.getVisibility() == View.VISIBLE && wechatContainer.getVisibility() == View.VISIBLE && alipayContainer.getVisibility() == View.VISIBLE) {
            addButton.setVisibility(View.GONE);
        } else {
            addButton.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.alipay_delete_button, R.id.wechat_delete_button, R.id.bank_delete_button, R.id.text_add_account, R.id.alpay_qr, R.id.wechat_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_delete_button:
                if (mC2CPayAccountInfo == null || mC2CPayAccountInfo.getAlipay() == null || TextUtils.isEmpty(mC2CPayAccountInfo.getAlipay().getId()))
                    return;
                deleteAccount("del_alipay", mC2CPayAccountInfo.getAlipay().getId());
                break;
            case R.id.wechat_delete_button:
                if (mC2CPayAccountInfo == null || mC2CPayAccountInfo.getWeixin() == null || TextUtils.isEmpty(mC2CPayAccountInfo.getWeixin().getId()))
                    return;
                deleteAccount("del_weixin", mC2CPayAccountInfo.getWeixin().getId());
                break;
            case R.id.bank_delete_button:
                if (mC2CPayAccountInfo == null || mC2CPayAccountInfo.getBank() == null || TextUtils.isEmpty(mC2CPayAccountInfo.getBank().getId()))
                    return;
                deleteAccount("del_bank", mC2CPayAccountInfo.getBank().getId());
                break;
            case R.id.text_add_account:
                showAddC2CAccountDialog();
                break;
            case R.id.alpay_qr:
                if (mC2CPayAccountInfo != null && mC2CPayAccountInfo.getAlipay() != null && !TextUtils.isEmpty(mC2CPayAccountInfo.getAlipay().getQrcode())) {
                    showQrDialog(mC2CPayAccountInfo.getAlipay().getQrcode(), "我的支付宝");
                }
                break;
            case R.id.wechat_qr:
                if (mC2CPayAccountInfo != null && mC2CPayAccountInfo.getWeixin() != null && !TextUtils.isEmpty(mC2CPayAccountInfo.getWeixin().getQrcode())) {
                    showQrDialog(mC2CPayAccountInfo.getWeixin().getQrcode(), "我的微信");
                }
                break;
        }
    }

    private void showQrDialog(String path, String title) {
        if (mShowQrDialog == null) {
            mShowQrDialog = new ShowQrDialog(this);
        }
        if (!mShowQrDialog.isShowing()) {
            mShowQrDialog.show();
        }
        mShowQrDialog.setQrImage(path);
        mShowQrDialog.setTitle(title);
    }

    private void showAddC2CAccountDialog() {
        if (mAddC2CAccountDialog == null) {
            mAddC2CAccountDialog = new AddC2CAccountDialog(this);
            mAddC2CAccountDialog.setOnItemViewOnClickListener(this);
        }
        mAddC2CAccountDialog.setShowBanek(!(bankContainer.getVisibility() == View.VISIBLE));
        mAddC2CAccountDialog.setShowAlipay(!(alipayContainer.getVisibility() == View.VISIBLE));
        mAddC2CAccountDialog.setShowWechat(!(wechatContainer.getVisibility() == View.VISIBLE));

        mAddC2CAccountDialog.show();
    }

    @Override
    public void onItemViewOnClick(int itemViewType) {
        if (itemViewType == AddC2CAccountDialog.ITEM_VIEW_TYPE_BANK) {
            openActivity(this, AddBankCardActivity.class, null);
        } else if (itemViewType == AddC2CAccountDialog.ITEM_VIEW_TYPE_ALIPAY) {
            openActivity(this, AddAlipayActivity.class, null);
        } else if (itemViewType == AddC2CAccountDialog.ITEM_VIEW_TYPE_WECHAT) {
            openActivity(this, AddWechatActivity.class, null);
        }
    }

}
