package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by ooddy on 10/05/2019.
 */

public class NonScrollGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public NonScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
