package com.ksdigtalnomad.koala.util;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FBEventLogHelper {

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


    public static void onInputDoenClick(DayModel dayModel){
        Runnable task = ()->{

            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

            Bundle bundle = new Bundle();
            bundle.putInt("drunk_level", dayModel.drunkLevel);
            bundle.putString("friend_list", CalendarConstUtils.getFullStrFromFriendList(dayModel.friendList));
            bundle.putString("food_list", CalendarConstUtils.getFullStrFromFoodList(dayModel.foodList));
            bundle.putString("drink_list", CalendarConstUtils.getFullStrFromDrinkList(dayModel.drinkList));
            bundle.putString("memo", dayModel.memo);
            bundle.putString("date", df.format(dayModel.date));
            bundle.putString("place", Locale.getDefault().toString());
            bundle.putString("createdAt", df.format(new Date()));

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
}
