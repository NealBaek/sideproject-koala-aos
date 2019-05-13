package com.ksdigtalnomad.koala.customView.calendar.month;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ooddy on 10/05/2019.
 */

public class MonthItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public MonthItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.right = space;
    }
}
