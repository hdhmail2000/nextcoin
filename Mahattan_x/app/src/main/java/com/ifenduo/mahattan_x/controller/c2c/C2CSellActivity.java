package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseEntity;
import com.ifenduo.mahattan_x.entity.C2CTransactionListEntity;
import com.ifenduo.mahattan_x.entity.User;
import com.ifenduo.mahattan_x.tools.CoinValueFilter;
import com.ifenduo.mahattan_x.tools.NumberUtil;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.ifenduo.mahattan_x.widget.dialog.InputPasswordDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class C2CSellActivity extends BaseActivity implements InputPasswordDialog.OnInputCallBack {

    @BindView(R.id.edit_sell_price)
    TextView editSellPrice;
    @BindView(R.id.edit_buy_num)
    EditText editBuyNum;
    @BindView(R.id.name_text_view)
    TextView nameTextView;
    @BindView(R.id.sum_money_text_view)
    TextView sumMoneyTextView;

    C2CTransactionListEntity entity;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_sell;
    }

    protected boolean isShouldCreateStatusBar() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        setToolbar();
        handleIntent();


        setupInputListener();
        editBuyNum.setFilters(new InputFilter[]{new CoinValueFilter()});
        if (entity != null) {
            nameTextView.setText(entity.getSymbol_name());
            editSellPrice.setText(NumberUtil.formatMoney(entity.getOrder_price()));
            editBuyNum.setHint("出售数量(必须大于或等于" + NumberUtil.formatMoney(entity.getMin_value()) + ")");
        }

    }

    private void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            entity = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
    }

    @OnClick({R.id.button_sell})
    public void click(View view) {
        if (view.getId() == R.id.button_sell) {
            User user = SharedPreferencesTool.getUser(getApplicationContext());
            if (user == null) {
                showToast("你尚未登录，请先登录");
                openLogin();
                return;
            }
            if (entity == null) return;
            String num = editBuyNum.getText().toString();
            if (TextUtils.isEmpty(num)) {
                showToast("请输入出售数量");
                return;
            } else {
                double num_ = Double.parseDouble(num);
                if (num_ < Double.parseDouble(entity.getMin_value())) {
                    showToast("出售数量必须大于或等于" + entity.getMin_value());
                    return;
                }
            }
            showInputDialog();
        }
    }

    private void showInputDialog() {
        InputPasswordDialog inputPasswordDialog = new InputPasswordDialog(this);
        inputPasswordDialog.setOnInputCallBack(this);
        inputPasswordDialog.show();
    }

    private void setToolbar() {
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("卖出");
        setNavigationRight("我的订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.BUNDLE_KEY_COMMON, "2");
                openActivity(C2CSellActivity.this, C2CMyOrderListActivity.class, bundle);
            }
        });
    }

    private void setupInputListener() {
        editBuyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (entity == null) return;
                double price = Double.parseDouble(entity.getOrder_price());
                double num = Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0.00" : s.toString());
                sumMoneyTextView.setText(NumberUtil.formatMoney(price * num));
            }
        });
    }


    @Override
    public void getInputPassword(String password) {
        submitSell(password);

    }

    private void submitSell(String pwd) {
        if (entity == null) return;
        User user = SharedPreferencesTool.getUser(getApplicationContext());
        if (user == null) {
            showToast("你尚未登录，请先登录");
            openLogin();
            return;
        }
        Api.getInstance().submitC2CSell(user.getFid(), editBuyNum.getText().toString(), entity.getOrder_price(), entity.getId(), pwd, new Callback<BaseEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<BaseEntity> response) {
                if (isSuccess) {
                    showToast("卖出成功");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BUNDLE_KEY_COMMON, "2");
                    openActivity(C2CSellActivity.this, C2CMyOrderListActivity.class, bundle);
                    finish();
                } else {
                    showToast(msg);
                }
            }
        });
    }
}
