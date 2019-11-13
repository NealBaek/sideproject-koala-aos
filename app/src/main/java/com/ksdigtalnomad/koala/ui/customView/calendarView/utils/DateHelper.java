package com.ksdigtalnomad.koala.ui.customView.calendarView.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    private DateHelper(){}
    private static DateHelper instance = null;
    public static DateHelper getInstance(){
        if (instance == null){  instance = new DateHelper(); }
        return instance;
    }

    private Date today = new Date();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
    private String todayStr = df.format(today);


    public boolean isToday(Date toCompare){
        return todayStr.equals(df.format(toCompare));
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
