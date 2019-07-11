package com.ksdigtalnomad.koala.ui.customView.day;

import java.util.ArrayList;

public class DayModel implements Cloneable{
    public int daySeq;
    public boolean isOutMonth;

    public int year;
    public int month;
    public int day;

    public String dayOfTheWeek;

    public int index;

    public int drunkLevel;
    public ArrayList<String> friendList;
    public ArrayList<String> foodList;
    public ArrayList<String> liquorList;
    public String memo;


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
