package com.ksdigtalnomad.koala.customView.calendar.calendarBody;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CalendarBodyPagerAdapter extends PagerAdapter {

    CalendarModel calendarModel;
    ArrayList<Fragment> fragmentList = new ArrayList<>();

    public CalendarBodyPagerAdapter(CalendarModel calendarModel) {
        super();
        this.calendarModel = calendarModel;


    }

    @Override
    public int getCount() { return calendarModel.monthList.size(); }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) { return false; }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Fragment fragment = new Fragment();

        fragmentList.add(fragment);

//        container.addView(fragment);

        return super.instantiateItem(container, position);
    }



}
