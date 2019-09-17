package com.ksdigtalnomad.koala.ui.customView.calendarView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ooddy on 11/07/2019.
 */

public class CalendarDataController {

    private static final String PREF_FILE_NAME = "CALENDAR_DATA";
    private static final String KEY_CALENDAR_MODEL = "CALENDAR_MODEL";


    private CalendarDataController(){}


    private static CalendarModel createCalendarModel(){

        // 시작 날짜 정하기 (2019.01.01 부터 시작)
        int startYear = 2017;
        int startMonth = 1;
        int startDay = 1;
        int dayIdx = 0;
        int monthIdx = 0;
        int yearIdx = 0;
        boolean isLeapYear = false;


        // 오늘 날짜 구하기
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfMonth = new SimpleDateFormat("MM");
        int thisYear = Integer.parseInt(dfYear.format(new Date()));
        int thisMonth = Integer.parseInt(dfMonth.format(new Date()));


        // 끝나는 날짜 구하기
        int endYear = thisYear + 1;
        int endMonth = thisMonth;

        ArrayList<DayModel> dayModelList = new ArrayList<>();

        CalendarModel calendarModel = new CalendarModel();
        calendarModel.monthList = new ArrayList<>();

        // 계산할 연 갯구 카운트
        int toCalYearCnt = 1 + endYear - startYear;

        // 연 계산
        for(int i=0; i < toCalYearCnt; ++i){

            // 만약 마지막 계산 연이면 종료 달까지/ 아니면 12개월
            int toCalMonthCnt = ((i == toCalYearCnt) ? endMonth : 12);

            // 윤년 계산
            if(startYear % 400 == 0){ isLeapYear = true; }       // 1. 연도가 400의 배수이면 윤년
            else if(startYear % 100 == 0){ isLeapYear = false; } // 2. 연도가 100의 배수이면 윤년이 아님.
            else if(startYear % 4 == 0){ isLeapYear = true; }    // 3. 연도가 4의 배수이면 윤년.

            // 월 계산
            for(int j=0; j < toCalMonthCnt; ++j){

                // 일 계산
                int daysCnt = CalendarConstUtils.NUM_DAYS_IN_MONTH[j%12];

                // 윤월에 1일 추가
                if(j==1) daysCnt += isLeapYear ? 1 : 0;

                MonthModel monthModel = new MonthModel();
                monthModel.year = startYear;
                monthModel.month = startMonth;
                monthModel.numberOfDaysInTheMonth = daysCnt;
                monthModel.dayList = new ArrayList<>();
                monthModel.index = monthIdx;

                boolean hasPMonth = (monthIdx == 0) ? false : calendarModel.monthList.size() > monthIdx - 1;
                MonthModel pMonthModel = hasPMonth ? calendarModel.monthList.get(monthIdx - 1) : null;



                // 전월 일 추가

                // 전월의 마지막날의 요일 + 1일한 요일
                //  1. 만약 일요일이면 pDayCnt == 0
                //  2. 아니면 전월 마지막 요일 에서 역순으로 일요일까지 데이터 세팅
                int pDayCnt = 0;
                int lastDaySeq = (pMonthModel == null) ? CalendarConstUtils.DAY_SEQ_SATURDAY : dayModelList.get(dayModelList.size()-1).daySeq;

                if(lastDaySeq != CalendarConstUtils.DAY_SEQ_SATURDAY){
                    pDayCnt = lastDaySeq + 1;
                    int lastMonthCnt = dayModelList.size() - 1;

                    for(int k=0; k < pDayCnt; ++k){
                        DayModel pDayModel = dayModelList.get(lastMonthCnt - ((pDayCnt-1) - k)).clone();
                        pDayModel.isOutMonth = true;
                        monthModel.dayList.add(pDayModel);
                    }
                }

                // 해당 일 추가
                for(int k=0; k < daysCnt; ++k ){

                    // 데이 값 입력
                    DayModel dayModel = new DayModel();
                    dayModel.year = startYear;
                    dayModel.month = startMonth;
                    dayModel.day = startDay;
                    dayModel.daySeq = ((dayIdx)%7);
                    dayModel.dayOfTheWeek = CalendarConstUtils.DAYS_OF_THE_WEEK[dayModel.daySeq];
                    dayModel.dayIdx = dayIdx;
                    dayModel.monthIdx = monthIdx;
                    dayModel.yearIdx = yearIdx;

                    dayModelList.add(dayModel);
                    monthModel.dayList.add(dayModel);

                    startDay += 1;
                    dayIdx += 1;

                    Log.d("ABC", "dayIdx: " + dayIdx);
                }

                // 익월 일 추가
                int nDayCnt = CalendarView.DAY_COUNT - daysCnt - pDayCnt;
                if(nDayCnt > 0){
                    boolean isNextYear = (((pMonthModel == null) ? startMonth : pMonthModel.month) == 12);
                    int nDayIdx = dayIdx + 1;

                    for(int k=0; k < nDayCnt; ++k){
                        DayModel nDayModel = new DayModel();
                        nDayModel.year = isNextYear ? startYear - 1 : startYear;
                        nDayModel.month = isNextYear ? 1 : startMonth - 1;
                        nDayModel.day = k + 1;
                        nDayModel.daySeq = ((nDayIdx - 1)%7);
                        nDayModel.dayOfTheWeek = CalendarConstUtils.DAYS_OF_THE_WEEK[nDayModel.daySeq];
                        nDayModel.dayIdx = nDayIdx;
                        nDayModel.monthIdx = monthIdx;
                        nDayModel.yearIdx = yearIdx;
                        nDayModel.isOutMonth = true;

                        monthModel.dayList.add(nDayModel);

                        nDayIdx += 1;
                    }
                }

                calendarModel.monthList.add(monthModel);

                startDay = 1;
                startMonth += 1;
                monthIdx += 1;
            }

            // 연 증가
            startDay = 1;
            startMonth = 1;
            startYear += 1;
            yearIdx += 1;
        }

        storeCalendarModel(calendarModel);

        return calendarModel;
    }
    public static CalendarModel getCalendarModel(){ return dumpCalendarModel(); }
    public static CalendarModel updateDayModel(DayModel dayModel){

        // @TODO: 느려짐 포인트 1-1
        CalendarModel calendarModel = CalendarDataController.dumpCalendarModel();
        ArrayList<MonthModel> mModelList = calendarModel.monthList;

        for(int i = 0; i < mModelList.size(); ++ i){
            MonthModel mModel = mModelList.get(i);
            if(mModel.index == dayModel.monthIdx){
                ArrayList<DayModel> dModelList = mModel.dayList;
                for(int j = 0; j < dModelList.size(); ++ j){
                    DayModel dModel = dModelList.get(j);
                    if(dModel.dayIdx == dayModel.dayIdx){
                        dModelList.set(j, dayModel);
                        Log.d("ABC", "memo2: " + dModelList.get(j).memo);
                        break;
                    }
                }
                break;
            }
        }

        // preference 에 저장
        // @TODO: 느려짐 포인트 1-2
        storeCalendarModel(calendarModel);

        return calendarModel;
    }


    private static void storeCalendarModel(CalendarModel model){ getEditPreference().putString(KEY_CALENDAR_MODEL, new Gson().toJson(model)).apply(); }
    private static CalendarModel dumpCalendarModel(){
        CalendarModel dumpModel = new Gson().fromJson(getReadPreference().getString(KEY_CALENDAR_MODEL, null), CalendarModel.class);
        if(dumpModel == null) dumpModel = createCalendarModel();
        return dumpModel;
    }


    private static SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private static SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }
}
