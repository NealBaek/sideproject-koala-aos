package com.ksdigtalnomad.koala.ui.views.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityHomeBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding mBinding;
    private HomeTapAdapter tapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mBinding.setActivity(this);

        tapAdapter = new HomeTapAdapter(getSupportFragmentManager());

        mBinding.homeTabViewPager.setAdapter(tapAdapter);
        mBinding.homeTabViewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void onTapClick(View v, int tapNum){ mBinding.homeTabViewPager.setCurrentItem(tapNum, true);  }
}
