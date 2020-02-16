package com.ksdigtalnomad.koala.ui.views.tabs.statistics;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.ksdigtalnomad.koala.databinding.FragmentTabStatisticsBinding;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;

import com.ksdigtalnomad.koala.ui.views.tabs.statistics.StatisticsTextChartRvAdapter.TextChartItem;
import com.ksdigtalnomad.koala.ui.views.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.HorizontalBarChartItem;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class TabStatisticsFragment extends BaseFragment {

    private FragmentTabStatisticsBinding mBinding;
    private Context mContext;

    private StatisticsTextChartRvAdapter textChartRvAdapter;
    private ArrayList<TextChartItem> textChartItemArrayList = new ArrayList<>();

    private StatisticsHorizontalBarChartRvAdapter horizontalBarChartRvAdapter;
    private ArrayList<HorizontalBarChartItem> horizontalBarChartItemArrayList= new ArrayList<>();

    private final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

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

        textChartRvAdapter = new StatisticsTextChartRvAdapter(getActivity(), textChartItemArrayList);
        mBinding.textChartRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBinding.textChartRv.setAdapter(textChartRvAdapter);

        horizontalBarChartRvAdapter = new StatisticsHorizontalBarChartRvAdapter(getActivity(), horizontalBarChartItemArrayList);
        mBinding.horizontalBarChartRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBinding.horizontalBarChartRv.setAdapter(horizontalBarChartRvAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        Executors.newSingleThreadExecutor().execute(()->{
            refreshTextChartRc();
            refreshBarChartRc();
        });
    }

    // 차트
    private void refreshTextChartRc(){
        if(CalendarDataController.isNoDataYet()){
            if(textChartItemArrayList.size() > 0) return;

            textChartItemArrayList.set(0, TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_drink_day))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.set(1, TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_quit))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.set(2, TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_drink))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.set(3, TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_total_expense))
                    .enoughData(false)
                    .build());
            // no data
            getActivity().runOnUiThread(()->textChartRvAdapter.notifyDataSetChanged());
        }else {
            // @TODO:
            // data cal
//            ArrayList<DayModel> noDrinkDayList = CalendarDataController.getNoDrinkDayList(DateHelper.getInstance().getTodayDate());
//            int noDrinkDayCount = noDrinkDayList.size();
//
//            if(noDrinkDayCount<2) {
//                getActivity().runOnUiThread(()->mBinding.noDrinkLayout.setVisibility(View.GONE));
//                return;
//            }
            textChartItemArrayList.clear();
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_drink_day))
                    .data("12일")
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_drink_day, "10"))
                    .enoughData(true)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_quit))
                    .data("12일")
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_longest_quit, "01.10 ~ 01.21")
                            + getResources().getString(R.string.fragment_tab_statistics_info_count_longest_quit_2, "12"))
                    .enoughData(true)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_drink))
                    .data("10일")
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_longest_drink, "01.01 ~ 01.09")
                            + getResources().getString(R.string.fragment_tab_statistics_info_count_longest_drink_2, "10"))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_total_expense))
                    .data(getResources().getString(R.string.calendar_expense_currency, "410,000"))
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_total_expense, "100,123"))
                    .enoughData(true)
                    .build());

            // show data
            getActivity().runOnUiThread(()->textChartRvAdapter.notifyDataSetChanged());
        }
    }
    private void refreshBarChartRc(){
        if(CalendarDataController.isNoDataYet()){
            // no data
            getActivity().runOnUiThread(()->{

            });
        }else {

            float barWidth = 8f;
            float spaceForBar = 10f;

            ArrayList<BarEntry> values = new ArrayList<>();
//            values.add(new BarEntry(1 * spaceForBar, 0.3f, Math.round((Math.random() * 10)) + "일"));
            values.add(new BarEntry(2 * spaceForBar, 0.4f, Math.round((Math.random() * 10)) + "일"));
            values.add(new BarEntry(3 * spaceForBar, 0.6f, Math.round((Math.random() * 10)) + "일"));
            values.add(new BarEntry(4 * spaceForBar, 0.8f, Math.round((Math.random() * 10)) + "일"));
            values.add(new BarEntry(5 * spaceForBar, 1.0f, Math.round((Math.random() * 10)) + "일"));

            BarDataSet barDataSet;
            barDataSet = new BarDataSet(values, "");
            barDataSet.setColors(
                    CalendarConstUtils.getDrunkLvColorMainByStep(0),
                    CalendarConstUtils.getDrunkLvColorMainByStep(1),
                    CalendarConstUtils.getDrunkLvColorMainByStep(2),
                    CalendarConstUtils.getDrunkLvColorMainByStep(3),
                    CalendarConstUtils.getDrunkLvColorMainByStep(4)
            );

            // 최댓값 최솟값을 위한 야매 변수..
            ArrayList<BarEntry> hiddenValues = new ArrayList<>();
            hiddenValues.add(new BarEntry(0, 0.0f, "")); // 최댓값
            hiddenValues.add(new BarEntry(0, 1.1f, "")); // 최솟값
            BarDataSet hiddenBarDataSet;
            hiddenBarDataSet = new BarDataSet(hiddenValues, "hidden");
            hiddenBarDataSet.setVisible(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);
            dataSets.add(hiddenBarDataSet);

            BarData barData = new BarData(dataSets);
            barData.setHighlightEnabled(false);
            barData.setValueTextSize(8);
            barData.setBarWidth(barWidth);
            barData.setValueFormatter(new ValueFormatter() {
                @Override
                public String getBarLabel(BarEntry barEntry) {
                    return "" + barEntry.getY() + "% " + barEntry.getData();
                }
            });

            ArrayList<String> xAxisLabelList = new ArrayList<>();
            xAxisLabelList.add("");
            xAxisLabelList.add("나름 취할 정도로");
            xAxisLabelList.add("그냥 가볍게");
            xAxisLabelList.add("필름 끊길 정도로");
            xAxisLabelList.add("많이 취할 정도로");


            horizontalBarChartItemArrayList.clear();
            horizontalBarChartItemArrayList.add(HorizontalBarChartItem.builder()
                    .title(getResources().getString(R.string.fragment_tab_statistics_title_frequency_drunk_level))
                    .xAxisLabelList(xAxisLabelList)
                    .barData(barData)
                    .build());
            horizontalBarChartItemArrayList.add(HorizontalBarChartItem.builder()
                    .title(getResources().getString(R.string.fragment_tab_statistics_title_frequency_drunk_level))
                    .xAxisLabelList(xAxisLabelList)
                    .barData(barData)
                    .build());
            horizontalBarChartItemArrayList.add(HorizontalBarChartItem.builder()
                    .title(getResources().getString(R.string.fragment_tab_statistics_title_frequency_drunk_level))
                    .xAxisLabelList(xAxisLabelList)
                    .barData(barData)
                    .build());
            horizontalBarChartItemArrayList.add(HorizontalBarChartItem.builder()
                    .title(getResources().getString(R.string.fragment_tab_statistics_title_frequency_drunk_level))
                    .xAxisLabelList(xAxisLabelList)
                    .barData(barData)
                    .build());

            getActivity().runOnUiThread(()->horizontalBarChartRvAdapter.notifyDataSetChanged());
        }
    }
}
