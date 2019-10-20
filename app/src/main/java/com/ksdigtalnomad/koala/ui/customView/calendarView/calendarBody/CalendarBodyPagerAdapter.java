package com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthItemDecoration;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthRvAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthRvLayoutManager;

import java.util.ArrayList;

public class CalendarBodyPagerAdapter extends PagerAdapter {

    private CalendarModel calendarModel;
    private CalendarView.EventInterface eventInterface;
    ArrayList<View> parentList = new ArrayList<>();

    private int COLOR_LIGHT_GRAY = Color.LTGRAY;

    public CalendarBodyPagerAdapter(Context context, CalendarModel calendarModel, CalendarView.EventInterface eventInterface){

        COLOR_LIGHT_GRAY = context.getResources().getColor(R.color.colorLightGray);

        this.calendarModel = calendarModel;
        this.eventInterface = eventInterface;

        int parentCnt = calendarModel.monthList.size();
        int randomId = (int) (Math.random() * 10000000);

        for(int i = 0; i < parentCnt; ++i){
            RecyclerView calendarRv = createCalendarRv(context, calendarModel.monthList.get(i), eventInterface, randomId);
            parentList.add(calendarRv);

            randomId += CalendarConstUtils.ID_CNT_MONTH;
        }
    }

    public void notifyDataChange(CalendarModel calendarModel){
        this.calendarModel = calendarModel;
        int parentCnt = parentList.size();
        for (int i = 0; i < parentCnt; ++i){
            ((MonthRvAdapter)((RecyclerView)parentList.get(i)).getAdapter()).notifyDataChange(calendarModel.monthList.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return calendarModel.monthList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        View child = parentList.get(position);
        container.addView(child, container.getMeasuredWidth(), container.getMeasuredHeight());

        return child;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }

    private RecyclerView createCalendarRv(Context context, MonthModel monthModel, CalendarView.EventInterface eventInterface, int randomId){

        RecyclerView recyclerView = new RecyclerView(context);

        // Bg Color
        recyclerView.setBackgroundColor(COLOR_LIGHT_GRAY);

        // Layout Manager & dividers
        MonthRvLayoutManager layoutManager = new MonthRvLayoutManager(context, 7);
        MonthItemDecoration itemDecoration = new MonthItemDecoration(1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        // Adapter
        recyclerView.setAdapter(new MonthRvAdapter(context, monthModel, eventInterface, randomId));

        return recyclerView;
    }

}
