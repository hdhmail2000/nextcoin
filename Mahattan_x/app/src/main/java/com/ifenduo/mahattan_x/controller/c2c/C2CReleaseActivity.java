package com.ifenduo.mahattan_x.controller.c2c;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BasePagerAdapter;
import com.ifenduo.mahattan_x.widget.viewpager.NoTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class C2CReleaseActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    NoTouchViewPager mViewPager;

    List<String> title = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    BasePagerAdapter basePagerAdapter;

    String type;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_c2c_release;
    }

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        StatusBarTool.setTranslucentForImageView(this, mToolbar);
        setNavigationCenter("发布");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            type = bundle.getString("type");
        }
        initViewPager();
    }


    private void initViewPager() {
        title.add("买入");
        title.add("卖出");
        title.add("我的发布");

        fragmentList.add(C2CReleaseBuyFragment.newInstance());
        fragmentList.add(C2CReleaseSellFragment.newInstance());
        fragmentList.add(C2CMyReleaseListFragment.newInstance());
        basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, title);
        mViewPager.setAdapter(basePagerAdapter);
        if ("buy".equals(type)) {
            mViewPager.setCurrentItem(0);
        } else if ("sell".equals(type)) {
            mViewPager.setCurrentItem(1);
        }
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
