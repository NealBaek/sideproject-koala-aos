package com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.detailListEditNew;

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
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDetailListEditNewBinding;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.ui.KeyboardHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.dialogs.AddDialog;
import com.ksdigtalnomad.koala.ui.views.dialogs.QuantityDrinkDialog;
import com.ksdigtalnomad.koala.ui.views.dialogs.UpdateDialog;
import com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.EditType;
import com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity;

import java.util.ArrayList;

public class CalendarDetailListEditNewActivity extends BaseActivity {

    private EditType editType = EditType.foods;
    private static final String KEY_EDIT_TYPE = "KEY_EDIT_TYPE";
    private static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";

    private DayModel dayModel;

    private ActivityCalendarDetailListEditNewBinding mBinding;
    private CalendarDetailListNewAdapter adapter;

    private ArrayList dataList = new ArrayList<>();

    private final int COLOR_ON = BaseApplication.getInstance().getResources().getColor(R.color.colorMain);
    private final int COLOR_OFF = BaseApplication.getInstance().getResources().getColor(R.color.colorLightGray);

    public static Intent intent(Context context, EditType type, DayModel dayModel) {
        Intent intent = new Intent(context, CalendarDetailListEditNewActivity.class);
        intent.putExtra(KEY_EDIT_TYPE, type);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editType = (EditType) getIntent().getSerializableExtra(KEY_EDIT_TYPE);

//        this.viewType = getIntent().getStringExtra(KEY_EDIT_TYPE);
        this.dayModel = new Gson().fromJson(getIntent().getStringExtra(KEY_DAY_MODEL), DayModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_detail_list_edit_new);
        mBinding.setActivity(this);

        setEditType(editType);

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

        adapter = new CalendarDetailListNewAdapter(this, dataList, editType);
        adapter.setItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener(){
            @Override
            public void onItemClick(int position) {
                setSaveBtnEnable(editType);
            }

            @Override
            public void onItemLongClick(int position) {
                String toUpdate = "";

                switch (editType){
                    case friends: toUpdate = ((ArrayList<Friend>)dataList).get(position).getName(); break;
                    case foods: toUpdate = ((ArrayList<Food>)dataList).get(position).getName(); break;
                    case drinks: toUpdate = ((ArrayList<Drink>)dataList).get(position).getName(); break;
                }


                UpdateDialog dialog = UpdateDialog.newInstance(position, toUpdate);
                dialog.setDialogListener((pos, newName)->{
                    Runnable runnable = ()->{

                        if(isDuplicate(newName, editType, dataList)) return;


                        ArrayList<BaseData> searchList = adapter.getSearchList();
                        int dataListCnt = dataList.size();

                        boolean isDelete = (newName == null || newName.equals(""));

                        if(editType == EditType.friends){
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
                        }else if(editType == EditType.foods){
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
                        }else if(editType == EditType.drinks){
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

                        CalendarDetailListEditNewActivity.this.runOnUiThread(()->{
                            mBinding.dataRv.getAdapter().notifyDataSetChanged();
                            setDataListVisible();
                        });

                    };
                    runnable.run();
                });

                dialog.show(getFragmentManager(), "Update Dialog");
            }
        });
        adapter.setItemdetailClickListener((pos)->{
            QuantityDrinkDialog dialog = QuantityDrinkDialog.newInstance();
            dialog.name = ((ArrayList<Drink>)dataList).get(pos).getName();;
            dialog.setDialogListener((String result, double amount, String unit)->{
                // @TODO:
//                ((ArrayList<Drink>)dataList).get(pos).set
                adapter.notifyItemChanged(pos);
            });
            dialog.show(getFragmentManager(), "QuantityDrink Dialog");
        });

        mBinding.dataRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.dataRv.setAdapter(adapter);

        setDataListVisible();
    }

