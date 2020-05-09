package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class QuantityDrinkDialog extends BaseDialogFragment {

    public String name = "";

    private CompleteClickListener completeClickListener;

    private TextView tvResult;
    private NumberPicker npQuantaty;
    private NumberPicker npUnit;

    public static QuantityDrinkDialog newInstance() { return new QuantityDrinkDialog();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_quantity_drink);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        view.findViewById(R.id.btnCancel).setOnClickListener((v) -> dismiss());
        view.findViewById(R.id.btnConfirm).setOnClickListener((v) -> {
            completeClickListener.onClick(tvResult.getText().toString(), 0, "");
            dismiss();
        });

        tvResult = view.findViewById(R.id.tv_result);

        String[] quantaties = new String[] {"0", "0.25", "0.5", "0.75", "1"};
        String[] units = new String[] {"병", "잔", "캔", "피쳐(3,000cc)", "피쳐(3,000cc)"};

        // 음주량
        npQuantaty = view.findViewById(R.id.np_quantity);
        npQuantaty.setMaxValue(quantaties.length - 1);
        npQuantaty.setMinValue(0);
        npQuantaty.setDisplayedValues(quantaties);
        npQuantaty.setOnValueChangedListener((NumberPicker numberPicker, int i, int i1) -> setResult(quantaties, units));
        npQuantaty.setValue(1);


        // 단위
        npUnit = view.findViewById(R.id.np_unit);
        npUnit.setMaxValue(units.length - 1);
        npUnit.setMinValue(0);
        npUnit.setDisplayedValues(units);
        npUnit.setOnValueChangedListener((NumberPicker numberPicker, int i, int i1) -> setResult(quantaties, units));
        npUnit.setValue(2);

        setResult(quantaties, units);
    }

    private void setResult(String[] quantaties, String[] units){
        tvResult.setText(name + " " + quantaties[npQuantaty.getValue()] + "" + units[npUnit.getValue()]);
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick(String result, double amount, String unit);
    }
}
