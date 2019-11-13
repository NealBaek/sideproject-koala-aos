package com.ksdigtalnomad.koala.ui.views.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.rd.PageIndicatorView;

public class GuideActivity extends BaseActivity {

    public static final String GUIDE_FIRST = "FIRST";
    public static final String GUIDE_SECOND = "SECOND";
    public static final String GUIDE_THIRD = "THIRD";


    PageIndicatorView pageIndicatorView;

    ViewPager viewPager;
    private GuideAdapter viewPagerAdapter;

    TextView nextBtn;
    TextView startBtn;

    public static Intent intent(Context context) {
        return new Intent(context, GuideActivity.class);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewPagerAdapter = new GuideAdapter(this);
        initViewPager();
    }

    private void initViewPager() {
        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelected(0);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new onPageChangeListenerClass());
    }

//    @OnClick(R.id.nextBtn) public void nextBtnClick() {
//        int current = viewPager.getCurrentItem();
//        viewPager.setCurrentItem(current + 1);
//    }
//
//    @OnClick(R.id.startBtn) public void startBtnClick() {
//        startActivity(LoginActivity.intent(this));
//        finish();
//    }

    class onPageChangeListenerClass implements ViewPager.OnPageChangeListener {

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageSelected(int position) {
            if (position >= 2) {
                nextBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
            } else {
                nextBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
            }
        }

        @Override public void onPageScrollStateChanged(int state) {

        }
    }

}
