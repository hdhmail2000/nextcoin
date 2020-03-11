package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;


public class AddC2CAccountDialog extends Dialog implements View.OnClickListener {
    public static final int ITEM_VIEW_TYPE_BANK = 1;
    public static final int ITEM_VIEW_TYPE_WECHAT = 2;
    public static final int ITEM_VIEW_TYPE_ALIPAY = 3;

    TextView bankTextView;
    TextView alipayTextView;
    TextView wechatTextView;
    TextView cancelTextView;

    private boolean isShowBanek = true;
    private boolean isShowAlipay = true;
    private boolean isShowWechat = true;
    private OnItemViewOnClickListener mOnItemViewOnClickListener;


    public AddC2CAccountDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_c2c_account);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
        initView();
    }

    private void initView() {
        bankTextView = findViewById(R.id.bank_text_view);
        alipayTextView = findViewById(R.id.alipay_text_view);
        wechatTextView = findViewById(R.id.wechat_text_view);
        cancelTextView = findViewById(R.id.cancel_text_view);

        alipayTextView.setOnClickListener(this);
        wechatTextView.setOnClickListener(this);
        bankTextView.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);

        bankTextView.setVisibility(isShowBanek ? View.VISIBLE : View.GONE);
        alipayTextView.setVisibility(isShowAlipay ? View.VISIBLE : View.GONE);
        wechatTextView.setVisibility(isShowWechat ? View.VISIBLE : View.GONE);
    }

    public void setOnItemViewOnClickListener(OnItemViewOnClickListener onItemViewOnClickListener) {
        this.mOnItemViewOnClickListener = onItemViewOnClickListener;
    }

    public void setShowBanek(boolean showBanek) {
        isShowBanek = showBanek;
        if (bankTextView != null) {
            bankTextView.setVisibility(isShowBanek ? View.VISIBLE : View.GONE);
        }
    }

    public void setShowAlipay(boolean showAlipay) {
        isShowAlipay = showAlipay;
        if (alipayTextView != null) {
            alipayTextView.setVisibility(isShowAlipay ? View.VISIBLE : View.GONE);
        }
    }

    public void setShowWechat(boolean showWechat) {
        isShowWechat = showWechat;
        if (wechatTextView != null) {
            wechatTextView.setVisibility(isShowWechat ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_text_view:
                if (mOnItemViewOnClickListener != null) {
                    mOnItemViewOnClickListener.onItemViewOnClick(ITEM_VIEW_TYPE_BANK);
                }
                dismiss();
                break;
            case R.id.alipay_text_view:
                if (mOnItemViewOnClickListener != null) {
                    mOnItemViewOnClickListener.onItemViewOnClick(ITEM_VIEW_TYPE_ALIPAY);
                }
                dismiss();
                break;
            case R.id.wechat_text_view:
                if (mOnItemViewOnClickListener != null) {
                    mOnItemViewOnClickListener.onItemViewOnClick(ITEM_VIEW_TYPE_WECHAT);
                }
                dismiss();
                break;
            case R.id.cancel_text_view:
                dismiss();
                break;
        }
    }

    public interface OnItemViewOnClickListener {
        void onItemViewOnClick(int itemViewType);
    }

}
