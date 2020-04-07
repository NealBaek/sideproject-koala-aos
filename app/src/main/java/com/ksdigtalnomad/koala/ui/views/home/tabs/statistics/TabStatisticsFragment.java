package com.ksdigtalnomad.koala.ui.views.home.tabs.statistics;

import android.databinding.DataBindingUtil;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.ksdigtalnomad.koala.data.models.calendar.Drink;
import com.ksdigtalnomad.koala.data.models.calendar.Food;
import com.ksdigtalnomad.koala.data.models.calendar.Friend;
import com.ksdigtalnomad.koala.databinding.FragmentTabStatisticsBinding;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.data.HashMapHelper;
import com.ksdigtalnomad.koala.helpers.data.ParseHelper;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;

import com.ksdigtalnomad.koala.ui.customView.calendarView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.month.MonthModel;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;
import com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsTextChartRvAdapter.TextChartItem;
import com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.HorizontalBarChartItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

import static com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.LABEL_CNT;
import static com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.MAX_DATA_CNT;
import static com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.BAR_WIDTH;
import static com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.COLOR_ARRAY_MAIN;
import static com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.StatisticsHorizontalBarChartRvAdapter.BAR_SPACE;

public class TabStatisticsFragment extends BaseFragment {

    private FragmentTabStatisticsBinding mBinding;

    private static final int DEFAULT_MONTH_IDX = -1;

