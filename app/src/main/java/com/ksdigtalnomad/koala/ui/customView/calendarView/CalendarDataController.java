package com.ksdigtalnomad.koala.ui.customView.calendarView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ooddy on 11/07/2019.
 */

public class CalendarDataController {

    private static final String PREF_FILE_NAME = "CALENDAR_DATA";
    private static final String KEY_CALENDAR_MODEL = "CALENDAR_MODEL";
    private static final String KEY_TOTAL_DAY_LIST = "TOTAL_DAY_LIST";
    private static final String KEY_NO_DATA_YET = "NO_DATA_YET";

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");

    private static DayModel todayModelFromDayView = new DayModel();
    private static DayModel yesterdayModelFromDayView = new DayModel();

    private CalendarDataController(){}
    private static SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private static SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }





    // Calendar Model - 달력을 위한 Calendar Model (익월/전월 포함)
    private static CalendarModel createCalendarModel(){

        // 시작 날짜 정하기 (2019.01.01 부터 시작)
        int startYear = 2017;
        int startMonth = 1;
        int startDay = 1;
        int dayIdx = 0;
        int monthIdx = 0;
        int yearIdx = 0;
        boolean isLeapYear = false;

        df.setTimeZone(TimeZone.getDefault());


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

                    try{
                        dayModel.setDate(df.parse("" + dayModel.year + "." + (dayModel.month < 10 ? ("0" + dayModel.month) : dayModel.month) + "." + (dayModel.day < 10 ? ("0" + dayModel.day) : dayModel.day)));
                    }catch (ParseException e){
                        Log.d("ABC", "ParseException: " + e.getMessage());
                    }

                    dayModelList.add(dayModel);
                    monthModel.dayList.add(dayModel);

                    startDay += 1;
                    dayIdx += 1;

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
                        try{
                            nDayModel.setDate(df.parse("" + nDayModel.year + "." + (nDayModel.month < 10 ? ("0" + nDayModel.month) : nDayModel.month) + "." + (nDayModel.day < 10 ? ("0" + nDayModel.day) : nDayModel.day)));
                        }catch (ParseException e){
                            nDayModel.setDate(new Date());
                        }
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

        createTotalDayList(dayModelList);

        return calendarModel;
    }
    public static CalendarModel getCalendarModel(){ return dumpCalendarModel(); }
    public static CalendarModel updateDayModel(DayModel dayModel){

        CalendarModel calendarModel = CalendarDataController.dumpCalendarModel();
        ArrayList<MonthModel> mModelList = calendarModel.monthList;
        int mModelCnt = mModelList.size();

        for(int i = 0; i < mModelCnt; ++ i){
            MonthModel mModel = mModelList.get(i);

            // 전월 previous month
            if(dayModel.day <= 15 && dayModel.monthIdx > 0 && mModel.index == (dayModel.monthIdx - 1)){
                ArrayList<DayModel> pDayList = mModel.dayList;
                int dayCnt = pDayList.size();

                for(int j = 0; j < dayCnt; ++ j){
                    DayModel pDay = pDayList.get(j);
                    if(pDay.isOutMonth && pDay.day == dayModel.day){
                        pDay.drunkLevel = dayModel.drunkLevel;
                        pDay.friendList.clear();
                        pDay.friendList.addAll(dayModel.friendList);
                        pDay.foodList.clear();
                        pDay.foodList.addAll(dayModel.foodList);
                        pDay.drinkList.clear();
                        pDay.drinkList.addAll(dayModel.drinkList);
                        pDay.memo = dayModel.memo;
                        pDay.isSaved = dayModel.isSaved;
                        pDayList.set(j, pDay);
                        break;
                    }
                }
            }
            // 금월
            if(mModel.index == dayModel.monthIdx){
                ArrayList<DayModel> cDayList = mModel.dayList;
                int dayCnt = cDayList.size();

                for(int j = 0; j < dayCnt; ++ j){
                    DayModel cDay = cDayList.get(j);
                    if(cDay.month == dayModel.month && cDay.day == dayModel.day){
                        cDayList.set(j, dayModel);
                        break;
                    }
                }
            }
            // 익월
            if(dayModel.day >= 13 && dayModel.monthIdx < mModelCnt && mModel.index == (dayModel.monthIdx + 1)){
                ArrayList<DayModel> nDayList = mModel.dayList;
                int dayCnt = nDayList.size();

                for(int j = 0; j < dayCnt; ++ j){
                    DayModel nDay = nDayList.get(j);
                    if(nDay.isOutMonth && nDay.day == dayModel.day){
                        nDay.drunkLevel = dayModel.drunkLevel;
                        nDay.friendList.clear();
                        nDay.friendList.addAll(dayModel.friendList);
                        nDay.foodList.clear();
                        nDay.foodList.addAll(dayModel.foodList);
                        nDay.drinkList.clear();
                        nDay.drinkList.addAll(dayModel.drinkList);
                        nDay.memo = dayModel.memo;
                        nDay.isSaved = dayModel.isSaved;
                        nDayList.set(j, nDay);
                        break;
                    }
                }
                break;
            }

            if(mModel.index == (dayModel.monthIdx + 1)){
                break;
            }

        }

        // preference 에 저장
        storeCalendarModel(calendarModel);


        // Total Day List
        updateTotalDayList(dayModel);

        return calendarModel;
    }

    private static void storeCalendarModel(CalendarModel model){ getEditPreference().putString(KEY_CALENDAR_MODEL, new Gson().toJson(model)).apply(); }
    private static CalendarModel dumpCalendarModel(){
        CalendarModel dumpModel = new Gson().fromJson(getReadPreference().getString(KEY_CALENDAR_MODEL, null), CalendarModel.class);
        if(dumpModel == null) dumpModel = createCalendarModel();
        return dumpModel;
    }



    public static void setNoDataYet(boolean flag){ getEditPreference().putBoolean(KEY_NO_DATA_YET, flag).apply(); }
    public static boolean isNoDataYet(){ return getReadPreference().getBoolean(KEY_NO_DATA_YET, true); }





    // Today Model
