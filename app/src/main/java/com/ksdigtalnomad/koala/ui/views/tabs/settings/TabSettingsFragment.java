package com.ksdigtalnomad.koala.ui.views.tabs.settings;

import android.content.Context;
import android.databinding.DataBindingUtil;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentTabSettingsBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.util.ToastWriter;

public class TabSettingsFragment extends BaseFragment {

    private FragmentTabSettingsBinding mBinding;

    private Context mContext;

    public static BaseFragment newInstance(){
        TabSettingsFragment fragment = new TabSettingsFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_settings, container, false);

        return mBinding.getRoot();
    }

    public void onLayoutClick(int layoutNum){
        switch (layoutNum){
            case 2:  break;
            case 3:  break;
            case 4:  break;
            case 5:  break;
        }

        ToastWriter.writeBottomLongToast("" + layoutNum);
    }
}
