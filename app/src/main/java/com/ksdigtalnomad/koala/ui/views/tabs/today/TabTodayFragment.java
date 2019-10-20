package com.ksdigtalnomad.koala.ui.views.tabs.today;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentTabTodayBinding;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TabTodayFragment extends BaseFragment {

    private FragmentTabTodayBinding mBinding;

    private Context mContext;

    public static BaseFragment newInstance(){
        TabTodayFragment fragment = new TabTodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_today, container, false);

        mBinding.headerText.setText(new SimpleDateFormat("yyyy.MM.dd.").format(new Date()));

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Runnable task = ()->setData();
//        task.run();
    }

    private void setData(){
        Date today = new Date();

        int fromDayIdx = 0;
        int toDayIdx = 0;
        ArrayList<DayModel> totalDayList = CalendarDataController.getTotalDayList();

        // 1. 오늘 날짜 계산
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dfDate = new SimpleDateFormat("dd");

        int thisYear = Integer.parseInt(dfYear.format(today));
        int thisMonth = Integer.parseInt(dfMonth.format(today));
        int thisDate = Integer.parseInt(dfDate.format(today));


        // 2. 오늘 모델 값 구하기
        int dayListCnt =  totalDayList.size();
        for(int i = 0; i < dayListCnt; ++i){
            DayModel day = totalDayList.get(i);
            boolean isThisYear  = (thisYear == day.year);
            boolean isThisMonth = (thisMonth == day.month);
            boolean isThisDate = (thisDate == day.day);

            if (isThisYear && isThisMonth && isThisDate){
                fromDayIdx = (i - 7 <= 0 ? 0 : i - 7);
                toDayIdx = i;
                break;
            }
        }

        // 3. 최근 7일
        List<DayModel> recent7Days = totalDayList.subList(fromDayIdx, toDayIdx);

        if(recent7Days.size() <= 0){
            // 데이터 부족
        }




//        음주량별 가중치를 음주횟수에 곱해서 계산하여 나온 지표를 근거로 상태를 표시
//        Ex) 7일간 3일은 2단계, 4일은 4단계면 3*0.25 + 4*0.75
//
//        0.00 ~ 1.00 : "안전"
//        1.01 ~ 2.00 : "양호"
//        2.01 ~ 3.00 : "자제"
//        3.01 ~ 4.00 : "위험"
//        4.01 ~ 5.00 : "금지"
//
//
//        "데이터 부족"
//        최근 7일간 하루라도 데이터가 없으면 표시
//        누르면 월간 달력화면 이동 버튼으로 변신(밑줄이 쳐진 안내설명  문구)

//        mBinding.avgDrinkState.setText();
//        mBinding.avgDrinkTimes.setText();
//        mBinding.avgDrinkLevel.setText();
    }
}
