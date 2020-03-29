package com.ksdigtalnomad.koala.ui.views.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.TabCalendarFragment;
import com.ksdigtalnomad.koala.ui.views.home.tabs.settings.TabSettingsFragment;
import com.ksdigtalnomad.koala.ui.views.home.tabs.statistics.TabStatisticsFragment;
import com.ksdigtalnomad.koala.ui.views.home.tabs.today.TabTodayFragment;

public class HomeTapAdapter extends FragmentStatePagerAdapter {

    public static final int FRAGMENT_CNT = 4;
    public static final int POS_CALENDAR = 0;
    public static final int POS_TODAY = 1;
    public static final int POS_STATISTICS = 2;
    public static final int POS_SETTINGS = 3;


    private TabCalendarFragment tabCalendarFragment;
    private TabTodayFragment tabTodayFragment;
    private TabStatisticsFragment tabStatisticsFragment;
    private TabSettingsFragment tabSettingsFragment;



    public HomeTapAdapter(FragmentManager fm ){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                if(tabCalendarFragment == null){  tabCalendarFragment = TabCalendarFragment.newInstance(); }
                return tabCalendarFragment;
            case 1:
                if(tabTodayFragment == null){  tabTodayFragment = TabTodayFragment.newInstance(); }
                return tabTodayFragment;
            case 2:
                if(tabStatisticsFragment == null){  tabStatisticsFragment = TabStatisticsFragment.newInstance(); }
                return tabStatisticsFragment;
            case 3:
                if(tabSettingsFragment == null){  tabSettingsFragment = TabSettingsFragment.newInstance(); }
                return tabSettingsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() { return FRAGMENT_CNT; }

    public void moveToTodayDetail(){ if(tabCalendarFragment != null) tabCalendarFragment.moveToTodayDetail(); }
    public void refreshTodayTab(){ if(tabTodayFragment != null) tabTodayFragment.refreshData(); }
    public void refreshStatisticsTab(){ if(tabStatisticsFragment != null) tabStatisticsFragment.refreshData(); }

}
