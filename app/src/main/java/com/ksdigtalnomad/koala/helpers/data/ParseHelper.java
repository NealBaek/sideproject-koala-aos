package com.ksdigtalnomad.koala.helpers.data;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import java.text.DecimalFormat;

public class ParseHelper {
    public static String parseDouble(double value) {
        DecimalFormat nf = new DecimalFormat("#,###.##");
        return nf.format(value);
    }

    public static String parseMoney(double money) {
        DecimalFormat nf = new DecimalFormat("#,###.##");
        return BaseApplication.getInstance().getResources().getString(
                R.string.calendar_expense_currency,
                nf.format(money));
    }
}
