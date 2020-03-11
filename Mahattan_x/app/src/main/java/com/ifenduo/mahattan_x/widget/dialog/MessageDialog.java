package com.ifenduo.mahattan_x.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;


public class MessageDialog {

    private Context mContext;
    private Dialog mDialog;
    private LinearLayout mContainer;
    private TextView mTitleTextView;
    private TextView mMessageTextView;
    private Button mNegButton;
    private Button mPosButton;
    private View mButtonLineView;
    private Display mDisplay;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public MessageDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public MessageDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_message, null);
        mContainer = view.findViewById(R.id.ll_message_dialog);
        mTitleTextView = view.findViewById(R.id.txt_title);
        mTitleTextView.setVisibility(View.GONE);
        mMessageTextView = view.findViewById(R.id.txt_msg);
        mMessageTextView.setVisibility(View.GONE);
        mNegButton =  view.findViewById(R.id.btn_neg);
        mNegButton.setVisibility(View.GONE);
        mPosButton = view.findViewById(R.id.btn_pos);
        mPosButton.setVisibility(View.GONE);
        mButtonLineView = view.findViewById(R.id.img_line);
        mButtonLineView.setVisibility(View.GONE);

        mDialog = new Dialog(mContext, R.style.MessageDialogStyle);
        mDialog.setContentView(view);

        mContainer.setLayoutParams(new FrameLayout.LayoutParams((int) (mDisplay.getWidth() * 0.80), FrameLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public MessageDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            mTitleTextView.setText("标题");
        } else {
            mTitleTextView.setText(title);
        }
        return this;
    }

    public MessageDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            mMessageTextView.setText("内容");
        } else {
            mMessageTextView.setText(msg);
        }
        return this;
    }

    public MessageDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public MessageDialog setPositiveButton(String text, final OnButtonClickListener onButtonClickListener) {
        showPosBtn = true;
        if ("".equals(text)) {
            mPosButton.setText("确定");
        } else {
            mPosButton.setText(text);
        }
        mPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClickListener(MessageDialog.this, v);
                }
            }
        });
        return this;
    }

    public MessageDialog setNegativeButton(String text, final OnButtonClickListener onButtonClickListener) {
        showNegBtn = true;
        if ("".equals(text)) {
            mNegButton.setText("取消");
        } else {
            mNegButton.setText(text);
        }
        mNegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClickListener(MessageDialog.this, v);
                }
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            mTitleTextView.setText("提示");
            mTitleTextView.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            mTitleTextView.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            mMessageTextView.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            mPosButton.setText("确定");
            mPosButton.setVisibility(View.VISIBLE);
            mPosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            mPosButton.setVisibility(View.VISIBLE);
            mNegButton.setVisibility(View.VISIBLE);
            mButtonLineView.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            mPosButton.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && showNegBtn) {
            mNegButton.setVisibility(View.VISIBLE);
        }
    }

    public void show() {
        setLayout();
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public interface OnButtonClickListener {
        void onButtonClickListener(MessageDialog messageDialog, View button);
    }

}
