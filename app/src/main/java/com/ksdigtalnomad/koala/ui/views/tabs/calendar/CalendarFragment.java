package com.ksdigtalnomad.koala.ui.views.tabs.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.CalendarDayDetailActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class CalendarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CalendarView calendarView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    public CalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = new CalendarView(mContext, CalendarDataController.getCalendarModel(), new CalendarView.EventInterface() {
            @Override
            public void onDayViewTouch(DayModel dayModel) {
                Toast.makeText(mContext, dayModel.year + "." + dayModel.month + "." + dayModel.day, Toast.LENGTH_SHORT).show();

                mContext.startActivity(CalendarDayDetailActivity.intent(mContext, dayModel));
            }
        });



        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        LinearLayout bodyLayout = view.findViewById(R.id.bodyLayout);

        bodyLayout.addView(calendarView, 0, params);
        bodyLayout.setBackgroundColor(Color.LTGRAY);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        calendarView.notifyDataChanged(CalendarDataController.getCalendarModel());
    }

    private void showCalendar(){


    }
}
