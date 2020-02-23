package com.ksdigtalnomad.koala.ui.customView.calendarView.day;

import android.util.Log;

import com.ksdigtalnomad.koala.data.models.calendar.Drink;
import com.ksdigtalnomad.koala.data.models.calendar.Food;
import com.ksdigtalnomad.koala.data.models.calendar.Friend;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayModel implements Cloneable{
    public int dayViewId; // Day View 에 접근하기 위한 아이디

    public int daySeq; // 일주일 중 몇 번째 요일 인지
    public boolean isOutMonth;

    public int year;
    public int month;
    public int day;
    private Date date;
    public void setDate(Date date){
        if (date == null) return;
        this.date = date;
    }
    public Date getDate(){
        if(date == null){
            try{
                if(year > 0 && month > 0 && day > 0){
                    date = (new SimpleDateFormat(DateHelper.FORMAT_DATE)).parse("" + year + "." + (month < 10 ? ("0" + month) : month) + "." + (day < 10 ? ("0" + day) : day));
                }
            }catch (ParseException e){
                Log.d("ABC", "ParseException: " + e.getMessage());
            }
        }
        return date;
    }

    public String dayOfTheWeek;

    public int dayIdx; //
    public int monthIdx; //
    public int yearIdx; //

    public int drunkLevel;
    public ArrayList<Friend> friendList = new ArrayList<>();
    public ArrayList<Food> foodList = new ArrayList<>();
    public ArrayList<Drink> drinkList = new ArrayList<>();
    public double expense = 0;
    public String memo = "";

    public boolean isSaved; // 한번이라도 저장되었는지 확인.


    public DayModel clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((DayModel)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }

}
