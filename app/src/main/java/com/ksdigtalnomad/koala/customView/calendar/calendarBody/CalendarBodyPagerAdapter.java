package com.ksdigtalnomad.koala.customView.calendar.calendarBody;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ksdigtalnomad.koala.customView.calendar.month.MonthItemDecoration;
import com.ksdigtalnomad.koala.customView.calendar.month.MonthModel;
import com.ksdigtalnomad.koala.customView.calendar.month.MonthRvAdapter;
import com.ksdigtalnomad.koala.customView.calendar.month.MonthRvLayoutManager;

import java.util.ArrayList;

public class CalendarBodyPagerAdapter extends PagerAdapter {

    CalendarModel calendarModel;
    ArrayList<LinearLayout> parentList = new ArrayList<>();

    public CalendarBodyPagerAdapter(CalendarModel calendarModel){
        this.calendarModel = calendarModel;
    }

    @Override
    public int getCount() {
        return calendarModel.monthList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return true;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

//        LinearLayout parent = new LinearLayout(container.getContext());
////        parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//        RecyclerView calendarRv = createCalendarRv(container.getContext(), calendarModel.monthList.get(position));
//
//        calendarRv.setBackgroundColor(Color.RED);
//
//        parentList.add(parent);
//
//        container.addView(parent);
//
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(container.getMeasuredWidth(), View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(container.getMeasuredHeight(), View.MeasureSpec.EXACTLY);
//
//        calendarRv.measure(widthSpec, heightSpec);
//
//        Log.d("ABC", "instantiateItem, p_w: " + container.getMeasuredWidth() + ", p_h " + container.getMeasuredHeight());
//        Log.d("ABC", "instantiateItem, c_w: " + calendarRv.getMeasuredWidth() + ", c_h " + calendarRv.getMeasuredHeight());
//
//        calendarRv.setAdapter(new MonthRvAdapter(container.getContext(), calendarModel.monthList.get(position)));
//
//
//        return calendarRv;

        View view = new View(container.getContext());
        view.setBackgroundColor(Color.RED);

        container.addView(view);

        return container;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }

    private RecyclerView createCalendarRv(Context context, MonthModel monthModel){

        RecyclerView recyclerView = new RecyclerView(context);

        // Bg Color
        recyclerView.setBackgroundColor(Color.LTGRAY);

        // Layout Manager & dividers
        MonthRvLayoutManager layoutManager = new MonthRvLayoutManager(context, 7);
        MonthItemDecoration itemDecoration    = new MonthItemDecoration(1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        // Adapter
        recyclerView.setAdapter(new MonthRvAdapter(context, monthModel));

        return recyclerView;
    }
}
