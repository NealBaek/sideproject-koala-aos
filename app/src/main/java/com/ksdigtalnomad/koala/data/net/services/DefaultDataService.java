package com.ksdigtalnomad.koala.data.net.services;

import com.ksdigtalnomad.koala.data.models.calendar.CalendarDataCategory;
import com.ksdigtalnomad.koala.data.models.calendar.CalendarDataUnit;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DefaultDataService {

    @GET("v1/defaultData/category/{type}")
    Observable<ArrayList<CalendarDataCategory>> getCategoryList(@Path("type") String type);

    @GET("v1/defaultData/unit/{type}")
    Observable<ArrayList<CalendarDataUnit>> getUnitList(@Path("type") String type);

}
