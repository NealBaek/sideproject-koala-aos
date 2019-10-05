package com.ksdigtalnomad.koala.util;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import java.util.ArrayList;

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
    private static final String APP_SHARE = "TAB_SETTINGS_app_share";
    private static final String EXIT = "EXIT";


    public static void onInputDoenClick(DayModel dayModel){
        Runnable task = ()->{
            Bundle bundle = new Bundle();
            bundle.putInt("drunk_level", dayModel.drunkLevel);
            bundle.putStringArrayList("friend_list", dayModel.friendList);
            bundle.putStringArrayList("food_list", dayModel.foodList);
            bundle.putStringArrayList("drink_list", dayModel.drinkList);
            bundle.putString("memo", dayModel.memo);


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
    public static void onFriendsAddDoen(){
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
