package com.ksdigtalnomad.koala.ui.views.tabs.settings;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.CalendarDayDetailActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SettingsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CalendarView calendarView;


//    private String mParam1;
//    private String mParam2;

    private Context mContext;

    public static BaseFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

//    public static BaseFragment newInstance(String param1, String param2) {
//        BaseFragment fragment = newInstance();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_settings, container, false);
//
//        return mBinding.getRoot();

        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        calendarView.notifyDataChanged(CalendarDataController.getCalendarModel());
    }
}