    private int thisYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(DateHelper.getInstance().getTodayDate()));
    private int thisMonth = Integer.parseInt(new SimpleDateFormat("MM").format(DateHelper.getInstance().getTodayDate()));
    private int thisMonthIdx;
    private int targetMonthIdx = DEFAULT_MONTH_IDX;
    private MonthModel targetMonthModel;
    private CalendarModel calendarModel;
    private int cDrinkDayCnt;
    private int cQuitDayCnt;
    private int totalExpense;

    private StatisticsTextChartRvAdapter textChartRvAdapter;
    private ArrayList<TextChartItem> textChartItemArrayList = new ArrayList<>();

    private StatisticsHorizontalBarChartRvAdapter horizontalBarChartRvAdapter;
    private ArrayList<HorizontalBarChartItem> horizontalBarChartItemArrayList= new ArrayList<>();

    public static TabStatisticsFragment newInstance(){ return new TabStatisticsFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_statistics, container, false);
        mBinding.setFragment(this);

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

            calendarModel = CalendarDataController.getCalendarModel();

            if(targetMonthIdx == DEFAULT_MONTH_IDX){
                int monthListCnt =  calendarModel.monthList.size();
                for(int i = 0; i < monthListCnt; ++i){
                    MonthModel item = calendarModel.monthList.get(i);
                    boolean isThisYear  = (thisYear == item.year);
                    boolean isThisMonth = (thisMonth == item.month);
                    if (isThisYear && isThisMonth) {
                        targetMonthIdx = i;
                        thisMonthIdx = i;
                    }
                }
            }

            targetMonthModel = calendarModel.monthList.get(targetMonthIdx);
            setMonthTitle(targetMonthModel);
            refreshTextChartRc(targetMonthModel.clone());
            refreshBarChartRc(targetMonthModel.clone());
        });
    }

    // 차트
    private synchronized void refreshTextChartRc(MonthModel monthModel){
        if(CalendarDataController.isNoDataYet()){
            if(textChartItemArrayList.size() > 0) return;

            textChartItemArrayList.clear();
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_drink_day))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_quit))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_drink))
                    .enoughData(false)
                    .build());
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_total_expense))
                    .enoughData(false)
                    .build());
            // no data
            getActivity().runOnUiThread(()->textChartRvAdapter.notifyDataSetChanged());
        }else {
            // data cal
            int savedDayCnt = 0;
            int dayOfMonthCnt = 0;
            int drinkDayCnt = 0;
            final int CONTINUE_ATLEAST = 2;
            final int CONTINUE_SAVE_ATLEAST = 7;


            ArrayList<DayModel> cDrinkTemps = new ArrayList<>();
            ArrayList<DayModel> cDrinkDayList= new ArrayList<>();
            ArrayList<DayModel> cQuitTemps = new ArrayList<>();
            ArrayList<DayModel> cQuitDayList = new ArrayList<>();

            cDrinkDayCnt = 0;
            cQuitDayCnt = 0;
            totalExpense = 0;

            int dayCnt = monthModel.dayList.size();
            for(int i = 0; i <dayCnt; ++ i){
                DayModel item = monthModel.dayList.get(i);

                if(item.isOutMonth) continue; // 해당 월이 아닌 경우
                else dayOfMonthCnt += 1; // 해당 월인 경우

                if(!item.isSaved) continue; // 미저장
                else savedDayCnt += 1; // 저장

                if(item.drunkLevel > 0){ // 음주
                    drinkDayCnt += 1;

                    /** cQuit */
                    if(cQuitDayCnt >= CONTINUE_ATLEAST &&
                       cQuitDayCnt >= cQuitDayList.size()){
                        cQuitDayList.clear();
                        cQuitDayList.addAll((ArrayList)cQuitTemps.clone());
                    }
                    cQuitTemps.clear();
                    cQuitDayCnt = 0;


                    /* cDrink */
                    cDrinkDayCnt += 1;
                    cDrinkTemps.add(item);

                }else{ // 금주

                    /** cQuit */
                    cQuitDayCnt += 1;
                    cQuitTemps.add(item);

                    /* cDrink */
                    if(cDrinkDayCnt >= CONTINUE_ATLEAST &&
                       cDrinkDayCnt >= cDrinkDayList.size()){
                        cDrinkDayList.clear();
                        cDrinkDayList.addAll((ArrayList)cDrinkTemps.clone());
                    }
                    cDrinkDayCnt = 0;
                    cDrinkTemps.clear();
                }
                totalExpense += item.expense;
            }

            /* cQuit */
            if(cQuitDayCnt >= CONTINUE_ATLEAST &&
               cQuitDayCnt >= cQuitDayList.size()){
                cQuitDayList.clear();
                cQuitDayList.addAll((ArrayList)cQuitTemps.clone());
            }

            /* cDrink */
            if(cDrinkDayCnt >= CONTINUE_ATLEAST &&
                    cDrinkDayCnt >= cDrinkDayList.size()){
                cDrinkDayList.clear();
                cDrinkDayList.addAll((ArrayList)cDrinkTemps.clone());
            }


            textChartItemArrayList.clear();

            // dayDrinkCnt
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_drink_day))
                    .data(getResources().getString(R.string.calendar_day_date, String.valueOf(drinkDayCnt)))
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_drink_day, String.valueOf(drinkDayCnt == 0 ? dayOfMonthCnt : dayOfMonthCnt/drinkDayCnt)))
                    .enoughData(savedDayCnt >= 1)
                    .build());

            // cQuit
            int cQuitCnt = cQuitDayList.size();
            boolean showCQuit = savedDayCnt >= CONTINUE_SAVE_ATLEAST && (cQuitCnt >= CONTINUE_ATLEAST);
            String cQuitDateStr = "";
            if(showCQuit){
                DayModel firstDay = cQuitDayList.get(0);
                DayModel lastDay = cQuitDayList.get(cQuitCnt - 1);
                cQuitDateStr = getMMddStr(firstDay.month, firstDay.day) + "~" + getMMddStr(lastDay.month, lastDay.day);
            }
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_quit))
                    .data(getResources().getString(R.string.calendar_day_date, String.valueOf(cQuitCnt)))
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_longest_quit, cQuitDateStr))
//                            + getResources().getString(R.string.fragment_tab_statistics_info_count_longest_quit_2, "12"))
                    .enoughData(showCQuit)
                    .build());


            // cDrink
            int cDrinkCnt = cDrinkDayList.size();
            boolean showCDrink = savedDayCnt >= CONTINUE_SAVE_ATLEAST && (cDrinkCnt >= CONTINUE_ATLEAST);
            String cDrinkDateStr = "";
            if(showCDrink){
                DayModel firstDay = cDrinkDayList.get(0);
                DayModel lastDay = cDrinkDayList.get(cDrinkCnt - 1);
                cDrinkDateStr = getMMddStr(firstDay.month, firstDay.day) + "~" + getMMddStr(lastDay.month, lastDay.day);
            }
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_count_longest_drink))
                    .data(getResources().getString(R.string.calendar_day_date, String.valueOf(cDrinkCnt)))
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_count_longest_drink, cDrinkDateStr))
//                            + getResources().getString(R.string.fragment_tab_statistics_info_count_longest_drink_2, "10"))
                    .enoughData(showCDrink)
                    .build());

            // totalExpense
            textChartItemArrayList.add(TextChartItem.builder().
                    title(getResources().getString(R.string.fragment_tab_statistics_title_total_expense))
                    .data(ParseHelper.parseMoney(totalExpense))
                    .info(getResources().getString(R.string.fragment_tab_statistics_info_total_expense, ParseHelper.parseMoney(totalExpense == 0 ? 0 : totalExpense/dayOfMonthCnt)))
                    .enoughData(savedDayCnt >= 1)
                    .build());

            // show data
            getActivity().runOnUiThread(()->textChartRvAdapter.notifyDataSetChanged());
        }
    }
    private synchronized void refreshBarChartRc(MonthModel monthModel){
        if(CalendarDataController.isNoDataYet()){
            // no data
            getActivity().runOnUiThread(()->{

            });
        }else {

            int dayCnt = monthModel.dayList.size();
            int drunkLevelTotalCnt = 0;
            int drinkTotalCnt = 0;
            int friendTotalCnt = 0;
            int foodTotalCnt = 0;

            HashMap<String, Integer> drunkLevelMap = new HashMap<String, Integer>();
            HashMap<String, Integer> drinkMap = new HashMap<>();
            HashMap<String, Integer> friendMap = new HashMap<>();
            HashMap<String, Integer> foodMap = new HashMap<>();

            for(int i = 0; i <dayCnt; ++ i){
                DayModel item = monthModel.dayList.get(i);

                if(item.isOutMonth) continue; // 해당 월이 아닌 경우
                if(!item.isSaved) continue; // 미저장

                // 음주 정도
                if(item.drunkLevel > 0){
                    drunkLevelTotalCnt += 1;
                    String key = CalendarConstUtils.getDrunkLvCommentShort(Integer.valueOf(item.drunkLevel));
                    if (drunkLevelMap.containsKey(key)){
                        drunkLevelMap.put(key, drunkLevelMap.get(key) + 1);
                    }else{
                        drunkLevelMap.put(key, 1);
                    }
                }

                // 술
                ArrayList<Drink> drinkList = item.drinkList;
                if(drinkList != null && drinkList.size() > 0){
                    int cnt = drinkList.size();
                    for(int k = 0; k < cnt; ++k){
                        drinkTotalCnt += 1;
                        Drink drink = drinkList.get(k);
                        String key = String.valueOf(drink.getName());
                        if (drinkMap.containsKey(key)){
                            drinkMap.put(key, drinkMap.get(key) + 1);
                        }else{
                            drinkMap.put(key, 1);
                        }
                    }
                }

                // 음식
                ArrayList<Food> foodList = item.foodList;
                if(foodList != null && foodList.size() > 0){
                    int cnt = foodList.size();
                    for(int k = 0; k < cnt; ++k){
                        foodTotalCnt += 1;
                        Food food = foodList.get(k);
                        String key = String.valueOf(food.getName());
                        if (foodMap.containsKey(key)){
                            foodMap.put(key, foodMap.get(key) + 1);
                        }else{
                            foodMap.put(key, 1);
                        }
                    }
                }

                // 친구
                ArrayList<Friend> friendList = item.friendList;
                if(friendList != null && friendList.size() > 0){
                    int cnt = friendList.size();
                    for(int k = 0; k < cnt; ++k){
                        friendTotalCnt += 1;
                        Friend friend = friendList.get(k);
                        String key = String.valueOf(friend.getName());
                        if (friendMap.containsKey(key)){
                            friendMap.put(key, friendMap.get(key) + 1);
                        }else{
                            friendMap.put(key, 1);
                        }
                    }
                }

            }


            // 정렬
            ArrayList<HashMap<String, Integer>> drunkLevelMapList = HashMapHelper.sortToArrayList(drunkLevelMap);
            ArrayList<HashMap<String, Integer>> drinkMapList = HashMapHelper.sortToArrayList(drinkMap);
            ArrayList<HashMap<String, Integer>> foodMapList = HashMapHelper.sortToArrayList(foodMap);
            ArrayList<HashMap<String, Integer>> friendMapList = HashMapHelper.sortToArrayList(friendMap);


            horizontalBarChartItemArrayList.clear();
            horizontalBarChartItemArrayList.add( // drunkLevel (내 음주 정도)
                    createBarItem(
                            getResources().getString(R.string.fragment_tab_statistics_title_frequency_drunk_level),
                            drunkLevelTotalCnt,
                            drunkLevelMap,
                            drunkLevelMapList
                            ));
            horizontalBarChartItemArrayList.add( // drink (내가 자주 마신 술)
                    createBarItem(
                            getResources().getString(R.string.fragment_tab_statistics_title_frequency_drink),
                            drinkTotalCnt,
                            drinkMap,
                            drinkMapList
                    ));
            horizontalBarChartItemArrayList.add( // food (내가 자주 먹은 안주)
                    createBarItem(
                            getResources().getString(R.string.fragment_tab_statistics_title_frequency_food),
                            foodTotalCnt,
                            foodMap,
                            foodMapList
                    ));
            horizontalBarChartItemArrayList.add( // friend (나와 같이 마신 사람)
                    createBarItem(
                            getResources().getString(R.string.fragment_tab_statistics_title_frequency_friend),
                            friendTotalCnt,
                            friendMap,
                            friendMapList
                    ));

            getActivity().runOnUiThread(()->horizontalBarChartRvAdapter.notifyDataSetChanged());
        }
    }
    private HorizontalBarChartItem createBarItem(String title, int totalCnt, HashMap<String, Integer> hashMap, ArrayList<HashMap<String, Integer>> hashMapList){

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> xAxisLabelList = new ArrayList<>();

        int hashMapListCnt = hashMapList.size();

        for(int i = 0; i < MAX_DATA_CNT; ++i){
            float perc = 0;
            String dataStr = "";
            String label = "-";

            if(i < hashMapListCnt){
                HashMap<String, Integer> item = hashMapList.get(i);
                for( String key: item.keySet() ){
                    int keyValue = hashMap.get(key);
                    perc = keyValue;
                    perc = (perc == 0) ? 0 : ((totalCnt == 0) ? 0.0f : perc/(float)totalCnt);
                    dataStr = ((int)(perc*100)) + "%(" + getResources().getString(R.string.calendar_day_date, String.valueOf(keyValue)) + ")";
                    label = key;

                }
            }

            values.add(new BarEntry((LABEL_CNT - i) * BAR_SPACE, perc /** 0~1 */, dataStr));
            xAxisLabelList.add(label);

        }

        // @important!!
        // 6번째 empty bar 를 만들기 위한것. axisvalue label들 중 가장 마지막 텍스트의 길이를 기반해서 x를 위치로 잡는다.. offsetLeft 건드려봤자 소용없음.
        values.add(new BarEntry(BAR_SPACE, 0 /** 0~1 */, ""));
        xAxisLabelList.add("");

        BarDataSet barDataSet;
        barDataSet = new BarDataSet(values, "");
        barDataSet.setColors(COLOR_ARRAY_MAIN);

        ArrayList<IBarDataSet> barDataSetList = new ArrayList<>();
        barDataSetList.add(barDataSet);

        BarData barData = new BarData(barDataSetList);
        barData.setHighlightEnabled(false);
        barData.setValueTextSize(BAR_WIDTH);
        barData.setBarWidth(BAR_WIDTH);
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return String.valueOf(barEntry.getData());
            }
        });

        return HorizontalBarChartItem.builder()
                .title(title)
                .xAxisLabelList(xAxisLabelList)
                .barData(barData)
                .build();
    }



    // 기타 함수
    private void setMonthTitle(MonthModel month){ mBinding.headerText.post(()->mBinding.headerText.setText(month.year + "." + (month.month < 10 ? "0" + month.month : month.month) + ".")); }
    private String getMMddStr(int mm, int dd){ return (mm < 10 ? "0" + mm : mm) + "." + (dd < 10 ? "0" + dd : dd) + "."; }

    // click events
    public void onBtnPreviousMonthClick(){
        if(targetMonthIdx <= 0) return;

        FBEventLogHelper.onStatisticsMonthMoved(
                mBinding.headerText.getText().toString(),
                cQuitDayCnt, cDrinkDayCnt, totalExpense);

        targetMonthIdx -= 1;
        getActivity().runOnUiThread(()->{
            mBinding.btnPreviousMonth.setVisibility(targetMonthIdx > 0 ? View.VISIBLE : View.GONE);
            mBinding.btnNextMonth.setVisibility(targetMonthIdx < thisMonthIdx? View.VISIBLE : View.GONE);
        });
        refreshData();
    }
    public void onBtnNextMonthClick(){
        if(calendarModel == null || calendarModel.monthList == null) return;
        if(targetMonthIdx >= thisMonthIdx) return;
        targetMonthIdx += 1;
        getActivity().runOnUiThread(()->{
            mBinding.btnPreviousMonth.setVisibility(targetMonthIdx > 0 ? View.VISIBLE : View.GONE);
            mBinding.btnNextMonth.setVisibility(targetMonthIdx < thisMonthIdx ? View.VISIBLE : View.GONE);
        });
        refreshData();
    }
}
