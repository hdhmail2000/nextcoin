package com.ifenduo.mahattan_x.controller.mine;

import android.os.Bundle;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;

public class TaskActivity extends BaseActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("算力任务");
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, TaskFragment.newInstance()).commit();
    }
}
