package com.ksdigtalnomad.koala.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    // 엑티비티에서
    public static void hide(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {  view = new View(activity);  }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // 프레그먼트 등 기타 뷰에서
    public static void hide(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
