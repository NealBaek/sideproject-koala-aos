package com.ksdigtalnomad.koala.customView.calendar;

import com.ksdigtalnomad.koala.customView.calendar.month.MonthModel;

/**
 * Created by ooddy on 14/05/2019.
 */

class CalendarPresenter implements CalendarContract.CalendarPresenter{

    private CalendarContract.CalendarView view;

    private int thisMonthIdx;
    private MonthModel thisMonth;


    public CalendarPresenter(CalendarContract.CalendarView view){
        this.view = view;
    }

    @Override
    public void setUpThisMonth() {

        // 1. 오늘 날짜 계산

        // 2. 이번 달 모델 값 구하기

        // 3. pager 이번달로 옮기기
        view.setUpThisMonth(0);
    }
}
