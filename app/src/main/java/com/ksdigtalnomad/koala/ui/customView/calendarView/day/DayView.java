package com.ksdigtalnomad.koala.ui.customView.calendarView.day;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;
import com.ksdigtalnomad.koala.util.ToastHelper;

import java.util.ArrayList;

public class DayView extends RelativeLayout {

    // Event Listener
    private CalendarView.EventInterface eventInterface =null;

    // Data
    private DayModel dayModel = null;


    // Paints
    private Paint dayTvPt = null;
    private Paint drunkLvRectPt = null;
    private Paint drunkLvTvPt = null;
    private Paint listTvPt = null;
    private Paint blinderRectPt = null;

    // Border
    private GradientDrawable border = null;


    // Text attributes
    private final String DRUNK_LV_STR = "MAX";

    private final float TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

    private final int TEXT_PADDING_LEFT  = 15;
    private final int TEXT_PADDING_RIGHT = 15;

    private int txHeightForHeader = 0;
    private int txHeightForBody = 0;



    public DayView(Context context, final CalendarView.EventInterface eventInterface){
        super(context);

        this.eventInterface = eventInterface;


        // 1. DayView 설정
        setBackgroundColor(Color.WHITE);



        // 2. 클릭 이벤트 설정
        setOnClickListener(view -> {
            if(!dayModel.isOutMonth && !DateHelper.getInstance().isAfterToday(dayModel.date)){
                eventInterface.onDayViewTouch(dayModel);
            }else{
                ToastHelper.writeBottomShortToast(getResources().getString(R.string.warning_cannot_edit_after_day));
            }
        });

        //use a GradientDrawable with only one color set, to make it a solid color
        GradientDrawable border = new GradientDrawable();
        border.setColor(getResources().getColor(R.color.colorPureWhite)); //white background
        border.setStroke(1, getResources().getColor(R.color.colorMain)); //black border with full opacity
    }


    /** get & set Data */
    public DayModel getDayModel(){
        return this.dayModel;
    }
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

        // 5. Blinder Rect Paint 만들기
        blinderRectPt = createRectPaint(ColorUtils.setAlphaComponent(BaseApplication.getInstance().getResources().getColor(R.color.colorLightGray), 200));

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

        // 0. 오늘이면 Border 추가
        if(DateHelper.getInstance().isToday(dayModel.date)){
            setBackground(border);
        }


        int parentWidth  = getMeasuredWidth();
        int parentHeight = getMeasuredHeight();

        // 1. day text 그리기

        //  1-1. 레이아웃 HEIGHT & MARGIN
        int dayTvHeight = txHeightForHeader * 2;
        int dayTvTopMargin = txHeightForHeader/2;


        //  1-2. 텍스트 그리기
        drawDayTv(canvas, txHeightForHeader, TEXT_PADDING_LEFT, dayTvTopMargin, dayTvHeight, dayTvPt, dayModel);


        // 2. DayModel 내용 그리기
        if(dayModel.isSaved){
            //  2-1. DrinkLv Rect Bottom 계산
            int drunkLvRectBottom = dayTvTopMargin + (dayTvHeight * 2);
            //  2-2. DrinkLv Rect & Text 그리기
            drawDrunkLv(canvas, txHeightForHeader, dayTvTopMargin, dayTvHeight, drunkLvRectBottom, parentWidth, drunkLvRectPt, drunkLvTvPt, DRUNK_LV_STR, dayModel.drunkLevel);

            // 2-3. 리스트 텍스트 그리기
            drawListTvs(canvas, txHeightForBody ,drunkLvRectBottom, listTvPt, dayModel);
        }


        // 3. OutMonth 블라인더 그리기
        if(dayModel.isOutMonth) drawBlinderRect(canvas, parentWidth, parentHeight, blinderRectPt);
    }


    // Draw Details
    private void drawDayTv(Canvas canvas, int TEXT_HEIGHT, int PADDING_R, int MARGIN_T, int HEIGHT, Paint paint, DayModel dayModel){

        String text = String.valueOf(dayModel.day);

        int PADDING_TOP = (HEIGHT - TEXT_HEIGHT)/2;


        int x = PADDING_R;
        int y = MARGIN_T + HEIGHT - PADDING_TOP;

        canvas.drawText(text, x, y, paint);
    }
    private void drawDrunkLv(Canvas canvas, int TEXT_HEIGHT, int DAY_TV_MARGIN_TOP, int DAY_TV_HEIGHT, int TARGET_BOTTOM,int PARENT_WIDTH, Paint rPaint, Paint txPaint, String text, int drunkLv){

        //  1. Rect 그리기
        int RECT_L = 0;
        int RECT_T = DAY_TV_MARGIN_TOP + (DAY_TV_HEIGHT * 1);
        int RECT_R = PARENT_WIDTH;
        int RECT_B = TARGET_BOTTOM;

        canvas.drawRect(RECT_L, RECT_T, RECT_R, RECT_B, rPaint);


        if(drunkLv != CalendarConstUtils.DRUNK_LV_MAX){ return; }

        //  2. MAX Text 그리기
        int PADDING_TOP = (RECT_B - RECT_T - TEXT_HEIGHT)/2;

        canvas.drawText(text, PARENT_WIDTH - TEXT_PADDING_RIGHT, RECT_B - PADDING_TOP, txPaint);
    }
    private void drawListTvs(Canvas canvas, int TEXT_HEIGHT, int TARGET_Y, Paint txPaint, DayModel dayModel){

        //  1. list text 검증
        String friendStr = CalendarConstUtils.getShortStrFromFriendList(dayModel.friendList);
        String foodStr = CalendarConstUtils.getShortStrFromFoodList(dayModel.foodList);
        String drinkStr = CalendarConstUtils.getShortStrFromDrinkList(dayModel.drinkList);
        String memoStr = CalendarConstUtils.getShortStr(dayModel.memo);

        ArrayList<String> toDrawList = new ArrayList<>();

        if(friendStr != "") { toDrawList.add(friendStr); }
        if(foodStr != "")   { toDrawList.add(foodStr); }
        if(drinkStr != "") { toDrawList.add(drinkStr); }
        if(memoStr != "")   { toDrawList.add(memoStr); }


        final int toDrawCnt = toDrawList.size();

        if(toDrawCnt == 0){ return; }

        //  2. list text 그리기 (vertical, from bottom)

        final int VIEW_HEIGHT = (int)(TEXT_HEIGHT * 1.5);
        final int LIST_TV_Y = TARGET_Y + TEXT_HEIGHT * 3;

        for(int i = 0; i < toDrawCnt; ++i){
            canvas.drawText(toDrawList.get(i), TEXT_PADDING_LEFT, LIST_TV_Y + (VIEW_HEIGHT * (i - 1)), txPaint);
        }


    }
    private void drawBlinderRect(Canvas canvas, int parentWidth, int parentHeight, Paint rPaint){
        canvas.drawRect(0, 0, parentWidth, parentHeight, rPaint);
    }
}
