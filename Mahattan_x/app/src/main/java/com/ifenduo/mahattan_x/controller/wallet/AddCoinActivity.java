package com.ifenduo.mahattan_x.controller.wallet;

import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

public class AddCoinActivity extends BaseActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_coin;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, AddCoinListFragment.newInstance()).commit();
        setNavigationCenter("添加币种");
    }
}
