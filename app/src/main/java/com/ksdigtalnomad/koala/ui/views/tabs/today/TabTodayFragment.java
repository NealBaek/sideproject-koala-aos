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

    private static final String PREFIX_1 = "최근 7일간 음주 ";
    private static final String PREFIX_2 = "최근 7일간 음주량 평균 ";

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

        if((recent7Days.size() <= 0)){
            showEmptyLayout(true);
            return;
        }


        double avgDrinkLevel = 0;
        int drinkCnt = 0;
        int drinkLevelCnt = 0;

        List<DayModel> recentSavedList = new ArrayList<>();

        for(DayModel item: recent7Days){
            if(item.isSaved){
                recentSavedList.add(item);
                avgDrinkLevel += item.drunkLevel * 25/100; // 0 ~ 4
                drinkCnt += item.drunkLevel > 0 ? 1 : 0;
                drinkLevelCnt += item.drunkLevel * 25;
            }
        }


        if(recentSavedList.size() <= 0){
            showEmptyLayout(true);
            return;
        }



//        음주량별 가중치를 음주횟수에 곱해서 계산하여 나온 지표를 근거로 상태를 표시
//        Ex) 7일간 3일은 2단계, 4일은 4단계면 3*0.25 + 4*0.75
//
//        0.00 ~ 1.00 : "안전"
//        1.01 ~ 2.00 : "양호"
//        2.01 ~ 3.00 : "자제"
//        3.01 ~ 4.00 : "위험"
//        4.01 ~ 5.00 : "금지"

        String drinkState = "";


        if(avgDrinkLevel > 0 && avgDrinkLevel <= 1){
            drinkState = "안전";
        }else if(avgDrinkLevel > 1 && avgDrinkLevel <= 2 ){
            drinkState = "양호";
        }else if(avgDrinkLevel > 2 && avgDrinkLevel <= 3 ) {
            drinkState = "자제";
        }else if(avgDrinkLevel > 3 && avgDrinkLevel <= 4 ) {
            drinkState = "위험";
        }else if(avgDrinkLevel > 4 && avgDrinkLevel <= 5 ) {
            drinkState = "금지";
        }


        mBinding.avgDrinkState.setText(drinkState);
        mBinding.avgDrinkTimes.setText(PREFIX_1 + drinkCnt + "회");
        mBinding.avgDrinkLevel.setText(PREFIX_2 + drinkLevelCnt/drinkCnt + "%");

        showEmptyLayout(false);
    }

    private void showEmptyLayout(boolean shouldShow){
//        "데이터 부족"
//        최근 7일간 하루라도 데이터가 없으면 표시
//        누르면 월간 달력화면 이동 버튼으로 변신(밑줄이 쳐진 안내설명  문구)
        mBinding.stateLayout.setVisibility(shouldShow ? View.INVISIBLE : View.VISIBLE);
        mBinding.emptyLayout.setVisibility(shouldShow ? View.VISIBLE : View.INVISIBLE);
    }
}
