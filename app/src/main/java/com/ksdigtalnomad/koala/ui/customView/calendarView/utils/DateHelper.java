package com.ksdigtalnomad.koala.ui.customView.calendarView.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private DateHelper(){
        df = new SimpleDateFormat("yyyy.MM.dd");


        today = new Date();
        todayStr = df.format(today);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        yesterday = calendar.getTime();
        yesterdayStr = df.format(yesterday);

    }
    private static DateHelper instance = null;
    public static DateHelper getInstance(){
        if (instance == null){  instance = new DateHelper(); }
        return instance;
    }

    private Date today;
    private SimpleDateFormat df;
    private String todayStr;
    private Date yesterday;
    private String yesterdayStr;

    public boolean isToday(Date toCompare){
        return todayStr.equals(df.format(toCompare));
    }
    public boolean isYesterday(Date toCompare){
        return yesterdayStr.equals(df.format(toCompare));
    }

    public boolean isAfterToday(Date toCompare){
        return toCompare.after(today);
    }


    public String getDateStr(String pattern, Date toConvert){
        return new SimpleDateFormat(pattern).format(toConvert);
    }

    public String getTodayStr(String pattern){
        return new SimpleDateFormat(pattern).format(today);
    }
}
