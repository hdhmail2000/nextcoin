package com.ifenduo.mahattan_x.controller.wallet;

import android.os.Bundle;
import android.view.View;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private static int REQUEST_CODE_SCAN=1;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, WalletListFragment.newInstance()).commit();
        initView();
    }

    private void initView() {
        setNavigationCenter("钱包");
        setNavigationRight(R.drawable.ic_add, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_toolbar_right_icon) {
            openActivity(this, AddCoinActivity.class, null);
        }
    }
}