    private void setEditType(EditType editType){
        if(editType == EditType.friends){
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

        }else if(editType == EditType.foods){
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
        }else if(editType == EditType.drinks){
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

        setSaveBtnEnable(editType);
    }
    private void setSaveBtnEnable(EditType editType){
        if(editType == EditType.friends){
            ArrayList<Friend> list = (ArrayList<Friend>) dataList;
            int listSize = list.size();
            for(int i = 0; i < listSize; ++i){
                if(list.get(i).isSelected()){
                    mBinding.saveBtn.setEnabled(true);
                    mBinding.saveBtn.setBackgroundColor(COLOR_ON);
                    return;
                }
            }
        }else if(editType == EditType.foods){
            ArrayList<Food> list = (ArrayList<Food>) dataList;
            int listSize = list.size();
            for(int i = 0; i < listSize; ++i){
                if(list.get(i).isSelected()){
                    mBinding.saveBtn.setEnabled(true);
                    mBinding.saveBtn.setBackgroundColor(COLOR_ON);
                    return;
                }
            }
        }else if(editType == EditType.drinks){
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
            KeyboardHelper.hide(CalendarDetailListEditNewActivity.this);
        }else{
            super.onBackPressed();
        }
    }
    public void onBackClick(){ finish(); }
    public void onAddClick(){

        // Firebase Event Log - add
        switch (editType){
            case friends: FBEventLogHelper.onFriendsAdd(); break;
            case foods: FBEventLogHelper.onFoodAdd(); break;
            case drinks: FBEventLogHelper.onDrinkAdd(); break;
        }


        String toAdd = "";
        if(mBinding.searchEt != null && mBinding.searchEt.getText() != null){
            toAdd = mBinding.searchEt.getText().toString();
        }

        AddDialog dialog = AddDialog.newInstance(toAdd);
        dialog.setDialogListener((newName)->{
            Runnable runnable = ()->{
                if(isDuplicate(newName, editType, dataList)) return;

                if(editType == EditType.friends){
                    Friend item = Friend.builder().build();
                    item.setName(newName);
                    dataList.add(0, item);
                    adapter.getSearchList().add(0, item);
                    FBEventLogHelper.onFriendsAddDone();
                }else if(editType == EditType.foods){
                    Food item = Food.builder().build();
                    item.setName(newName);
                    dataList.add(0, item);
                    adapter.getSearchList().add(0, item);
                    FBEventLogHelper.onFoodAddDone();
                }else if(editType == EditType.drinks){
                    Drink item = Drink.builder().build();
                    item.setName(newName);
                    dataList.add(0, item);
                    adapter.getSearchList().add(0, item);
                    FBEventLogHelper.onDrinkAddDone();
                }

                CalendarDetailListEditNewActivity.this.runOnUiThread(()->{
                    mBinding.dataRv.getAdapter().notifyDataSetChanged();
                    setDataListVisible();
                });

            };
            runnable.run();

        });

        dialog.show(getFragmentManager(), "Add Dialog");
    }
    public void onSaveClick(){

        if(editType == EditType.friends){
            ArrayList<Friend> list = (ArrayList<Friend>) dataList;

            MainDataController.setFriendList(list);

            for (int i = 0; i < list.size(); ++i){
                if(!list.get(i).isSelected()){ list.remove(list.get(i)); i -= 1;}
            }
            dayModel.friendList.clear();
            dayModel.friendList.addAll(list);

            FBEventLogHelper.onFriendsInputDone();
        }else if(editType == EditType.foods){
            ArrayList<Food> list = (ArrayList<Food>) dataList;

            MainDataController.setFoodList(list);

            for (int i = 0; i < list.size(); ++i){
                if(!list.get(i).isSelected()){ list.remove(list.get(i)); i -= 1;}
            }
            dayModel.foodList.clear();
            dayModel.foodList.addAll(list);

            FBEventLogHelper.onFoodInputDone();
        }else if(editType == EditType.drinks) {
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


    private boolean isDuplicate(String newName, EditType editType, ArrayList dataList){
        boolean isDuplicate = false;

        for(Object data : dataList){
            if(editType == EditType.friends){
                Friend item = (Friend) data;
                if(item.getName().equals(newName)){
                    isDuplicate = true;
                    break;
                }
            }else if(editType == EditType.foods){
                Food item = (Food) data;
                if(item.getName().equals(newName)){
                    isDuplicate = true;
                    break;
                }
            }else if(editType == EditType.drinks){
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
