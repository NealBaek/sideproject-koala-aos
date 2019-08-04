package com.ksdigtalnomad.koala.ui.views.tabs.statistics;

import android.content.Context;
import android.databinding.DataBindingUtil;
import com.ksdigtalnomad.koala.databinding.FragmentTabStatisticsBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;

public class TabStatisticsFragment extends BaseFragment {

    private FragmentTabStatisticsBinding mBinding;

    private Context mContext;

    public static BaseFragment newInstance(){
        TabStatisticsFragment fragment = new TabStatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

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
    }
}
