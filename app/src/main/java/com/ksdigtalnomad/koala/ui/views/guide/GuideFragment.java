package com.ksdigtalnomad.koala.ui.views.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;

public class GuideFragment extends BaseFragment {
    private static final String KEY_SEQUENCE = "sequence";
    private String sequence;
    private Activity activity;

    ImageView image;
    TextView text;


    private static BaseFragment newInstance() {
        return new GuideFragment();
    }

    public static BaseFragment newInstance(String sequence) {
        BaseFragment fragment = newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SEQUENCE, sequence);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, this, R.layout.fragment_guide);
        return null;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.sequence = getArguments().getString(KEY_SEQUENCE);

        switch (sequence) {
            case GuideActivity.GUIDE_FIRST:
//                GlideApp.with(activity)
//                        .load(R.drawable.ic_intro_1)
//                        .into(image);
                text.setText(getContext().getResources().getString(R.string.guide_first));
                break;

            case GuideActivity.GUIDE_SECOND:
//                GlideApp.with(activity)
//                        .load(R.drawable.ic_intro_2)
//                        .into(image);
                text.setText(getContext().getResources().getString(R.string.guide_second));
                break;

            case GuideActivity.GUIDE_THIRD:
//                GlideApp.with(activity)
//                        .load(R.drawable.ic_intro_3)
//                        .into(image);
                text.setText(getContext().getResources().getString(R.string.guide_third));
                break;
        }
    }
}
