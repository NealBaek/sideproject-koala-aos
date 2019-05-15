package com.ksdigtalnomad.koala.customView.month;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.customView.day.DayView;

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
