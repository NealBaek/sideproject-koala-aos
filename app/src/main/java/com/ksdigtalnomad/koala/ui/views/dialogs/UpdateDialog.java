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

public class UpdateDialog extends BaseDialogFragment {
    private static final String KEY_POSITION = "KEY_POSITION";
    private static final String KEY_TO_ADD = "KEY_TO_ADD";

    private CompleteClickListener completeClickListener;

    private EditText editText;
    private String originalName;
    private int originalPos;

    private static UpdateDialog newInstance() {
        return new UpdateDialog();
    }

    public static UpdateDialog newInstance(int position, String text) {
        UpdateDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);
        bundle.putString(KEY_TO_ADD, text);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_update);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        originalName = getArguments().getString(KEY_TO_ADD);
        originalPos = getArguments().getInt(KEY_POSITION);

        editText = view.findViewById(R.id.textTF);
        editText.setText(originalName);
        editText.setSelection(originalName.length());
        editText.requestFocus();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        editText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(isNameValidate()){
                    completeClickListener.onClick(originalPos, editText.getText().toString()); dismiss();
                }
            }
            return false;
        });
        view.findViewById(R.id.btnUpdate).setOnClickListener((v)->{
            if(isNameValidate()){
                completeClickListener.onClick(originalPos, editText.getText().toString()); dismiss();
            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener((v)->{
            dismiss();
        });
        view.findViewById(R.id.btnDelete).setOnClickListener((v)->{
            DeleteDialog dialog = DeleteDialog.newInstance(originalName);
            dialog.setDialogListener(()->{
                // 삭제 처리
                completeClickListener.onClick(originalPos, "");
                dismiss();
            });
            dialog.show(getActivity().getFragmentManager(), "Delete Dialog");
        });
    }

    private boolean isNameValidate(){
        Editable editable = editText.getText();

        if (editable == null || editable.toString() == "" || editable.toString().length() <= 0){
            ToastHelper.writeBottomLongToast("최소 1글자 이상 입력하셔야 합니다.");
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            return false;
        }

        return true;
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick(int pos, String toChange);
    }
}
