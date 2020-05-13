package com.ksdigtalnomad.koala.ui.customView.calendarView.day;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;

import java.util.ArrayList;

import static com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils.Design;

public class DayView extends RelativeLayout {

    // Event Listener
    private CalendarView.EventInterface eventInterface =null;

    // Data
    private DayModel dayModel = null;


    // Paints
    private Paint borderRectPt = null;
    private Paint dayTvPt = null;
    private Paint drunkLvRectPt = null;
    private Paint drunkLvTvPt = null;
    private Paint listTvPtDefaults = null;

    // Image
    private Drawable imageStamp_1 = getResources().getDrawable(R.drawable.img_stamp_1);
    private int ivStampId = 77777777;
    private int vBlindId = 8888888;

    private int todayBoarderWidth = 2;

    // Text attributes
    private final String DRUNK_LV_STR = "MAX";

    private final float TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

    private final int TEXT_PADDING_LEFT  = 15;
    private final int TEXT_PADDING_RIGHT = 15;

    private int txHeightForHeader = 0;
    private int txHeightForBody = 0;
    private Design design;



    public DayView(Context context, final CalendarView.EventInterface eventInterface, Design design){
        super(context);

        this.eventInterface = eventInterface;
        this.design = design;

        // 1. DayView 설정
        setBackgroundColor(Color.WHITE);



        // 2. 클릭 이벤트 설정
        setOnClickListener(view -> {
            if(dayModel.isOutMonth) return;

            if(!DateHelper.getInstance().isAfterToday(dayModel.getDate())){
                eventInterface.onDayViewTouch(dayModel);
                return;
            }else{
                ToastHelper.writeBottomShortToast(getResources().getString(R.string.warning_cannot_edit_after_day));
            }

        });

    }


    /** get & set Data */
    public DayModel getDayModel(){
        return this.dayModel;
    }
    public void setDayModel(DayModel dayModel){
        this.dayModel = dayModel;

        CalendarDataController.checkThenSetYesterdayModel(dayModel);

        // 1. 페인트 준비
        initPaints(dayModel);
    }

    /** Paints */
    private void initPaints(DayModel dayModel){

        // 0. Border Paint 만들기
        borderRectPt = createBorderRectPaint(getResources().getColor(R.color.colorMain));

        // 1. day text Paint 만들기
        int DAY_COLOR = CalendarConstUtils.getDayColor(dayModel.daySeq);
        dayTvPt = createTxPaint(DAY_COLOR, Paint.Align.LEFT, null);

        // 2. drunkLevel Paint 만들기
        //  2-1. Rect
        switch (design){
            case defaults:
                drunkLvRectPt = createRectPaint(CalendarConstUtils.getDrunkLvColorRedByStep(dayModel.drunkLevel));
                break;
            case stamp_1:
                if(dayModel.drunkLevel == CalendarConstUtils.DRUNK_LV_0){
                    drunkLvRectPt = createRectPaint(CalendarConstUtils.COLOL_MAIN);
                }else{
                    drunkLvRectPt = createRectPaint(CalendarConstUtils.getDrunkLvColorRedByStep(CalendarConstUtils.DRUNK_LV_0));
                }
                break;
        }

        //  2-2. MAX
        drunkLvTvPt = createTxPaint(Color.WHITE, Paint.Align.RIGHT, Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // 3. 리스트 텍스트 Paint 만들기 (역순으로 리스트 형식은 어떨까?)
        listTvPtDefaults = createTxPaint(Color.BLACK, Paint.Align.LEFT, null);

        // 4. 텍스트 기준 높이 계산

        Rect headerTvBound = new Rect();
        dayTvPt.getTextBounds(DRUNK_LV_STR, 0, DRUNK_LV_STR.length(), headerTvBound);

        Rect bodyTvBound = new Rect();
        listTvPtDefaults.getTextBounds(DRUNK_LV_STR, 0, DRUNK_LV_STR.length(), bodyTvBound);


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

        if(typeface != null){
            textp.setTypeface(typeface);
        }

        return textp;
    }
    private Paint createRectPaint(int bgColor){
        Paint rectP = new Paint();

        rectP.setColor(bgColor);
        rectP.setStrokeWidth(0);

        return rectP;
    }
    private Paint createBorderRectPaint(int color){
        Paint rectP = new Paint();

        rectP.setColor(color);
        rectP.setStyle(Paint.Style.STROKE);
        rectP.setStrokeWidth(todayBoarderWidth + 3);

        return rectP;
    }




    /** Draw */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(dayModel == null) return;

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

            switch (design){
                case defaults:

                    // 2-3-1. 리스트 텍스트 그리기
                    drawListTvs(canvas, txHeightForBody ,drunkLvRectBottom, listTvPtDefaults, dayModel);

                    break;
                case stamp_1:

                    if (dayModel.drunkLevel == CalendarConstUtils.DRUNK_LV_0){
                        // 2-3-2. 금주 -> 스탬프 그리기
                        addStamp1(
                            /* x */ ((int) Math.round(parentWidth * 0.125)),
                            /* y */ (txHeightForBody + drunkLvRectBottom),
                            /* width */ ((int) Math.round(parentWidth * 0.875)),
                            parentHeight/2
                        );
                    }else{
                        // 2-3-2. 음주 -> 스탬프 이미지 삭제, 리스트 텍스트 그리기
                        hideStamp1();
                        drawListTvs(canvas, txHeightForBody ,drunkLvRectBottom, listTvPtDefaults, dayModel);
                    }

                    break;
            }


        }else{
            hideStamp1();
        }

