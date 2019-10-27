package com.ksdigtalnomad.koala.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.Drink;
import com.ksdigtalnomad.koala.data.models.Food;
import com.ksdigtalnomad.koala.data.models.Friend;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class MainDataController {
    private static final String PREF_FILE_NAME = "CALENDAR_DATA";
    private static final String KEY_FRIEND_LIST = "KEY_FRIEND_LIST";
    private static final String KEY_DRINK_LIST = "KEY_DRINK_LIST";
    private static final String KEY_FOOD_LIST = "KEY_FOOD_LIST";


    private MainDataController(){}


    // CRUD
    public static ArrayList<Friend> getFriendList(){
        return dumpFriendList();
    }
    public static void setFriendList(ArrayList<Friend> list){
        ArrayList<Friend> toSaveList = new ArrayList<>();
        toSaveList.addAll(list);

        int cnt = toSaveList.size();

        for(int i = 0; i < cnt; ++i){
            Friend cloned = toSaveList.get(i).clone();
            cloned.setSelected(false);
            toSaveList.set(i, cloned);
        }
        MainDataController.storeFriendList(toSaveList);
    }
//    public static void addFriend(String name){
//
//        ArrayList<Friend> list =  dumpFriendList();
//
//        if(name == null || name.length() <= 0){
//            return;
//        }
//
//        Friend item = Friend.builder().build();
//        item.setName(name);
//
//        list.add(0, item);
//
//        storeFriendList(list);
//
//        return;
//    }
//    public static ArrayList<Friend> updateFriend(String before, String after){
//        ArrayList<Friend> list =  dumpFriendList();
//
//        if(before == null || before.length() <= 0 || after == null || after.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Friend temp = list.get(i);
//
//            if(temp.getName().equals(before)){
//                temp.setName(after);
//                list.set(i, temp);
//                storeFriendList(list);
//                break;
//            }
//        }
//
//        return list;
//    }
//    public static ArrayList<Friend> deleteFriend(String name){
//        ArrayList<Friend> list =  dumpFriendList();
//
//        if(name == null || name.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Friend temp = list.get(i);
//            if(temp.getName().equals(name)){
//                list.remove(i);
//                storeFriendList(list);
//                break;
//            }
//        }
//
//        return list;
//    }

    public static ArrayList<Food> getFoodList(){
        return dumpFoodList();
    }
    public static void setFoodList(ArrayList<Food> list){
        ArrayList<Food> toSaveList = new ArrayList<>();
        toSaveList.addAll(list);

        int cnt = toSaveList.size();

        for(int i = 0; i < cnt; ++i){
            Food cloned = toSaveList.get(i).clone();
            cloned.setSelected(false);
            toSaveList.set(i, cloned);
        }
        MainDataController.storeFoodList(toSaveList);
    }
//    public static void addFood(String name){
//
//        ArrayList<Food> list =  dumpFoodList();
//
//        if(name == null || name.length() <= 0){
//            return;
//        }
//
//        Food item = Food.builder().build();
//        item.setName(name);
//
//        list.add(0, item);
//
//        storeFoodList(list);
//
//        return;
//    }
//    public static ArrayList<Food> updateFood(String before, String after){
//        ArrayList<Food> list =  dumpFoodList();
//
//        if(before == null || before.length() <= 0 || after == null || after.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Food temp = list.get(i);
//            if(temp.getName().equals(before)){
//                temp.setName(after);
//                list.set(i, temp);
//                storeFoodList(list);
//                break;
//            }
//        }
//
//        return list;
//    }
//    public static ArrayList<Food> deleteFood(String name){
//        ArrayList<Food> list =  dumpFoodList();
//
//        if(name == null || name.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Food temp = list.get(i);
//            if(temp.getName().equals(name)){
//                list.remove(i);
//                storeFoodList(list);
//                break;
//            }
//        }
//
//        return list;
//    }

    public static ArrayList<Drink> getDrinkList(){
        return dumpDrinkList();
    }
    public static void setDrinkList(ArrayList<Drink> list){
        ArrayList<Drink> toSaveList = new ArrayList<>();
        toSaveList.addAll(list);
        int cnt = toSaveList.size();

        for(int i = 0; i < cnt; ++i){
            Drink cloned = toSaveList.get(i).clone();
            cloned.setSelected(false);
            toSaveList.set(i, cloned);
        }
        MainDataController.storeDrinkList(toSaveList);
    }
//    public static void addDrink(String name){
//
//        ArrayList<Drink> list =  dumpDrinkList();
//
//        if(name == null || name.length() <= 0){
//            return;
//        }
//
//        Drink item = Drink.builder().build();
//        item.setName(name);
//
//        list.add(0, item);
//
//        storeDrinkList(list);
//
//        return;
//    }
//    public static ArrayList<Drink> updateDrink(String before, String after){
//        ArrayList<Drink> list =  dumpDrinkList();
//
//        if(before == null || before.length() <= 0 || after == null || after.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Drink temp = list.get(i);
//            if(temp.getName().equals(before)){
//                temp.setName(after);
//                list.set(i, temp);
//                storeDrinkList(list);
//                break;
//            }
//        }
//
//        return list;
//    }
//    public static ArrayList<Drink> deleteDrink(String name){
//        ArrayList<Drink> list =  dumpDrinkList();
//
//        if(name == null || name.length() <= 0){
//            return list;
//        }
//
//        int listSize = list.size();
//        for(int i = 0; i < listSize; ++ i){
//            Drink temp = list.get(i);
//            if(temp.getName().equals(name)){
//                list.remove(i);
//                storeDrinkList(list);
//                break;
//            }
//        }
//
//        return list;
//    }



    // Dump | Store
    private static void storeFriendList(ArrayList<Friend> list){
        getEditPreference().putString(KEY_FRIEND_LIST, new Gson().toJson(list)).apply();
    }
    private static ArrayList<Friend> dumpFriendList(){
        ArrayList<Friend> dump = new Gson().fromJson(getReadPreference().getString(KEY_FRIEND_LIST, null), new TypeToken<ArrayList<Friend>>(){}.getType());
        if(dump == null) dump = createFriendList();
        return dump;
    }
    private static void storeFoodList(ArrayList<Food> list){
        getEditPreference().putString(KEY_FOOD_LIST, new Gson().toJson(list)).apply();
    }
    private static ArrayList<Food> dumpFoodList(){
        ArrayList<Food> dump = new Gson().fromJson(getReadPreference().getString(KEY_FOOD_LIST, null), new TypeToken<ArrayList<Food>>(){}.getType());
        if(dump == null) dump = createFoodList();
        return dump;
    }
    private static void storeDrinkList(ArrayList<Drink> list){
        getEditPreference().putString(KEY_DRINK_LIST, new Gson().toJson(list)).apply();
    }
    private static ArrayList<Drink> dumpDrinkList(){
        ArrayList<Drink> dump = new Gson().fromJson(getReadPreference().getString(KEY_DRINK_LIST, null), new TypeToken<ArrayList<Drink>>(){}.getType());
        if(dump == null) dump = createDrinkList();
        return dump;
    }



    // Create
    private static ArrayList<Friend> createFriendList(){

        ArrayList<Friend> friendList = new ArrayList<>();

        Friend friend = Friend.builder().build();
        friend.setName(BaseApplication.getInstance().getResources().getString(R.string.default_friend_list));

        friendList.add(friend);

        return friendList;
    }
    private static ArrayList<Food> createFoodList(){

        ArrayList<Food> foodList = new ArrayList<>();

        Food food = Food.builder().build();
        food.setName(BaseApplication.getInstance().getResources().getString(R.string.default_food_list));

        foodList.add(food);

        return foodList;
    }
    private static ArrayList<Drink> createDrinkList(){

        ArrayList<Drink> drinkList = new ArrayList<>();

        return drinkList;
    }



    // Preference
    private static SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private static SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }
}
