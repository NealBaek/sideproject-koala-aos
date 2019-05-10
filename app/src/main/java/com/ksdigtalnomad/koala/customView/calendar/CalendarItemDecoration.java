package com.ksdigtalnomad.koala.customView.calendar;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ooddy on 10/05/2019.
 */

public class CalendarItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public CalendarItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        outRect.right = space;
    }
}
