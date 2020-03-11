package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.ifenduo.mahattan_x.R;

public class ShareDialog extends Dialog implements View.OnClickListener {
    ImageView mWXImageView;
    ImageView mGoogleImageView;
    ImageView mFaceBookImageView;
    ImageView mTwitterImageView;
    Button mCancelButton;

    OnShareTypeClickListener mOnShareTypeClickListener;

    public ShareDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
        initView();
    }

    public void setOnShareTypeClickListener(OnShareTypeClickListener onShareTypeClickListener) {
        mOnShareTypeClickListener = onShareTypeClickListener;
    }

    private void initView() {
        mWXImageView = findViewById(R.id.wx_image_view);
        mGoogleImageView = findViewById(R.id.google_image_view);
        mFaceBookImageView = findViewById(R.id.facebook_image_view);
        mTwitterImageView = findViewById(R.id.twitter_image_view);
        mCancelButton = findViewById(R.id.cancel_button);

        mWXImageView.setOnClickListener(this);
        mGoogleImageView.setOnClickListener(this);
        mFaceBookImageView.setOnClickListener(this);
        mTwitterImageView.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnShareTypeClickListener != null) {
            if (v.getId() == R.id.wx_image_view) {
                mOnShareTypeClickListener.onShareTypeClick(SHARE_TYPE_WECHAT);
            } else if (v.getId() == R.id.google_image_view) {
                mOnShareTypeClickListener.onShareTypeClick(SHARE_TYPE_GOOGLE);
            } else if (v.getId() == R.id.facebook_image_view) {
                mOnShareTypeClickListener.onShareTypeClick(SHARE_TYPE_FACE_BOOK);
            } else if (v.getId() == R.id.twitter_image_view) {
                mOnShareTypeClickListener.onShareTypeClick(SHARE_TYPE_TWITTER);
            }
        }
        dismiss();
    }


    public static final int SHARE_TYPE_WECHAT = 1;
    public static final int SHARE_TYPE_GOOGLE = 2;
    public static final int SHARE_TYPE_FACE_BOOK = 3;
    public static final int SHARE_TYPE_TWITTER = 4;

    public static interface OnShareTypeClickListener {
        void onShareTypeClick(int type);
    }
}
