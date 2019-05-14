package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarBodyViewPager;
import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarBodyPagerAdapter;
import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarModel;

public class CalendarView extends LinearLayout{

    // Views
    // 1. calendarHeaderLayout
    private ConstraintLayout calendarHeaderLayout;
    // 2. dayHeaderLayout
    private LinearLayout dayHeaderLayout;
//    // 3. calendarRcView
//    private RecyclerView calendarRcView;

    // 3. YearViewPager
    private CalendarBodyViewPager calendarBodyViewPager;


    // View Ratios
    private int PARENT_H_RATIO = 7;
    // 1. Calendar RecyclerView Height
    private int C_RV_H_RATIO = 6;
    // 2. Total Header Height
    private int TOTAL_HEADER_H_RATION = 4;
    // 2-1. Calendar Header Height
    private int C_HEADER_H_RATION = 3;
    // 2-2. Day Header Height
    private int D_HEADER_H_RATION = 1;



    private final float TEXT_SIZE = 8;

    public CalendarView(Context context, CalendarModel calendarModel){
        super(context);
        initViews(calendarModel);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) { super.onLayout(changed, left, top, right, bottom); }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int cHeaderHeightSpec = MeasureSpec.makeMeasureSpec(heightSize/PARENT_H_RATIO * C_HEADER_H_RATION/TOTAL_HEADER_H_RATION, MeasureSpec.EXACTLY);
        int dHeaderHeightSpec = MeasureSpec.makeMeasureSpec(heightSize/PARENT_H_RATIO * D_HEADER_H_RATION/TOTAL_HEADER_H_RATION, MeasureSpec.EXACTLY);
        int rvHeightSpec = MeasureSpec.makeMeasureSpec(heightSize * C_RV_H_RATIO/PARENT_H_RATIO, MeasureSpec.EXACTLY);

        calendarHeaderLayout.measure(widthMeasureSpec, cHeaderHeightSpec);
        dayHeaderLayout.measure(widthMeasureSpec, dHeaderHeightSpec);
        calendarBodyViewPager.measure(widthMeasureSpec, rvHeightSpec);


        setChildrenLp(dayHeaderLayout);
    }

    private void initViews(CalendarModel calendarModel){

        this.setOrientation(LinearLayout.VERTICAL);

        calendarHeaderLayout = createCalendarHeaderLayout();
        dayHeaderLayout      = createDayHeaderLayout();
        calendarBodyViewPager = createYearViewPager(calendarModel);

        this.addView(calendarHeaderLayout);
        this.addView(dayHeaderLayout);
        this.addView(calendarBodyViewPager);
    }

    private ConstraintLayout createCalendarHeaderLayout(){

        ConstraintLayout layout = new ConstraintLayout(getContext());
        layout.setBackgroundColor(Color.BLUE);

        return layout;
    }

    private LinearLayout createDayHeaderLayout(){

        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.LTGRAY);
        layout.setOrientation(LinearLayout.HORIZONTAL);


        String[] dayList = {"일", "월", "화", "수", "목", "금", "토"};
        int[] colorList = {Color.RED, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.BLUE};

        for(int i = 0; i < 7; ++ i){
            TextView textView = createCenterSideTextView(dayList[i], colorList[i]);
            textView.setBackgroundColor(Color.LTGRAY);
            layout.addView(textView);
        }

        return layout;
    }

    private CalendarBodyViewPager createYearViewPager(CalendarModel calendarModel){
        CalendarBodyViewPager viewPager = new CalendarBodyViewPager(getContext());

        viewPager.setBackgroundColor(Color.RED);

        CalendarBodyPagerAdapter adapter = new CalendarBodyPagerAdapter(calendarModel);

        viewPager.setAdapter(adapter);

        viewPager.setBackgroundColor(Color.GREEN);

        return viewPager;
    }

    private TextView createCenterSideTextView(String contents, int textColor){

        TextView textView = new TextView(getContext());
        textView.setText(contents);
        textView.setTextColor(textColor);
        textView.setTextSize(TEXT_SIZE);

        textView.setGravity(Gravity.CENTER);

        textView.setBackgroundColor(Color.WHITE);


        return textView;
    }

    private void setChildrenLp(LinearLayout parent){

        int parentWidth  = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();
        int childCnt     = parent.getChildCount();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth/childCnt, parentHeight, 1);

        for(int i = 0; i < childCnt; ++ i){
            parent.getChildAt(i).setLayoutParams(params);
        }

    }
}
