package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DayView extends RelativeLayout {

    private LinearLayout dayHeaderView;

    public DayView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("ABC", "onLayout");
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("ABC", "onDraw");

    }

    private void initView(){
        dayHeaderView = createDayHeaderLayout();

        this.addView(dayHeaderView, 50, 50);

        dayHeaderView.setBackgroundColor(Color.RED);
    }


    private LinearLayout createDayHeaderLayout(){

        LinearLayout layout = new LinearLayout(getContext());

        LayoutParams params = new LayoutParams(50, 50);

        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        layout.setLayoutParams(params);

        layout.setBackgroundColor(Color.BLUE);


        return layout;
    }

}