//    private static DayModel getTodayModelWithViewId(){
//        return todayModelFromDayView;
//    }
//    private static void checkThenSetTodayModel(DayModel dayModel){
//        if(getTodayModel().dayIdx == dayModel.dayIdx){
//            setTodayModel(dayModel);
//        }
//    }
//    private static DayModel getTodayModel(){
//        if(todayModelFromDayView.date == null){
//            ArrayList<DayModel> totalDayList = CalendarDataController.getTotalDayList();
//
//            // 1. 오늘 모델 값 구하기
//            int dayListCnt =  totalDayList.size();
//            for(int i = 0; i < dayListCnt; ++i){
//                DayModel day = totalDayList.get(i);
//
//                if (DateHelper.getInstance().isToday(day.date) && !day.isOutMonth){
//                    setTodayModel(day);
//                    break;
//                }
//            }
//        }
//
//        return todayModelFromDayView;
//    }
//    private static void setTodayModel(DayModel dayModel){
//        todayModelFromDayView = dayModel;
//    }





    // Yesterday Model
    public static DayModel getYesterdayModelWithViewId(){
        return yesterdayModelFromDayView;
    }
    public static void checkThenSetYesterdayModel(DayModel dayModel){
        if(getYesterdayModel().dayIdx == dayModel.dayIdx){
            setYesterdayModel(dayModel);
        }
    }
    public static DayModel getYesterdayModel(){
        if(yesterdayModelFromDayView.getDate() == null){
            ArrayList<DayModel> totalDayList = CalendarDataController.getTotalDayList();

            // 1. 오늘 모델 값 구하기
            int dayListCnt =  totalDayList.size();
            for(int i = 0; i < dayListCnt; ++i){
                DayModel day = totalDayList.get(i);

                if (DateHelper.getInstance().isYesterday(day.getDate()) && !day.isOutMonth){
                    setYesterdayModel(day);
                    break;
                }
            }
        }

        return yesterdayModelFromDayView;
    }
    private static void setYesterdayModel(DayModel dayModel){
        yesterdayModelFromDayView = dayModel;
    }





    // Total Day List - 통계를 위한 모든 DayModel 리스트 (일월/ 전월 없음)
    private static void createTotalDayList(ArrayList<DayModel> list){
        storeTotalDayList(list);
    }
    public static ArrayList<DayModel> getTotalDayList(){
        return dumpTotalDayList();
    }
    public static void updateTotalDayList(DayModel dayModel){
        ArrayList<DayModel> totalDayList = CalendarDataController.dumpTotalDayList();
        int totalDayListCnt = totalDayList.size();

        for(int i = 0; i < totalDayListCnt; ++ i){
            DayModel item = totalDayList.get(i);
            if(item.dayIdx == dayModel.dayIdx){
                totalDayList.set(i, dayModel);
                break;
            }
        }

        CalendarDataController.storeTotalDayList(totalDayList);
    }

    private static void storeTotalDayList(ArrayList<DayModel> list){
        getEditPreference().putString(KEY_TOTAL_DAY_LIST, new Gson().toJson(list)).apply();
    }
    private static ArrayList<DayModel> dumpTotalDayList(){
        ArrayList<DayModel> dump = new Gson().fromJson(getReadPreference().getString(KEY_TOTAL_DAY_LIST, null), new TypeToken<ArrayList<DayModel>>(){}.getType());
        if(dump == null){ return  new ArrayList<>(); }
        return dump;
    }




    // Recent Day List - 통계를 위한 임의 갯수 DayModel 리스트
//    public static ArrayList<DayModel> getRecentDayFromToday(){
//
//    }
    public static ArrayList<DayModel> getNoDrinkDayList(Date from){
        int fromDayIdx = 0;
        ArrayList<DayModel> totalDayList = CalendarDataController.getTotalDayList();


        // 1. 목적 모델 값 구하기
        int dayListCnt =  totalDayList.size();
        for(int i = 0; i < dayListCnt; ++i){
            DayModel day = totalDayList.get(i);

            // 같은 일인지 확인
            if (DateHelper.getInstance().isSameDay(from, day.getDate())){
                fromDayIdx = i;
                break;
            }
        }

        ArrayList<DayModel> noDrinkDayList = new ArrayList<>();
        for (int i = fromDayIdx; i > 0; --i){
            DayModel day = totalDayList.get(i);
            if(day.drunkLevel > 0) break;
            if(day.isSaved) noDrinkDayList.add(day);
        }

        return noDrinkDayList;
    }

}
