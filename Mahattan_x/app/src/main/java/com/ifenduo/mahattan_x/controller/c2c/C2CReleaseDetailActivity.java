package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

public class C2CReleaseDetailActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_release_detail;
    }

    protected boolean isShouldCreateStatusBar() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        setNavigationCenter("订单详情");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String type = "";
        String id="";
        if (bundle != null) {
            id=bundle.getString(Constant.BUNDLE_KEY_COMMON,"");
            type=bundle.getString(Constant.BUNDLE_KEY_PAGE_TYPE,"");
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, C2CReleaseDetailFragment.newInstance(id,type)).commit();
    }



}
