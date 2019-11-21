package com.ksdigtalnomad.koala.util;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FBEventLogHelper {

    // v 0.1.0
    private static final String INPUT_DONE = "DAY_DETAIL_input_done";
    private static final String FRIENDS_ADD = "DAY_DETAIL_LIST_EDIT_FRIEND_add";
    private static final String FRIENDS_ADD_DONE = "DAY_DETAIL_LIST_EDIT_FRIEND_add_done";
    private static final String FRIENDS_INPUT_DONE = "DAY_DETAIL_LIST_EDIT_FRIEND_input_done";
    private static final String DRINK_ADD = "DAY_DETAIL_LIST_EDIT_DRINK_add";
    private static final String DRINK_ADD_DONE = "DAY_DETAIL_LIST_EDIT_DRINK_add_done";
    private static final String DRINK_INPUT_DONE = "DAY_DETAIL_LIST_EDIT_DRINK_input_done";
    private static final String FOOD_ADD = "DAY_DETAIL_LIST_EDIT_FOOD_add";
    private static final String FOOD_ADD_DONE = "DAY_DETAIL_LIST_EDIT_FOOD_add_done";
    private static final String FOOD_INPUT_DONE = "DAY_DETAIL_LIST_EDIT_FOOD_input_done";
    private static final String KAKAO_OPEN_CHAT_ROON = "TAB_SETTINGS_kakao_open_chat_room";
    private static final String PLAY_STORE_COMPLEMENT = "TAB_SETTINGS_play_store_compliment";
    private static final String APP_SHARE = "TAB_SETTINGS_app_share";
    private static final String EXIT = "EXIT";


    public static void onInputDoneClick(DayModel dayModel){
        Runnable task = ()->{

            Bundle bundle = new Bundle();
            bundle.putInt("drunk_level", dayModel.drunkLevel);
            bundle.putString("friend_list", CalendarConstUtils.getFullStrFromFriendList(dayModel.friendList));
            bundle.putString("food_list", CalendarConstUtils.getFullStrFromFoodList(dayModel.foodList));
            bundle.putString("drink_list", CalendarConstUtils.getFullStrFromDrinkList(dayModel.drinkList));
            bundle.putString("memo", dayModel.memo);
            bundle.putString("date", DateHelper.getInstance().getDateStr(DateHelper.FORMAT_FULL, dayModel.date));
            bundle.putString("place", Locale.getDefault().toString());
            bundle.putBoolean("isToday", DateHelper.getInstance().isToday(dayModel.date));
            bundle.putString("createdAt", DateHelper.getInstance().getTodayStr(DateHelper.FORMAT_FULL));

            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(INPUT_DONE, bundle);
        };

        task.run();
    }

    public static void onFriendsAdd(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FRIENDS_ADD, new Bundle());
        };
        task.run();
    }
    public static void onFriendsAddDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FRIENDS_ADD_DONE, new Bundle());
        };
        task.run();
    }
    public static void onFriendsInputDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FRIENDS_INPUT_DONE, new Bundle());
        };
        task.run();
    }
    public static void onDrinkAdd(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(DRINK_ADD, new Bundle());
        };
        task.run();
    }
    public static void onDrinkAddDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(DRINK_ADD_DONE, new Bundle());
        };
        task.run();
    }
    public static void onDrinkInputDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(DRINK_INPUT_DONE, new Bundle());
        };
        task.run();
    }
    public static void onFoodAdd(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FOOD_ADD, new Bundle());
        };
        task.run();
    }
    public static void onFoodAddDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FOOD_ADD_DONE, new Bundle());
        };
        task.run();
    }
    public static void onFoodInputDone(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(FOOD_INPUT_DONE, new Bundle());
        };
        task.run();
    }
    public static void onKakaoOpenChatRoom(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(KAKAO_OPEN_CHAT_ROON, new Bundle());
        };
        task.run();
    }
    public static void onPlayStoreComplement(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(PLAY_STORE_COMPLEMENT, new Bundle());
        };
        task.run();
    }
    public static void onAppShare(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(APP_SHARE, new Bundle());
        };
        task.run();
    }
    public static void onExit(){
        Runnable task = ()->{
            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(EXIT, new Bundle());
        };
        task.run();
    }





    // v 0.2.1
    private static final String ALARM_DAILY_AGREE = "TAB_SETTINGS_push_alarm_daily_agree";
    private static final String ALARM_DAILY_TIME = "TAB_SETTINGS_push_alarm_daily_time";
    private static final String ALARM_DAILY_PUSH_CLICK = "Push_alarm_daily_click";
    private static final String ALARM_DAILY_PUSH_INPUT_DONE = "Push_alarm_daily_click_detail_input_done";


    public static void onAlarmDailyAgree(boolean isAgree, String createdAt){
        Runnable task = ()->{
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAgree", isAgree);
            bundle.putString("createdAt", createdAt);

            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(ALARM_DAILY_AGREE, bundle);
        };
        task.run();
    }
    public static void onAlarmDailyTime(String settingTime){
        Runnable task = ()->{
            Bundle bundle = new Bundle();
            bundle.putString("settingTime", settingTime); // hh시mm분
            bundle.putString("createdAt", DateHelper.getInstance().getTodayStr(DateHelper.FORMAT_FULL));

            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(ALARM_DAILY_TIME, bundle);
        };
        task.run();
    }
    public static void onAlarmDailyPushClick(String settingTime){
        Runnable task = ()->{
            Bundle bundle = new Bundle();
            bundle.putString("settingTime", settingTime); // hh시mm분
            bundle.putString("createdAt", DateHelper.getInstance().getTodayStr(DateHelper.FORMAT_FULL));

            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(ALARM_DAILY_PUSH_CLICK, new Bundle());
        };
        task.run();
    }
    public static void onAlarmDailyInputDone(String settingTime){
        Runnable task = ()->{
            Bundle bundle = new Bundle();
            bundle.putString("settingTime", settingTime); // hh시mm분
            bundle.putString("createdAt", DateHelper.getInstance().getTodayStr(DateHelper.FORMAT_FULL));

            FirebaseAnalytics
                    .getInstance(BaseApplication.getInstance().getApplicationContext())
                    .logEvent(ALARM_DAILY_PUSH_INPUT_DONE, bundle);
        };
        task.run();
    }
}
