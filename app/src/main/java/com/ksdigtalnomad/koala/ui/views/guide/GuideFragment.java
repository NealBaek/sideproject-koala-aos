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

        switch (sequence) {
            case GuideActivity.GUIDE_FIRST:
//                Glide.with(mContext)
//                        .load(R.drawable.ic_guide_1)
//                        .into(mBinding.guideImage);
                mBinding.guideText.setText(getContext().getResources().getString(R.string.guide_first));
                break;

            case GuideActivity.GUIDE_SECOND:
//                Glide.with(mContext)
//                        .load(R.drawable.ic_guide_2)
//                        .into(mBinding.guideImage);
                mBinding.guideText.setText(getContext().getResources().getString(R.string.guide_second));
                break;

            case GuideActivity.GUIDE_THIRD:
//                Glide.with(mContext)
//                        .load(R.drawable.ic_guide_3)
//                        .into(mBinding.guideImage);
                mBinding.guideText.setText(getContext().getResources().getString(R.string.guide_third));
                break;
        }
    }
}
