package com.ksdigtalnomad.koala.data.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<CalendarModel> calendarModel = new MutableLiveData<>();

    public LiveData<CalendarModel> getCalenderData() {
        if (calendarModel.getValue() == null) {  calendarModel.setValue(CalendarDataController.getCalendarModel());  }
        return calendarModel;
    }
    public void updateDay(DayModel dayModel){
        calendarModel.postValue(CalendarDataController.updateDayModel(dayModel));
    }
}
