package com.ksdigtalnomad.koala.customView.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

//    private int parentWidth;
//    private int parentHeight;

    // header l t r b
    private int headerLeft;
    private int headerTop;
    private int headerRight;
    private int headerBottom;

    // body l t r b
    private int bodyLeft;
    private int bodyTop;
    private int bodyRight;
    private int bodyBottom;
    private static final int BODY_MARGIN = 20;

    public LinearLayout dayHeaderLayout;
    private LinearLayout dayBodyLayout;


    // Text attributes
    private final float TEXT_SIZE = 8;
    private final int TEXT_PADDING_LEFT  = 15;
    private final int TEXT_PADDING_RIGHT = 10;


    public DayView(Context context){ //, int width, int height) {
        super(context);

//        parentWidth = width;
//        parentHeight = height;

//        initView();
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//        Log.d("ABC", "onLayout");
//
//        sup
//
//        if (!changed){ return; }
//
////        Log.d("ABC", "changed: " + changed);
//
//        int parentWidth  = getMeasuredWidth();
//        int parentHeight = getMeasuredHeight();
//
//        // 1. Layout DayHeaderLayout
//
//        headerLeft   = 0;
//        headerTop    = 0;
//        headerRight  = parentWidth;
//        headerBottom = parentHeight/3;
//
//        dayHeaderLayout.layout(headerLeft, headerTop, headerRight, headerBottom);
//        setChildrenLp(dayHeaderLayout);
//
//
//
//        // 2. Layout DayBodyLayout
//
//        bodyLeft   = 0;
//        bodyTop    = parentHeight/2 - BODY_MARGIN;
//        bodyRight  = parentWidth;
//        bodyBottom = parentHeight - BODY_MARGIN;
//
//        dayBodyLayout.layout(bodyLeft, bodyTop, bodyRight, bodyBottom);
//        setChildrenLp(dayBodyLayout);
////
////
////        Log.d("ABC", "onMeasure, w: " + parentWidth + ", h: " + parentHeight);
////        Log.d("ABC", "onMeasure, ㅇ: " + dayHeaderLayout.getWidth());
//    }



//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        Log.d("ABC", "onMeasure");
//
//        // @TODO: 몇번 호출되는지 확인
//        // @TODO: onLayout 에서 부르는 함수를 여기서 호출해보자
//            // @TODO: 한번만 호출 되면 여기서 init
//            // @TODO: 아니면
//
//
//
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("ABC", "onDraw");

        int parentWidth  = getMeasuredWidth();
        int parentHeight = getMeasuredHeight();


        // 1. day text 그리기

        // 2. drunkLevel
        //  2-1. Rect 그리기
        //  2-2. MAX 그리기

        // 3. 리스트 텍스트 (역순으로 리스트 형식은 어떨까?)
        //  3-1. friendList text 그리기
        //  3-2. friendList text 그리기
        //  3-3. friendList text 그리기
        //  3-4. friendList text 그리기


        // 텍스트 그리기 속성
        Paint textp = new Paint();

        textp.setColor(Color.BLACK);
        textp.setTextSize(10);
        textp.setTextAlign(Paint.Align.CENTER);


        // 텍스트 그리기
        canvas.drawText("테스트", parentWidth / 2, parentHeight / 2, textp);


//        canvas.draw
    }

    public void initView(){
        dayHeaderLayout = createDayHeaderLayout();
        dayBodyLayout = createDayBodyLayout();


        addView(dayHeaderLayout);
        addView(dayBodyLayout);
    }


    private LinearLayout createDayHeaderLayout(){ // dayNum, drunkLevel

        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView dayTv        = createLeftSideTv("11", Color.DKGRAY);
        TextView drunkLevelTv = createRightSideTv("MAX", Color.WHITE, Color.RED);

        drunkLevelTv.setTypeface(null, Typeface.BOLD);

        layout.addView(dayTv);
        layout.addView(drunkLevelTv);

        return layout;
    }

    private LinearLayout createDayBodyLayout(){ // dayNum, drunkLevel

        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView friendListTv = createLeftSideTv("아이유 외 1", Color.DKGRAY);
        TextView foodListTv   = createLeftSideTv("곱창 외 1", Color.DKGRAY);
        TextView liquorListTv = createLeftSideTv("소주 외 1", Color.DKGRAY);
        TextView memoTv       = createLeftSideTv("행사 뒷풀이", Color.DKGRAY);

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
        textView.setTextSize(TEXT_SIZE);

        textView.setGravity(Gravity.LEFT | Gravity.CENTER);

        textView.setBackgroundColor(Color.WHITE);

        textView.setPadding(TEXT_PADDING_LEFT,0,0,0);

        return textView;
    }
    private TextView createRightSideTv(String contents, int txtColor, int bgColor){

        TextView textView = new TextView(getContext());

        textView.setText(contents);
        textView.setTextColor(txtColor);
        textView.setTextSize(TEXT_SIZE);

        textView.setGravity(Gravity.RIGHT | Gravity.CENTER);

        textView.setBackgroundColor(bgColor);

        textView.setPadding(0,0,TEXT_PADDING_RIGHT,0);

        return textView;
    }


    public void setChildrenLp(LinearLayout parent){

        int parentWidth  = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();
        int childCnt     = parent.getChildCount();

        Log.d("ABC", "setChildrenLp, w: " + parentWidth + ", h: " + parentHeight);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(parentWidth, parentHeight/childCnt, 1);

        for(int i = 0; i < childCnt; ++ i){
            parent.getChildAt(i).setLayoutParams(params);
        }

    }
}
