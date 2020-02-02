package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.MainDataController;
import com.ksdigtalnomad.koala.data.models.calendar.BaseData;
import com.ksdigtalnomad.koala.data.models.calendar.Drink;
import com.ksdigtalnomad.koala.data.models.calendar.Food;
import com.ksdigtalnomad.koala.data.models.calendar.Friend;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDetailListEditBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.dialogs.AddDialog;
import com.ksdigtalnomad.koala.ui.views.dialogs.UpdateDialog;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.ui.KeyboardHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;

import java.util.ArrayList;

public class CalendarDetailListEditActivity extends BaseActivity {

    private String viewType = "";
    private static final String KEY_HEADER_TITLE = "KEY_HEADER_TITLE";
    private static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";
    public static final String TYPE_FRIENDS = "TYPE_FRIENDS";
    public static final String TYPE_FOODS = "TYPE_FOODS";
    public static final String TYPE_DRINKS = "TYPE_DRINKS";

    private DayModel dayModel;

    private ActivityCalendarDetailListEditBinding mBinding;
    private CalendarDetailListAdapter adapter;

    private ArrayList dataList = new ArrayList<>();

    private final int COLOR_ON = BaseApplication.getInstance().getResources().getColor(R.color.colorMain);
    private final int COLOR_OFF = BaseApplication.getInstance().getResources().getColor(R.color.colorLightGray);

