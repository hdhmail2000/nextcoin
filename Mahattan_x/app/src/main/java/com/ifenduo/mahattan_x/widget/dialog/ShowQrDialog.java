package com.ifenduo.mahattan_x.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ifenduo.mahattan_x.R;

//import com.bumptech.glide.Glide;

/**
 * Created by ll on 2018/3/8.
 */

public class ShowQrDialog extends Dialog implements View.OnClickListener {
    ImageView mImageView;
    TextView mTextView;
    ImageButton mButton;
    String mTitle;
    String mQRPath="";

    public ShowQrDialog(@NonNull Context context) {
        super(context, R.style.MessageDialogStyle);
    }

    public void setTitle(String title) {
        this.mTitle = title;
        if (mTextView != null) {
            mTextView.setText(title);
        }
    }

    public void setQrImage(String path){
        mQRPath=path;
        Log.d("TAG", "setQrImage: "+path);
        if(mImageView!=null){
            Glide.with(getContext()).load(mQRPath).into(mImageView);
        }
    }
    public void setQrImage(@DrawableRes int drawable){
//        Glide.with(getContext()).load(drawable).into(mImageView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_qr);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        getWindow().setGravity(Gravity.CENTER);
        onWindowAttributesChanged(attributes);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mImageView = findViewById(R.id.image_dialog_show_qr);
        mTextView = findViewById(R.id.text_dialog_show_qr);
        mButton = findViewById(R.id.button_dialog_show_qr);
        mButton.setOnClickListener(this);
        Glide.with(getContext()).load(mQRPath).into(mImageView);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
