package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.C2CCoinType;

public class ChooseCoinActivity extends BaseActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_choose_coin;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        C2CCoinType c2CCoinType = null;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            c2CCoinType = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, ChooseCoinFragment.newInstance(c2CCoinType)).commit();
    }

    public void setResultCoin(C2CCoinType coinType) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_KEY_COMMON, coinType);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
