package com.charmingwong.hoopsports.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by 56223 on 2017/3/10.
 */

public class BaseViewpager extends ViewPager {

    public BaseViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
