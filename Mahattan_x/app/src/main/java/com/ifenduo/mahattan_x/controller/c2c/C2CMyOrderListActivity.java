package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

public class C2CMyOrderListActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_my_order_list;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        setNavigationCenter("我的订单");
        String type="1";
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            type=bundle.getString(Constant.BUNDLE_KEY_COMMON,"1");
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,C2CMyOrderListFragment.newInstance(type)).commit();
    }


}
