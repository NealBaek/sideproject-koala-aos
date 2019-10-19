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
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;
import com.ksdigtalnomad.koala.util.ToastHelper;

public class AddDialog extends BaseDialogFragment {
    private static final String KEY_TO_ADD = "KEY_TO_ADD";

    private CompleteClickListener completeClickListener;

    public EditText editText;

    private static AddDialog newInstance() {
        return new AddDialog();
    }

    public static AddDialog newInstance(String text) {
        AddDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TO_ADD, text);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_add);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        editText = view.findViewById(R.id.textTF);
        editText.setText(getArguments().getString(KEY_TO_ADD));

        editText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(isNameValidate()){
                    completeClickListener.onClick(editText.getText().toString()); dismiss();
                }
            }
            return false;
        });

        view.findViewById(R.id.btnAdd).setOnClickListener((v)->{
            if(isNameValidate()){
                completeClickListener.onClick(editText.getText().toString()); dismiss();
            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener((v)->{
            dismiss();
        });
    }

    private boolean isNameValidate(){
        Editable editable = editText.getText();

        if (editable == null || editable.toString() == "" || editable.toString().length() <= 0){
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            ToastHelper.writeBottomLongToast("최소 1글자 이상 입력하셔야 합니다.");
            return false;
        }

        return true;
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick(String newName);
    }
}
