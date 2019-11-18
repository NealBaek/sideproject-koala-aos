package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;


/**
 * Created by ooddy on 15/11/2019.
 */

public class CustomTimePickerDialog extends BaseDialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String KEY_TIME_HOUR = "TIME_HOUR";
    private static final String KEY_TIME_MINUTE = "TIME_MINUTE";

    private TimeSetListener timeSetListener;

    private int hour;
    private int minute;

    private static CustomTimePickerDialog newInstance() {
        return new CustomTimePickerDialog();
    }
    public static CustomTimePickerDialog newInstance(int hour, int min) {
        CustomTimePickerDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TIME_HOUR, hour);
        bundle.putInt(KEY_TIME_MINUTE, min);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        hour = getArguments().getInt(KEY_TIME_HOUR);
        minute = getArguments().getInt(KEY_TIME_MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timeSetListener.onFinish(hour, minute);
    }

    public void setDialogListener(TimeSetListener timeSetListener) {
        this.timeSetListener = timeSetListener;
    }

    public interface TimeSetListener {
        void onFinish(int hour, int minute);
    }
}
