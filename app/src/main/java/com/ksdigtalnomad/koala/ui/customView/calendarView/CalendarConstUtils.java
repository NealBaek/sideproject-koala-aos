package com.ksdigtalnomad.koala.ui.customView.calendarView;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.calendar.Drink;
import com.ksdigtalnomad.koala.data.models.calendar.Food;
import com.ksdigtalnomad.koala.data.models.calendar.Friend;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import java.util.ArrayList;

/**
 * Created by ooddy on 10/05/2019.
 */

public class CalendarConstUtils {

    public static final String[] DAYS_OF_THE_WEEK = {
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_sun),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_mon),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_tue),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_wed),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_thu),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_fri),
            BaseApplication.getInstance().getResources().getString(R.string.calendar_day_sat)
    };

    public static final int[] NUM_DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int DAY_SEQ_SUNDAY = 0;
    public static final int DAY_SEQ_SATURDAY = 6;

    public static final int ID_CNT_MONTH = 100;
    public static final int ID_CNT_ISOUTMONTH = 50;

    public static final String DRUNK_LV_0_STR = BaseApplication.getInstance().getResources().getString(R.string.calendar_detail_drunk_level_0);
    public static final String DRUNK_LV_1_STR = BaseApplication.getInstance().getResources().getString(R.string.calendar_detail_drunk_level_1);
    public static final String DRUNK_LV_2_STR = BaseApplication.getInstance().getResources().getString(R.string.calendar_detail_drunk_level_2);
    public static final String DRUNK_LV_3_STR = BaseApplication.getInstance().getResources().getString(R.string.calendar_detail_drunk_level_3);
    public static final String DRUNK_LV_MAX_STR = BaseApplication.getInstance().getResources().getString(R.string.calendar_detail_drunk_level_max);


    // Color
    public static final int COLOR_SUNDAY = Color.RED;
    public static final int COLOR_SATURDAY = Color.BLUE;
    public static final int COLOR_WEEKDAY = Color.BLACK;
    public static final int COLOL_MAIN = BaseApplication.getInstance().getResources().getColor(R.color.colorMain);
    public static int getDayColor(int daySeq){

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
                return ColorUtils.setAlphaComponent(COLOL_MAIN, /** 0~ 255 */ 255 * 1/5);
            case DRUNK_LV_2:
                return ColorUtils.setAlphaComponent(COLOL_MAIN, /** 0~ 255 */ 255 * 2/5);
            case DRUNK_LV_3:
                return ColorUtils.setAlphaComponent(COLOL_MAIN, /** 0~ 255 */ 255 * 3/5);
            case DRUNK_LV_MAX:
                return ColorUtils.setAlphaComponent(COLOL_MAIN, /** 0~ 255 */ 255 * 4/4);
            default:
                return ColorUtils.setAlphaComponent(Color.LTGRAY, 100);
        }

    }
    public static String getDrunkLvComment(int drunkLv){
        switch (drunkLv){
            case DRUNK_LV_0:
                return DRUNK_LV_0_STR;
            case DRUNK_LV_1:
                return DRUNK_LV_1_STR;
            case DRUNK_LV_2:
                return DRUNK_LV_2_STR;
            case DRUNK_LV_3:
                return DRUNK_LV_3_STR;
            case DRUNK_LV_MAX:
                return DRUNK_LV_MAX_STR;
            default:
                return "";
        }
    }


    // DayView Texts
    public static String getLongStrFromFriendList(ArrayList<Friend> list){
        return getLongStr(getFullStrFromFriendList(list));
    }
    public static String getLongStrFromFoodList(ArrayList<Food> list){
        return getLongStr(getFullStrFromFoodList(list));
    }
    public static String getLongStrFromDrinkList(ArrayList<Drink> list){
        return getLongStr(getFullStrFromDrinkList(list));
    }

    public static String getShortStrFromFriendList(ArrayList<Friend> list){
        return getShortStr(getFullStrFromFriendList(list));
    }
    public static String getShortStrFromFoodList(ArrayList<Food> list){
        return getShortStr(getFullStrFromFoodList(list));
    }
    public static String getShortStrFromDrinkList(ArrayList<Drink> list){
        return getShortStr(getFullStrFromDrinkList(list));
    }


    public static String getFullStrFromFriendList(ArrayList<Friend> list){
        if(list == null || list.size() <= 0) return "";

        String toReturn = "";
        int cnt = list.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + list.get(i).getName();  }

        return toReturn;
    }
    public static String getFullStrFromFoodList(ArrayList<Food> list){
        if(list == null || list.size() <= 0) return "";

        String toReturn = "";
        int cnt = list.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + list.get(i).getName();  }

        return toReturn;
    }
    public static String getFullStrFromDrinkList(ArrayList<Drink> list){
        if(list == null || list.size() <= 0) return "";

        String toReturn = "";
        int cnt = list.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + list.get(i).getName();  }

        return toReturn;
    }

    public static String getLongStr(String string){

        if(string == null || string.isEmpty()) return "";

        if(string.length() < 25) return string;

        return string.substring(0, 25) + " ..";
    }
    public static String getShortStr(String string){

        if(string == null || string.isEmpty()) return "";

        if(string.length() < 7) return string;

        return string.substring(0, 6) + " ..";
    }
}
