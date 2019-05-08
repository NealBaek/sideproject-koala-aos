package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

public class CalendarView extends LinearLayout{

    // 1. calenderHeaderLayout
    private ConstraintLayout calenderHeaderLayout;
    // 2. dayHeaderLayout
    private LinearLayout dayHeaderLayout;
    // 3. calendarRcView


    public CalendarView(Context context){
        super(context);
        initViews();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }




    private void initViews(){
        calenderHeaderLayout = createCalendarHeaderLayout();
        dayHeaderLayout      = createDayHeaderLayout();

        this.addView(calenderHeaderLayout);
        this.addView(dayHeaderLayout);
    }

    private ConstraintLayout createCalendarHeaderLayout(){
        return null;
    }

    private LinearLayout createDayHeaderLayout(){
        return null;
    }

}
