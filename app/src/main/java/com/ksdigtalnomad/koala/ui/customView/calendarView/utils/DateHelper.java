package com.ksdigtalnomad.koala.ui.customView.calendarView.utils;

import android.util.Log;

import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private DateHelper(){

        today = new Date();
        todayStr = (new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(today);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        yesterday = calendar.getTime();
        yesterdayStr = (new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(yesterday);

    }
    private static DateHelper instance = null;
    public static DateHelper getInstance(){
        if (instance == null){  instance = new DateHelper(); }
        return instance;
    }




    public static final String FORMAT_FULL = "yyyy.MM.dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy.MM.dd";
    public static final String FORMAT_YEAR_MONTH = "yyyy.MM";


    private Date today;
    private DateTimeFormatter df;
    private String todayStr;
    private Date yesterday;
    private String yesterdayStr;


    public boolean isSameYearMonth(Date from, Date to){
        if (from == null || to == null) return false;

        return (new SimpleDateFormat(DateHelper.FORMAT_YEAR_MONTH)).format(to).equals((new SimpleDateFormat(DateHelper.FORMAT_YEAR_MONTH)).format(from));
    }
    public boolean isSameDay(Date from, Date to){
        if (from == null || to == null) return false;

        return (new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(to).equals((new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(from));
    }
    public boolean isToday(Date toCompare){
        if (toCompare == null) return false;

        return todayStr.equals((new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(toCompare));
    }
    public boolean isYesterday(Date toCompare){
        if (toCompare == null) return false;

        return yesterdayStr.equals((new SimpleDateFormat(DateHelper.FORMAT_DATE)).format(toCompare));
    }

    public boolean isAfterToday(Date toCompare){
        if (toCompare == null) return false;
        return toCompare.after(today);
    }


    public String getDateStr(String pattern, Date toConvert){
        return new SimpleDateFormat(pattern).format(toConvert);
    }
    public Date getDateFromStr(String pattern, String dateStr){
        try{
            return new SimpleDateFormat(pattern).parse(dateStr);
        }catch (ParseException e){
            FBEventLogHelper.onError(e);
            return null;
        }
    }

    public Date getTodayDate(){
        return today;
    }
    public String getTodayStr(String pattern){
        return new SimpleDateFormat(pattern).format(today);
    }
    public Date getYesterdayDate(){
        return yesterday;
    }
    public String getYesterdayStr(String pattern){
        return new SimpleDateFormat(pattern).format(yesterday);
    }
}
