package com.ifenduo.mahattan_x.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ll on 2018/4/14.
 */

public class BasePagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;
    List<String> mTitleList;

    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList=fragmentList;
    }

    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList){
        super(fm);
        mFragmentList=fragmentList;
        mTitleList=titleList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList!=null && position<mTitleList.size()){
            return mTitleList.get(position);
        }else {
            return super.getPageTitle(position);
        }
    }
}
