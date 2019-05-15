package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarBodyViewPager;
import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarBodyPagerAdapter;
import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.customView.calendar.day.DayModel;
import com.ksdigtalnomad.koala.customView.calendar.month.MonthModel;

public class CalendarView extends LinearLayout implements CalendarContract.CalendarView{

    // TouchInterface
    private EventInterface eventInterface;
    private CalendarModel calendarModel;
    private CalendarPresenter presenter;

    // Views
    // 1. calendarHeaderLayout
    private RelativeLayout calendarHeaderLayout;
//    //  1-1. MonthTitle
    private TextView monthTitleTv;
    // 2. dayHeaderLayout
    private LinearLayout dayLayout;
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




    public CalendarView(Context context, CalendarModel calendarModel, EventInterface eventInterface){
        super(context);

        this.calendarModel = calendarModel;
        this.eventInterface = eventInterface;

        // 1. CalendarView 설정
        final int BG_COLOR = Color.parseColor("#eaeaea");

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(BG_COLOR);


        // 2. 자식 Layout 생성
        initViews(calendarModel, eventInterface);


        // 3. Presenter 생성
        presenter = new CalendarPresenter(this);

        //  3-1. 이번 달로 설정
        presenter.setUpThisMonth();
    }




    /** Create Layouts */
    private void initViews(CalendarModel calendarModel, EventInterface eventInterface){

        calendarHeaderLayout  = createCalendarHeaderLayout();
        dayLayout             = createDayHeaderLayout();
        calendarBodyViewPager = createYearViewPager(calendarModel, eventInterface);

        this.addView(calendarHeaderLayout);
        this.addView(dayLayout);
        this.addView(calendarBodyViewPager);
    }
    private RelativeLayout createCalendarHeaderLayout(){


        final float MONTH_TITLE_TEXT_SIZE = 20;
        final float MOVE_TO_TODAY_TEXT_SIZE = 14;

        final int MARGIN_R_MOVE_TO_TODAY = 40;

        final String TODAY_TEXT = "오늘";



        // 1. Calendar Header Layout
        RelativeLayout layout = new RelativeLayout(getContext());

        RelativeLayout.LayoutParams relativeLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



        // 2. Month Title TextView
        monthTitleTv = new TextView(getContext());
        monthTitleTv.setTextSize(MONTH_TITLE_TEXT_SIZE);
        monthTitleTv.setGravity(Gravity.CENTER);
        monthTitleTv.setLayoutParams(relativeLp);

        layout.addView(monthTitleTv);




        // 3. Touch Layout
        LinearLayout touchLinearLayout = new LinearLayout(getContext());
        touchLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        View leftTouchLayout = new View(getContext());
        leftTouchLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveToPreviousMonth();
            }
        });
        touchLinearLayout.addView(leftTouchLayout, lp);


        View rightTouchLayout = new View(getContext());
        rightTouchLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveToNextMonth();
            }
        });
        touchLinearLayout.addView(rightTouchLayout, lp);


        layout.addView(touchLinearLayout, relativeLp);


        // 4. MoveToToday TextView
        RelativeLayout.LayoutParams moveToTodayLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        moveToTodayLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        moveToTodayLp.setMargins(0,0,MARGIN_R_MOVE_TO_TODAY,0);

        TextView moveToTodayTv = new TextView(getContext());
        moveToTodayTv.setText(TODAY_TEXT);
        moveToTodayTv.setTextSize(MOVE_TO_TODAY_TEXT_SIZE);
        moveToTodayTv.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        moveToTodayTv.setGravity(Gravity.CENTER);
        moveToTodayTv.setLayoutParams(moveToTodayLp);

        moveToTodayTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveToToday();
            }
        });

        layout.addView(moveToTodayTv);


        return layout;
    }
    private LinearLayout createDayHeaderLayout(){

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);


        String[] dayList = {"일", "월", "화", "수", "목", "금", "토"};
        int[] colorList = {Color.RED, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.BLUE};

        for(int i = 0; i < 7; ++ i){
            TextView textView = createCenterSideTextView(dayList[i], colorList[i]);
            layout.addView(textView);
        }

        return layout;
    }
    private CalendarBodyViewPager createYearViewPager(CalendarModel calendarModel, EventInterface eventInterface){
        CalendarBodyViewPager viewPager = new CalendarBodyViewPager(getContext());

        viewPager.setBackgroundColor(Color.LTGRAY);

        CalendarBodyPagerAdapter adapter = new CalendarBodyPagerAdapter(calendarModel, eventInterface);

        viewPager.setAdapter(adapter);

        return viewPager;
    }

    /** TextView */
    private TextView createCenterSideTextView(String contents, int textColor){

        final float TEXT_SIZE = 8;

        TextView textView = new TextView(getContext());
        textView.setText(contents);
        textView.setTextColor(textColor);
        textView.setTextSize(TEXT_SIZE);

        textView.setGravity(Gravity.CENTER);

        textView.setBackgroundColor(Color.WHITE);


        return textView;
    }
    private void setMonthTitle(int monthIdx, TextView monthTitleTv){
        MonthModel previousMonth = calendarModel.monthList.get(monthIdx);
        final int YEAR = previousMonth.year;
        final int MONTH = previousMonth.month;

        String monthTitle = "<  " + YEAR + "." + (MONTH < 10 ? "0" + MONTH : MONTH) + "  >";
        monthTitleTv.setText(monthTitle);
    }





    /** Measure Children */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int cHeaderHeightSpec = MeasureSpec.makeMeasureSpec(heightSize/PARENT_H_RATIO * C_HEADER_H_RATION/TOTAL_HEADER_H_RATION, MeasureSpec.EXACTLY);
        int dHeaderHeightSpec = MeasureSpec.makeMeasureSpec(heightSize/PARENT_H_RATIO * D_HEADER_H_RATION/TOTAL_HEADER_H_RATION, MeasureSpec.EXACTLY);
        int rvHeightSpec = MeasureSpec.makeMeasureSpec(heightSize * C_RV_H_RATIO/PARENT_H_RATIO, MeasureSpec.EXACTLY);

        calendarHeaderLayout.measure(widthMeasureSpec, cHeaderHeightSpec);
        dayLayout.measure(widthMeasureSpec, dHeaderHeightSpec);
        calendarBodyViewPager.measure(widthMeasureSpec, rvHeightSpec);


        setDayLayoutChildrenLp(dayLayout);

    }
    private void setDayLayoutChildrenLp(LinearLayout parent){

        int parentWidth  = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();
        int childCnt     = parent.getChildCount();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth/childCnt, parentHeight, 1);

        for(int i = 0; i < childCnt; ++ i){ parent.getChildAt(i).setLayoutParams(params); }
    }

    /** Events */
    public void onMoveToPreviousMonth(){
        int cIdx = calendarBodyViewPager.getCurrentItem();

        if(cIdx == 0){ return; }

        final int targetIdx = cIdx - 1;

        calendarBodyViewPager.setCurrentItem(targetIdx, true);

        setMonthTitle(targetIdx, monthTitleTv);
    }
    public void onMoveToNextMonth(){
        int cIdx = calendarBodyViewPager.getCurrentItem();

        if(cIdx == calendarModel.monthList.size() - 1){ return; }

        final int targetIdx = cIdx + 1;

        calendarBodyViewPager.setCurrentItem(targetIdx, true);

        setMonthTitle(targetIdx, monthTitleTv);
    }
    public void onMoveToToday(){

    }

    public interface EventInterface{ void onDayViewTouch(DayModel dayModel); }





    /** CalendarContract.CalendarView Overrides */
    @Override  public void setUpThisMonth(int monthIdx) { setMonthTitle(monthIdx, monthTitleTv); }
}
