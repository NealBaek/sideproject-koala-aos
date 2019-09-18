package com.ksdigtalnomad.koala.ui.customView.calendarView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarBodyViewPager;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarBodyPagerAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;

public class CalendarView extends LinearLayout implements CalendarContract.CalendarView{

    public static final int DAY_COUNT = 42;

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


    public CalendarView(Context context, CalendarModel calendarModel, EventInterface eventInterface){
        super(context);

        this.calendarModel = calendarModel;
        this.eventInterface = eventInterface;

        // 1. CalendarView 설정
        final int BG_COLOR = Color.parseColor("#eaeaea");

        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(BG_COLOR);


        // 2. 자식 Layout 생성
        initViews(BG_COLOR, calendarModel, eventInterface);


        // 3. Presenter 생성
        presenter = new CalendarPresenter(this, calendarModel);

        //  3-1. 이번 달로 설정
        presenter.setUpThisMonth();
    }

    public void notifyDataChanged(CalendarModel calendarModel){
        this.calendarModel = calendarModel;
        ((CalendarBodyPagerAdapter)calendarBodyViewPager.getAdapter()).notifyDataChange(calendarModel);
    }




    /** Create Layouts */
    private void initViews(int bgColor, final CalendarModel calendarModel, EventInterface eventInterface){

        // 1. Create Views
        calendarHeaderLayout  = createCalendarHeaderLayout(bgColor);
        dayLayout             = createDayHeaderLayout(bgColor);
        calendarBodyViewPager = createYearViewPager(calendarModel, eventInterface);

        // 2. Add Views
        this.addView(calendarHeaderLayout);
        this.addView(dayLayout);
        this.addView(calendarBodyViewPager);

        // 3. SetUp Listeners
        calendarBodyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}
            @Override
            public void onPageSelected(int i) { setMonthTitle(i, monthTitleTv); }
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }
    private RelativeLayout createCalendarHeaderLayout(int bgColor){


        final float MONTH_TITLE_TEXT_SIZE = 20;
        final float MOVE_TO_TODAY_TEXT_SIZE = 14;

        final int MARGIN_R_MOVE_TO_TODAY = 40;

        final String TODAY_TEXT = "오늘";



        // 1. Calendar Header Layout
        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setBackgroundColor(bgColor);


        // 2. Month Title TextView
        monthTitleTv = new TextView(getContext());
        monthTitleTv.setTextSize(MONTH_TITLE_TEXT_SIZE);
        monthTitleTv.setGravity(Gravity.CENTER);
        monthTitleTv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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

        RelativeLayout.LayoutParams touchLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        touchLp.height = 200;
        touchLp.width = 500;
        touchLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(touchLinearLayout, touchLp);


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
            public void onClick(View view) { onMoveToToday(presenter.getThisMonthIdx()); }
        });

        layout.addView(moveToTodayTv);


        return layout;
    }
    private LinearLayout createDayHeaderLayout(int bgColor){

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);


        String[] dayList = {"일", "월", "화", "수", "목", "금", "토"};
        int[] colorList = {Color.RED, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.DKGRAY, Color.BLUE};

        for(int i = 0; i < 7; ++ i){
            TextView textView = createCenterSideTextView(dayList[i], colorList[i]);
            textView.setBackgroundColor(bgColor);
            layout.addView(textView);
        }

        return layout;
    }
    private CalendarBodyViewPager createYearViewPager(CalendarModel calendarModel, EventInterface eventInterface){
        CalendarBodyViewPager viewPager = new CalendarBodyViewPager(getContext());

        CalendarBodyPagerAdapter adapter = new CalendarBodyPagerAdapter(getContext(), calendarModel, eventInterface);

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

        // 1. Measure 자식 Layouts
        final int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        final int PARENT_H_RATIO = 7;
        final int C_RV_H_RATIO = 6;
        final int D_HEADER_H_SIZE = 45;

        int rvHeightSize = (parentHeightSize * C_RV_H_RATIO/PARENT_H_RATIO);
        int cHeaderHeightSize = parentHeightSize - rvHeightSize - D_HEADER_H_SIZE;

        int cHeaderHeightSpec = MeasureSpec.makeMeasureSpec(cHeaderHeightSize, MeasureSpec.AT_MOST);
        int dHeaderHeightSpec = MeasureSpec.makeMeasureSpec(D_HEADER_H_SIZE, MeasureSpec.EXACTLY);
        int rvHeightSpec = MeasureSpec.makeMeasureSpec(rvHeightSize, MeasureSpec.EXACTLY);

        calendarHeaderLayout.measure(widthMeasureSpec, cHeaderHeightSpec);
        dayLayout.measure(widthMeasureSpec, dHeaderHeightSpec);
        calendarBodyViewPager.measure(widthMeasureSpec, rvHeightSpec);



        // 2. Day Layout 의 자식 텍스트 뷰 LayoutParam
        int parentWidth  = dayLayout.getMeasuredWidth();
        int parentHeight = dayLayout.getMeasuredHeight();
        int childCnt     = dayLayout.getChildCount();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth/childCnt, parentHeight, 1);

        for(int i = 0; i < childCnt; ++ i){ dayLayout.getChildAt(i).setLayoutParams(params); }
    }




    /** Events */
    public void onMoveToPreviousMonth(){ smoothScrollMonthPage(calendarBodyViewPager.getCurrentItem() - 1, true); }
    public void onMoveToNextMonth(){ smoothScrollMonthPage(calendarBodyViewPager.getCurrentItem() + 1, true); }
    public void onMoveToToday(int toIdx){ smoothScrollMonthPage(toIdx, true); }
    private void smoothScrollMonthPage(int toIdx, boolean isSmooth){
        if(toIdx < 0 || toIdx > calendarModel.monthList.size() - 1){ return; }

        calendarBodyViewPager.setCurrentItem(toIdx, isSmooth);
        setMonthTitle(toIdx, monthTitleTv);
    }




    public interface EventInterface{ void onDayViewTouch(DayModel dayModel); }





    /** CalendarContract.CalendarView Overrides */
    @Override  public void setUpThisMonth(int monthIdx) { smoothScrollMonthPage(monthIdx, false); }
}
