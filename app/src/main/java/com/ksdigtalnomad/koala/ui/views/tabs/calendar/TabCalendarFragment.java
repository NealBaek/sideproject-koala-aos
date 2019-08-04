package com.ksdigtalnomad.koala.ui.views.tabs.calendar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.viewModels.CalendarViewModel;
import com.ksdigtalnomad.koala.data.viewModels.ViewModelHelper;
import com.ksdigtalnomad.koala.databinding.FragmentTabCalendarBinding;

import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail.CalendarDayDetailActivity;


import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TabCalendarFragment extends BaseFragment {

    private FragmentTabCalendarBinding mBinding;

    private CalendarView calendarView;
//    private Observer<CalendarModel> calendarObserver;

    private Context mContext;

    public static BaseFragment newInstance(){ return new TabCalendarFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_calendar, container, false);
//        mBinding.setLifecycleOwner(this);

        addCalendar(mBinding.bodyLayout);

        return mBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        calendarView.post(()-> calendarView.notifyDataChanged(CalendarDataController.getCalendarModel()) );
    }


    private void addCalendar(ViewGroup parent){

        calendarView = new CalendarView(mContext, CalendarDataController.getCalendarModel(), dayModel -> {
            mContext.startActivity(CalendarDayDetailActivity.intent(mContext, dayModel));
        });

        parent.addView(calendarView, 0, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parent.setBackgroundColor(Color.LTGRAY);

    }
}