    public static Intent intent(Context context, String type, DayModel dayModel) {
        Intent intent = new Intent(context, CalendarDetailListEditActivity.class);
        intent.putExtra(KEY_HEADER_TITLE, type);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewType = getIntent().getStringExtra(KEY_HEADER_TITLE);
        this.dayModel = new Gson().fromJson(getIntent().getStringExtra(KEY_DAY_MODEL), DayModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_detail_list_edit);
        mBinding.setActivity(this);

        setViewType(viewType);

        mBinding.adView.loadAd(new AdRequest.Builder().build());
        mBinding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
            }
        });

        adapter = new CalendarDetailListAdapter(this, dataList, viewType);
        adapter.setItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener(){
            @Override
            public void onItemClick(int position) {
                setSaveBtnEnable(viewType);
            }

            @Override
            public void onItemLongClick(int position) {
                String toUpdate = "";

                if(viewType.equals(TYPE_FRIENDS)){
                    toUpdate = ((ArrayList<Friend>)dataList).get(position).getName();
                }else if(viewType.equals(TYPE_FOODS)){
                    toUpdate = ((ArrayList<Food>)dataList).get(position).getName();
                }else if(viewType.equals(TYPE_DRINKS)){
                    toUpdate = ((ArrayList<Drink>)dataList).get(position).getName();
                }

                UpdateDialog dialog = UpdateDialog.newInstance(position, toUpdate);
                dialog.setDialogListener((pos, newName)->{
                    Runnable runnable = ()->{

                        if(isDuplicate(newName, viewType, dataList)) return;


                        ArrayList<BaseData> searchList = adapter.getSearchList();
                        int dataListCnt = dataList.size();

                        boolean isDelete = (newName == null || newName.equals(""));

                        if(viewType.equals(TYPE_FRIENDS)){
                            Friend searchItem = (Friend) searchList.get(pos);
                            if(isDelete){
                                for(int i = 0; i < dataListCnt; ++i){
                                    Friend originalItem = (Friend) dataList.get(i);
                                    if(searchItem.getName().equals(originalItem.getName())){
                                        dataList.remove(i);
                                        break;
                                    }
                                }
                                searchList.remove(pos);
                            }else{
                                searchItem.setName(newName);
                            }
                        }else if(viewType.equals(TYPE_FOODS)){
                            Food searchItem = (Food) searchList.get(pos);
                            if(isDelete){
                                for(int i = 0; i < dataListCnt; ++i){
                                    Food originalItem = (Food) dataList.get(i);
                                    if(searchItem.getName().equals(originalItem.getName())){
                                        dataList.remove(i);
                                        break;
                                    }
                                }
                                searchList.remove(pos);
                            }else{
                                searchItem.setName(newName);
                            }
                        }else if(viewType.equals(TYPE_DRINKS)){
                            Drink searchItem = (Drink) searchList.get(pos);
                            if(isDelete){
                                for(int i = 0; i < dataListCnt; ++i){
                                    Drink originalItem = (Drink) dataList.get(i);
                                    if(searchItem.getName().equals(originalItem.getName())){
                                        dataList.remove(i);
                                        break;
                                    }
                                }
                                searchList.remove(pos);
                            }else{
                                searchItem.setName(newName);
                            }
                        }

                        CalendarDetailListEditActivity.this.runOnUiThread(()->{
                            mBinding.dataRv.getAdapter().notifyDataSetChanged();
                            setDataListVisible();
                        });

                    };
                    runnable.run();
                });

                dialog.show(getFragmentManager(), "Update Dialog");
            }
        });

        mBinding.dataRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.dataRv.setAdapter(adapter);

        setDataListVisible();
    }

    private void setViewType(String viewType){
        if(viewType.equals(TYPE_FRIENDS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_friends_title));

            ArrayList<Friend> selectedList = dayModel.friendList;
            ArrayList<Friend> savedList = MainDataController.getFriendList();

            int selectedCnt = selectedList.size();
            for(int i = 0; i < selectedCnt; ++i){
                Friend selected = selectedList.get(i);

                int savedCnt = savedList.size();
                for(int j = 0; j < savedCnt; ++j){
                    Friend saved = savedList.get(j);
                    if(selected.getName().equals(saved.getName())){
                        saved.setSelected(selected.isSelected());
                        break;
                    }
                }
            }

            dataList.clear();
            dataList.addAll(savedList);

        }else if(viewType.equals(TYPE_FOODS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_foods_title));

            ArrayList<Food> selectedList = dayModel.foodList;
            ArrayList<Food> savedList = MainDataController.getFoodList();

            int selectedCnt = selectedList.size();
            for(int i = 0; i < selectedCnt; ++i){
                Food selected = selectedList.get(i);

                int savedCnt = savedList.size();
                for(int j = 0; j < savedCnt; ++j){
                    Food saved = savedList.get(j);
                    if(selected.getName().equals(saved.getName())){
                        saved.setSelected(selected.isSelected());
                        break;
                    }
                }
            }

            dataList.clear();
            dataList.addAll(savedList);
        }else if(viewType.equals(TYPE_DRINKS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_drink_title));

            ArrayList<Drink> selectedList = dayModel.drinkList;
            ArrayList<Drink> savedList = MainDataController.getDrinkList();

            int selectedCnt = selectedList.size();
            for(int i = 0; i < selectedCnt; ++i){
                Drink selected = selectedList.get(i);

                int savedCnt = savedList.size();
                for(int j = 0; j < savedCnt; ++j){
                    Drink saved = savedList.get(j);
                    if(selected.getName().equals(saved.getName())){
                        saved.setSelected(selected.isSelected());
                        break;
                    }
                }
            }

            dataList.clear();
            dataList.addAll(savedList);

        }

        setSaveBtnEnable(viewType);
    }
    private void setSaveBtnEnable(String viewType){
        if(viewType.equals(TYPE_FRIENDS)){
            ArrayList<Friend> list = (ArrayList<Friend>) dataList;
            int listSize = list.size();
            for(int i = 0; i < listSize; ++i){
                if(list.get(i).isSelected()){
                    mBinding.saveBtn.setEnabled(true);
                    mBinding.saveBtn.setBackgroundColor(COLOR_ON);
                    return;
                }
            }
        }else if(viewType.equals(TYPE_FOODS)){
            ArrayList<Food> list = (ArrayList<Food>) dataList;
            int listSize = list.size();
            for(int i = 0; i < listSize; ++i){
                if(list.get(i).isSelected()){
                    mBinding.saveBtn.setEnabled(true);
                    mBinding.saveBtn.setBackgroundColor(COLOR_ON);
                    return;
                }
            }
        }else if(viewType.equals(TYPE_DRINKS)){
            ArrayList<Drink> list = (ArrayList<Drink>) dataList;
            int listSize = list.size();
            for(int i = 0; i < listSize; ++i){
                if(list.get(i).isSelected()){
                    mBinding.saveBtn.setEnabled(true);
                    mBinding.saveBtn.setBackgroundColor(COLOR_ON);
                    return;
                }
            }
        }
        if(!dayModel.isSaved){
            mBinding.saveBtn.setEnabled(false);
            mBinding.saveBtn.setBackgroundColor(COLOR_OFF);
        }
    }
    private void setDataListVisible(){
        boolean shouldShow = dataList.size() > 0;
        mBinding.emptyDataLayout.setVisibility(shouldShow ? View.GONE : View.VISIBLE);
        mBinding.dataRv.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }





    @Override
    public void onBackPressed() {
        if(mBinding.searchEt.hasFocus()){
            mBinding.searchEt.clearFocus();
            KeyboardHelper.hide(CalendarDetailListEditActivity.this);
        }else{
            super.onBackPressed();
        }
    }
    public void onBackClick(){ finish(); }
    public void onAddClick(){

        // Firebase Event Log - add
        if(viewType.equals(TYPE_FRIENDS)){
            FBEventLogHelper.onFriendsAdd();
        }else if(viewType.equals(TYPE_FOODS)){
            FBEventLogHelper.onFoodAdd();
        }else if(viewType.equals(TYPE_DRINKS)){
            FBEventLogHelper.onDrinkAdd();
        }


        String toAdd = "";
        if(mBinding.searchEt != null && mBinding.searchEt.getText() != null){
            toAdd = mBinding.searchEt.getText().toString();
        }

        AddDialog dialog = AddDialog.newInstance(toAdd);
        dialog.setDialogListener((newName)->{
            Runnable runnable = ()->{
                if(isDuplicate(newName, viewType, dataList)) return;

                 if(viewType.equals(TYPE_FRIENDS)){
                     Friend item = Friend.builder().build();
                     item.setName(newName);
                     dataList.add(0, item);
                     adapter.getSearchList().add(0, item);
                     FBEventLogHelper.onFriendsAddDone();
                 }else if(viewType.equals(TYPE_FOODS)){
                    Food item = Food.builder().build();
                    item.setName(newName);
                    dataList.add(0, item);
                    adapter.getSearchList().add(0, item);
                     FBEventLogHelper.onFoodAddDone();
                 }else if(viewType.equals(TYPE_DRINKS)){
                     Drink item = Drink.builder().build();
                     item.setName(newName);
                     dataList.add(0, item);
                     adapter.getSearchList().add(0, item);
                     FBEventLogHelper.onDrinkAddDone();
                 }

                CalendarDetailListEditActivity.this.runOnUiThread(()->{
                    mBinding.dataRv.getAdapter().notifyDataSetChanged();
                    setDataListVisible();
                });

            };
            runnable.run();

        });

        dialog.show(getFragmentManager(), "Add Dialog");
    }
    public void onSaveClick(){

        if(viewType.equals(TYPE_FRIENDS)){
            ArrayList<Friend> list = (ArrayList<Friend>) dataList;

            MainDataController.setFriendList(list);

            for (int i = 0; i < list.size(); ++i){
                if(!list.get(i).isSelected()){ list.remove(list.get(i)); i -= 1;}
            }
            dayModel.friendList.clear();
            dayModel.friendList.addAll(list);

            FBEventLogHelper.onFriendsInputDone();
        }else if(viewType.equals(TYPE_FOODS)){
            ArrayList<Food> list = (ArrayList<Food>) dataList;

            MainDataController.setFoodList(list);

            for (int i = 0; i < list.size(); ++i){
                if(!list.get(i).isSelected()){ list.remove(list.get(i)); i -= 1;}
            }
            dayModel.foodList.clear();
            dayModel.foodList.addAll(list);

            FBEventLogHelper.onFoodInputDone();
        }else if(viewType.equals(TYPE_DRINKS)) {
            ArrayList<Drink> list = (ArrayList<Drink>) dataList;

            MainDataController.setDrinkList(list);

            for (int i = 0; i < list.size(); ++i) {
                if (!list.get(i).isSelected()) {
                    list.remove(list.get(i));
                    i -= 1;
                }
            }
            dayModel.drinkList.clear();
            dayModel.drinkList.addAll(list);

            FBEventLogHelper.onDrinkInputDone();
        }

        Intent intent = new Intent();
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        setResult(1, intent);
        finish();
    }


    private boolean isDuplicate(String newName, String viewType, ArrayList dataList){
        boolean isDuplicate = false;

        for(Object data : dataList){
            if(viewType.equals(TYPE_FRIENDS)){
                Friend item = (Friend) data;
                if(item.getName().equals(newName)){
                    isDuplicate = true;
                    break;
                }
            }else if(viewType.equals(TYPE_FOODS)){
                Food item = (Food) data;
                if(item.getName().equals(newName)){
                    isDuplicate = true;
                    break;
                }
            }else if(viewType.equals(TYPE_DRINKS)){
                Drink item = (Drink) data;
                if(item.getName().equals(newName)){
                    isDuplicate = true;
                    break;
                }
            }
        }


        if(isDuplicate) ToastHelper.writeBottomLongToast(getResources().getString(R.string.warning_duplicate_name));

        return isDuplicate;
    }
}
