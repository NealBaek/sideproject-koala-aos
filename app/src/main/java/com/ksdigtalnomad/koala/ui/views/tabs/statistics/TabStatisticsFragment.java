package com.ksdigtalnomad.koala.ui.views.tabs.statistics;

import android.content.Context;
import android.databinding.DataBindingUtil;
import com.ksdigtalnomad.koala.databinding.FragmentTabStatisticsBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;

import java.util.concurrent.Executors;

public class TabStatisticsFragment extends BaseFragment {

    private FragmentTabStatisticsBinding mBinding;
    private Context mContext;

    public static TabStatisticsFragment newInstance(){ return new TabStatisticsFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_statistics, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        Executors.newSingleThreadExecutor().execute(()->{
            if(PreferenceHelper.isLogin()){
                // show chart
                refreshTextChartRc();
                refreshBarChartRc();
            }else{
                // show Login
                // @TODO:
            }

        });
    }

    // 텍스트 차트
    private void refreshTextChartRc(){
        if(CalendarDataController.isNoDataYet()){
            // no data
            getActivity().runOnUiThread(()->{

            });
        }else {
            // data cal
//            ArrayList<DayModel> noDrinkDayList = CalendarDataController.getNoDrinkDayList(DateHelper.getInstance().getTodayDate());
//            int noDrinkDayCount = noDrinkDayList.size();
//
//            if(noDrinkDayCount<2) {
//                getActivity().runOnUiThread(()->mBinding.noDrinkLayout.setVisibility(View.GONE));
//                return;
//            }
//
//            getActivity().runOnUiThread(()->{
//                mBinding.noDrinkLayout.setVisibility(View.VISIBLE);
//                mBinding.noDrinkInfo2.setText(getResources().getString(R.string.tap_today_no_drink_info_2, String.valueOf(noDrinkDayCount)));
//                mBinding.noDrinkInfo3.setText(getResources().getString(R.string.tap_today_no_drink_info_3, DateHelper.getInstance().getDateStr("yyyy.MM.dd.", noDrinkDayList.get(noDrinkDayCount - 1).getDate())));
//            });
            // show data
            getActivity().runOnUiThread(()->{

            });
        }
    }

    // 바 차트
    private void refreshBarChartRc(){
        if(CalendarDataController.isNoDataYet()){
            // no data
            getActivity().runOnUiThread(()->{

            });
        }else {
            // data cal
//            ArrayList<DayModel> noDrinkDayList = CalendarDataController.getNoDrinkDayList(DateHelper.getInstance().getTodayDate());
//            int noDrinkDayCount = noDrinkDayList.size();
//
//            if(noDrinkDayCount<2) {
//                getActivity().runOnUiThread(()->mBinding.noDrinkLayout.setVisibility(View.GONE));
//                return;
//            }
//
//            getActivity().runOnUiThread(()->{
//                mBinding.noDrinkLayout.setVisibility(View.VISIBLE);
//                mBinding.noDrinkInfo2.setText(getResources().getString(R.string.tap_today_no_drink_info_2, String.valueOf(noDrinkDayCount)));
//                mBinding.noDrinkInfo3.setText(getResources().getString(R.string.tap_today_no_drink_info_3, DateHelper.getInstance().getDateStr("yyyy.MM.dd.", noDrinkDayList.get(noDrinkDayCount - 1).getDate())));
//            });


            // show data
            getActivity().runOnUiThread(()->{

            });
        }
    }

    private void a(){
        // chart.setHighlightEnabled(false);

        mBinding.testChart.setDrawBarShadow(false);

        mBinding.testChart.setDrawValueAboveBar(true);

        mBinding.testChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBinding.testChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBinding.testChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);

//        chart.setDrawGridBackground(false);
//
//        XAxis xl = chart.getXAxis();
//        xl.setPosition(XAxisPosition.BOTTOM);
//        xl.setTypeface(tfLight);
//        xl.setDrawAxisLine(true);
//        xl.setDrawGridLines(false);
//        xl.setGranularity(10f);
//
//        YAxis yl = chart.getAxisLeft();
//        yl.setTypeface(tfLight);
//        yl.setDrawAxisLine(true);
//        yl.setDrawGridLines(true);
//        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
////        yl.setInverted(true);
//
//        YAxis yr = chart.getAxisRight();
//        yr.setTypeface(tfLight);
//        yr.setDrawAxisLine(true);
//        yr.setDrawGridLines(false);
//        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
////        yr.setInverted(true);
//
//        chart.setFitBars(true);
//        chart.animateY(2500);
//
//        // setting data
//        seekBarY.setProgress(50);
//        seekBarX.setProgress(12);
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setFormSize(8f);
//        l.setXEntrySpace(4f);
    }
}
