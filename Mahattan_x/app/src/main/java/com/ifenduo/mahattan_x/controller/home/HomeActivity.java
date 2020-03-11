package com.ifenduo.mahattan_x.controller.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.FrameLayout;

import com.ifenduo.common.tool.StatusBarTool;
import com.ifenduo.lib_bottomnavigation.BaseTab;
import com.ifenduo.lib_bottomnavigation.BottomNavigation;
import com.ifenduo.mahattan_x.R;
import com.ifenduo.mahattan_x.api.Api;
import com.ifenduo.mahattan_x.api.Callback;
import com.ifenduo.mahattan_x.api.DataResponse;
import com.ifenduo.mahattan_x.base.BaseActivity;
import com.ifenduo.mahattan_x.base.BaseFragment;
import com.ifenduo.mahattan_x.base.BaseFragmentAdapter;
import com.ifenduo.mahattan_x.controller.city.CityFragment;
import com.ifenduo.mahattan_x.controller.me.MeFragment;
import com.ifenduo.mahattan_x.controller.mine.MineFragment;
import com.ifenduo.mahattan_x.controller.transaction.TransactionCenterFragment;
import com.ifenduo.mahattan_x.entity.ExchangeRate;
import com.ifenduo.mahattan_x.entity.LoginInfo;
import com.ifenduo.mahattan_x.entity.SafeSettingEntity;
import com.ifenduo.mahattan_x.event.BaseEvent;
import com.ifenduo.mahattan_x.tools.SharedPreferencesTool;
import com.tencent.bugly.Bugly;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ifenduo.mahattan_x.event.BaseEvent.REFRESH_QUOTES_LIST_PRICE;

public class HomeActivity extends BaseActivity implements BottomNavigation.OnItemTabClickCallBack {
    public static double EXCHANGE_RATE = -1;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigation mBottomNavigation;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected boolean toolbarIsEnable() {
        return false;
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    MineFragment mMineFragment;
    TransactionCenterFragment mTransactionCenterFragment;
    CityFragment mCityFragment;
    MeFragment mMeFragment;

    @Override
    protected void onCreateViewAfter(Bundle savedInstanceState) {
        super.onCreateViewAfter(savedInstanceState);
        Bugly.init(this, "5caafd8af2", false);
        StatusBarTool.setTranslucentForImageView(this, null);
        setupViewPager();
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchExchangeRate();
        fetchSafeSetting();
    }

    /**
     * 初始化相关Fragment
     */
    private void initFragments() {
        mMineFragment = MineFragment.newInstance();
        mTransactionCenterFragment = TransactionCenterFragment.newInstance();
        mCityFragment = CityFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
    }

    /**
     * 设置ViewPager
     */
    private void setupViewPager() {
        initFragments();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mMineFragment);
        fragmentList.add(mTransactionCenterFragment);
        fragmentList.add(mCityFragment);
        fragmentList.add(mMeFragment);
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    /**
     * 设置底部导航栏
     */
    private void setupBottomNavigation() {
        mBottomNavigation.setOnItemTabClickCallBack(this);
        mBottomNavigation.addTab(R.drawable.ic_nav_coin_, R.drawable.ic_nav_coin, getResources().getColor(R.color.colorTabNormal)
                , getResources().getColor(R.color.colorTabChecked), "挖矿");
        mBottomNavigation.addTab(R.drawable.ic_nav_transaction_, R.drawable.ic_nav_transaction, getResources().getColor(R.color.colorTabNormal)
                , getResources().getColor(R.color.colorTabChecked), "交易");
        mBottomNavigation.addTab(R.drawable.ic_nav_city_, R.drawable.ic_nav_city, getResources().getColor(R.color.colorTabNormal)
                , getResources().getColor(R.color.colorTabChecked), "曼哈顿城");
        mBottomNavigation.addTab(R.drawable.ic_nav_me_, R.drawable.ic_nav_me, getResources().getColor(R.color.colorTabNormal)
                , getResources().getColor(R.color.colorTabChecked), "我的");

        mBottomNavigation.setupWithViewPager(mViewPager);
        mBottomNavigation.setCheckedTab(0);
    }

    @Override
    public boolean onItemTabClick(int index, BaseTab tab) {
        return false;
    }

    /**
     * 获取汇率
     */
    private void fetchExchangeRate() {
        Api.getInstance().fetchExchangeRate(new Callback<ExchangeRate>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<ExchangeRate> response) {
                if (isSuccess && response.data != null) {
                    EXCHANGE_RATE = response.data.getCNY();
                    EventBus.getDefault().post(new BaseEvent(REFRESH_QUOTES_LIST_PRICE, null));
                }
            }
        });
    }

    /**
     * 获取安全设置状态
     */
    private void fetchSafeSetting() {
        LoginInfo loginInfo = SharedPreferencesTool.getLoginInfo(getApplicationContext());
        if (loginInfo == null) {
            openLogin();
            return;
        }
        Api.getInstance().fetchSafeSettingDetail(loginInfo.getToken(), new Callback<SafeSettingEntity>() {
            @Override
            public void onComplete(boolean isSuccess, int code, String msg, DataResponse<SafeSettingEntity> response) {
                if (isSuccess) {
                    SharedPreferencesTool.saveSafeSetting(getApplicationContext(),response.data);
                } else {
                    if (code == 401) {
                        showToast("登录已失效请重新登录");
                        openLogin();
                    } else {
                        showToast(msg);
                    }
                }
            }
        });
    }
}
