package com.ifenduo.mahattan_x.entity;

import android.support.annotation.DrawableRes;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    @DrawableRes
    int tabSelectedIcon;
    @DrawableRes
    int tabUnselectedIcon;
    String tabTitle;

    public TabEntity() {
    }

    public TabEntity(int tabSelectedIcon, int tabUnselectedIcon, String tabTitle) {
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
        this.tabTitle = tabTitle;
    }

    public TabEntity(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public void setTabSelectedIcon(int tabSelectedIcon) {
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public void setTabUnselectedIcon(int tabUnselectedIcon) {
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
