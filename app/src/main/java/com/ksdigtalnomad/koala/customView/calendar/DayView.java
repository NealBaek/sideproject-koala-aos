package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends RelativeLayout {

    private LinearLayout dayHeaderView;
    private Context context;

    public DayView(Context context) {
        super(context);

        this.context = context;

        Log.d("ABC", "DayView w/ childCnt: " + getChildCount());

        initView();

        Log.d("ABC", "DayView w/ childCnt: " + getChildCount());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("ABC", "onLayout w/ childCnt: " + getChildCount());

        dayHeaderView.layout(0, 0, getMeasuredWidth(), 100);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getMeasuredWidth(), 50, 1);

        dayHeaderView.getChildAt(0).setLayoutParams(params);
        dayHeaderView.getChildAt(1).setLayoutParams(params);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("ABC", "onDraw w/ childCnt: " + getChildCount());

    }

    private void initView(){
        dayHeaderView = createDayHeaderLayout();


        TextView dayTv = new TextView(context);
        dayTv.setText("11");
        dayTv.setGravity(Gravity.LEFT | Gravity.CENTER);
        dayTv.setBackgroundColor(Color.CYAN);
        dayTv.setTextColor(Color.WHITE);
        dayTv.setTextSize(8);
        dayTv.setPadding(10,0,0,0);


        TextView levelTv = new TextView(context);
        levelTv.setText("Max");
        levelTv.setGravity(Gravity.RIGHT | Gravity.CENTER);
        levelTv.setBackgroundColor(Color.LTGRAY);
        levelTv.setTextColor(Color.WHITE);
        levelTv.setTextSize(8);
        levelTv.setPadding(0,0,10,0);


        dayHeaderView.addView(dayTv);
        dayHeaderView.addView(levelTv);


    }


    private LinearLayout createDayHeaderLayout(){

        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(Color.BLUE);
        layout.setOrientation(LinearLayout.VERTICAL);
        this.addView(layout);

        return layout;
    }

}
