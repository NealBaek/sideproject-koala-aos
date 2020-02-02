package com.ksdigtalnomad.koala.ui.customView.calendarView.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarBodyViewPager;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarBodyPagerAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;
import com.ksdigtalnomad.koala.helpers.ui.TypeKitHelper;

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

    final int COLOR_LIGHT_GRAY = getContext().getResources().getColor(R.color.colorLightGray);
    // Color.parseColor("#eaeaea");
    final int COLOR_MAIN = getContext().getResources().getColor(R.color.colorMain);
    final int COLOR_PURE_WHITE = getContext().getResources().getColor(R.color.colorPureWhite);

    final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public CalendarView(Context context, CalendarModel calendarModel, EventInterface eventInterface){
        super(context);

        this.calendarModel = calendarModel;
        this.eventInterface = eventInterface;

        // 1. CalendarView 설정


        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(COLOR_LIGHT_GRAY);


        // 2. 자식 Layout 생성
        initViews(calendarModel, eventInterface);


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
    private void initViews(final CalendarModel calendarModel, EventInterface eventInterface){

        // 1. Create Views
        calendarHeaderLayout  = createCalendarHeaderLayout();
        dayLayout             = createDayHeaderLayout();
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
    private RelativeLayout createCalendarHeaderLayout(){

        final float MONTH_TITLE_TEXT_SIZE = 17;
        final float TODAY_TEXT_SIZE = 16;

        final int WIDTH_MONTH_TITLE_LAYOUT = (int) (150 * DENSITY);
        final int WIDTH_MONTH_TITLE_TOUCH_LISTENER = (int) (160 * DENSITY);
        final int HEIGHT_MONTH_TITLE_LAYOUT = (int) (24 * DENSITY);
        final int MARGIN_R_MOVE_TO_TODAY = (int) (18 * DENSITY);
        final int MARGIN_ARROW = (int) (5 * DENSITY);

        final String TODAY_TEXT = getResources().getString(R.string.calendar_today);




        // 1. Calendar Header Layout
        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setBackgroundColor(COLOR_MAIN);


        // 2. Month Title Layout
        RelativeLayout monthTitleLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams monthTitleLayoutLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        monthTitleLayoutLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        monthTitleLayoutLp.width = WIDTH_MONTH_TITLE_LAYOUT;
        monthTitleLayout.setLayoutParams(monthTitleLayoutLp);


        // 2-1. Month Title TextView
        RelativeLayout.LayoutParams monthTitleLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        monthTitleLp.addRule(RelativeLayout.CENTER_VERTICAL);
        monthTitleLp.addRule(RelativeLayout.CENTER_HORIZONTAL);

        monthTitleTv = new TextView(getContext());
        monthTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, MONTH_TITLE_TEXT_SIZE);
        monthTitleTv.setTypeface(TypeKitHelper.getNotoBold());
        monthTitleTv.setGravity(Gravity.CENTER);
        monthTitleTv.setTextColor(COLOR_PURE_WHITE);
        monthTitleTv.setLayoutParams(monthTitleLp);


        // 2-2. < Arrow ImageViews
        RelativeLayout.LayoutParams leftArrowLp= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftArrowLp.width = HEIGHT_MONTH_TITLE_LAYOUT;
        leftArrowLp.height = HEIGHT_MONTH_TITLE_LAYOUT;
        leftArrowLp.leftMargin = MARGIN_ARROW;
        leftArrowLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftArrowLp.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView leftArrowImageView = new ImageView(getContext());
        leftArrowImageView.setBackgroundResource(R.drawable.ic_arrow_left);
        leftArrowImageView.setLayoutParams(leftArrowLp);

        // 2-3. < Arrow ImageViews
        RelativeLayout.LayoutParams rightArrowLp  = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightArrowLp.width = HEIGHT_MONTH_TITLE_LAYOUT;
        rightArrowLp.height = HEIGHT_MONTH_TITLE_LAYOUT;
        rightArrowLp.rightMargin = MARGIN_ARROW;
        rightArrowLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightArrowLp.addRule(RelativeLayout.CENTER_VERTICAL);

        ImageView rightArrowImageView = new ImageView(getContext());
        rightArrowImageView.setBackgroundResource(R.drawable.ic_arrow_right);
        rightArrowImageView.setLayoutParams(rightArrowLp);

        // 2-4. Add Views on MonthTitleLayout
        monthTitleLayout.addView(leftArrowImageView);
        monthTitleLayout.addView(monthTitleTv);
        monthTitleLayout.addView(rightArrowImageView);
        layout.addView(monthTitleLayout);





        // 3. Touch Layout
        LinearLayout touchLinearLayout = new LinearLayout(getContext());
        touchLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        View leftTouchLayout = new View(getContext());
        leftTouchLayout.setOnClickListener((v) -> onMoveToPreviousMonth());
        touchLinearLayout.addView(leftTouchLayout, lp);


        View rightTouchLayout = new View(getContext());
        rightTouchLayout.setOnClickListener((v) -> onMoveToNextMonth());
        touchLinearLayout.addView(rightTouchLayout, lp);

        RelativeLayout.LayoutParams touchLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        touchLp.width = WIDTH_MONTH_TITLE_TOUCH_LISTENER;
        touchLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(touchLinearLayout, touchLp);


        // 4. MoveToToday TextView
        RelativeLayout.LayoutParams moveToTodayLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        moveToTodayLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        moveToTodayLp.addRule(RelativeLayout.CENTER_VERTICAL);
        moveToTodayLp.setMargins(0,0,MARGIN_R_MOVE_TO_TODAY,0);

        TextView moveToTodayTv = new TextView(getContext());
        moveToTodayTv.setText(TODAY_TEXT);
        moveToTodayTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TODAY_TEXT_SIZE);
        moveToTodayTv.setTypeface(TypeKitHelper.getNotoBold());
        moveToTodayTv.setGravity(Gravity.CENTER);
        moveToTodayTv.setLayoutParams(moveToTodayLp);
        moveToTodayTv.setTextColor(COLOR_PURE_WHITE);

        moveToTodayTv.setOnClickListener((v) -> onMoveToToday(presenter.getThisMonthIdx()));

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
            textView.setBackgroundColor(COLOR_PURE_WHITE);
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

        String monthTitle = YEAR + "." + (MONTH < 10 ? "0" + MONTH : MONTH) + ".";
        monthTitleTv.setText(monthTitle);
    }





    /** Measure Children */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 1. Measure 자식 Layouts
        final int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        final int D_HEADER_H_SIZE = (int) (20 * DENSITY);
        final int C_HEADER_H_SIZE = (int) (45 * DENSITY);

        int rvHeightSize = parentHeightSize - (D_HEADER_H_SIZE + C_HEADER_H_SIZE);

        int cHeaderHeightSpec = MeasureSpec.makeMeasureSpec(C_HEADER_H_SIZE, MeasureSpec.EXACTLY);
        int dHeaderHeightSpec = MeasureSpec.makeMeasureSpec(D_HEADER_H_SIZE, MeasureSpec.EXACTLY);
        int rvHeightSpec = MeasureSpec.makeMeasureSpec(rvHeightSize, MeasureSpec.AT_MOST);

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
