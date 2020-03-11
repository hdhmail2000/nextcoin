package com.ifenduo.mahattan_x.controller.wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.ChargeRecord;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.RechargeAddressAndRecord;
import com.ifenduo.mahattan_x.entity.Wallet;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ChargeMoneyActivity extends BaseActivity {
    @BindView(R.id.text_code)
    TextView mTvCode;
    @BindView(R.id.image_qr_code)
    ImageView mQRImageView;

    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;
    List<ChargeRecord> mChargeRecordList;

    String mCoinId = "";
    Wallet mWallet;
    String mEthID = "";

    public static void openChargeMoney(Context context, Wallet wallet, String ethID) {
        Intent intent = new Intent(context, ChargeMoneyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("wallet", wallet);
        bundle.putString("ethID", ethID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_charge_money;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        handleIntent();
        if (mWallet != null) {
            setNavigationCenter("我的 " + mWallet.getShortName() + " 收款地址");
        }
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        fetchRechargeAddress();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mWallet = bundle.getParcelable("wallet");
            mEthID = bundle.getString("ethID", "");
            if (mWallet != null) {
                mCoinId = String.valueOf(mWallet.getCoinId());
            }
        }
    }

    @OnClick({R.id.text_copy_qr_code})
    public void click(View view) {
        if (view.getId() == R.id.text_copy_qr_code) {
            String copy = mTvCode.getText().toString();
            //创建一个新的文本clip对象
            mClipData = ClipData.newPlainText("Simple test", copy);
            //把clip对象放在剪贴板中
            mClipboardManager.setPrimaryClip(mClipData);
            showToast("文本已经复制成功！");
        }
    }

    private void fetchRechargeAddress() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) return;
        Api.getInstance().fetchRechargeAddressAndRecord(loginInfo.getToken(), mCoinId, new Callback<RechargeAddressAndRecord>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<RechargeAddressAndRecord> response) {
                if (isSuccess) {
                    if (response.data != null && response.data.getRechargeAddress() != null) {
                        mTvCode.setText(response.data.getRechargeAddress().getFadderess());
                        if (response.data != null && response.data.getPage() != null) {
                            mChargeRecordList = response.data.getPage().getData();
                        } else {
                            mChargeRecordList = null;
                        }
                        new MyAsyncTask(ChargeMoneyActivity.this).execute(response.data.getRechargeAddress().getFadderess());
                    }
                } else {
                    mChargeRecordList = null;
                    if (code == 401) {
                        showToast("登录已失效请重新登录");
                        openLogin();
                    } else if (!TextUtils.isEmpty(msg)&&msg.contains("当前币种已经禁止充币")) {
                        fetchETHRechargeAddress();
                    } else {
                        showToast(msg);
                    }
                }
            }
        });
    }

    private void fetchETHRechargeAddress() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) return;
        Api.getInstance().fetchRechargeAddressAndRecord(loginInfo.getToken(), mEthID, new Callback<RechargeAddressAndRecord>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<RechargeAddressAndRecord> response) {
                if (isSuccess) {
                    if (response.data != null && response.data.getRechargeAddress() != null) {
                        mTvCode.setText(response.data.getRechargeAddress().getFadderess());
                        if (response.data != null && response.data.getPage() != null) {
                            mChargeRecordList = response.data.getPage().getData();
                        } else {
                            mChargeRecordList = null;
                        }
                        new MyAsyncTask(ChargeMoneyActivity.this).execute(response.data.getRechargeAddress().getFadderess());
                    }
                } else {
                    mChargeRecordList = null;
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

    public void loadQR(Bitmap bitmap) {
        if (bitmap == null) return;
        mQRImageView.setImageBitmap(bitmap);
    }

    static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        WeakReference<ChargeMoneyActivity> weakReference;

        public MyAsyncTask(ChargeMoneyActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (strings.length > 0 && weakReference != null && weakReference.get() != null) {
                return QRCodeEncoder.syncEncodeQRCode(strings[0], BGAQRCodeUtil.dp2px(weakReference.get(), 100));
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().loadQR(bitmap);
            }
        }
    }
}


