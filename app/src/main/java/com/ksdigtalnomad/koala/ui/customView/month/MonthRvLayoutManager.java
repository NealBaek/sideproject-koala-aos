package com.ksdigtalnomad.koala.ui.customView.month;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by ooddy on 10/05/2019.
 */

public class MonthRvLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public MonthRvLayoutManager(Context context, int spanCount) {
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
