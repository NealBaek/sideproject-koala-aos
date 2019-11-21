package com.ksdigtalnomad.koala.ui.views.guide;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentGuideBinding;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;

public class GuideFragment extends BaseFragment {
    private static final String KEY_SEQUENCE = "sequence";
    private String sequence;
    private Context mContext;
    private FragmentGuideBinding mBinding;

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
        mContext= getActivity();
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false);

        return mBinding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.sequence = getArguments().getString(KEY_SEQUENCE);

        String title = "";
        String contents = "";

        switch (sequence) {
            case GuideActivity.GUIDE_1:
                title = getContext().getResources().getString(R.string.guide_title_1);
                contents = getContext().getResources().getString(R.string.guide_contents_1);
                break;

            case GuideActivity.GUIDE_2:
                title = getContext().getResources().getString(R.string.guide_title_2);
                contents = getContext().getResources().getString(R.string.guide_contents_2);
                break;

            case GuideActivity.GUIDE_3:
                title = getContext().getResources().getString(R.string.guide_title_3);
                contents = getContext().getResources().getString(R.string.guide_contents_3);
                break;

            case GuideActivity.GUIDE_4:
                title = getContext().getResources().getString(R.string.guide_title_4);
                contents = getContext().getResources().getString(R.string.guide_contents_4);
                break;
        }


        mBinding.title.setText(title);
        mBinding.contents.setText(contents);
    }
}
