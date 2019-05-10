package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ooddy on 08/05/2019.
 */

public class CalendarRvAdapter extends RecyclerView.Adapter{

    private Context context;


    public CalendarRvAdapter(Context context){
        this.context = context;
    }


    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        int targetWidth = viewGroup.getMeasuredWidth()/7;
        int targetHeight = viewGroup.getMeasuredHeight()/6;

        DayView dayView = new DayView(context);
        dayView.setMinimumWidth(targetWidth);
        dayView.setMinimumHeight(targetHeight);

        return new ViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Log.d("ABC", "?? : " + ((int) (Math.random() * 10) %5));

        DayModel dayModel = new DayModel();
        dayModel.day = i;
        dayModel.daySeq = i;
        dayModel.drunkLevel = ((int)( Math.random() * 10)) % 5;
        dayModel.friendList = new ArrayList<String>();
        dayModel.foodList = new ArrayList<String>();
        dayModel.liquorList = new ArrayList<String>();


        if(i%2 == 0) dayModel.friendList.add("아이유 외 1");

        if(i%3 == 0) dayModel.foodList.add("곱창 외 1");

        dayModel.liquorList.add("소주 외 1");

        if(i%4 == 0) dayModel.memo = "송별회";


        DayView dayView = ((ViewHolder) viewHolder).dayView;

        dayView.setDayModel(dayModel);
    }

    @Override
    public int getItemCount() {
        return 42;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        DayView dayView;

        ViewHolder(DayView dayView) {
            super(dayView);
            this.dayView = dayView;
        }


    }
}
