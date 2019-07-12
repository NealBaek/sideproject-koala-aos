package com.ksdigtalnomad.koala.ui.customView.day;

import java.util.ArrayList;

public class DayModel implements Cloneable{
    public int daySeq; // 일주일 중 몇 번째 요일 인지
    public boolean isOutMonth;

    public int year;
    public int month;
    public int day;

    public String dayOfTheWeek;

    public int dayIdx; //
    public int monthIdx; //
    public int yearIdx; //

    public int drunkLevel;
    public ArrayList<String> friendList = new ArrayList<String>();
    public ArrayList<String> foodList = new ArrayList<String>();
    public ArrayList<String> liquorList = new ArrayList<String>();
    public String memo = "";


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
