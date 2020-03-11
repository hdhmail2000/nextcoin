package com.ifenduo.mahattan_x.controller.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.MHXApplication;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.FromatTool;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.MessageDialog;
import com.ifenduo.wheelpicker.picker.SinglePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.price_type_text_view)
    TextView mPriceTypeTextView;
    @BindView(R.id.account_text_view)
    TextView mAccountTextView;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        User user = SharedPreferencesTool.getUser(getApplicationContext());
        if (user != null) {
            mAccountTextView.setText(FromatTool.phoneNumberFormat(user.getFloginname()));
        }
        mPriceTypeTextView.setText("人民币");
    }


    @OnClick({R.id.price_type_container, R.id.login_out_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.price_type_container:
//                showPriceTypePicker();
                break;
            case R.id.login_out_container:
                showLoginOutDialog();
                break;
        }
    }

    private void showPriceTypePicker() {
        List<String> type = new ArrayList<>();
        type.add("人民币");
        type.add("美元");
        final SinglePicker singlePicker = new SinglePicker(this, type);
//        singlePicker.setSubmitImage(R.drawable.icon_picker_ok);
        singlePicker.setTitleText("计价方式");
        singlePicker.setTitleTextSize(14);
        singlePicker.setTextSize(12);
//        singlePicker.setCancelImage(R.drawable.icon_picker_cancel);
        singlePicker.setDividerColor(getResources().getColor(R.color.colorGrayText));
        singlePicker.setTopLineColor(getResources().getColor(R.color.colorGrayText));
//        singlePicker.setTextColor(getResources().getColor(R.color.colorBlackText));
        singlePicker.setShowCancelImageButton(true);
        singlePicker.setShowSubmitImageButton(true);
        String sex = mPriceTypeTextView.getText().toString();
        if ("男".equals(sex)) {
            singlePicker.setSelectedIndex(0);
        } else if ("女".equals(sex)) {
            singlePicker.setSelectedIndex(1);
        }
        singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener() {
            @Override
            public void onItemPicked(int index, Object item) {
                if (index == 0) {
                    mPriceTypeTextView.setText("人民币");
                } else if (index == 1) {
                    mPriceTypeTextView.setText("美元");
                }
                singlePicker.dismiss();
            }
        });
        singlePicker.show();
    }

    private void showLoginOutDialog() {
        MessageDialog messageDialog = new MessageDialog(this);
        messageDialog.builder();
        messageDialog.setMsg("是否确认退出登录？");
        messageDialog.setNegativeButton("取消", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setPositiveButton("确定", new MessageDialog.OnButtonClickListener() {
            @Override
            public void onButtonClickListener(MessageDialog messageDialog, View button) {
                MHXApplication.getInstance().loginOut(SettingActivity.this);
                messageDialog.dismiss();
            }
        });
        messageDialog.show();
    }
}
