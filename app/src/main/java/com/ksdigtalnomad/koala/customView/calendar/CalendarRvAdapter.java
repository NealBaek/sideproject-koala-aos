package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        dayView.setBackgroundColor(Color.LTGRAY);

//        viewGroup.addView(dayView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


//        dayView.setLayoutParams(viewGroup.getLayoutParams());

//        Log.d("ABC", "viewGroup: " + (viewGroup == null));
//
//        dayView.measure( View.MeasureSpec.makeMeasureSpec(targetWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(targetHeight, View.MeasureSpec.UNSPECIFIED));

//        dayView.measure(View.MeasureSpec.makeMeasureSpec());

//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(viewGroup.getMeasuredWidth()/7, viewGroup.getMeasuredHeight()/6);
//        dayView.setLayoutParams(lp);

        return new ViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DayView dayView = ((ViewHolder) viewHolder).dayView;

        dayView.requestLayout();
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
