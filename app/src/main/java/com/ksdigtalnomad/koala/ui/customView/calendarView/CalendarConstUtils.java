package com.ksdigtalnomad.koala.ui.customView.calendarView;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import java.util.ArrayList;

/**
 * Created by ooddy on 10/05/2019.
 */

public class CalendarConstUtils {

    public static final String[] DAYS_OF_THE_WEEK = {"일", "월", "화", "수", "목", "금", "토"};

    public static final int[] NUM_DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int DAY_SEQ_SUNDAY = 0;
    public static final int DAY_SEQ_SATURDAY = 6;


    // Color
    public static final int COLOR_SUNDAY = Color.RED;
    public static final int COLOR_SATURDAY = Color.BLUE;
    public static final int COLOR_WEEKDAY = Color.BLACK;
    public static int getDayColor(int daySeq){

        final int NUM_DAYS_IN_WEEK = 7;

        switch (daySeq) {
            case DAY_SEQ_SUNDAY:
                return COLOR_SUNDAY;
            case DAY_SEQ_SATURDAY:
                return COLOR_SATURDAY;
            default:
                return COLOR_WEEKDAY;
        }
    }

    public static final int DRUNK_LV_0 = 0;
    public static final int DRUNK_LV_1 = 1;
    public static final int DRUNK_LV_2 = 2;
    public static final int DRUNK_LV_3 = 3;
    public static final int DRUNK_LV_MAX = 4;
    public static int getDrunkLvColor(int drunkLv){

        switch (drunkLv){
            case DRUNK_LV_1:
                return ColorUtils.setAlphaComponent(Color.RED, /** 0~ 255 */ 255 * 1/5);
            case DRUNK_LV_2:
                return ColorUtils.setAlphaComponent(Color.RED, /** 0~ 255 */ 255 * 2/5);
            case DRUNK_LV_3:
                return ColorUtils.setAlphaComponent(Color.RED, /** 0~ 255 */ 255 * 3/5);
            case DRUNK_LV_MAX:
                return ColorUtils.setAlphaComponent(Color.RED, /** 0~ 255 */ 255 * 4/4);
            default:
                return ColorUtils.setAlphaComponent(Color.LTGRAY, 100);
        }

    }
    public static String getDrunkLvComment(int drunkLv){

        switch (drunkLv){
            case DRUNK_LV_0:
                return "'단 한잔도 하지 않았어요'";
            case DRUNK_LV_1:
                return "'맥주 한 두잔?'";
            case DRUNK_LV_2:
                return "'칵테일 한 두잔..?'";
            case DRUNK_LV_3:
                return "'소주 한 두병..?'";
            case DRUNK_LV_MAX:
                return "'만취'";
            default:
                return "";
        }

    }


    // DayView Texts
    public static String getShortStr(ArrayList<String> strList){

        if(strList == null || strList.isEmpty()) return "";

        if(strList.size() == 1) return strList.get(0);

        return strList.get(0) + " 외 1";
    }
    public static String getShortStr(String string){

        if(string == null || string.isEmpty()) return "";

        if(string.length() < 10) return string;

        return string.substring(1, 0) + " ..";
    }
}
