package com.ifenduo.mahattan_x.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/***
 * @author sirLL
 * @email 1725790539@qq.com
 * create at 2018/5/3 下午3:13
 * description: 使ViewPager不能滑动
 */
public class NoTouchViewPager extends ViewPager {

    public NoTouchViewPager(Context context) {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * 去掉动画
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
