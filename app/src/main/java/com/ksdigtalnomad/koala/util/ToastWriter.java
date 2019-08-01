package com.ksdigtalnomad.koala.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class ToastWriter {
    public static void writeCenterShortToast(String message) {
        toCenter(makeToast(message, Toast.LENGTH_SHORT)).show();
    }

    public static void writeCenterLongToast(String message) {
        toCenter(makeToast(message, Toast.LENGTH_LONG)).show();
    }

    public static void writeBottomShortToast(String message) {
        makeToast(message, Toast.LENGTH_SHORT).show();
    }

    public static void writeBottomLongToast(String message) {
        makeToast(message, Toast.LENGTH_LONG).show();
    }

    // private function
    private static Context getContext() {
        return BaseApplication.getInstance();
    }

    private static Toast makeToast(String message, int position) {
        return Toast.makeText(getContext(), message, position);
    }

    private static Toast toCenter(Toast toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }
}
