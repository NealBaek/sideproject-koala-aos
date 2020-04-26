package com.ksdigtalnomad.koala.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.ksdigtalnomad.koala.helpers.ui.KeyboardHelper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null) {
            KeyboardHelper.hide(this);
            view.clearFocus();
        }
        return super.dispatchTouchEvent(ev);
    }
}
