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

import java.text.SimpleDateFormat;
import java.util.Date;

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


        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        mBinding.headerText.setText(df.format(new Date()));

        return mBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
