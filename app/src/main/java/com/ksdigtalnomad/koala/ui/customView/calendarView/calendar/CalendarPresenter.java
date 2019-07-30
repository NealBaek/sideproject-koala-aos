package com.ksdigtalnomad.koala.ui.customView.calendarView.calendar;

import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ooddy on 14/05/2019.
 */

class CalendarPresenter implements CalendarContract.CalendarPresenter{

    private CalendarContract.CalendarView view;
    private CalendarModel calendarModel;

    private int thisMonthIdx;
    private MonthModel thisMonthMondel;


    public CalendarPresenter(CalendarContract.CalendarView view, CalendarModel calendarModel){
        this.view = view;
        this.calendarModel = calendarModel;
    }

    @Override
    public void setUpThisMonth() {

        // 1. 오늘 날짜 계산
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfMonth = new SimpleDateFormat("MM");

        int thisMonth = Integer.parseInt(dfMonth.format(new Date()));
        int thisYear = Integer.parseInt(dfYear.format(new Date()));



        // 2. 이번 달 모델 값 구하기
        int monthListCnt =  calendarModel.monthList.size();
        for(int i = 0; i < monthListCnt; ++i){
            MonthModel item = calendarModel.monthList.get(i);
            boolean isThisYear  = (thisYear == item.year);
            boolean isThisMonth = (thisMonth == item.month);

            if (isThisYear && isThisMonth){
                thisMonthMondel = item;
                thisMonthIdx = i;
                break;
            }
        }

        // 3. pager 이번달로 옮기기
        view.setUpThisMonth(thisMonthIdx);
    }

    @Override
    public int getThisMonthIdx() { return thisMonthIdx; }
}
