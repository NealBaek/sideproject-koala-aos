package com.ksdigtalnomad.koala.ui.views.tabs.today;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentTabTodayBinding;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabTodayFragment extends BaseFragment {

    private FragmentTabTodayBinding mBinding;

    private Context mContext;

    public static TabTodayFragment newInstance(){
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

        mBinding.headerText.setText(DateHelper.getInstance().getTodayStr("yyyy.MM.dd."));

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        Runnable task = ()->checkRecent7Days();
        Runnable task1 = ()->calNoDrinkDays();


        task.run();
        task1.run();
    }


    // NoDrinkCounts
    private void calNoDrinkDays(){
        if(CalendarDataController.isNoDataYet()){
            mBinding.noDrinkLayout.setVisibility(View.VISIBLE);
            mBinding.noDrinkInfo2.setText(getResources().getString(R.string.tap_today_no_drink_info_2, String.valueOf(0)));
            mBinding.noDrinkInfo3.setVisibility(View.GONE);
        }else{
            ArrayList<DayModel> noDrinkDayList = CalendarDataController.getNoDrinkDayList(DateHelper.getInstance().getTodayDate());
            int noDrinkDayCount = noDrinkDayList.size();

            mBinding.noDrinkLayout.setVisibility(View.VISIBLE);
            mBinding.noDrinkInfo2.setText(getResources().getString(R.string.tap_today_no_drink_info_2, String.valueOf(noDrinkDayCount)));
            mBinding.noDrinkInfo3.setVisibility(noDrinkDayCount>0 ? View.VISIBLE : View.GONE );
            if(noDrinkDayCount>0) {
                mBinding.noDrinkInfo3.setText(getResources().getString(R.string.tap_today_no_drink_info_3, DateHelper.getInstance().getDateStr("yyyy.MM.dd.", noDrinkDayList.get(noDrinkDayCount - 1).date)));
            }
        }
    }




    // RecentDrinks
    private void checkRecent7Days(){
        if(CalendarDataController.isNoDataYet()) return;

        int fromDayIdx = 0;
        int toDayIdx = 0;
        ArrayList<DayModel> totalDayList = CalendarDataController.getTotalDayList();


        // 1. 오늘 모델 값 구하기
        int dayListCnt =  totalDayList.size();
        for(int i = 0; i < dayListCnt; ++i){
            DayModel day = totalDayList.get(i);
            if (DateHelper.getInstance().isToday(day.date)){
                fromDayIdx = (i - 7 <= 0 ? 0 : i - 7);
                toDayIdx = i;
                break;
            }
        }


        // 2. 최근 7일
        List<DayModel> recent7Days = totalDayList.subList(fromDayIdx, toDayIdx);

        if((recent7Days.size() <= 0)){
            showEmptyLayout(true);
            return;
        }

        // 3. 최근 7일 중 저장된 데이터 리스트
        double avgDrinkLevel = 0;
        int drinkCnt = 0;

        List<DayModel> recentSavedList = new ArrayList<>();

        for(DayModel item: recent7Days){
            if(item.isSaved){
                recentSavedList.add(item);
                avgDrinkLevel += item.drunkLevel * 25; // 0 ~ 4
                drinkCnt += item.drunkLevel > 0 ? 1 : 0;
            }else{
                showEmptyLayout(true);
                return;
            }
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

        if(avgDrinkLevel >= 0 && avgDrinkLevel <= 100){
            drinkState = getResources().getString(R.string.drink_state_nowadays_0);
        }else if(avgDrinkLevel > 100 && avgDrinkLevel <= 200 ){
            drinkState = getResources().getString(R.string.drink_state_nowadays_1);
        }else if(avgDrinkLevel > 200 && avgDrinkLevel <= 300 ) {
            drinkState = getResources().getString(R.string.drink_state_nowadays_2);
        }else if(avgDrinkLevel > 300 && avgDrinkLevel <= 400 ) {
            drinkState = getResources().getString(R.string.drink_state_nowadays_3);
        }else if(avgDrinkLevel > 400 && avgDrinkLevel <= 500 ) {
            drinkState = getResources().getString(R.string.drink_state_nowadays_4);
        }

        mBinding.avgDrinkState.setText(drinkState);
        mBinding.avgDrinkTimes.setText(getResources().getString(R.string.tap_today_drink_times, String.valueOf(drinkCnt)));
        mBinding.avgDrinkLevel.setText(getResources().getString(R.string.tap_today_drink_level, String.valueOf(Math.round(avgDrinkLevel/drinkCnt)) + "%"));

        showEmptyLayout(false);
    }
    private void showEmptyLayout(boolean shouldShow){
//        "데이터 부족"
//        최근 7일간 하루라도 데이터가 없으면 표시
//        누르면 월간 달력화면 이동 버튼으로 변신(밑줄이 쳐진 안내설명  문구)
        mBinding.stateLayout.setVisibility(shouldShow ? View.GONE : View.VISIBLE);
        mBinding.emptyLayout.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }
}
