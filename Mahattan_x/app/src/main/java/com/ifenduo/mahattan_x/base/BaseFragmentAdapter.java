package com.ifenduo.mahattan_x.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ll on 2017/10/14.
 */

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragmentList;
    List<String> mTitleList;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList=fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList){
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
