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

    private static int HEIIGHT_HEADER = 100;
    private static int HEIIGHT_BODY = 200;
    private LinearLayout dayHeaderLayout;
    private LinearLayout dayBodyLayout;


    public DayView(Context context) {
        super(context);

        Log.d("ABC", "DayView w/ childCnt: " + getChildCount());

        initView();

        Log.d("ABC", "DayView w/ childCnt: " + getChildCount());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("ABC", "onLayout w/ childCnt: " + getChildCount());

        int parentWidth  = getMeasuredWidth();
        int parentHeight = getMeasuredHeight();

        // 1. Layout DayHeaderLayout
        dayHeaderLayout.layout(0, 0, parentWidth, HEIIGHT_HEADER);
        setChildrenLp(dayHeaderLayout);


        // 2. Layout DayBodyLayout
        dayBodyLayout.layout(0, parentHeight - HEIIGHT_HEADER, parentWidth, HEIIGHT_BODY);
        setChildrenLp(dayHeaderLayout);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // @TODO: 몇번 호출되는지 확인
        // @TODO: onLayout 에서 부르는 함수를 여기서 호출해보자
            // @TODO: 한번만 호출 되면 여기서 init
            // @TODO: 아니면
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("ABC", "onDraw w/ childCnt: " + getChildCount());

    }

    private void initView(){
        dayHeaderLayout = createDayHeaderLayout();
        dayBodyLayout = createDayBodyLayout();


        addView(dayHeaderLayout);
        addView(dayBodyLayout);
    }


    private LinearLayout createDayHeaderLayout(){ // dayNum, drunkLevel

        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView dayTv        = createLeftSideTv("11", Color.RED);
        TextView drunkLevelTv = createRightSideTv("Max", Color.WHITE, Color.RED);

        layout.addView(dayTv);
        layout.addView(drunkLevelTv);

        return layout;
    }

    private LinearLayout createDayBodyLayout(){ // dayNum, drunkLevel

        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView friendListTv = createLeftSideTv("아이유 외 1", Color.BLACK);
        TextView foodListTv   = createLeftSideTv("곱창 외 1", Color.BLACK);
        TextView liquorListTv = createLeftSideTv("소주 외 1", Color.BLACK);
        TextView memoTv       = createLeftSideTv("행사 뒷풀이", Color.BLACK);

        layout.addView(friendListTv);
        layout.addView(foodListTv);
        layout.addView(liquorListTv);
        layout.addView(memoTv);


        return layout;
    }

    private TextView createLeftSideTv(String contents, int textColor){

        TextView textView = new TextView(getContext());
        textView.setText(contents);
        textView.setTextColor(textColor);
        textView.setTextSize(8);

        textView.setGravity(Gravity.LEFT | Gravity.CENTER);

        textView.setBackgroundColor(Color.WHITE);

        textView.setPadding(10,0,0,0);

        return textView;
    }
    private TextView createRightSideTv(String contents, int txtColor, int bgColor){

        TextView textView = new TextView(getContext());

        textView.setText(contents);
        textView.setTextColor(txtColor);
        textView.setTextSize(8);

        textView.setGravity(Gravity.RIGHT | Gravity.CENTER);
        textView.setBackgroundColor(bgColor);

        textView.setPadding(0,0,10,0);

        return textView;
    }


    private void setChildrenLp(LinearLayout parent){

        int parentWidth  = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();
        int childCnt     = parent.getChildCount();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth, parentHeight/childCnt, 1);

        for(int i = 0; i < childCnt; ++ i){
            parent.getChildAt(i).setLayoutParams(params);
        }

    }
}
