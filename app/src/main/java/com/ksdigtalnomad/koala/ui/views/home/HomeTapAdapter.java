package com.ksdigtalnomad.koala.ui.views.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ksdigtalnomad.koala.ui.views.tabs.calendar.TabCalendarFragment;
import com.ksdigtalnomad.koala.ui.views.tabs.settings.TabSettingsFragment;
import com.ksdigtalnomad.koala.ui.views.tabs.statistics.TabStatisticsFragment;
import com.ksdigtalnomad.koala.ui.views.tabs.today.TabTodayFragment;

public class HomeTapAdapter extends FragmentStatePagerAdapter {

    private final int FRAGMENT_CNT = 4;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;


    public HomeTapAdapter(FragmentManager fm ){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                if(fragment1 == null){  fragment1 = TabTodayFragment.newInstance(); }
                return fragment1;
            case 1:
                if(fragment2 == null){  fragment2 = TabCalendarFragment.newInstance(); }
                return fragment2;
            case 2:
                if(fragment3 == null){  fragment3 = TabStatisticsFragment.newInstance(); }
                return fragment3;
            case 3:
                if(fragment4 == null){  fragment4 = TabSettingsFragment.newInstance(); }
                return fragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() { return FRAGMENT_CNT; }
}
