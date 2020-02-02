package com.ksdigtalnomad.koala.ui.views.dialogs.interview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;

import java.util.ArrayList;

public class InterviewWhythisappDialog extends BaseDialogFragment {

    private InterviewWhythisappListAdapter adapter;
    private RecyclerView recyclerView;
    private String reason = "";

    public static InterviewWhythisappDialog newInstance() { return new InterviewWhythisappDialog();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_interview_whythisapp);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.btnCancel).setOnClickListener((v) -> callPostInterviewWhythisapp());
        view.findViewById(R.id.btnConfirm).setOnClickListener((v) -> callPostInterviewWhythisapp());

        ArrayList<InterviewWhythisappListAdapter.Item> itemList = new ArrayList<>();
        itemList.add(InterviewWhythisappListAdapter.Item.builder().title("금주").isSelected(false).build());
        itemList.add(InterviewWhythisappListAdapter.Item.builder().title("절주").isSelected(false).build());
        itemList.add(InterviewWhythisappListAdapter.Item.builder().title("그냥 기록").isSelected(false).build());

        adapter = new InterviewWhythisappListAdapter(getActivity(), itemList);
        adapter.setItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener(){
            @Override
            public void onItemClick(int position) {
                if(reason.isEmpty()){
                    view.findViewById(R.id.infoLayout1).setVisibility(View.GONE);
                    view.findViewById(R.id.infoLayout2).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.btnCancel).setVisibility(View.GONE);
                    view.findViewById(R.id.btnConfirm).setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onItemLongClick(int position) {
                // do nothing
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void callPostInterviewWhythisapp(){
        ToastHelper.writeBottomLongToast(reason);
    }

}
