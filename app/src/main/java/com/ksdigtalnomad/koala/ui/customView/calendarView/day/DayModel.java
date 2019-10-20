package com.ksdigtalnomad.koala.ui.customView.calendarView.day;

import com.ksdigtalnomad.koala.data.models.Drink;
import com.ksdigtalnomad.koala.data.models.Food;
import com.ksdigtalnomad.koala.data.models.Friend;

import java.util.ArrayList;

public class DayModel implements Cloneable{
    public int dayViewId; // Day View 에 접근하기 위한 아이디

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
    public ArrayList<Friend> friendList = new ArrayList<>();
    public ArrayList<Food> foodList = new ArrayList<>();
    public ArrayList<Drink> drinkList = new ArrayList<>();
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
