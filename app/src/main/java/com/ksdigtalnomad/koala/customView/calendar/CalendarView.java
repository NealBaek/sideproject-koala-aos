package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarView extends LinearLayout{

    // Views
    // 1. calendarHeaderLayout
    private ConstraintLayout calendarHeaderLayout;
    // 2. dayHeaderLayout
    private LinearLayout dayHeaderLayout;
    // 3. calendarRcView
    private RecyclerView calendarRcView;


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

    public CalendarView(Context context){
        super(context);
        initViews();
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
        calendarRcView.measure(widthMeasureSpec, rvHeightSpec);

        Log.d("ABC", "dayHeaderLayout: " + dayHeaderLayout.getWidth());

        setChildrenLp(dayHeaderLayout);
    }

    private void initViews(){

        this.setOrientation(LinearLayout.VERTICAL);

        calendarHeaderLayout = createCalendarHeaderLayout();
        dayHeaderLayout      = createDayHeaderLayout();
        calendarRcView       = createCalendarRcView();

        this.addView(calendarHeaderLayout);
        this.addView(dayHeaderLayout);
        this.addView(calendarRcView);
    }

    private ConstraintLayout createCalendarHeaderLayout(){

        ConstraintLayout layout = new ConstraintLayout(getContext());
        layout.setBackgroundColor(Color.BLUE);

        return layout;
    }

    private LinearLayout createDayHeaderLayout(){

        LinearLayout layout = new LinearLayout(getContext());
//        layout.setBackgroundColor(Color.BLACK);
        layout.setOrientation(LinearLayout.HORIZONTAL);


        String[] dayList = {"일", "월", "화", "수", "목", "금", "토"};
        int[] colorList = {Color.RED, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.BLUE};

        for(int i = 0; i < 7; ++ i){
            TextView textView = createCenterSideTextView(dayList[i], colorList[i]);
            textView.setBackgroundColor(Color.BLACK);
            layout.addView(textView);
        }

        return layout;
    }

    private RecyclerView createCalendarRcView(){

        RecyclerView recyclerView = new RecyclerView(getContext());

        // Bg Color
        recyclerView.setBackgroundColor(Color.WHITE);


        // Layout Manager & dividers
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);


        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(Color.DKGRAY);
//        shapeDrawable.setAlpha(1);
        shapeDrawable.getPaint().setStrokeWidth(1.0f);


        DividerItemDecoration horiDivider = new DividerItemDecoration(recyclerView.getContext(), GridLayoutManager.HORIZONTAL);
        DividerItemDecoration vertDivider = new DividerItemDecoration(recyclerView.getContext(), GridLayoutManager.VERTICAL);
        horiDivider.setDrawable(shapeDrawable);
        vertDivider.setDrawable(shapeDrawable);

        recyclerView.addItemDecoration(horiDivider);
        recyclerView.addItemDecoration(vertDivider);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new CalendarRvAdapter(getContext()));

        return recyclerView;
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
