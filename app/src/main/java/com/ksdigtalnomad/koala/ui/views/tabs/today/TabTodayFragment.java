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

        setData();

        return mBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void setData(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        mBinding.headerText.setText(df.format(new Date()));


//        음주량별 가중치를 음주횟수에 곱해서 계산하여 나온 지표를 근거로 상태를 표시
//        Ex) 7일간 3일은 2단계, 4일은 4단계면 3*0.25 + 4*0.75
//
//        0.00 ~ 1.00 : "안전"
//        1.01 ~ 2.00 : "양호"
//        2.01 ~ 3.00 : "자제"
//        3.01 ~ 4.00 : "위험"
//        4.01 ~ 5.00 : "금지"
//
//
//        "데이터 부족"
//        최근 7일간 하루라도 데이터가 없으면 표시
//        누르면 월간 달력화면 이동 버튼으로 변신(밑줄이 쳐진 안내설명  문구)

//        mBinding.avgDrinkState.setText();
//        mBinding.avgDrinkTimes.setText();
//        mBinding.avgDrinkLevel.setText();
    }
}
