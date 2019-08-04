package com.ksdigtalnomad.koala.data.viewModels;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

public class ViewModelHelper{
//    private static ViewModelHelper instance;
//    private CalendarViewModel calendarViewModel;
//
//    private ViewModelHelper(){ calendarViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getInstance()).create(CalendarViewModel.class); }
//    public static ViewModelHelper getInstance(){
//        if(instance == null){ instance = new ViewModelHelper();}
//        return instance;
//    }
//
//    /** Calendar */
//    public void observeCalendar(@NonNull LifecycleOwner owner, @NonNull Observer<CalendarModel> observer){ calendarViewModel.getCalenderData().observe(owner, observer); }
//    public void removeCalendar(@NonNull Observer<CalendarModel> observer){ calendarViewModel.getCalenderData().removeObserver(observer); }
//    public CalendarModel getCalendar(){ return calendarViewModel.getCalenderData().getValue(); }
//    public void updateCalendar(DayModel dayModel){ calendarViewModel.updateDay(dayModel); }
}
