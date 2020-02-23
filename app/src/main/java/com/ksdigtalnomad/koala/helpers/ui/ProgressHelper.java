package com.ksdigtalnomad.koala.helpers.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;

public class ProgressHelper {

    private static final int PROGRESS_COLOR = CalendarConstUtils.COLOL_MAIN;
    private static final int BLINDER_ID = 123456789;
    private static final int TILE_AUTO_CLOSE = 7000;

    public static void showProgress(ViewGroup parent, Boolean isAutoClose){
        if(parent == null) return;

        // 프로그래스 생성
        RelativeLayout.LayoutParams progressLp = new RelativeLayout.LayoutParams(60, 60);
        progressLp.addRule(RelativeLayout.CENTER_IN_PARENT);

        ProgressBar progressBar = new ProgressBar(parent.getContext());
        progressBar.setLayoutParams(progressLp);

        // 블라인더 생성
        BlinderLayout blinder = new BlinderLayout(parent.getContext());
        blinder.setBackgroundColor(ColorUtils.setAlphaComponent(Color.BLACK, 100));
        blinder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blinder.setId(BLINDER_ID);
        blinder.setEnabled(false);


        // 뷰 추가
        blinder.addView(progressBar);
        parent.addView(blinder);

        // run progress
        progressBar.post(()->progressBar.getIndeterminateDrawable().setColorFilter(PROGRESS_COLOR, PorterDuff.Mode.MULTIPLY));

        // 자동 종료
        if(isAutoClose) progressBar.postDelayed(()->dismissProgress(parent), TILE_AUTO_CLOSE);
    }



    public static void dismissProgress(ViewGroup parent){
        if(parent == null) return;
        if(parent.getChildCount() <= 0) return;
        if(parent.findViewById(BLINDER_ID) == null) return;

        parent.post(()->{
            ViewGroup blinder = parent.findViewById(BLINDER_ID);
            blinder.setVisibility(View.GONE);
            parent.removeView(blinder);
        });
    }

    private static class BlinderLayout extends RelativeLayout{
        public BlinderLayout(Context context) {
            super(context);
        }
        public BlinderLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev){
            return true;//consume
        }
    }
}
