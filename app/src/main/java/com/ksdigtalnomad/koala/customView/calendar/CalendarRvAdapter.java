package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        DayView dayView = new DayView(context);
        dayView.setMinimumHeight(viewGroup.getMeasuredHeight()/6);
        dayView.setMinimumWidth(viewGroup.getMeasuredWidth()/7);


//        dayView.setLayoutParams(new ViewGroup.LayoutParams(viewGroup.getMeasuredHeight()/6, viewGroup.getMeasuredWidth()/7));
//
        Log.d("ABC", "onCreateViewHolder, w: " + viewGroup.getMeasuredWidth()/6 + ", h: " + viewGroup.getMeasuredHeight()/7);
 //
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredWidth(), View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getMeasuredHeight(), View.MeasureSpec.EXACTLY);
//
//        dayView.measure(heightSpec, widthSpec);

        return new ViewHolder(dayView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DayView dayView = ((ViewHolder) viewHolder).dayView;
//        dayView.setChildrenLp(dayView.dayHeaderLayout);

//        dayView.requestLayout();
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
