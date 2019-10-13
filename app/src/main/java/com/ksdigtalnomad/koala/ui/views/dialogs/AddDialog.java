package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;
import com.ksdigtalnomad.koala.util.ToastHelper;

public class AddDialog extends BaseDialogFragment {
    private static final String KEY_TO_ADD = "KEY_TO_ADD";

    private CompleteClickListener completeClickListener;

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
        getArguments().getString(KEY_TO_ADD);

//        mBinding.searchEt.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                ToastHelper.writeBottomLongToast("검색!");
//            }
//            return false;
//        });

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|);

        view.findViewById(R.id.btnAdd).setOnClickListener((v)->{
            completeClickListener.onClick(); dismiss();
        });
        view.findViewById(R.id.btnCancel).setOnClickListener((v)->{
            dismiss();
        });
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick();
    }

    public void onCancelClick(View v){ dismiss(); }
    public void onAddClick(View v){ completeClickListener.onClick(); dismiss(); }
}
