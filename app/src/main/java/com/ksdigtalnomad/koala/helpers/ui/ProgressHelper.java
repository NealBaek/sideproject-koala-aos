package com.ksdigtalnomad.koala.helpers.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ProgressHelper {

    private static final String PROGRESS_COLOR = "#000000";
    private static final int BLINDER_ID = 123456789;
//    private static final int PROGRESS_ID = 987654321;

    public static void showProgress(ViewGroup parent){
        if(parent == null) return;

        // 프로그래스 생성
        RelativeLayout.LayoutParams progressLp = new RelativeLayout.LayoutParams(60, 60);
        progressLp.addRule(RelativeLayout.CENTER_IN_PARENT);

        ProgressBar progressBar = new ProgressBar(parent.getContext());
        progressBar.setLayoutParams(progressLp);
//        progressBar.setId(PROGRESS_ID);

        // 블라인더 생성
        RelativeLayout blinder = new RelativeLayout(parent.getContext());
        blinder.setBackgroundColor(ColorUtils.setAlphaComponent(Color.BLACK, 100));
        blinder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blinder.setId(BLINDER_ID);


        // 뷰 추가
        blinder.addView(progressBar);
        parent.addView(blinder);

        // run progress
        progressBar.post(()->{
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(PROGRESS_COLOR), PorterDuff.Mode.MULTIPLY);
        });
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

//        ProgressBar progressBar = parent.findViewById(PROGRESS_ID);

//        int childCnt = parent.getChildCount();
//        for(int i = 0; i < childCnt; ++i){
//            View child = parent.getChildAt(i);
//            int childId = child.getId();
//            if(childId == BLINDER_ID){ parent.removeView(child); }
//        }
    }
}
