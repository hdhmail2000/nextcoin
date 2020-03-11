package com.ifenduo.mahattan_x.controller.me;

import android.content.Intent;
import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.entity.Help;

public class HelpCenterDetailActivity extends BaseActivity {

    Help help;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_help_center_detail;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this,mToolbar);
        handleIntent();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,HelpCenterDetailListFragment.newInstance(help)).commit();
    }

    private void handleIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            help = bundle.getParcelable(Constant.BUNDLE_KEY_COMMON);
            bandData(help);
        }
    }

    private void bandData(Help help){
        if (help!=null){
            setNavigationCenter(help.getName());
        }
    }
}
