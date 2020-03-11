package com.ifenduo.mahattan_x.controller.me;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.GoogleKeyInfo;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class BindGoogleCodeActivity extends BaseActivity {
    @BindView(R.id.image_scan_code)
    ImageView mQRImageView;
    @BindView(R.id.text_account)
    TextView mAccountTextView;
    @BindView(R.id.text_key)
    TextView mKeyTextView;
    @BindView(R.id.text_next)
    TextView mNextButton;

    String mGoogleKey;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_bind_google_code;
    }


    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        EventBus.getDefault().register(this);
        setNavigationCenter("Google验证码");
        handIntent();
        mNextButton.setEnabled(false);
        fetchGoogleDevice();
        setOnLongClickListener();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doAction(BaseEvent baseEvent) {
        if (baseEvent.getCode() == BaseEvent.EVENT_CODE_GOOGLE_CODE_ADD_ADDRESS_SUCCESS) {//绑定谷歌成功
            finish();
        }
    }

    private void setOnLongClickListener() {
        mAccountTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyAccount();
                return false;
            }
        });
        mKeyTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyKey();
                return false;
            }
        });
    }

    private void handIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mAccountTextView.setText(bundle.getString(Constant.BUNDLE_KEY_COMMON));
        }
    }

    @OnClick({R.id.text_next, R.id.text_download_google, R.id.text_account, R.id.text_key})
    public void click(View view) {
        if (view.getId() == R.id.text_next) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_KEY_COMMON, mGoogleKey);
            openActivity(BindGoogleCodeActivity.this, VerifyGoogleCodeActivity.class, bundle);
        } else if (view.getId() == R.id.text_download_google) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://2bai.co/965563");
            intent.setData(content_url);
            startActivity(intent);
        }
    }

    private void fetchGoogleDevice() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(this);
        if (loginInfo == null) {
            showToast("您未登录，请先登录");
            openLogin();
            return;
        }
        showProgress();
        Api.getInstance().fetchGoogleDevice(loginInfo.getToken(), new Callback<GoogleKeyInfo>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<GoogleKeyInfo> response) {
                dismissProgress();
                if (isSuccess) {
                    if (response.data != null) {
                        mGoogleKey = response.data.getTotpKey();
//                        mAccountTextView.setText(response.data.getDevice_name());
                        mKeyTextView.setText(response.data.getTotpKey());
                        mNextButton.setEnabled(response.data != null && !TextUtils.isEmpty(mGoogleKey));
                        new MyAsyncTask(BindGoogleCodeActivity.this).execute(response.data.getQecode());
                    }
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

    public void loadQR(Bitmap bitmap) {
        if (bitmap == null) return;
        mQRImageView.setImageBitmap(bitmap);
    }


    static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        WeakReference<BindGoogleCodeActivity> weakReference;

        public MyAsyncTask(BindGoogleCodeActivity activity) {
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

    private void copyAccount() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(mAccountTextView.getText().toString());
        showToast("复制成功");
    }

    private void copyKey() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(mKeyTextView.getText().toString());
        showToast("复制成功");
    }

}
