package com.ksdigtalnomad.koala.helpers.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.BuildConfig;
import com.ksdigtalnomad.koala.helpers.data.PreferenceGenericHelper;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;

public class BadgeHelper {

    private static final int BADGE_COLOR = CalendarConstUtils.COLOL_RED;

    public enum Key { // Notice: version set must be 0.x.x (see BuildConfig.VERSION_NAME)
        v036_settingTapName(BuildConfig.BADGE_VERSION),
        v036_settingTapCalendarDesign(BuildConfig.BADGE_VERSION);

        private String version;

        Key(String version){
            this.version = version;
        }
    }

    public static void showBadge(Key key, ViewGroup parent, float marginTop, float marginRight, float radius){

        if(parent == null) return;
        if(!key.version.equals(BuildConfig.VERSION_NAME)) return;
        if((Boolean) PreferenceGenericHelper.getInstance().getValue(key.name(), false)) return;

        View badge = new DotBadge(parent.getContext(), key, marginTop, marginRight, radius);
        badge.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        parent.addView(badge);
    }

    public static class DotBadge extends View {

        private Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private Key key;
        private float marginTop;
        private float marginRight;
        private float radius;

        public DotBadge(Context context, Key key, float marginTop, float marginRight, float radius) {
            super(context);

            this.key = key;
            this.marginTop = marginTop;
            this.marginRight = marginRight;
            this.radius = radius;

            badgePaint.setColor(BadgeHelper.BADGE_COLOR);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = MeasureSpec.getSize(heightMeasureSpec);

            int size = Math.min(w, h);
            setMeasuredDimension(size, size);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int parentWidth = getMeasuredWidth();
            float density = getResources().getDisplayMetrics().density;
            float marginTop = this.marginTop * density;
            float marginRight = this.marginRight * density;
            float radius = this.radius * density;

            // float cx, float cy, float radius, @NonNull Paint paint
            canvas.drawCircle(parentWidth - marginRight, marginTop, radius, badgePaint); // 원의중심 x,y, 반지름,paint

        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev){
            if(getVisibility() != View.GONE) PreferenceGenericHelper.getInstance().setValue(key.name(), true);
            setVisibility(View.GONE);
            return false;//consume
        }
    }
}
