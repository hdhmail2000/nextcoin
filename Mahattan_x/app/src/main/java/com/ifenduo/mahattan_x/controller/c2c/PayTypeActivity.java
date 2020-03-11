package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PayTypeActivity extends BaseActivity {
    @BindView(R.id.title_text_view)
    TextView titleTextView;
    @BindView(R.id.alipay_check_box)
    ImageView alipayCheckBox;
    @BindView(R.id.weixin_check_box)
    ImageView weixinCheckBox;
    @BindView(R.id.bank_check_box)
    ImageView bankCheckBox;
    List<String> mCheckedList;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_pay_type;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = "支付方式";
        if (bundle != null) {
            mCheckedList = bundle.getStringArrayList(Constant.BUNDLE_KEY_COMMON);
            title = bundle.getString(Constant.BUNDLE_KEY_PAGE_TITLE, "支付方式");
        }

        titleTextView.setText(title);

        if (mCheckedList == null) {
            mCheckedList = new ArrayList<>();
        }
        alipayCheckBox.setVisibility(mCheckedList.contains("支付宝") ? View.VISIBLE : View.GONE);
        weixinCheckBox.setVisibility(mCheckedList.contains("微信") ? View.VISIBLE : View.GONE);
        bankCheckBox.setVisibility(mCheckedList.contains("银行卡") ? View.VISIBLE : View.GONE);

        setNavigationRight("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultPayType();
            }
        });
    }

    @OnClick({R.id.alipay_container, R.id.weixin_container, R.id.bank_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_container:
                if (mCheckedList.contains("支付宝")) {
                    mCheckedList.remove("支付宝");
                } else {
                    mCheckedList.add("支付宝");
                }
                alipayCheckBox.setVisibility(mCheckedList.contains("支付宝") ? View.VISIBLE : View.GONE);
                break;
            case R.id.weixin_container:
                if (mCheckedList.contains("微信")) {
                    mCheckedList.remove("微信");
                } else {
                    mCheckedList.add("微信");
                }
                weixinCheckBox.setVisibility(mCheckedList.contains("微信") ? View.VISIBLE : View.GONE);
                break;
            case R.id.bank_container:
                if (mCheckedList.contains("银行卡")) {
                    mCheckedList.remove("银行卡");
                } else {
                    mCheckedList.add("银行卡");
                }
                bankCheckBox.setVisibility(mCheckedList.contains("银行卡") ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void setResultPayType() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constant.BUNDLE_KEY_COMMON, (ArrayList<String>) mCheckedList);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
