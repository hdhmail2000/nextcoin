package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.RechargeAddress;
import com.ifenduo.mahattan_x.entity.Wallet;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawPlaceActivity extends BaseActivity {
    @BindView(R.id.btn_add_address)
    Button mBtnAddAddress;

    Wallet mWallet;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_with_draw_place;
    }

    @Override
    protected boolean toolbarIsEnable() {
        return true;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        setNavigationCenter("地址簿");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            mWallet=bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, WithDrawPlaceListFragment.newInstance(mWallet)).commit();
    }

    @OnClick({R.id.btn_add_address})
    public void click(View view) {
        if (view.getId() == R.id.btn_add_address) {
            if(mWallet==null)return;
            Bundle bundle=new Bundle();
            bundle.putParcelable(Constant.BUNDLE_KEY_COMMON,mWallet);
            openActivity(WithDrawPlaceActivity.this, AddAddressActivity.class, bundle);
        }
    }

    public void getSelectedAddress(RechargeAddress withdrawAddress) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("hotCoin", withdrawAddress);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
