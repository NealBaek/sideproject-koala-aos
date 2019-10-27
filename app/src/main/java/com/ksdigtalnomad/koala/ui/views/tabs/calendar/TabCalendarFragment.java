package com.ksdigtalnomad.koala.ui.views.tabs.calendar;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentTabCalendarBinding;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayView;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail.CalendarDayDetailActivity;


import static android.app.Activity.RESULT_OK;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail.CalendarDayDetailActivity.KEY_DAY_MODEL;

public class TabCalendarFragment extends BaseFragment {

    private FragmentTabCalendarBinding mBinding;

    private CalendarView calendarView;

    private Context mContext;

    public static TabCalendarFragment newInstance(){ return new TabCalendarFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_calendar, container, false);

        addCalendar(mBinding.bodyLayout);

        return mBinding.getRoot();
    }

    private void addCalendar(ViewGroup parent){

        calendarView = new CalendarView(
                mContext,
                CalendarDataController.getCalendarModel(),
                dayModel -> TabCalendarFragment.this.startActivityForResult((CalendarDayDetailActivity.intent(mContext, dayModel)), 0)
        );

        parent.addView(calendarView, 0, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parent.setBackgroundColor(Color.LTGRAY);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            DayModel dayModel = new Gson().fromJson(data.getStringExtra(KEY_DAY_MODEL), DayModel.class);


            // 전월
            if(dayModel.day < 14){
                DayView pDayView = this.calendarView.findViewById(dayModel.dayViewId - (CalendarConstUtils.ID_CNT_MONTH - CalendarConstUtils.ID_CNT_ISOUTMONTH));
                if(pDayView != null){
                    DayModel pDayModel = pDayView.getDayModel();
                    pDayModel.drunkLevel = dayModel.drunkLevel;
                    pDayModel.friendList.clear();
                    pDayModel.friendList.addAll(dayModel.friendList);
                    pDayModel.foodList.clear();
                    pDayModel.foodList.addAll(dayModel.foodList);
                    pDayModel.drinkList.clear();
                    pDayModel.drinkList.addAll(dayModel.drinkList);
                    pDayModel.memo = dayModel.memo;
                    pDayModel.isSaved = dayModel.isSaved;
                    pDayView.setDayModel(pDayModel);
                    pDayView.invalidate();
                }
            }


            // 금월
            DayView cDayView = this.calendarView.findViewById(dayModel.dayViewId);
            cDayView.setDayModel(dayModel);
            cDayView.invalidate();

            // 익월
            if(dayModel.day > 20){
                DayView nDayView = this.calendarView.findViewById(dayModel.dayViewId + (CalendarConstUtils.ID_CNT_MONTH + CalendarConstUtils.ID_CNT_ISOUTMONTH));
                if(nDayView != null){
                    DayModel nDayModel = nDayView.getDayModel();
                    nDayModel.drunkLevel = dayModel.drunkLevel;
                    nDayModel.friendList.clear();
                    nDayModel.friendList.addAll(dayModel.friendList);
                    nDayModel.foodList.clear();
                    nDayModel.foodList.addAll(dayModel.foodList);
                    nDayModel.drinkList.clear();
                    nDayModel.drinkList.addAll(dayModel.drinkList);
                    nDayModel.memo = dayModel.memo;
                    nDayModel.isSaved = dayModel.isSaved;
                    nDayView.setDayModel(nDayModel);
                    nDayView.invalidate();
                }

            }

        }
    }
}