        // 3. 오늘이면 Border 추가
        if(DateHelper.getInstance().isToday(dayModel.getDate())){
            canvas.drawRect(
                    todayBoarderWidth,
                    todayBoarderWidth,
                    parentWidth - todayBoarderWidth,
                    parentHeight - todayBoarderWidth,
                    borderRectPt
            );
        }


        // 4. OutMonth 블라인더 그리기
        if(dayModel.isOutMonth) addBlinder(parentWidth, parentHeight);
        else hideBlinder();
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

        switch (design){
            case defaults:
                if(drunkLv != CalendarConstUtils.DRUNK_LV_MAX){ return; }

                //  2. MAX Text 그리기
                int PADDING_TOP = (RECT_B - RECT_T - TEXT_HEIGHT)/2;

                canvas.drawText(text, PARENT_WIDTH - TEXT_PADDING_RIGHT, RECT_B - PADDING_TOP, txPaint);
                break;
            case stamp_1:
                break;
        }
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
    private void addBlinder(int parentWidth, int parentHeight){
//        canvas.drawRect(0, 0, parentWidth, parentHeight, rPaint);

        View vBlind = findViewById(vBlindId);
        if(vBlind == null){
            vBlind = new View(getContext());
            vBlind.setBackgroundColor(BaseApplication.getInstance().getResources().getColor(R.color.colorLightGray));
            vBlind.setLayoutParams(new RelativeLayout.LayoutParams(parentWidth, parentHeight));
            vBlind.setId(vBlindId);
            vBlind.setAlpha(0.6f);
            addView(vBlind);
        }else{
            vBlind.setVisibility(VISIBLE);
        }
    }
    private void hideBlinder(){
        View vBlind = findViewById(vBlindId);
        if(vBlind != null) {
            vBlind.setVisibility(GONE);
        }
    }
    private void addStamp1(int x, int y, int width, int height){

        ImageView ivStamp1 = findViewById(ivStampId);

        if(ivStamp1 == null){
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
            lp.setMargins(x, y, x, 0);

            ivStamp1 = new ImageView(getContext());
            ivStamp1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ivStamp1.setImageDrawable(imageStamp_1);
            ivStamp1.setLayoutParams(lp);
            ivStamp1.setId(ivStampId);
            addView(ivStamp1);
        }else{
            ivStamp1.setVisibility(VISIBLE);
        }
    }
    private void hideStamp1(){
        ImageView ivStamp1 = findViewById(ivStampId);
        if(ivStamp1 != null){
            ivStamp1.setVisibility(INVISIBLE);
        }
    }
}
