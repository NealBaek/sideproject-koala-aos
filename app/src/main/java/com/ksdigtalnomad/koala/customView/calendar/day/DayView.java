package com.ksdigtalnomad.koala.customView.calendar.day;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.ksdigtalnomad.koala.customView.calendar.CalendarConstUtils;

import java.util.ArrayList;

public class DayView extends RelativeLayout {

    private DayModel dayModel = null;

    // Paints
    private Paint dayTvPt = null;
    private Paint drunkLvRectPt = null;
    private Paint drunkLvTvPt = null;
    private Paint listTvPt = null;


    private final String DRUNK_LV_STR = "MAX";


    // Text attributes
    private final float TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
    private final int TEXT_PADDING_LEFT  = 15;
    private final int TEXT_PADDING_RIGHT = 15;

    private int txHeightForHeader = 0;
    private int txHeightForBody = 0;



    public DayView(Context context){ //, int width, int height) {
        super(context);

        // DayView 설정
        setBackgroundColor(Color.WHITE);
    }


    /** set Data*/
    public void setDayModel(DayModel dayModel){
        this.dayModel = dayModel;

        // 1. 페인트 준비
        initPaints(dayModel);
    }

    /** Paints */
    private void initPaints(DayModel dayModel){

        // 1. day text Pain 만들기
        int DAY_COLOR = CalendarConstUtils.getDayColor(dayModel.daySeq);
        dayTvPt = createTxPaint(DAY_COLOR, Paint.Align.LEFT, null);

        // 2. drunkLevel Paint 만들기
        //  2-1. Rect
        drunkLvRectPt = createRectPaint(CalendarConstUtils.getDrunkLvColor(dayModel.drunkLevel));
        //  2-2. MAX
        drunkLvTvPt = createTxPaint(Color.WHITE, Paint.Align.RIGHT, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // 3. 리스트 텍스트 Pain 만들기 (역순으로 리스트 형식은 어떨까?)
        listTvPt = createTxPaint(Color.BLACK, Paint.Align.LEFT, null);


        // 4. 텍스트 기준 높이 계산

        Rect headerTvBound = new Rect();
        dayTvPt.getTextBounds(DRUNK_LV_STR, 0, DRUNK_LV_STR.length(), headerTvBound);

        Rect bodyTvBound = new Rect();
        listTvPt.getTextBounds(DRUNK_LV_STR, 0, DRUNK_LV_STR.length(), bodyTvBound);


        txHeightForHeader = headerTvBound.height();
        txHeightForBody = bodyTvBound.height();
    }

    // Create Paints
    private Paint createTxPaint(int textColor, Paint.Align align, Typeface typeface){
        Paint textp = new Paint();

        textp.setColor(textColor);
        textp.setTextSize(TEXT_SIZE);
        textp.setTextAlign(align);
        textp.setAntiAlias(true);

        if(typeface != null){ textp.setTypeface(typeface); }

        return textp;
    }
    private Paint createRectPaint(int bgColor){
        Paint rectP = new Paint();

        rectP.setColor(bgColor);
        rectP.setStrokeWidth(0);

        return rectP;
    }





    /** Draw */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(dayModel == null) return;

        // 1. Header 그리기
        drawHeader(canvas, dayModel);

        // 2. Body 그리기
        drawBody(canvas);


    }

    // Draw Layouts
    private void drawHeader(Canvas canvas, DayModel model){

        int parentWidth  = getMeasuredWidth();

        // 1. day text 그리기

        // 1-1. 레이아웃 HEIGHT & MARGIN
        int dayTvHeight = txHeightForHeader * 2;
        int dayTvTopMargin = txHeightForHeader/2;


        // 1-2. 텍스트 그리기
        drawDayTv(canvas, txHeightForHeader, TEXT_PADDING_LEFT, dayTvTopMargin, dayTvHeight, dayTvPt, model);



        // 2. drunkLv 그리기
        drawDrunkLv(canvas, txHeightForHeader, dayTvTopMargin, dayTvHeight, parentWidth, drunkLvRectPt, drunkLvTvPt, DRUNK_LV_STR);
    }
    private void drawBody(Canvas canvas){

        int parentHeight = getMeasuredHeight();

        // 리스트 텍스트 그리기
        drawListTvs(canvas, txHeightForBody ,parentHeight, listTvPt, dayModel);

    }


    // Draw Details
    private void drawDayTv(Canvas canvas, int TEXT_HEIGHT, int PADDING_R, int MARGIN_T, int HEIGHT, Paint paint, DayModel dayModel){

        String text = String.valueOf(dayModel.day);

        int PADDING_TOP = (HEIGHT - TEXT_HEIGHT)/2;


        int x = PADDING_R;
        int y = MARGIN_T + HEIGHT - PADDING_TOP;

        canvas.drawText(text, x, y, paint);
    }
    private void drawDrunkLv(Canvas canvas, int TEXT_HEIGHT, int DAY_TV_MARGIN_TOP, int DAY_TV_HEIGHT, int PARENT_WIDTH, Paint rPaint, Paint txPaint, String text){

        //  1. Rect 그리기

        int RECT_L = 0;
        int RECT_T = DAY_TV_MARGIN_TOP + (DAY_TV_HEIGHT * 1);
        int RECT_R = PARENT_WIDTH;
        int RECT_B = DAY_TV_MARGIN_TOP + (DAY_TV_HEIGHT * 2);

        canvas.drawRect(RECT_L, RECT_T, RECT_R, RECT_B, rPaint);


        //  2. MAX Text 그리기
        int PADDING_TOP = (RECT_B - RECT_T - TEXT_HEIGHT)/2;

        canvas.drawText(text, PARENT_WIDTH - TEXT_PADDING_RIGHT, RECT_B - PADDING_TOP, txPaint);
    }
    private void drawListTvs(Canvas canvas, int TEXT_HEIGHT, int PARENT_HEIGHT, Paint txPaint, DayModel dayModel){

        //  1. list text 검증

        String friendStr = CalendarConstUtils.getShortStr(dayModel.friendList);
        String foodStr = CalendarConstUtils.getShortStr(dayModel.foodList);
        String liquorStr = CalendarConstUtils.getShortStr(dayModel.liquorList);
        String memoStr = CalendarConstUtils.getShortStr(dayModel.memo);

        ArrayList<String> toDrawList = new ArrayList<>();

        if(memoStr != "")   { toDrawList.add(memoStr); }
        if(liquorStr != "") { toDrawList.add(liquorStr); }
        if(foodStr != "")   { toDrawList.add(foodStr); }
        if(friendStr != "") { toDrawList.add(friendStr); }


        final int toDrawCnt = toDrawList.size();

        if(toDrawCnt == 0){ return; }

        //  2. list text 그리기 (vertical, from bottom)

        final int VIEW_HEIGHT = (int)(TEXT_HEIGHT * 1.5);
        final int LIST_TV_Y = PARENT_HEIGHT - TEXT_HEIGHT * 4;

        for(int i = 0; i < toDrawCnt; ++i){
            canvas.drawText(toDrawList.get(i), TEXT_PADDING_LEFT, LIST_TV_Y + (VIEW_HEIGHT * (2 - i)), txPaint);
        }


    }
}
