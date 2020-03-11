package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.tools.EmojiInputFilter;

public class CommonInputDialog {
    private Context mContext;
    private Dialog mDialog;
    private LinearLayout mContainer;
    private TextView mTitleTextView;
    private EditText mEditText;
    private Button mNegButton;
    private Button mPosButton;
    private View mButtonLineView;

    private Display mDisplay;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private int mMaxInputLength = 50;

    public CommonInputDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public CommonInputDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_input, null);
        mContainer = view.findViewById(R.id.ll_message_dialog);
        mTitleTextView = view.findViewById(R.id.txt_title);
        mTitleTextView.setVisibility(View.GONE);
        mEditText = view.findViewById(R.id.edit_text);
        mNegButton = view.findViewById(R.id.btn_neg);
        mNegButton.setVisibility(View.GONE);
        mPosButton = view.findViewById(R.id.btn_pos);
        mPosButton.setVisibility(View.GONE);
        mButtonLineView = view.findViewById(R.id.img_line);
        mButtonLineView.setVisibility(View.GONE);
        mEditText.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(mMaxInputLength)});
        mDialog = new Dialog(mContext, R.style.MessageDialogStyle);
        mDialog.setContentView(view);

        mContainer.setLayoutParams(new FrameLayout.LayoutParams((int) (mDisplay.getWidth() * 0.80), FrameLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public CommonInputDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            mTitleTextView.setText("标题");
        } else {
            mTitleTextView.setText(title);
        }
        return this;
    }

    public CommonInputDialog setHint(String hint) {
        mEditText.setHint(hint);
        return this;
    }

    public CommonInputDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public CommonInputDialog setMaxInputLength(int maxLength) {
        mMaxInputLength = maxLength;
        mEditText.setFilters(new InputFilter[]{new EmojiInputFilter(), new InputFilter.LengthFilter(mMaxInputLength)});
        return this;
    }

    public CommonInputDialog setPositiveButton(String text, final CommonInputDialog.OnButtonClickListener onButtonClickListener) {
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
                    onButtonClickListener.onButtonClickListener(CommonInputDialog.this, v, mEditText.getText().toString());
                }
            }
        });
        return this;
    }

    public CommonInputDialog setNegativeButton(String text, final CommonInputDialog.OnButtonClickListener onButtonClickListener) {
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
                    onButtonClickListener.onButtonClickListener(CommonInputDialog.this, v, mEditText.getText().toString());
                }
            }
        });
        return this;
    }

    private void setLayout() {
//        if (!showTitle) {
//            mTitleTextView.setText("提示");
//            mTitleTextView.setVisibility(View.VISIBLE);
//        }

        if (showTitle) {
            mTitleTextView.setVisibility(View.VISIBLE);
        }

        mEditText.setVisibility(View.VISIBLE);

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
        InputMethodManager imm = (InputMethodManager) mDialog.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
        mDialog.dismiss();
    }

    public interface OnButtonClickListener {
        void onButtonClickListener(CommonInputDialog commonInputDialog, View button, String inputContent);
    }
}
