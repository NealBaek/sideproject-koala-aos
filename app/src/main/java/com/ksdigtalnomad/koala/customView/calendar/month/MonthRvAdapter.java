package com.ksdigtalnomad.koala.customView.calendar.month;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.customView.calendar.CalendarConstUtils;
import com.ksdigtalnomad.koala.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.customView.calendar.day.DayModel;
import com.ksdigtalnomad.koala.customView.calendar.day.DayView;

import java.util.ArrayList;

/**
 * Created by ooddy on 08/05/2019.
 */

public class MonthRvAdapter extends RecyclerView.Adapter{

    private Context context;
    private MonthModel monthModel;
    private CalendarView.EventInterface eventInterface;


    public MonthRvAdapter(Context context, MonthModel monthModel, CalendarView.EventInterface eventInterface){
        this.context = context;
        this.monthModel = monthModel;
        this.eventInterface = eventInterface;
    }


    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        int targetWidth = viewGroup.getMeasuredWidth()/7;
        int targetHeight = viewGroup.getMeasuredHeight()/6;

        DayView dayView = new DayView(context, eventInterface);
        dayView.setMinimumWidth(targetWidth);
        dayView.setMinimumHeight(targetHeight);

        return new ViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


//        DayModel dayModel = new DayModel();
//        dayModel.day = i;
//        dayModel.daySeq = i;
//        dayModel.drunkLevel = ((int)( Math.random() * 10)) % 5;
//        dayModel.friendList = new ArrayList<String>();
//        dayModel.foodList = new ArrayList<String>();
//        dayModel.liquorList = new ArrayList<String>();
//
//
//
//        if(dayModel.drunkLevel != CalendarConstUtils.DRUNK_LV_0){
//            if(i%2 == 0) dayModel.friendList.add("아이유 외 1");
//
//            if(i%3 == 0) dayModel.foodList.add("곱창 외 1");
//
//            dayModel.liquorList.add("소주 외 1");
//
//            if(i%4 == 0) dayModel.memo = "송별회";
//        }

        DayView dayView = ((ViewHolder) viewHolder).dayView;

        dayView.setDayModel(monthModel.dayList.get(i));


    }

    @Override
    public int getItemCount() { return monthModel.dayList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {

        DayView dayView;

        ViewHolder(DayView dayView) {
            super(dayView);
            this.dayView = dayView;
        }


    }
}
