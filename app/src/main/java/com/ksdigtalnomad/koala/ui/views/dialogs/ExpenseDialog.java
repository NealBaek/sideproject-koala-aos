package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class ExpenseDialog extends BaseDialogFragment {
    private static final String KEY_TO_ADD = "KEY_TO_ADD";

    private CompleteClickListener completeClickListener;

    public EditText editText;

    private static ExpenseDialog newInstance() {
        return new ExpenseDialog();
    }

    public static ExpenseDialog newInstance(double expense) {
        ExpenseDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putDouble(KEY_TO_ADD, expense);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_expense);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        double expense = getArguments().getDouble(KEY_TO_ADD);

        editText = view.findViewById(R.id.textTF);
        if(expense > 0) editText.setText(String.valueOf(expense));
        editText.setSelection(editText.getText().length());
        editText.requestFocus();

        editText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try{
                    String result = editText.getText().toString();
                    completeClickListener.onClick((result.isEmpty())? 0 : Double.valueOf(result)); dismiss();
                }catch (Exception e){
                    completeClickListener.onClick((0)); dismiss();
                }
            }
            return false;
        });

        view.findViewById(R.id.btnAdd).setOnClickListener((v)->{
            try{
                String result = editText.getText().toString();
                completeClickListener.onClick((result.isEmpty())? 0 : Double.valueOf(result)); dismiss();
            }catch (Exception e){
                completeClickListener.onClick((0)); dismiss();
            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener((v)->{
            dismiss();
        });
    }


    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick(double newValue);
    }
}
